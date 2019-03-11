package com.quirktastic.onboard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quirktastic.R;
import com.quirktastic.dashboard.profile.model.profiledetails.SocialLifeItem;

import java.util.ArrayList;

public class SocialLifeListForViewAdapter extends RecyclerView.Adapter<SocialLifeListForViewAdapter.InterestListHolder> {

    private ArrayList<SocialLifeItem> genderList;
    private Context context;

    public SocialLifeListForViewAdapter(Context context, ArrayList<SocialLifeItem> list) {
        this.genderList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public InterestListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new InterestListHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_social_for_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InterestListHolder holder, final int position) {

        if (genderList.get(position).getName() != null) {
            if (genderList.get(position).getName().trim().equalsIgnoreCase("drinking")) {

                holder.ivSocialImage.setImageResource(R.drawable.icon_drinking);
                if (genderList.get(position).getIsSelected() == 1) {
                    holder.tvInterestItem.setText("Yes");
                } else  {
                    holder.tvInterestItem.setText("No");
                }

            } else if (genderList.get(position).getName().trim().equalsIgnoreCase("smoking")) {

                holder.ivSocialImage.setImageResource(R.drawable.icon_smoking);

                if (genderList.get(position).getIsSelected() == 1) {
                    holder.tvInterestItem.setText("Yes");
                } else  {
                    holder.tvInterestItem.setText("No");
                }

            } else if (genderList.get(position).getName().trim().equalsIgnoreCase("marijuana")
                    || genderList.get(position).getName().trim().equalsIgnoreCase("marijuanas")) {

                holder.ivSocialImage.setImageResource(R.drawable.icon_marijuana);

                if (genderList.get(position).getIsSelected() == 1) {
                    holder.tvInterestItem.setText("Yes");
                } else  {
                    holder.tvInterestItem.setText("No");
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return genderList.size();
    }

    public class InterestListHolder extends RecyclerView.ViewHolder {

        TextView tvInterestItem;
        ImageView ivSocialImage;

        public InterestListHolder(@NonNull View itemView) {
            super(itemView);

            tvInterestItem = itemView.findViewById(R.id.tvInterestItem);
            ivSocialImage = itemView.findViewById(R.id.ivSocialImage);

        }
    }

}
