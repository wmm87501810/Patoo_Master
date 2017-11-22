package com.shishiTec.HiMaster.UI.Activity.mall;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishiTec.HiMaster.Model.bean.ClassBean;
import com.shishiTec.HiMaster.Model.bean.InfoConfBean;
import com.shishiTec.HiMaster.Model.bean.SpecialBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.mall.MallClassAdapter;
import com.shishiTec.HiMaster.UI.Adapter.mall.MallSpecialAdapter;
import com.shishiTec.HiMaster.UI.Views.ScreenPicPopupWindow;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.base.BaseFragment;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hyu_anzhuo on 2016/5/27.
 */


public class MallItemFragment extends BaseFragment {
    public DeviceParams device;
    public BaseParams params;
    private RecyclerView buyOrderRecycler;
    private LinearLayoutManager layoutManager;
    private SuperSwipeRefreshLayout super_buy_order;
    private List<SpecialBean.SubjectListBean> subjectListBeen;
    private List<ClassBean.CourseListBean> classBeen;
    private MallSpecialAdapter mallSpecialAdapter;
    private MallClassAdapter mallClassAdapter;
    public String type;
    private AutoLinearLayout MD_al;
    protected MallHomeActivity activity;
    private String i;
    private ScreenPicPopupWindow menuWindow;//排序的POP
    private MallFragment mallFragment;
    private TextView textView;
    private int page = 1;
    private List<InfoConfBean.CityListBean> c;

    public MallItemFragment() {
        super();
    }

