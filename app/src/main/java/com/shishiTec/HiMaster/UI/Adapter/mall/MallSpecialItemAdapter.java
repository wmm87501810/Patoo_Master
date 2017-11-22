package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.SpecialBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.CourseDetailActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;


public class MallSpecialItemAdapter extends RecyclerView.Adapter<MallSpecialItemAdapter.ItemViewHolder> {
    private Context context;
    private List<SpecialBean.SubjectListBean.CourseListBean> courseListBeen;
    private BaseApplication baseApplication;

    public MallSpecialItemAdapter(Context context, List<SpecialBean.SubjectListBean.CourseListBean> courseListBeen) {
        super();
        this.context = context;
        this.courseListBeen = courseListBeen;
        baseApplication = BaseApplication.getInstance();
    }

    @Override
    public int getItemCount() {
        return courseListBeen.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_mall_special_item, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder mViewHolder, final int arg1) {
        mViewHolder.special_item_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("course_id", courseListBeen.get(arg1).getCourse_id());
                intent.setClass(context, CourseDetailActivity.class);
                context.startActivity(intent);
            }
        });
        baseApplication.loadImageALLView(context, mViewHolder.class_cover, courseListBeen.get(arg1).getCover());
        mViewHolder.class_title.setText(courseListBeen.get(arg1).getTitle());
        mViewHolder.class_new_price.setText(courseListBeen.get(arg1).getPrice());
        mViewHolder.class_old_price.setText(courseListBeen.get(arg1).getMarket_price());
        mViewHolder.class_old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
//        setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView class_cover;
        private TextView class_title, class_new_price, class_old_price;
        private AutoRelativeLayout special_item_ar;

        public ItemViewHolder(View view) {
            super(view);
            class_cover = (ImageView) view.findViewById(R.id.class_cover);
            class_title = (TextView) view.findViewById(R.id.class_title);
            class_old_price = (TextView) view.findViewById(R.id.class_old_price);
            class_new_price = (TextView) view.findViewById(R.id.class_new_price);
            special_item_ar = (AutoRelativeLayout) view.findViewById(R.id.special_item_ar);
        }
    }
}
