package com.quirktastic.dashboard.profile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quirktastic.R;
import com.quirktastic.dashboard.profile.model.profiledetails.InterestItem;

import java.util.List;

public class InterestCheckAdapter extends RecyclerView.Adapter<InterestCheckAdapter.GenderHolder> {

    private List<InterestItem> interestList;
    private Context context;

    public InterestCheckAdapter(Context context, List<InterestItem> list) {
        this.interestList = list;
        this.context=context;
    }

    @NonNull
    @Override
    public GenderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GenderHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_checkbox, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final GenderHolder genderHolder, int i) {
        final InterestItem interestListItem = interestList.get(i);
        genderHolder.tvTitle.setText(interestList.get(i).getInterestName());
        //in some cases, it will prevent unwanted situations
        genderHolder.checkBox.setOnCheckedChangeListener(null);
        //if true, your checkbox will be selected, else unselected
        if (interestListItem.getIsSelected() == 1) {
            genderHolder.checkBox.setChecked(true);
        } else  {
            genderHolder.checkBox.setChecked(false);
        }

        genderHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                if (isChecked) {
                    interestListItem.setIsSelected(1);
                } else  {
                    interestListItem.setIsSelected(0);
                }

            }
        });

        genderHolder.llCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderHolder.checkBox.setChecked(!genderHolder.checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return interestList.size();
    }

    public class GenderHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView tvTitle;
        LinearLayout llCheckBox;

        public GenderHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            llCheckBox=itemView.findViewById(R.id.llCheckBox);
        }
    }
}
