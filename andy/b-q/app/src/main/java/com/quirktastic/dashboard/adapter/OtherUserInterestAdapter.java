package com.quirktastic.dashboard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quirktastic.R;

import java.util.ArrayList;

public class OtherUserInterestAdapter extends RecyclerView.Adapter<OtherUserInterestAdapter.OtherUserInterestHolder> {
    private Context context;
    private ArrayList<String> mList;
    private final LayoutInflater layoutInflater;

    public OtherUserInterestAdapter(Context context, ArrayList<String> mList) {
        this.context = context;
        this.mList = mList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public OtherUserInterestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.item_interest_for_view,viewGroup,false);
        return new OtherUserInterestHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull OtherUserInterestHolder otherUserInterestHolder, int i) {
        otherUserInterestHolder.tvInterestItem.setBackgroundResource(R.drawable.rounded_corner_black);
        otherUserInterestHolder.tvInterestItem.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
        otherUserInterestHolder.tvInterestItem.setText(mList.get(i));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class  OtherUserInterestHolder extends RecyclerView.ViewHolder{
        private TextView tvInterestItem;
        public OtherUserInterestHolder(@NonNull View itemView) {
            super(itemView);
            tvInterestItem = (TextView) itemView.findViewById(R.id.tvInterestItem);
        }
    }
}
