package com.shishiTec.HiMaster.UI.Views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.ShareDetailBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

/**
 * Created by hyu_anzhuo on 2016/4/28.
 */
public class fullScreenDialog extends Dialog {
    private ViewPager myDialogViewpager;
    private Activity context;
    private List<ShareDetailBean.ShareBean.DetailBean> mDialogList;
    private PagerAdapter adapter;
    public fullScreenDialog(Activity context,int themeResId, List<ShareDetailBean.ShareBean.DetailBean> mDialogList) {
        super(context, themeResId);
        this.context = context;
        this.mDialogList = mDialogList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);
        myDialogViewpager = (ViewPager) findViewById(R.id.myDialogViewpager);
        adapter = new pagerAdapter(context,mDialogList);
        myDialogViewpager.setAdapter(adapter);
    }

    public class pagerAdapter extends PagerAdapter{
        private  List<ShareDetailBean.ShareBean.DetailBean> mList;
        private  Context mContext;

        public pagerAdapter(Context context, List<ShareDetailBean.ShareBean.DetailBean> list) {
            this.mList=list;
            this.mContext=context;
        }

        @Override
        public int getCount() {
            return mList==null?0:mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            FrameLayout frameLayout = new FrameLayout(mContext);
            FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            frameLayout.setLayoutParams(fp);
            ImageView iv=new ImageView(mContext);
            TextView tv = new TextView(mContext);
            tv.setTextColor(mContext.getResources().getColor(R.color.white));
            FrameLayout.LayoutParams tp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tp.gravity = Gravity.BOTTOM;
            tv.setPadding(mContext.getResources().getDimensionPixelSize(R.dimen.dialogPadding),0,mContext.getResources().getDimensionPixelSize(R.dimen.dialogPadding),mContext.getResources().getDimensionPixelSize(R.dimen.dialogPaddingBottom));
            tv.setTextSize(mContext.getResources().getDimensionPixelSize(R.dimen.dialogTextSize));
            tv.setLayoutParams(tp);
//            iv.setScaleType(ImageView.ScaleType.CENTER);
            FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);
            BaseApplication.getInstance().loadImageALLView(mContext,iv,mList.get(position).getUrl());
            tv.setText(mList.get(position).getIntro());
            frameLayout.addView(iv);
            frameLayout.addView(tv);
            container.addView(frameLayout);
            return frameLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if(container!=null&&object!=null)
                container.removeView((View) object);
        }
    }


}
