package com.quirktastic.onboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.chat.ChatProfileActivity;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.model.addphone.AddPhoneNumberLoginDetailsItem;
import com.quirktastic.onboard.model.addphone.AddPhoneNumberResponse;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;

import java.util.HashMap;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class AddPhoneNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = AddPhoneNumberActivity.class.getName();

    EditText edtPhoneNumberBoxOne, edtPhoneNumberBoxTwo, edtPhoneNumberBoxThree;
    LinearLayout llBottomBtnNextVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);

        viewBinding();
        init();
        addListner();
    }

    private void viewBinding() {
        edtPhoneNumberBoxOne = (EditText) findViewById(R.id.edtPhoneNumberBoxOne);
        edtPhoneNumberBoxTwo = (EditText) findViewById(R.id.edtPhoneNumberBoxTwo);
        edtPhoneNumberBoxThree = (EditText) findViewById(R.id.edtPhoneNumberBoxThree);
        llBottomBtnNextVerify = (LinearLayout) findViewById(R.id.llBottomBtnNextVerify);
    }

    private void init() {
    }


    private void addListner() {
        llBottomBtnNextVerify.setOnClickListener(this);

        edtPhoneNumberBoxOne.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtPhoneNumberBoxOne.getText().toString().length() == 3)     //size as per your requirement
                {
                    edtPhoneNumberBoxTwo.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        edtPhoneNumberBoxTwo.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtPhoneNumberBoxTwo.getText().toString().length() == 3) {
                    edtPhoneNumberBoxThree.requestFocus();
                } else if (edtPhoneNumberBoxTwo.getText().toString().length() == 0) {
                    edtPhoneNumberBoxOne.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        edtPhoneNumberBoxThree.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (edtPhoneNumberBoxThree.getText().toString().length() == 0) {
                    edtPhoneNumberBoxTwo.requestFocus();
                }
            }

        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llBottomBtnNextVerify:
                addPhoneNumberWs();
                break;
        }

    }

    // call ws for sending phone number to server
    private void addPhoneNumberWs() {
        String phonno = edtPhoneNumberBoxOne.getText().toString() + edtPhoneNumberBoxTwo.getText().toString() + edtPhoneNumberBoxThree.getText().toString();


        if (phonno.equals("")) {
            Toast.show(AddPhoneNumberActivity.this, "Please enter the phone number");
        } else if (phonno.length() != 10) {
            Toast.show(AddPhoneNumberActivity.this, "Please enter valid phone number");
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put(WSKey.USER_ID, Prefs.getString(AddPhoneNumberActivity.this, PrefsKey.USER_ID, ""));
            map.put(WSKey.PHONE_NUMBER, phonno);

            new CallNetworkRequest().postResponse(AddPhoneNumberActivity.this, true, "testyapi", AUTH_VALUE, WSUrl.POST_USER_SIGNUP_PHONE, map,
                    new INetworkResponse() {
                        @Override
                        public void onSuccess(String response) {

                            try {
                                AddPhoneNumberResponse addPhoneNumberResponse = new Gson().fromJson(response, AddPhoneNumberResponse.class);
                                if (addPhoneNumberResponse.isFLAG()) {

                                    if (addPhoneNumberResponse.getISPHONE() != null) {

                                        if (addPhoneNumberResponse.getISPHONE().equalsIgnoreCase("0")) {

                                            if (addPhoneNumberResponse.getUserDetails() != null) {
                                                Prefs.setString(AddPhoneNumberActivity.this, PrefsKey.ADD_PMN_OTP, addPhoneNumberResponse.getUserDetails().getToken());
                                                Prefs.setString(AddPhoneNumberActivity.this, PrefsKey.ADD_PNM_OTP_NUMBER, addPhoneNumberResponse.getUserDetails().getPhoneNumber());
                                                Prefs.setString(AddPhoneNumberActivity.this, PrefsKey.ADD_PNM_OTP_USER_ID, "" + String.valueOf(addPhoneNumberResponse.getUserDetails().getId()));

                                                Prefs.setString(AddPhoneNumberActivity.this, PrefsKey.USER_ID, "" + String.valueOf(addPhoneNumberResponse.getUserDetails().getId()));

                                                String formatedNumber = "+1" + " (" + edtPhoneNumberBoxOne.getText().toString() + ") "
                                                        + edtPhoneNumberBoxTwo.getText().toString() + "-" + edtPhoneNumberBoxThree.getText().toString();
                                                Prefs.setString(AddPhoneNumberActivity.this, PrefsKey.PHONE_NUMBER_FOR_DISPLAY, formatedNumber);
                                                loadVerifyPhoneNumberActivity();
                                            }

                                        } else if (addPhoneNumberResponse.getISPHONE().equalsIgnoreCase("1")) {

                                            AddPhoneNumberLoginDetailsItem detailsItem = addPhoneNumberResponse.getLOGINDETAILS().get(0);

                                            if (detailsItem.getFirstName().trim().length() == 0
                                                    || detailsItem.getLastName().trim().length() == 0
                                                    || detailsItem.getDateOfBirth().trim().length() == 0
                                                    || detailsItem.getEmailId().trim().length() == 0) {

                                                loadBasicInfoActivity();

                                            } else {
                                                loadVerifyPhoneNumberActivity();
                                            }

                                        }
                                    } else {
                                        if (addPhoneNumberResponse.getUserDetails() != null) {
                                            Prefs.setString(AddPhoneNumberActivity.this, PrefsKey.ADD_PMN_OTP, addPhoneNumberResponse.getUserDetails().getToken());
                                            Prefs.setString(AddPhoneNumberActivity.this, PrefsKey.ADD_PNM_OTP_NUMBER, addPhoneNumberResponse.getUserDetails().getPhoneNumber());
                                            Prefs.setString(AddPhoneNumberActivity.this, PrefsKey.ADD_PNM_OTP_USER_ID, "" + String.valueOf(addPhoneNumberResponse.getUserDetails().getId()));
                                            String formatedNumber = "+1" + " (" + edtPhoneNumberBoxOne.getText().toString() + ") "
                                                    + edtPhoneNumberBoxTwo.getText().toString() + "-" + edtPhoneNumberBoxThree.getText().toString();
                                            Prefs.setString(AddPhoneNumberActivity.this, PrefsKey.PHONE_NUMBER_FOR_DISPLAY, formatedNumber);
                                            loadVerifyPhoneNumberActivity();
                                        }
                                    }

                                } else {
                                    Toast.show(AddPhoneNumberActivity.this, addPhoneNumberResponse.getMESSAGE());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError error) {
                            Toast.show(AddPhoneNumberActivity.this, getString(R.string.error_contact_server));

                        }
                    });
        }
    }


    private void loadVerifyPhoneNumberActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                startActivity(new Intent(AddPhoneNumberActivity.this, VerifyPhoneNumberActivity.class));
            }
        }, 200);
    }


    private void loadBasicInfoActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                startActivity(new Intent(AddPhoneNumberActivity.this, BasicInfoActivity.class));
            }
        }, 200);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }


}
