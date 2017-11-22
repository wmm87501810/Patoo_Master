package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.MasterCourseBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MasterCourseActivity extends BaseActivity {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryMyCourse(page,page_size);
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        topTitle.setText("我的课程");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
        toolbar.setNavigationIcon(R.mipmap.finish);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_coupon;
    }

    private void queryMyCourse(int page,int page_size){
        Map<String, String> myShareMap = new HashMap<>();
        myShareMap.put("page", page + "");
        myShareMap.put("page_size", page_size + "");
        params.setData(myShareMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .queryMasterCourse(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<MasterCourseBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseModel<List<MasterCourseBean>> listBaseModel) {
                        if (listBaseModel.getCode()==200){
                            ToastUtils.showGravity(MasterCourseActivity.this, "请求成功", Gravity.CENTER, 0, 0);
                        }

                    }
                });
        addSubscription(subscribe);
    }


}
