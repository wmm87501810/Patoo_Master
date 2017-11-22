package com.shishiTec.HiMaster.UI.Adapter.bigman;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;


public class ReleaseItemAdapter extends RecyclerView.Adapter<ReleaseItemAdapter.ItemViewHolder> {
    private Context context;
    private List list;
    private BaseApplication baseApplication;
    private OnItemClickListener listener;
    public ReleaseItemAdapter(Context context,List list) {
        super();
        this.context = context;
        this.list = list;
        baseApplication = BaseApplication.getInstance();
    }
    public ReleaseItemAdapter(Context context) {
        super();
        this.context = context;
        baseApplication = BaseApplication.getInstance();
    }
    /**
     * 接口回调
     */
    public interface  OnItemClickListener{
        void onItemClick(int position);
    }
    /**
     * 设置监听方法
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //添加数据
    public void addMoreItem(List newDatas) {
        list.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_release_imageitem, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder mViewHolder,final int arg1) {
        baseApplication.loadImageALLView(context,mViewHolder.release_item_image,"");
        mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onItemClick(arg1);
                }
            }
        });

    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView release_item_image;

        public ItemViewHolder(View view) {
            super(view);
            this.release_item_image = (ImageView) itemView.findViewById(R.id.release_item_image);
        }
    }
}
