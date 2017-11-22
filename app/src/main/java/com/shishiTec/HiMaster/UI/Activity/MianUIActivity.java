package com.shishiTec.HiMaster.UI.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.Accouont.MyCenter;
import com.shishiTec.HiMaster.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by 83802 on 2017/8/14.
 */

public class MianUIActivity extends BaseActivity {
    @Bind(R.id.top_left_back_btn)
    ImageButton topLeftBackBtn;
    @Bind(R.id.top_title)
    TextView topTitle;
    @Bind(R.id.top_center)
    TextView topCenter;
    @Bind(R.id.right_title)
    TextView rightTitle;
    @Bind(R.id.iv_papa)
    ImageView ivPapa;
    @Bind(R.id.iv_status)
    ImageView ivStatus;
    @Bind(R.id.iv_faxian)
    ImageView ivFaxian;
    @Bind(R.id.iv_shequ)
    ImageView ivShequ;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        topTitle.setVisibility(View.GONE);
        rightTitle.setVisibility(View.GONE);
        topLeftBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //个人中心
                startActivity(new Intent(MianUIActivity.this,MyCenter.class));
            }

        });
        ivFaxian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发现
                startActivity(new Intent(MianUIActivity.this,Discover.class));
            }
        });

        ivPapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pawu
                startActivity(new Intent(MianUIActivity.this,Mypapa.class));
            }
        });
        ivStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //状态
                startActivity(new Intent(MianUIActivity.this,MyStatus.class));
            }
        });
        ivShequ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //社区
                startActivity(new Intent(MianUIActivity.this,MySheQu.class));
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mainui;
    }

}
