package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.SpecialBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.CourseDetailActivity;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;


public class MallSpecialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SpecialBean.SubjectListBean> subjectListBeen;
    private BaseApplication baseApplication;
    private MallSpecialItemAdapter mallSpecialItemAdapter;
    private LinearLayoutManager linearLayoutManager;

    public MallSpecialAdapter(Context context, List<SpecialBean.SubjectListBean> subjectListBeen) {
        super();
        this.context = context;
        this.subjectListBeen = subjectListBeen;
        baseApplication = BaseApplication.getInstance();
    }

    @Override
    public int getItemCount() {
        return subjectListBeen == null ? 0 : subjectListBeen.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
//            View view = View.inflate(viewGroup.getContext(), R.layout.adapter_mall_fragment_special_image_item, null);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_mall_fragment_special_image_item, viewGroup, false);
        MViewHolder holder = new MViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mViewHolder, final int arg1) {
        if (mViewHolder instanceof MViewHolder) {
            baseApplication.loadImageALLView(context, ((MViewHolder) mViewHolder).special_title_img, subjectListBeen.get(arg1).getCover());
            ((MViewHolder) mViewHolder).special_title_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("course_id", subjectListBeen.get(arg1).getSubject_id());
                    intent.setClass(context, CourseDetailActivity.class);
                    context.startActivity(intent);
                }
            });
            linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((MViewHolder) mViewHolder).recyclerView.setLayoutManager(linearLayoutManager);
            mallSpecialItemAdapter = new MallSpecialItemAdapter(context, subjectListBeen.get(arg1).getCourse_list());
            ((MViewHolder) mViewHolder).recyclerView.setAdapter(mallSpecialItemAdapter);
        }
    }

    public static class MViewHolder extends RecyclerView.ViewHolder {
        private ImageView special_title_img;
        private RecyclerView recyclerView;

        public MViewHolder(View view) {
            super(view);
            special_title_img = (ImageView) view.findViewById(R.id.special_title_img);

            recyclerView = (RecyclerView) view.findViewById(R.id.adapter_mall_special_item_recycle);
        }
    }


    //添加数据
    public void addMoreItem(List<SpecialBean.SubjectListBean> newDatas) {
        subjectListBeen.addAll(newDatas);
        notifyDataSetChanged();
    }
}
