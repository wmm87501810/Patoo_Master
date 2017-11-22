package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shishiTec.HiMaster.Model.bean.ClassBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.CourseDetailActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;


public class MallClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ClassBean.CourseListBean> classBeen;
    private BaseApplication baseApplication;

    public MallClassAdapter(Context context, List<ClassBean.CourseListBean> classBeen) {
        super();
        this.context = context;
        this.classBeen = classBeen;
        baseApplication = BaseApplication.getInstance();
    }

    @Override
    public int getItemCount() {
        return classBeen == null ? 0 : classBeen.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        //进行判断显示类型，来创建返回不同的View
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_mall_fragment_class_item, null);
        MViewHolder holder = new MViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mViewHolder,  int arg1) {
        if (mViewHolder instanceof MViewHolder) {
            ((MViewHolder) mViewHolder).mall_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClassBean.CourseListBean bean = (ClassBean.CourseListBean)view.getTag();
                    Intent intent = new Intent();
                    intent.putExtra("course_id", bean.getCourse_id());
                    intent.setClass(context, CourseDetailActivity.class);
                    context.startActivity(intent);
                }
            });
            ClassBean.CourseListBean bean = classBeen.get(arg1);
            ((MViewHolder) mViewHolder).mall_course.setTag(bean);
            baseApplication.loadImageView(context, ((MViewHolder) mViewHolder).iv_course_cover, bean.getCover());
            ((MViewHolder) mViewHolder).tv_course_name.setText(bean.getTitle());
            ((MViewHolder) mViewHolder).tv_course_price.setText(bean.getPrice());
            ((MViewHolder) mViewHolder).tv_store_name.setText(bean.getStore());
            ((MViewHolder) mViewHolder).tv_km.setText(bean.getDistance());
            if (bean.getTags().size() >= 4) {
                for (int i = 0; i < 5; i++) {
                    switch (i) {
                        case 0:
                            ((MViewHolder) mViewHolder).tag1.setText(bean.getTags().get(0));
                            ((MViewHolder) mViewHolder).tag1.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            ((MViewHolder) mViewHolder).tag2.setText(bean.getTags().get(1));
                            ((MViewHolder) mViewHolder).tag2.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            ((MViewHolder) mViewHolder).tag3.setText(bean.getTags().get(2));
                            ((MViewHolder) mViewHolder).tag3.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            ((MViewHolder) mViewHolder).tag4.setText(bean.getTags().get(3));
                            ((MViewHolder) mViewHolder).tag4.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            } else if (bean.getTags().size() > 0) {
                for (int i = 0; i < bean.getTags().size(); i++) {
                    switch (i) {
                        case 0:
                            ((MViewHolder) mViewHolder).tag1.setText(bean.getTags().get(0));
                            ((MViewHolder) mViewHolder).tag1.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            ((MViewHolder) mViewHolder).tag2.setText(bean.getTags().get(1));
                            ((MViewHolder) mViewHolder).tag2.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            ((MViewHolder) mViewHolder).tag3.setText(bean.getTags().get(2));
                            ((MViewHolder) mViewHolder).tag3.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            ((MViewHolder) mViewHolder).tag4.setText(bean.getTags().get(3));
                            ((MViewHolder) mViewHolder).tag4.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }

        }
    }


    public static class MViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_course_cover;
        private TextView tv_course_name, tv_km, tv_course_price, tv_store_name, tag1, tag2, tag3, tag4;
        private AutoRelativeLayout mall_course;

        public MViewHolder(View view) {
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


    //添加数据
    public void addMoreItem(List<ClassBean.CourseListBean> newDatas) {
        classBeen = newDatas;
        notifyDataSetChanged();
    }
}
