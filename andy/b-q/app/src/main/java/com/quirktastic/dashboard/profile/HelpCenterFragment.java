package com.quirktastic.dashboard.profile;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quirktastic.R;
import com.quirktastic.onboard.custom.GradientTextView;
import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.Toast;
import com.quirktastic.webview.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpCenterFragment extends Fragment {

    @BindView(R.id.tvHelpCenter)
    TextView tvHelpCenter;
    @BindView(R.id.llFaq)
    LinearLayout llFaq;
    @BindView(R.id.tvContactSupport)
    TextView tvContactSupport;
    Unbinder unbinder;
    @BindView(R.id.tvBack)
    TextView tvBack;
    private View rootView;

    public HelpCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_help_center, container, false);
        // Inflate the layout for this fragment
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @OnClick({R.id.tvContactSupport, R.id.llFaq, R.id.tvBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvContactSupport:

                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setType("message/rfc822");
                i.setData(Uri.parse("mailto:" + AppContants.CONTECT_SUPPORT_EMAIL));
//                i.putExtra(Intent.EXTRA_EMAIL, new String[]{AppContants.CONTECT_SUPPORT_EMAIL});
                i.putExtra(Intent.EXTRA_SUBJECT, "Contact Support");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (ActivityNotFoundException ex) {
                    Toast.show(getActivity(), "There are no email clients installed.");

                }

                break;

            case R.id.llFaq:
                Intent intent=new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("url",AppContants.FAQ_LINK);
                startActivity(intent);
                break;
            case R.id.tvBack:
                getActivity().onBackPressed();
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
