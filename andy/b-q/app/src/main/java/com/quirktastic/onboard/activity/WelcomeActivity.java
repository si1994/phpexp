package com.quirktastic.onboard.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.facebook.login.LoginManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.facebooksignIn.FacebookHelper;
import com.quirktastic.onboard.facebooksignIn.FacebookResponse;
import com.quirktastic.onboard.facebooksignIn.FacebookUser;
import com.quirktastic.onboard.model.fblogin.FbLoginDetailsItem;
import com.quirktastic.onboard.model.fblogin.FbLoginResponse;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.webview.WebViewActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, FacebookResponse {

    @BindView(R.id.tvTerms)
    TextView tvTerms;
    @BindView(R.id.tvPrivacyPolicy)
    TextView tvPrivacyPolicy;
    @BindView(R.id.llLoginWithFb)
    LinearLayout llLoginWithFb;
    private String TAG = WelcomeActivity.class.getName();
    private FacebookHelper mFbHelper;
    private TextView tvOrUsePhone;
    String FbID = "", FbFName = "", FbLName = "", FbEmail = "", FbGender = "";
    Handler handler;
    Runnable run;
    int DELAY = 1500;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        viewBinding();
        initializeFacebook();
        init();
        addListner();
        hashKey();
    }

    private void viewBinding() {
        tvOrUsePhone = (TextView) findViewById(R.id.tvOrUsePhone);
    }

    private void init() {

        try {
            FirebaseInstanceId.getInstance().getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void addListner() {
        llLoginWithFb.setOnClickListener(this);
        tvOrUsePhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llLoginWithFb:
                facebookLogin();
                break;
            case R.id.tvOrUsePhone:
                Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_FIRST_NAME, "");
                Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_LAST_NAME, "");
                Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_DATE_OF_BIRTH, "");
                Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_EMAIL, "");
                startActivity(new Intent(WelcomeActivity.this, AddPhoneNumberActivity.class));
                break;
        }

    }

    private void initializeFacebook() {

        try {
            mFbHelper = new FacebookHelper(this, "id,name,email,birthday,picture", this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void facebookLogin() {
        LoginManager.getInstance().logOut();
        mFbHelper.performSignIn(this);
    }

    @Override
    public void onFbSignInFail() {

    }

    @Override
    public void onFbSignInSuccess() {

    }

    @Override
    public void onFbProfileReceived(FacebookUser facebookUser) {
        FbFName = facebookUser.first_name + "";
        FbLName = facebookUser.last_name + "";
        FbEmail = facebookUser.email + "";
        FbID = facebookUser.facebookID + "";
        fbLoginWs();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFbHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void hashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.quirktastic",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Logger.d("KeyHash", "KeyHash:" + Base64.encodeToString(md.digest(),
                        Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    // call ws for login with facebook
    private void fbLoginWs() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FACEBOOK_ID, FbID);
        map.put(WSKey.FIRST_NAME, FbFName);
        map.put(WSKey.LAST_NAME, FbLName);
        map.put(WSKey.EMAIL_ID, FbEmail);
        map.put(WSKey.DEVICE_TYPE, "1");
        map.put(WSKey.PHONE_NUMBER, "");
        map.put(WSKey.GENDER, "");
        map.put(WSKey.DATE_OF_BIRTH, "");
        map.put(WSKey.DEVICE_TOKEN, FirebaseInstanceId.getInstance().getToken());

        new CallNetworkRequest().postResponse(WelcomeActivity.this, true, "facebookLogin", AUTH_VALUE, WSUrl.POST_SOCIAL_LOGIN, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            FbLoginResponse fbLoginResponse = new Gson().fromJson(response, FbLoginResponse.class);
                            if (fbLoginResponse.isFLAG()) {

                                Toast.show(WelcomeActivity.this, fbLoginResponse.getMESSAGE());

                                FbLoginDetailsItem fbLoginDetailsItem = fbLoginResponse.getLOGINDETAILS().get(0);

                                Prefs.setString(WelcomeActivity.this, PrefsKey.USER_ID, fbLoginDetailsItem.getId());

                                if (fbLoginResponse.getISPHONE().equalsIgnoreCase("1")) {

                                    if (fbLoginDetailsItem.getFirstName() != null && fbLoginDetailsItem.getFirstName().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_FIRST_NAME, fbLoginDetailsItem.getFirstName());
                                    }

                                    if (fbLoginDetailsItem.getLastName() != null && fbLoginDetailsItem.getLastName().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_LAST_NAME, fbLoginDetailsItem.getLastName());
                                    }

                                    if (fbLoginDetailsItem.getDateOfBirth() != null && fbLoginDetailsItem.getDateOfBirth().trim().length() > 0
                                            && !fbLoginDetailsItem.getDateOfBirth().trim().equalsIgnoreCase("0000-00-00")) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_DATE_OF_BIRTH, fbLoginDetailsItem.getDateOfBirth());
                                    }

                                    if (fbLoginDetailsItem.getPhoneNumber() != null && fbLoginDetailsItem.getPhoneNumber().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_PHONE_NUMBER, fbLoginDetailsItem.getPhoneNumber());
                                    }

                                    if (fbLoginDetailsItem.getEmailId() != null && fbLoginDetailsItem.getEmailId().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_EMAIL, fbLoginDetailsItem.getEmailId());
                                    }

                                    if (fbLoginDetailsItem.getProfilePic() != null && fbLoginDetailsItem.getProfilePic().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.PROFILE_PIC, fbLoginDetailsItem.getProfilePic());
                                    }

                                    Prefs.setBoolean(WelcomeActivity.this, PrefsKey.IS_LOGIN, true);


                                    if (fbLoginDetailsItem.getFirstName().trim().length() == 0
                                            || fbLoginDetailsItem.getLastName().trim().length() == 0
                                            || fbLoginDetailsItem.getDateOfBirth().trim().length() == 0
                                            || fbLoginDetailsItem.getEmailId().trim().length() == 0) {

                                        loadBasicInfoActivity();
                                    }/* else if (fbLoginDetailsItem.getIsPhotoUploaded().equals("0")) {
                                        loadEditProfileActivity();
                                    }*/ else {
                                        loadDashBoardActivity();
                                    }
                                } else {

                                    if (fbLoginDetailsItem.getFirstName() != null && fbLoginDetailsItem.getFirstName().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_FIRST_NAME, fbLoginDetailsItem.getFirstName());
                                    }

                                    if (fbLoginDetailsItem.getLastName() != null && fbLoginDetailsItem.getLastName().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_LAST_NAME, fbLoginDetailsItem.getLastName());
                                    }

                                    if (fbLoginDetailsItem.getDateOfBirth() != null && fbLoginDetailsItem.getDateOfBirth().trim().length() > 0
                                            && !fbLoginDetailsItem.getDateOfBirth().trim().equalsIgnoreCase("0000-00-00")) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_DATE_OF_BIRTH, fbLoginDetailsItem.getDateOfBirth());
                                    }

                                    if (fbLoginDetailsItem.getPhoneNumber() != null && fbLoginDetailsItem.getPhoneNumber().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_PHONE_NUMBER, fbLoginDetailsItem.getPhoneNumber());
                                    }

                                    if (fbLoginDetailsItem.getEmailId() != null && fbLoginDetailsItem.getEmailId().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_EMAIL, fbLoginDetailsItem.getEmailId());
                                    }

                                    if (fbLoginDetailsItem.getProfilePic() != null && fbLoginDetailsItem.getProfilePic().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.PROFILE_PIC, fbLoginDetailsItem.getProfilePic());
                                    }

                                    if (fbLoginDetailsItem.getAge() != null && fbLoginDetailsItem.getAge().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_INFO_AGE, fbLoginDetailsItem.getAge());
                                    }

                                    if (fbLoginDetailsItem.getLatitude() != null && fbLoginDetailsItem.getLatitude().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_LATITUDE, fbLoginDetailsItem.getLatitude());
                                    }

                                    if (fbLoginDetailsItem.getLongitude() != null && fbLoginDetailsItem.getLongitude().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_LONGITUDE, fbLoginDetailsItem.getLongitude());
                                    }

                                    if (fbLoginDetailsItem.getCityName() != null && fbLoginDetailsItem.getCityName().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.CITY_NAME, fbLoginDetailsItem.getCityName());
                                    }
                                    if (fbLoginDetailsItem.getStateName() != null && fbLoginDetailsItem.getStateName().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.STATE_NAME, fbLoginDetailsItem.getStateName());
                                    }

                                    if (fbLoginDetailsItem.getStateShortName() != null && fbLoginDetailsItem.getStateShortName().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.STATE_SHORT_NAME, fbLoginDetailsItem.getStateShortName());
                                    }

                                    if (fbLoginDetailsItem.getGender() != null && fbLoginDetailsItem.getGender().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_GENDER, fbLoginDetailsItem.getGender());
                                    }
                                    if (fbLoginDetailsItem.getIsVerifyPhoneNumber() != null && fbLoginDetailsItem.getIsVerifyPhoneNumber().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_IS_VERIFY_PHONE_NUMBER, fbLoginDetailsItem.getIsVerifyPhoneNumber());
                                    }

                                    if (fbLoginDetailsItem.getAboutUs() != null && fbLoginDetailsItem.getAboutUs().trim().length() > 0) {
                                        Prefs.setString(WelcomeActivity.this, PrefsKey.BASIC_ABOUT_US, fbLoginDetailsItem.getAboutUs());
                                    }

                                    Intent intent = new Intent(WelcomeActivity.this, AddPhoneNumberActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                Toast.show(WelcomeActivity.this, fbLoginResponse.getMESSAGE());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(WelcomeActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }


    private void loadBasicInfoActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                startActivity(new Intent(WelcomeActivity.this, BasicInfoActivity.class));
            }
        }, 200);
    }


    /*private void loadEditProfileActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                *//* Create an Intent that will start the Menu-Activity. *//*
                AppContants.IS_FROM_SPLASH = true;
                startActivity(new Intent(WelcomeActivity.this, ProfileActivity.class));
                finish();
            }
        }, 200);
    }*/

    private void loadDashBoardActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                startActivity(new Intent(WelcomeActivity.this, DashboardActivity.class));
                finish();
            }
        }, 200);
    }

    @OnClick({R.id.tvTerms, R.id.tvPrivacyPolicy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvTerms:
                Intent intentTerms = new Intent(WelcomeActivity.this, WebViewActivity.class);
                intentTerms.putExtra("url", AppContants.TERMS);
                startActivity(intentTerms);
                break;
            case R.id.tvPrivacyPolicy:
                Intent intentPrivacyPolicy = new Intent(WelcomeActivity.this, WebViewActivity.class);
                intentPrivacyPolicy.putExtra("url", AppContants.PRIVACY_POLICY);
                startActivity(intentPrivacyPolicy);
                break;
        }
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.show(this, getString(R.string.exit));

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }

}
