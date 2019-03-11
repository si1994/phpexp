package com.quirktastic.sendrequest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.quirktastic.R;
import com.quirktastic.chat.fragment.ChatFragment;
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

public class SendRequestActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = SendRequestActivity.class.getName();

    private AppCompatTextView tvIntroduceYourSelf;
    private TextView tvFriendRequestSend;
    private TextView tvIntroCount;
    private CircleImageView ivOtherUserImage;
    private EditText edtIntroduce;
    private LinearLayout llCancelRequest;

    private String strToUserId = "";
    private String strProfilePic = "";
    private String firstName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);
        if (getIntent() != null && getIntent().hasExtra(IBundleKey.TO_USER_ID)) {
            strToUserId = getIntent().getStringExtra(IBundleKey.TO_USER_ID);
            strProfilePic = getIntent().getStringExtra(IBundleKey.OTHER_PROFILE_PIC);
            firstName = getIntent().getStringExtra(IBundleKey.FIRST_NAME);
        }
        initView();
    }

    private void initView() {
        ivOtherUserImage = (CircleImageView) findViewById(R.id.ivOtherUserImage);
        tvIntroduceYourSelf = (AppCompatTextView) findViewById(R.id.tvIntroduceYourSelf);
        tvFriendRequestSend = (TextView) findViewById(R.id.tvFriendRequestSend);
        tvIntroCount = (TextView) findViewById(R.id.tvIntroCount);
        edtIntroduce = (EditText) findViewById(R.id.edtIntroduce);
        edtIntroduce.setFilters(new InputFilter[]{new InputFilter.LengthFilter(150)});
        llCancelRequest = (LinearLayout) findViewById(R.id.llCancelRequest);
        tvIntroduceYourSelf.setText(getString(R.string.introduce_yourself) + " " + firstName);
        edtIntroduce.setHint("Hi, " + firstName + "!" + " I really Like");
        tvFriendRequestSend.setOnClickListener(this);
        llCancelRequest.setOnClickListener(this);

        Glide.with(SendRequestActivity.this)
                .load(strProfilePic)
                .apply(RequestOptions
                        .placeholderOf(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivOtherUserImage);

        edtIntroduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvIntroCount.setText(String.valueOf(s.length()) + "/150");
            }

            @Override
            public void afterTextChanged(Editable s) {
//                tvIntroCount.setText(String.valueOf(150-s.length())+"/150");
            }
        });

    }

    @Override
    public void onClick(View view) {
        Util.hideKeyboard(SendRequestActivity.this, view);
        switch (view.getId()) {
            case R.id.tvFriendRequestSend:
                if (!TextUtils.isEmpty(edtIntroduce.getText().toString().trim())) {
                    sendFriendRequest();
                } else {
                    Toast.show(SendRequestActivity.this, getString(R.string.please_introduce_yourself));
                }

                break;
            case R.id.llCancelRequest:
                finish();
                break;
            default:
                break;
        }
    }

    private void sendFriendRequest() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, Prefs.getString(SendRequestActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, strToUserId);
        map.put(WSKey.STATUS, "1");
        map.put(WSKey.INTRO_YOURSELF_TO_FRIEND, Util.gifEncode(edtIntroduce.getText().toString().trim()));
        new CallNetworkRequest().postResponse(SendRequestActivity.this, true, "sendFriendRequest",
                AppContants.AUTH_VALUE, WSUrl.POST_SEND_FRIEND_REQUEST, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            if ((!TextUtils.isEmpty(response))) {
                                ModelSendRequest modelSendRequest = new Gson().fromJson(response, ModelSendRequest.class);
                                if (modelSendRequest != null) {
                                    modelSendRequest.getMESSAGE();
                                    Intent intent = new Intent(SendRequestActivity.this, SentRequestActivity.class);
                                    intent.putExtra(IBundleKey.OTHER_PROFILE_PIC, strProfilePic);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.show(SendRequestActivity.this, getString(R.string.error_contact_server));
                                }
                            } else {
                                Toast.show(SendRequestActivity.this, getString(R.string.error_contact_server));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.show(SendRequestActivity.this, getString(R.string.error_contact_server));
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(SendRequestActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Util.hideKeyboard(SendRequestActivity.this, edtIntroduce);
        super.onBackPressed();
    }

    private class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContants.IS_SEND_FRIEND_REQUEST=true;
        new CallNetworkRequest().hideProgressDialog();
    }



}
