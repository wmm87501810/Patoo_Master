package com.shishiTec.HiMaster.UI.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.shishiTec.HiMaster.Model.bean.OrderBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseFragment;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Pursue on 16/5/4.
 */
public class OrderStateFragment extends BaseFragment {
    @Bind(R.id.main_expandablelistview)
    ExpandableListView mainExpandablelistview;
    private String orderStatus;
    private int page = 1;
    private int page_size = 10;
    private  List<OrderBean> mData = new ArrayList<>();

    public OrderStateFragment(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order_state;
    }

    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
        queryMyOrder(page,page_size,orderStatus);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void queryMyOrder(int page, int page_size,String orderStatus) {
        Map<String,String> orderMap = new HashMap<>();
        orderMap.put("page",page+"");
        orderMap.put("page_size",page_size+"");
        orderMap.put("orderStatus",orderStatus);
        orderMap.put("oid","");
        orderMap.put("orderId","");
        params.setData(orderMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .queryOrder(params)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<OrderBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<OrderBean>> listBaseModel) {
                        if (listBaseModel.getCode()==200){
                            List<OrderBean> data = listBaseModel.getData();
                            if (data!=null&&data.size()>0){
                                mData.addAll(data);
                            }
                        }

                    }
                });
        addSubscription(subscribe);

    }
}
