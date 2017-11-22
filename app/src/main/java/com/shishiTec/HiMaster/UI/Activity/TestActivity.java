package com.shishiTec.HiMaster.UI.Activity;

import android.net.Uri;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseActivity;

//import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class TestActivity extends BaseActivity {
    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    //    private JCVideoPlayerStandard jcVideoPlayerStandard;
    @Override
    protected void initViews() {
//        jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.test_video);
//        jcVideoPlayerStandard.setUp("http://kaifa.gomaster.cn/attms/uploadfile/2016/0504/20160504021157236.mov","钢铁侠怼死逗比");
//        jcVideoPlayerStandard.ivThumb.setImageURI(Uri.parse("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }
}
