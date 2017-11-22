package com.shishiTec.HiMaster.UI.Views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishiTec.HiMaster.Model.bean.InfoConfBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.MallItemFragment;
import com.shishiTec.HiMaster.UI.Adapter.mall.ScreenPopupAdapter;
import com.shishiTec.HiMaster.UI.Adapter.mall.ScreenPopupRightAdapter;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

public class ScreenPicPopupWindow extends PopupWindow {
    private AutoRelativeLayout al_popup_paixu, al_popup_paixu2;
    public ImageView img_popup1, img_popup2;
    public RecyclerView recycler_popup_left, recycler_popup_right;
    private View mMenuView;
    private LinearLayoutManager linearLayoutManager_left, linearLayoutManager_right;
    public DeviceParams device;
    public BaseParams params;
    private Context activity;
    private List<InfoConfBean.OrderTypeBean> o;
    private List<InfoConfBean.SelectTypeBean> s;
    private Gson gson;
    private TextView textView1, textView2;
    public String order_type = "", select_type = "";
    private MallItemFragment mallItemFragment;

    public ScreenPicPopupWindow(Context context, OnClickListener itemsOnClick, MallItemFragment mallItemFragment) {
        super(context);
        this.mallItemFragment = mallItemFragment;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.screen_popupwindow, null);
        activity = context;
        initDeviceData();
        gson = new Gson();
        linearLayoutManager_left = new LinearLayoutManager(context);
        linearLayoutManager_right = new LinearLayoutManager(context);
        al_popup_paixu = (AutoRelativeLayout) mMenuView.findViewById(R.id.al_popup_paixu);
        al_popup_paixu2 = (AutoRelativeLayout) mMenuView.findViewById(R.id.al_popup_paixu2);
        recycler_popup_left = (RecyclerView) mMenuView.findViewById(R.id.recycler_popup_left);
        recycler_popup_right = (RecyclerView) mMenuView.findViewById(R.id.recycler_popup_right);
        img_popup1 = (ImageView) mMenuView.findViewById(R.id.img_popup1);
        img_popup2 = (ImageView) mMenuView.findViewById(R.id.img_popup2);
        recycler_popup_left.setLayoutManager(linearLayoutManager_left);
        recycler_popup_left.setHasFixedSize(true);
        recycler_popup_right.setLayoutManager(linearLayoutManager_right);
        recycler_popup_right.setHasFixedSize(true);
        getData();
        //设置按钮监听
        al_popup_paixu.setOnClickListener(itemsOnClick);
        al_popup_paixu2.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.CityTop);
        //实例化一个ColorDrawable颜色为透明
        ColorDrawable dw = new ColorDrawable(0xe0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    private void getData() {
        o = gson.fromJson(SharedPreferencesUtil.getInstance().getOrderType(), new TypeToken<List<InfoConfBean.OrderTypeBean>>() {
        }.getType());
        s = gson.fromJson(SharedPreferencesUtil.getInstance().getSelectType(), new TypeToken<List<InfoConfBean.SelectTypeBean>>() {
        }.getType());
        ScreenPopupAdapter adapter_left = new ScreenPopupAdapter(activity, o);
        adapter_left.setOnItemClickListener(new ScreenPopupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView view) {
                if (textView1 != null) {
                    textView1.setTextColor(Color.BLACK);
                    view.setTextColor(Color.parseColor("#4ABAEE"));
                } else {
                    view.setTextColor(Color.parseColor("#4ABAEE"));
                }
                textView1 = view;
                order_type = o.get(position).getItem_id();
                mallItemFragment.screenRefresh(order_type, select_type, mallItemFragment.type);
                dismiss();
            }
        });
        recycler_popup_left.setAdapter(adapter_left);
        ScreenPopupRightAdapter adapter_right = new ScreenPopupRightAdapter(activity, s);
        adapter_right.setOnItemClickListener(new ScreenPopupRightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView view) {
                if (textView2 != null) {
                    textView2.setTextColor(Color.BLACK);
                    view.setTextColor(Color.parseColor("#4ABAEE"));
                } else {
                    view.setTextColor(Color.parseColor("#4ABAEE"));
                }
                textView2 = view;
                select_type = s.get(position).getItem_id();
                mallItemFragment.screenRefresh(order_type, select_type, mallItemFragment.type);
                dismiss();
            }
        });
        recycler_popup_right.setAdapter(adapter_right);
        recycler_popup_left.setVisibility(View.VISIBLE);
        recycler_popup_right.setVisibility(View.GONE);

    }

    //初始化设备信息
    private void initDeviceData() {
        device = new DeviceParams();
        params = new BaseParams(device);
        params.setDevice(device);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
    }
//
//    public RecyclerView test() {
//        return recyle_popup;
//    }
}
