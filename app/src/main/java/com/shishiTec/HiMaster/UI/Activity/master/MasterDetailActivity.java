package com.shishiTec.HiMaster.UI.Activity.master;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.MasterDetailBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.bigManShare.BigManHomePageActivity;
import com.shishiTec.HiMaster.UI.Adapter.master.CourseAdapter;
import com.shishiTec.HiMaster.UI.Adapter.master.DetailAdapter;
import com.shishiTec.HiMaster.Utils.DividerItemDecoration;
import com.shishiTec.HiMaster.Utils.UserPublicActionUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MasterDetailActivity extends BaseActivity implements View.OnClickListener{
    private TextView follow_detail;   //分享关注
    private TextView nike_name_detail;//分享昵称
    private TextView tv_master_detail_comment_count;//评论数
    private TextView content_detail;  //分享内容
    private TextView title_detail;                     //分享标题
    private TextView tv_master_detail_course_title;    //课程标题
    private TextView tv_km;                            //课程距离
    private TextView tv_course_price;                  //课程价格
    private TextView tv_m_course_price;                //优惠价格
    private TextView tv_master_detail_relevant_address;//课程地址
    private TextView tv_master_detail_look_comment;    //查看评论
    private TextView tag1,tag2,tag3,tag4;              //课程标签

    private AutoLinearLayout al_master_detail; //屏幕区域

    private String uid;
    private String share_id;
    private String user_id;

    private ImageView user_logo_detail;              //用户头像
    private ImageView img_title_cover;               //分享封面
    private ImageView iv_master_detail_course_cover; //课程封面

    private RecyclerView rv_master_comment;      //评论列表
    private RecyclerView course_info_detail;     //课程


    @Override
    public int getLayoutId() {
        return R.layout.activity_master_detail;
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

//        al_master_detail.setLayoutParams(new AutoLinearLayout.LayoutParams(metric.widthPixels,metric.heightPixels-110));
        tag1 = (TextView) findViewById(R.id.tag1);
        tag2 = (TextView) findViewById(R.id.tag2);
        tag3 = (TextView) findViewById(R.id.tag3);
        tag4 = (TextView) findViewById(R.id.tag4);
        tv_km = (TextView) findViewById(R.id.tv_km);
        title_detail = (TextView)findViewById(R.id.title_detail);
        follow_detail = (TextView)findViewById(R.id.follow_detail);
        content_detail = (TextView) findViewById(R.id.content_detail);
        tv_course_price = (TextView) findViewById(R.id.tv_course_price);
        nike_name_detail = (TextView) findViewById(R.id.nike_name_detail);
        tv_m_course_price = (TextView) findViewById(R.id.tv_m_course_price);
        tv_master_detail_look_comment = (TextView)findViewById(R.id.tv_master_detail_look_comment);
        tv_master_detail_course_title = (TextView) findViewById(R.id.tv_master_detail_course_title);
        tv_master_detail_comment_count = (TextView) findViewById(R.id.tv_master_detail_comment_count);
        tv_master_detail_relevant_address = (TextView) findViewById(R.id.tv_master_detail_relevant_address);

        img_title_cover = (ImageView)findViewById(R.id.img_title_cover);
        user_logo_detail = (ImageView) findViewById(R.id.user_logo_detail);
        iv_master_detail_course_cover = (ImageView) findViewById(R.id.iv_master_detail_course_cover);

        rv_master_comment = (RecyclerView) findViewById(R.id.rv_master_comment);
        LinearLayoutManager contentManager = new LinearLayoutManager(this);
        contentManager.setOrientation(LinearLayoutManager.VERTICAL);
        //添加分割线
        rv_master_comment.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST));
        rv_master_comment.setLayoutManager(contentManager);
        rv_master_comment.setItemAnimator(new DefaultItemAnimator());

        course_info_detail = (RecyclerView) findViewById(R.id.course_info_detail);
        LinearLayoutManager courseInfoManager = new LinearLayoutManager(this);
        courseInfoManager.setOrientation(LinearLayoutManager.VERTICAL);
        course_info_detail.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL_LIST));
        course_info_detail.setLayoutManager(courseInfoManager);
        course_info_detail.setItemAnimator(new DefaultItemAnimator());

        Intent intent = getIntent();
        share_id = intent.getStringExtra("share_id");
        Map<String,String> map = new HashMap<>();
        map.put("share_id",share_id);
        getMasterDetail(map);
    }

    @Override
    protected void initListener() {
        follow_detail.setOnClickListener(this);
        tv_master_detail_look_comment.setOnClickListener(this);
    }

    /**
     * 获取分享详情
     */
    private void getMasterDetail(Map<String,String> map){
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .masterDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<MasterDetailBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<MasterDetailBean> md) {
                        content_detail.setText(md.getData().getShare().getContent());
                        tv_master_detail_comment_count.setText("评论: ("+md.getData().getShare().getComment_count()+")");
                        tv_master_detail_look_comment.setText("查看全部"+md.getData().getShare().getComment_count()+"条评论");

                        title_detail.setText(md.getData().getShare().getTitle());
                        tv_master_detail_relevant_address.setText(md.getData().getCourse().getStore()+"     "+md.getData().getCourse().getDistance());

                        BaseApplication.getInstance().loadRoundImageView(MasterDetailActivity.this,user_logo_detail,md.getData().getUser().getImg_top());
                        BaseApplication.getInstance().loadRoundImageView(MasterDetailActivity.this,iv_master_detail_course_cover,md.getData().getCourse().getCover());
                        BaseApplication.getInstance().loadImageALLView(MasterDetailActivity.this,img_title_cover,md.getData().getUser().getImg_top());

                        uid = md.getData().getUser().getIs_follow();
                        user_id = md.getData().getUser().getUid();
                        if(md.getData().getUser().getIs_follow().equals("1")){
                            follow_detail.setText("已关注");
                        }else{
                            follow_detail.setText("关注");
                        }
                        //评论内容
                        DetailAdapter masterDetailAdapter = new DetailAdapter(MasterDetailActivity.this,md.getData().getComment_list());
                        rv_master_comment.setAdapter(masterDetailAdapter);
                        //分享列表
                        CourseAdapter courseAdapter = new CourseAdapter(MasterDetailActivity.this,md.getData().getShare().getDetail());
                        course_info_detail.setAdapter(courseAdapter);
                        //课程价格
                        tv_course_price.setText("￥"+md.getData().getCourse().getM_price());
                        //价格减免
                        tv_m_course_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        tv_m_course_price.setText("￥"+md.getData().getCourse().getPrice());
                        //课程标题
                        tv_master_detail_course_title.setText(md.getData().getCourse().getTitle());
                        //课程标签
                        if(md.getData().getCourse().getTags().get(0).equals("")){
                            tag1.setVisibility(View.GONE);
                        }else{
                            tag1.setText(md.getData().getCourse().getTags().get(0));
                        }
                        if(md.getData().getCourse().getTags().get(1).equals("")){
                            tag2.setVisibility(View.GONE);
                        }else{
                            tag2.setText(md.getData().getCourse().getTags().get(1));
                        }
                        if(md.getData().getCourse().getTags().get(2).equals("")){
                            tag2.setVisibility(View.GONE);
                        }else{
                            tag2.setText(md.getData().getCourse().getTags().get(2));
                        }
                        if(md.getData().getCourse().getTags().get(3).equals("")){
                            tag2.setVisibility(View.GONE);
                        }else{
                            tag2.setText(md.getData().getCourse().getTags().get(3));
                        }

                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void onClick(View v) {
         Intent intent;
        switch (v.getId()){
            case R.id.follow_detail :
                if(follow_detail.getText().toString().equals("关注")){
                    UserPublicActionUtils.getInstance().user_follow(MasterDetailActivity.this,uid);
                    follow_detail.setText("已关注");
                }else{
                    UserPublicActionUtils.getInstance().user_cancel_follow(MasterDetailActivity.this,uid);
                    follow_detail.setText("关注");
                }
                break;
            case R.id.tv_master_detail_look_comment :
                intent = new Intent(MasterDetailActivity.this,MasterCommentListActivity.class);
                intent.putExtra("share_id",share_id);
                startActivity(intent);
                break;
//            case R.id.iv_master_detail_logo :
//                intent = new Intent(MasterDetailActivity.this,BigManHomePageActivity.class);
//                intent.putExtra("uid",user_id);
//                startActivity(intent);
//                break;
        }

    }


}
