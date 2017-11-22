package com.shishiTec.HiMaster.UI.Activity.master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.master.AddTagAdapter;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddTagActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_cancel;
    private EditText et_add_tag;
    private TextView tv_add_tag;
    private RecyclerView rl_tag;
    private AddTagAdapter addTagAdapter;
    private AutoLinearLayout al_tag_type;
    private long tag1 = 0;
    private long tag2 = 0;
    private Gson gson = new Gson();

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        rl_tag = (RecyclerView) findViewById(R.id.rl_tag);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_add_tag = (TextView)findViewById(R.id.tv_add_tag);
        et_add_tag = (EditText) findViewById(R.id.et_add_tag);
        al_tag_type = (AutoLinearLayout)findViewById(R.id.al_tag_type);

        //设置RecyclerView管理器
        LinearLayoutManager tag_manager = new LinearLayoutManager(this);
        tag_manager.setOrientation(LinearLayoutManager.VERTICAL);
        rl_tag.setLayoutManager(tag_manager);
        rl_tag.setItemAnimator(new DefaultItemAnimator());

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(tag1!=0 && count!=0){
                    tag2 = System.currentTimeMillis()-tag1;
                    tag1 = 0;
                    //时间大于一秒进行类别检索
                    if(tag2>1000)tagListByKeywords(et_add_tag.getText().toString());
                }
                tag1 = System.currentTimeMillis();
                al_tag_type.setVisibility(View.VISIBLE);
                tv_add_tag.setText(et_add_tag.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        et_add_tag.addTextChangedListener(watcher);

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_tag;
    }

    @Override
    protected void initListener() {
        tv_cancel.setOnClickListener(this);
        al_tag_type.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                AddTagActivity.this.finish();
                break;
            case R.id.al_tag_type:
                Intent intent = new Intent();
                intent.putExtra("tagName",tv_add_tag.getText().toString().trim());
                setResult(111,intent);
                this.finish();
                break;
        }
    }

    public void tagListByKeywords(String str){
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("keywords",str);
        map.put("page_size","10");
        params.setData(map);
        Subscription subscription = new RetrofitManager()
                .createService()
                .tgaList(params)
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
                    public void onNext(BaseModel baseModel) {
                        List<String> list = gson.fromJson(baseModel.getData().toString(), ArrayList.class);
                        if(addTagAdapter == null){
                            addTagAdapter = new AddTagAdapter(AddTagActivity.this,list);
                            rl_tag.setAdapter(addTagAdapter);
                            addTagAdapter.setOnItemClickListener(new AddTagAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position, Object object) {
                                    List<String> list = (List<String>)object;
                                    Intent intent = new Intent();
                                    intent.putExtra("tagName",list.get(position).trim());
                                    setResult(111,intent);
                                    AddTagActivity.this.finish();
                                }
                            });
                        }
                        addTagAdapter.onChangeList(list);
                        addTagAdapter.notifyDataSetChanged();

                    }
                });
        addSubscription(subscription);
    }
}
