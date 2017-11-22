package com.shishiTec.HiMaster.UI.fragment.master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shishiTec.HiMaster.Model.bean.MasterListTagBean;
import com.shishiTec.HiMaster.Model.bean.MasterListsBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.WebViewActivity;
import com.shishiTec.HiMaster.UI.Activity.bigManShare.BigManHomePageActivity;
import com.shishiTec.HiMaster.UI.Activity.mall.CourseDetailActivity;
import com.shishiTec.HiMaster.UI.Activity.master.MasterDetailActivity;
import com.shishiTec.HiMaster.UI.Adapter.master.ContentAdapter;
import com.shishiTec.HiMaster.UI.Adapter.master.HotMasterAdapter;
import com.shishiTec.HiMaster.UI.Views.LooperViewPager;
import com.shishiTec.HiMaster.UI.Views.MyScrollView;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.Utils.WebUtils;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseFragment;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hyu on 2016/4/29.
 */
public class contentFragment extends BaseFragment {
    //    private TabLayout tl_master;//分类列表
    //    private ViewPager vp_master;//分类列表部分

    private AutoFrameLayout af_looper;//轮播整体
    private LooperViewPager lv_master;//轮播部分
    private CircleIndicator ci_master;//轮播指示器
    private RecyclerView rv_hot_master;//热门达人
    private RecyclerView rv_content;  //内容详情
    private MyScrollView ns_master;
    private SuperSwipeRefreshLayout swipeLayout;
    private String page = "1";
    private String page_size = "10";
    private String category_id = "-1";
    private AutoLinearLayout al_hot_master;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_master_content;
    }


    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        category_id = getArguments().getString("category_id");
        af_looper = (AutoFrameLayout) getContentView().findViewById(R.id.af_looper);
        al_hot_master = (AutoLinearLayout) getContentView().findViewById(R.id.al_hot_master);
        lv_master = (LooperViewPager) getContentView().findViewById(R.id.lv_master);
        ci_master = (CircleIndicator) getContentView().findViewById(R.id.ci_master);
        rv_content = (RecyclerView) getContentView().findViewById(R.id.rv_content);
        rv_hot_master = (RecyclerView) getContentView().findViewById(R.id.rv_hot_master);
        ns_master = (MyScrollView) getContentView().findViewById(R.id.ns_master);
        swipeLayout = (SuperSwipeRefreshLayout) getContentView().findViewById(R.id.swipeLayout);
        initRecyclerView();
        Map<String, String> map = new HashMap<>();
        map.put("category_id", category_id);
        map.put("page", page);
        map.put("page_size", page_size);
        //分享列表初始化
        masterShareList(map);
        Log.i("masterCategory", category_id);
        swipeLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                Map<String, String> map = new HashMap<>();
                map.put("category_id", category_id + "");
                map.put("page", page + "");
                map.put("page_size", page_size + "");
                //分享列表初始化
                Log.i("masterDDD", "分享列表");
                masterShareList(map);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
    }


    /**
     * 初始化列表项
     */
    private void initRecyclerView() {
        LinearLayoutManager hotManager = new LinearLayoutManager(getActivity());
        hotManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //热门达人
        rv_hot_master.setLayoutManager(hotManager);
        rv_hot_master.setItemAnimator(new DefaultItemAnimator());

        //达人分享
        LinearLayoutManager contentManager = new LinearLayoutManager(getActivity());
        contentManager.setOrientation(LinearLayoutManager.VERTICAL);
        //添加分割线
//        rv_content.addItemDecoration(new DividerItemDecoration(
//                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        rv_content.setLayoutManager(contentManager);
        rv_content.setItemAnimator(new DefaultItemAnimator());
        //分类
        LinearLayoutManager typeManager = new LinearLayoutManager(getActivity());
        typeManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        Log.i("masterDDD", "初始化列表");
    }

    /**
     * 轮播适配器
     */
    private class LooperViewPagerAdapter extends PagerAdapter {

        private final List<MasterListsBean.BannerListBean> mList;
        private final Context mContext;

        public LooperViewPagerAdapter(Context context, List<MasterListsBean.BannerListBean> list) {
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
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);
            position = (mList.size() + position % mList.size()) % mList.size();
            BaseApplication.getInstance().loadImageALLView(mContext, iv, mList.get(position).getPic_url());
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MasterListsBean.BannerListBean bean = (MasterListsBean.BannerListBean) view.getTag();
                    Intent intent = new Intent();
                    String url = WebUtils.getInstance().getMasterUrl(bean.getPfurl());
                    switch (url.substring(0, 1)) {
                        case "1":
                            intent.putExtra("course_id",url.substring(1));
                            intent.setClass(getActivity(), CourseDetailActivity.class);
                            break;
                        case "2":
                            intent.putExtra("uid",url.substring(1));
                            intent.setClass(getActivity(), BigManHomePageActivity.class);
                            break;
                        case "3":
                            intent.putExtra("share_id",url.substring(1));
                            intent.setClass(getActivity(), MasterDetailActivity.class);
                            break;
                        case "4":
                            intent.putExtra("share_id",url.substring(1));
                            intent.setClass(getActivity(), CourseDetailActivity.class);
                            break;
                        default:
                            intent.putExtra("url", bean.getPfurl());
                            intent.setClass(getActivity(), WebViewActivity.class);
                            break;
                    }
                    startActivity(intent);
                }
            });
            MasterListsBean.BannerListBean bean = mList.get(position);
            iv.setTag(bean);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (container != null && object != null)
                container.removeView((View) object);
        }
    }

    /**
     * 达人分享列表
     *
     * @param map 请求参数
     */
    public void masterShareList(Map<String, String> map) {
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .masterList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<MasterListsBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<MasterListsBean> masterListsBean) {
                        Log.i("masterCategory", "请求成功");
                        if (masterListsBean.getData() != null) {
                            //Banner
                            if (masterListsBean.getData().getBanner_list() == null || masterListsBean.getData().getBanner_list().size() <= 0) {
                                af_looper.setVisibility(View.GONE);
                            } else {
                                LooperViewPagerAdapter looperViewPagerAdapter = new LooperViewPagerAdapter(getContext(), masterListsBean.getData().getBanner_list());
                                lv_master.setAdapter(looperViewPagerAdapter);
                            }

                            //热门达人
                            if (masterListsBean.getData().getMaster_list() == null || masterListsBean.getData().getMaster_list().size() <= 0) {
                                al_hot_master.setVisibility(View.GONE);
                            } else {
                                HotMasterAdapter masterHotAdapter = new HotMasterAdapter(getContext(), masterListsBean.getData().getMaster_list());
                                rv_hot_master.setAdapter(masterHotAdapter);
                            }
                            //达人分享内容
                            ContentAdapter masterContentAdapter = new ContentAdapter(getContext(), masterListsBean.getData().getShare_list());
                            masterContentAdapter.setOnItemClickListener(new ContentAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position, Object object) {
                                    MasterListsBean.ShareListBean shareListBean = (MasterListsBean.ShareListBean) object;
//                                ToastUtils.show(getContext(),shareListBean.getTitle(),ToastUtils.LENGTH_LONG);
                                    Intent intent = new Intent(getActivity(), MasterDetailActivity.class);
                                    intent.putExtra("share_id", shareListBean.getShare_id());
                                    getContext().startActivity(intent);
                                }
                            });
                            rv_content.setAdapter(masterContentAdapter);
                            swipeLayout.setRefreshing(false);
                        }
                    }
                });
        addSubscription(subscribe);
    }

    /**
     * 达人标签筛选分享列表
     *
     * @param map 请求参数
     */
    public void masterListTag(Map<String, String> map) {
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .masterListTag(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<MasterListTagBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<MasterListTagBean>> listBaseModel) {

                    }
                });
        addSubscription(subscribe);
    }


}
