package com.quirktastic.instagram.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.quirktastic.R;
import com.quirktastic.instagram.InstaImageSlideActivity;
import com.quirktastic.instagram.model.instagram.DataItem;
import com.quirktastic.utility.AppContants;

import java.util.List;

public class InstaFeedAdapter extends RecyclerView.Adapter<InstaFeedAdapter.ViewHolder> {

    private List<DataItem> data;
    private Context context;

    public InstaFeedAdapter(Context context, List<DataItem> data) {
        this.context=context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_instafeed, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        DataItem dataItem=data.get(position);

        if(dataItem.getImages()!=null) {
            Glide.with(context)
                    .load(dataItem.getImages().getLowResolution().getUrl())
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.img_placeholder)
                            .error(R.drawable.img_placeholder)
                            .priority(Priority.IMMEDIATE)
                            .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            return false;
                        }
                    })
                    .into(viewHolder.imgInsta);

            viewHolder.imgInsta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppContants.INSTA_CURRENT_PAGER_ITEM=position;
                    AppContants.INSTA_FEED_LIST=data;
                    context.startActivity(new Intent(context, InstaImageSlideActivity.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgInsta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgInsta = (ImageView) itemView.findViewById(R.id.imgInsta);
        }
    }
}
