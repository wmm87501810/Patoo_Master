package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WelfareActivity extends BaseActivity {
    private ImageView left_back;
    private EditText edit_welfare;
    private EditText edit_phone;
    private Button btn_welfare_submit;

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
        edit_welfare = (EditText) findViewById(R.id.edit_welfare);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        btn_welfare_submit = (Button) findViewById(R.id.btn_welfare_submit);
        btn_welfare_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welfare();
            }
        });
    }

    private void welfare() {
        Map<String, String> map = new HashMap<>();
        map.put("code", edit_welfare.getText().toString());
        map.put("mobile", edit_phone.getText().toString());
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .codeExchange(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseModel mb) {
                        if (mb.getCode() == 200) {
                            Toast.makeText(WelfareActivity.this, mb.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(WelfareActivity.this, mb.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welfare;
    }
}
