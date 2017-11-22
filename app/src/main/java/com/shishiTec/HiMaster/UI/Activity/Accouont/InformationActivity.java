package com.shishiTec.HiMaster.UI.Activity.Accouont;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;

public class InformationActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_cancel;
    private RelativeLayout rl_notification;
    private RelativeLayout rl_comment_reply;
    private RelativeLayout rl_private_leeter;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);

        rl_notification = (RelativeLayout) findViewById(R.id.rl_notification);
        rl_comment_reply = (RelativeLayout) findViewById(R.id.rl_comment_reply);
        rl_private_leeter = (RelativeLayout) findViewById(R.id.rl_private_leeter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected void initListener() {
        iv_cancel.setOnClickListener(this);
        rl_notification.setOnClickListener(this);
        rl_comment_reply.setOnClickListener(this);
        rl_private_leeter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            //返回
            case R.id.iv_cancel:
                this.finish();
                break;
            //系统通知
            case R.id.rl_notification:
                intent = new Intent(InformationActivity.this,NotificationActivity.class);
                startActivity(intent);
                break;
            //评论回复
            case R.id.rl_comment_reply:
                intent = new Intent(InformationActivity.this,CommentReplayActivity.class);
                startActivity(intent);
                break;
            //私信
            case R.id.rl_private_leeter:
                intent = new Intent(InformationActivity.this,PrivateLetterListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
