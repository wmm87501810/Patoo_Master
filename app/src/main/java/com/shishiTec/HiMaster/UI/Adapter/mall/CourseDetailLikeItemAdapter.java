package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.CourseDetailBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.CourseDetailActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;


public class CourseDetailLikeItemAdapter extends RecyclerView.Adapter<CourseDetailLikeItemAdapter.ItemViewHolder> {
    private Context context;
    private List<CourseDetailBean.GuessLikeBean> courseListBeen;
    private BaseApplication baseApplication;
    private OnItemClickListener listener;

    public CourseDetailLikeItemAdapter(Context context, List<CourseDetailBean.GuessLikeBean> courseListBeen) {
        super();
        this.context = context;
        this.courseListBeen = courseListBeen;
        baseApplication = BaseApplication.getInstance();
    }

    @Override
    public int getItemCount() {
        return courseListBeen == null ? 0 : courseListBeen.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.course_like_recycle_item, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder mViewHolder, final int arg1) {
        mViewHolder.like_item_title.setText(courseListBeen.get(arg1).getTitle());
        mViewHolder.like_item_address.setText(courseListBeen.get(arg1).getStore());
        mViewHolder.like_item_Km.setText(courseListBeen.get(arg1).getDistance());
        baseApplication.loadImageALLView(context, mViewHolder.like_item_img, courseListBeen.get(arg1).getCover());
        mViewHolder.like_item_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("course_id", courseListBeen.get(arg1).getCourse_id());
                intent.setClass(context, CourseDetailActivity.class);
                context.startActivity(intent);
            }
        });
        /**
         * 调用接口回调
         */
        mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onItemClick(arg1, courseListBeen);
            }
        });
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView like_item_img;
        private TextView like_item_title, like_item_address, like_item_Km;
        private AutoLinearLayout like_item_ll;

        public ItemViewHolder(View view) {
            super(view);
            like_item_ll = (AutoLinearLayout) view.findViewById(R.id.like_item_ll);
            like_item_img = (ImageView) view.findViewById(R.id.like_item_img);
            like_item_title = (TextView) view.findViewById(R.id.like_item_title);
            like_item_address = (TextView) view.findViewById(R.id.like_item_address);
            like_item_Km = (TextView) view.findViewById(R.id.like_item_Km);
        }
    }

    /**
     * 内部接口回调方法
     */
    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }

    /**
     * 设置监听方法
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
