package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by hyu on 2016/4/22.
 */
public class BuyOrderTabFragment extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public BuyOrderTabFragment(FragmentManager fragmentManager, List<Fragment> fragments, List<String> titles){
        super(fragmentManager);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public List<Fragment> getFragments(){
        return this.fragments;
    }

}
