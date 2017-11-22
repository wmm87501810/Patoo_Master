package com.shishiTec.HiMaster.UI.fragment.UserCenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shishiTec.HiMaster.Model.bean.OrderListBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.BuyOrderContentAdapter;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.base.BaseFragment;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hyu_anzhuo on 2016/5/27.
 */


public class BuyOrderContentFragment extends BaseFragment {
    private RecyclerView buyOrderRecycler;
    private LinearLayoutManager layoutManager;
    private BuyOrderContentAdapter buyOrderContentAdapter;
    private SuperSwipeRefreshLayout super_buy_order;
    private int page = 1;
    private int pagesize = 10;
    private String types;
    private List<OrderListBean> orderListBeen;
    private AutoLinearLayout MD_al;
    public BuyOrderContentFragment() {
        super();
    }

    public static BuyOrderContentFragment newInstance(String type) {
        BuyOrderContentFragment mallFragment = new BuyOrderContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        mallFragment.setArguments(bundle);
        return mallFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_buy_order_content;
    }

    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
        MD_al = (AutoLinearLayout) contentView.findViewById(R.id.MD_al);
        MD_al.setVisibility(View.GONE);
        types = getType();
        super_buy_order = (SuperSwipeRefreshLayout) contentView.findViewById(R.id.super_buy_order);
        buyOrderRecycler = (RecyclerView) contentView.findViewById(R.id.buyOrderRecycler);
        layoutManager = new LinearLayoutManager(getActivity());
        buyOrderContentAdapter = new BuyOrderContentAdapter(getActivity(),orderListBeen);
        buyOrderRecycler.setLayoutManager(layoutManager);
        buyOrderRecycler.setHasFixedSize(true);
        initData(page, pagesize, true);
        super_buy_order.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                initData(1, pagesize, true);
                super_buy_order.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        super_buy_order.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                initData(1, pagesize, false);
                super_buy_order.setLoadMore(false);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
    }

    private void initData(int page, int pagesize, final boolean b) {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("page_size", pagesize + "");
        if (!types.equals("")) {
            map.put("orderStatus", types);
        }
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .orderList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<OrderListBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<OrderListBean>> md) {
                        if (md.getCode() == 200) {
                            if (b) {
                                orderListBeen = md.getData();
                                buyOrderContentAdapter = new BuyOrderContentAdapter(getActivity(), orderListBeen);
                                buyOrderRecycler.setAdapter(buyOrderContentAdapter);
                            }else{
                                if(md.getData().size()>0){
                                    orderListBeen.addAll(md.getData());
                                    buyOrderContentAdapter.addMoreItem(orderListBeen);
                                }
                            }

                        }
                    }
                });
    }

    public String getType() {
        String type = "";
        if (getArguments().getString("type") != null) {
            switch (getArguments().getString("type")) {
                case "全部":
                    type = "";
                    break;
                case "待接单":
                    type = "4";
                    break;
                case "待上课":
                    type = "0";
                    break;
                case "待评价":
                    type = "1";
                    break;
                case "退款":
                    type = "5";
                    break;
            }
        }
        return type;
    }
}
