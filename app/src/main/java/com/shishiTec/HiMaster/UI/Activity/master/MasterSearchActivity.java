package com.shishiTec.HiMaster.UI.Activity.master;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.fragment.master.SearchCourseFragment;
import com.shishiTec.HiMaster.UI.fragment.master.SearchMasterFragment;
import com.shishiTec.HiMaster.UI.fragment.master.SearchShareFragment;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.zhy.autolayout.AutoLinearLayout;

public class MasterSearchActivity extends BaseActivity implements View.OnClickListener,Animator.AnimatorListener{
    private ObjectAnimator oa;
    private int index = 0;

    private TextView tv_search_kecheng;   //课程
    private TextView tv_search_person;    //人
    private TextView tv_search_share;     //分享
    private TextView tv_cancel;           //取消
    private EditText et_search;           //搜索

    private View navigationLine;          //导航按钮下划线

    private LinearLayout ll_default_view;
    private LinearLayout ll_default_content;

    private SearchShareFragment searchShareFragment = new SearchShareFragment();
    private SearchCourseFragment searchCourseFragment = new SearchCourseFragment();
    private SearchMasterFragment searchMasterFragment = new SearchMasterFragment();

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_search_person = (TextView) findViewById(R.id.tv_search_person);
        tv_search_kecheng = (TextView) findViewById(R.id.tv_search_kecheng);
        tv_search_share = (TextView) findViewById(R.id.tv_search_share);
        ll_default_view = (LinearLayout) findViewById(R.id.ll_default_view);
        ll_default_content = (LinearLayout) findViewById(R.id.ll_default_content);
        navigationLine = findViewById(R.id.navigationLine);
        setCurrentFragment(R.id.tv_search_kecheng);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_master_search;
    }

    @Override
    protected void initListener() {
        et_search.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        ll_default_view.setOnClickListener(this);
        tv_search_share.setOnClickListener(this);
        tv_search_person.setOnClickListener(this);
        tv_search_kecheng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search_kecheng:
            case R.id.tv_search_person:
            case R.id.tv_search_share:
            startAnimator(v);
            setCurrentFragment(v.getId());
                break;
            case R.id.ll_default_view:
                ll_default_view.setVisibility(View.GONE);
                ll_default_content.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_cancel:
                this.finish();
                break;
            case R.id.et_search:
                et_search.addTextChangedListener(textWatcher);
                break;
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(ll_default_view.getVisibility()==View.VISIBLE){
                ll_default_view.setVisibility(View.GONE);
                ll_default_content.setVisibility(View.VISIBLE);
            }
            searchCourseFragment.notifyData(s.toString());
            searchMasterFragment.notifyData(s.toString());
            searchShareFragment.notifyData(s.toString());
        }
    };

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
                navigationLine.setLayoutParams(new AutoLinearLayout.LayoutParams(tv_search_kecheng.getWidth(),5));
                oa = ObjectAnimator.ofFloat(navigationLine, "translationX", startX,tv_search_kecheng.getX());
                index = 0;
            } else if(v.getId() == R.id.tv_search_person){
                if(index == 1) return;
                navigationLine.setLayoutParams(new AutoLinearLayout.LayoutParams(tv_search_person.getWidth(),5));
                oa = ObjectAnimator.ofFloat(navigationLine, "translationX", startX,tv_search_person.getX());
                index = 1;
            }else if(v.getId() == R.id.tv_search_share){
                if(index == 2) return;
                navigationLine.setLayoutParams(new AutoLinearLayout.LayoutParams(tv_search_share.getWidth(),5));
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
                tv_search_kecheng.setTextColor(getResources().getColor(R.color.black));
                tv_search_person.setTextColor(getResources().getColor(R.color.default_font_gray));
                tv_search_share.setTextColor(getResources().getColor(R.color.default_font_gray));
                break;
            case 1:
                tv_search_person.setTextColor(getResources().getColor(R.color.black));
                tv_search_kecheng.setTextColor(getResources().getColor(R.color.default_font_gray));
                tv_search_share.setTextColor(getResources().getColor(R.color.default_font_gray));
                break;
            case 2:
                tv_search_share.setTextColor(getResources().getColor(R.color.black));
                tv_search_kecheng.setTextColor(getResources().getColor(R.color.default_font_gray));
                tv_search_person.setTextColor(getResources().getColor(R.color.default_font_gray));
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
