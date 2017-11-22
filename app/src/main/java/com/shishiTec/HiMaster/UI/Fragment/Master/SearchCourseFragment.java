package com.shishiTec.HiMaster.UI.fragment.master;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shishiTec.HiMaster.Adapter.CourseAdapter;
import com.shishiTec.HiMaster.Model.bean.GetMasterCourseBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
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
public class SearchCourseFragment extends BaseFragment {
    private RecyclerView rl_search_course;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_course;
    }

    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
        rl_search_course = (RecyclerView) getContentView().findViewById(R.id.rl_search_course);
        //课程列表
        LinearLayoutManager course_manager = new LinearLayoutManager(getActivity());
        course_manager.setOrientation(LinearLayoutManager.VERTICAL);
        rl_search_course.setLayoutManager(course_manager);
        rl_search_course.setItemAnimator(new DefaultItemAnimator());
    }

    public void notifyData(String key){
        Map<String,String> map = new HashMap<>();
        map.put("keywords",key);
        map.put("page","1");
        map.put("page_size","10");
        getMasterCourses(map);
    }

    public void getMasterCourses(Map<String,String> map){
        params.setData(map);
        Subscription subscription = RetrofitManager.getmInstance()
                .createService()
                .searchCourse(params)
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
                            CourseAdapter courseAdapter = new CourseAdapter(getActivity(),listBaseModel.getData());
                            rl_search_course.setAdapter(courseAdapter);
                        }
                    }
                });
        addSubscription(subscription);
    }
}
