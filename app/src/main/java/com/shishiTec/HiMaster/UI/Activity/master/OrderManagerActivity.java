package com.shishiTec.HiMaster.UI.Activity.master;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.flyco.tablayout.SlidingTabLayout;
import com.shishiTec.HiMaster.Model.bean.MasterOrderBean;
import com.shishiTec.HiMaster.Model.bean.MasterStartBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.fragment.master.OrderManagerFragment;
import com.shishiTec.HiMaster.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderManagerActivity extends BaseActivity {
    private ViewPager vp_order_manager;
    private SlidingTabLayout st_order_manager;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        vp_order_manager = (ViewPager) findViewById(R.id.vp_order_manager);
        st_order_manager = (SlidingTabLayout) findViewById(R.id.st_order_manager);
        //状态 -1、全部 0、待验单 4、待接单 5、已拒单
        Bundle bundle = new Bundle();
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        List<String> list = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();
        list.add("全部");
        list.add("待验单");
        list.add("待接单");
        list.add("已拒单");

        bundle.putString("orderStatus","-1");
        OrderManagerFragment orderManagerFragment = new OrderManagerFragment();
        orderManagerFragment.setArguments(bundle);
        bundle1.putString("orderStatus","0");
        OrderManagerFragment orderManagerFragment2 = new OrderManagerFragment();
        orderManagerFragment2.setArguments(bundle1);
        bundle2.putString("orderStatus","4");
        OrderManagerFragment orderManagerFragment3 = new OrderManagerFragment();
        orderManagerFragment3.setArguments(bundle2);
        bundle3.putString("orderStatus","5");
        OrderManagerFragment orderManagerFragment4 = new OrderManagerFragment();
        orderManagerFragment4.setArguments(bundle3);

        fragmentList.add(orderManagerFragment);
        fragmentList.add(orderManagerFragment2);
        fragmentList.add(orderManagerFragment3);
        fragmentList.add(orderManagerFragment4);

        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(),fragmentList,list);
        vp_order_manager.setAdapter(tabFragmentAdapter);
        st_order_manager.setViewPager(vp_order_manager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_manager;
    }

    public class TabFragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;
        private List<String> titles;

        public TabFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragments, List<String> titles){
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
}
