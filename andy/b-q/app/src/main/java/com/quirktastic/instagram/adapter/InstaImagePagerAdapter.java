package com.quirktastic.instagram.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.quirktastic.R;
import com.quirktastic.instagram.model.instagram.DataItem;

import java.util.List;

public class InstaImagePagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<DataItem> data;

    public InstaImagePagerAdapter(Context context, List<DataItem> data) {
        this.data = data;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((LinearLayout) o);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.insta_feed_pager_item, container, false);

        PhotoView imageView = (PhotoView) itemView.findViewById(R.id.imgInstaFeedPager);
        final LinearLayout llPdImage = (LinearLayout) itemView.findViewById(R.id.llPdImage);

        DataItem dataItem = data.get(position);


        llPdImage.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(dataItem.getImages().getStandardResolution().getUrl())
                .apply(RequestOptions
                        .placeholderOf(R.drawable.img_placeholder)
                        .error(R.drawable.img_placeholder)
                        .priority(Priority.IMMEDIATE)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)).listener(new RequestListener<Drawable>() {

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                llPdImage.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                llPdImage.setVisibility(View.GONE);
                return false;
            }
        })
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
