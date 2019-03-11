package com.quirktastic.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.chat.ChatProfileActivity;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.dashboard.profile.ProfileActivity;
import com.quirktastic.dashboard.profile.model.profiledetails.ProfileDetailsResponse;
import com.quirktastic.dashboard.profile.model.profiledetails.UserDetailsItem;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.activity.BasicInfoActivity;
import com.quirktastic.onboard.activity.VerifyPhoneNumberActivity;
import com.quirktastic.onboard.activity.WelcomeActivity;
import com.quirktastic.onboard.model.fblogin.FbLoginDetailsItem;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class SplashActivity extends AppCompatActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private UserDetailsItem detailsItem;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Hiding Title bar of this activity screen */
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        /** Making this activity, full screen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Prefs.getBoolean(SplashActivity.this, PrefsKey.IS_LOGIN, false)) {
                    // user is already login

//                    Intent mainIntent = new Intent(SplashActivity.this, DashboardActivity.class);
//                    startActivity(mainIntent);
//                    finish();

                    redirectToDashboard();

                } else {
                    // user is not logged in

                    Intent mainIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    private void redirectToDashboard() {

        String firstName = Prefs.getString(SplashActivity.this, PrefsKey.BASIC_INFO_FIRST_NAME, "");
        String lastName = Prefs.getString(SplashActivity.this, PrefsKey.BASIC_INFO_LAST_NAME, "");
        String dob = Prefs.getString(SplashActivity.this, PrefsKey.BASIC_INFO_DATE_OF_BIRTH, "");
        String email = Prefs.getString(SplashActivity.this, PrefsKey.BASIC_INFO_EMAIL, "");
        if (!TextUtils.isEmpty(firstName) || !TextUtils.isEmpty(lastName) || !TextUtils.isEmpty(dob) || !TextUtils.isEmpty(email)) {

            Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


    }



}
