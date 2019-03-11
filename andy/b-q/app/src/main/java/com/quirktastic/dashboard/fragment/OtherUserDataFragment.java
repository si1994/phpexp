package com.quirktastic.dashboard.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.core.BaseFragment;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.dashboard.adapter.OtherUserSocialAdapter;
import com.quirktastic.dashboard.model.modeluserlist.OtherUserSocialModel;
import com.quirktastic.dashboard.model.modeluserlist.USERLISTItem;
import com.quirktastic.instagram.adapter.InstaFeedAdapter;
import com.quirktastic.instagram.model.instagram.InstagramMediaResponse;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.SquareImageView;
import com.quirktastic.utility.SquareLinearLayout;
import com.quirktastic.utility.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import br.com.instachat.emojilibrary.model.layout.EmojiTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OtherUserDataFragment extends BaseFragment {


    private static final String TAG = OtherUserDataFragment.class.getName();
    @BindView(R.id.tvOtherProfileName)
    AppCompatTextView tvOtherProfileName;
    @BindView(R.id.tvOtherProfileAge)
    AppCompatTextView tvOtherProfileAge;
    @BindView(R.id.imgZodiac)
    ImageView imgZodiac;
    @BindView(R.id.tvOtherProfileCityState)
    AppCompatTextView tvOtherProfileCityState;
    @BindView(R.id.ivDescription)
    SquareImageView ivDescription;
    @BindView(R.id.pbDescription)
    SquareLinearLayout pbDescription;
    @BindView(R.id.tvDescription)
    EmojiTextView tvDescription;
    @BindView(R.id.tvGender)
    AppCompatTextView tvGender;
    @BindView(R.id.imgGender)
    ImageView imgGender;
    @BindView(R.id.tvEthnicity)
    AppCompatTextView tvEthnicity;
    @BindView(R.id.ivEthnicGender)
    SquareImageView ivEthnicGender;
    @BindView(R.id.pbEthnicGender)
    SquareLinearLayout pbEthnicGender;
    @BindView(R.id.flInterestChip)
    FlexboxLayout flInterestChip;
    @BindView(R.id.tvFriendDesc)
    EmojiTextView tvFriendDesc;
    @BindView(R.id.ivFriendDesc)
    SquareImageView ivFriendDesc;
    @BindView(R.id.pbFriendDesc)
    SquareLinearLayout pbFriendDesc;
    @BindView(R.id.rvSocialDrink)
    RecyclerView rvSocialDrink;
    @BindView(R.id.tvMutualInterest)
    EmojiTextView tvMutualInterest;
    @BindView(R.id.ivMutualInterest)
    SquareImageView ivMutualInterest;
    @BindView(R.id.pbMutualInterest)
    SquareLinearLayout pbMutualInterest;
    @BindView(R.id.flEventsChip)
    FlexboxLayout flEventsChip;
    @BindView(R.id.rvInstaFeed)
    RecyclerView rvInstaFeed;
    @BindView(R.id.llInstagram)
    LinearLayout llInstagram;
    @BindView(R.id.llMain)
    LinearLayout llMain;
    @BindView(R.id.nsvOtherProfile)
    NestedScrollView nsvOtherProfile;
    @BindView(R.id.llNoData)
    RelativeLayout llNoData;
    @BindView(R.id.relMain)
    RelativeLayout relMain;
    Unbinder unbinder;

    private DashboardActivity dashboardActivity;
    private View rootView;
    private USERLISTItem userlistItem;

    private ArrayList<String> listInterest;
    private ArrayList<OtherUserSocialModel> listSocial;
    private ArrayList<String> listEvent;

    private Boolean isViewVisible = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardActivity = (DashboardActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.item_other_user_profile, container, false);
        // Inflate the layout for this fragment

        unbinder = ButterKnife.bind(this, rootView);

        userlistItem = (USERLISTItem) getArguments().getParcelable("userlistItem");

        setOtherUserListData();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void setOtherUserListData() {

        listSocial = new ArrayList<>();
        listInterest = new ArrayList<>();
        listEvent = new ArrayList<>();


        removeAllView();
        initSocialAdapter();


        if (userlistItem.getId() != null) {

            nsvOtherProfile.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);

            if (userlistItem.getInstagramId() != null && userlistItem.getInstagramId().trim().length() > 0) {

                if (userlistItem.getInstagramAccessToken() != null && userlistItem.getInstagramAccessToken().trim().length() > 0) {

                    llInstagram.setVisibility(View.VISIBLE);
                    instaFeedWs(userlistItem.getInstagramId(), userlistItem.getInstagramAccessToken());

                } else {
                    llInstagram.setVisibility(View.GONE);
                }
            } else {
                llInstagram.setVisibility(View.GONE);
            }


            setUserListTextData(userlistItem);


            if (userlistItem.getPhotos() != null && userlistItem.getPhotos().size() > 0) {
                setImage(userlistItem);
            }


            if (userlistItem.getSocialLife() != null) {
                for (int i = 0; i < 3; i++) {
                    if (i == 0) {
                        OtherUserSocialModel otherUserSocialModel = new OtherUserSocialModel();
                        otherUserSocialModel.setDrawable(R.drawable.icon_drinking);
                        otherUserSocialModel.setType(userlistItem.getSocialLife().getDrinking());
                        listSocial.add(otherUserSocialModel);
                    } else if (i == 1) {
                        OtherUserSocialModel otherUserSocialModel = new OtherUserSocialModel();
                        otherUserSocialModel.setDrawable(R.drawable.icon_smoking);
                        otherUserSocialModel.setType(userlistItem.getSocialLife().getSmoking());
                        listSocial.add(otherUserSocialModel);
                    } else {
                        OtherUserSocialModel otherUserSocialModel = new OtherUserSocialModel();
                        otherUserSocialModel.setDrawable(R.drawable.icon_marijuana);
                        otherUserSocialModel.setType(userlistItem.getSocialLife().getMarijuanas());
                        listSocial.add(otherUserSocialModel);
                    }

                }

                setSocialAdapter();
            }

            if (userlistItem.getInterestName() != null && userlistItem.getInterestName().size() > 0) {
                Object[] objectArray = userlistItem.getInterestName().toArray();
                String[] stringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);

                for (int i = 0; i < stringArray.length; i++) {
                    listInterest.add(stringArray[i]);
                }
                setInterestAdapter();
            }

            if (userlistItem.getEventName() != null && userlistItem.getEventName().size() > 0) {
                Object[] objectArray = userlistItem.getEventName().toArray();
                String[] stringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);
                for (int i = 0; i < stringArray.length; i++) {
                    listEvent.add(stringArray[i]);
                }
                setEventAdapter();
            }


        } else {

            nsvOtherProfile.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
        }

    }


    private void setInterestAdapter() {
        for (int i = 0; i < listInterest.size(); i++) {
            View v = createChip(dashboardActivity, listInterest.get(i));
            flInterestChip.addView(v, 0);
        }
    }

    private void setSocialAdapter() {
        OtherUserSocialAdapter otherUserSocialAdapter = new OtherUserSocialAdapter(dashboardActivity, listSocial);
        rvSocialDrink.setAdapter(otherUserSocialAdapter);
    }

    private void setEventAdapter() {

        for (int i = 0; i < listEvent.size(); i++) {
            View v = createChip(dashboardActivity, listEvent.get(i));
            flEventsChip.addView(v, 0);
        }
    }

    private View createChip(Context context, final String interestItem) {
        View view = View.inflate(context, R.layout.chip_text_view, null);
        TextView tvChip = view.findViewById(R.id.tvChip);
        tvChip.setText(interestItem);
        return view;
    }


    private void setUserListTextData(USERLISTItem userlistItem) {
        tvOtherProfileName.setText(userlistItem.getFirstName() + " " + userlistItem.getLastName());
        if (userlistItem.getCityName().equals("") && userlistItem.getStateShortName().equals("")) {
            tvOtherProfileCityState.setText("");
            // imgZodiac.setVisibility(View.GONE);
        } else {
            //imgZodiac.setVisibility(View.VISIBLE);
            tvOtherProfileCityState.setText(userlistItem.getCityName() + ", " + userlistItem.getStateShortName());
        }


        if (userlistItem.getGender().equalsIgnoreCase("1")) {
            tvGender.setText(dashboardActivity.getString(R.string.gender_man));
            imgGender.setImageResource(R.drawable.icon_male);

        } else if (userlistItem.getGender().equalsIgnoreCase("2")) {
            tvGender.setText(dashboardActivity.getString(R.string.gender_woman));
            imgGender.setImageResource(R.drawable.icon_female_black);
        } else {
            tvGender.setText(dashboardActivity.getString(R.string.gender_non_binary));
            imgGender.setImageResource(R.drawable.icon_frame);
        }

        if (userlistItem.getIdentityDescription() != null && !TextUtils.isEmpty(userlistItem.getIdentityName().toString())) {
            tvEthnicity.setText(userlistItem.getIdentityName().toString());
        } else {
            tvEthnicity.setText("");
        }

        if (!TextUtils.isEmpty(userlistItem.getPersonalityText().toString())) {
            tvFriendDesc.setText(Util.gifDecode(userlistItem.getPersonalityText().toString()));
        } else {
            tvFriendDesc.setText("");
        }

        if (!TextUtils.isEmpty(userlistItem.getInterestsText().toString())) {
            tvMutualInterest.setText(Util.gifDecode(userlistItem.getInterestsText().toString()));
        } else {
            tvMutualInterest.setText("");
        }

        if (!TextUtils.isEmpty(userlistItem.getHeaderBio().toString())) {
            tvDescription.setText(Util.gifDecode(userlistItem.getHeaderBio().toString()));
        } else {
            tvDescription.setText("");
        }

        tvOtherProfileAge.setText(userlistItem.getAge());
        setZodiacSign(userlistItem.getDateOfBirth());
    }


    private void removeAllView() {

        if (flEventsChip != null) {
            flEventsChip.removeAllViews();
        }
        if (flInterestChip != null) {
            flInterestChip.removeAllViews();
        }
    }


    private void setImage(USERLISTItem userlistItem) {

        System.out.println("getPhotos().size(===========================" + userlistItem.getPhotos().size());
        if (userlistItem.getPhotos().size() == 1) {

            System.out.println(userlistItem.getPhotos().get(0).toString());



            if(pbDescription!=null) {
                pbDescription.setVisibility(View.VISIBLE);
            }
            Glide.with(dashboardActivity)
                    .load(userlistItem.getPhotos().get(0).toString()).thumbnail(0.1f).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                           if(pbDescription!=null) {
                               pbDescription.setVisibility(View.GONE);
                           }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                           if(pbDescription!=null)
                           {
                               pbDescription.setVisibility(View.GONE);
                           }
                            return false;
                        }
                    })
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.img_placeholder)
                            .error(R.drawable.img_placeholder)
                            .priority(Priority.IMMEDIATE)
                            .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(ivDescription);
        } else if (userlistItem.getPhotos().size() == 2) {

            System.out.println(userlistItem.getPhotos().get(0).toString());
            System.out.println(userlistItem.getPhotos().get(1).toString());


            if(pbDescription!=null) {
                pbDescription.setVisibility(View.VISIBLE);
            }
            Glide.with(dashboardActivity)
                    .load(userlistItem.getPhotos().get(0).toString()).thumbnail(0.1f).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(pbDescription!=null) {
                                pbDescription.setVisibility(View.GONE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(pbDescription!=null) {
                                pbDescription.setVisibility(View.GONE);
                            }
                            return false;
                        }
                    }).apply(RequestOptions
                    .placeholderOf(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(ivDescription);




            if(pbEthnicGender!=null) {
                pbEthnicGender.setVisibility(View.VISIBLE);
            }

            Glide.with(dashboardActivity)
                    .load(userlistItem.getPhotos().get(1).toString()).thumbnail(0.1f).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(pbEthnicGender!=null) {
                                pbEthnicGender.setVisibility(View.GONE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(pbEthnicGender!=null) {
                                pbEthnicGender.setVisibility(View.GONE);
                            }
                            return false;
                        }
                    }).apply(RequestOptions
                    .placeholderOf(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(ivEthnicGender);


        } else if (userlistItem.getPhotos().size() == 3) {

            System.out.println(userlistItem.getPhotos().get(0).toString());
            System.out.println(userlistItem.getPhotos().get(1).toString());
            System.out.println(userlistItem.getPhotos().get(2).toString());

            if(pbDescription!=null) {
                pbDescription.setVisibility(View.VISIBLE);
            }

            Glide.with(dashboardActivity)
                    .load(userlistItem.getPhotos().get(0).toString()).thumbnail(0.1f).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(pbDescription!=null) {
                                pbDescription.setVisibility(View.GONE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(pbDescription!=null) {
                                pbDescription.setVisibility(View.GONE);
                            }
                            return false;
                        }
                    }).apply(RequestOptions
                    .placeholderOf(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(ivDescription);


            if(pbEthnicGender!=null) {
                pbEthnicGender.setVisibility(View.VISIBLE);
            }
            Glide.with(dashboardActivity)
                    .load(userlistItem.getPhotos().get(1).toString()).thumbnail(0.1f).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(pbEthnicGender!=null) {
                                pbEthnicGender.setVisibility(View.GONE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(pbEthnicGender!=null) {
                                pbEthnicGender.setVisibility(View.GONE);
                            }
                            return false;
                        }
                    }).apply(RequestOptions
                    .placeholderOf(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(ivEthnicGender);


            if(pbFriendDesc!=null) {
                pbFriendDesc.setVisibility(View.VISIBLE);
            }
            Glide.with(dashboardActivity)
                    .load(userlistItem.getPhotos().get(2).toString()).thumbnail(0.1f).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(pbFriendDesc!=null) {
                                pbFriendDesc.setVisibility(View.GONE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(pbFriendDesc!=null) {
                                pbFriendDesc.setVisibility(View.GONE);
                            }
                            return false;
                        }
                    }).apply(RequestOptions
                    .placeholderOf(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(ivFriendDesc);

        } else if (userlistItem.getPhotos().size() == 4) {

            System.out.println(userlistItem.getPhotos().get(0).toString());
            System.out.println(userlistItem.getPhotos().get(1).toString());
            System.out.println(userlistItem.getPhotos().get(2).toString());
            System.out.println(userlistItem.getPhotos().get(3).toString());

            if(pbDescription!=null) {
                pbDescription.setVisibility(View.VISIBLE);
            }
            Glide.with(dashboardActivity)
                    .load(userlistItem.getPhotos().get(0).toString()).thumbnail(0.1f).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(pbDescription!=null) {
                                pbDescription.setVisibility(View.GONE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(pbDescription!=null) {
                                pbDescription.setVisibility(View.GONE);
                            }
                            return false;
                        }

                    }).apply(RequestOptions
                    .placeholderOf(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(ivDescription);

            if(pbEthnicGender!=null) {
                pbEthnicGender.setVisibility(View.VISIBLE);
            }
            Glide.with(dashboardActivity)
                    .load(userlistItem.getPhotos().get(1).toString()).thumbnail(0.1f).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(pbEthnicGender!=null) {
                                pbEthnicGender.setVisibility(View.GONE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(pbEthnicGender!=null) {
                                pbEthnicGender.setVisibility(View.GONE);
                            }
                            return false;
                        }
                    })
                    .apply(RequestOptions
                            .placeholderOf(R.drawable.img_placeholder)
                            .error(R.drawable.img_placeholder)
                            .priority(Priority.IMMEDIATE)
                            .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(ivEthnicGender);

            if(pbFriendDesc!=null) {
                pbFriendDesc.setVisibility(View.VISIBLE);
            }

            Glide.with(dashboardActivity)
                    .load(userlistItem.getPhotos().get(2).toString()) .thumbnail(0.1f).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(pbFriendDesc!=null) {
                                pbFriendDesc.setVisibility(View.GONE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(pbFriendDesc!=null) {
                                pbFriendDesc.setVisibility(View.GONE);
                            }
                            return false;
                        }
                    }).apply(RequestOptions
                    .placeholderOf(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(ivFriendDesc);



            if(pbMutualInterest!=null) {
                pbMutualInterest.setVisibility(View.VISIBLE);
            }
            Glide.with(dashboardActivity)
                    .load(userlistItem.getPhotos().get(3).toString()).thumbnail(0.1f).
                    listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(pbMutualInterest!=null) {
                                pbMutualInterest.setVisibility(View.GONE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(pbMutualInterest!=null) {
                                pbMutualInterest.setVisibility(View.GONE);
                            }
                            return false;
                        }
                    }).apply(RequestOptions
                    .placeholderOf(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(ivMutualInterest);

        }

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


    private void initSocialAdapter() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(dashboardActivity, 3);
        rvSocialDrink.setLayoutManager(layoutManager);
        rvSocialDrink.setNestedScrollingEnabled(false);
    }


    private void instaFeedWs(String userId, String accessToken) {

        String url = "https://api.instagram.com/v1/users/" + userId + "/media/recent/?access_token=" + accessToken + "&count=9";

        new CallNetworkRequest().getResponse(dashboardActivity, false, "userChatMsgList", AppContants.AUTH_VALUE, url,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {

                            InstagramMediaResponse instagramMediaResponse = new Gson().fromJson(response, InstagramMediaResponse.class);

                            if (instagramMediaResponse != null) {

                                final InstaFeedAdapter myPhotosAdapter = new InstaFeedAdapter(dashboardActivity, instagramMediaResponse.getData());
                                rvInstaFeed.setLayoutManager(new GridLayoutManager(dashboardActivity, 3));
                                ViewCompat.setNestedScrollingEnabled(rvInstaFeed, false);
                                rvInstaFeed.setAdapter(myPhotosAdapter);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
//                        Toast.show(chatProfileActivity,getString(R.string.error_contact_server));
                        llInstagram.setVisibility(View.GONE);
                    }
                });

    }


    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isViewVisible = true;
        } else {
            isViewVisible = false;
        }
    }*/


}
