package com.shishiTec.HiMaster.UI.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shishiTec.HiMaster.Adapter.CourseAdapter;
import com.shishiTec.HiMaster.Model.bean.GetMasterCourseBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hyu on 2016/5/16.
 */
public class CoursePicPopupWindow extends PopupWindow {
    private TextView tv_link_course;        //链接课程
    private TextView tv_store_name;         //店铺名称
    private TextView tv_course_price;       //课程价格
    private TextView tv_km;                 //课程距离
    private TextView tv_course_name;        //课程名称
    private String course_id;                  //课程ID
    private ImageView iv_cancel;
    private ImageView iv_course_cover;
    private RecyclerView rv_course;
    private View mMenuView;
    private Activity activity;
    private GetMasterCourseBean masterCourseBean;

    public CoursePicPopupWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        this.activity = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.course_alert_dialog, null);
        iv_cancel = (ImageView) mMenuView.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //销毁弹出框
                dismiss();
            }
        });

        //课程列表
        iv_course_cover = (ImageView) activity.findViewById(R.id.iv_course_cover);
        tv_course_name = (TextView)activity.findViewById(R.id.tv_course_name);
        tv_course_price = (TextView) activity.findViewById(R.id.tv_course_price);
        tv_link_course = (TextView) activity.findViewById(R.id.tv_link_course);
        tv_store_name = (TextView) activity.findViewById(R.id.tv_store_name);
        tv_km = (TextView) activity.findViewById(R.id.tv_km);

        rv_course = (RecyclerView)mMenuView.findViewById(R.id.rv_course);
        LinearLayoutManager course_manager = new LinearLayoutManager(context);
        course_manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_course.setLayoutManager(course_manager);
        rv_course.setItemAnimator(new DefaultItemAnimator());
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("page_size","10");
        getMasterCourses(map);


        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    public void getMasterCourses(Map<String,String> map){
        DeviceParams device = new DeviceParams();
        BaseParams params = new BaseParams(device);
        params.setDevice(device);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
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
                            CourseAdapter courseAdapter = new CourseAdapter(activity,listBaseModel.getData());
                            rv_course.setAdapter(courseAdapter);
                            courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position, Object object) {
                                    GetMasterCourseBean getMasterCourseBean = (GetMasterCourseBean)object;
                                    ToastUtils.show(activity,getMasterCourseBean.getTitle(),ToastUtils.LENGTH_LONG);
                                    tv_link_course.setVisibility(View.GONE);
                                    tv_km.setVisibility(View.VISIBLE);
                                    tv_course_name.setVisibility(View.VISIBLE);
                                    tv_store_name.setVisibility(View.VISIBLE);
                                    tv_course_price.setVisibility(View.VISIBLE);
                                    tv_course_name.setText(getMasterCourseBean.getTitle());
                                    tv_course_price.setText("￥"+getMasterCourseBean.getPrice());
                                    tv_store_name.setText(getMasterCourseBean.getStore());
                                    tv_km.setText(getMasterCourseBean.getDistance());
                                    BaseApplication.getInstance().loadImageView(activity,iv_course_cover,getMasterCourseBean.getCover());
                                    course_id = getMasterCourseBean.getCourse_id();
                                    dismiss();
                                }
                            });
                        }
                    }
                });
    }

    public String getCourseId(){
        return  course_id;
    }
}