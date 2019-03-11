package com.quirktastic.dashboard.friendrequeststatus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.core.BaseActivity;
import com.quirktastic.dashboard.profile.ProfileActivity;
import com.quirktastic.dashboard.profile.model.profiledetails.EventItem;
import com.quirktastic.dashboard.profile.model.profiledetails.IdentityItem;
import com.quirktastic.dashboard.profile.model.profiledetails.InterestItem;
import com.quirktastic.dashboard.profile.model.profiledetails.ProfileDetailsResponse;
import com.quirktastic.dashboard.profile.model.profiledetails.UserDetailsItem;
import com.quirktastic.instagram.adapter.InstaFeedAdapter;
import com.quirktastic.instagram.model.instagram.InstagramMediaResponse;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.onboard.adapter.SocialLifeListForViewAdapter;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.IBundleKey;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.SquareLinearLayout;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import br.com.instachat.emojilibrary.model.layout.EmojiTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;
import static com.quirktastic.utility.AppContants.GENDER_MAN;
import static com.quirktastic.utility.AppContants.GENDER_NON_BINARY;
import static com.quirktastic.utility.AppContants.GENDER_WOMAN;

public class ViewUserProfileActivity extends BaseActivity {

    public static final String TAG = ViewUserProfileActivity.class.getName();


    @BindView(R.id.pbDescription)
    SquareLinearLayout pbDescription;
    @BindView(R.id.pbEthnicGender)
    SquareLinearLayout pbEthnicGender;
    @BindView(R.id.pbFriendDesc)
    SquareLinearLayout pbFriendDesc;
    @BindView(R.id.pbMutualInterest)
    SquareLinearLayout pbMutualInterest;

    @BindView(R.id.tvOtherProfileName)
    AppCompatTextView tvOtherProfileName;
    @BindView(R.id.tvOtherProfileAge)
    AppCompatTextView tvOtherProfileAge;
    @BindView(R.id.tvOtherProfileCityState)
    AppCompatTextView tvOtherProfileCityState;
    @BindView(R.id.ivDescription)
    ImageView ivDescription;
    @BindView(R.id.tvDescription)
    EmojiTextView tvDescription;
    @BindView(R.id.ivEthnicGender)
    ImageView ivEthnicGender;
    @BindView(R.id.tvGender)
    AppCompatTextView tvGender;
    @BindView(R.id.tvEthnicity)
    AppCompatTextView tvEthnicity;

    @BindView(R.id.ivFriendDesc)
    ImageView ivFriendDesc;
    @BindView(R.id.tvFriendDesc)
    EmojiTextView tvFriendDesc;
    @BindView(R.id.rvSocialDrink)
    RecyclerView rvSocialDrink;
    @BindView(R.id.ivMutualInterest)
    ImageView ivMutualInterest;
    @BindView(R.id.tvMutualInterest)
    EmojiTextView tvMutualInterest;


    @BindView(R.id.flInterestChip)
    FlexboxLayout flInterestChip;

    @BindView(R.id.flEventsChip)
    FlexboxLayout flEventsChip;

    String id;
    @BindView(R.id.imgZodiac)
    ImageView imgZodiac;

