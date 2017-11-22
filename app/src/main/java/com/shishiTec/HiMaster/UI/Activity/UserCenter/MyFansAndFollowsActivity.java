package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.FansAndFollowBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.FansAdapter;
import com.shishiTec.HiMaster.UI.Views.RecycleViewDivider;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyFansAndFollowsActivity extends BaseActivity {
    @Bind(R.id.top_title)
    TextView topTitle;
    @Bind(R.id.right_title)
    TextView rightTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int page = 1;
    private int page_size = 10;
    private List<FansAndFollowBean> mData = new ArrayList<>();
    private FansAdapter adapter;
    private List<FansAndFollowBean> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getStringExtra("fans")!=null){
            if (getIntent().getStringExtra("fans").equals("0")){
                topTitle.setText(R.string.myFans);
                getFans(page, page_size);
            }else {
                topTitle.setText(R.string.my_follows);
                queryFollows(page,page_size);
            }
        }

    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
        toolbar.setNavigationIcon(R.mipmap.finish);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,1,getResources().getColor(R.color.gray_cc)));
        adapter = new FansAdapter(this,mData);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new FansAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {

            }
        });
        adapter.setOnCheckedChangeListener(new FansAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int position,CompoundButton buttonView, boolean isChecked) {
                FansAndFollowBean bean = data.get(position);
                if (!isChecked){
                    user_cancel_follow(bean.getFid());
                }else {
                    user_follow(bean.getFid());
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_coupon;
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
                            ToastUtils.showCenter(MyFansAndFollowsActivity.this,listBaseModel.getMsg());
                        }

                    }
                });
        addSubscription(subscribe);
    }

    /**
     * 关注列表
     * @param page
     * @param page_size
     */
    private void queryFollows(final int page, int page_size) {
        Map<String, String> scoreMap = new HashMap<>();
        scoreMap.put("page", page + "");
        scoreMap.put("page_size", page_size + "");
        params.setData(scoreMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .myFollows(params)
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
                        if (listBaseModel.getCode()==200){
                            data = listBaseModel.getData();
                            if (data!=null&&data.size()>0){
                                if (page==1){
                                    mData.clear();
                                }
                                mData.addAll(data);
                            }
                            adapter.notifyDataSetChanged();

                        }else {
                            ToastUtils.showCenter(MyFansAndFollowsActivity.this,listBaseModel.getMsg());
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
                            ToastUtils.showGravity(MyFansAndFollowsActivity.this, baseModel.getMsg(), Gravity.CENTER, 0, 0);
                        } else {
                            ToastUtils.showGravity(MyFansAndFollowsActivity.this, baseModel.getMsg(), Gravity.CENTER, 0, 0);
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
                            ToastUtils.showGravity(MyFansAndFollowsActivity.this, baseModel.getMsg(), Gravity.CENTER, 20, 200);
                        } else {
                            ToastUtils.showGravity(MyFansAndFollowsActivity.this, baseModel.getMsg(), Gravity.CENTER, 20, 200);
                        }
                    }
                });
        addSubscription(subscribe);
    }
}
