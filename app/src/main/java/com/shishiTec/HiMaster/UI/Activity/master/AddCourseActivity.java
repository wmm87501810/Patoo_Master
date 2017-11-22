package com.shishiTec.HiMaster.UI.Activity.master;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Adapter.CourseAdapter;
import com.shishiTec.HiMaster.Model.bean.GetMasterCourseBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hyu on 2016/5/10.
 */
public class AddCourseActivity extends BaseActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private TextView topTitle;
    private TextView rightTitle;
    private ImageView iv_cancel;
    private ImageView iv_course_cover;
    private RecyclerView rv_course;
    private AutoRelativeLayout ar_select_course;
    private TextView tv_course_null;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        //头部
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        topTitle = (TextView) findViewById(R.id.top_title);
        rightTitle = (TextView) findViewById(R.id.right_title);
        iv_course_cover = (ImageView) findViewById(R.id.iv_course_cover);

        iv_cancel = (ImageView)findViewById(R.id.iv_cancel);
        tv_course_null = (TextView)findViewById(R.id.tv_course_null);
        ar_select_course = (AutoRelativeLayout) findViewById(R.id.ar_select_course);

        topTitle.setText("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
        toolbar.setNavigationIcon(R.mipmap.finish);
        toolbar.setBackgroundColor(Color.rgb(255,216,1));

        //课程列表
        rv_course = (RecyclerView) findViewById(R.id.rv_course);
        LinearLayoutManager course_manager = new LinearLayoutManager(this);
        course_manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_course.setLayoutManager(course_manager);
        rv_course.setItemAnimator(new DefaultItemAnimator());
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("page_size","10");
        getMasterCourses(map);
    }

    public void getMasterCourses(Map<String,String> map){
        params.setData(map);
        Subscription subscription = RetrofitManager.getmInstance()
                .createService()
                .getMasterCourses(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<GetMasterCourseBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<GetMasterCourseBean>> listBaseModel) {
                        if(listBaseModel.getData()!=null && listBaseModel.getData().size()>0){
                            CourseAdapter courseAdapter = new CourseAdapter(AddCourseActivity.this,listBaseModel.getData());
                            rv_course.setAdapter(courseAdapter);
                            courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position, Object object) {
                                    GetMasterCourseBean getMasterCourseBean = (GetMasterCourseBean)object;
                                    ToastUtils.show(AddCourseActivity.this,getMasterCourseBean.getTitle(),ToastUtils.LENGTH_LONG);
                                    Intent intent = new Intent();
                                    intent.putExtra("title",getMasterCourseBean.getTitle());
                                    intent.putExtra("cover",getMasterCourseBean.getCover());
                                    intent.putExtra("distance",getMasterCourseBean.getDistance());
                                    intent.putExtra("store",getMasterCourseBean.getStore());
                                    intent.putExtra("course_id",getMasterCourseBean.getCourse_id());
                                    setResult(112,intent);
                                    AddCourseActivity.this.finish();
                                }
                            });
                        }else{
                            rv_course.setVisibility(View.GONE);
                            ar_select_course.setVisibility(View.GONE);
                            tv_course_null.setVisibility(View.VISIBLE);
                        }
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_course;
    }

    @Override
    protected void initListener() {
        iv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cancel:
                rv_course.setVisibility(View.GONE);
                ar_select_course.setVisibility(View.GONE);
                this.finish();
                break;
        }
    }
}
