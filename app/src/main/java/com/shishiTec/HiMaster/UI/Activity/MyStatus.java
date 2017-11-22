package com.shishiTec.HiMaster.UI.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Views.ArcProgressbar;
import com.shishiTec.HiMaster.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 83802 on 2017/8/21.
 */

public class MyStatus extends BaseActivity {
    @Bind(R.id.arc_progress)
    ArcProgressbar arcProgress;
    private int mProgress = 1;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        setGrade(90);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mystatus;
    }

    private void setGrade(final int g) {
        mProgress = 1;
        arcProgress.reset();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (0 < (mProgress / 3.6) && (mProgress / 3.6) <= 59) {
                    arcProgress.setmArcColor(Color.parseColor("#ff0000"));
                   // tv_grade.setTextColor(Color.parseColor("#ff0000"));
                } else if (60 < (mProgress / 3.6) && (mProgress / 3.6) <= 79) {
                    arcProgress.setmArcColor(Color.parseColor("#f39700"));
                   // tv_grade.setTextColor(Color.parseColor("#f39700"));
                } else if (80 < (mProgress / 3.6) && (mProgress / 3.6) <= 100) {
                    arcProgress.setmArcColor(Color.parseColor("#42ae7c"));
                   // tv_grade.setTextColor(Color.parseColor("#42ae7c"));
                }
                if (msg.what == 0x1223) {
                    arcProgress.setProgress(mProgress * (1));
                   // tv_grade.setText("" + (int) (mProgress / 3.6));
                } else if (msg.what == 0x1224) {
                   // tv_grade.setText("" + g);
                }
            }
        };
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                if (mProgress < (int) (((float) 360 / 100) * g)) {
                    msg.what = 0x1223;
                    mProgress++;
                } else {
                    msg.what = 0x1224;
                    this.cancel();
                }
                handler.sendMessage(msg);
            }
        }, 0, 5);
    }
}
