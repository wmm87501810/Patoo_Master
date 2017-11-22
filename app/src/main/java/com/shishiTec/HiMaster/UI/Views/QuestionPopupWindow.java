package com.shishiTec.HiMaster.UI.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shishiTec.HiMaster.R;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by hyu_anzhuo on 2016/5/19.
 */
public class QuestionPopupWindow extends PopupWindow {
    private TextView cancel_question_tv;
    private AutoRelativeLayout ar_question_popup_phone, ar_question_popup_private_letter;
    private View mMenuView;

    public QuestionPopupWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.question_popup_window, null);
        cancel_question_tv = (TextView) mMenuView.findViewById(R.id.cancel_question_tv);
        ar_question_popup_phone = (AutoRelativeLayout) mMenuView.findViewById(R.id.ar_question_popup_phone);
        ar_question_popup_private_letter = (AutoRelativeLayout) mMenuView.findViewById(R.id.ar_question_popup_private_letter);
        ar_question_popup_phone.setOnClickListener(itemsOnClick);
        ar_question_popup_private_letter.setOnClickListener(itemsOnClick);
        cancel_question_tv.setOnClickListener(new View.OnClickListener() {

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

                int height = mMenuView.findViewById(R.id.question_layout).getBottom();
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
