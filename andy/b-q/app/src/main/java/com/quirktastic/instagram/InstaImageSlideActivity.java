package com.quirktastic.instagram;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.quirktastic.R;
import com.quirktastic.instagram.adapter.InstaImagePagerAdapter;
import com.quirktastic.view.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.quirktastic.utility.AppContants.INSTA_CURRENT_PAGER_ITEM;
import static com.quirktastic.utility.AppContants.INSTA_FEED_LIST;

public class InstaImageSlideActivity extends AppCompatActivity {

    @BindView(R.id.vpInstaImage)
    CustomViewPager vpInstaImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insta_image_slide);
        ButterKnife.bind(this);
        initPager();
    }

    private void initPager() {

        if (INSTA_FEED_LIST != null) {
            InstaImagePagerAdapter instaImagePagerAdapter = new InstaImagePagerAdapter(InstaImageSlideActivity.this, INSTA_FEED_LIST);
            vpInstaImage.setAdapter(instaImagePagerAdapter);
            vpInstaImage.setCurrentItem(INSTA_CURRENT_PAGER_ITEM);
        }

    }
}
