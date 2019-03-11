package br.com.instachat.emojilibrary.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import br.com.instachat.emojilibrary.R;
import br.com.instachat.emojilibrary.controller.WhatsAppPanel;


public class GifAdapter extends RecyclerView.Adapter<GifAdapter.ViewHolder> {
    private Context context;
    private final LayoutInflater layoutInflater;
    private ArrayList<Integer> listGif;
    private WhatsAppPanel whatsAppPanel;

    public GifAdapter(Context context, ArrayList<Integer> listGif, WhatsAppPanel whatsAppPanel) {
        this.context = context;
        this.whatsAppPanel=whatsAppPanel;
        this.listGif=listGif;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.rsc_gif_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {


        Glide.with(context).load(listGif.get(i))
                .apply(RequestOptions
                        .placeholderOf(R.drawable.ic_emoji_nature_light_activated)
                        .error(R.drawable.ic_emoji_nature_light_activated)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewHolder.imgGif);

        viewHolder.imgGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    whatsAppPanel.onGifClick(getFileByResourceId(listGif.get(i),"test.gif"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return listGif.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGif;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGif = (ImageView) itemView.findViewById(R.id.imgGif);
        }
    }

    private File getFileByResourceId(int id, String fileName) throws IOException {
        String filePath = context.getFilesDir().getPath().toString() + "/"+fileName;
//        File f = new File(filePath);
        File file = new File(filePath);
        InputStream ins = context.getResources().openRawResource(id);
       /* log.debug(ins.toString());
        log.debug(file.getAbsolutePath());*/
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int size = 0;
        // Read the entire resource into a local byte buffer.
        byte[] buffer = new byte[1024];
        while ((size = ins.read(buffer, 0, 1024)) >= 0) {
            outputStream.write(buffer, 0, size);
        }
        ins.close();
        buffer = outputStream.toByteArray();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(buffer);
        fos.close();
        return file;}

}
