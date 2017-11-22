package com.shishiTec.HiMaster.UI.Activity.Accouont;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.shishiTec.HiMaster.Model.bean.VerifyBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 83802 on 2017/8/11.
 */

public class SetSecurityCode extends BaseActivity {
    @Bind(R.id.mobile1)
    EditText mobile1;
    @Bind(R.id.mobile2)
    EditText mobile2;
    @Bind(R.id.mobile3)
    EditText mobile3;
    @Bind(R.id.mobile4)
    EditText mobile4;
    @Bind(R.id.next)
    Button next;
    SharedPreferencesUtil instance = SharedPreferencesUtil.getInstance();

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        mobile1.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mobile2.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mobile3.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mobile4.setInputType(EditorInfo.TYPE_CLASS_PHONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setsecuritycode;
    }

    public void getSecurityCode(View view) {
        final String security_code = mobile1.getText().toString()+mobile2.getText().toString()+mobile3.getText().toString()+mobile4.getText().toString();
        Map<String, String> verifyMap = new HashMap<>();
        verifyMap.put("security_code", security_code);
        verifyMap.put("type", "1");
        DeviceParams device = new DeviceParams();
        BaseParams params = new BaseParams(device);
        params.setDevice(device);
        params.setData(verifyMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");

        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .getSecurityCode(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<VerifyBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<VerifyBean> verifyBeanBaseModel) {
                        ToastUtils.showGravity(SetSecurityCode.this, "成功", Gravity.CENTER, 20, 50);
                        instance.saveSecurity_code(security_code);
                        startActivity(new Intent(SetSecurityCode.this,SetSex.class));
                    }
                });
        addSubscription(subscribe);
    }


}
