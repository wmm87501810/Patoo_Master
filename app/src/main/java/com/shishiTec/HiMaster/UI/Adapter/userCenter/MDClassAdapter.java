package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.MCourseBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.CourseDetailActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;


public class MDClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MCourseBean> classBeen;
    private BaseApplication baseApplication;

    public MDClassAdapter(Context context, List<MCourseBean> classBeen) {
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
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_mall_fragment_class_item, null);
        MViewHolder holder = new MViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mViewHolder, final int arg1) {
        ((MViewHolder) mViewHolder).mall_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("course_id", classBeen.get(arg1).getCourse_id());
                intent.setClass(context, CourseDetailActivity.class);
                context.startActivity(intent);
            }
        });
        baseApplication.loadImageView(context, ((MViewHolder) mViewHolder).iv_course_cover, classBeen.get(arg1).getCover());
        ((MViewHolder) mViewHolder).tv_course_name.setText(classBeen.get(arg1).getTitle());
        ((MViewHolder) mViewHolder).tv_course_price.setText(classBeen.get(arg1).getPrice());
        ((MViewHolder) mViewHolder).tv_store_name.setText(classBeen.get(arg1).getStore());
        ((MViewHolder) mViewHolder).tv_km.setText(classBeen.get(arg1).getDistance());
        if (classBeen.get(arg1).getTags().size() >= 4) {
            for (int i = 0; i < 5; i++) {
                switch (i) {
                    case 0:
                        ((MViewHolder) mViewHolder).tag1.setText(classBeen.get(arg1).getTags().get(0));
                        ((MViewHolder) mViewHolder).tag1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        ((MViewHolder) mViewHolder).tag2.setText(classBeen.get(arg1).getTags().get(1));
                        ((MViewHolder) mViewHolder).tag2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        ((MViewHolder) mViewHolder).tag3.setText(classBeen.get(arg1).getTags().get(2));
                        ((MViewHolder) mViewHolder).tag3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        ((MViewHolder) mViewHolder).tag4.setText(classBeen.get(arg1).getTags().get(3));
                        ((MViewHolder) mViewHolder).tag4.setVisibility(View.VISIBLE);
                        break;
                }
            }
        } else if (classBeen.get(arg1).getTags().size() > 0) {
            for (int i = 0; i < classBeen.get(arg1).getTags().size(); i++) {
                switch (i) {
                    case 0:
                        ((MViewHolder) mViewHolder).tag1.setText(classBeen.get(arg1).getTags().get(0));
                        ((MViewHolder) mViewHolder).tag1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        ((MViewHolder) mViewHolder).tag2.setText(classBeen.get(arg1).getTags().get(1));
                        ((MViewHolder) mViewHolder).tag2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        ((MViewHolder) mViewHolder).tag3.setText(classBeen.get(arg1).getTags().get(2));
                        ((MViewHolder) mViewHolder).tag3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        ((MViewHolder) mViewHolder).tag4.setText(classBeen.get(arg1).getTags().get(3));
                        ((MViewHolder) mViewHolder).tag4.setVisibility(View.VISIBLE);
                        break;
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
    public void addMoreItem(List<MCourseBean> newDatas) {
        classBeen = newDatas;
        notifyDataSetChanged();
    }

}
