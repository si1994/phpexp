package com.quirktastic.onboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.quirktastic.dashboard.model.modeluserlist.SocialLife;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.adapter.SocialLifeListAdapter;
import com.quirktastic.onboard.model.CommonResponse;
import com.quirktastic.onboard.model.sociallife.SocialLifeListItem;
import com.quirktastic.onboard.model.sociallife.SocialLifeResponse;
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

public class SocialLifeActivity extends AppCompatActivity {

    @BindView(R.id.tvSocialTitleLabel)
    TextView tvSocialTitleLabel;
    @BindView(R.id.tvSaveForLater)
    TextView tvSaveForLater;
    @BindView(R.id.llBackNext)
    LinearLayout llBackNext;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;

    @BindView(R.id.rcvSocialLifeList)
    RecyclerView rcvSocialLifeList;

    private String smokingValue = "";
    private String TAG = getClass().getSimpleName();
    private ArrayList<SocialLifeListItem> socialLifeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_life);
        ButterKnife.bind(this);


        rcvSocialLifeList.setLayoutManager(new GridLayoutManager(SocialLifeActivity.this, 1));

        getSocialLifeAPI();
    }

    @OnClick({ R.id.llBackNext, R.id.llBottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llBackNext:
                redirectEventsScreen();
                break;
            case R.id.llBottom:
                checkAndCallUpdateSocialLifeAPI();
//                redirectEventsScreen();
                break;
        }
    }

    // call ws for get social life list
    private void getSocialLifeAPI() {
        HashMap<String, Object> map = new HashMap<>();
        new CallNetworkRequest().postResponse(SocialLifeActivity.this, true, "socialLifeList", AUTH_VALUE, WSUrl.POST_SOCIAL_LIFE_LIST, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            SocialLifeResponse socialLifeResponse = gson.fromJson(response, SocialLifeResponse.class);

                            if (socialLifeResponse.isFlag()) {
                                if (socialLifeResponse.getSociallifeList() != null && socialLifeResponse.getSociallifeList().size() > 0) {
                                    socialLifeList = socialLifeResponse.getSociallifeList();
                                    rcvSocialLifeList.setAdapter(new SocialLifeListAdapter(SocialLifeActivity.this, socialLifeList));
                                } else {

                                }
                            } else {
                                Toast.show(SocialLifeActivity.this, socialLifeResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                        Toast.show(SocialLifeActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }

    private void updateSocialLifeAPI(String interestIds) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(SocialLifeActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.SOCIAL_LIFE_DATA, interestIds);

        new CallNetworkRequest().postResponse(SocialLifeActivity.this, true, "update Social Life", AUTH_VALUE, WSUrl.POST_UPDATE_SOCIAL_LIFE, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            CommonResponse commonResponse = gson.fromJson(response, CommonResponse.class);

                            if (commonResponse.isFlag()) {
                                //   Toast.show(SocialLifeActivity.this, commonResponse.getMessage());
                                redirectEventsScreen();
                            } else {
                                Toast.show(SocialLifeActivity.this, commonResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                        Toast.show(SocialLifeActivity.this, getString(R.string.error_contact_server));

                    }
                });
    }

    private void checkAndCallUpdateSocialLifeAPI() {

        JSONArray selectedIds = getSelectedSocialLifetList();

        if (selectedIds.length() > 0) {
            updateSocialLifeAPI(selectedIds.toString());
        } else {
            Toast.show(SocialLifeActivity.this, getString(R.string.please_select_your_social_life));
        }

    }

    private JSONArray getSelectedSocialLifetList() {

        JSONArray selectedIds = new JSONArray();

        if (socialLifeList != null && socialLifeList.size() > 0) {
            for (int i = 0; i < socialLifeList.size(); i++) {

                if (socialLifeList.get(i).isSelected()) {
                    selectedIds.put(socialLifeList.get(i).getId());
                }
            }
        }

        return selectedIds;
    }

    private void redirectEventsScreen() {

        Intent intent = new Intent(SocialLifeActivity.this, EventsActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }
}
