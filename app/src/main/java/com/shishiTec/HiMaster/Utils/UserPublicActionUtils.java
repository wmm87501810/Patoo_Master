package com.shishiTec.HiMaster.Utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hyu on 2016/4/29.
 */
public class UserPublicActionUtils {
    private static UserPublicActionUtils userPublicActionUtils = new UserPublicActionUtils();

    private UserPublicActionUtils(){}
    public static UserPublicActionUtils getInstance(){
        return userPublicActionUtils;
    }

    /**
     * 用户关注
     * @param id
     */
    public void user_follow(final Context context,String id) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("fid", id);
        DeviceParams deviceParams = new DeviceParams();
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        RetrofitManager.getmInstance().createService()
                .bigManShartDetailFollow(params)
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
                        if (baseModel.getCode() == 200) {
                            ToastUtils.showGravity(context, "已成功关注", Gravity.CENTER, 20, 200);
//                            view.setText("已关注");
                        }else{
                            ToastUtils.showGravity(context, baseModel.getMsg(), Gravity.CENTER, 20, 200);
                        }
                    }
                });
    }

    /**
     * 取消关注
     * @param id
     */
    public void user_cancel_follow(final Context context,String id) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("fid", id);
        DeviceParams deviceParams = new DeviceParams();
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        RetrofitManager.getmInstance().createService()
                .bigManDetailCancleFollow(params)
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
                        if (baseModel.getCode() == 200) {
                            ToastUtils.showGravity(context, "已成功取消关注", Gravity.CENTER, 20, 200);
//                            view.setText("关注");
                        }else{
                            ToastUtils.showGravity(context, baseModel.getMsg(), Gravity.CENTER, 20, 200);
//                            view.setText("已关注");
                        }
                    }
                });
    }
    /**
     * 用户点赞
     * @param id
     */
    public void user_zambia(final Context context,String id, final TextView view) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("uid_other", id);
        DeviceParams deviceParams = new DeviceParams();
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        RetrofitManager.getmInstance().createService()
                .userZambia(params)
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
                        if (baseModel.getCode() == 200) {
                            ToastUtils.showGravity(context, "点赞成功", Gravity.CENTER, 20, 200);
                            view.setText("已赞");
                        }else{
                            ToastUtils.showGravity(context, baseModel.getMsg(), Gravity.CENTER, 20, 200);
                        }
                    }
                });
    }

    /**
     * 用户取消点赞
     * @param id
     */
    public void user_cancel_zambia(final Context context,String id, final TextView view) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("uid_other", id);
        DeviceParams deviceParams = new DeviceParams();
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        RetrofitManager.getmInstance().createService()
                .userCancelZambia(params)
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
                        if (baseModel.getCode() == 200) {
                            ToastUtils.showGravity(context, "取消点赞", Gravity.CENTER, 20, 200);
                            view.setText("点赞");
                        }else{
                            ToastUtils.showGravity(context, baseModel.getMsg(), Gravity.CENTER, 20, 200);
                            view.setText("已赞");
                        }
                    }
                });
    }
}
