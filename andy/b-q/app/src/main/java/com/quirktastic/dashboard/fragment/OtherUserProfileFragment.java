package com.quirktastic.dashboard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.core.BaseFragment;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.dashboard.adapter.OtherUserFragmentPagerAdapter;
import com.quirktastic.dashboard.model.modeluserlist.ModelUserList;
import com.quirktastic.dashboard.model.modeluserlist.USERLISTItem;
import com.quirktastic.dashboard.model.modeluserlist.UserNotInterestedResponse;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.Utility;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.sendrequest.SendRequestActivity;
import com.quirktastic.utility.IBundleKey;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.SendRequestListener;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;
import com.quirktastic.view.SwipViewpager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class OtherUserProfileFragment extends BaseFragment implements View.OnClickListener, SendRequestListener {


    private static final String TAG = OtherUserProfileFragment.class.getName();
    private DashboardActivity dashboardActivity;
    private View rootView;

    private RelativeLayout llNoData;
    private TextView tvNoData;
    private LottieAnimationView lavNoData;
    private AppCompatImageView ivSendRequest;
    private AppCompatImageView ivNextRequest;
    private SwipViewpager customViewPager;
    private int uriSegment = 0;
    private int currentItemVp = 0;
    ArrayList<USERLISTItem> userlistItems;


    public OtherUserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardActivity = (DashboardActivity) getActivity();
        userlistItems = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_other_user_profile, container, false);
        initView();
        return rootView;
    }


    //  initialize view
    private void initView() {

        tvNoData = (TextView) rootView.findViewById(R.id.tvNoData);
        llNoData = (RelativeLayout) rootView.findViewById(R.id.llNoData);
        lavNoData = (LottieAnimationView) rootView.findViewById(R.id.lavNoData);
        customViewPager = (SwipViewpager) rootView.findViewById(R.id.customViewPager);
        ivNextRequest = (AppCompatImageView) rootView.findViewById(R.id.ivNextRequest);
        ivSendRequest = (AppCompatImageView) rootView.findViewById(R.id.ivSendRequest);
        ivSendRequest.setOnClickListener(this);
        ivNextRequest.setOnClickListener(this);


        customViewPager.setSendRequestListener(this);
        customViewPager.setScrollDurationFactor(3);
        customViewPager.setAllowedSwipeDirection(SwipViewpager.SwipeDirection.right);
        customViewPager.setPageTransformer(true, new DepthTransformation());
        customViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                currentItemVp = i;


                if (userlistItems.get(currentItemVp).getId() == null) {
                    noUserFound();
                } else {

                    userNotInterested(userlistItems.get(currentItemVp - 1).getId());

                    if (currentItemVp == userlistItems.size() - 1) {
                        wsUserList();
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        wsUserList();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivSendRequest:

                loadSendRequestActivity();


                break;
            case R.id.ivNextRequest:

                loadNextRequest();

                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.loadNextRequest) {
            Util.loadNextRequest = false;
            loadNextRequest();
        }
    }

    public void loadSendRequestActivity() {
        if (userlistItems != null && userlistItems.size() > currentItemVp) {
            Intent intent = new Intent(dashboardActivity, SendRequestActivity.class);
            intent.putExtra(IBundleKey.TO_USER_ID, userlistItems.get(currentItemVp).getId());
            intent.putExtra(IBundleKey.FIRST_NAME, userlistItems.get(currentItemVp).getFirstName());
            if (userlistItems.get(currentItemVp).getPhotos() != null && userlistItems.get(currentItemVp).getPhotos().size() > 0) {
                intent.putExtra(IBundleKey.OTHER_PROFILE_PIC, userlistItems.get(currentItemVp).getPhotos().get(0).toString());
            }

            startActivity(intent);
            dashboardActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
        }
    }

    public void loadNextRequest() {

        if (userlistItems.size() > currentItemVp) {

            userNotInterested(userlistItems.get(currentItemVp).getId());

            currentItemVp = currentItemVp + 1;
            //  rotateAnim();
            customViewPager.setCurrentItem(currentItemVp);

            if (userlistItems.size() == currentItemVp) {


            }
        } else {
            wsUserList();
        }

    }

    // call ws for getting all the list of friends
    private void wsUserList() {
        if (!Utility.isInternetOn(dashboardActivity)) {
            showNodataFound("Internet seems to be offline.", "no_internet_connection.json");
            ivSendRequest.setVisibility(View.GONE);
            ivNextRequest.setVisibility(View.GONE);
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(dashboardActivity, PrefsKey.USER_ID, ""));
        map.put(WSKey.PER_PAGE, String.valueOf("10"));
        map.put(WSKey.URI_SEGMENT, String.valueOf(uriSegment));
        new CallNetworkRequest().postResponse(dashboardActivity, true, "wsUserList", AUTH_VALUE, WSUrl.POST_USER_LIST, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            if (!TextUtils.isEmpty(response)) {
                                ModelUserList modelUserList = new Gson().fromJson(response, ModelUserList.class);
                                if (modelUserList != null) {


                                    if (modelUserList.isFLAG() && modelUserList.getUSERLIST() != null && modelUserList.getUSERLIST().size() > 0) {
                                        customViewPager.setVisibility(View.VISIBLE);
                                        uriSegment = uriSegment + 10;

                                        for (int i = 0; i < modelUserList.getUSERLIST().size(); i++) {
                                            userlistItems.add(modelUserList.getUSERLIST().get(i));
                                        }


                                        if (userlistItems.size() == 1) {
                                            USERLISTItem userlistItem = new USERLISTItem();
                                            userlistItems.add(userlistItem);
                                        }

//                                        customViewPager.setOffscreenPageLimit(userlistItems.size());

                                        ;

                                        //  OtherUserPagerAdapter instaImagePagerAdapter = new OtherUserPagerAdapter(dashboardActivity, userlistItems);
                                        OtherUserFragmentPagerAdapter otherUserFragmentPagerAdapter = new OtherUserFragmentPagerAdapter(getChildFragmentManager(), buildFragments(), dashboardActivity, userlistItems);
                                        customViewPager.setAdapter(otherUserFragmentPagerAdapter);
                                        customViewPager.setCurrentItem(currentItemVp);


                                    } else {

                                        USERLISTItem userlistItem = new USERLISTItem();

                                        userlistItems.add(userlistItem);


                                        if (userlistItems.size() > 1) {

                                           /* OtherUserPagerAdapter instaImagePagerAdapter = new OtherUserPagerAdapter(dashboardActivity, userlistItems);
                                            customViewPager.setAdapter(instaImagePagerAdapter);
                                            customViewPager.setCurrentItem(currentItemVp);*/

                                            OtherUserFragmentPagerAdapter otherUserFragmentPagerAdapter = new OtherUserFragmentPagerAdapter(getChildFragmentManager(), buildFragments(), dashboardActivity, userlistItems);

                                            customViewPager.setAdapter(otherUserFragmentPagerAdapter);
                                            customViewPager.setCurrentItem(currentItemVp);
                                        } else {

                                            noUserFound();
                                        }

                                       /* ivSendRequest.setVisibility(View.GONE);
                                        ivNextRequest.setVisibility(View.GONE);
                                        showNodataFound(dashboardActivity.getString(R.string.no_user_found), "a_user.json");*/
                                    }

                                } else {
                                    Toast.show(dashboardActivity, dashboardActivity.getString(R.string.error_contact_server));
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            showNodataFound(dashboardActivity.getString(R.string.error_contact_server), "no_internet_connection.json");
                            ivSendRequest.setVisibility(View.GONE);
                            ivNextRequest.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        ivSendRequest.setVisibility(View.GONE);
                        ivNextRequest.setVisibility(View.GONE);
                        Logger.d(TAG, error.getErrorBody());
                        showNodataFound(dashboardActivity.getString(R.string.error_contact_server), "no_internet_connection.json");
                    }
                });
    }


    private void noUserFound() {
        ivSendRequest.setVisibility(View.GONE);
        ivNextRequest.setVisibility(View.GONE);
        showNodataFound(dashboardActivity.getString(R.string.no_user_found), "a_user.json");
    }

    private void userNotInterested(String toUserId) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.USER_ID, Prefs.getString(dashboardActivity, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, toUserId);

        new CallNetworkRequest().postResponse(dashboardActivity, false, "userNotInterested", AUTH_VALUE, WSUrl.POST_USER_NOT_INTERESTED, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            if (!TextUtils.isEmpty(response)) {
                                UserNotInterestedResponse userNotInterestedResponse = new Gson().fromJson(response, UserNotInterestedResponse.class);
                                if (userNotInterestedResponse != null) {
                                    if (userNotInterestedResponse.isFLAG()) {

                                    }

                                } else {
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.d(TAG, error.getErrorBody());
                    }
                });
    }


    public void showNodataFound(String textMsg, String iconMsg) {
        llNoData.setVisibility(View.VISIBLE);
        customViewPager.setVisibility(View.GONE);
        lavNoData.setAnimation(iconMsg);
        tvNoData.setText(textMsg);
        lavNoData.playAnimation();
        lavNoData.loop(true);


    }

    public void hideNodataFound() {
        llNoData.setVisibility(View.GONE);
        customViewPager.setVisibility(View.VISIBLE);
        lavNoData.cancelAnimation();

    }


    @Override
    public void sendRequestCallback() {
        if (userlistItems != null && userlistItems.size() > currentItemVp) {
            Intent intent = new Intent(dashboardActivity, SendRequestActivity.class);
            intent.putExtra(IBundleKey.TO_USER_ID, userlistItems.get(currentItemVp).getId());
            intent.putExtra(IBundleKey.FIRST_NAME, userlistItems.get(currentItemVp).getFirstName());
            if (userlistItems.get(currentItemVp).getPhotos() != null && userlistItems.get(currentItemVp).getPhotos().size() > 0) {
                intent.putExtra(IBundleKey.OTHER_PROFILE_PIC, userlistItems.get(currentItemVp).getPhotos().get(0).toString());
            }

            startActivity(intent);
            dashboardActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
        }
    }


    public class HingeTransformation implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {

            page.setTranslationX(-position * page.getWidth());
            page.setPivotX(0);
            page.setPivotY(0);


            if (position < -1) {    // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setAlpha(0);

            } else if (position <= 0) {    // [-1,0]
                page.setRotation(90 * Math.abs(position));
                page.setAlpha(1 - Math.abs(position));

            } else if (position <= 1) {    // (0,1]
                page.setRotation(0);
                page.setAlpha(1);

            } else {    // (1,+Infinity]
                // This page is way off-screen to the right.
                page.setAlpha(0);

            }


        }
    }

    public class DepthTransformation implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {

            if (position < -1) {    // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setAlpha(0);

            } else if (position <= 0) {    // [-1,0]
                page.setAlpha(1);
                page.setTranslationX(0);
                page.setScaleX(1);
                page.setScaleY(1);

            } else if (position <= 1) {    // (0,1]
                page.setTranslationX(-position * page.getWidth());
                page.setAlpha(1 - Math.abs(position));
                page.setScaleX(1 - Math.abs(position));
                page.setScaleY(1 - Math.abs(position));

            } else {    // (1,+Infinity]
                // This page is way off-screen to the right.
                page.setAlpha(0);

            }


        }
    }


    private List<OtherUserDataFragment> buildFragments() {
        List<OtherUserDataFragment> fragments = new ArrayList<OtherUserDataFragment>();
        for (int i = 0; i < userlistItems.size(); i++) {
            Bundle b = new Bundle();
            b.putInt("position", i);
            fragments.add(new OtherUserDataFragment());
        }

        return fragments;
    }
}
