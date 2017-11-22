package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.FragmentAdapter;
import com.shishiTec.HiMaster.UI.fragment.OrderStateFragment;
import com.shishiTec.HiMaster.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class UserOrderActivity extends BaseActivity {

    @Bind(R.id.top_title)
    TextView topTitle;
    @Bind(R.id.right_title)
    TextView rightTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        initTop();
        List<String> titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.all));
        titles.add(getResources().getString(R.string.wait_accept_order));
        titles.add(getResources().getString(R.string.wait_study));
        titles.add(getResources().getString(R.string.wait_evaluate));
        titles.add(getResources().getString(R.string.refund));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(4)));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OrderStateFragment(""));
        fragments.add(new OrderStateFragment("4"));
        fragments.add(new OrderStateFragment("0"));
        fragments.add(new OrderStateFragment("1"));
        fragments.add(new OrderStateFragment("5"));
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

    }

    private void initTop() {
        topTitle.setText(R.string.my_course_order);
        rightTitle.setText(R.string.custom_service);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
        toolbar.setNavigationIcon(R.mipmap.finish);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_order;
    }
}
