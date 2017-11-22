package com.shishiTec.HiMaster.UI.Views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by Administrator on 2016/3/22.
 */
public class LooperViewPager extends ViewPager {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private int mInterval = 5000;
    private int mCurrent = -1;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCurrent < getAdapter().getCount()) {
                mCurrent++;
            } else {
                mCurrent = 0;
            }
            setCurrentItem(mCurrent);
            mHandler.postDelayed(this, mInterval);
        }
    };

    public LooperViewPager(Context context) {
        super(context);
    }

    public LooperViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void start() {
        if (getAdapter() == null) {
            return;
        }
        mHandler.postDelayed(mRunnable, mInterval);
    }

    public void stop() {
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                stop();
                return true;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                start();
                break;
        }
        return super.onTouchEvent(ev);
    }
}
