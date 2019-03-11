package com.quirktastic.chat;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
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

public class ReportActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ReportActivity.class.getName();

    private AppCompatTextView tvIntroduceYourSelf;
    private TextView tvSendReport;
    private CircleImageView ivOtherUserImage;
    private EditText edtIntroduce;
    private LinearLayout llCancelRequest;

    private String strToUserId = "";
    private String strProfilePic = "";
    private String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        if (getIntent() != null && getIntent().hasExtra(IBundleKey.TO_USER_ID)) {
            strToUserId = getIntent().getStringExtra(IBundleKey.TO_USER_ID);
            strProfilePic = getIntent().getStringExtra(IBundleKey.OTHER_PROFILE_PIC);
            userName = getIntent().getStringExtra(IBundleKey.CHAT_USER_NAME);
        }
        initView();
    }

    private void initView() {
        ivOtherUserImage = (CircleImageView) findViewById(R.id.ivOtherUserImage);
        tvIntroduceYourSelf = (AppCompatTextView) findViewById(R.id.tvIntroduceYourSelf);
        tvSendReport = (TextView) findViewById(R.id.tvSendReport);
        edtIntroduce = (EditText) findViewById(R.id.edtIntroduce);
        edtIntroduce.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        llCancelRequest = (LinearLayout) findViewById(R.id.llCancelRequest);
        tvSendReport.setText("Report "+userName);
        tvSendReport.setOnClickListener(this);
        llCancelRequest.setOnClickListener(this);

        Glide.with(ReportActivity.this)
                .load(strProfilePic)
                .apply(RequestOptions
                        .placeholderOf(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivOtherUserImage);

    }

    @Override
    public void onClick(View view) {
        Util.hideKeyboard(ReportActivity.this, view);
        switch (view.getId()) {
            case R.id.tvSendReport:
                if(!TextUtils.isEmpty(edtIntroduce.getText().toString().trim())){
                    sendReport();
                }else{
                    Toast.show(ReportActivity.this,getString(R.string.please_report));
                }
                break;
            case R.id.llCancelRequest:
                finish();
                break;
            default:
                break;
        }
    }

    private void sendReport() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(WSKey.FROM_USER_ID, Prefs.getString(ReportActivity.this, PrefsKey.USER_ID, ""));
        map.put(WSKey.TO_USER_ID, strToUserId);
        map.put(WSKey.STATUS, "5");
        map.put(WSKey.REPORT, edtIntroduce.getText().toString().trim());
        new CallNetworkRequest().postResponse(ReportActivity.this, true, "sendFriendRequest",
                AppContants.AUTH_VALUE, WSUrl.POST_SEND_FRIEND_REQUEST, map,
                new INetworkResponse() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            if ((!TextUtils.isEmpty(response))) {
                                ModelSendRequest modelSendRequest = new Gson().fromJson(response, ModelSendRequest.class);
                                if (modelSendRequest != null) {

                                    if (modelSendRequest.isFLAG()) {
                                        Toast.show(ReportActivity.this, modelSendRequest.getMESSAGE());
                                        finish();
                                    } else {
                                        Toast.show(ReportActivity.this, modelSendRequest.getMESSAGE());
                                        finish();
                                    }

                                } else {
                                    Toast.show(ReportActivity.this, getString(R.string.error_contact_server));
                                }
                            } else {
                                Toast.show(ReportActivity.this, getString(R.string.error_contact_server));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.show(ReportActivity.this, getString(R.string.error_contact_server));
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.show(ReportActivity.this, getString(R.string.error_contact_server));
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Util.hideKeyboard(ReportActivity.this, edtIntroduce);
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
        new CallNetworkRequest().hideProgressDialog();
    }

}
