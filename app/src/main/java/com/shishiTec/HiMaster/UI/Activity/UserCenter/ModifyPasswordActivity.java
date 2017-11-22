package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import org.xutils.common.util.MD5;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText pwd_et;
    private Button btn_submit;
    private EditText new_pwd_et;
    private EditText again_new_pwd_et;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        new_pwd_et = (EditText) findViewById(R.id.new_pwd_et);
        again_new_pwd_et = (EditText) findViewById(R.id.again_new_pwd_et);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (pwd_et.getText().toString().equals("") || new_pwd_et.getText().toString().equals("") || again_new_pwd_et.getText().toString().equals("")) {
                    ToastUtils.show(ModifyPasswordActivity.this, "密码不能为空", ToastUtils.LENGTH_LONG);
                    return;
                }
                if (!new_pwd_et.getText().toString().equals(again_new_pwd_et.getText().toString())) {
                    ToastUtils.show(ModifyPasswordActivity.this, "俩次密码输入不一致", ToastUtils.LENGTH_LONG);
                    return;
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("password", MD5.md5(pwd_et.getText().toString()));
                    map.put("password_new", MD5.md5(new_pwd_et.getText().toString()));
                    params.setData(map);
                    Subscription subscribe = RetrofitManager.getmInstance()
                            .createService()
                            .resetPwd(params)
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
                                public void onNext(BaseModel baseModel) {
                                    ToastUtils.show(ModifyPasswordActivity.this, baseModel.getMsg().toString(), ToastUtils.LENGTH_LONG);
                                }
                            });
                    addSubscription(subscribe);
                }
                break;
        }
    }
}
