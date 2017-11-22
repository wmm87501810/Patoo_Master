package com.shishiTec.HiMaster.UI.Activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.shishiTec.HiMaster.Model.bean.ClassBean;
import com.shishiTec.HiMaster.Model.bean.MallCategoryBean;
import com.shishiTec.HiMaster.Model.bean.SearchKeyWordsBean;
import com.shishiTec.HiMaster.Model.bean.SpecialBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.master.MasterSearchActivity;
import com.shishiTec.HiMaster.UI.Adapter.mall.MallClassAdapter;
import com.shishiTec.HiMaster.UI.Adapter.mall.MallPopupClassAdapter;
import com.shishiTec.HiMaster.UI.Adapter.mall.MallSpecialAdapter;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.BuyOrderTabFragment;
import com.shishiTec.HiMaster.UI.Views.ScreenPicPopupWindow;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MallFragment extends MallBaseFragment implements AnimatorListener {
    private List<MallCategoryBean.CategoryListBean> ms;
    private ArrayList<String> idStrings = new ArrayList<>();
    private ArrayList<String> nameStrings = new ArrayList<>();
    private List<MallCategoryBean.BannerListBean> lv;
    private List<SpecialBean.SubjectListBean> subjectListBeen;
    private List<SearchKeyWordsBean> searchKeyWordsBeen;
    private List<ClassBean.CourseListBean> classBeen;
    private View mLayout;
    private View mSearchlayout;
    private MallHomeActivity.MyOnTouchListener myOnTouchListener;
    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;
    private float lastX = 0;
    private float lastY = 0;
    private String tabNum = "-1";
    private int lastVisibleItem, firstVisibleItem;
    private int page = 1;
    private MallSpecialAdapter mallSpecialAdapter;
    private MallClassAdapter mallClassAdapter;
    private MallPopupClassAdapter mallPopupClassAdapter;
    private ScreenPicPopupWindow menuWindow;//排序的POP
    private String typeTrue = "0";
    private String typeFalse = "1";
    private int position;
    private int fragmentPosition = 0;
    BuyOrderTabFragment buyOrderTabFragment;
    private AutoRelativeLayout search_ar;

    public MallFragment() {
        super();
    }

    public static MallFragment newInstance(String m, String l) {
        MallFragment mallFragment = new MallFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ms", m);
        bundle.putString("lv", l);
        mallFragment.setArguments(bundle);
        return mallFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void cityRefresh(String city_code) {
        MallItemFragment mallItemFragment = (MallItemFragment) this.getChildFragmentManager().getFragments().get(fragmentPosition);
        mallItemFragment.cityRefresh(this, city_code);
    }

    public void showPopWindow() {
        MallItemFragment mallItemFragment = (MallItemFragment) this.getChildFragmentManager().getFragments().get(fragmentPosition);
        mallItemFragment.showPopWindow(this);
    }

    public void search() {
        String test = typeTrue + nameStrings.get(fragmentPosition).substring(1);
        nameStrings.set(fragmentPosition, test);
        activity.screen.setImageResource(R.mipmap.enterprise);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Gson gson = new Gson();
        activity.search(false);
        mLayout = getActivity().findViewById(R.id.layout);
        search_ar = (AutoRelativeLayout) getActivity().findViewById(R.id.search_ar);
        mSearchlayout = getActivity().findViewById(R.id.search_layout);
        ms = gson.fromJson(getArguments().getString("ms"), new TypeToken<List<MallCategoryBean.CategoryListBean>>() {
        }.getType());
        lv = gson.fromJson(getArguments().getString("lv"), new TypeToken<List<MallCategoryBean.BannerListBean>>() {
        }.getType());
        if (ms != null && ms.size() > 0) {
            for (int i = 0; i < ms.size(); i++) {
                list.add(MallItemFragment.newInstance(ms.get(i).getId(), i + ""));
                idStrings.add(ms.get(i).getName());
                nameStrings.add(typeFalse + ms.get(i).getId());
            }
            buyOrderTabFragment = new BuyOrderTabFragment(getChildFragmentManager(), list, idStrings);
            mListView.setAdapter(buyOrderTabFragment);
            mListView.setOffscreenPageLimit(2);
            tl_mall.setViewPager(mListView);
        }
        myOnTouchListener = new MallHomeActivity.MyOnTouchListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return dispathTouchEvent(ev);
            }
        };
        ((MallHomeActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);
        tablayout();
        initListener();
        search_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MasterSearchActivity.class));
            }
        });
    }


    /**
     * 监听事件
     */
    private void initListener() {
        myOnTouchListener = new MallHomeActivity.MyOnTouchListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return dispathTouchEvent(ev);
            }
        };
    }

    private void tablayout() {
        tl_mall.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(final int position) {
                fragmentPosition = position;
                mListView.setCurrentItem(position);
                String a = nameStrings.get(position).substring(0, 1);
                if (a.equals(typeTrue)) {
                    activity.screen.setImageResource(R.mipmap.enterprise);
                } else {
                    activity.screen.setImageResource(R.mipmap.gift);
                }
                if (ms.get(position).getId().equals("-1")) {
                    activity.search(false);
                } else {
                    activity.search(true);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mListView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                String a = nameStrings.get(position).substring(0, 1);
                if (a.equals(typeTrue)) {
                    activity.screen.setImageResource(R.mipmap.enterprise);
                } else {
                    activity.screen.setImageResource(R.mipmap.gift);
                }
                tl_mall.setCurrentTab(position);
                fragmentPosition = position;
//                Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
                if (ms.get(position).getId().equals("-1")) {
                    activity.search(false);
                } else {
                    activity.search(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mListView.setCurrentItem(0);
    }

    private boolean isDown = false;
    private boolean isUp = false;

    private boolean dispathTouchEvent(MotionEvent event) {
        if (mIsAnim) {
            return false;
        }
        final int action = event.getAction();

        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
                isUp = dX < 8 && dY > 8 && !mIsTitleHide && !down;
                isDown = dX < 8 && dY > 8 && mIsTitleHide && down;
                if (isUp) {
                    View view = this.mLayout;
                    float[] f = new float[2];
                    f[0] = 0.0F;
                    f[1] = -mSearchlayout.getHeight();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.setDuration(200);
                    animator1.start();
                    animator1.addListener(this);
                    setMarginTop(mLayout.getHeight() - mSearchlayout.getHeight());
                    activity.control(false);
                } else if (isDown) {
                    View view = this.mLayout;
                    float[] f = new float[2];
                    f[0] = -mSearchlayout.getHeight();
                    f[1] = 0F;
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setDuration(200);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.start();
                    animator1.addListener(this);
                    activity.control(true);
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                break;
            default:
                return false;
        }
        return false;

    }

    @Override
    public void onAnimationCancel(Animator arg0) {

    }


    @Override
    public void onAnimationEnd(Animator arg0) {
        if (isDown) {
            setMarginTop(mLayout.getHeight());
        }
        mIsAnim = false;
    }


    @Override
    public void onAnimationRepeat(Animator arg0) {

    }


    @Override
    public void onAnimationStart(Animator arg0) {

    }

    /**
     * 轮播适配器
     */
//    private class LooperViewPagerAdapter extends PagerAdapter {
//
//        private final List<MallCategoryBean.BannerListBean> mList;
//        private final Context mContext;
//
//        public LooperViewPagerAdapter(Context context, List<MallCategoryBean.BannerListBean> list) {
//            this.mList = list;
//            this.mContext = context;
//        }
//
//        @Override
//        public int getCount() {
//            return mList == null ? 0 : mList.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            ImageView iv = new ImageView(mContext);
//            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            iv.setLayoutParams(lp);
//            BaseApplication.getInstance().loadImageALLView(mContext, iv, mList.get(position).getPic_url());
//            container.addView(iv);
//            return iv;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            if (container != null && object != null)
//                container.removeView((View) object);
//        }
//    }
}
