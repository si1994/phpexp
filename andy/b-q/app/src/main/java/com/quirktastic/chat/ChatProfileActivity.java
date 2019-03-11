package com.quirktastic.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.chat.adapter.ChatProfileAdapter;
import com.quirktastic.core.BaseActivity;
import com.quirktastic.dashboard.profile.model.profiledetails.ProfileDetailsResponse;
import com.quirktastic.dashboard.profile.model.profiledetails.UserDetailsItem;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.sendrequest.SendRequestActivity;
import com.quirktastic.sendrequest.SentRequestActivity;
import com.quirktastic.sendrequest.model.ModelSendRequest;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.IBundleKey;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;
import com.quirktastic.view.CustomViewPager;

import java.util.HashMap;

import br.com.instachat.emojilibrary.model.layout.EmojiCompatActivity;

import static com.quirktastic.utility.AppContants.AUTH_VALUE;

public class ChatProfileActivity extends EmojiCompatActivity implements View.OnClickListener {
    private static final String TAG = ChatProfileActivity.class.getName();

    private CustomViewPager vpChatProfile;
    private TabLayout vpSlidingTabStrip;
    private TextView tvChatBack;
    private TextView tvChatUserName;
    private TextView tvUnfriend;
    private TextView tvBlock;
    private TextView tvReport;
    private ImageView imgBtnMenu;
    private RelativeLayout relMenu;

