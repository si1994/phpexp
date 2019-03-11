package com.quirktastic.dashboard.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.core.BaseActivity;
import com.quirktastic.dashboard.profile.adapter.PrefEthnicCheckAdapter;
import com.quirktastic.dashboard.profile.adapter.PrefGenderCheckAdapter;
import com.quirktastic.dashboard.profile.adapter.PrefSocialCheckAdapter;
import com.quirktastic.dashboard.profile.customView.RangeSeekBar;
import com.quirktastic.dashboard.profile.model.CheckBoxModel;
import com.quirktastic.dashboard.profile.model.preferences.EthnicityItem;
import com.quirktastic.dashboard.profile.model.preferences.GenderItem;
import com.quirktastic.dashboard.profile.model.preferences.InterestsItem;
import com.quirktastic.dashboard.profile.model.preferences.SocialLifeItem;
import com.quirktastic.dashboard.profile.model.preferences.USERPREFERENCESItem;
import com.quirktastic.dashboard.profile.model.preferences.UserPreferencesResponce;
import com.quirktastic.dashboard.profile.model.preferences.UserSetPreferenceResponse;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.Utility;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class PreferencesActivity extends BaseActivity {

    @BindView(R.id.rvGender)
    RecyclerView rvGender;
    @BindView(R.id.tvDone)
    TextView tvDone;
    @BindView(R.id.rangeAgeSeekBar)
    RangeSeekBar rangeAgeSeekBar;
    @BindView(R.id.rvSocialLife)
    RecyclerView rvSocialLife;
    @BindView(R.id.rvEthinicity)
    RecyclerView rvEthinicity;
    @BindView(R.id.rvFriendInterests)
    RecyclerView rvFriendInterests;
    @BindView(R.id.cbEvents)
    CheckBox cbEvents;
    @BindView(R.id.cbMobile)
    CheckBox cbMobile;
    @BindView(R.id.cbEmail)
    CheckBox cbEmail;
    @BindView(R.id.sbFriendLocation)
    SeekBar sbFriendLocation;
    @BindView(R.id.tvRangeAgeSeekBarMin)
    TextView tvRangeAgeSeekBarMin;
    @BindView(R.id.tvRangeAgeSeekBarMax)
    TextView tvRangeAgeSeekBarMax;
    @BindView(R.id.tvSbFriendLocationProcess)
    TextView tvSbFriendLocationProcess;

    ArrayList<CheckBoxModel> genderList;
    ArrayList<CheckBoxModel> ethincityList;
    ArrayList<CheckBoxModel> socialLifeList;
    ArrayList<CheckBoxModel> interestList;
    @BindView(R.id.tvMobileNumberPreference)
    TextView tvMobileNumberPreference;
    @BindView(R.id.tvEmailPreference)
    TextView tvEmailPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);


        // Inflate the layout for this fragment
        ButterKnife.bind(this);
        initUI();
    }


    private void initUI() {

        tvEmailPreference.setText(Prefs.getString(PreferencesActivity.this, PrefsKey.BASIC_INFO_EMAIL, ""));

        String phNumber = Prefs.getString(PreferencesActivity.this, PrefsKey.BASIC_INFO_PHONE_NUMBER, "");
        String formatedNumber = "+1 (" + phNumber.substring(0, 3) + ") " + phNumber.substring(3, 6) + "-" + phNumber.substring(6, 10);

        tvMobileNumberPreference.setText(formatedNumber);
        sbFriendLocation.setMax(1000);
        getPreferencesWs();

        rangeAgeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {

                int agemin = ((int) Math.round(minValue.intValue()));
                int agemax = ((int) Math.round(maxValue.intValue()));

                tvRangeAgeSeekBarMin.setText(String.valueOf(agemin));
                tvRangeAgeSeekBarMax.setText(String.valueOf(agemax));

                if ((int) Math.round(maxValue.intValue()) >= 65) {
                    tvRangeAgeSeekBarMax.setText("65+");
                }

            }

        });

        sbFriendLocation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 1000) {
                    tvSbFriendLocationProcess.setText("Worldwide");
                } else {
                    tvSbFriendLocationProcess.setText(String.valueOf(progress) + " mi");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    @OnClick(R.id.tvDone)
    public void onViewClicked() {
        setPreferences();
    }

// call ws for getting user preferences
    private void getPreferencesWs() {

        new CallNetworkRequest().getResponse(PreferencesActivity.this, true, "testyapi", AUTH_VALUE, WSUrl.GET_PREFERENCES + Prefs.getString(PreferencesActivity.this, PrefsKey.USER_ID, "")
                , new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {

                            UserPreferencesResponce userPreferencesResponce = new Gson().fromJson(response, UserPreferencesResponce.class);
                            if (userPreferencesResponce.isFLAG()) {

                                    USERPREFERENCESItem userPreferencesItem = userPreferencesResponce.getUSERPREFERENCES().get(0);

                                    genderList = new ArrayList<>();
                                    ethincityList = new ArrayList<>();
                                    socialLifeList = new ArrayList<>();
                                    interestList = new ArrayList<>();

                                    for (GenderItem userPreferencesGenderItem : userPreferencesItem.getGender()) {


                                        CheckBoxModel checkBoxModel = new CheckBoxModel();
                                        checkBoxModel.setName(userPreferencesGenderItem.getGender());
                                        checkBoxModel.setSelected(userPreferencesGenderItem.getIsSelected() > 0 ? true : false);
                                        checkBoxModel.setId(Integer.valueOf(userPreferencesGenderItem.getId()));
                                        genderList.add(checkBoxModel);
                                    }
                                    rvGender.setLayoutManager(new GridLayoutManager(PreferencesActivity.this, 3));
                                    rvGender.setAdapter(new PrefGenderCheckAdapter(PreferencesActivity.this, genderList));


                                    for (EthnicityItem userPreferencesEthnicityItem : userPreferencesItem.getEthnicity()) {

                                        CheckBoxModel checkBoxModel = new CheckBoxModel();
                                        checkBoxModel.setName(userPreferencesEthnicityItem.getIdentityName());
                                        checkBoxModel.setSelected(userPreferencesEthnicityItem.getIsSelected() > 0 ? true : false);
                                        checkBoxModel.setId(Integer.valueOf(userPreferencesEthnicityItem.getId()));
                                        ethincityList.add(checkBoxModel);
                                    }

                                    rvEthinicity.setLayoutManager(new GridLayoutManager(PreferencesActivity.this, 3));
                                    rvEthinicity.setAdapter(new PrefEthnicCheckAdapter(PreferencesActivity.this, ethincityList));


                                    for (InterestsItem interestsItem : userPreferencesItem.getInterests()) {

                                        CheckBoxModel checkBoxModel = new CheckBoxModel();
                                        checkBoxModel.setName(interestsItem.getInterestName());
                                        checkBoxModel.setSelected(interestsItem.getIsSelected() > 0 ? true : false);
                                        checkBoxModel.setId(Integer.valueOf(interestsItem.getId()));
                                        interestList.add(checkBoxModel);
                                    }

                                    rvFriendInterests.setLayoutManager(new GridLayoutManager(PreferencesActivity.this, 3));
                                    rvFriendInterests.setAdapter(new PrefEthnicCheckAdapter(PreferencesActivity.this, interestList));


                                    for (SocialLifeItem userPreferencesSocialLifeItem : userPreferencesItem.getSocialLife()) {

                                        CheckBoxModel checkBoxModel = new CheckBoxModel();
                                        checkBoxModel.setName(userPreferencesSocialLifeItem.getName());
                                        checkBoxModel.setSelected(userPreferencesSocialLifeItem.getIsSelected() > 0 ? true : false);
                                        checkBoxModel.setId(Integer.valueOf(userPreferencesSocialLifeItem.getId()));
                                        socialLifeList.add(checkBoxModel);
                                    }

                                    rvSocialLife.setLayoutManager(new GridLayoutManager(PreferencesActivity.this, 3));
                                    rvSocialLife.setAdapter(new PrefSocialCheckAdapter(PreferencesActivity.this, socialLifeList));

                                    if (userPreferencesItem.getAttendingMyEvent().equals("")) {
                                        cbEvents.setChecked(false);
                                    } else {
                                        cbEvents.setChecked(Integer.valueOf(userPreferencesItem.getAttendingMyEvent()) > 0 ? true : false);
                                    }


                                    if (userPreferencesItem.getIsEmailNotification().equals("")) {
                                        cbEmail.setChecked(false);
                                    } else {
                                        cbEmail.setChecked(Integer.valueOf(userPreferencesItem.getIsEmailNotification()) > 0 ? true : false);
                                    }

                                    if (userPreferencesItem.getIsMobileNotification().equals("")) {
                                        cbMobile.setChecked(false);
                                    } else {
                                        cbMobile.setChecked(Integer.valueOf(userPreferencesItem.getIsMobileNotification()) > 0 ? true : false);
                                    }

                                    if (userPreferencesItem.getFriendsAge().equals("")) {
                                        rangeAgeSeekBar.setSelectedMinValue(18);
                                        rangeAgeSeekBar.setSelectedMaxValue(60);
                                        tvRangeAgeSeekBarMin.setText("18");
                                        tvRangeAgeSeekBarMax.setText("60");
                                    } else {
                                        String s = userPreferencesItem.getFriendsAge();
                                        String[] parts = s.split("-"); //returns an array with the 2 parts
                                        int firstPart = Integer.parseInt(parts[0]); //14.015
                                        int secondPart = Integer.parseInt(parts[1]);


                                        rangeAgeSeekBar.setSelectedMinValue(firstPart);
                                        rangeAgeSeekBar.setSelectedMaxValue(secondPart);

                                        tvRangeAgeSeekBarMin.setText(String.valueOf(firstPart));

                                        if (secondPart == 65) {
                                            tvRangeAgeSeekBarMax.setText(String.valueOf(secondPart) + "+");
                                        } else {
                                            tvRangeAgeSeekBarMax.setText(String.valueOf(secondPart));
                                        }
                                    }

                                    int location;
                                    if (userPreferencesItem.getFriendsLocation().equals("")) {
                                        location = 1000;
                                        sbFriendLocation.setProgress(location);
                                        tvSbFriendLocationProcess.setText("Worldwide");
                                    } else {


                                        if (userPreferencesItem.getFriendsLocation().equals("w")) {
                                            location = 1000;
                                        } else {
                                            location = Integer.valueOf(userPreferencesItem.getFriendsLocation());
                                        }


                                        sbFriendLocation.setProgress(location);
                                        if (location == 1000) {
                                            tvSbFriendLocationProcess.setText("Worldwide");
                                        } else {
                                            tvSbFriendLocationProcess.setText(String.valueOf(location) + " mi");
                                        }
                                    }

                            } else {
                                Toast.show(PreferencesActivity.this, userPreferencesResponce.getMESSAGE());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(PreferencesActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }


    // calling ws for update preferences

    private void setPreferencesWs(String friends_gender, String friends_ethnicity,
                                  String friends_interest,
                                  String friends_age,
                                  String friends_social_life,
                                  String friends_location,
                                  String friends_attending_event,String is_email_notification,String is_mobile_notification) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(PreferencesActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.FRIENDS_GENDER, friends_gender);
        map.put(WSKey.FRIENDS_ETHNICITY, friends_ethnicity);
        map.put(WSKey.FRIENDS_INTEREST, friends_interest);
        map.put(WSKey.FRIENDS_AGE, friends_age);
        map.put(WSKey.FRIENDS_SOCIAL_LIFE, friends_social_life);
        map.put(WSKey.FRIENDS_LOCATION, friends_location);
        map.put(WSKey.FRIENDS_ATTENDING_EVENT, friends_attending_event);
        map.put(WSKey.IS_EMAIL_NOTIFICATION, is_email_notification);
        map.put(WSKey.IS_MOBILE_NOTIFICATION, is_mobile_notification);
        map.put(WSKey.IS_PUSH_NOTIFICATION,"1");
        map.put(WSKey.DEVICE_TYPE, "1");// 1 for android
        map.put(WSKey.DEVICE_TOKEN,  FirebaseInstanceId.getInstance().getToken());

        new CallNetworkRequest().postResponse(PreferencesActivity.this, true, "setPreferencesWs", AUTH_VALUE, WSUrl.POST_SET_PREFERENCES, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            UserSetPreferenceResponse userSetPreferenceResponse = new Gson().fromJson(response, UserSetPreferenceResponse.class);
                            if (userSetPreferenceResponse.isFLAG()) {

                                if (userSetPreferenceResponse.isISACTIVE()) {
                                    //Toast.show(PreferencesActivity.this, "Preferences set successfully");
                                } else {
                                    Toast.show(PreferencesActivity.this, userSetPreferenceResponse.getMESSAGE());
                                }

                            } else {
                                Toast.show(PreferencesActivity.this, userSetPreferenceResponse.getMESSAGE());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        finish();

                    }

                    @Override
                    public void onError(ANError error) {
                        finish();
                        Toast.show(PreferencesActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }


    private void setPreferences() {

        if(genderList!=null) {

            if (Utility.isInternetOn(PreferencesActivity.this)) {

                List<String> friends_gender = new ArrayList<>();
                List<String> friends_ethnicity = new ArrayList<>();
                List<String> friends_social_life = new ArrayList<>();
                List<String> friends_interest = new ArrayList<>();

                for (CheckBoxModel checkBoxModel : genderList) {
                    if (checkBoxModel.isSelected()) {
                        friends_gender.add(String.valueOf(checkBoxModel.getId()));
                    }
                }

                for (CheckBoxModel checkBoxModel : ethincityList) {
                    if (checkBoxModel.isSelected()) {
                        friends_ethnicity.add(String.valueOf(checkBoxModel.getId()));
                    }
                }

                for (CheckBoxModel checkBoxModel : interestList) {
                    if (checkBoxModel.isSelected()) {
                        friends_interest.add(String.valueOf(checkBoxModel.getId()));
                    }
                }

                for (CheckBoxModel checkBoxModel : socialLifeList) {
                    if (checkBoxModel.isSelected()) {
                        friends_social_life.add(String.valueOf(checkBoxModel.getId()));
                    }
                }
                String ageRange = rangeAgeSeekBar.getSelectedMinValue() + "-" + rangeAgeSeekBar.getSelectedMaxValue();
                String friend_location;
                if (sbFriendLocation.getProgress() == 1000) {
                    friend_location = "w";
                } else {
                    friend_location = String.valueOf(sbFriendLocation.getProgress());
                }

                String attend_event, is_email_notification, is_mobile_notification;

                if (cbEvents.isChecked()) {
                    attend_event = "1";
                } else {
                    attend_event = "0";
                }

                if (cbEmail.isChecked()) {
                    is_email_notification = "1";
                } else {
                    is_email_notification = "0";
                }

                if (cbMobile.isChecked()) {
                    is_mobile_notification = "1";
                } else {
                    is_mobile_notification = "0";
                }

                setPreferencesWs(TextUtils.join(",", friends_gender), TextUtils.join(",", friends_ethnicity),
                        TextUtils.join(",", friends_interest), ageRange, TextUtils.join(",", friends_social_life),
                        friend_location, attend_event, is_email_notification, is_mobile_notification);
            } else {
                Toast.show(PreferencesActivity.this, getString(R.string.internet_offline));
            }
        }
        else {
            finish();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }

}
