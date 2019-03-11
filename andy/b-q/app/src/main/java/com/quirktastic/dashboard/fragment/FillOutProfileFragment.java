package com.quirktastic.dashboard.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.quirktastic.R;
import com.quirktastic.core.BaseFragment;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.dashboard.profile.ProfileActivity;
import com.quirktastic.utility.AppContants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FillOutProfileFragment extends BaseFragment {
    public static final String TAG = FillOutProfileFragment.class.getName();
    @BindView(R.id.llFillOutProfile)
    LinearLayout llFillOutProfile;
    Unbinder unbinder;

    private View rootView;

    private DashboardActivity dashboardActivity;


    public FillOutProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardActivity = (DashboardActivity) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fill_out_profile, container, false);
        // Inflate the layout for this fragment
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.llFillOutProfile)
    public void onViewClicked() {

        Intent intentProfile = new Intent(getActivity(), ProfileActivity.class);
        dashboardActivity.startActivity(intentProfile);
    }


}
