package com.shishiTec.HiMaster.UI.Adapter.master;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.GetMasterCourseBean;
import com.shishiTec.HiMaster.R;

import java.util.List;

/**
 * Created by hyu on 2016/5/11.
 */
public class AddTagAdapter extends RecyclerView.Adapter<AddTagAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;
    private Resources res;
    private OnItemClickListener listener;

    public AddTagAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_add_tag, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_tag_name.setText(list.get(position));

        /**
         * 调用接口回调
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onItemClick(position,list);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_tag_name;

        public MyViewHolder(View view) {
            super(view);
            tv_tag_name = (TextView) view.findViewById(R.id.tv_tag_name);
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

    public void onChangeList(List<String> list){
        this.list = list;
    }
}