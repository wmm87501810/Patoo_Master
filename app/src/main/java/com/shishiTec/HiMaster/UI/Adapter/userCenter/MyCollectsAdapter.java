package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.CollectsListBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.CourseDetailActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;


public class MyCollectsAdapter extends RecyclerView.Adapter<MyCollectsAdapter.ItemViewHolder> {
    private Context context;
    private List<CollectsListBean> collectsListBeen;
    private BaseApplication baseApplication;

    public MyCollectsAdapter(Context context, List<CollectsListBean> collectsListBeen) {
        super();
        this.context = context;
        this.collectsListBeen = collectsListBeen;
        baseApplication = BaseApplication.getInstance();
    }

    @Override
    public int getItemCount() {
        return collectsListBeen == null ? 0 : collectsListBeen.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_mall_fragment_class_item, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder mViewHolder, final int arg1) {

        mViewHolder.mall_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("course_id", collectsListBeen.get(arg1).getCourse_id());
                intent.setClass(context, CourseDetailActivity.class);
                context.startActivity(intent);
            }
        });
        baseApplication.loadImageView(context, mViewHolder.iv_course_cover, collectsListBeen.get(arg1).getCover());
        mViewHolder.tv_course_name.setText(collectsListBeen.get(arg1).getTitle());
        mViewHolder.tv_course_price.setText(collectsListBeen.get(arg1).getPrice());
        mViewHolder.tv_store_name.setText(collectsListBeen.get(arg1).getStore());
        mViewHolder.tv_km.setText(collectsListBeen.get(arg1).getDistance());
        if (collectsListBeen.get(arg1).getTags().size() >= 4) {
            for (int i = 0; i < 5; i++) {
                switch (i) {
                    case 0:
                        mViewHolder.tag1.setText(collectsListBeen.get(arg1).getTags().get(0));
                        mViewHolder.tag1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mViewHolder.tag2.setText(collectsListBeen.get(arg1).getTags().get(1));
                        mViewHolder.tag2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        mViewHolder.tag3.setText(collectsListBeen.get(arg1).getTags().get(2));
                        mViewHolder.tag3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        mViewHolder.tag4.setText(collectsListBeen.get(arg1).getTags().get(3));
                        mViewHolder.tag4.setVisibility(View.VISIBLE);
                        break;
                }
            }
        } else if (collectsListBeen.get(arg1).getTags().size() > 0) {
            for (int i = 0; i < collectsListBeen.get(arg1).getTags().size(); i++) {
                switch (i) {
                    case 0:
                        mViewHolder.tag1.setText(collectsListBeen.get(arg1).getTags().get(0));
                        mViewHolder.tag1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mViewHolder.tag2.setText(collectsListBeen.get(arg1).getTags().get(1));
                        mViewHolder.tag2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        mViewHolder.tag3.setText(collectsListBeen.get(arg1).getTags().get(2));
                        mViewHolder.tag3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        mViewHolder.tag4.setText(collectsListBeen.get(arg1).getTags().get(3));
                        mViewHolder.tag4.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_course_cover;
        private TextView tv_course_name, tv_km, tv_course_price, tv_store_name, tag1, tag2, tag3, tag4;
        private AutoRelativeLayout mall_course;

        public ItemViewHolder(View view) {
            super(view);
            mall_course = (AutoRelativeLayout) view.findViewById(R.id.mall_course);
            iv_course_cover = (ImageView) view.findViewById(R.id.iv_course_cover);
            tv_course_name = (TextView) view.findViewById(R.id.tv_course_name);
            tv_course_price = (TextView) view.findViewById(R.id.tv_course_price);
            tv_store_name = (TextView) view.findViewById(R.id.tv_store_name);
            tv_km = (TextView) view.findViewById(R.id.tv_km);
            tag1 = (TextView) view.findViewById(R.id.tag1);
            tag2 = (TextView) view.findViewById(R.id.tag2);
            tag3 = (TextView) view.findViewById(R.id.tag3);
            tag4 = (TextView) view.findViewById(R.id.tag4);
        }
    }

    public void addMore(List<CollectsListBean> newDatas) {
        this.collectsListBeen = newDatas;
        notifyDataSetChanged();
    }
}
