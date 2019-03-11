package com.quirktastic.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.core.BaseActivity;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.network.INetworkResponse;
import com.quirktastic.network.WSKey;
import com.quirktastic.network.WSUrl;
import com.quirktastic.sendrequest.SentRequestActivity;
import com.quirktastic.sendrequest.model.ModelSendRequest;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.IBundleKey;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UnfriendActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = UnfriendActivity.class.getName();

    private AppCompatTextView tvIntroduceYourSelf;
    private TextView tvFriendRequestSend;
    private CircleImageView ivOtherUserImage;

    private String strToUserId = "";
    private String strProfilePic = "";
    private String userName = "";
    private String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfriend);
        if (getIntent() != null) {
            strProfilePic = getIntent().getStringExtra(IBundleKey.OTHER_PROFILE_PIC);
            userName = getIntent().getStringExtra(IBundleKey.CHAT_USER_NAME);
            status = getIntent().getStringExtra(IBundleKey.STATUS);
        }
        initView();
    }

    private void initView() {
        ivOtherUserImage = (CircleImageView) findViewById(R.id.ivOtherUserImage);
        tvIntroduceYourSelf = (AppCompatTextView) findViewById(R.id.tvIntroduceYourSelf);
        tvFriendRequestSend = (TextView) findViewById(R.id.tvFriendRequestSend);
        if(status.equals("1"))
        {
            tvIntroduceYourSelf.setText("Friend request sent to "+ userName);
        }
        else if(status.equals("2")){
            tvIntroduceYourSelf.setText(userName + " has been removed as a friend");
        }

        tvFriendRequestSend.setOnClickListener(this);
        Glide.with(UnfriendActivity.this)
                .load(strProfilePic)
                .apply(RequestOptions
                        .placeholderOf(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivOtherUserImage);

    }

    @Override
    public void onClick(View view) {
        Util.hideKeyboard(UnfriendActivity.this, view);
        switch (view.getId()) {
            case R.id.tvFriendRequestSend:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }

}
