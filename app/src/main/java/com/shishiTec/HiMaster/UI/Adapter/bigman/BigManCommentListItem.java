package com.shishiTec.HiMaster.UI.Adapter.bigman;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.bigManCommentBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.FileUtil;

import java.util.List;

/**
 * Created by hyu_anzhuo on 2016/4/29.
 */

public class BigManCommentListItem extends RecyclerView.Adapter<BigManCommentListItem.ItemViewHolder> {
    private Context context;
    private List<bigManCommentBean.ReplyBean> bigManBean;
    public BigManCommentListItem(Context context,List<bigManCommentBean.ReplyBean> bigManBean) {
        super();
        this.context = context;
        this.bigManBean = bigManBean;
    }
    @Override
    public int getItemCount() {
        return bigManBean==null?0:bigManBean.size();
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_bigman_comment_list_item, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder mViewHolder, int arg1) {
        mViewHolder.tv_bigMan_adapter_comment_list_item.setText(FileUtil.replaceFace(bigManBean.get(arg1).getContent()));
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_bigMan_adapter_comment_list_item;

        public ItemViewHolder(View view) {
            super(view);
            this.tv_bigMan_adapter_comment_list_item = (TextView) itemView.findViewById(R.id.tv_bigMan_adapter_comment_list_item);
        }
    }
}

