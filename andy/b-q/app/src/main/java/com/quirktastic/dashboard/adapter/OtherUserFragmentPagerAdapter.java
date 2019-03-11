package com.quirktastic.dashboard.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.quirktastic.dashboard.fragment.OtherUserDataFragment;
import com.quirktastic.dashboard.model.modeluserlist.USERLISTItem;

import java.util.ArrayList;
import java.util.List;

public class OtherUserFragmentPagerAdapter extends FragmentPagerAdapter {


    private List<OtherUserDataFragment> myFragments;
    private ArrayList<USERLISTItem> usetListItem;

    public OtherUserFragmentPagerAdapter(FragmentManager fm, List<OtherUserDataFragment> myFragments, Context context,ArrayList<USERLISTItem> usetListItem) {
        super(fm);
        this.myFragments = myFragments;
        this.usetListItem = usetListItem;
    }

    @Override
    public Fragment getItem(int i) {
        OtherUserDataFragment otherUserDataFragment = myFragments.get(i);

        Bundle bundle = new Bundle();
        bundle.putParcelable("userlistItem",usetListItem.get(i) );
// set Fragmentclass Arguments
        otherUserDataFragment.setArguments(bundle);

        return otherUserDataFragment;
    }

    @Override
    public int getCount() {
        return myFragments.size();
    }

}
