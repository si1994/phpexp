package com.quirktastic.onboard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.quirktastic.R;
import com.quirktastic.onboard.activity.AddPhotosActivity;
import com.quirktastic.onboard.model.addphotos.AddPhotoModel;

import java.util.ArrayList;

/**
 * @author Deepak
 * @created on Wed,Nov,2018
 * @updated on $file.lastModified
 */
public class AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.AddPhotoHolder> {
    private Context context;
    private ArrayList<AddPhotoModel> mListFile;
    private final LayoutInflater layoutInflater;

    public AddPhotoAdapter(Context context, ArrayList<AddPhotoModel> mListFile) {
        this.context = context;
        this.mListFile = mListFile;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public AddPhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.row_item_add_photo,viewGroup,false);
        return new AddPhotoHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull AddPhotoHolder addPhotoHolder, final int i) {
        addPhotoHolder.ivRowAddPhoto.setImageURI(mListFile.get(i).getUri());
        addPhotoHolder.ivCloseAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof AddPhotosActivity){
                    ((AddPhotosActivity)context).deletePhoto(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListFile.size();
    }

    public class AddPhotoHolder extends RecyclerView.ViewHolder{
        private ImageView ivRowAddPhoto;
        private ImageView ivCloseAddPhoto;
        public AddPhotoHolder(@NonNull View itemView) {
            super(itemView);
            ivRowAddPhoto = (ImageView)itemView.findViewById(R.id.ivRowAddPhoto);
            ivCloseAddPhoto = (ImageView)itemView.findViewById(R.id.ivCloseAddPhoto);
        }
    }

}
