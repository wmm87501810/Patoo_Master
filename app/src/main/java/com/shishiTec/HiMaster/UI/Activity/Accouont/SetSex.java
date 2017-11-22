package com.shishiTec.HiMaster.UI.Activity.Accouont;


import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shishiTec.HiMaster.Model.bean.VerifyBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.MianUIActivity;
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
 * Created by 83802 on 2017/8/14.
 */

public class SetSex extends BaseActivity {


    @Bind(R.id.iv_setsex_nv)
    ImageView ivSetsexNv;
    @Bind(R.id.iv_setsex_nan)
    ImageView ivSetsexNan;
    @Bind(R.id.next2)
    Button next;
    private String sex = "0";
    SharedPreferencesUtil instance = SharedPreferencesUtil.getInstance();

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        ivSetsexNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = "2";
            }
        });
        ivSetsexNan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = "1";
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setsex;
    }

    public void getSex(View view) {
        Map<String, String> verifyMap = new HashMap<>();
        verifyMap.put("sex", sex);
        verifyMap.put("type", "1");
        DeviceParams device = new DeviceParams();
        BaseParams params = new BaseParams(device);
        params.setDevice(device);
        params.setData(verifyMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");

        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .getSex(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<VerifyBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showGravity(SetSex.this, "失败", Gravity.CENTER, 20, 50);
                        //startActivity(new Intent(SetSex.this,MianUIActivity.class));
                    }

                    @Override
                    public void onNext(BaseModel<VerifyBean> verifyBeanBaseModel) {
                        ToastUtils.showGravity(SetSex.this, "成功", Gravity.CENTER, 20, 50);
                       instance.saveSex(sex);
                        startActivity(new Intent(SetSex.this,MianUIActivity.class));
                    }
                });
        addSubscription(subscribe);
    }

}
