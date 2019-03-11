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

public class InterestAnswerActivity extends AppCompatActivity {

    @BindView(R.id.tvHeaderOnBoardTitle)
    TextView tvHeaderOnBoardTitle;
    @BindView(R.id.tvInterestTitleLabel)
    TextView tvInterestTitleLabel;
    @BindView(R.id.edtInterestAnswer)
    EditText edtInterestAnswer;
    @BindView(R.id.tvSaveForLater)
    TextView tvSaveForLater;
    @BindView(R.id.llBackNext)
    LinearLayout llBackNext;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;
    @BindView(R.id.tvInterestAnswerCount)
    TextView tvInterestAnswerCount;

    private String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_answer);
        ButterKnife.bind(this);


        edtInterestAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tvInterestAnswerCount.setText(charSequence.toString().length() + "/150");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    // call ws for update interest answer
    private void updateInterestAnswerAPI(String interestAnswer) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(InterestAnswerActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.INTERESTS, interestAnswer);

        new CallNetworkRequest().postResponse(InterestAnswerActivity.this, true, "update Interest answer ", AUTH_VALUE, WSUrl.POST_UPDATE_INTERESTS_TEXT, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            CommonResponse commonResponse = gson.fromJson(response, CommonResponse.class);

                            if (commonResponse.isFlag()) {
                                //   Toast.show(InterestAnswerActivity.this, commonResponse.getMessage());
                                redirectToPersonalityScreen();
                            } else {
                                Toast.show(InterestAnswerActivity.this, commonResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error----->" + error.getErrorBody());
                        Toast.show(InterestAnswerActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }


    @OnClick({R.id.llBackNext, R.id.llBottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llBackNext:
                redirectToPersonalityScreen();
                break;
            case R.id.llBottom:
                checkAndCallUpdateInterestAnswerAPI();
//                redirectToPersonalityScreen();
                break;
        }
    }

    private void checkAndCallUpdateInterestAnswerAPI() {

        if (edtInterestAnswer.getText() != null && edtInterestAnswer.getText().toString().trim().length() > 0) {
            updateInterestAnswerAPI(Util.gifEncode(edtInterestAnswer.getText().toString()));
        } else {
            Toast.show(InterestAnswerActivity.this, getString(R.string.please_enter_interest));
        }

    }

    private void redirectToPersonalityScreen() {
        Intent intent = new Intent(InterestAnswerActivity.this, PersonalityActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }
}
