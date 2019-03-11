package com.quirktastic.dashboard.profile.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quirktastic.R;
import com.quirktastic.dashboard.profile.model.profiledetails.IdentityItem;
import com.quirktastic.onboard.activity.AddPhoneNumberActivity;
import com.quirktastic.onboard.activity.VerifyPhoneNumberActivity;

import java.util.List;

public class IdentityCheckAdapter extends RecyclerView.Adapter<IdentityCheckAdapter.GenderHolder> {

    private List<IdentityItem> identitiyList;
    private Context context;
    private int selectedPosition = -1;

    public IdentityCheckAdapter(Context context, List<IdentityItem> list) {
        this.identitiyList = list;
        this.context=context;
        selectedPosition = getSelectedIdentityPosition();
    }

    @NonNull
    @Override
    public GenderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GenderHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_checkbox, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenderHolder genderHolder, final int position) {
        final IdentityItem identitiyListItem = identitiyList.get(position);
        genderHolder.tvTitle.setText(identitiyList.get(position).getIdentityName());

        if (position == selectedPosition) {
            genderHolder.checkBox.setChecked(true);
            identitiyList.get(position).setIsSelected(1);
        } else  {
            genderHolder.checkBox.setChecked(false);
            identitiyList.get(position).setIsSelected(0);
        }


        genderHolder.llCheckBox.setOnClickListener(onStateChangedListener(genderHolder.checkBox, position));
    }

    private void managechecked(int position) {

        for (int i = 0; i < identitiyList.size(); i++) {
            if (i == position) {
                identitiyList.get(i).setIsSelected(1);
            } else {
                identitiyList.get(i).setIsSelected(0);
            }
        }

    }

    private View.OnClickListener onStateChangedListener(final CheckBox checkBox, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());

                if (checkBox.isChecked()) {
                    selectedPosition = position;
                } else {
                    selectedPosition = -1;
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                }, 200);


            }
        };
    }

    @Override
    public int getItemCount() {
        return identitiyList.size();
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

    private int getSelectedIdentityPosition() {
        for (int i = 0; i <identitiyList.size(); i++) {

            if (identitiyList.get(i).getIsSelected() == 1) {
                return i;
            }
        }

        return -1;
    }
}