    public static MallItemFragment newInstance(String type, String i) {
        MallItemFragment mallFragment = new MallItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("i", i);
        mallFragment.setArguments(bundle);
        return mallFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_buy_order_content;
    }

    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
        Gson gson = new Gson();
        type = getArguments().getString("type");
        i = getArguments().getString("i");
        MD_al = (AutoLinearLayout) contentView.findViewById(R.id.MD_al);
        activity = (MallHomeActivity) getActivity();
        MD_al.setVisibility(View.GONE);
        subjectListBeen = new ArrayList<SpecialBean.SubjectListBean>();
        classBeen = new ArrayList<ClassBean.CourseListBean>();
        menuWindow = new ScreenPicPopupWindow(getActivity(), itemsOnClick, this);
        mallSpecialAdapter = new MallSpecialAdapter(getActivity(), subjectListBeen);
        mallClassAdapter = new MallClassAdapter(getActivity(), classBeen);
        super_buy_order = (SuperSwipeRefreshLayout) contentView.findViewById(R.id.super_buy_order);
        buyOrderRecycler = (RecyclerView) contentView.findViewById(R.id.buyOrderRecycler);
        layoutManager = new LinearLayoutManager(getActivity());
        buyOrderRecycler.setLayoutManager(layoutManager);
        c = gson.fromJson(SharedPreferencesUtil.getInstance().getOrderType(), new TypeToken<List<InfoConfBean.CityListBean>>() {
        }.getType());
        device = new DeviceParams();
        device.setCity_code(c.get(0).getCity_code());
        params = new BaseParams(device);
        params.setDevice(device);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        Map<String, String> map = new HashMap<>();
        map.put("category_id", type);
        map.put("page", "1");
        map.put("page_size", "10");
        map.put("order_type", "4");
        map.put("select_type", "5");
        if (type.equals("-1")) {
            special(map, true);
        } else {
            classItem(map, true);
        }
        initListener();
    }

    public void showPopWindow(MallFragment f) {
        this.mallFragment = f;
        if (null == menuWindow) {
            menuWindow = new ScreenPicPopupWindow(getActivity(), itemsOnClick, this);
        }
        menuWindow.showAsDropDown(getActivity().findViewById(R.id.tl_mall));
    }

    public void cityRefresh(MallFragment mallFragment, String city_code) {
        this.mallFragment = mallFragment;
        device = new DeviceParams();
        device.setCity_code(city_code);
        params = new BaseParams(device);
        params.setDevice(device);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        Map<String, String> map = new HashMap<>();
        map.put("category_id", type);
        map.put("page", "1");
        map.put("page_size", "10");
        map.put("order_type", "4");
        map.put("select_type", "5");
        if (type.equals("-1")) {
            special(map, true);
        } else {
            classItem(map, true);
        }
    }

    private void initListener() {

        super_buy_order.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                Map<String, String> map = new HashMap<>();
                map.put("category_id", type);
                map.put("page", "1");
                map.put("page_size", "10");
                map.put("order_type", "4");
                map.put("select_type", "5");
                if (type.equals("-1")) {
                    special(map, true);
                } else {
                    classItem(map, true);
                }
                super_buy_order.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        super_buy_order.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Map<String, String> map = new HashMap<>();
                map.put("category_id", type);
                map.put("page", ++page + "");
                map.put("page_size", "10");
                map.put("order_type", "4");
                map.put("select_type", "5");
                if (type.equals("-1")) {
                    special(map, false);
                } else {
                    classItem(map, false);
                }
                super_buy_order.setLoadMore(false);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
    }

    /**
     * 专题数据拉取
     *
     * @param map
     */
    private void special(Map<String, String> map, final boolean isRefresh) {
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .mallSpecial(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<SpecialBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<SpecialBean> mb) {
                        if (mb.getCode() == 200) {
                            if (isRefresh) {
                                subjectListBeen.clear();
                                subjectListBeen.addAll(mb.getData().getSubject_list());
                                buyOrderRecycler.setAdapter(mallSpecialAdapter);
                                mallSpecialAdapter.notifyDataSetChanged();
                            } else if (mb.getData().getCourse_list().size() > 0) {
                                subjectListBeen.addAll(mb.getData().getSubject_list());
                                mallSpecialAdapter.addMoreItem(subjectListBeen);
                            } else {
                                Toast.makeText(getActivity(), "已经没有最新数据了！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    /**
     * 类型数据拉取
     *
     * @param map
     */
    private void classItem(Map<String, String> map, final boolean isRefresh) {
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .mallClass(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<ClassBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<ClassBean> mb) {
                        if (mb.getCode() == 200) {
                            if (isRefresh) {
                                classBeen.clear();
                                classBeen.addAll(mb.getData().getCourse_list());
                                buyOrderRecycler.setAdapter(mallClassAdapter);
                                mallClassAdapter.notifyDataSetChanged();
                            } else if (mb.getData().getCourse_list().size() > 0) {
                                classBeen.addAll(mb.getData().getCourse_list());
                                mallClassAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), "已经没有最新数据了！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.al_popup_paixu: {
                    menuWindow.img_popup1.setVisibility(View.VISIBLE);
                    menuWindow.img_popup2.setVisibility(View.INVISIBLE);
                    menuWindow.recycler_popup_right.setVisibility(View.GONE);
                    menuWindow.recycler_popup_left.setVisibility(View.VISIBLE);
                    if (menuWindow.order_type == null) {
                        menuWindow.order_type = "";
                    }
                    if (menuWindow.select_type == null) {
                        menuWindow.select_type = "";
                    }
                    break;
                }
                case R.id.al_popup_paixu2: {
                    menuWindow.img_popup1.setVisibility(View.INVISIBLE);
                    menuWindow.img_popup2.setVisibility(View.VISIBLE);
                    menuWindow.recycler_popup_right.setVisibility(View.VISIBLE);
                    menuWindow.recycler_popup_left.setVisibility(View.GONE);
                    if (menuWindow.order_type == null) {
                        menuWindow.order_type = "";
                    }
                    if (menuWindow.select_type == null) {
                        menuWindow.select_type = "";
                    }
                    screenRefresh(menuWindow.order_type, menuWindow.select_type, type);
                    break;
                }
                default:
                    break;
            }
        }
    };

    public void screenRefresh(String order_type, String select_type, String category_id) {
        Map<String, String> map = new HashMap<>();
        map.put("category_id", type);
        map.put("page", "1");
        map.put("page_size", "10");
        map.put("order_type", order_type);
        map.put("select_type", select_type);
        if (type.equals("-1")) {
            special(map, true);
        } else {
            classItem(map, true);
        }
        if (mallFragment != null) {
            mallFragment.search();
        }
    }

}
