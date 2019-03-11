package com.quirktastic.dashboard.profile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.chat.ReportActivity;
import com.quirktastic.dashboard.profile.EditProfileFragment;
import com.quirktastic.dashboard.profile.ProfileActivity;
import com.quirktastic.dashboard.profile.model.changephotoorder.ChangePhotoOrderResponse;
import com.quirktastic.dashboard.profile.model.profiledetails.PhotosItem;
import com.quirktastic.dashboard.profile.model.profiledetails.SocialLifeItem;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.sendrequest.model.ModelSendRequest;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.ItemMoveCallback;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.StartDragListener;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MyPhotosAdapter extends RecyclerView.Adapter<MyPhotosAdapter.ViewHolder> implements ItemMoveCallback.ItemTouchHelperContract {

    private ArrayList<PhotosItem> photosItemsList;
    private ProfileActivity profileActivity;
    private int imageWidthHeight;
    private StartDragListener startDragListener;
    private EditProfileFragment editProfileFragment;

    public MyPhotosAdapter(ProfileActivity profileActivity, EditProfileFragment editProfileFragment, ArrayList<PhotosItem> photosItemsList, int imageWidthHeight, StartDragListener startDragListener) {
        this.photosItemsList = photosItemsList;
        this.profileActivity = profileActivity;
        this.imageWidthHeight = imageWidthHeight;
        this.startDragListener = startDragListener;
        this.editProfileFragment = editProfileFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_myphotos, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {


        if (photosItemsList != null) {

            final PhotosItem photosItem = photosItemsList.get(position);


            if (!photosItem.getPhotosUri().equals("")) {

                viewHolder.ivMyPhoto.setImageURI(Util.stringToUri(photosItem.getPhotosUri()));
                viewHolder.ivRemoveImage.setVisibility(View.VISIBLE);
                viewHolder.ivAddImage.setVisibility(View.GONE);
                viewHolder.ivMyPhoto.setVisibility(View.VISIBLE);

            } else if (!photosItem.getPhotos().equals("")) {
                Glide.with(profileActivity)
                        .load(photosItem.getPhotos())
                        .apply(RequestOptions
                                .placeholderOf(R.drawable.img_placeholder)
                                .error(R.drawable.img_placeholder)
                                .priority(Priority.IMMEDIATE)
                                .diskCacheStrategy(DiskCacheStrategy.DATA)
                                .override(imageWidthHeight, imageWidthHeight))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                viewHolder.ivRemoveImage.setVisibility(View.VISIBLE);
                                viewHolder.ivAddImage.setVisibility(View.GONE);
                                viewHolder.ivMyPhoto.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
                        .into(viewHolder.ivMyPhoto);
            } else {
                viewHolder.ivRemoveImage.setVisibility(View.GONE);
                viewHolder.ivAddImage.setVisibility(View.VISIBLE);
                viewHolder.ivMyPhoto.setVisibility(View.GONE);
                viewHolder.itemView.setMinimumHeight(imageWidthHeight);
                viewHolder.itemView.setMinimumWidth(imageWidthHeight);
            }


            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    startDragListener.requestDrag(viewHolder);
                    return false;
                }
            });

            viewHolder.ivRemoveImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditProfileFragment.totalSize=EditProfileFragment.totalSize-1;

                    viewHolder.ivRemoveImage.setVisibility(View.GONE);
                    viewHolder.ivAddImage.setVisibility(View.VISIBLE);
                    viewHolder.ivMyPhoto.setVisibility(View.GONE);
                    viewHolder.itemView.setMinimumHeight(imageWidthHeight);
                    viewHolder.itemView.setMinimumWidth(imageWidthHeight);

                    if(photosItem.getId()!=null) {
                        if (!photosItem.getId().equals("")) {
                            editProfileFragment.deleteImageAPI(photosItem.getId(), position);
                        }
                    }

                    photosItem.setId("");
                    photosItem.setPhotosUri("");
                    photosItem.setPhotos("");

                    notifyDataSetChanged();

                }
            });

        } else {
            viewHolder.ivRemoveImage.setVisibility(View.GONE);
            viewHolder.ivAddImage.setVisibility(View.VISIBLE);
            viewHolder.ivMyPhoto.setVisibility(View.GONE);
            viewHolder.itemView.setMinimumHeight(imageWidthHeight);
            viewHolder.itemView.setMinimumWidth(imageWidthHeight);
        }

        viewHolder.ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfileFragment.addPhoto(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {

        profileActivity.vpProfile.setPagingEnabled(false);

        Logger.e("position___", fromPosition + " : " + toPosition);

        Collections.swap(photosItemsList, fromPosition, toPosition);

       /* if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(photosItemsList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(photosItemsList, i, i - 1);
            }
        }*/
        notifyDataSetChanged();
    }

    @Override
    public void onRowSelected(ViewHolder myViewHolder) {

        myViewHolder.itemView.setBackgroundColor(Color.GRAY);

    }

    @Override
    public void onRowClear(ViewHolder myViewHolder) {
        myViewHolder.itemView.setBackgroundColor(Color.WHITE);
        notifyDataSetChanged();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                profileActivity.vpProfile.setPagingEnabled(true);
            }
        }, 200);

       /* JSONArray jsonArray = new JSONArray();
        int index = 1;
        for (PhotosItem photosItem : photosItemsList) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("photo_id", photosItem.getId());
                jsonObject.put("position", index++);
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Logger.e("ChangeOrderWs", jsonArray.toString());*/

        //callChangeOrderWs(homeMainActivity, jsonArray.toString());

        //changePhotoOrderWs(jsonArray.toString());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAddImage, ivMyPhoto, ivRemoveImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAddImage = (ImageView) itemView.findViewById(R.id.ivAddImage);
            ivMyPhoto = (ImageView) itemView.findViewById(R.id.ivMyPhoto);
            ivRemoveImage = (ImageView) itemView.findViewById(R.id.ivRemoveImage);

        }
    }

}
