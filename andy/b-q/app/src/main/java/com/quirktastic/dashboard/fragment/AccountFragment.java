package com.quirktastic.dashboard.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.core.BaseFragment;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.dashboard.profile.HelpCenterFragment;
import com.quirktastic.dashboard.profile.PreferencesActivity;
import com.quirktastic.dashboard.profile.ProfileActivity;
import com.quirktastic.dashboard.profile.QrCodeFragment;
import com.quirktastic.dashboard.model.ProfileCompletionResponse;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.Utility;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.activity.WelcomeActivity;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class AccountFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = AccountFragment.class.getName();

    private View rootView;
    private RelativeLayout rlHelpCenter, rlAccountPref;
    private TextView tvMyQrCode, tvAccountUserName, tvLogout, tvAccountProfileComplete, tvEditProfile;
    //private ImageView ivEditProfilePic;
    private CircleImageView ivProfilePic;
    private DashboardActivity dashboardActivity;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardActivity = (DashboardActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();

        setUserName(Prefs.getString(dashboardActivity, PrefsKey.BASIC_INFO_FIRST_NAME, "")
                , Prefs.getString(dashboardActivity, PrefsKey.BASIC_INFO_LAST_NAME, ""));

        Glide.with(dashboardActivity)
                .load(Prefs.getString(dashboardActivity, PrefsKey.PROFILE_PIC, ""))
                .apply(RequestOptions
                        .placeholderOf(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(400, 400))
                .into(ivProfilePic);

        setUserName(Prefs.getString(dashboardActivity, PrefsKey.BASIC_INFO_FIRST_NAME, "")
                , Prefs.getString(dashboardActivity, PrefsKey.BASIC_INFO_LAST_NAME, ""));

        tvAccountProfileComplete.setText(Prefs.getString(dashboardActivity, PrefsKey.PROFILE_COMPLETION, "0% complete"));

        if (Prefs.getString(dashboardActivity, PrefsKey.PROFILE_COMPLETION_IS_FIRST_TIME, "1").equals("1")) {
            getProjectCompletionsWs(true);
        } else {
            getProjectCompletionsWs(false);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account, container, false);
        // Inflate the layout for this fragment
        viewBinding();
        addListner();

        return rootView;
    }


    private void viewBinding() {
        rlHelpCenter = (RelativeLayout) rootView.findViewById(R.id.rlHelpCenter);
        rlAccountPref = (RelativeLayout) rootView.findViewById(R.id.rlAccountPref);
        tvMyQrCode = (TextView) rootView.findViewById(R.id.tvMyQrCode);
        tvAccountProfileComplete = (TextView) rootView.findViewById(R.id.tvAccountProfileComplete);
        tvAccountUserName = (TextView) rootView.findViewById(R.id.tvAccountUserName);
        tvEditProfile = (TextView) rootView.findViewById(R.id.tvEditProfile);
        tvLogout = (TextView) rootView.findViewById(R.id.tvLogout);
        // ivEditProfilePic = rootView.findViewById(R.id.ivEditProfilePic);
        ivProfilePic = (CircleImageView) rootView.findViewById(R.id.ivProfilePic);

    }

    private void addListner() {
        rlHelpCenter.setOnClickListener(this);
        rlAccountPref.setOnClickListener(this);
        tvMyQrCode.setOnClickListener(this);
        tvEditProfile.setOnClickListener(this);
        //ivEditProfilePic.setOnClickListener(this);
        ivProfilePic.setOnClickListener(this);
        tvLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlHelpCenter:
                dashboardActivity.replaceFragment(new HelpCenterFragment());
                break;
            case R.id.rlAccountPref:
                startActivity(new Intent(dashboardActivity, PreferencesActivity.class));
                break;
            case R.id.tvMyQrCode:
                dashboardActivity.replaceFragment(new QrCodeFragment());
                break;
            case R.id.ivProfilePic:
                //AppContants.IS_FROM_SPLASH=false;
                dashboardActivity.startActivity(new Intent(getActivity(), ProfileActivity.class));
                break;
            case R.id.tvLogout:
                LogOutDialog();
                break;
            case R.id.tvEditProfile:
                dashboardActivity.startActivity(new Intent(getActivity(), ProfileActivity.class));
                break;
        }
    }

    private void setUserName(String firstName, String lastName) {

        String userName = "";

        if (firstName != null && firstName.length() > 0) {
            userName = firstName;
        }

        if (lastName != null && lastName.length() > 0) {
            if (userName.length() > 0) {
                userName += " " + lastName;
            } else {
                userName = lastName;
            }
        }

        tvAccountUserName.setText(userName);
    }


    // Show dialog for LogOut from Application..
    private void LogOutDialog() {

        final Dialog dialog = new Dialog(dashboardActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_popup_log_out_layout);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        final TextView btnNo = (TextView) dialog.findViewById(R.id.btnNo);
        final TextView btnYes = (TextView) dialog.findViewById(R.id.btnYes);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isInternetOn(dashboardActivity)) {
                    dialog.dismiss();
                    logOutWs();
                } else {
                    dialog.dismiss();
                    Prefs.clearPreferences(dashboardActivity);
                    Intent intent = new Intent(dashboardActivity, WelcomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    dashboardActivity.startActivity(intent);
                    dashboardActivity.finish();
                }

            }
        });
    }

    // calling ws for gettting percentage of profile completions
    private void getProjectCompletionsWs(boolean showProcess) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(dashboardActivity, PrefsKey.USER_ID, ""));

        new CallNetworkRequest().postResponse(dashboardActivity, showProcess, "getProjectCompletionsWs", AUTH_VALUE, WSUrl.POST_CHECK_PROFILE_COMPLETION, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            ProfileCompletionResponse profileCompletionResponse = new Gson().fromJson(response, ProfileCompletionResponse.class);
                            if (profileCompletionResponse.isFLAG()) {

                                String per = String.valueOf(Math.round(profileCompletionResponse.getPROFILECOMPLETION())) + "% Complete";

                                Prefs.setString(dashboardActivity, PrefsKey.PROFILE_COMPLETION_IS_FIRST_TIME, "0");
                                Prefs.setString(dashboardActivity, PrefsKey.PROFILE_COMPLETION, per);
                                tvAccountProfileComplete.setText(per);

                            } else {
                                tvAccountProfileComplete.setText("0% Complete");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        tvAccountProfileComplete.setText("0% Complete");
                        Toast.show(dashboardActivity, getString(R.string.error_contact_server));
                    }
                });
    }

    //calling api for log out
    private void logOutWs() {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(dashboardActivity, PrefsKey.USER_ID, ""));
        map.put(WSKey.DEVICE_TOKEN, FirebaseInstanceId.getInstance().getToken());
        map.put(WSKey.DEVICE_TYPE, "1");

        new CallNetworkRequest().postResponse(dashboardActivity, true, "logOutWs", AUTH_VALUE, WSUrl.POST_LOGOUT, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            Prefs.clearPreferences(dashboardActivity);
                            Intent intent = new Intent(dashboardActivity, WelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            dashboardActivity.startActivity(intent);
                            dashboardActivity.finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        tvAccountProfileComplete.setText("0% Complete");
                        Toast.show(dashboardActivity, getString(R.string.error_contact_server));
                        Prefs.clearPreferences(dashboardActivity);
                        Intent intent = new Intent(dashboardActivity, WelcomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        dashboardActivity.startActivity(intent);
                        dashboardActivity.finish();
                    }
                });
    }

}
