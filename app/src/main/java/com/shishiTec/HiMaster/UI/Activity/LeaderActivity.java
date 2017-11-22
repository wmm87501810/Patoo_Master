package com.shishiTec.HiMaster.UI.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.Accouont.RegisterActivity;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.config.Config;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;



public class LeaderActivity extends BaseActivity {

    @Bind(R.id.leader_img)
    ViewPager mLeaderImg;
    @Bind(R.id.leader_circle)
    AutoLinearLayout mLeaderCircle;
    @Bind(R.id.leader_red)
    ImageView mLeaderRed;
    @Bind(R.id.leader_register)
    TextView leaderRegister;
    //引导页具体的图片
    private List<View> mViewList;
    private View mView;
    private int left;//左边间距
    private int mImgIndex = 0;
    private ValueAnimator animator;


    @Override
    public int getLayoutId() {
        return R.layout.activity_leader;
    }


    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        //全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        init();
        leaderRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeaderActivity.this,RegisterActivity.class));
                finish();
            }
    });
    }

    //初始化控件
    private void init() {

        initCircles();
        mLeaderImg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float leftMargin = left * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLeaderRed.getLayoutParams();
                params.leftMargin = Math.round(leftMargin);
                mLeaderRed.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                leaderRegister.setVisibility(View.INVISIBLE);
                switch (position) {
                    case 0:
                        startAnimation(0);
                        break;
                    case 1:
                        startAnimation(1);
                        break;
                    case 2:
                        startAnimation(2);
                        break;
                    case 3:
                        startAnimation(3);
                        leaderRegister.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mLeaderImg.setAdapter(new LeaderAdapter());
        startAnimation(0);
    }

    //初始化引导页指示器
    private void initCircles() {
        mViewList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mView = LayoutInflater.from(this).inflate(R.layout.item_leader_viewpager, null);
            mViewList.add(mView);
        }

        mLeaderRed.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                left = mLeaderCircle.getChildAt(1).getLeft() - mLeaderCircle.getChildAt(0).getLeft();
            }
        });

    }

    /**
     * 开始
     *
     * @param position
     */
    private void startAnimation(final int position) {
        final ImageView simple = (ImageView) mViewList.get(position).findViewById(R.id.leader_img);
        animator = ValueAnimator.ofFloat(0, 1.0f);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.setDuration(50);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                switch (position) {
                    case 0:
                        simple.setImageResource(Config.IMG_ONE[mImgIndex]);
                        if (mImgIndex == Config.IMG_ONE.length - 1) {
                            mImgIndex = 0;
                            animator.end();
                        }
                        break;
                    case 1:
                        simple.setImageResource(Config.IMG_TWO[mImgIndex]);
                        if (mImgIndex == Config.IMG_TWO.length - 1) {
                            mImgIndex = 0;
                            animator.end();
                        }
                        break;
                    case 2:
                        simple.setImageResource(Config.IMG_THREE[mImgIndex]);
                        if (mImgIndex == Config.IMG_THREE.length - 1) {
                            mImgIndex = 0;
                            animator.end();
                        }
                        break;
                    case 3:
                        simple.setImageResource(Config.IMG_FOUR[mImgIndex]);
                        if (mImgIndex == Config.IMG_FOUR.length - 1) {
                            mImgIndex = 0;
                            animator.end();
                        }
                        break;
                    default:
                        break;
                }
                mImgIndex++;
            }
        });
        animator.start();
    }


    //ViewPager适配器
    private class LeaderAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
