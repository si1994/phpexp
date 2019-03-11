package com.quirktastic.chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.quirktastic.R;
import com.quirktastic.chat.chatmodel.USERDETAILSItem;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import br.com.instachat.emojilibrary.model.layout.EmojiTextView;

/**
 * @author Deepak
 * @created on Thu,Nov,2018
 * @updated on $file.lastModified
 */
public class ChatRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<USERDETAILSItem> mList;
    private final LayoutInflater layoutInflater;
    private String recv_profile_pic;

    public ChatRecycleAdapter(Context context, List<USERDETAILSItem> mList,String recv_profile_pic) {
        this.context = context;
        this.mList = mList;
        this.recv_profile_pic = recv_profile_pic;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        if(i == 0){
            View itemViewSend = layoutInflater.inflate(R.layout.row_item_send_msg,viewGroup,false);
            viewHolder = new ChatSendViewHolder(itemViewSend);
        }else{
            View itemViewRecieve = layoutInflater.inflate(R.layout.row_item_recieve_msg,viewGroup,false);
            viewHolder = new ChatReceiveViewHolder(itemViewRecieve);
        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder.getItemViewType() == 0) {
            ChatSendViewHolder firstVH = (ChatSendViewHolder) viewHolder;

           firstVH.tvDateSend.setText(Util.getChatDate(mList.get(i).getCreatedDate()));
           firstVH.tvTimeSend.setText(Util.getChatTime(mList.get(i).getCreatedDate()));


            if(mList.get(i).getMassage().equals("") && !mList.get(i).getMessageUrl().equals(""))
            {

                firstVH.rlSendMsg.setVisibility(View.GONE);
                firstVH.imgGifSend.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load( mList.get(i).getMessageUrl())
                        .apply(RequestOptions
                                .placeholderOf(R.drawable.user_placeholder)
                                .error(R.drawable.user_placeholder)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(firstVH.imgGifSend);
            }
            else if(!mList.get(i).getMassage().equals("") && mList.get(i).getMessageUrl().equals("")){
                firstVH.rlSendMsg.setVisibility(View.VISIBLE);
                firstVH.imgGifSend.setVisibility(View.GONE);
                try {
                    firstVH.tvSendMsg.setText(URLDecoder.decode(
                            mList.get(i).getMassage().trim(), "UTF-8").trim());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            Glide.with(context)
                    .load(Prefs.getString(context,PrefsKey.PROFILE_PIC,""))
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.user_placeholder)
                            .error(R.drawable.user_placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .override(100, 100))
                    .into(firstVH.ivChatSendImg);

        } else {
            ChatReceiveViewHolder openVH = (ChatReceiveViewHolder) viewHolder;

            openVH.tvDateReceive.setText(Util.getChatDate(mList.get(i).getCreatedDate()));
            openVH.tvTimeReceive.setText(Util.getChatTime(mList.get(i).getCreatedDate()));

            if(mList.get(i).getMassage().equals("") && !mList.get(i).getMessageUrl().equals(""))
            {
                openVH.imgGifReceive.setVisibility(View.VISIBLE);
                openVH.rlReceivedMsg.setVisibility(View.GONE);
                Glide.with(context)
                        .load( mList.get(i).getMessageUrl())
                        .apply(RequestOptions
                                .placeholderOf(R.drawable.user_placeholder)
                                .error(R.drawable.user_placeholder)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(openVH.imgGifReceive);
            }

            else if(!mList.get(i).getMassage().equals("") && mList.get(i).getMessageUrl().equals("")){

                try {
                    openVH.imgGifReceive.setVisibility(View.GONE);
                    openVH.rlReceivedMsg.setVisibility(View.VISIBLE);
                    openVH.tvReceivedMsg.setText(URLDecoder.decode(
                            mList.get(i).getMassage().trim(), "UTF-8").trim());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }


            Glide.with(context)
                    .load(recv_profile_pic)
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.user_placeholder)
                            .error(R.drawable.user_placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .override(100, 100))
                    .into(openVH.ivChatRecvImg);

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mList.get(position).getFromUserId().equalsIgnoreCase(Prefs.getString(context,PrefsKey.USER_ID,""))){
            return 0;
        }else{
            return 1;
        }

    }

    public class ChatSendViewHolder extends RecyclerView.ViewHolder{
        private EmojiTextView tvSendMsg;
        private ImageView ivChatSendImg;
        private ImageView imgGifSend;
        private RelativeLayout rlSendMsg;
        private TextView tvDateSend,tvTimeSend;
        public ChatSendViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSendMsg = (EmojiTextView)itemView.findViewById(R.id.tvSendMsg);
            ivChatSendImg = (ImageView)itemView.findViewById(R.id.ivChatSendImg);
            imgGifSend = (ImageView)itemView.findViewById(R.id.imgGifSend);
            rlSendMsg = (RelativeLayout)itemView.findViewById(R.id.rlSendMsg);
            tvDateSend = (TextView)itemView.findViewById(R.id.tvDateSend);
            tvTimeSend = (TextView)itemView.findViewById(R.id.tvTimeSend);
        }
    }

    public class ChatReceiveViewHolder extends RecyclerView.ViewHolder{
        private EmojiTextView tvReceivedMsg;
        private ImageView ivChatRecvImg;
        private ImageView imgGifReceive;
        private RelativeLayout rlReceivedMsg;
        private TextView tvDateReceive,tvTimeReceive;
        public ChatReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReceivedMsg = (EmojiTextView) itemView.findViewById(R.id.tvReceivedMsg);
            ivChatRecvImg = (ImageView)itemView.findViewById(R.id.ivChatRecvImg);
            imgGifReceive = (ImageView)itemView.findViewById(R.id.imgGifReceive);
            rlReceivedMsg = (RelativeLayout)itemView.findViewById(R.id.rlReceivedMsg);
            tvDateReceive = (TextView)itemView.findViewById(R.id.tvDateReceive);
            tvTimeReceive = (TextView)itemView.findViewById(R.id.tvTimeReceive);
        }
    }
}
