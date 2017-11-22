package com.shishiTec.HiMaster.UI.Activity.bigManShare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Adapter.BigManShareDetailItemAdapter;
import com.shishiTec.HiMaster.Model.bean.ShareDetailBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Views.LoginDialogView;
import com.shishiTec.HiMaster.UI.Views.LooperViewPager;
import com.shishiTec.HiMaster.UI.Views.fullScreenDialog;
import com.shishiTec.HiMaster.Utils.LoginUtil;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BigManShareDetailsActivity extends BaseActivity {
    private String share_id;
    private LooperViewPager bigman_sharedetail_viewPager;
    private ImageView logo, detail_relevant_img, detail_zamiba, detail_share_all, left_back;
    private TextView name, detail_content, detail_comment_count, bigman_detail_userContent, detail_relevant_name, detail_relevant_address, detail_follow, detail_zambia_status;
    private RecyclerView detail_recyle;
    private BigManShareDetailItemAdapter detailItemAdapter;
    private AutoLinearLayout detail_comment_more;
    public int count;
    private fullScreenDialog fullScreenDialog;
    private FragmentManager fragmentManager;
    private ShareDetailBean shaerdetail;
    private LoginDialogView confirmDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_big_man__share_details;
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        fragmentManager = getSupportFragmentManager();
//        Bundle bunde = this.getIntent().getExtras();
        Intent intent = getIntent();
        share_id = intent.getStringExtra("share_id");
        bigman_sharedetail_viewPager = (LooperViewPager) findViewById(R.id.bigman_sharedetail_viewPager);
        left_back = (ImageView) findViewById(R.id.left_back);
        logo = (ImageView) findViewById(R.id.bigman_detail_logo);
        detail_share_all = (ImageView) findViewById(R.id.detail_share_all);
        detail_relevant_img = (ImageView) findViewById(R.id.detail_relevant_img);
        detail_zamiba = (ImageView) findViewById(R.id.detail_zamiba);
        detail_comment_more = (AutoLinearLayout) findViewById(R.id.detail_comment_more);
        name = (TextView) findViewById(R.id.bigman_detail_name);
        detail_follow = (TextView) findViewById(R.id.detail_follow);
        detail_content = (TextView) findViewById(R.id.detail_content);
        detail_zambia_status = (TextView) findViewById(R.id.detail_zambia_status);
        detail_comment_count = (TextView) findViewById(R.id.detail_comment_count);
        detail_relevant_name = (TextView) findViewById(R.id.detail_relevant_name);
        detail_relevant_address = (TextView) findViewById(R.id.detail_relevant_address);
        detail_recyle = (RecyclerView) findViewById(R.id.detail_recyle);
        linearLayoutManager = new LinearLayoutManager(BigManShareDetailsActivity.this);
        detail_recyle.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        detail_recyle.setHasFixedSize(true);
        initData(share_id);
    }

    private void initData(String _id) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("share_id", _id);
        DeviceParams deviceParams = new DeviceParams();
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        RetrofitManager.getmInstance().createService()
                .BigManShartDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<ShareDetailBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<ShareDetailBean> BigManBaseModel) {
                        if (BigManBaseModel.getCode() == 200) {
                            shaerdetail = BigManBaseModel.getData();
                            count = Integer.parseInt(shaerdetail.getShare().getComment_count());
                            baseApplication.loadRoundImageView(BigManShareDetailsActivity.this, logo, shaerdetail.getUser().getImg_top());
                            name.setText(shaerdetail.getUser().getNikename().trim());
                            bigman_sharedetail_viewPager.setAdapter(new LooperViewPagerAdapter(BigManShareDetailsActivity.this, shaerdetail.getShare().getDetail()));
                            bigman_sharedetail_viewPager.start();
                            detail_comment_count.setText("评论：（" + shaerdetail.getShare().getComment_count().trim() + "）");
                            detail_content.setText(shaerdetail.getShare().getContent().trim());
                            detailItemAdapter = new BigManShareDetailItemAdapter(BigManShareDetailsActivity.this, shaerdetail.getComment_list());
                            detail_recyle.setAdapter(detailItemAdapter);
                            detail_relevant_name.setText(shaerdetail.getCourse().getTitle().trim());
                            detail_relevant_address.setText(shaerdetail.getCourse().getStore().trim() + "  " + shaerdetail.getCourse().getDistance().trim());
                            baseApplication.loadImageALLView(BigManShareDetailsActivity.this, detail_relevant_img, shaerdetail.getCourse().getCover());
                            final int follow = Integer.parseInt(shaerdetail.getUser().getIs_follow());
                            detail_zambia_status.setText(shaerdetail.getShare().getIs_like());
                            detail_zamiba.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (SharedPreferencesUtil.getInstance().isLogin()) {
                                        Zmbia(shaerdetail.getShare().getShare_id());
                                    } else {
                                        LoginUtil.getInstance().login(BigManShareDetailsActivity.this);
                                    }
                                }
                            });
                            detail_share_all.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    showShare(shaerdetail);
                                }
                            });
                            if (follow == 1) {
                                detail_follow.setText("已关注");
                            } else {
                                detail_follow.setText("关注");
                            }
                            detail_follow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String follow = detail_follow.getText().toString();
                                    if (SharedPreferencesUtil.getInstance().isLogin()) {
                                        if (follow == "关注" || follow.equals("关注")) {
                                            user_follow(shaerdetail.getUser().getUid());
                                        } else {
                                            user_cancle_follow(shaerdetail.getUser().getUid());
                                        }
                                    } else {
                                        LoginUtil.getInstance().login(BigManShareDetailsActivity.this);
                                    }
                                }
                            });
                        } else {

                        }
                    }
                });
    }

    private void showShare(ShareDetailBean shaerdetail) {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(shaerdetail.getShare().getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shaerdetail.getShare().getContent());
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

    private void Zmbia(String like_id) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("share_id", like_id);
        DeviceParams deviceParams = new DeviceParams();
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        int status = Integer.parseInt(detail_zambia_status.getText().toString());
        if (status == 1) {
            RetrofitManager.getmInstance().createService()
                    .BigManCancle_Zambia(params)
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
                        public void onNext(BaseModel BigManBaseModel) {
                            if (BigManBaseModel.getCode() == 200) {
                                detail_zambia_status.setText("0");
                                ToastUtils.showGravity(BigManShareDetailsActivity.this, "取消点赞成功", Gravity.CENTER, 20, 200);
                            } else {
                                ToastUtils.showGravity(BigManShareDetailsActivity.this, "取消点赞失败", Gravity.CENTER, 20, 200);
                            }
                        }
                    });
        } else if (status == 0) {
            RetrofitManager.getmInstance().createService()
                    .BigManZambia(params)
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
                        public void onNext(BaseModel BigManBaseModel) {
                            if (BigManBaseModel.getCode() == 200) {
                                detail_zambia_status.setText("1");
                                ToastUtils.showGravity(BigManShareDetailsActivity.this, "点赞成功", Gravity.CENTER, 20, 200);
                            } else {
                                ToastUtils.showGravity(BigManShareDetailsActivity.this, "点赞失败", Gravity.CENTER, 20, 200);
                            }
                        }
                    });
        }
    }

    private void user_cancle_follow(String id) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("fid", id);
        DeviceParams deviceParams = new DeviceParams();
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        RetrofitManager.getmInstance().createService()
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
                    public void onNext(BaseModel BigManBaseModel) {
                        if (BigManBaseModel.getCode() == 200) {
                            detail_follow.setText("关注");
                            ToastUtils.showGravity(BigManShareDetailsActivity.this, "已成功取消关注", Gravity.CENTER, 20, 200);
                        } else {
                            ToastUtils.showGravity(BigManShareDetailsActivity.this, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 200);
                        }
                    }
                });
    }

    private void user_follow(String id) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("fid", id);
        DeviceParams deviceParams = new DeviceParams();
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        RetrofitManager.getmInstance().createService()
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
                    public void onNext(BaseModel BigManBaseModel) {
                        if (BigManBaseModel.getCode() == 200) {
                            detail_follow.setText("已关注");
                            ToastUtils.showGravity(BigManShareDetailsActivity.this, "已成功关注", Gravity.CENTER, 20, 200);
                        } else {
                            ToastUtils.showGravity(BigManShareDetailsActivity.this, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 200);
                        }
                    }
                });
    }


    @Override
    protected void initListener() {
        detail_comment_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BigManShareDetailsActivity.this, bigManCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("share_id", share_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("uid", shaerdetail.getUser().getUid());
                intent.setClass(BigManShareDetailsActivity.this, BigManHomePageActivity.class);
                startActivity(intent);
            }
        });
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigManShareDetailsActivity.this.finish();
            }
        });
    }

    private class LooperViewPagerAdapter extends PagerAdapter {

        private final List<ShareDetailBean.ShareBean.DetailBean> mList;
        private final Context mContext;

        public LooperViewPagerAdapter(Context context, List<ShareDetailBean.ShareBean.DetailBean> list) {
            this.mList = list;
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(mContext);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fullScreenDialog = new fullScreenDialog(BigManShareDetailsActivity.this, R.style.Dialog_Fullscreen, mList);
                    fullScreenDialog.show();
                }
            });
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);
//            ImageUtil.getInstance().setImage(iv,mList.get(position).getImg());
            BaseApplication.getInstance().loadImageALLView(mContext, iv, mList.get(position).getUrl());
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (container != null && object != null)
                container.removeView((View) object);
        }
    }
}
