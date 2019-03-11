package com.quirktastic.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.github.pwittchen.swipe.library.rx2.SwipeListener;
import com.google.gson.Gson;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.quirktastic.R;
import com.quirktastic.core.BaseActivity;
import com.quirktastic.dashboard.fragment.EventsFragment;
import com.quirktastic.dashboard.fragment.FillOutProfileFragment;
import com.quirktastic.dashboard.fragment.OtherUserProfileFragment;
import com.quirktastic.dashboard.fragment.SocialFragment;
import com.quirktastic.dashboard.fragment.InboxFragment;
import com.quirktastic.dashboard.fragment.AccountFragment;
import com.quirktastic.dashboard.model.ProfileCompletionResponse;
import com.quirktastic.dashboard.profile.HelpCenterFragment;
import com.quirktastic.dashboard.profile.ProfileActivity;
import com.quirktastic.dashboard.profile.QrCodeFragment;
import com.quirktastic.dashboard.profile.model.profiledetails.ProfileDetailsResponse;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.splash.SplashActivity;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;

import java.util.HashMap;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class DashboardActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {
    private static final String TAG = DashboardActivity.class.getName();

    private BottomNavigationViewEx dashboardBottomNav;
    private boolean doubleBackToExitPressedOnce = false;
    public TextView tvBadgeCountInbox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();

        if (getIntent() != null && getIntent().hasExtra("is_from_noti")) {
            loadFragment(new InboxFragment());
            dashboardBottomNav.getMenu().getItem(0).setChecked(true);
        }
        else {
            loadFragment(new OtherUserProfileFragment());
            dashboardBottomNav.getMenu().getItem(2).setChecked(true);
            getProjectCompletionsWs(false);
        }


       /* swipe = new Swipe();
        swipe.setListener(new SwipeListener() {
            @Override
            public void onSwipingLeft(final MotionEvent event) {
            }

            @Override
            public boolean onSwipedLeft(final MotionEvent event) {
                Fragment fragment =
                        getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragment instanceof OtherUserProfileFragment) {

                    otherUserProfileFragment = (OtherUserProfileFragment) fragment;
                    otherUserProfileFragment.loadSendRequestActivity();
                }
                return false;
            }

            @Override
            public void onSwipingRight(final MotionEvent event) {
            }

            @Override
            public boolean onSwipedRight(final MotionEvent event) {
                Fragment fragment =
                        getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                if (fragment instanceof OtherUserProfileFragment) {

                    otherUserProfileFragment = (OtherUserProfileFragment) fragment;
                    otherUserProfileFragment.loadNextRequest();

                }

                return false;
            }

            @Override
            public void onSwipingUp(final MotionEvent event) {
            }

            @Override
            public boolean onSwipedUp(final MotionEvent event) {


                return false;
            }

            @Override
            public void onSwipingDown(final MotionEvent event) {
            }

            @Override
            public boolean onSwipedDown(final MotionEvent event) {
                return false;
            }
        });*/
    }

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return swipe.dispatchTouchEvent(event) || super.dispatchTouchEvent(event);
    }
*/

    @Override
    protected void onResume() {
        super.onResume();

        if (AppContants.IS_FROM_FILL_OUT) {
            loadFragment(new OtherUserProfileFragment());
            dashboardBottomNav.getMenu().getItem(2).setChecked(true);
            AppContants.IS_FROM_FILL_OUT=false;
        }

    }

    private void initView() {
        dashboardBottomNav = (BottomNavigationViewEx) findViewById(R.id.dashboardBottomNav);
        tvBadgeCountInbox = (TextView) findViewById(R.id.tvBadgeCountInbox);
        ImageView dashboardFabBtn = (ImageView) findViewById(R.id.dashboardFabBtn);

        dashboardBottomNav.enableItemShiftingMode(false);
        dashboardBottomNav.enableShiftingMode(false);
        dashboardBottomNav.enableAnimation(false);

        dashboardBottomNav.setOnNavigationItemSelectedListener(this);
        dashboardFabBtn.setOnClickListener(this);

        dashboardBottomNav.getMenu().getItem(2).setCheckable(false);

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commitAllowingStateLoss();
            return true;
        }
        return false;
    }

    public boolean replaceFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.dashboard_inbox:
                //menuItem.setIcon(R.drawable.icon_message_selected);
                AppContants.IS_FROM_FILL_OUT=false;
                fragment = new InboxFragment();
                break;
            case R.id.dashboard_account:
                //  menuItem.setIcon(R.drawable.icon_profile_selected);
                AppContants.IS_FROM_FILL_OUT=false;
                fragment = new AccountFragment();
                break;
            case R.id.dashboard_social:
                AppContants.IS_FROM_FILL_OUT=false;
                fragment = new SocialFragment();
                break;
            case R.id.dashboard_event:
                AppContants.IS_FROM_FILL_OUT=false;
                fragment = new EventsFragment();
                break;

        }
        return loadFragment(fragment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dashboardFabBtn:
             /*   dashboardBottomNav.getIconAt(0).setImageResource(R.drawable.icon_message_normal);
                dashboardBottomNav.getIconAt(3).setImageResource(R.drawable.icon_profile_normal);*/
                AppContants.IS_FROM_FILL_OUT=false;
                loadFragment(new OtherUserProfileFragment());
                dashboardBottomNav.getMenu().getItem(2).setChecked(true);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null && (fragment instanceof OtherUserProfileFragment || fragment instanceof InboxFragment || fragment instanceof QrCodeFragment)) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null && (fragment instanceof HelpCenterFragment
                || fragment instanceof QrCodeFragment)) {
            super.onBackPressed();
        } else {
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
    }


    private void getProjectCompletionsWs(boolean showProcess) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(DashboardActivity.this, PrefsKey.USER_ID, ""));

        new CallNetworkRequest().postResponse(DashboardActivity.this, showProcess, "getProjectCompletionsWs", AUTH_VALUE, WSUrl.POST_CHECK_PROFILE_COMPLETION, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            ProfileCompletionResponse profileCompletionResponse = new Gson().fromJson(response, ProfileCompletionResponse.class);
                            if (profileCompletionResponse.isFLAG()) {
                                if (profileCompletionResponse.getPROFILECOMPLETION() < 100) {
                                    AppContants.IS_FROM_FILL_OUT=true;
                                    loadFragment(new FillOutProfileFragment());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });
    }


    /*private void getProfileDetailsAPI() {

        String url = WSUrl.GET_PROFILE_DETAILS + Prefs.getString(DashboardActivity.this, PrefsKey.USER_ID, "");

        new CallNetworkRequest().getResponse(DashboardActivity.this, false, "update Eevent", AUTH_VALUE, url,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            ProfileDetailsResponse profileDetailsResponse = gson.fromJson(response, ProfileDetailsResponse.class);

                            if (profileDetailsResponse.isFlag()) {

                                if (profileDetailsResponse.getUserDetails().get(0).getIsPhotoUploaded().equals("0")) {
                                    loadFragment(new FillOutProfileFragment());
                                }
                               *//* else {
                                    loadFragment(new InboxFragment());
                                }*//*

                            } else {
                                Toast.show(DashboardActivity.this, profileDetailsResponse.getMessage());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(DashboardActivity.this, getString(R.string.error_contact_server));
                        Logger.e(TAG, "Error ===> " + error.getErrorBody());
                    }
                });
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }

}
