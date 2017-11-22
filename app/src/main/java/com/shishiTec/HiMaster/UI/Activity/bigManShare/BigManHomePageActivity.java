package com.shishiTec.HiMaster.UI.Activity.bigManShare;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.UserHomePageBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.mall.MyHomePageShareAdapter;
import com.shishiTec.HiMaster.UI.Adapter.master.HomePagerShareAdapter;
import com.shishiTec.HiMaster.UI.Views.LoopViewPager;
import com.shishiTec.HiMaster.Utils.UserPublicActionUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BigManHomePageActivity extends BaseActivity implements View.OnClickListener,Animator.AnimatorListener{
    private String uid;
    //主页公共部分
    private ImageView left_back;//后退按钮
    private ImageView gift;//分享按钮
    private ImageView title_cover;//用户cover
    private TextView title_name;//用户名字
    private TextView Zambia_home_page_num;//点赞数量
    private TextView browse_num;//浏览数量
    private ImageView img_title_cover;//背景图片
    private UserHomePageBean userHomePageBean;
    //牛人主页View
    private RecyclerView include_big_man_home_recycle;//牛人分享列表
    //底部事件
    private RelativeLayout rl_sixin, rl_guanzhu, rl_dianzan;
    private TextView guanzhu, dianzan;

    private LinearLayout ll_my_share;   //达人视频
    private LoopViewPager video_loop;   //达人视频

    //分享、课程、达人经历
    private ObjectAnimator oa;
    private int index = 0;

    private TextView tv_course;    //课程
    private TextView tv_jingli;    //经历
    private TextView tv_share;     //分享
    private EditText et_add_tag;          //搜索
    private View navigationLine;          //导航按钮下划线

    private RecyclerView rv_share;  //课程内容
    private RecyclerView rv_course;    //分享内容
//    private RecyclerView experienceFragment;  //经历内容


    @Override
    public int getLayoutId() {
        return R.layout.activity_big_man_home_page;
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        left_back = (ImageView) findViewById(R.id.left_back);
        gift = (ImageView) findViewById(R.id.gift);
        rl_sixin = (RelativeLayout) findViewById(R.id.rl_sixin);
        rl_guanzhu = (RelativeLayout) findViewById(R.id.rl_guanzhu);
        rl_dianzan = (RelativeLayout) findViewById(R.id.rl_dianzan);
        guanzhu = (TextView) findViewById(R.id.guanzhu);
        dianzan = (TextView) findViewById(R.id.dianzan);
        img_title_cover = (ImageView) findViewById(R.id.img_title_cover);
        title_cover = (ImageView) findViewById(R.id.title_cover);
        title_name = (TextView) findViewById(R.id.title_name);
        Zambia_home_page_num = (TextView) findViewById(R.id.Zambia_home_page_num);
        browse_num = (TextView) findViewById(R.id.browse_num);
        video_loop = (LoopViewPager) findViewById(R.id.video_loop);

        include_big_man_home_recycle = (RecyclerView) findViewById(R.id.include_big_man_home_recycle);
        rv_share = (RecyclerView) findViewById(R.id.rv_share);
        rv_course = (RecyclerView) findViewById(R.id.rv_course);

        ll_my_share = (LinearLayout) findViewById(R.id.ll_my_share);

        tv_share = (TextView) findViewById(R.id.tv_search_kecheng);
        tv_course = (TextView) findViewById(R.id.tv_search_person);
        tv_jingli = (TextView) findViewById(R.id.tv_search_share);
        navigationLine = findViewById(R.id.navigationLine);

        initRecyclerManager();
        initData();
    }

    private void initRecyclerManager(){
        LinearLayoutManager manager = new LinearLayoutManager(BigManHomePageActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        include_big_man_home_recycle.setLayoutManager(manager);

        LinearLayoutManager share = new LinearLayoutManager(BigManHomePageActivity.this);
        share.setOrientation(LinearLayoutManager.VERTICAL);
        rv_share.setLayoutManager(share);

        LinearLayoutManager course = new LinearLayoutManager(BigManHomePageActivity.this);
        course.setOrientation(LinearLayoutManager.VERTICAL);
        rv_course.setLayoutManager(course);
    }

    @Override
    protected void initListener() {
        gift.setOnClickListener(this);
        rl_sixin.setOnClickListener(this);
        left_back.setOnClickListener(this);
        rl_guanzhu.setOnClickListener(this);
        rl_dianzan.setOnClickListener(this);

        tv_share.setOnClickListener(this);
        tv_jingli.setOnClickListener(this);
        tv_course.setOnClickListener(this);
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("uid_other", uid);
        bigManShare(map);
    }

    private void  bigManShare(Map<String,String> map){
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .userOther(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<UserHomePageBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("courseDetail", e.getMessage() + "11");
                    }

                    @Override
                    public void onNext(BaseModel<UserHomePageBean> mb) {
                        if (mb.getCode() == 200) {
                            userHomePageBean = mb.getData();
                            BaseApplication.getInstance().loadImageALLView(BigManHomePageActivity.this, title_cover, userHomePageBean.getImg_top());
                            BaseApplication.getInstance().loadImageALLView(BigManHomePageActivity.this, img_title_cover, userHomePageBean.getCover());
                            title_name.setText(userHomePageBean.getNikename());
                            Zambia_home_page_num.setText(userHomePageBean.getBy_like_num());
                            browse_num.setText(userHomePageBean.getBy_browse_num());
                            if (userHomePageBean.getIs_like().equals("0")) {
                                dianzan.setText("点赞");
                            } else {
                                dianzan.setText("已赞");
                            }
                            if (userHomePageBean.getIs_follow().equals("0")) {
                                guanzhu.setText("关注");
                            } else {
                                guanzhu.setText("已关注");
                            }
                            if (userHomePageBean.getShare_lists().size() > 0) {
                                MyHomePageShareAdapter myHomePageShareAdapter = new MyHomePageShareAdapter(BigManHomePageActivity.this, userHomePageBean.getShare_lists());
                                include_big_man_home_recycle.setAdapter(myHomePageShareAdapter);
                            }

                            //达人视频
                            MasterShareAdapter masterShareAdapter = new MasterShareAdapter(BigManHomePageActivity.this,userHomePageBean.getVideo());
                            video_loop.setAdapter(masterShareAdapter);
                            video_loop.setEnabled(false);
                            //达人分享
                            HomePagerShareAdapter masterHomePagerShareAdapter = new HomePagerShareAdapter(BigManHomePageActivity.this,userHomePageBean.getShare_lists());
                            rv_share.setAdapter(masterHomePagerShareAdapter);
                            rv_course.setVisibility(View.GONE);

                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                this.finish();
                break;
            case R.id.gift:
                showShare();
                break;
            case R.id.rl_sixin:
                break;
            case R.id.rl_dianzan:
                if (userHomePageBean.getIs_like().equals("0")) {
                    UserPublicActionUtils.getInstance().user_zambia(BigManHomePageActivity.this, uid, dianzan);
                } else {
                    UserPublicActionUtils.getInstance().user_cancel_zambia(BigManHomePageActivity.this, uid, dianzan);
                }
                break;
            case R.id.rl_guanzhu:
                if (userHomePageBean.getIs_follow().equals("0")) {
                    UserPublicActionUtils.getInstance().user_follow(BigManHomePageActivity.this, uid);
                    guanzhu.setText("已关注");
                } else {
                    UserPublicActionUtils.getInstance().user_cancel_follow(BigManHomePageActivity.this, uid);
                    guanzhu.setText("关注 ");
                }
                break;
            case R.id.tv_search_kecheng:
            case R.id.tv_search_person:
            case R.id.tv_search_share:
                startAnimator(view);
                setCurrentFragment(view.getId());
                break;
        }
    }


    /**
     * 设置当前Fragment
     * @param id
     */
    private void setCurrentFragment(int id){
        FragmentTransaction trasection = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        switch (id){
            case R.id.tv_search_kecheng:
                //切换到分享
                break;
            case R.id.tv_search_person:
                //切换到课程
                break;
            case R.id.tv_search_share:
                //切换到经历
                break;
        }
        trasection.commitAllowingStateLoss();
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(userHomePageBean.getNikename());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(userHomePageBean.getCategory_name());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }

    public class MasterShareAdapter extends PagerAdapter{
        private Context context;
        private List<UserHomePageBean.VideoBean> list;

        public MasterShareAdapter(Context context, List<UserHomePageBean.VideoBean> list){
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size()>0?list.size():0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.adapter_master_share,null);
            TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
            JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) view.findViewById(R.id.video_play);
            position = (list.size() + position%list.size())%list.size();
            tv_content.setText(list.get(position).getDesc());
            jcVideoPlayerStandard.setUp("http://kaifa.gomaster.cn/attms/"+list.get(position).getUrl(),"达人视频");
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (container != null && object != null)
                container.removeView((View) object);
        }

    }

    /**
     * 导航按钮切换效果动画
     */
    private void startAnimator(View v){
        float startX = 0;
        if(index == 0){
            startX = tv_share.getX();
        }else if(index == 1){
            startX = tv_course.getX();
        }else if(index == 2){
            startX = tv_jingli.getX();
        }
        if (oa == null || !oa.isRunning()) {
            if (v.getId() == R.id.tv_search_kecheng) {
                if(index == 0) return;
                oa = ObjectAnimator.ofFloat(navigationLine, "translationX", startX,tv_share.getX());
                index = 0;
            } else if(v.getId() == R.id.tv_search_person){
                if(index == 1) return;
                oa = ObjectAnimator.ofFloat(navigationLine, "translationX", startX,tv_course.getX());
                index = 1;
            }else if(v.getId() == R.id.tv_search_share){
                if(index == 2) return;
                oa = ObjectAnimator.ofFloat(navigationLine, "translationX", startX,tv_jingli.getX());
                index = 2;
            }
            oa.addListener(this);
            oa.setDuration(500);
            oa.start();
        }
    }

    /**
     * ViewPager页面选择
     */
    private void selectedPage(){
        switch (index){
            case 0:
                tv_share.setTextColor(getResources().getColor(R.color.home_page_red));
                tv_course.setTextColor(getResources().getColor(R.color.black_ligh));
                tv_jingli.setTextColor(getResources().getColor(R.color.black_ligh));
                break;
            case 1:
                tv_course.setTextColor(getResources().getColor(R.color.home_page_red));
                tv_share.setTextColor(getResources().getColor(R.color.black_ligh));
                tv_jingli.setTextColor(getResources().getColor(R.color.black_ligh));
                break;
            case 2:
                tv_jingli.setTextColor(getResources().getColor(R.color.home_page_red));
                tv_share.setTextColor(getResources().getColor(R.color.black_ligh));
                tv_course.setTextColor(getResources().getColor(R.color.black_ligh));
                break;
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        selectedPage();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

}
