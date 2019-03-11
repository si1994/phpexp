package com.quirktastic.dashboard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quirktastic.R;
import com.quirktastic.dashboard.model.modeluserlist.OtherUserSocialModel;

import java.util.ArrayList;

public class OtherUserSocialAdapter extends RecyclerView.Adapter<OtherUserSocialAdapter.OtherUserInterestHolder> {
    private Context context;
    private ArrayList<OtherUserSocialModel> mList;
    private final LayoutInflater layoutInflater;

    public OtherUserSocialAdapter(Context context, ArrayList<OtherUserSocialModel> mList) {
        this.context = context;
        this.mList = mList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public OtherUserInterestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.item_social_for_view,viewGroup,false);
        return new OtherUserInterestHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull OtherUserInterestHolder otherUserInterestHolder, int i) {


//        if (mList.get(i).getDrawable().equalsIgnoreCase("")) {
//            Glide.with(mContext).load(R.drawable.blog_image).placeholder(R.drawable.blog_image).thumbnail(0.1f).into(viewHolder.ivLatestPostPic);
//        } else {
//            Glide.with(mContext).load(mList.get(i).getDrawable()).placeholder(R.mipmap.ic_launcher).thumbnail(0.1f).into(viewHolder.ivLatestPostPic);
//        }
        otherUserInterestHolder.ivSocialImage.setImageResource(mList.get(i).getDrawable());

        if(mList.get(i).getType().equalsIgnoreCase("0")){
            otherUserInterestHolder.tvInterestItem.setText(context.getString(R.string.no));
        }else if(mList.get(i).getType().equalsIgnoreCase("1")){
            otherUserInterestHolder.tvInterestItem.setText(context.getString(R.string.yes));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class  OtherUserInterestHolder extends RecyclerView.ViewHolder{
        private ImageView ivSocialImage;
        private TextView tvInterestItem;
        public OtherUserInterestHolder(@NonNull View itemView) {
            super(itemView);
            ivSocialImage = (ImageView) itemView.findViewById(R.id.ivSocialImage);
            tvInterestItem = (TextView) itemView.findViewById(R.id.tvInterestItem);
        }
    }
}
