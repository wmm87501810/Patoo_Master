package com.shishiTec.HiMaster.UI.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Pursue on 16/1/14.
 */
public class SixteenToTenImageView extends ImageView{

    public SixteenToTenImageView(Context context) {
        super(context);
    }

    public SixteenToTenImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SixteenToTenImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int)(width*10/16);//锁定宽高比例
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
