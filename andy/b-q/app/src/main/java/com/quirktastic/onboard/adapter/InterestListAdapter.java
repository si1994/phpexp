package com.quirktastic.onboard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quirktastic.R;
import com.quirktastic.onboard.model.InterestListItem;

import java.util.ArrayList;

public class InterestListAdapter extends RecyclerView.Adapter<InterestListAdapter.InterestListHolder> {


    private ArrayList<InterestListItem> genderList;
    private Context context;

    public InterestListAdapter(Context context, ArrayList<InterestListItem> list) {
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

        if (genderList.get(position).getInterestName() != null) {
            holder.tvInterestItem.setText(genderList.get(position).getInterestName());
        }

        if (genderList.get(position).isSelected()) {
            holder.tvInterestItem.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            holder.tvInterestItem.setBackgroundResource(R.drawable.rectangle_blue_fill);
        } else {
            holder.tvInterestItem.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.tvInterestItem.setBackgroundResource(R.drawable.rectangle_blue_line);
        }

        int padding_5 = (int) context.getResources().getDimension(R.dimen._5sdp);
        int padding_14 = (int) context.getResources().getDimension(R.dimen._14sdp);
        int padding_7 = (int) context.getResources().getDimension(R.dimen._7sdp);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (position % 2 == 1) {
            params.setMargins(padding_7, padding_5, padding_14, padding_5);
            holder.tvInterestItem.setLayoutParams(params);
        } else  {
            params.setMargins(padding_14, padding_5, padding_7, padding_5);
            holder.tvInterestItem.setLayoutParams(params);
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
