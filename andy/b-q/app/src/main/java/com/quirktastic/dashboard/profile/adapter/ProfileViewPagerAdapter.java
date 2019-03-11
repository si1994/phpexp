package com.quirktastic.dashboard.profile.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.quirktastic.dashboard.profile.EditProfileFragment;
import com.quirktastic.dashboard.profile.ViewProfileFragment;
import com.quirktastic.dashboard.profile.model.profiledetails.UserDetailsItem;

import java.lang.ref.WeakReference;
import java.util.Hashtable;

//adapter for appointments view pager
public class ProfileViewPagerAdapter extends FragmentStatePagerAdapter {
    protected Hashtable<Integer, WeakReference<Fragment>> fragmentReferences;
    private UserDetailsItem userDetailsItem;

    public ProfileViewPagerAdapter(FragmentManager fm, UserDetailsItem userDetailsItem) {
        super(fm);
        this.userDetailsItem = userDetailsItem;
        fragmentReferences = new Hashtable<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {

            case 0:
                fragment = EditProfileFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable("userDetailsItem", userDetailsItem);
                fragment.setArguments(bundle);
                break;

            case 1:
                fragment = ViewProfileFragment.newInstance();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("userDetailsItem", userDetailsItem);
                fragment.setArguments(bundle2);
                break;

            default:
                fragment = new EditProfileFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("userDetailsItem", userDetailsItem);
                fragment.setArguments(bundle3);
                break;
        }
        fragmentReferences.put(position, new WeakReference<Fragment>(fragment));

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }
}
