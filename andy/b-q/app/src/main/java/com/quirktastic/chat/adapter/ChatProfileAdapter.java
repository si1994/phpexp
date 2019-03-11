package com.quirktastic.chat.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.quirktastic.R;
import com.quirktastic.chat.fragment.ChatFragment;
import com.quirktastic.chat.fragment.ChatProfileFragment;
import com.quirktastic.dashboard.profile.model.profiledetails.UserDetailsItem;
import com.quirktastic.utility.IBundleKey;

import java.util.ArrayList;

/**
 * @author Deepak
 * @created on Wed,Nov,2018
 * @updated on $file.lastModified
 */
public class ChatProfileAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private Fragment fragment;
    private ArrayList<String> tabTitles;
    private String userId;
    private String strChatUSerName;
    private String strChatUserPic;
    private String isBlocked;
    private String isUnfriend;
    private UserDetailsItem userDetailsItem;

    public ChatProfileAdapter(FragmentManager fm, Context context, String strChatUserPic, String userId,
                              String strChatUSerName,
                              String isBlocked,String isUnfriend,
                              UserDetailsItem userDetailsItem) {
        super(fm);
        tabTitles = new ArrayList();
        tabTitles.add(context.getString(R.string.chat));
        tabTitles.add(context.getString(R.string.profile));
        this.userDetailsItem = userDetailsItem;
        this.userId = userId;
        this.strChatUSerName = strChatUSerName;
        this.strChatUserPic = strChatUserPic;
        this.isBlocked = isBlocked;
        this.isUnfriend = isUnfriend;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {

            Bundle bundle = new Bundle();
            bundle.putString(IBundleKey.TO_USER_ID, userId);
            bundle.putString(IBundleKey.CHAT_USER_NAME, strChatUSerName);
            bundle.putString(IBundleKey.CHAT_USER_PIC, strChatUserPic);
            bundle.putString(IBundleKey.IS_BLOCKED, isBlocked);
            bundle.putString(IBundleKey.IS_UNFRIEND, isUnfriend);
            fragment = new ChatFragment();
            fragment.setArguments(bundle);

        } else if (i == 1) {

            fragment = new ChatProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putString(IBundleKey.TO_USER_ID, userId);
            bundle.putSerializable("userDetailsItem", userDetailsItem);
            fragment.setArguments(bundle);

        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles.get(position);
    }
}
