package com.shishiTec.HiMaster.UI.Activity.master;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.fragment.master.SearchCourseFragment;
import com.shishiTec.HiMaster.UI.fragment.master.SearchMasterFragment;
import com.shishiTec.HiMaster.UI.fragment.master.SearchShareFragment;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;

public class SearchActivity extends BaseActivity implements View.OnClickListener,Animator.AnimatorListener{
    private ObjectAnimator oa;
    private int index = 0;
    
    private TextView tv_search_kecheng;   //课程
    private TextView tv_search_person;    //人
    private TextView tv_search_share;     //分享
    private EditText et_add_tag;          //搜索
    private View navigationLine;          //导航按钮下划线

    private SearchShareFragment searchShareFragment;   
    private SearchCourseFragment searchCourseFragment;
    private SearchMasterFragment searchMasterFragment;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        et_add_tag = (EditText) findViewById(R.id.et_add_tag);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ToastUtils.show(SearchActivity.this,s.toString(),ToastUtils.LENGTH_LONG);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ToastUtils.show(SearchActivity.this,s.toString(),ToastUtils.LENGTH_LONG);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        et_add_tag.addTextChangedListener(textWatcher);
        tv_search_kecheng = (TextView) findViewById(R.id.tv_search_kecheng);
        tv_search_person = (TextView) findViewById(R.id.tv_search_kecheng);
        tv_search_share = (TextView) findViewById(R.id.tv_search_share);
        navigationLine = findViewById(R.id.navigationLine);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initListener() {
        tv_search_share.setOnClickListener(this);
        tv_search_person.setOnClickListener(this);
        tv_search_kecheng.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        startAnimator(v);
        setCurrentFragment(v.getId());
    }



    /**
     * 设置当前Fragment
     * @param id
     */
    private void setCurrentFragment(int id){
        FragmentTransaction trasection = getSupportFragmentManager().beginTransaction();
        switch (id){
            case R.id.tv_search_kecheng:
                //切换到课程
                if(searchCourseFragment == null){
                    searchCourseFragment = new SearchCourseFragment();
                }
                trasection.replace(R.id.my_message_framelayout,searchCourseFragment);
                break;
            case R.id.tv_search_person:
                //切换到达人
                if( searchMasterFragment == null){
                    searchMasterFragment = new SearchMasterFragment();
                }
                trasection.replace(R.id.my_message_framelayout,searchMasterFragment);
                break;
            case R.id.tv_search_share:
                //切换到我的通知
                if(searchShareFragment == null){
                    searchShareFragment = new SearchShareFragment();
                }
                trasection.replace(R.id.my_message_framelayout, searchShareFragment);
                break;
        }
        trasection.commitAllowingStateLoss();
    }

    /**
     * 导航按钮切换效果动画
     */
    private void startAnimator(View v){
        float startX = 0;
        if(index == 0){
            startX = tv_search_kecheng.getX();
        }else if(index == 1){
            startX = tv_search_person.getX();
        }else if(index == 2){
            startX = tv_search_share.getX();
        }
        if (oa == null || !oa.isRunning()) {
            if (v.getId() == R.id.tv_search_kecheng) {
                if(index == 0) return;
                oa = ObjectAnimator.ofFloat(navigationLine, "translationX", startX,tv_search_kecheng.getX());
                index = 0;
            } else if(v.getId() == R.id.tv_search_person){
                if(index == 1) return;
                oa = ObjectAnimator.ofFloat(navigationLine, "translationX", startX,tv_search_person.getX());
                index = 1;
            }else if(v.getId() == R.id.tv_search_share){
                if(index == 2) return;
                oa = ObjectAnimator.ofFloat(navigationLine, "translationX", startX,tv_search_share.getX());
                index = 2;
            }
            oa.addListener(this);
            oa.setDuration(500);
            oa.start();
        }
    }

    /**
     * ViewPager页面选择
     */
    private void selectedPage(){
        switch (index){
            case 0:
                tv_search_kecheng.setTextColor(getResources().getColor(R.color.trader_up_color));
                tv_search_person.setTextColor(getResources().getColor(R.color.black_ligh));
                tv_search_share.setTextColor(getResources().getColor(R.color.black_ligh));
                break;
            case 1:
                tv_search_person.setTextColor(getResources().getColor(R.color.trader_up_color));
                tv_search_kecheng.setTextColor(getResources().getColor(R.color.black_ligh));
                tv_search_share.setTextColor(getResources().getColor(R.color.black_ligh));
                break;
            case 2:
                tv_search_share.setTextColor(getResources().getColor(R.color.trader_up_color));
                tv_search_kecheng.setTextColor(getResources().getColor(R.color.black_ligh));
                tv_search_person.setTextColor(getResources().getColor(R.color.black_ligh));
                break;
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        selectedPage();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
