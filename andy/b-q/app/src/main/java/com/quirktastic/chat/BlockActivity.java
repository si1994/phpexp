package com.quirktastic.chat;

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
import com.quirktastic.sendrequest.model.ModelSendRequest;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.IBundleKey;
import com.quirktastic.utility.Prefs;
import com.quirktastic.utility.PrefsKey;
import com.quirktastic.utility.Toast;
import com.quirktastic.utility.Util;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class BlockActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = BlockActivity.class.getName();

    private AppCompatTextView tvIntroduceYourSelf;
    private TextView tvBlockOrUnblock;
    private CircleImageView ivOtherUserImage;

    private String strToUserId = "";
    private String strProfilePic = "";
    private String userName = "";
    private String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);
        if (getIntent() != null && getIntent().hasExtra(IBundleKey.TO_USER_ID)) {
            strToUserId = getIntent().getStringExtra(IBundleKey.TO_USER_ID);
            strProfilePic = getIntent().getStringExtra(IBundleKey.OTHER_PROFILE_PIC);
            userName = getIntent().getStringExtra(IBundleKey.CHAT_USER_NAME);
            status = getIntent().getStringExtra(IBundleKey.STATUS);
        }
        initView();
    }

    private void initView() {
        ivOtherUserImage = (CircleImageView) findViewById(R.id.ivOtherUserImage);
        tvIntroduceYourSelf = (AppCompatTextView) findViewById(R.id.tvIntroduceYourSelf);
        tvBlockOrUnblock = (TextView) findViewById(R.id.tvBlockOrUnblock);

        if(status.equals("4"))
        {
            tvIntroduceYourSelf.setText(userName + " has been blocked");
            tvBlockOrUnblock.setText("Un-block "+userName);

        }
        else if(status.equals("3")){
            tvIntroduceYourSelf.setText(userName + " has been Un-blocked");
            tvBlockOrUnblock.setText("Block "+userName);
        }

        tvBlockOrUnblock.setOnClickListener(this);

        Glide.with(BlockActivity.this)
                .load(strProfilePic)
                .apply(RequestOptions
                        .placeholderOf(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivOtherUserImage);

    }

    @Override
    public void onClick(View view) {
        Util.hideKeyboard(BlockActivity.this, view);
        switch (view.getId()) {

            case R.id.tvBlockOrUnblock:
                /*if(!TextUtils.isEmpty(edtIntroduce.getText().toString().trim())){
                    sendFriendRequest();
                }else{
                    Toast.show(SendRequestActivity.this,getString(R.string.please_introduce_yourself));
                }*/

                sendFriendRequest(status);

                break;
            default:
                break;
        }
    }

    private void sendFriendRequest(String status) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, Prefs.getString(BlockActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, strToUserId);
        map.put(WSKey.STATUS, status);
        new CallNetworkRequest().postResponse(BlockActivity.this, true, "sendFriendRequest",
                AppContants.AUTH_VALUE, WSUrl.POST_SEND_FRIEND_REQUEST, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            if ((!TextUtils.isEmpty(response))) {
                                ModelSendRequest modelSendRequest = new Gson().fromJson(response, ModelSendRequest.class);
                                if (modelSendRequest.isFLAG()) {
                                    Toast.show(BlockActivity.this, modelSendRequest.getMESSAGE());
                                    finish();
                                } else {
                                    Toast.show(BlockActivity.this, modelSendRequest.getMESSAGE());
                                    finish();
                                }
                            } else {
                                Toast.show(BlockActivity.this, getString(R.string.error_contact_server));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.show(BlockActivity.this, getString(R.string.error_contact_server));
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(BlockActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new CallNetworkRequest().hideProgressDialog();
    }
}
