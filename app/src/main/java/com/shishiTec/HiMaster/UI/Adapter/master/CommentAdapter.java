package com.shishiTec.HiMaster.UI.Adapter.master;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.Adapter.FootAdapter;
import com.shishiTec.HiMaster.Model.bean.MasterCommentListBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.FileUtil;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;


public class CommentAdapter extends FootAdapter {
    public Activity context;
    private List<MasterCommentListBean> bigManBean;
    private BaseApplication baseApplication;
    private OnItemClickListener listener;
    private CommentListItem commentListItem;
    public CommentAdapter(Activity context, List<MasterCommentListBean> bigManBean) {
        super();
        this.context = context;
        this.bigManBean = bigManBean;
        baseApplication = BaseApplication.getInstance();
    }

    @Override
    public int getItemCount() {
        return bigManBean==null?0:bigManBean.size()+1;
    }

    /**
     * 接口回调
     */
    public interface  OnItemClickListener{
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


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        //进行判断显示类型，来创建返回不同的View
        if(arg1==TYPE_ITEM){
            View view = View.inflate(viewGroup.getContext(), R.layout.activity_big_man_comment_item, null);
            MViewHolder holder = new MViewHolder(view);
            return holder;
        }else if(arg1==TYPE_FOOTER){
            View foot_view = View.inflate(viewGroup.getContext(), R.layout.load_more_footview_layout, null);
            CommentAdapter.FootViewHolder footViewHolder=new CommentAdapter.FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mViewHolder, final int arg1) {
    if(mViewHolder instanceof MViewHolder) {
        final MasterCommentListBean bean = bigManBean.get(arg1);
        ((MViewHolder)mViewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onItemClick(arg1, bean);
                }
            }
        });{

        }
        ((MViewHolder)mViewHolder).comment_content.setText(FileUtil.replaceFace(bigManBean.get(arg1).getContent()));
        ((MViewHolder)mViewHolder).user_name.setText(bigManBean.get(arg1).getNikename());
        baseApplication.loadRoundImageView(context,((MViewHolder)mViewHolder).comment_user_logo,bigManBean.get(arg1).getImg_top());
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        ((MViewHolder)mViewHolder).comment_release.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        ((MViewHolder)mViewHolder).comment_release.setHasFixedSize(true);
        //创建并设置Adapter
        commentListItem = new CommentListItem(context,bigManBean.get(arg1).getReply());
        ((MViewHolder)mViewHolder).comment_release.setAdapter(commentListItem);
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
        public TextView user_name,comment_content;
        public ImageView comment_user_logo;
        public RelativeLayout comment_click;
        public RecyclerView comment_release;
        public MViewHolder(View view) {
            super(view);
            this.user_name = (TextView) view.findViewById(R.id.comment_name);
            this.comment_content = (TextView) view.findViewById(R.id.comment_content);
            this.comment_user_logo = (ImageView) itemView.findViewById(R.id.comment_user_logo);
            this.comment_click = (RelativeLayout) itemView.findViewById(R.id.comment_click);
            this.comment_release = (RecyclerView) itemView.findViewById(R.id.comment_release);
        }
    }


    //添加数据
    public void addMoreItem(List<MasterCommentListBean> newDatas) {
        bigManBean.addAll(newDatas);
    }

}
