package com.shishiTec.HiMaster.UI.fragment.master;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.shishiTec.HiMaster.Model.bean.FansAndFollowBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.FansAdapter;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseFragment;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.ArrayList;
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
public class SearchMasterFragment extends BaseFragment {
    private int page = 1;
    private int page_size = 10;
    private List<FansAndFollowBean> mData = new ArrayList<>();
    private FansAdapter adapter;
    private List<FansAndFollowBean> data;
    private String key;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_master;
    }

    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
//        initViews();
//        getFans(page, page_size);
    }

//    protected void initViews() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,1,getResources().getColor(R.color.gray_cc)));
//        adapter = new FansAdapter(getActivity(),mData);
//        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(new FansAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(int position, View view) {
//
//            }
//        });
//    }

    public void notifyData(String key){
        this.key = key;
    }

    /**
     * 获得粉丝列表
     * @param page
     * @param page_size
     */
    private void getFans(final int page, int page_size) {
        Map<String, String> scoreMap = new HashMap<>();
        scoreMap.put("page", page + "");
        scoreMap.put("page_size", page_size + "");
        params.setData(scoreMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .myFans(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<FansAndFollowBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<FansAndFollowBean>> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {
                            data = listBaseModel.getData();
                            if (data!=null&&data.size()>0){
                                if (page==1){
                                    mData.clear();
                                }
                                mData.addAll(data);
                            }
                            adapter.notifyDataSetChanged();
                        }else {
                            ToastUtils.showCenter(getActivity(),listBaseModel.getMsg());
                        }

                    }
                });
        addSubscription(subscribe);
    }


    /**
     * 用户关注
     * @param id
     */
    public void user_follow(String id) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("fid", id);
        params.setData(BigManMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
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
                            ToastUtils.showGravity(getActivity(), baseModel.getMsg(), Gravity.CENTER, 0, 0);
                        } else {
                            ToastUtils.showGravity(getActivity(), baseModel.getMsg(), Gravity.CENTER, 0, 0);
                        }
                    }
                });
        addSubscription(subscribe);
    }

    /**
     * 取消关注
     * @param id
     */
    public void user_cancel_follow(String id) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("fid", id);
        params.setData(BigManMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
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
                            ToastUtils.showGravity(getActivity(), baseModel.getMsg(), Gravity.CENTER, 20, 200);
                        } else {
                            ToastUtils.showGravity(getActivity(), baseModel.getMsg(), Gravity.CENTER, 20, 200);
                        }
                    }
                });
        addSubscription(subscribe);
    }
}
