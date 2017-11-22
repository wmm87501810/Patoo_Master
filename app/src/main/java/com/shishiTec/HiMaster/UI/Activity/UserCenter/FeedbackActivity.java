package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedbackActivity extends BaseActivity {
    private TextView right_title;
    private EditText add_content;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        right_title = (TextView) findViewById(R.id.right_title);
        add_content = (EditText) findViewById(R.id.add_content);
        right_title.setText("保存");
        final Map<String,String> map = new HashMap<>();
        right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("content",add_content.getText().toString());
                params.setData(map);
                Subscription subscribe = RetrofitManager.getmInstance()
                        .createService()
                        .addSuggest(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<BaseModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(BaseModel md) {
                                if(md.getCode()==200){
                                    ToastUtils.show(FeedbackActivity.this,md.getMsg(), ToastUtils.LENGTH_LONG);
                                    finish();
                                }
                            }
                        });
                addSubscription(subscribe);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }
}
