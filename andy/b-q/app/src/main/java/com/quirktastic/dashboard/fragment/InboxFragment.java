package com.quirktastic.dashboard.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.quirktastic.R;
import com.quirktastic.core.BaseFragment;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.dashboard.adapter.MyConnectionAdapter;
import com.quirktastic.dashboard.adapter.MyConversationAdapter;
import com.quirktastic.dashboard.friendrequeststatus.FriendRequestStatusActivity;
import com.quirktastic.dashboard.model.modelmyconnection.ModelMyConnection;
import com.quirktastic.dashboard.model.modelmyconnection.USERDETAILSItem;
import com.quirktastic.dashboard.model.modelmyconversation.ModelMyConversation;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.ProgressDialog;
import com.quirktastic.network.Utility;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.locationrequest.GetLatLng;
import com.quirktastic.utility.locationrequest.GpsTracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;
import static com.quirktastic.utility.AppContants.GOOGLE_MAP_API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = InboxFragment.class.getName();

    private View rootView;
    private RecyclerView rvMyConnectionList;
    private RecyclerView rvMyConversationList;
    private RelativeLayout rlMyConnection;
    private RelativeLayout rlMyConversation;
    private RelativeLayout rlFriendRequestStatus;
    private ImageView ivMyConnectionArrow;
    private ImageView ivMyConversationArrow;
    private View viewMyConnection;
    private TextView tvConnectionCount;
    private TextView tvConversationCount;
    private TextView tvBadgeConCount;
    private RelativeLayout rlBadgeCount;

    private MyConnectionAdapter myConnectionAdapter;
    private MyConversationAdapter myConversationAdapter;
    private DashboardActivity dashboardActivity;

    private ArrayList<USERDETAILSItem> listMyConnection;
    private ArrayList<com.quirktastic.dashboard.model.modelmyconversation.USERDETAILSItem> listMyConversation;
    private boolean isConnectionVisible = false;
    private boolean isConversationVisible = false;


    private ProgressDialog progressDialog;

    private String userId;


    private String badgeCount = "";
    private int conversationCount = 0;
    private boolean isDataLoaded = false;

    private GpsTracker gpsTracker;
    private double latitude = 0f;
    private double longitude = 0f;

    private String cityName = "";
    private String stateName = "";
    private String stateShortName = "";

    public InboxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardActivity = (DashboardActivity) getActivity();
        listMyConnection = new ArrayList<>();
        listMyConversation = new ArrayList<>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_inbox, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        rvMyConnectionList = (RecyclerView) rootView.findViewById(R.id.rvMyConnectionList);
        rvMyConversationList = (RecyclerView) rootView.findViewById(R.id.rvMyConversationList);
        rlMyConnection = (RelativeLayout) rootView.findViewById(R.id.rlMyConnection);
        rlMyConversation = (RelativeLayout) rootView.findViewById(R.id.rlMyConversation);
        rlFriendRequestStatus = (RelativeLayout) rootView.findViewById(R.id.rlFriendRequestStatus);
        viewMyConnection = (View) rootView.findViewById(R.id.viewMyConnection);
        ivMyConnectionArrow = (ImageView) rootView.findViewById(R.id.ivMyConnectionArrow);
        ivMyConversationArrow = (ImageView) rootView.findViewById(R.id.ivMyConversationArrow);
        tvConnectionCount = (TextView) rootView.findViewById(R.id.tvConnectionCount);
        tvConversationCount = (TextView) rootView.findViewById(R.id.tvConversationCount);
        tvBadgeConCount = (TextView) rootView.findViewById(R.id.tvBadgeConCount);
        rlBadgeCount = (RelativeLayout) rootView.findViewById(R.id.rlBadgeCount);


        rlMyConnection.setOnClickListener(this);
        rlMyConversation.setOnClickListener(this);
        rlFriendRequestStatus.setOnClickListener(this);

    }

    private void setValuesConnection(USERDETAILSItem userdetailsItem) {
        userdetailsItem.setFirstName(userdetailsItem.getFirstName());
        userdetailsItem.setLastName(userdetailsItem.getLastName());
        userdetailsItem.setCityState(userdetailsItem.getCityName() + ", " + userdetailsItem.getStateShortName());
        userdetailsItem.setFullName(userdetailsItem.getFirstName() + " " + userdetailsItem.getLastName());


        listMyConnection.add(userdetailsItem);

        if (!TextUtils.isEmpty(badgeCount) && badgeCount.equalsIgnoreCase("0")) {
            rlBadgeCount.setVisibility(View.GONE);
        } else {
            rlBadgeCount.setVisibility(View.VISIBLE);
            if (Integer.parseInt(badgeCount) >= 10) {
                tvBadgeConCount.setText(badgeCount + "+");
            } else {
                tvBadgeConCount.setText(badgeCount);
            }
        }


        setConnectionAdapter();
    }

    private void setValuesConversation(com.quirktastic.dashboard.model.modelmyconversation.USERDETAILSItem userdetailsItem) {
        userdetailsItem.setFirstName(userdetailsItem.getFirstName());
        userdetailsItem.setLastName(userdetailsItem.getLastName());
        userdetailsItem.setFullName(userdetailsItem.getFirstName() + " " + userdetailsItem.getLastName());
        userdetailsItem.setAboutUs(userdetailsItem.getAboutUs());
        listMyConversation.add(userdetailsItem);
        conversationCount = conversationCount + Integer.parseInt(userdetailsItem.getUnreadCount());

    }

    private void setConnectionAdapter() {
        if (listMyConnection.size() > 0) {
            tvConnectionCount.setVisibility(View.VISIBLE);
            tvConnectionCount.setText("(" + listMyConnection.size() + ")");
            ivMyConnectionArrow.setVisibility(View.VISIBLE);
        } else {
            ivMyConnectionArrow.setImageResource(R.drawable.icon_right_arrow);
            tvConnectionCount.setVisibility(View.GONE);
            viewMyConnection.setVisibility(View.GONE);
        }


        myConnectionAdapter = new MyConnectionAdapter(dashboardActivity, listMyConnection);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dashboardActivity);
        rvMyConnectionList.setLayoutManager(layoutManager);
        rvMyConnectionList.setNestedScrollingEnabled(false);
        rvMyConnectionList.setAdapter(myConnectionAdapter);
    }

    private void setConversationAdapter() {

        if (listMyConversation.size() > 0) {
            isConversationVisible = true;
            ivMyConversationArrow.setVisibility(View.VISIBLE);
            rvMyConversationList.setVisibility(View.VISIBLE);
            ivMyConversationArrow.setImageResource(R.drawable.icon_down_arrow);
        } else {
            ivMyConversationArrow.setVisibility(View.GONE);
        }

        myConversationAdapter = new MyConversationAdapter(dashboardActivity, listMyConversation);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dashboardActivity);
        rvMyConversationList.setLayoutManager(layoutManager);
        rvMyConversationList.setNestedScrollingEnabled(false);
        rvMyConversationList.setAdapter(myConversationAdapter);
    }

    private void setConversationCount() {
        if (conversationCount > 0) {
            tvConversationCount.setVisibility(View.VISIBLE);
            tvConversationCount.setText("(" + conversationCount + ")");
            dashboardActivity.tvBadgeCountInbox.setVisibility(View.VISIBLE);
            dashboardActivity.tvBadgeCountInbox.setText(""+conversationCount);
        } else {
            tvConversationCount.setVisibility(View.GONE);
            dashboardActivity.tvBadgeCountInbox.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlMyConnection:
                if (listMyConnection.size() == 0) {
                    Toast.show(dashboardActivity, getString(R.string.no_connection_found));
                    return;

                }
                showHideConnection();
                break;
            case R.id.rlMyConversation:
                if (listMyConversation.size() == 0) {
                    Toast.show(dashboardActivity, getString(R.string.no_conversation_found));
                    return;

                }
                showHideConversation();

                break;
            case R.id.rlFriendRequestStatus:
                startActivity(new Intent(getActivity(), FriendRequestStatusActivity.class));
                break;
        }
    }

    private void showHideConnection() {
        if (isConnectionVisible) {
            isConnectionVisible = false;
            ivMyConnectionArrow.setImageResource(R.drawable.icon_right_arrow);
            rvMyConnectionList.setVisibility(View.GONE);
            viewMyConnection.setVisibility(View.GONE);
        } else {
            isConnectionVisible = true;
            rvMyConnectionList.setVisibility(View.VISIBLE);
            viewMyConnection.setVisibility(View.VISIBLE);
            ivMyConnectionArrow.setImageResource(R.drawable.icon_down_arrow);

        }
    }

    private void showHideConversation() {
        if (isConversationVisible) {
            isConversationVisible = false;
            ivMyConversationArrow.setImageResource(R.drawable.icon_right_arrow);
            rvMyConversationList.setVisibility(View.GONE);
        } else {
            isConversationVisible = true;
            rvMyConversationList.setVisibility(View.VISIBLE);
            ivMyConversationArrow.setImageResource(R.drawable.icon_down_arrow);
        }
    }

    private void showProgressDialog(Context context) {
        if (progressDialog != null && !progressDialog.isShowing() && !((Activity) context).isFinishing()) {
            progressDialog.show();
        } else if (!((Activity) context).isFinishing()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;

        }
    }


    private void wsConnectionList() {
        if (Utility.isInternetOn(dashboardActivity)) {
            showProgressDialog(dashboardActivity);
            userId = Prefs.getString(dashboardActivity, PrefsKey.USER_ID, "");
            String url = WSUrl.GET_FRND_LIST + userId;

            new CallNetworkRequest().getResponse(dashboardActivity, false, "wsConnectionList", AUTH_VALUE, url,
                    new INetworkResponse() {
                        @Override
                        public void onSuccess(String response) {
                            try {

                                final ModelMyConnection modelMyConnection = new Gson().fromJson(response, ModelMyConnection.class);
                                if (modelMyConnection != null) {
                                    badgeCount = modelMyConnection.getPENDINGCOUNT();
                                    if (!badgeCount.equalsIgnoreCase("0")) {
                                        rlBadgeCount.setVisibility(View.VISIBLE);
                                        tvBadgeConCount.setText(badgeCount);
                                    }
                                    if (modelMyConnection.isFLAG()) {
                                        if (modelMyConnection.getUSERDETAILS() != null && modelMyConnection.getUSERDETAILS().size() > 0) {
                                            listMyConnection = new ArrayList<>();

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    for (int i = 0; i < modelMyConnection.getUSERDETAILS().size(); i++) {
                                                        setValuesConnection(modelMyConnection.getUSERDETAILS().get(i));
                                                    }
                                                }
                                            }, 100);



                                        }

                                    } else {

                                        listMyConnection = new ArrayList<>();
                                        setConnectionAdapter();

                                    }

                                    wsConversatationList();

                                } else {
                                    hideProgressDialog();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                hideProgressDialog();
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            hideProgressDialog();
                            Toast.show(dashboardActivity, getString(R.string.error_contact_server));
                        }
                    }
            );
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isDataLoaded) {
            wsConnectionList();
        }

    }

    private void wsConversatationList() {
        if (Utility.isInternetOn(dashboardActivity)) {
            userId = Prefs.getString(dashboardActivity, PrefsKey.USER_ID, "");
            String url = WSUrl.GET_FRND_MSG_LIST + userId;

            new CallNetworkRequest().getResponse(dashboardActivity, false, "wsConversatationList", AUTH_VALUE, url,
                    new INetworkResponse() {
                        @Override
                        public void onSuccess(String response) {
                            isDataLoaded = true;
                            hideProgressDialog();
                            try {
                                ModelMyConversation modelMyConversation = new Gson().fromJson(response, ModelMyConversation.class);
                                if (modelMyConversation != null) {
                                    if (modelMyConversation.isFLAG()) {
                                        listMyConversation = new ArrayList<>();
                                        conversationCount = 0;
                                        if (modelMyConversation.getUSERDETAILS() != null && modelMyConversation.getUSERDETAILS().size() > 0) {
                                            for (int i = 0; i < modelMyConversation.getUSERDETAILS().size(); i++) {
                                                setValuesConversation(modelMyConversation.getUSERDETAILS().get(i));
                                            }
                                        }

                                        setConversationAdapter();
                                        setConversationCount();

                                    } else {
                                        hideProgressDialog();
                                    }


                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                hideProgressDialog();
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            hideProgressDialog();
                            Logger.e("error",error.getErrorBody());
                            Toast.show(dashboardActivity, getString(R.string.error_contact_server));
                        }
                    }
            );
        }
    }

    @Override
    public void onDestroyView() {
        isDataLoaded = false;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        isDataLoaded = false;
        super.onDestroy();
    }

    @Override
    public void onStop() {
        isDataLoaded = false;
        super.onStop();
    }


}
