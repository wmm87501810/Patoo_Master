package com.shishiTec.HiMaster.UI.Adapter.master;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.MasterListsBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.master.MasterDetailActivity;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * Created by hyu on 2016/4/26.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {

    private Context context;
    private List<MasterListsBean.ShareListBean> list;
    private Resources res;
    private OnItemClickListener listener;

    public ContentAdapter(Context context, List<MasterListsBean.ShareListBean> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_master_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final MasterListsBean.ShareListBean bean = list.get(position);
        if(list!=null && list.size()>0){
            //内容区图片
            BaseApplication.getInstance().loadRoundImageView(context,holder.iv_content_img,list.get(position).getCover());
            //头像
            BaseApplication.getInstance().loadRoundImageView(context,holder.iv_content_logo,list.get(position).getImg_top());
            holder.tv_content_nikname.setText(list.get(position).getNikename());
            holder.tv_content_type_name.setText(list.get(position).getTag_name());
            holder.tv_content_liulan.setText(list.get(position).getBrowse_count());
            holder.tv_content_info.setText(list.get(position).getContent());
            holder.tv_content_title.setText(list.get(position).getTitle());
            holder.ll_master_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MasterDetailActivity.class);
                    ToastUtils.show(context,"boom",ToastUtils.LENGTH_LONG);
                    context.startActivity(intent);
                }
            });

        }

        /**
         * 调用接口回调
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onItemClick(position, bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AutoLinearLayout ll_master_content;
        private ImageView iv_content_img;
        private ImageView iv_content_logo;
        private ImageView iv_content_liulan;
        private TextView tv_content_liulan;
        private TextView tv_content_info;
        private TextView tv_content_title;
        private TextView tv_content_nikname;
        private TextView tv_content_type_name;

        public MyViewHolder(View view) {
            super(view);
            ll_master_content = (AutoLinearLayout)view.findViewById(R.id.ll_master_content);
            iv_content_img = (ImageView) view.findViewById(R.id.iv_content_img);
            iv_content_logo = (ImageView) view.findViewById(R.id.iv_content_logo);
            iv_content_liulan = (ImageView) view.findViewById(R.id.iv_content_liulan);
            tv_content_info = (TextView) view.findViewById(R.id.tv_content_info);
            tv_content_title = (TextView) view.findViewById(R.id.tv_content_title);
            tv_content_nikname = (TextView) view.findViewById(R.id.tv_content_nikname);
            tv_content_liulan = (TextView) view.findViewById(R.id.tv_content_liulanshu);
            tv_content_type_name = (TextView) view.findViewById(R.id.tv_content_type_name);
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
