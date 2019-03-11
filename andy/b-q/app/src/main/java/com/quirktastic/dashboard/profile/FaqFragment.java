package com.quirktastic.dashboard.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quirktastic.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FaqFragment extends Fragment {


    @BindView(R.id.tvFaq)
    TextView tvFaq;
    @BindView(R.id.imgBack)
    ImageView imgBack;

    public FaqFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }


    private void init() {
        tvFaq.setText(Html.fromHtml("<h1><i>Lorem Ipsum?</i></h1></br><p><strong>Lorem Ipsum</strong> " +
                "is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's " +
                "standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it " +
                "to make a type specimen book. It has survived not only five centuries, but also the leap into electronic " +
                "typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset " +
                "sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus " +
                "PageMaker including versions of Lorem Ipsum.</p>"));
    }


    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }
}
