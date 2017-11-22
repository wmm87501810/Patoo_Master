package com.shishiTec.HiMaster.Adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.bigManCommentBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.bigman.BigManCommentListItem;
import com.shishiTec.HiMaster.Utils.FileUtil;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;


public class bigManCommentAdapter extends RecyclerView.Adapter<bigManCommentAdapter.MViewHolder> {
    public Activity context;
    private List<bigManCommentBean> bigManBean;
    private BaseApplication baseApplication;
    private OnItemClickListener listener;
    private BigManCommentListItem commentListItem;

    public bigManCommentAdapter(Activity context, List<bigManCommentBean> bigManBean) {
        super();
        this.context = context;
        this.bigManBean = bigManBean;
        baseApplication = BaseApplication.getInstance();
    }

    @Override
    public int getItemCount() {
        return bigManBean == null ? 0 : bigManBean.size();
    }

    /**
     * 接口回调
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


    @Override
    public MViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.activity_big_man_comment_item, null);
        MViewHolder holder = new MViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, final int arg1) {
        final bigManCommentBean bean = bigManBean.get(arg1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(arg1, bean);
                }
            }
        });
        holder.comment_content.setText(FileUtil.replaceFace(bigManBean.get(arg1).getContent()));
        holder.user_name.setText(bigManBean.get(arg1).getNikename());
        baseApplication.loadRoundImageView(context, holder.comment_user_logo, bigManBean.get(arg1).getImg_top());
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        holder.comment_release.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        holder.comment_release.setHasFixedSize(true);
        //创建并设置Adapter
        commentListItem = new BigManCommentListItem(context, bigManBean.get(arg1).getReply());
        holder.comment_release.setAdapter(commentListItem);
    }

    public static class MViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, comment_content;
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
    public void addMoreItem(List<bigManCommentBean> newDatas) {
        bigManBean.addAll(newDatas);
        notifyDataSetChanged();
    }

}
