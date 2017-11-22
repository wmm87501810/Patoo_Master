package com.shishiTec.HiMaster.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import java.util.Timer;
import java.util.TimerTask;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Pursue on 16/1/18.
 */
public class BaseFragmentActivity extends FragmentActivity{
    private CompositeSubscription mCompositeSubscription;
    public BaseApplication baseApplication = BaseApplication.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 按返回键两次退出程序
     *
     */
    private boolean canExit = true;
    public void exit() {
        if (canExit == true) {
            String str = "再按一次退出程序";
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT)
                    .show();
            canExit = false;
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    canExit = true; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            canExit = true;
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription!=null){
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
