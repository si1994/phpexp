package com.quirktastic.dashboard.friendrequeststatus;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.core.BaseActivity;
import com.quirktastic.dashboard.friendrequeststatus.adapter.RequestStatusAdapter;
import com.quirktastic.dashboard.friendrequeststatus.model.FriendRequestListResponse;
import com.quirktastic.dashboard.fragment.InboxFragment;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.Utility;
import com.quirktastic.network.WSUrl;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.SpacesItemDecoration;
import com.quirktastic.utility.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class FriendRequestStatusActivity extends BaseActivity {

    public static final String TAG = InboxFragment.class.getName();
    @BindView(R.id.rvRequestStatus)
    RecyclerView rvRequestStatus;
    @BindView(R.id.tvBack)
    TextView tvBack;
    /*@BindView(R.id.lavNoData)
    LottieAnimationView lavNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;*/
    @BindView(R.id.llNoData)
    LinearLayout llNoData;

    private RequestStatusAdapter requestStatusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_status);
        ButterKnife.bind(this);
        getRequestStatusAPI();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void showNodataFound(String textMsg, String iconMsg) {
        llNoData.setVisibility(View.VISIBLE);
        rvRequestStatus.setVisibility(View.GONE);
       // lavNoData.setAnimation(iconMsg);
       // tvNoData.setText(textMsg);
        //lavNoData.playAnimation();
      //  lavNoData.loop(true);


    }

    public void hideNodataFound() {
        llNoData.setVisibility(View.GONE);
        rvRequestStatus.setVisibility(View.VISIBLE);
       // lavNoData.cancelAnimation();

    }


// calling api for getting list of friend request.
    private void getRequestStatusAPI() {

        if (Utility.isInternetOn(FriendRequestStatusActivity.this)) {


            new CallNetworkRequest().getResponse(FriendRequestStatusActivity.this, true, "getRequestStatusAPI", AUTH_VALUE, WSUrl.GET_USER_PENDING_LIST + Prefs.getString(FriendRequestStatusActivity.this, PrefsKey.USER_ID, ""),
                    new INetworkResponse() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                Gson gson = new Gson();
                                FriendRequestListResponse friendRequestListResponse = gson.fromJson(response, FriendRequestListResponse.class);

                                if (friendRequestListResponse.isFLAG()) {

                                    if (friendRequestListResponse.getUSERDETAILS() != null && friendRequestListResponse.getUSERDETAILS().size() > 0) {
                                        hideNodataFound();
                                        requestStatusAdapter = new RequestStatusAdapter(FriendRequestStatusActivity.this, friendRequestListResponse.getUSERDETAILS());
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FriendRequestStatusActivity.this);
                                        rvRequestStatus.setLayoutManager(layoutManager);
                                        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen._10sdp);
                                        rvRequestStatus.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
                                        rvRequestStatus.setAdapter(requestStatusAdapter);
                                    } else {
                                        showNodataFound(getString(R.string.no_pending_request), "a_user.json");
                                    }


                                } else {
                                    showNodataFound(getString(R.string.no_pending_request), "a_user.json");

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError error) {
                            Toast.show(FriendRequestStatusActivity.this,getString(R.string.error_contact_server));
                            Logger.e(TAG, "Error----->" + error.getErrorBody());


                        }
                    });
        } else {
            showNodataFound(getString(R.string.internet_offline), "no_internet_connection.json");

        }
    }

    @OnClick(R.id.tvBack)
    public void onViewClicked() {
        finish();
    }
}
