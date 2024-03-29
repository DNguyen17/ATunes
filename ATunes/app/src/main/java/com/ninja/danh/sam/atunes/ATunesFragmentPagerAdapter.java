package com.ninja.danh.sam.atunes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Danh on 12/9/2015.
 */
public class ATunesFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Search", "Favorites"};
    private Context context;
    private FragmentManager fm;

    public ATunesFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fm = fm;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return PageFragment.newInstance(position + 1);
        else {
            FavePageFragment frag = (FavePageFragment) FavePageFragment.newInstance(position + 1);
            //frag.notifyChange();
            return frag;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }


}