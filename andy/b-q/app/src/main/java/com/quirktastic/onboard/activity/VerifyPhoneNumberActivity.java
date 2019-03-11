package com.quirktastic.onboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.quirktastic.R;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.dashboard.profile.ProfileActivity;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.model.addphone.AddPhoneNumberResponse;
import com.quirktastic.onboard.model.fblogin.FbLoginDetailsItem;
import com.quirktastic.onboard.model.verifyphonenumber.VerifyPhoneNumberResponse;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class VerifyPhoneNumberActivity extends AppCompatActivity implements View.OnClickListener,
        OnOtpCompletionListener {

    LinearLayout llBottomBtnNextFillOutProfile;
    EditText edtOtpOne, edtOtpTwo, edtOtpThree, edtOtpFour, edtOtpFive;
    TextView tvDidnTReceiveOtp, tvPleaseEnter;
    String otp;
    @BindView(R.id.otp_view)
    OtpView otpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        ButterKnife.bind(this);

        viewBinding();
        init();
        addListner();

    }

    private void viewBinding() {
        llBottomBtnNextFillOutProfile = (LinearLayout) findViewById(R.id.llBottomBtnNextFillOutProfile);
        edtOtpOne = (EditText) findViewById(R.id.edtOtpOne);
        edtOtpTwo = (EditText) findViewById(R.id.edtOtpTwo);
        edtOtpThree = (EditText) findViewById(R.id.edtOtpThree);
        edtOtpFour = (EditText) findViewById(R.id.edtOtpFour);
        edtOtpFive = (EditText) findViewById(R.id.edtOtpFive);
        tvDidnTReceiveOtp = (TextView) findViewById(R.id.tvDidnTReceiveOtp);
        tvPleaseEnter = (TextView) findViewById(R.id.tvPleaseEnter);

        if (Prefs.getString(VerifyPhoneNumberActivity.this, PrefsKey.PHONE_NUMBER_FOR_DISPLAY, "").length() > 0) {
            tvPleaseEnter.setText(String.format(getString(R.string.please_enter_five_digit), Prefs.getString(VerifyPhoneNumberActivity.this, PrefsKey.PHONE_NUMBER_FOR_DISPLAY, "")));
        } else {
            tvPleaseEnter.setText(getString(R.string.please_ente));
        }
    }

    private void init() {
    }


    private void addListner() {
        llBottomBtnNextFillOutProfile.setOnClickListener(this);
        tvDidnTReceiveOtp.setOnClickListener(this);

        edtOtpOne.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtOtpOne.getText().toString().length() == 1) {
                    edtOtpTwo.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        edtOtpTwo.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtOtpTwo.getText().toString().length() == 1) {
                    edtOtpThree.requestFocus();
                } else {
                    edtOtpOne.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        edtOtpThree.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtOtpThree.getText().toString().length() == 1) {
                    edtOtpFour.requestFocus();
                } else {
                    edtOtpTwo.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        edtOtpFour.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtOtpFour.getText().toString().length() == 1) {
                    edtOtpFive.requestFocus();
                } else {
                    edtOtpThree.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        edtOtpFive.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (edtOtpFive.getText().toString().length() == 0) {
                    edtOtpFour.requestFocus();
                }
            }

        });

        edtOtpOne.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    edtOtpOne.setSelection(edtOtpOne.getText().toString().length());
                }
            }
        });

        edtOtpTwo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    edtOtpTwo.setSelection(edtOtpTwo.getText().toString().length());
                }
            }
        });

        edtOtpThree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    edtOtpThree.setSelection(edtOtpThree.getText().toString().length());
                }
            }
        });

        edtOtpFour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    edtOtpFour.setSelection(edtOtpFour.getText().toString().length());
                }
            }
        });

        edtOtpFive.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    edtOtpFive.setSelection(edtOtpFive.getText().toString().length());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llBottomBtnNextFillOutProfile:
                verifyPhoneNumberWs();
                break;

            case R.id.tvDidnTReceiveOtp:
                didNotGetOptWs();
                break;
        }
    }


    // call ws for verify phone number using opt
    private void verifyPhoneNumberWs() {
    otp=otpView.getText().toString();
        if (otp.equals("")) {
            Toast.show(VerifyPhoneNumberActivity.this, "Please enter the OTP");
        } else if (otp.length() != 5) {
            Toast.show(VerifyPhoneNumberActivity.this, "Please enter the valid OTP");
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put(WSKey.CODE, otp);
            map.put(WSKey.USER_ID, Prefs.getString(VerifyPhoneNumberActivity.this, PrefsKey.ADD_PNM_OTP_USER_ID, ""));
            map.put(WSKey.DEVICE_TYPE, "1");
            map.put(WSKey.DEVICE_TOKEN, FirebaseInstanceId.getInstance().getToken());

            new CallNetworkRequest().postResponse(VerifyPhoneNumberActivity.this, true, "verify Phone Number", AUTH_VALUE, WSUrl.POST_VERIFICATION_CODE, map,
                    new INetworkResponse() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                VerifyPhoneNumberResponse verifyPhoneNumberResponse = new Gson().fromJson(response, VerifyPhoneNumberResponse.class);
                                if (verifyPhoneNumberResponse.isFLAG()) {

                                    Prefs.setBoolean(VerifyPhoneNumberActivity.this, PrefsKey.IS_LOGIN, true);

                                    if (verifyPhoneNumberResponse.getIsRegistered().equalsIgnoreCase("1")) {

                                        FbLoginDetailsItem fbLoginDetailsItem = verifyPhoneNumberResponse.getLOGINDETAILS().get(0);

                                        Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.USER_ID, fbLoginDetailsItem.getId());


                                        if (fbLoginDetailsItem.getFirstName() != null && fbLoginDetailsItem.getFirstName().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.BASIC_INFO_FIRST_NAME, fbLoginDetailsItem.getFirstName());
                                        }

                                        if (fbLoginDetailsItem.getLastName() != null && fbLoginDetailsItem.getLastName().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.BASIC_INFO_LAST_NAME, fbLoginDetailsItem.getLastName());
                                        }

                                        if (fbLoginDetailsItem.getDateOfBirth() != null && fbLoginDetailsItem.getDateOfBirth().trim().length() > 0
                                                && !fbLoginDetailsItem.getDateOfBirth().trim().equalsIgnoreCase("0000-00-00")) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.BASIC_INFO_DATE_OF_BIRTH, fbLoginDetailsItem.getDateOfBirth());
                                        }

                                        if (fbLoginDetailsItem.getPhoneNumber() != null && fbLoginDetailsItem.getPhoneNumber().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.BASIC_INFO_PHONE_NUMBER, fbLoginDetailsItem.getPhoneNumber());
                                        }

                                        if (fbLoginDetailsItem.getEmailId() != null && fbLoginDetailsItem.getEmailId().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.BASIC_INFO_EMAIL, fbLoginDetailsItem.getEmailId());
                                        }

                                        if (fbLoginDetailsItem.getProfilePic() != null && fbLoginDetailsItem.getProfilePic().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.PROFILE_PIC, fbLoginDetailsItem.getProfilePic());
                                        }

                                        if (fbLoginDetailsItem.getAge() != null && fbLoginDetailsItem.getAge().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.BASIC_INFO_AGE, fbLoginDetailsItem.getAge());
                                        }

                                        if (fbLoginDetailsItem.getLatitude() != null && fbLoginDetailsItem.getLatitude().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.BASIC_LATITUDE, fbLoginDetailsItem.getLatitude());
                                        }

                                        if (fbLoginDetailsItem.getLongitude() != null && fbLoginDetailsItem.getLongitude().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.BASIC_LONGITUDE, fbLoginDetailsItem.getLongitude());
                                        }

                                        if (fbLoginDetailsItem.getCityName() != null && fbLoginDetailsItem.getCityName().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.CITY_NAME, fbLoginDetailsItem.getCityName());
                                        }
                                        if (fbLoginDetailsItem.getStateName() != null && fbLoginDetailsItem.getStateName().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.STATE_NAME, fbLoginDetailsItem.getStateName());
                                        }

                                        if (fbLoginDetailsItem.getStateShortName() != null && fbLoginDetailsItem.getStateShortName().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.STATE_SHORT_NAME, fbLoginDetailsItem.getStateShortName());
                                        }

                                        if (fbLoginDetailsItem.getGender() != null && fbLoginDetailsItem.getGender().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.BASIC_GENDER, fbLoginDetailsItem.getGender());
                                        }
                                        if (fbLoginDetailsItem.getIsVerifyPhoneNumber() != null && fbLoginDetailsItem.getIsVerifyPhoneNumber().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.BASIC_IS_VERIFY_PHONE_NUMBER, fbLoginDetailsItem.getIsVerifyPhoneNumber());
                                        }

                                        if (fbLoginDetailsItem.getAboutUs() != null && fbLoginDetailsItem.getAboutUs().trim().length() > 0) {
                                            Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.BASIC_ABOUT_US, fbLoginDetailsItem.getAboutUs());
                                        }


                                        if (fbLoginDetailsItem.getFirstName().trim().length() == 0
                                                || fbLoginDetailsItem.getLastName().trim().length() == 0
                                                || fbLoginDetailsItem.getDateOfBirth().trim().length() == 0
                                                || fbLoginDetailsItem.getEmailId().trim().length() == 0) {

                                            startActivity(new Intent(VerifyPhoneNumberActivity.this, BasicInfoActivity.class));
                                        } /*else if (fbLoginDetailsItem.getIsPhotoUploaded().equals("0")) {
                                            AppContants.IS_FROM_SPLASH = true;
                                            startActivity(new Intent(VerifyPhoneNumberActivity.this, ProfileActivity.class));
                                        } */else {
                                            Intent intent = new Intent(VerifyPhoneNumberActivity.this, DashboardActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }

                                    } else {
                                        startActivity(new Intent(VerifyPhoneNumberActivity.this, BasicInfoActivity.class));
                                    }

                                } else {
                                    Toast.show(VerifyPhoneNumberActivity.this, verifyPhoneNumberResponse.getMESSAGE());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError error) {
                            Toast.show(VerifyPhoneNumberActivity.this, getString(R.string.error_contact_server));
                        }
                    });
        }
    }


    private void didNotGetOptWs() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.PHONE_NUMBER, Prefs.getString(VerifyPhoneNumberActivity.this, PrefsKey.ADD_PNM_OTP_NUMBER, ""));

        new CallNetworkRequest().postResponse(VerifyPhoneNumberActivity.this, true, "user Signup Phone ", AUTH_VALUE, WSUrl.POST_USER_SIGNUP_PHONE, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            AddPhoneNumberResponse addPhoneNumberResponse = new Gson().fromJson(response, AddPhoneNumberResponse.class);
                            if (addPhoneNumberResponse.isFLAG()) {
                                if (addPhoneNumberResponse.getUserDetails() != null) {
                                    Toast.show(VerifyPhoneNumberActivity.this, addPhoneNumberResponse.getMESSAGE());
                                    Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.ADD_PMN_OTP, addPhoneNumberResponse.getUserDetails().getToken());
                                    Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.ADD_PNM_OTP_NUMBER, addPhoneNumberResponse.getUserDetails().getPhoneNumber());
                                    Prefs.setString(VerifyPhoneNumberActivity.this, PrefsKey.ADD_PNM_OTP_USER_ID, "" + String.valueOf(addPhoneNumberResponse.getUserDetails().getId()));
                                }
                            } else {
                                Toast.show(VerifyPhoneNumberActivity.this, addPhoneNumberResponse.getMESSAGE());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(VerifyPhoneNumberActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }


    @Override
    public void onOtpCompleted(String s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }
}