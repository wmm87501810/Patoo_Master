package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.ClassBean;
import com.shishiTec.HiMaster.Model.bean.SearchKeyWordsBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.CourseDetailActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;


public class MallPopupClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //上拉加载更多
    public static final int  PULLUP_LOAD_MORE=0;
    //正在加载中
    public static final int  LOADING_MORE=1;
    //上拉加载更多状态-默认为0
    public int load_more_status=0;
    private static final int TYPE_ITEM =0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //底部FootView
    private Context context;
    private List<SearchKeyWordsBean> classBeen;
    private BaseApplication baseApplication;
    public MallPopupClassAdapter(Context context, List<SearchKeyWordsBean> classBeen) {
        super();
        this.context = context;
        this.classBeen = classBeen;
        baseApplication = BaseApplication.getInstance();
    }
    @Override
    public int getItemCount() {
        return classBeen.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        //进行判断显示类型，来创建返回不同的View
        if(arg1==TYPE_ITEM){
            View view = View.inflate(viewGroup.getContext(), R.layout.adapter_mall_fragment_class_item, null);
            MViewHolder holder = new MViewHolder(view);
            return holder;
        }else if(arg1==TYPE_FOOTER){
            View foot_view = View.inflate(viewGroup.getContext(), R.layout.load_more_footview_layout, null);
            MallPopupClassAdapter.FootViewHolder footViewHolder=new MallPopupClassAdapter.FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;

    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mViewHolder, final int arg1) {
        if(mViewHolder instanceof MViewHolder) {
            ((MViewHolder)mViewHolder).mall_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("course_id",classBeen.get(arg1).getCourse_id());
                    intent.setClass(context, CourseDetailActivity.class);
                    context.startActivity(intent);
                }
            });
            baseApplication.loadImageView(context, ((MViewHolder)mViewHolder).iv_course_cover,classBeen.get(arg1).getCover());
            ((MViewHolder)mViewHolder).tv_course_name.setText(classBeen.get(arg1).getTitle());
            ((MViewHolder)mViewHolder).tv_course_price.setText(classBeen.get(arg1).getPrice());
            ((MViewHolder)mViewHolder).tv_store_name.setText(classBeen.get(arg1).getStore());
            ((MViewHolder)mViewHolder).tv_km.setText(classBeen.get(arg1).getDistance());
            if(classBeen.get(arg1).getTags().size()>=4){
                for(int i=0;i<5;i++){
                    switch (i){
                        case 0:
                            ((MViewHolder)mViewHolder).tag1.setText(classBeen.get(arg1).getTags().get(0));
                            ((MViewHolder)mViewHolder).tag1.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            ((MViewHolder)mViewHolder).tag2.setText(classBeen.get(arg1).getTags().get(1));
                            ((MViewHolder)mViewHolder).tag2.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            ((MViewHolder)mViewHolder).tag3.setText(classBeen.get(arg1).getTags().get(2));
                            ((MViewHolder)mViewHolder).tag3.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            ((MViewHolder)mViewHolder).tag4.setText(classBeen.get(arg1).getTags().get(3));
                            ((MViewHolder)mViewHolder).tag4.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }else if(classBeen.get(arg1).getTags().size()>0){
                for(int i=0;i<classBeen.get(arg1).getTags().size();i++){
                    switch (i){
                        case 0:
                            ((MViewHolder)mViewHolder).tag1.setText(classBeen.get(arg1).getTags().get(0));
                            ((MViewHolder)mViewHolder).tag1.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            ((MViewHolder)mViewHolder).tag2.setText(classBeen.get(arg1).getTags().get(1));
                            ((MViewHolder)mViewHolder).tag2.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            ((MViewHolder)mViewHolder).tag3.setText(classBeen.get(arg1).getTags().get(2));
                            ((MViewHolder)mViewHolder).tag3.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            ((MViewHolder)mViewHolder).tag4.setText(classBeen.get(arg1).getTags().get(3));
                            ((MViewHolder)mViewHolder).tag4.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }

        }else if(mViewHolder instanceof FootViewHolder){
            FootViewHolder footViewHolder=(FootViewHolder)mViewHolder;
            switch (load_more_status){
                case PULLUP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    break;
            }
        }
    }

    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends  RecyclerView.ViewHolder{
        private TextView foot_view_item_tv;
        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv=(TextView)view.findViewById(R.id.load_next_page_text);
        }
    }

    public static class MViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_course_cover;
        private TextView tv_course_name,tv_km,tv_course_price,tv_store_name,tag1,tag2,tag3,tag4;
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
    public void addMoreItem(List<SearchKeyWordsBean> newDatas) {
        classBeen = newDatas;
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     * @param status
     */
    public void changeMoreStatus(int status){
        load_more_status=status;
        notifyDataSetChanged();
    }
}
