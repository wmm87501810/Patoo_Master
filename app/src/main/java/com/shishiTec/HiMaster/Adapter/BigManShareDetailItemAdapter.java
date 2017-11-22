package com.shishiTec.HiMaster.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.ShareDetailBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.FileUtil;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;


public class BigManShareDetailItemAdapter extends RecyclerView.Adapter<BigManShareDetailItemAdapter.ItemViewHolder> {
    private Context context;
    private List<ShareDetailBean.CommentListBean> bigManItemBean;
    private BaseApplication baseApplication;
    public BigManShareDetailItemAdapter(Context context, List<ShareDetailBean.CommentListBean> bigManItemBean) {
        super();
        this.context = context;
        this.bigManItemBean = bigManItemBean;
        baseApplication = BaseApplication.getInstance();
    }
    @Override
    public int getItemCount() {
        return bigManItemBean==null?0:bigManItemBean.size();
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.bigman__sharedetails_item, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder mViewHolder, int arg1) {
        baseApplication.loadRoundImageView(context,mViewHolder.user_logo,bigManItemBean.get(arg1).getImg_top());
        mViewHolder.user_name.setText(bigManItemBean.get(arg1).getNikename().toString().trim());
        mViewHolder.user_content.setText(FileUtil.replaceFace(bigManItemBean.get(arg1).getContent().toString().trim()));
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView user_logo;
        public TextView user_name,user_content;
        public ItemViewHolder(View view) {
            super(view);
            this.user_logo = (ImageView) itemView.findViewById(R.id.detail_user_logo);
            this.user_name = (TextView) itemView.findViewById(R.id.user_name);
            this.user_content = (TextView) itemView.findViewById(R.id.tv_bigMan_adapter_comment_list_item);
        }
    }
    //添加数据
    public void addMoreItem(List<ShareDetailBean.CommentListBean> newDatas) {
        bigManItemBean.addAll(newDatas);
    }
}
