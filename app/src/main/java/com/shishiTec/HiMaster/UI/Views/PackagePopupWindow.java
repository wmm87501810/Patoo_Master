package com.shishiTec.HiMaster.UI.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shishiTec.HiMaster.R;

/**
 * Created by hyu_anzhuo on 2016/5/19.
 */
public class PackagePopupWindow extends PopupWindow {

    private TextView cancel_address,choose_tv;
    public RecyclerView choose_other_address_recycler;
    private View mMenuView;
    private LinearLayoutManager linearLayoutManager;

    public PackagePopupWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.other_address_popup_window, null);
        linearLayoutManager = new LinearLayoutManager(context);
        cancel_address = (TextView) mMenuView.findViewById(R.id.cancel_address);
        choose_tv = (TextView) mMenuView.findViewById(R.id.choose_tv);
        choose_tv.setText("选择课程");
        choose_other_address_recycler = (RecyclerView) mMenuView.findViewById(R.id.choose_other_address_recycler);
        choose_other_address_recycler.setLayoutManager(linearLayoutManager);
        choose_other_address_recycler.setHasFixedSize(true);
        cancel_address.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为透明
        ColorDrawable dw = new ColorDrawable(0xe0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.address_layout).getBottom();
                int y=(int) event.getY();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y>height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}
