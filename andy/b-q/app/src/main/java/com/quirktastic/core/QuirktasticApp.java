package com.quirktastic.core;

import android.support.multidex.MultiDexApplication;

import com.androidnetworking.AndroidNetworking;
import com.bumptech.glide.request.target.ViewTarget;
import com.quirktastic.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class QuirktasticApp extends MultiDexApplication {
    private static final String TAG = QuirktasticApp.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);

        //initialize OkHttpClient for network connections
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);


       /* Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        Fabric.with(this, crashlyticsKit);*/


    }
}
