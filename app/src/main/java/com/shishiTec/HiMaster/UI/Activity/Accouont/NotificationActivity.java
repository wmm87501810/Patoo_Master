package com.shishiTec.HiMaster.UI.Activity.Accouont;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shishiTec.HiMaster.Model.bean.NotificationBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.NotificationAdapter;
import com.shishiTec.HiMaster.Utils.DividerItemDecoration;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NotificationActivity extends BaseActivity{
    private RecyclerView rv_container;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        rv_container = (RecyclerView) findViewById(R.id.rv_container);
        LinearLayoutManager contentManager = new LinearLayoutManager(this);
        contentManager.setOrientation(LinearLayoutManager.VERTICAL);
        //添加分割线
        rv_container.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST));
        rv_container.setLayoutManager(contentManager);
        rv_container.setItemAnimator(new DefaultItemAnimator());
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_notification;
    }

    public void initData(){
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("page_size","100");
        params.setData(map);
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .getSystemInform(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<NotificationBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<NotificationBean>> listBaseModel) {
                        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this,listBaseModel.getData());
                        rv_container.setAdapter(notificationAdapter);
                        //创建默认的线性LayoutManager
                    }
                });
        addSubscription(subscription);
    }
}
