package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.BuyOrderTabFragment;
import com.shishiTec.HiMaster.UI.fragment.UserCenter.CardPackageContentFragment;
import com.shishiTec.HiMaster.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CardPackageActivity extends BaseActivity {
    private SlidingTabLayout tl_card_package;
    private ViewPager vp_card_packager;
    private BuyOrderTabFragment buyOrderTabFragment;
    private List<String> strings;
    private ImageView left_back_card;

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
        Intent intent = getIntent();
        String m = intent.getStringExtra("num");
        left_back_card = (ImageView) findViewById(R.id.left_back);
        left_back_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardPackageActivity.this.finish();
            }
        });
        strings = new ArrayList<>();
        strings.add("代金券");
        strings.add("M点");
        strings.add("酱油卡");
        tl_card_package = (SlidingTabLayout) findViewById(R.id.tl_card_package);
        vp_card_packager = (ViewPager) findViewById(R.id.vp_card_packager);
        List<Fragment> list = new ArrayList<>();
        //分类列表
        if (strings != null && strings.size() > 0) {
            for (int i = 0; i < strings.size(); i++) {
                list.add(CardPackageContentFragment.newInstance(strings.get(i),m));
            }
            buyOrderTabFragment = new BuyOrderTabFragment(getSupportFragmentManager(), list, strings);
            vp_card_packager.setAdapter(buyOrderTabFragment);
            vp_card_packager.setOffscreenPageLimit(2);
            tl_card_package.setViewPager(vp_card_packager);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_card_package;
    }
}
