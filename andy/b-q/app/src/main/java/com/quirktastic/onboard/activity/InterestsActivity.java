package com.quirktastic.onboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.chat.ChatProfileActivity;
import com.quirktastic.core.BaseActivity;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.adapter.InterestListAdapter;
import com.quirktastic.onboard.model.CommonResponse;
import com.quirktastic.onboard.model.InterestListItem;
import com.quirktastic.onboard.model.InterestListResponse;
import com.quirktastic.sendrequest.SendRequestActivity;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class InterestsActivity extends BaseActivity {

    @BindView(R.id.tvHeaderOnBoardTitle)
    TextView tvHeaderOnBoardTitle;
    @BindView(R.id.tvInterestTitleLabel)
    TextView tvInterestTitleLabel;
    @BindView(R.id.tvSaveForLater)
    TextView tvSaveForLater;
    @BindView(R.id.llBackNext)
    LinearLayout llBackNext;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;
    @BindView(R.id.rcvInterestList)
    RecyclerView rcvInterestList;

    private ArrayList<InterestListItem> interestList;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        ButterKnife.bind(this);

        rcvInterestList.setLayoutManager(new GridLayoutManager(InterestsActivity.this, 2));

        getInterestAPI();

    }

    // call ws for get interest
    private void getInterestAPI() {
        HashMap<String, Object> map = new HashMap<>();
        new CallNetworkRequest().postResponse(InterestsActivity.this, true, "interestList", AUTH_VALUE, WSUrl.POST_INTEREST_LIST, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            Gson gson = new Gson();
                            InterestListResponse interestListResponse = gson.fromJson(response, InterestListResponse.class);

                            if (interestListResponse.isFlag()) {
                                if (interestListResponse.getInterestList() != null && interestListResponse.getInterestList().size() > 0) {
                                    interestList = interestListResponse.getInterestList();
                                    rcvInterestList.setAdapter(new InterestListAdapter(InterestsActivity.this, interestList));
                                } else {

                                }

                            } else {
                                Toast.show(InterestsActivity.this, interestListResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                        Toast.show(InterestsActivity.this, getString(R.string.error_contact_server));

                    }
                });
    }


    // call ws for update interest
    private void updateInterestAPI(String interestIds) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(InterestsActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.INTEREST_DATA, interestIds);

        new CallNetworkRequest().postResponse(InterestsActivity.this, true, "update Interest", AUTH_VALUE, WSUrl.POST_UPDATE_INTEREST, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            CommonResponse commonResponse = gson.fromJson(response, CommonResponse.class);

                            if (commonResponse.isFlag()) {
                                //  Toast.show(InterestsActivity.this, commonResponse.getMessage());
                                redirectToInterestAnswerScreen();
                            } else {
                                Toast.show(InterestsActivity.this, commonResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                        Toast.show(InterestsActivity.this, getString(R.string.error_contact_server));

                    }
                });
    }

    private void checkAndCallUpdateInterestAPI() {

        JSONArray selectedIds = getSelectedInterestList();

        if (selectedIds.length() > 0) {
            updateInterestAPI(selectedIds.toString());
        } else {
            Toast.show(InterestsActivity.this, getString(R.string.please_select_interest));
        }

    }

    private JSONArray getSelectedInterestList() {

        JSONArray selectedIds = new JSONArray();

        if (interestList != null && interestList.size() > 0) {
            for (int i = 0; i < interestList.size(); i++) {

                if (interestList.get(i).isSelected()) {
                    selectedIds.put(interestList.get(i).getId());
                }
            }
        }


        return selectedIds;
    }

    @OnClick({ R.id.llBackNext, R.id.llBottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llBackNext:
                redirectToInterestAnswerScreen();
                break;
            case R.id.llBottom:
                checkAndCallUpdateInterestAPI();
//                redirectToInterestAnswerScreen();
                break;
        }
    }

    private void redirectToInterestAnswerScreen() {

        Intent intent = new Intent(InterestsActivity.this, InterestAnswerActivity.class);
        startActivity(intent);

    }

   /* @Override
    public void onBackPressed() {

        Intent intent = new Intent(InterestsActivity.this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }
}
