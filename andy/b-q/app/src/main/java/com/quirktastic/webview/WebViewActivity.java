package com.quirktastic.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.quirktastic.R;
import com.quirktastic.network.ProgressDialog;
import com.quirktastic.network.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.lavNoData)
    LottieAnimationView lavNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.llNoData)
    RelativeLayout llNoData;
    @BindView(R.id.webView)
    WebView webView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        if (Utility.isInternetOn(WebViewActivity.this)) {
            hideNodataFound();
            Intent intent = getIntent();
            String url = intent.getStringExtra("url");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new AppWebViewClients());
            webView.loadUrl(url);
        } else {
            showNodataFound("Internet seems to be offline.", "no_internet_connection.json");
        }
    }


    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        finish();
    }

    public void showNodataFound(String textMsg, String iconMsg) {
        llNoData.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        lavNoData.setAnimation(iconMsg);
        tvNoData.setText(textMsg);
        lavNoData.playAnimation();
        lavNoData.loop(true);


    }

    public void hideNodataFound() {
        llNoData.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        lavNoData.cancelAnimation();

    }

    public class AppWebViewClients extends WebViewClient {

        public AppWebViewClients() {
            showProgressDialog(WebViewActivity.this);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            hideProgressDialog();

        }
    }

    private void showProgressDialog(Context context) {
        if (progressDialog != null && !progressDialog.isShowing() && !((Activity) context).isFinishing()) {
            progressDialog.show();
        } else if (!((Activity) context).isFinishing()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();//if it's not webview in your case then add the method name you want pause when user device is pause
    }


    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();//same as here if it's not webview then add the method name you want to resume when user resume their device
    }
}
