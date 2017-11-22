package com.shishiTec.HiMaster.UI.fragment.master;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shishiTec.HiMaster.Model.bean.MasterOrderBean;
import com.shishiTec.HiMaster.Model.bean.MasterStartBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.master.OrderManagerAdapter;
import com.shishiTec.HiMaster.UI.Adapter.master.TabFragmentAdapter;
import com.shishiTec.HiMaster.base.BaseFragment;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hyu on 2016/6/3.
 */
public class OrderManagerFragment extends BaseFragment {
    private RecyclerView order_manager_rv;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order_manager;
    }

    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
        order_manager_rv = (RecyclerView) contentView.findViewById(R.id.order_manager_rv);
        LinearLayoutManager orderManger = new LinearLayoutManager(getActivity());
        orderManger.setOrientation(LinearLayoutManager.HORIZONTAL);
        //热门达人
        order_manager_rv.setLayoutManager(orderManger);
        order_manager_rv.setItemAnimator(new DefaultItemAnimator());
        initData();
    }

    public void initData(){
        Map<String,String> map = new Hashtable<>();
        if(getArguments().get("orderStatus")!=null && !getArguments().get("orderStatus").toString().equals("")){
            map.put("orderStatus",getArguments().get("orderStatus").toString());
        }
        map.put("page","1");
        map.put("pageSize","10");
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .getMasterOders(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<MasterOrderBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<MasterOrderBean>> mb) {
                        if (mb.getCode() == 200) {
                            OrderManagerAdapter orderManagerAdapter = new OrderManagerAdapter(getContext(),mb.getData());
                        }
                    }

                });
        addSubscription(subscribe);
    }
}

