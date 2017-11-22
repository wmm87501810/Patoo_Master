package com.shishiTec.HiMaster.UI.fragment.master;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.shishiTec.HiMaster.Model.bean.MyShareBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.MyShareAdapter;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseFragment;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hyu on 2016/5/13.
 */
public class SearchShareFragment extends BaseFragment {
    private RecyclerView rl_search_share;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page = 1;
    private int page_size = 10;
    private MyShareAdapter myShareAdapter;
    private List<MyShareBean> list;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;
    private String key;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_share;
    }

    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
        initViews();
        Refresh();
    }

    public void notifyData(String key){
        this.key = key;
        queryMyShare(page,page_size,true);
    }

    public void setdapter(List<MyShareBean> key){
        MyShareAdapter shareAdapter = new MyShareAdapter(getActivity(),key);
        rl_search_share.setAdapter(shareAdapter);
    }


    private void Refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryMyShare(1,page_size,true);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        rl_search_share.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == myShareAdapter.getItemCount()) {
                    queryMyShare(++page,page_size,false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    protected void initViews() {
        rl_search_share = (RecyclerView) getContentView().findViewById(R.id.rl_search_share);
        swipeRefreshLayout = (SwipeRefreshLayout) getContentView().findViewById(R.id.swipeRefreshLayout);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rl_search_share.setLayoutManager(linearLayoutManager);

    }

    private void queryMyShare(int page, int page_size, final boolean b){
        Map<String,String> myShareMap = new HashMap<>();
        device = new DeviceParams();
        params = new BaseParams(device);
        params.setDevice(device);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        Log.i("key:",key);
        myShareMap.put("keywords",key);
        myShareMap.put("page", page + "");
        myShareMap.put("page_size", page_size + "");
        Log.i("key",myShareMap.size()+"");
        params.setData(myShareMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .searchShare(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<MyShareBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showGravity(getActivity(), "请求失败", Gravity.CENTER, 0, 0);
                    }

                    @Override
                    public void onNext(BaseModel<List<MyShareBean>> listBaseModel) {
                        if (listBaseModel.getCode()==200){
                            if(b){
                                list = listBaseModel.getData();
                                myShareAdapter = new MyShareAdapter(getActivity(),list);
                                rl_search_share.setAdapter(myShareAdapter);
                            }else{
                                if(listBaseModel.getData().size() > 0){
                                    list.addAll(listBaseModel.getData());
                                    myShareAdapter.addMoreItem(list);
                                }else{
                                }
                            }
                        }

                    }
                });
        addSubscription(subscribe);
    }


}
