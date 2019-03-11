package com.quirktastic.dashboard.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.quirktastic.R;
import com.quirktastic.core.BaseFragment;
import com.quirktastic.dashboard.DashboardActivity;
import com.quirktastic.network.ProgressDialog;
import com.quirktastic.network.Utility;
import com.quirktastic.utility.AppContants;
import com.quirktastic.webview.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends BaseFragment {
    public static final String TAG = SocialFragment.class.getName();
    @BindView(R.id.lavNoData)
    LottieAnimationView lavNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.llNoData)
    RelativeLayout llNoData;
    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.pbWeb)
    LinearLayout pbWeb;

    Unbinder unbinder;


    private DashboardActivity dashboardActivity;
    private View rootView;
   // private ProgressDialog progressDialog;

    public SocialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardActivity = (DashboardActivity) getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_web_view, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {

        if (Utility.isInternetOn(dashboardActivity)) {
            hideNodataFound();
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.setWebViewClient(new AppWebViewClients());
            webView.loadUrl(AppContants.URL_SOCIAL);
        } else {
            showNodataFound("Internet seems to be offline.", "no_internet_connection.json");
        }
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
            showProgressDialog(dashboardActivity);
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

            try {
                webView.loadUrl("javascript:(function(){ document.body.style.paddingBottom = '60px'})();");
            } catch (Exception e) {
                e.printStackTrace();
            }

            hideProgressDialog();
        }
    }

    private void showProgressDialog(Context context) {
       /* if (progressDialog != null && !progressDialog.isShowing() && !((Activity) context).isFinishing()) {
            progressDialog.show();
        } else if (!((Activity) context).isFinishing()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.show();
        }*/

        if(isVisible())
        {
            pbWeb.setVisibility(View.VISIBLE);
        }

    }

    private void hideProgressDialog() {
        /*if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }*/
        if(isVisible()) {
            pbWeb.setVisibility(View.GONE);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideProgressDialog();
        webView.stopLoading();
        unbinder.unbind();
    }
}
