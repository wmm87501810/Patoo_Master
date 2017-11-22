package com.shishiTec.HiMaster.UI.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishiTec.HiMaster.Model.bean.InfoConfBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.MallFragment;
import com.shishiTec.HiMaster.UI.Activity.mall.MallHomeActivity;
import com.shishiTec.HiMaster.UI.Adapter.mall.MallCityAdapter;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;

import java.util.List;

public class CityPopupWindow extends PopupWindow {
    private TextView popupWindow_city, popupWindow_city1, popupWindow_city2;
    private RecyclerView city_layout;
    private View mMenuView;
    private List<InfoConfBean.CityListBean> fromJson;
    private Gson gson;
    private MallFragment mallFragment;
    private MallHomeActivity activity;
    public CityPopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        activity = (MallHomeActivity) context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.city_popup_window, null);
        city_layout = (RecyclerView) mMenuView.findViewById(R.id.city_layout);
        gson = new Gson();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        city_layout.setLayoutManager(linearLayoutManager);
        fromJson = gson.fromJson(SharedPreferencesUtil.getInstance().getCityList(), new TypeToken<List<InfoConfBean.CityListBean>>() {
        }.getType());
        MallCityAdapter adapter = new MallCityAdapter(context, fromJson);
        city_layout.setAdapter(adapter);
        adapter.setOnItemClickListener(new MallCityAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {
                activity.cityRefresh(fromJson.get(position).getCity_code(),fromJson.get(position).getCity_name());
                dismiss();
            }
        });
        //设置按钮监听
//        popupWindow_city.setOnClickListener(itemsOnClick);
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

                int height = mMenuView.findViewById(R.id.city_layout).getBottom();
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
}
