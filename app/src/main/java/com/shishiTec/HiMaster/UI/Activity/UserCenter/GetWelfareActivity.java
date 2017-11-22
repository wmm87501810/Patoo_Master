package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.os.Bundle;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseActivity;

public class GetWelfareActivity extends BaseActivity {

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

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_get_welfare;
    }
}
