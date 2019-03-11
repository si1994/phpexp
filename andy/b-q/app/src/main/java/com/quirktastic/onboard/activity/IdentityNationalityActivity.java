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
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.adapter.IdentityListAdapter;
import com.quirktastic.onboard.model.CommonResponse;
import com.quirktastic.onboard.model.identity.IdentitiyListResponse;
import com.quirktastic.onboard.model.identity.IdentityListItem;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class IdentityNationalityActivity extends AppCompatActivity {



    @BindView(R.id.tvIdentityTitleLabel)
    TextView tvIdentityTitleLabel;
    @BindView(R.id.tvIdentitySelectAllLabel)
    TextView tvIdentitySelectAllLabel;
    @BindView(R.id.tvSaveForLater)
    TextView tvSaveForLater;
    @BindView(R.id.llBackNext)
    LinearLayout llBackNext;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;
    @BindView(R.id.rcvInterestList)
    RecyclerView rcvInterestList;

    private ArrayList<IdentityListItem> identityList;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_nationality);
        ButterKnife.bind(this);

        rcvInterestList.setLayoutManager(new GridLayoutManager(IdentityNationalityActivity.this, 2));

        getIdentityAPI();

    }


    // call ws for get inentity

    private void getIdentityAPI() {
        HashMap<String, Object> map = new HashMap<>();
        new CallNetworkRequest().postResponse(IdentityNationalityActivity.this, true, "interestList", AUTH_VALUE, WSUrl.POST_IDENTITY_LIST, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            IdentitiyListResponse interestListResponse = gson.fromJson(response, IdentitiyListResponse.class);

                            if (interestListResponse.isFlag()) {
                                if (interestListResponse.getIdentityList() != null && interestListResponse.getIdentityList().size() > 0) {
                                    identityList = interestListResponse.getIdentityList();
                                    rcvInterestList.setAdapter(new IdentityListAdapter(IdentityNationalityActivity.this, identityList));
                                } else {

                                }

                            } else {
                                Toast.show(IdentityNationalityActivity.this, interestListResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                        Toast.show(IdentityNationalityActivity.this, getString(R.string.error_contact_server));

                    }
                });
    }



    // call ws for update nationality

    private void updateNationalityAPI(String nationalityId) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(IdentityNationalityActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.IDENTITY_ID, nationalityId);

        new CallNetworkRequest().postResponse(IdentityNationalityActivity.this, true, "update Nationality", AUTH_VALUE, WSUrl.POST_UPDATE_IDENTITY, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            CommonResponse commonResponse = gson.fromJson(response, CommonResponse.class);

                            if (commonResponse.isFlag()) {
                                //  Toast.show(IdentityNationalityActivity.this, commonResponse.getMessage());
                                redirectToSocialScreen();
                            } else {
                                Toast.show(IdentityNationalityActivity.this, commonResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                        Toast.show(IdentityNationalityActivity.this, getString(R.string.error_contact_server));

                    }
                });
    }

    private void checkAndCallUpdateNationalityAPI() {

        String nationalityId = getSelectedNationalityId();

        if (nationalityId.length() > 0) {
            updateNationalityAPI(nationalityId);
        } else {
            Toast.show(IdentityNationalityActivity.this, getString(R.string.please_select_your_natioanlity));
        }

    }

    private String getSelectedNationalityId() {

        String nationalityId = "";

        if (identityList != null && identityList.size() > 0) {
            for (int i = 0; i < identityList.size(); i++) {

                if (identityList.get(i).isSelected()) {
                    nationalityId = identityList.get(i).getId();
                    break;
                }
            }
        }

        return nationalityId;
    }

    @OnClick({R.id.llBackNext, R.id.llBottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llBackNext:
                redirectToSocialScreen();
                break;
            case R.id.llBottom:
                checkAndCallUpdateNationalityAPI();
//                redirectToSocialScreen();
                break;
        }
    }

    private void redirectToSocialScreen() {

        Intent intent = new Intent(IdentityNationalityActivity.this, SocialLifeActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }
}
