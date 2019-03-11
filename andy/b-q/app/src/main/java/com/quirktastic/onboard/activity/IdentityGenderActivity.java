package com.quirktastic.onboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.model.CommonResponse;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class IdentityGenderActivity extends AppCompatActivity {

    @BindView(R.id.tvIdentityGenderTitleLabel)
    TextView tvIdentityGenderTitleLabel;
    @BindView(R.id.tvIdentityGenderWoman)
    TextView tvIdentityGenderWoman;
    @BindView(R.id.tvIdentityGenderMan)
    TextView tvIdentityGenderMan;
    @BindView(R.id.tvIdentityGenderNonBinary)
    TextView tvIdentityGenderNonBinary;
    @BindView(R.id.tvSaveForLater)
    TextView tvSaveForLater;
    @BindView(R.id.llBackNext)
    LinearLayout llBackNext;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;

    private String genderValue = "";
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_gender);
        ButterKnife.bind(this);
    }


    // call ws for update gender
    private void updateGenderAPI(String gender) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(IdentityGenderActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.GENDER, gender);

        new CallNetworkRequest().postResponse(IdentityGenderActivity.this, true, "update GEnder ", AUTH_VALUE, WSUrl.POST_UPDATE_GENDER, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            CommonResponse commonResponse = gson.fromJson(response, CommonResponse.class);

                            if (commonResponse.isFlag()) {
                                // Toast.show(IdentityGenderActivity.this, commonResponse.getMessage());
                                redirectToIdentityNationalityScreen();
                            } else {
                                Toast.show(IdentityGenderActivity.this, commonResponse.getMessage());
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                        Toast.show(IdentityGenderActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }

    private void checkAndCallUpdateGenderAPI() {

        if (genderValue.length() > 0) {
            updateGenderAPI(genderValue);
        } else {
            Toast.show(IdentityGenderActivity.this, getString(R.string.please_select_your_gender));
        }
    }

    @OnClick({R.id.tvIdentityGenderWoman, R.id.tvIdentityGenderMan, R.id.tvIdentityGenderNonBinary,  R.id.llBackNext, R.id.llBottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvIdentityGenderWoman:

                genderValue = "2";

                setWomanSelected();

                break;
            case R.id.tvIdentityGenderMan:

                genderValue = "1";

                setManSelected();

                break;
            case R.id.tvIdentityGenderNonBinary:

                genderValue = "3";

                setNonBinarySelected();

                break;
            case R.id.llBackNext:
                redirectToIdentityNationalityScreen();
                break;
            case R.id.llBottom:
                checkAndCallUpdateGenderAPI();
//                redirectToIdentityNationalityScreen();
                break;
        }
    }

    private void setWomanSelected() {
        tvIdentityGenderWoman.setTextColor(ContextCompat.getColor(IdentityGenderActivity.this, R.color.colorWhite));
        tvIdentityGenderWoman.setBackgroundResource(R.drawable.rectangle_blue_fill);

        tvIdentityGenderMan.setTextColor(ContextCompat.getColor(IdentityGenderActivity.this, R.color.black));
        tvIdentityGenderMan.setBackgroundResource(R.drawable.rectangle_blue_line);

        tvIdentityGenderNonBinary.setTextColor(ContextCompat.getColor(IdentityGenderActivity.this, R.color.black));
        tvIdentityGenderNonBinary.setBackgroundResource(R.drawable.rectangle_blue_line);
    }

    private void setManSelected() {
        tvIdentityGenderWoman.setTextColor(ContextCompat.getColor(IdentityGenderActivity.this, R.color.black));
        tvIdentityGenderWoman.setBackgroundResource(R.drawable.rectangle_blue_line);

        tvIdentityGenderMan.setTextColor(ContextCompat.getColor(IdentityGenderActivity.this, R.color.colorWhite));
        tvIdentityGenderMan.setBackgroundResource(R.drawable.rectangle_blue_fill);

        tvIdentityGenderNonBinary.setTextColor(ContextCompat.getColor(IdentityGenderActivity.this, R.color.black));
        tvIdentityGenderNonBinary.setBackgroundResource(R.drawable.rectangle_blue_line);
    }

    private void setNonBinarySelected() {

        tvIdentityGenderWoman.setTextColor(ContextCompat.getColor(IdentityGenderActivity.this, R.color.black));
        tvIdentityGenderWoman.setBackgroundResource(R.drawable.rectangle_blue_line);

        tvIdentityGenderMan.setTextColor(ContextCompat.getColor(IdentityGenderActivity.this, R.color.black));
        tvIdentityGenderMan.setBackgroundResource(R.drawable.rectangle_blue_line);

        tvIdentityGenderNonBinary.setTextColor(ContextCompat.getColor(IdentityGenderActivity.this, R.color.colorWhite));
        tvIdentityGenderNonBinary.setBackgroundResource(R.drawable.rectangle_blue_fill);
    }

    private void redirectToIdentityNationalityScreen() {

        Intent intent = new Intent(IdentityGenderActivity.this, IdentityNationalityActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }
}
