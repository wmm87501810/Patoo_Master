package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.FansAndFollowBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Pursue on 16/4/29.
 */
public class FansAdapter extends RecyclerView.Adapter<FansAdapter.ViewHolder> {
    private Context context;
    private List<FansAndFollowBean> mData;
    private OnItemClickListener onItemClickListener;
    private OnCheckedChangeListener onCheckedChangeListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener){
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public FansAdapter(Context context,List<FansAndFollowBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.fans_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        FansAndFollowBean fansAndFollowBean = mData.get(position);
        BaseApplication.getInstance().loadRoundImageView(context,holder.fansIcon,fansAndFollowBean.getImg_top());
        holder.intro.setText(fansAndFollowBean.getIntro());
        holder.nikeName.setText(fansAndFollowBean.getNikename());
        holder.checkFocus.setOnCheckedChangeListener(null);
        if (fansAndFollowBean.getIs_follow().equals("1")){
            holder.checkFocus.setChecked(true);
        }else {
            holder.checkFocus.setChecked(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.OnItemClick(position,holder.itemView);
                }
            }
        });
        holder.checkFocus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onCheckedChangeListener!=null){
                    onCheckedChangeListener.onCheckedChanged(position,buttonView,isChecked);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener{
        void OnItemClick(int position, View view);
    }

    public interface OnCheckedChangeListener{
        void onCheckedChanged(int position,CompoundButton buttonView, boolean isChecked);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.fans_icon)
        ImageView fansIcon;
        @Bind(R.id.check_focus)
        CheckBox checkFocus;
        @Bind(R.id.nikeName)
        TextView nikeName;
        @Bind(R.id.intro)
        TextView intro;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
