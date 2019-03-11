package com.quirktastic.dashboard.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.chat.ChatProfileActivity;
import com.quirktastic.dashboard.profile.adapter.ProfileViewPagerAdapter;
import com.quirktastic.dashboard.profile.model.profiledetails.ProfileDetailsResponse;
import com.quirktastic.dashboard.profile.model.profiledetails.UserDetailsItem;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.view.CustomViewPager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.viewEditSelector)
    View viewEditSelector;
    @BindView(R.id.llEdit)
    LinearLayout llEdit;
    @BindView(R.id.viewViewSelector)
    View viewViewSelector;
    @BindView(R.id.llView)
    LinearLayout llView;
    @BindView(R.id.vpProfile)
    public CustomViewPager vpProfile;
    @BindView(R.id.tvEdit)
    TextView tvEdit;
    @BindView(R.id.tvView)
    TextView tvView;

    private ViewPager.OnPageChangeListener listener;
    private String TAG = getClass().getSimpleName();
    private ProfileViewPagerAdapter profileViewPagerAdapter;

    public static TextView tvDone;
    public static TextView tvUserName;
    private UserDetailsItem detailsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        tvDone = findViewById(R.id.tvDone);
        tvUserName = (TextView) findViewById(R.id.tvUserName);

        setUserName(Prefs.getString(ProfileActivity.this, PrefsKey.BASIC_INFO_FIRST_NAME, "")
                , Prefs.getString(ProfileActivity.this, PrefsKey.BASIC_INFO_LAST_NAME, ""));

        getProfileDetailsAPI();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    // call ws for get our profile details

    private void getProfileDetailsAPI() {

        final HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, Prefs.getString(ProfileActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, Prefs.getString(ProfileActivity.this, PrefsKey.USER_ID, ""));

        new CallNetworkRequest().postResponse(ProfileActivity.this, true, "update Eevent", AUTH_VALUE, WSUrl.GET_PROFILE_DETAILS,map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            Gson gson = new Gson();
                            ProfileDetailsResponse profileDetailsResponse = gson.fromJson(response, ProfileDetailsResponse.class);

                            if (profileDetailsResponse.isFlag()) {

                                if (profileDetailsResponse.getUserDetails() != null && profileDetailsResponse.getUserDetails().size() > 0) {
                                    detailsItem = profileDetailsResponse.getUserDetails().get(0);
                                    setUpviewPager(detailsItem);
                                }

                            } else {
                                Toast.show(ProfileActivity.this, profileDetailsResponse.getMessage());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error ===> " + error.getErrorBody());
                        Toast.show(ProfileActivity.this, getString(R.string.error_contact_server));

                    }
                });
    }

    public static void setUserName(String firstName, String lastName) {

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

        tvUserName.setText(userName);
    }


    @OnClick({R.id.tvCancel, R.id.llEdit, R.id.llView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:

                countImagesAndSaveProfilePicInPrefs();
                // Fragment fragment = profileViewPagerAdapter.getItem(0);

                /*if (EditProfileFragment.checkPhotos() != 4) {
                    Toast.show(ProfileActivity.this, "Please add at least 4 photos");
                } else if (!(AppContants.IS_UPLOAD_CALLED)) {
                    Toast.show(ProfileActivity.this, "Please upload photos");
                } else {*/
                    finish();
               // }
                break;
            case R.id.llEdit:
                viewEditSelector.setVisibility(View.VISIBLE);
                viewViewSelector.setVisibility(View.GONE);
                vpProfile.setCurrentItem(0);
                tvEdit.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.sf_pro_text_semibold));
                tvEdit.setTextColor(ContextCompat.getColor(ProfileActivity.this, R.color.colorPrimary));
                tvView.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.sf_pro_text_light));
                tvView.setTextColor(ContextCompat.getColor(ProfileActivity.this, R.color.black));
                break;
            case R.id.llView:
                viewEditSelector.setVisibility(View.GONE);
                viewViewSelector.setVisibility(View.VISIBLE);
                vpProfile.setCurrentItem(1);
                tvView.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.sf_pro_text_semibold));
                tvView.setTextColor(ContextCompat.getColor(ProfileActivity.this, R.color.colorPrimary));
                tvEdit.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.sf_pro_text_light));
                tvEdit.setTextColor(ContextCompat.getColor(ProfileActivity.this, R.color.black));
                break;
        }
    }

    private void countImagesAndSaveProfilePicInPrefs() {

        int count = 0;
        if (detailsItem != null && detailsItem.getPhotos() != null && detailsItem.getPhotos().size() > 0) {
            for (int i = 0; i < detailsItem.getPhotos().size(); i++) {
                if (detailsItem.getPhotos().get(i).getPhotos() != null
                        && detailsItem.getPhotos().get(i).getPhotos().length() > 0) {
                    count++;
                }
            }
        }


        if (count == 0) {
            Prefs.setString(ProfileActivity.this, PrefsKey.PROFILE_PIC, "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (vpProfile.getCurrentItem() == 0) {
            Fragment fragment = profileViewPagerAdapter.getItem(0);
            ((EditProfileFragment) fragment).onActivityResult(requestCode, resultCode, data);
        }
    }


    // set up view pager for edit and profile

    private void setUpviewPager(UserDetailsItem userDetailsItem) {

        profileViewPagerAdapter = new ProfileViewPagerAdapter(getSupportFragmentManager(), userDetailsItem);

        vpProfile.setAdapter(profileViewPagerAdapter);
        vpProfile.setCurrentItem(0);

        listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        viewEditSelector.setVisibility(View.VISIBLE);
                        viewViewSelector.setVisibility(View.GONE);
                        vpProfile.setCurrentItem(0);
                        tvDone.setVisibility(View.VISIBLE);
                        tvUserName.setVisibility(View.VISIBLE);

                        tvEdit.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.sf_pro_text_semibold));
                        tvEdit.setTextColor(ContextCompat.getColor(ProfileActivity.this, R.color.colorPrimary));
                        tvView.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.sf_pro_text_light));
                        tvView.setTextColor(ContextCompat.getColor(ProfileActivity.this, R.color.black));

                        break;

                    case 1:
                        viewEditSelector.setVisibility(View.GONE);
                        viewViewSelector.setVisibility(View.VISIBLE);
                        vpProfile.setCurrentItem(1);
                        tvDone.setVisibility(View.INVISIBLE);
                        tvUserName.setVisibility(View.VISIBLE);

                        tvView.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.sf_pro_text_semibold));
                        tvView.setTextColor(ContextCompat.getColor(ProfileActivity.this, R.color.colorPrimary));
                        tvEdit.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.sf_pro_text_light));
                        tvEdit.setTextColor(ContextCompat.getColor(ProfileActivity.this, R.color.black));

                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        vpProfile.addOnPageChangeListener(listener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
/*
        if (EditProfileFragment.checkPhotos() != 4) {
            Toast.show(ProfileActivity.this, "Please add at least 4 photos");
        } else if (!(AppContants.IS_UPLOAD_CALLED)) {
            Toast.show(ProfileActivity.this, "Please upload photos");
        } else {
            super.onBackPressed();
        }*/


        countImagesAndSaveProfilePicInPrefs();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }
}
