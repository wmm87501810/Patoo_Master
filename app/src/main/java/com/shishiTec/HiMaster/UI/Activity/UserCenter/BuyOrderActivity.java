package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.BuyOrderTabFragment;
import com.shishiTec.HiMaster.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

//import com.shishiTec.HiMaster.UI.Fragment.UserCenter.BuyOrderContentFragment;

public class BuyOrderActivity extends BaseActivity {
    private SlidingTabLayout tl_buy_order;
    private ViewPager vp_buy_order;
    private BuyOrderTabFragment buyOrderTabFragment;
    private List<String> strings;
    private ImageView left_back;

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
        left_back = (ImageView) findViewById(R.id.left_back);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuyOrderActivity.this.finish();
            }
        });
        strings = new ArrayList<>();
        strings.add("全部");
        strings.add("待接单");
        strings.add("待上课");
        strings.add("待评价");
        strings.add("退款");
        tl_buy_order = (SlidingTabLayout) findViewById(R.id.tl_buy_order);
        vp_buy_order = (ViewPager) findViewById(R.id.vp_buy_order);
        List<Fragment> list = new ArrayList<>();
        //分类列表
        if (strings != null && strings.size() > 0) {
            for (int i = 0; i < strings.size(); i++) {
                list.add(com.shishiTec.HiMaster.UI.fragment.UserCenter.BuyOrderContentFragment.newInstance(strings.get(i)));
            }
            buyOrderTabFragment = new BuyOrderTabFragment(getSupportFragmentManager(), list, strings);
            vp_buy_order.setAdapter(buyOrderTabFragment);
            vp_buy_order.setOffscreenPageLimit(0);
            tl_buy_order.setViewPager(vp_buy_order);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_order;
    }
}
