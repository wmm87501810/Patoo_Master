package com.shishiTec.HiMaster.UI.Activity.mall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shishiTec.HiMaster.Model.bean.CourseDetailBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.UserCenter.MyShareActivity;
import com.shishiTec.HiMaster.UI.Activity.bigManShare.BigManHomePageActivity;
import com.shishiTec.HiMaster.UI.Adapter.mall.*;
import com.shishiTec.HiMaster.UI.Views.OtherAddressPopupWindow;
import com.shishiTec.HiMaster.UI.Views.QuestionPopupWindow;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CourseDetailActivity extends BaseActivity implements View.OnClickListener {
    private Intent intent;
    private String course_id;
    private AutoRelativeLayout rl_coupon; //
    private AutoRelativeLayout ar_other_address;  //其他课程地址
    private AutoLinearLayout al_course_cfg; //套餐组合
    private AutoLinearLayout al_student;    //他们也想学
    private RelativeLayout rl_course_detail_question;//提问
    private ImageView course_personal_img_head;//用户头像
    private ImageView iv_course_cover;  //课程封面
    private TextView course_detail_about_her;//约她
    private TextView tv_course_title;   //课程标题
    private TextView tv_course_price;   //课程价格
    private TextView tv_course_coupon;  //优惠介绍
    private TextView tv_time_period;    //上课时间
    private TextView tv_address;        //上课地址
    private TextView tv_other;          //其他地址
    private TextView course_head_name;  //长版空间
    private TextView curriculum_tv;     //进阶课程
    //    private TextView tv_order_payment;  //约她
    private RecyclerView course_detail_recycler_ka; //卡
    private RecyclerView rv_course_cfg;             //套餐
    private RecyclerView rv_student;                //他们也想学
    private WebView web_course;
    private ImageView enterprise;//收藏按钮
    private ImageView gift;//分享按钮
    private RecyclerView course_like_recycle;//猜你喜欢底部列表
    private RecyclerView course_share_recycle;//分享列表
    private RecyclerView course_detail_recycler_curriculum;//进阶课程列表
    private CourseDetailBean courseDetailBeen;
    private LinearLayoutManager linearLayoutManager_like, linearLayoutManager_share, linearLayoutManager_curriculum;
    private TextView course_tv_share_num;
    private OtherAddressPopupWindow menuWindow;//选择地址和套餐的POP
    private QuestionPopupWindow questionWindow;//提问的POP
    private OtherAddressPopupAdapter otherAddressPopupAdapter;
    private PackagePopupAdapter packagePopupAdapter;
    private AutoRelativeLayout ar_rili;//日历页面跳转
    private ImageView left_back;//回退按钮
    private boolean is_collect;
    private AutoRelativeLayout ar_address;
    private AutoRelativeLayout ar_course_home_page;//个人主页跳转
    private AutoRelativeLayout share_num_AR;

    @Override
    protected void initViews() {
        intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        web_course = (WebView) findViewById(R.id.web_course);
        WebSettings webSettings = web_course.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        enterprise = (ImageView) findViewById(R.id.enterprise);
        gift = (ImageView) findViewById(R.id.gift);
        enterprise.setOnClickListener(this);
        gift.setOnClickListener(this);
        rv_student = (RecyclerView) findViewById(R.id.rv_student);
        left_back = (ImageView) findViewById(R.id.left_back);
        ar_rili = (AutoRelativeLayout) findViewById(R.id.ar_rili);
        ar_course_home_page = (AutoRelativeLayout) findViewById(R.id.ar_course_home_page);
        ar_address = (AutoRelativeLayout) findViewById(R.id.ar_address);
        share_num_AR = (AutoRelativeLayout) findViewById(R.id.share_num_AR);
        left_back.setOnClickListener(this);
        ar_rili.setOnClickListener(this);
        ar_address.setOnClickListener(this);
        share_num_AR.setOnClickListener(this);
        ar_course_home_page.setOnClickListener(this);
        rl_course_detail_question = (RelativeLayout) findViewById(R.id.rl_course_detail_question);
//        rl_course_detail_question = (AutoRelativeLayout) findViewById(R.id.rl_course_detail_question);
        course_like_recycle = (RecyclerView) findViewById(R.id.course_like_recycle);
        course_share_recycle = (RecyclerView) findViewById(R.id.course_share_recycle);
        course_detail_recycler_curriculum = (RecyclerView) findViewById(R.id.course_detail_recycler_curriculum);
        course_tv_share_num = (TextView) findViewById(R.id.course_tv_share_num);
        course_detail_about_her = (TextView) findViewById(R.id.course_detail_about_her);
        curriculum_tv = (TextView) findViewById(R.id.curriculum_tv);
        linearLayoutManager_like = new LinearLayoutManager(CourseDetailActivity.this);
        linearLayoutManager_share = new LinearLayoutManager(CourseDetailActivity.this);
        linearLayoutManager_curriculum = new LinearLayoutManager(CourseDetailActivity.this);
        courseDetailBeen = new CourseDetailBean();
        linearLayoutManager_like.setOrientation(LinearLayoutManager.HORIZONTAL);
        course_like_recycle.setLayoutManager(linearLayoutManager_like);
        course_like_recycle.setHasFixedSize(true);
        course_share_recycle.setLayoutManager(linearLayoutManager_share);
        course_detail_recycler_curriculum.setLayoutManager(linearLayoutManager_curriculum);
        initData(course_id);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initListener() {
        iv_course_cover = (ImageView) findViewById(R.id.iv_course_cover);
        tv_course_title = (TextView) findViewById(R.id.tv_course_title);
        tv_course_price = (TextView) findViewById(R.id.tv_course_price);
        tv_course_coupon = (TextView) findViewById(R.id.tv_course_coupon);
        tv_time_period = (TextView) findViewById(R.id.tv_time_period);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_other = (TextView) findViewById(R.id.tv_other);
        course_head_name = (TextView) findViewById(R.id.course_head_name);
//        tv_order_payment = (TextView) findViewById(R.id.tv_order_payment);
        ar_other_address = (AutoRelativeLayout) findViewById(R.id.ar_other_address);
        rl_coupon = (AutoRelativeLayout) findViewById(R.id.rl_coupon);

        al_student = (AutoLinearLayout) findViewById(R.id.al_student);
        al_student.setOnClickListener(this);
        al_course_cfg = (AutoLinearLayout) findViewById(R.id.al_course_cfg);

        course_personal_img_head = (ImageView) findViewById(R.id.course_personal_img_head);

        course_detail_recycler_ka = (RecyclerView) findViewById(R.id.course_detail_recycler_ka);
        rv_course_cfg = (RecyclerView) findViewById(R.id.rv_course_cfg);
        LinearLayoutManager contentManager = new LinearLayoutManager(this);
        contentManager.setOrientation(LinearLayoutManager.VERTICAL);
        //添加分割线
        course_detail_recycler_ka.setLayoutManager(contentManager);
        course_detail_recycler_ka.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager rv_course = new LinearLayoutManager(this);
        rv_course.setOrientation(LinearLayoutManager.VERTICAL);
        rv_course_cfg.setLayoutManager(rv_course);
        rv_course_cfg.setItemAnimator(new DefaultItemAnimator());

        //他们也想学
        LinearLayoutManager student_manager = new LinearLayoutManager(this);
        student_manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_student.setLayoutManager(student_manager);
        rv_student.setItemAnimator(new DefaultItemAnimator());
        rl_course_detail_question.setOnClickListener(this);
        course_detail_about_her.setOnClickListener(this);
    }

    private void initData(String course_id) {
        intent = getIntent();
        Map<String, String> map = new HashMap<>();
        map.put("course_id", course_id);
        Log.i("courseDetail", course_id);
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .courseDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<CourseDetailBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("courseDetail", e.getMessage() + "11");
                    }

                    @Override
                    public void onNext(BaseModel<CourseDetailBean> mb) {
                        if (mb.getCode() == 200) {
                            courseDetailBeen = mb.getData();
                            Log.i("courseDetail", courseDetailBeen.getCourse_advance().size() + "11");
                            if (courseDetailBeen.getIs_collect().equals("0")) {
                                is_collect = true;
                                enterprise.setImageResource(R.mipmap.shoucang);
                            } else {
                                is_collect = false;
                                enterprise.setImageResource(R.mipmap.shoucang_white);
                            }
                            //设置封面
                            BaseApplication.getInstance().loadImageALLView(CourseDetailActivity.this, iv_course_cover, courseDetailBeen.getCover());
                            tv_course_title.setText(courseDetailBeen.getTitle());
                            if (courseDetailBeen.getIs_mpay().equals("1")) {
                                tv_course_price.setText(courseDetailBeen.getM_price() + "  M点");
                            } else {
                                tv_course_price.setText("￥ " + courseDetailBeen.getPrice());
                            }
                            //课程优惠
                            if (courseDetailBeen.getCoupon() != null && courseDetailBeen.getCoupon().size() > 0) {
//                                tv_course_coupon.setText(courseDetailBeen.getCoupon());
                            } else {
                                rl_coupon.setVisibility(View.GONE);
                            }
                            //上课时间
                            tv_time_period.setText(courseDetailBeen.getTime_period());
                            //上课地址
                            tv_address.setText(courseDetailBeen.getAddress());
                            //其他地址
                            if (courseDetailBeen.getCourse_advance() != null && courseDetailBeen.getCourse_advance().size() > 0) {
//                                List<CourseAdvance> courseAdvanceList  = (List<CourseAdvance>)courseDetailBeen.getCourse_advance();

                            } else {
                                ar_other_address.setVisibility(View.GONE);
                            }
                            //卡
                            if (courseDetailBeen.getCard() != null && courseDetailBeen.getCard().size() > 0) {
                                CourseCardAdapter courseCardAdapter = new CourseCardAdapter(CourseDetailActivity.this, courseDetailBeen.getCard());
                                course_detail_recycler_ka.setAdapter(courseCardAdapter);
                                courseCardAdapter.setOnItemClickListener(new CourseCardAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position, Object object) {
                                        Intent intent = new Intent();
                                        intent.putExtra("card_id", courseDetailBeen.getCard().get(position).getCard_id());
                                        intent.setClass(CourseDetailActivity.this, MallCardDetailActtivity.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                course_detail_recycler_ka.setVisibility(View.GONE);
                            }
                            //套餐
                            if (courseDetailBeen.getCourse_cfg() != null && courseDetailBeen.getCourse_cfg().size() > 0) {
                                CourseCfgAdapter courseCfgAdapter = new CourseCfgAdapter(CourseDetailActivity.this, courseDetailBeen.getCourse_cfg());
                                rv_course_cfg.setAdapter(courseCfgAdapter);
                            } else {
                                al_course_cfg.setVisibility(View.GONE);
                            }
                            //他们也想学
                            Log.i("studentSize", "String" + courseDetailBeen.getStudent().get(0).getAddtime());
                            if (courseDetailBeen.getStudent() != null && courseDetailBeen.getStudent().size() > 0) {
                                StudentAdapter studentAdapter = new StudentAdapter(CourseDetailActivity.this, courseDetailBeen.getStudent());
                                rv_student.setAdapter(studentAdapter);
                            } else {
                                al_student.setVisibility(View.GONE);
                            }

                            //猜你喜欢
                            if (courseDetailBeen.getGuess_like() != null && courseDetailBeen.getGuess_like().size() > 0) {
                                CourseDetailLikeItemAdapter courseDetailLikeItemAdapter = new CourseDetailLikeItemAdapter(CourseDetailActivity.this, courseDetailBeen.getGuess_like());
                                course_like_recycle.setAdapter(courseDetailLikeItemAdapter);
                            } else {
                                course_like_recycle.setVisibility(View.GONE);
                            }

                            //用户名称
                            course_head_name.setText(courseDetailBeen.getNikename());
                            //用户头像
                            BaseApplication.getInstance().loadImageALLView(CourseDetailActivity.this, course_personal_img_head, courseDetailBeen.getImg_top());
                            if (courseDetailBeen.getCourse_cfg() != null && courseDetailBeen.getCourse_cfg().size() > 0) {
//                                CourseCfgAdapter courseCfgAdapter = new CourseCfgAdapter(CourseDetailActivity.this,(List<CourseCfgBean>)courseDetailBeen.getCourse_cfg());
//                                rv_course_cfg.setAdapter(courseCfgAdapter);
                            } else {
                                al_course_cfg.setVisibility(View.GONE);
                            }
                            //课程
                            Log.i("studentSize", "String" + courseDetailBeen.getStudent().get(0).getAddtime());
                            //猜你喜欢
                            CourseDetailLikeItemAdapter courseDetailLikeItemAdapter = new CourseDetailLikeItemAdapter(CourseDetailActivity.this, courseDetailBeen.getGuess_like());
                            course_like_recycle.setAdapter(courseDetailLikeItemAdapter);
                            //分享
                            CourseShareItemAdapter courseShareItemAdapter = new CourseShareItemAdapter(CourseDetailActivity.this, courseDetailBeen.getShare().getList());
                            course_share_recycle.setAdapter(courseShareItemAdapter);
                            course_tv_share_num.setText("分享（" + courseDetailBeen.getShare().getShare_count() + "）");
                            //他们也想学
                            if (courseDetailBeen.getCourse_advance().size() > 0) {
                                CourseDetailCurriculumAdapter courseDetailCurriculumAdapter = new CourseDetailCurriculumAdapter(CourseDetailActivity.this, courseDetailBeen.getCourse_advance());
                                course_detail_recycler_curriculum.setAdapter(courseDetailCurriculumAdapter);
                            } else {
                                curriculum_tv.setVisibility(View.GONE);
                            }
                            //webView嵌套
                            if (courseDetailBeen.getDetail_link() != "") {
                                web_course.setWebViewClient(mWebViewClientBase);
                                web_course.setWebChromeClient(mWebChromeClientBase);
                                web_course.loadUrl(courseDetailBeen.getDetail_link());
                                web_course.onResume();
                            }
                            //其他课程的Pop
                            if (courseDetailBeen.getCourse_store().size() > 0) {
                                ar_other_address.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        menuWindow = new OtherAddressPopupWindow(CourseDetailActivity.this, itemsOnClick);
                                        menuWindow.showAsDropDown(CourseDetailActivity.this.findViewById(R.id.iv_course_cover));
                                        otherAddressPopupAdapter = new OtherAddressPopupAdapter(CourseDetailActivity.this, courseDetailBeen.getCourse_store());
                                        menuWindow.choose_other_address_recycler.setAdapter(otherAddressPopupAdapter);
                                        otherAddressPopupAdapter.setOnItemClickListener(new OtherAddressPopupAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(int position, Object object) {
                                                initData(courseDetailBeen.getCourse_store().get(position).getCourse_id());
                                                menuWindow.dismiss();
                                            }
                                        });
                                    }
                                });

                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.course_detail_about_her:
                if (courseDetailBeen != null) {
                    if (courseDetailBeen.getIs_mpay().equals("0")) {
                        if (courseDetailBeen.getCourse_cfg().size() > 0) {
                            menuWindow = new OtherAddressPopupWindow(CourseDetailActivity.this, itemsOnClick);
                            menuWindow.showAsDropDown(CourseDetailActivity.this.findViewById(R.id.iv_course_cover));
                            packagePopupAdapter = new PackagePopupAdapter(CourseDetailActivity.this, courseDetailBeen.getCourse_cfg());
                            menuWindow.choose_other_address_recycler.setAdapter(packagePopupAdapter);
                            packagePopupAdapter.setOnItemClickListener(new PackagePopupAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position, Object object) {
                                    Intent intent = new Intent(CourseDetailActivity.this, CalendarActivity.class);
                                    intent.putExtra("course_id", courseDetailBeen.getCourse_id());
                                    intent.putExtra("course_cfg_id", courseDetailBeen.getCourse_cfg().get(position).getCourse_cfg_id());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            OrderPay("0");
                        }
                    } else if (courseDetailBeen.getIs_mpay().equals("1")) {
                        OrderPay("1");
                    }
                } else {
                    return;
                }
                break;
            case R.id.rl_course_detail_question:
                if (questionWindow == null) {
                    questionWindow = new QuestionPopupWindow(CourseDetailActivity.this, itemsOnClick);
                }
                questionWindow.showAtLocation(CourseDetailActivity.this.findViewById(R.id.course_detail_all), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
            case R.id.enterprise:
                enterprises();//是否收藏
                break;
            case R.id.gift:
                showShare();
                break;
            case R.id.ar_rili:
                if (courseDetailBeen.getCourse_cfg().size() > 0) {
                    menuWindow = new OtherAddressPopupWindow(CourseDetailActivity.this, itemsOnClick);
                    menuWindow.showAsDropDown(CourseDetailActivity.this.findViewById(R.id.iv_course_cover));
                    packagePopupAdapter = new PackagePopupAdapter(CourseDetailActivity.this, courseDetailBeen.getCourse_cfg());
                    menuWindow.choose_other_address_recycler.setAdapter(packagePopupAdapter);
                    packagePopupAdapter.setOnItemClickListener(new PackagePopupAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, Object object) {
                            Intent intent = new Intent(CourseDetailActivity.this, CalendarActivity.class);
                            intent.putExtra("course_id", courseDetailBeen.getCourse_id());
                            intent.putExtra("course_cfg_id", courseDetailBeen.getCourse_cfg().get(position).getCourse_cfg_id());
                            startActivity(intent);
                        }
                    });
                } else {

                }
                break;
            case R.id.left_back:
                CourseDetailActivity.this.finish();
                break;
            case R.id.ar_address:
                Intent intent = new Intent();
                if (courseDetailBeen != null && !courseDetailBeen.getLatitude().equals("") && !courseDetailBeen.getLongitude().equals("")) {
                    intent.putExtra("latitude", courseDetailBeen.getLatitude());
                    intent.putExtra("longitude", courseDetailBeen.getLongitude());
                }
                intent.setClass(CourseDetailActivity.this, MallMapActivity.class);
                startActivity(intent);
                break;
            case R.id.al_student:
                Intent intent1 = new Intent();
                intent1.putExtra("course_id", course_id);
                intent1.setClass(CourseDetailActivity.this, MallOtherStudyActivity.class);
                startActivity(intent1);
                break;
            case R.id.ar_course_home_page:
                Intent intent2 = new Intent();
                intent2.putExtra("uid", courseDetailBeen.getUid());
                intent2.setClass(CourseDetailActivity.this, BigManHomePageActivity.class);
                startActivity(intent2);
                break;
            case R.id.share_num_AR:
                startActivity(new Intent(CourseDetailActivity.this, MyShareActivity.class));
                break;
        }
    }

    private void OrderPay(String s) {
        Intent intent = new Intent(CourseDetailActivity.this, OrderPaymentActivity.class);
        intent.putExtra("course_id", courseDetailBeen.getCourse_id());
        intent.putExtra("course_cfg_id", "");
        intent.putExtra("is_mpay", s);
        startActivity(intent);
    }


    private void enterprises() {
        Map<String, String> map = new HashMap<>();
        map.put("course_id", course_id);
        params.setData(map);
        if (is_collect) {
            Subscription subscribe = RetrofitManager.getmInstance()
                    .createService()
                    .collect(params)
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
                        public void onNext(BaseModel mb) {
                            if (mb.getCode() == 200) {
                                enterprise.setImageResource(R.mipmap.shoucang);
                                is_collect = false;
                                Toast.makeText(CourseDetailActivity.this, mb.getMsg(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Subscription subscribe = RetrofitManager.getmInstance()
                    .createService()
                    .cancelCollect(params)
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
                        public void onNext(BaseModel mb) {
                            if (mb.getCode() == 200) {
                                enterprise.setImageResource(R.mipmap.shoucang_white);
                                is_collect = true;
                                Toast.makeText(CourseDetailActivity.this, mb.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(courseDetailBeen.getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(courseDetailBeen.getDescription());
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

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ar_question_popup_phone:
                    Intent myInt = new Intent("android.intent.action.CALL", Uri.parse("tel:" + courseDetailBeen.getCourse_mobile()));
                    startActivity(myInt);
                    break;
                case R.id.ar_question_popup_private_letter:

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void onPause() {
        super.onPause();
        web_course.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        web_course.destroy();
    }

    private WebViewClientBase mWebViewClientBase = new WebViewClientBase();

    private class WebViewClientBase extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url,
                                           boolean isReload) {
            // TODO Auto-generated method stub
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    private WebChromeClientBase mWebChromeClientBase = new WebChromeClientBase();

    private class WebChromeClientBase extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            activity.setProgress(newProgress * 1000);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            // TODO Auto-generated method stub
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url,
                                           boolean precomposed) {
            // TODO Auto-generated method stub
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            // TODO Auto-generated method stub
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            CourseDetailActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
