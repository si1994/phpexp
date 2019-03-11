package com.quirktastic.onboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
import com.quirktastic.onboard.model.CommonResponse;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class PersonalityActivity extends AppCompatActivity {

    @BindView(R.id.tvPersonalityTitleLabel)
    TextView tvPersonalityTitleLabel;
    @BindView(R.id.edtPersonalityAnswer)
    EditText edtPersonalityAnswer;
    @BindView(R.id.tvSaveForLater)
    TextView tvSaveForLater;
    @BindView(R.id.llBackNext)
    LinearLayout llBackNext;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;
    @BindView(R.id.tvPersonalityAnswerCount)
    TextView tvPersonalityAnswerCount;

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality);
        ButterKnife.bind(this);


        edtPersonalityAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tvPersonalityAnswerCount.setText(charSequence.toString().length() + "/150");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    // call ws for update personality
    private void updatePersonalityAPI(String personality) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(PersonalityActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.PERSONALITY, personality);

        new CallNetworkRequest().postResponse(PersonalityActivity.this, true, "update Personality ", AUTH_VALUE, WSUrl.POST_UPDATE_PERSONALITY_TEXT, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            Logger.e(TAG, "FriendRequestStatusResponse-------->" + response);
                            Gson gson = new Gson();
                            CommonResponse commonResponse = gson.fromJson(response, CommonResponse.class);

                            if (commonResponse.isFlag()) {
                                //Toast.show(PersonalityActivity.this, commonResponse.getMessage());
                                redirectToIdentityGenderScreen();
                            } else {
                                Toast.show(PersonalityActivity.this, commonResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                        Toast.show(PersonalityActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }

    private void checkAndCallUpdatePersonalityAPI() {

        if (edtPersonalityAnswer.getText() != null && edtPersonalityAnswer.getText().toString().trim().length() > 0) {
            updatePersonalityAPI(Util.gifEncode(edtPersonalityAnswer.getText().toString().trim()));
        } else {
            Toast.show(PersonalityActivity.this, getString(R.string.please_enter_your_personality));
        }
    }

    @OnClick({ R.id.llBackNext, R.id.llBottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llBackNext:
                redirectToIdentityGenderScreen();
                break;
            case R.id.llBottom:
                checkAndCallUpdatePersonalityAPI();
//                redirectToIdentityGenderScreen();
                break;
        }
    }

    private void redirectToIdentityGenderScreen() {

        Intent intent = new Intent(PersonalityActivity.this, IdentityGenderActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }
}