    @BindView(R.id.nsvOtherProfile)
    NestedScrollView nsvOtherProfile;
    @BindView(R.id.rvInstaFeed)
    RecyclerView rvInstaFeed;
    @BindView(R.id.llInstagram)
    LinearLayout llInstagram;
    @BindView(R.id.imgGender)
    ImageView imgGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_profile);
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().hasExtra(IBundleKey.USER_ID)) {
            id = getIntent().getStringExtra(IBundleKey.USER_ID);
        }
        getProfileDetailsAPI();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void getProfileDetailsAPI() {


        nsvOtherProfile.setVisibility(View.GONE);

        final HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, Prefs.getString(ViewUserProfileActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, id);

        new CallNetworkRequest().postResponse(ViewUserProfileActivity.this, true, "update Eevent", AUTH_VALUE, WSUrl.GET_PROFILE_DETAILS, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            Gson gson = new Gson();
                            ProfileDetailsResponse profileDetailsResponse = gson.fromJson(response, ProfileDetailsResponse.class);

                            if (profileDetailsResponse.isFlag()) {

                                if (profileDetailsResponse.getUserDetails() != null && profileDetailsResponse.getUserDetails().size() > 0) {
                                    nsvOtherProfile.setVisibility(View.VISIBLE);
                                    setUpViews(profileDetailsResponse.getUserDetails().get(0));
                                }

                            } else {
                                Toast.show(ViewUserProfileActivity.this, profileDetailsResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error ===> " + error.getErrorBody());
                        Toast.show(ViewUserProfileActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }

    private void setUpViews(UserDetailsItem userDetailsItem) {
        try {
            if (userDetailsItem != null) {

                setUserName(userDetailsItem.getFirstName(), userDetailsItem.getLastName());
                setAgeCityState(userDetailsItem.getDateOfBirth(), userDetailsItem.getCityName(), userDetailsItem.getStateShortName());
                setZodiacSign(userDetailsItem.getDateOfBirth());
                if (userDetailsItem.getHeaderBio() != null && userDetailsItem.getHeaderBio().length() > 0) {
                    tvDescription.setVisibility(View.VISIBLE);
                    tvDescription.setText(Util.gifDecode(userDetailsItem.getHeaderBio()));
                }
//                else  {
//                    tvDescription.setVisibility(View.GONE);
//                }

                setGender(userDetailsItem.getGender());
                setEthenity(userDetailsItem.getIdentity());

//                if (tvGender.getText().toString().trim().length() <= 0 && tvEthnicity.getText().toString().trim().length() <= 0) {
//                    llGenderIdentity.setVisibility(View.GONE);
//                }

                setInterests(userDetailsItem.getInterest());

                if (userDetailsItem.getPersonalityText() != null && userDetailsItem.getPersonalityText().length() > 0) {
                    tvFriendDesc.setText(Util.gifDecode(userDetailsItem.getPersonalityText()));
                }
//                else {
//                    llPersonalityData.setVisibility(View.GONE);
//                }

                if (userDetailsItem.getInterestsText() != null && userDetailsItem.getInterestsText().length() > 0) {
                    tvMutualInterest.setText(Util.gifDecode(userDetailsItem.getInterestsText()));
                }
//                else {
//                    llInterestTextData.setVisibility(View.GONE);
//                }

                setUpImages(userDetailsItem);
                setSocialList(userDetailsItem);
                setUpEvents(userDetailsItem);
                setUpInstagram(userDetailsItem);
            }
        } catch (Exception ex) {

        }
    }

    private void setUpInstagram(UserDetailsItem userDetailsItem) {
        if (userDetailsItem.getInstagramId() != null && userDetailsItem.getInstagramId().trim().length() > 0) {

            if (userDetailsItem.getInstagramAccessToken() != null && userDetailsItem.getInstagramAccessToken().trim().length() > 0) {
                llInstagram.setVisibility(View.VISIBLE);
                instaFeedWs(userDetailsItem.getInstagramId(), userDetailsItem.getInstagramAccessToken());
            } else {
                llInstagram.setVisibility(View.GONE);
            }
        } else {
            llInstagram.setVisibility(View.GONE);
        }
    }

    private void setUpEvents(UserDetailsItem userDetailsItem) {
        if (userDetailsItem.getEvent() != null && userDetailsItem.getEvent().size() > 0) {

            ArrayList<EventItem> selectedEvents = new ArrayList<>();

            for (int i = 0; i < userDetailsItem.getEvent().size(); i++) {
                if (userDetailsItem.getEvent().get(i).getIsSelected() == 1) {
                    selectedEvents.add(userDetailsItem.getEvent().get(i));
                }
            }

            if (selectedEvents.size() > 0) {

                for (int i = 0; i < selectedEvents.size(); i++) {
                    View v = createEventsChip(ViewUserProfileActivity.this, selectedEvents.get(i));
                    flEventsChip.addView(v, 0);
                }

            }

        }
    }

    private void setSocialList(UserDetailsItem userDetailsItem) {
        if (userDetailsItem.getSocialLife() != null && userDetailsItem.getSocialLife().size() > 0) {
            rvSocialDrink.setLayoutManager(new GridLayoutManager(ViewUserProfileActivity.this, 3));
            rvSocialDrink.setAdapter(new SocialLifeListForViewAdapter(ViewUserProfileActivity.this, userDetailsItem.getSocialLife()));
        }
    }

    private void setInterests(ArrayList<InterestItem> interest) {
        if (interest != null && interest.size() > 0) {
            ArrayList<InterestItem> selectedInterest = new ArrayList<>();

            for (int i = 0; i < interest.size(); i++) {
                if (interest.get(i).getIsSelected() == 1) {
                    selectedInterest.add(interest.get(i));
                }
            }

            if (selectedInterest.size() > 0) {
                for (int i = 0; i < selectedInterest.size(); i++) {
                    View v = createInterestChip(ViewUserProfileActivity.this, selectedInterest.get(i));
                    flInterestChip.addView(v, 0);
                }
            }


        }
//        else {
//            llInterestList.setVisibility(View.GONE);
//        }
    }

    private void setEthenity(ArrayList<IdentityItem> identity) {
        if (identity != null && identity.size() > 0) {
            for (int i = 0; i < identity.size(); i++) {

                if (identity.get(i).getIsSelected() == 1) {
                    tvEthnicity.setText(identity.get(i).getIdentityName());
                    break;
                }
            }
        }
    }

    private void setGender(String gender) {
        if (gender != null && gender.length() > 0) {

            if (gender.equalsIgnoreCase(GENDER_MAN)) {
                tvGender.setText(getString(R.string.man));
                imgGender.setImageResource(R.drawable.icon_male);
            } else if (gender.equalsIgnoreCase(GENDER_WOMAN)) {
                tvGender.setText(getString(R.string.women));
                imgGender.setImageResource(R.drawable.icon_female_black);
            } else if (gender.equalsIgnoreCase(GENDER_NON_BINARY)) {
                tvGender.setText(getString(R.string.non_binary));
                imgGender.setImageResource(R.drawable.icon_frame);
            }
        }
    }

    private void setAgeCityState(String dateOfBirth, String cityName, String stateShortName) {

        String userAge = "";
        String cityState = "";

        if (dateOfBirth != null && dateOfBirth.length() > 0) {
            try {
                userAge = Util.calculateAge(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth));
                tvOtherProfileAge.setText(userAge);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (cityName != null && cityName.length() > 0) {
            cityState = cityName + ", ";
        }

        if (stateShortName != null && stateShortName.length() > 0) {
            if (cityState.length() > 0) {
                cityState = cityState + stateShortName;
            } else {
                cityState = stateShortName;
            }
        }

        tvOtherProfileCityState.setText(cityState);

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

        tvOtherProfileName.setText(userName);
    }

    private void setUpImages(UserDetailsItem userDetailsItem) {

        try {
            if (userDetailsItem.getPhotos().size() > 0) {
                switch (userDetailsItem.getPhotos().size()) {
                    case 1:
                        setFirstimage(userDetailsItem.getPhotos().get(0).getPhotos());
                        break;
                    case 2:
                        setFirstimage(userDetailsItem.getPhotos().get(0).getPhotos());
                        setSecondimage(userDetailsItem.getPhotos().get(1).getPhotos());
                        break;
                    case 3:
                        setFirstimage(userDetailsItem.getPhotos().get(0).getPhotos());
                        setSecondimage(userDetailsItem.getPhotos().get(1).getPhotos());
                        setThirdimage(userDetailsItem.getPhotos().get(2).getPhotos());
                        break;
                    case 4:
                        setFirstimage(userDetailsItem.getPhotos().get(0).getPhotos());
                        setSecondimage(userDetailsItem.getPhotos().get(1).getPhotos());
                        setThirdimage(userDetailsItem.getPhotos().get(2).getPhotos());
                        setFourthimage(userDetailsItem.getPhotos().get(3).getPhotos());
                        break;
                    case 5:
                        setFirstimage(userDetailsItem.getPhotos().get(0).getPhotos());
                        setSecondimage(userDetailsItem.getPhotos().get(1).getPhotos());
                        setThirdimage(userDetailsItem.getPhotos().get(2).getPhotos());
                        setFourthimage(userDetailsItem.getPhotos().get(3).getPhotos());
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void setFirstimage(String imageUrl) {
        pbDescription.setVisibility(View.VISIBLE);
        Glide.with(ViewUserProfileActivity.this).load(imageUrl)
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        pbDescription.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pbDescription.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(RequestOptions
                        .placeholderOf(R.drawable.img_placeholder)
                        .error(R.drawable.img_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(1600, 900))
                .into(ivDescription);
    }

    private void setSecondimage(String imageUrl) {
        pbEthnicGender.setVisibility(View.VISIBLE);
        Glide.with(ViewUserProfileActivity.this).load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        pbEthnicGender.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pbEthnicGender.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(RequestOptions
                        .placeholderOf(R.drawable.img_placeholder)
                        .error(R.drawable.img_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(1600, 900))
                .into(ivEthnicGender);
    }

    private void setThirdimage(String imageUrl) {
        pbFriendDesc.setVisibility(View.VISIBLE);
        Glide.with(ViewUserProfileActivity.this).load(imageUrl)
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        pbFriendDesc.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pbFriendDesc.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(RequestOptions
                        .placeholderOf(R.drawable.img_placeholder)
                        .error(R.drawable.img_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(1600, 900))
                .into(ivFriendDesc);
    }

    private void setFourthimage(String imageUrl) {
        pbMutualInterest.setVisibility(View.VISIBLE);
        Glide.with(ViewUserProfileActivity.this).load(imageUrl)
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        pbMutualInterest.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pbMutualInterest.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(RequestOptions
                        .placeholderOf(R.drawable.img_placeholder)
                        .error(R.drawable.img_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(1600, 900))
                .into(ivMutualInterest);
    }

    private View createInterestChip(Context context, final InterestItem interestItem) {
        View view = View.inflate(context, R.layout.chip_text_view, null);
        TextView tvChip = view.findViewById(R.id.tvChip);
        tvChip.setText(interestItem.getInterestName());
        return view;
    }

    private View createEventsChip(Context context, final EventItem interestItem) {
        View view = View.inflate(context, R.layout.chip_text_view, null);
        TextView tvChip = view.findViewById(R.id.tvChip);
        tvChip.setText(interestItem.getTitle());
        return view;
    }

    private void setZodiacSign(String dateString) {


        int month = 0;
        int day = 0;

        if (dateString.equals("0000-00-00")) {
            imgZodiac.setImageResource(R.drawable.solid_circle);
        } else {
            try {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sd.parse(dateString);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                month = cal.get(Calendar.MONTH) + 1;
                day = cal.get(Calendar.DAY_OF_MONTH);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            switch (month) {
                case 1:
                    if (day < 20) {
                        imgZodiac.setImageResource(R.drawable.ic_capricorn);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_aquarius);
                    }
                    break;
                case 2:
                    if (day < 18) {
                        imgZodiac.setImageResource(R.drawable.ic_aquarius);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_pisces);
                    }
                    break;
                case 3:
                    if (day < 21) {
                        imgZodiac.setImageResource(R.drawable.ic_pisces);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_aries);
                    }
                    break;
                case 4:
                    if (day < 20) {
                        imgZodiac.setImageResource(R.drawable.ic_aries);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_taurus);
                    }
                    break;
                case 5:
                    if (day < 21) {
                        imgZodiac.setImageResource(R.drawable.ic_taurus);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_gemini);
                    }
                    break;
                case 6:
                    if (day < 21) {
                        imgZodiac.setImageResource(R.drawable.ic_gemini);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_cancer);
                    }
                    break;
                case 7:
                    if (day < 23) {
                        imgZodiac.setImageResource(R.drawable.ic_cancer);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_leo);
                    }
                    break;
                case 8:
                    if (day < 23) {
                        imgZodiac.setImageResource(R.drawable.ic_leo);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_virgo);
                    }
                    break;
                case 9:
                    if (day < 23) {
                        imgZodiac.setImageResource(R.drawable.ic_virgo);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_libra);
                    }
                    break;
                case 10:
                    if (day < 23) {
                        imgZodiac.setImageResource(R.drawable.ic_libra);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_scorpio);
                    }
                    break;
                case 11:
                    if (day < 22) {
                        imgZodiac.setImageResource(R.drawable.ic_scorpio);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_sagittarius);
                    }
                    break;
                case 12:
                    if (day < 22) {
                        imgZodiac.setImageResource(R.drawable.ic_sagittarius);
                    } else {
                        imgZodiac.setImageResource(R.drawable.ic_capricorn);
                    }
                    break;
            }
        }
    }

    private void instaFeedWs(String userId, String accessToken) {

        String url = "https://api.instagram.com/v1/users/" + userId + "/media/recent/?access_token=" + accessToken + "&count=9";

        new CallNetworkRequest().getResponse(ViewUserProfileActivity.this, false, "userChatMsgList", AppContants.AUTH_VALUE, url,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {

                            InstagramMediaResponse instagramMediaResponse = new Gson().fromJson(response, InstagramMediaResponse.class);

                            if (instagramMediaResponse != null) {
                                InstaFeedAdapter myPhotosAdapter = new InstaFeedAdapter(ViewUserProfileActivity.this, instagramMediaResponse.getData());
                                rvInstaFeed.setLayoutManager(new GridLayoutManager(ViewUserProfileActivity.this, 3));
                                rvInstaFeed.setAdapter(myPhotosAdapter);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            llInstagram.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onError(ANError error) {
//                        Toast.show(chatProfileActivity,getString(R.string.error_contact_server));
                        llInstagram.setVisibility(View.GONE);
                    }
                });

    }
}
