package com.quirktastic.sendrequest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.quirktastic.R;
import com.quirktastic.network.CallNetworkRequest;
import com.quirktastic.onboard.custom.GradientTextView;
import com.quirktastic.utility.IBundleKey;
import com.quirktastic.utility.Util;

import de.hdodenhof.circleimageview.CircleImageView;

public class SentRequestActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SentRequestActivity.class.getName();
    private TextView tvKeepBrowsing;
    private CircleImageView ivSentUserImage;

    private String strProfilePic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_request);
        strProfilePic = getIntent().getStringExtra(IBundleKey.OTHER_PROFILE_PIC);
        Util.loadNextRequest = true;
        initView();
    }

    private void initView() {
        tvKeepBrowsing = (TextView) findViewById(R.id.tvKeepBrowsing);
        ivSentUserImage = (CircleImageView) findViewById(R.id.ivSentUserImage);
        tvKeepBrowsing.setOnClickListener(this);

        Glide.with(SentRequestActivity.this)
                .load(strProfilePic)
                .apply(RequestOptions
                        .placeholderOf(R.drawable.img_placeholder)
                        .error(R.drawable.img_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(200, 200))
                .into(ivSentUserImage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvKeepBrowsing:
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
