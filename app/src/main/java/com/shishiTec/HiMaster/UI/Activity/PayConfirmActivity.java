package com.shishiTec.HiMaster.UI.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseActivity;

public class PayConfirmActivity extends BaseActivity {
    private Button pay;
    private TextView money;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_confirm;
    }

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
        pay = (Button) findViewById(R.id.pay);
        money = (TextView) findViewById(R.id.money);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
