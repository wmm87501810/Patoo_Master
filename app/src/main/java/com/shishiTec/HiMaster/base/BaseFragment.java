package com.shishiTec.HiMaster.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Pursue on 16/1/18.
 */
public abstract class BaseFragment extends Fragment{

    private CompositeSubscription mCompositeSubscription;
    private View mContentView;
    protected boolean mIsInflated;
    public DeviceParams device;
    public BaseParams params;
    public  BaseApplication baseApplication = BaseApplication.getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,mContentView);
        initDeviceData();
        if(mContentView == null){
            mContentView = inflater.inflate(getLayoutResId(), container, false);
            onInflated(mContentView,savedInstanceState);
        }
        return mContentView;
    }
    //初始化设备信息
    private void initDeviceData(){
        device = new DeviceParams();
        params = new BaseParams(device);
        params.setDevice(device);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
    }

    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this,view);
//        if (mContentView != null) {
//            onInflated(mContentView, savedInstanceState);
//            mIsInflated = true;
//        }
//    }

    protected abstract int getLayoutResId();

    protected abstract void onInflated(View contentView, Bundle savedInstanceState);

    public View getContentView() {
        return mContentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription!=null){
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
