package com.quirktastic.onboard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quirktastic.R;
import com.quirktastic.onboard.model.sociallife.SocialLifeListItem;

import java.util.ArrayList;

public class SocialLifeListAdapter extends RecyclerView.Adapter<SocialLifeListAdapter.InterestListHolder> {

    private ArrayList<SocialLifeListItem> genderList;
    private Context context;

    public SocialLifeListAdapter(Context context, ArrayList<SocialLifeListItem> list) {
        this.genderList = list;
        this.context = context;

    }

    @NonNull
    @Override
    public InterestListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new InterestListHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_interest, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull InterestListHolder holder, final int position) {

        if (genderList.get(position).getName() != null) {
            holder.tvInterestItem.setText(genderList.get(position).getName());
        }


        if (genderList.get(position).isSelected()) {
            holder.tvInterestItem.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            holder.tvInterestItem.setBackgroundResource(R.drawable.rectangle_blue_fill);
        } else {
            holder.tvInterestItem.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.tvInterestItem.setBackgroundResource(R.drawable.rectangle_blue_line);
        }


        holder.tvInterestItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (genderList.get(position).isSelected()) {
                    genderList.get(position).setSelected(false);
                } else {
                    genderList.get(position).setSelected(true);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return genderList.size();
    }

    public class InterestListHolder extends RecyclerView.ViewHolder {

        TextView tvInterestItem;

        public InterestListHolder(@NonNull View itemView) {
            super(itemView);

            tvInterestItem = itemView.findViewById(R.id.tvInterestItem);

        }
    }

}
