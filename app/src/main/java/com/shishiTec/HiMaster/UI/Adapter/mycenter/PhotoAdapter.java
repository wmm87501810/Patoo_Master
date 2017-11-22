package com.shishiTec.HiMaster.UI.Adapter.mycenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.shishiTec.HiMaster.Model.bean.MasterListsBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;
import java.util.List;


/**
 * Created by hyu on 2016/4/25.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;


    public PhotoAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_mycenter_photo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

            BaseApplication.getInstance().loadRoundImageView(context,holder.iv_photo_logo,list.get(position));

            holder.iv_photo_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_photo_logo;


        public MyViewHolder(View view) {
            super(view);
            iv_photo_logo = (ImageView) view.findViewById(R.id.iv_photo_logo);

        }
    }


}
