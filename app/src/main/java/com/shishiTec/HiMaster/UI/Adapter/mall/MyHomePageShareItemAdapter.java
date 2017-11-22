package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shishiTec.HiMaster.Model.bean.UserHomePageBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;


public class MyHomePageShareItemAdapter extends RecyclerView.Adapter<MyHomePageShareItemAdapter.ItemViewHolder> {
    private Context context;
    private List<UserHomePageBean.ShareListsBean.DetailBean> bigManItemBean;
    private BaseApplication baseApplication;
    public MyHomePageShareItemAdapter(Context context, List<UserHomePageBean.ShareListsBean.DetailBean> bigManItemBean) {
        super();
        this.context = context;
        this.bigManItemBean = bigManItemBean;
        baseApplication = BaseApplication.getInstance();
    }
    @Override
    public int getItemCount() {
        return bigManItemBean.size();
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.bigman_fragment_layout_item_imageitem, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder mViewHolder, int arg1) {
            baseApplication.loadImageALLView(context,mViewHolder.item_image,bigManItemBean.get(arg1).getImg_url());
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_image;

        public ItemViewHolder(View view) {
            super(view);
            this.item_image = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
}
