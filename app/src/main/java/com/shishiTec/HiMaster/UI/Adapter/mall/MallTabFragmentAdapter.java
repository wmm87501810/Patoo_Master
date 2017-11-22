package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shishiTec.HiMaster.Model.bean.MasterStartBean;

import java.util.List;

/**
 * Created by hyu on 2016/4/22.
 */
public class MallTabFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<MasterStartBean.CategoryListBean> titles;

    public MallTabFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragments, List<MasterStartBean.CategoryListBean> titles){
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
        return titles.get(position).getName();
    }

    public List<Fragment> getFragments(){
        return this.fragments;
    }

}
