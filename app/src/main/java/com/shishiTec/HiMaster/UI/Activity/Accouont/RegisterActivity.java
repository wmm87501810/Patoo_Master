package com.shishiTec.HiMaster.UI.Activity.Accouont;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.shishiTec.HiMaster.Model.bean.RegisterAndLoginBean;
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

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.next)
    Button next;
    @Bind(R.id.mobile)
    EditText mobile;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.device_info)
    TextView deviceInfo;


    private VerifyBean data;
    private RegisterAndLoginBean registData;
    SharedPreferencesUtil instance = SharedPreferencesUtil.getInstance();


    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        mobile.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        password.setInputType(EditorInfo.TYPE_CLASS_PHONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    //上传验证码
    public void getVerifyCode(View view) {
        Map<String, String> verifyMap = new HashMap<>();
        verifyMap.put("mobile", mobile.getText().toString());
        verifyMap.put("type", "1");
        DeviceParams device = new DeviceParams();
        BaseParams params = new BaseParams(device);
        params.setDevice(device);
        params.setData(verifyMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");

        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .getVerifyCode(params)
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
                        if (verifyBeanBaseModel.getCode() == 200) {
                            ToastUtils.showGravity(RegisterActivity.this, data.getCode(), Gravity.CENTER, 20, 50);
                        }
                    }
                });
        addSubscription(subscribe);
    }

    //手机验证码登陆
    public void login(View view) {
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("mobile", mobile.getText().toString());
        loginMap.put("code", password.getText().toString());//验证码
        loginMap.put("clientId", PushManager.getInstance().getClientid(this));
        DeviceParams deviceParams = new DeviceParams();
//        deviceParams.setToken(registData.getToken());
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setData(loginMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .Login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<RegisterAndLoginBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showGravity(RegisterActivity.this, "注册失败", Gravity.CENTER, 20, 200);
                    }

                    @Override
                    public void onNext(BaseModel<RegisterAndLoginBean> registBeanBaseModel) {
                        if (registBeanBaseModel.getCode() == 200) {
                            RegisterAndLoginBean data = registBeanBaseModel.getData();
                            ToastUtils.showGravity(RegisterActivity.this, "注册成功", Gravity.CENTER, 20, 200);
                            instance.saveMobNum(mobile.getText().toString());
                            SharedPreferencesUtil.getInstance().saveLoginInfo("localLogin", data.getMobile(), password.getText().toString(), data.getUid(), data.getToken(), data.getIdentity());
                            startActivity(new Intent(RegisterActivity.this, SetSecurityCode.class));
                        }
                    }
                });
        addSubscription(subscription);
    }

}
