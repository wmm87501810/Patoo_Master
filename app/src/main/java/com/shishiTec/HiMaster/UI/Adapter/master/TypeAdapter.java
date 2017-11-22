package com.shishiTec.HiMaster.UI.Adapter.master;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.MasterStartBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

/**
 * Created by hyu on 2016/4/25.
 */
public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder> {

    private Context context;
    private List<MasterStartBean.CategoryListBean> list;
    private Resources res;
    private OnItemClickListener listener;

    public TypeAdapter(Context context, List<MasterStartBean.CategoryListBean> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_master_type, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final MasterStartBean.CategoryListBean bean = list.get(position);
        holder.title.setText(bean.getName());
        BaseApplication.getInstance().loadCircleImageView(context,holder.imageView,bean.getPic_url()+"");

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

        private ImageView imageView;
        private TextView title;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.iv_master_type_name);
            title = (TextView) view.findViewById(R.id.tv_master_type_name);
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
