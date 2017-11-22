package com.shishiTec.HiMaster.UI.fragment.BigMan;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.shishiTec.HiMaster.Adapter.BigManAdapter;
import com.shishiTec.HiMaster.Model.bean.BigManBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
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
 * Created by Pursue on 16/4/6.
 */
public class BigManFragment extends BaseFragment {
    protected Subscription subscription;
    private RecyclerView bigmanFragment_recyler;
    private BigManAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SuperSwipeRefreshLayout bigman_swiperefreshlayout;
    private int lastVisibleItem;
    private int w = 2;

    @Override
    protected int getLayoutResId() {
        return R.layout.bigman_fragment_layout;
    }

    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initView() {
        bigmanFragment_recyler = (RecyclerView) getContentView().findViewById(R.id.bigmanFragment_recyler);
        bigman_swiperefreshlayout = (SuperSwipeRefreshLayout) getContentView().findViewById(R.id.bigman_swiperefreshlayout);
    }

    private void initData() {
        getBigManContent();
        bigman_swiperefreshlayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                getBigManContent();
                bigman_swiperefreshlayout.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        bigman_swiperefreshlayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                LoadBigManContent();
                bigman_swiperefreshlayout.setLoadMore(false);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
    }

    private void LoadBigManContent() {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("page", "" + w++);
        BigManMap.put("page_size", "10");
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken("");
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        subscription = RetrofitManager.getmInstance().createService()
                .BigManContet(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<BigManBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showGravity(getActivity(), "数据获取失败", Gravity.CENTER, 20, 200);
                        bigmanFragment_recyler.setAdapter(null);
                    }

                    @Override
                    public void onNext(BaseModel<List<BigManBean>> BigManBaseModel) {
                        if (BigManBaseModel.getCode() == 200) {
                            Log.i("数据=======", "成功");
                            if (BigManBaseModel.getData().size() > 0) {
                                List<BigManBean> bigmanlist = BigManBaseModel.getData();
                                mAdapter.addMoreItem(bigmanlist);
                            } else {
                                ToastUtils.showGravity(getActivity(), "暂无任何数据", Gravity.CENTER, 20, 200);
                            }
                        } else {
                            ToastUtils.showGravity(getActivity(), "数据获取失败", Gravity.CENTER, 20, 200);
                            bigmanFragment_recyler.setAdapter(null);
                        }
                    }
                });
    }

    private void getBigManContent() {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("page", "1");
        BigManMap.put("page_size", "10");
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken("");
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        subscription = RetrofitManager.getmInstance().createService()
                .BigManContet(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<BigManBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showGravity(getActivity(), "数据获取失败", Gravity.CENTER, 20, 200);
                        bigmanFragment_recyler.setAdapter(null);
                    }

                    @Override
                    public void onNext(BaseModel<List<BigManBean>> BigManBaseModel) {
                        if (BigManBaseModel.getCode() == 200) {
                            List<BigManBean> bigmanlist = BigManBaseModel.getData();
                            //创建默认的线性LayoutManager
                            mLayoutManager = new LinearLayoutManager(getActivity());
                            bigmanFragment_recyler.setLayoutManager(mLayoutManager);
                            //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
                            bigmanFragment_recyler.setHasFixedSize(true);
                            //创建并设置Adapter
                            mAdapter = new BigManAdapter(getActivity(), bigmanlist);
                            bigmanFragment_recyler.setAdapter(mAdapter);
                        } else {
                            ToastUtils.showGravity(getActivity(), "数据获取失败", Gravity.CENTER, 20, 200);
                            bigmanFragment_recyler.setAdapter(null);
                        }
                    }
                });
    }
}