    private String strToUSerId = "";
    private String strChatUSerName = "";
    private String strChatUserPic = "";
    private String is_blocked = "";
    private String is_unfriend = "";
    private int currentItem = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_profile);
        if (getIntent() != null && getIntent().hasExtra(IBundleKey.TO_USER_ID)) {
            strToUSerId = getIntent().getStringExtra(IBundleKey.TO_USER_ID);
            strChatUSerName = getIntent().getStringExtra(IBundleKey.CHAT_USER_NAME);
            strChatUserPic = getIntent().getStringExtra(IBundleKey.CHAT_USER_PIC);
            currentItem = getIntent().getIntExtra(IBundleKey.SET_USER_PROFILE, 0);
            is_blocked = getIntent().getStringExtra(IBundleKey.IS_BLOCKED);
            is_unfriend = getIntent().getStringExtra(IBundleKey.IS_UNFRIEND);
        } else if (getIntent() != null && getIntent().hasExtra("sender_id")) {
            strToUSerId = getIntent().getStringExtra("sender_id");
            currentItem = 0;
        }
        initView();
    }

    private void initView() {
        vpChatProfile = (CustomViewPager) findViewById(R.id.vpChatProfile);
        vpSlidingTabStrip = (TabLayout) findViewById(R.id.vpSlidingTabStrip);
        tvChatBack = (TextView) findViewById(R.id.tvChatBack);
        tvChatUserName = (TextView) findViewById(R.id.tvChatUserName);
        tvUnfriend = (TextView) findViewById(R.id.tvUnfriend);
        tvReport = (TextView) findViewById(R.id.tvReport);
        tvBlock = (TextView) findViewById(R.id.tvBlock);
        imgBtnMenu = (ImageView) findViewById(R.id.imgBtnMenu);
        relMenu = (RelativeLayout) findViewById(R.id.relMenu);
        tvChatUserName.setText(strChatUSerName);


        vpSlidingTabStrip.setTabIndicatorFullWidth(true);

        if (is_unfriend.equals("3")) {
            tvUnfriend.setText("Send friend request");
        } else if (is_unfriend.equals("1")) {
            tvUnfriend.setText("Unfriend " + strChatUSerName);
        }else if (is_unfriend.equals("0")) {
            tvUnfriend.setText("Friend request sent");
        }
        else if (is_unfriend.equals("2")) {
            tvUnfriend.setText("Send friend request");
        }

        if (is_blocked.equals("1")) {
            tvBlock.setText("Un-Block " + strChatUSerName);
        } else {
            tvBlock.setText("Block " + strChatUSerName);
        }

        tvReport.setText("Report " + strChatUSerName);

        tvUnfriend.setOnClickListener(this);
        tvChatBack.setOnClickListener(this);
        tvReport.setOnClickListener(this);
        tvBlock.setOnClickListener(this);
        imgBtnMenu.setOnClickListener(this);
        relMenu.setOnClickListener(this);
        getProfileDetailsAPI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvChatBack:
                Util.hideKeyboard(ChatProfileActivity.this, tvChatBack);
                finish();
                break;

            case R.id.imgBtnMenu:
                relMenu.setVisibility(View.VISIBLE);
                break;


            case R.id.tvUnfriend:
                relMenu.setVisibility(View.GONE);

                if (is_blocked.equals("1")) {
                    Toast.showLongToast(ChatProfileActivity.this, "You can't unfriend because you have been Blocked or " + strChatUSerName + " has been blocked by you");
                } else {
                    if (is_unfriend.equals("3")) {
                        wsFriendStatus("1", "");
                    } else if (is_unfriend.equals("1")) {
                        wsFriendStatus("2", "");
                    }else if (is_unfriend.equals("2")) {
                        wsFriendStatus("1", "");
                    }
                }

                //   startActivity(new Intent(ChatProfileActivity.this, UnfriendActivity.class));
                break;

            case R.id.tvBlock:
                relMenu.setVisibility(View.GONE);

                if (is_unfriend.equals("3")) {
                    Toast.showLongToast(ChatProfileActivity.this, "You can't block or unblock because " + strChatUSerName + " is not in your friend list");
                } else {
                    if (is_blocked.equals("1")) {
                        wsFriendStatus("4", "");
                    } else {
                        wsFriendStatus("3", "");
                    }
                }

                // startActivity(new Intent(ChatProfileActivity.this, BlockActivity.class));
                break;


            case R.id.tvReport:
                relMenu.setVisibility(View.GONE);
                Intent intent = new Intent(ChatProfileActivity.this, ReportActivity.class);
                intent.putExtra(IBundleKey.TO_USER_ID, strToUSerId);
                intent.putExtra(IBundleKey.CHAT_USER_NAME, strChatUSerName);
                intent.putExtra(IBundleKey.OTHER_PROFILE_PIC, strChatUserPic);
                startActivity(intent);
                finish();
                break;

            case R.id.relMenu:
                relMenu.setVisibility(View.GONE);
                break;
        }
    }

    // calling WS for getting friend profile details
    private void getProfileDetailsAPI() {

        final HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, Prefs.getString(ChatProfileActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, strToUSerId);

        new CallNetworkRequest().postResponse(ChatProfileActivity.this, true, "update Eevent", AUTH_VALUE, WSUrl.GET_PROFILE_DETAILS,map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            Gson gson = new Gson();
                            ProfileDetailsResponse profileDetailsResponse = gson.fromJson(response, ProfileDetailsResponse.class);

                            if (profileDetailsResponse.isFlag()) {

                                if (profileDetailsResponse.getUserDetails() != null && profileDetailsResponse.getUserDetails().size() > 0) {
                                    strChatUserPic = profileDetailsResponse.getUserDetails().get(0).getProfilePic();
                                    strChatUSerName = profileDetailsResponse.getUserDetails().get(0).getFirstName() + " " + profileDetailsResponse.getUserDetails().get(0).getLastName();
                                    tvChatUserName.setText(strChatUSerName);
                                    setUpviewPager(profileDetailsResponse.getUserDetails().get(0));
                                }

                            } else {
                                Toast.show(ChatProfileActivity.this, profileDetailsResponse.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Logger.e(TAG, "Error ===> " + error.getErrorBody());
                        Toast.show(ChatProfileActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }


    private void wsFriendStatus(final String status, String report) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, Prefs.getString(ChatProfileActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, strToUSerId);
        map.put(WSKey.STATUS, status);
        map.put(WSKey.REPORT, report);
        map.put(WSKey.INTRO_YOURSELF_TO_FRIEND, "");
        new CallNetworkRequest().postResponse(ChatProfileActivity.this, true, "sendFriendRequest",
                AppContants.AUTH_VALUE, WSUrl.POST_SEND_FRIEND_REQUEST, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            if ((!TextUtils.isEmpty(response))) {
                                ModelSendRequest modelSendRequest = new Gson().fromJson(response, ModelSendRequest.class);
                                if (modelSendRequest != null) {

                                    if (modelSendRequest.isFLAG()) {


                                        if (status.equals("1")) {

                                            Intent intent = new Intent(ChatProfileActivity.this, UnfriendActivity.class);
                                            intent.putExtra(IBundleKey.CHAT_USER_NAME, strChatUSerName);
                                            intent.putExtra(IBundleKey.OTHER_PROFILE_PIC, strChatUserPic);
                                            intent.putExtra(IBundleKey.STATUS, status);
                                            startActivity(intent);
                                            finish();

                                        } else if (status.equals("2")) {

                                            Intent intent = new Intent(ChatProfileActivity.this, UnfriendActivity.class);
                                            intent.putExtra(IBundleKey.CHAT_USER_NAME, strChatUSerName);
                                            intent.putExtra(IBundleKey.OTHER_PROFILE_PIC, strChatUserPic);
                                            intent.putExtra(IBundleKey.STATUS, status);
                                            startActivity(intent);
                                            finish();
                                        } else if (status.equals("3")) {

                                            Intent intent = new Intent(ChatProfileActivity.this, BlockActivity.class);
                                            intent.putExtra(IBundleKey.TO_USER_ID, strToUSerId);
                                            intent.putExtra(IBundleKey.CHAT_USER_NAME, strChatUSerName);
                                            intent.putExtra(IBundleKey.OTHER_PROFILE_PIC, strChatUserPic);
                                            intent.putExtra(IBundleKey.STATUS, "4");
                                            startActivity(intent);
                                            finish();
                                        } else if (status.equals("4")) {

                                            Intent intent = new Intent(ChatProfileActivity.this, BlockActivity.class);
                                            intent.putExtra(IBundleKey.TO_USER_ID, strToUSerId);
                                            intent.putExtra(IBundleKey.CHAT_USER_NAME, strChatUSerName);
                                            intent.putExtra(IBundleKey.OTHER_PROFILE_PIC, strChatUserPic);
                                            intent.putExtra(IBundleKey.OTHER_PROFILE_PIC, strChatUserPic);
                                            intent.putExtra(IBundleKey.STATUS, "3");
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        Toast.show(ChatProfileActivity.this, modelSendRequest.getMESSAGE());
                                    }

                                } else {
                                    Toast.show(ChatProfileActivity.this, getString(R.string.error_contact_server));
                                }
                            } else {
                                Toast.show(ChatProfileActivity.this, getString(R.string.error_contact_server));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.show(ChatProfileActivity.this, getString(R.string.error_contact_server));
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(ChatProfileActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }

    private void setUpviewPager(UserDetailsItem userDetailsItem) {

        vpChatProfile.setAdapter(new ChatProfileAdapter(getSupportFragmentManager(), ChatProfileActivity.this, strChatUserPic, strToUSerId, strChatUSerName, is_blocked, is_unfriend, userDetailsItem));
        // Attach the view pager to the tab strip
        vpSlidingTabStrip.setupWithViewPager(vpChatProfile);

        vpChatProfile.setCurrentItem(currentItem);


    }

    @Override
    public void onBackPressed() {
        Util.hideKeyboard(ChatProfileActivity.this, vpChatProfile);
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.isChatProfileActivityVisible = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        Util.isChatProfileActivityVisible = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Util.isChatProfileActivityVisible = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Util.isChatProfileActivityVisible = false;
        new CallNetworkRequest().hideProgressDialog();
    }
}
