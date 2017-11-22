package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.ScoreDetailBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.AppUtils;
import com.shishiTec.HiMaster.Utils.DateUtil;
import com.shishiTec.HiMaster.Utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Pursue on 16/4/26.
 */
public class MyMdDetailAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater inflater;
    private Map<String,List<ScoreDetailBean.Credit>> childItem;
    private ArrayList<String> parentItem;
    private ExpandableListView expandableListView;

    public MyMdDetailAdapter(Context context, Map<String,List<ScoreDetailBean.Credit>> childItem, ArrayList<String> parentItem, ExpandableListView expandableListView) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.childItem = childItem;
        this.parentItem = parentItem;
        this.expandableListView = expandableListView;
    }

    @Override
    public int getGroupCount() {
        return parentItem.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupName = parentItem.get(groupPosition);
        List list = childItem.get(groupName);
        if (list==null){
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentItem.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentHolder parentholder = null;
        if (parentholder == null) {
            convertView = inflater.inflate(R.layout.md_parent_layout, null);
            parentholder = new ParentHolder(convertView);
            convertView.setTag(parentholder);
        } else {
            parentholder = (ParentHolder) convertView.getTag();
        }
        parentholder.parentTitle.setText(parentItem.get(groupPosition));
        convertView.setClickable(true);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (childHolder == null) {
            convertView = inflater.inflate(R.layout.md_child_layout, null);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        String groupName = parentItem.get(groupPosition);
        List<ScoreDetailBean.Credit> list = childItem.get(groupName);
        ScoreDetailBean.Credit credit = list.get(childPosition);
        childHolder.childTitle.setText(credit.getInstruct());
        childHolder.mTime.setText(DateUtil.getMonthDay(Long.parseLong(credit.getAddtime())));
        int my_m = Integer.parseInt(credit.getGet()) - Integer.parseInt(credit.getPut());
        if (my_m>0){
            childHolder.getMNumber.setTextColor(Color.parseColor("#009943"));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("+");
            stringBuilder.append(my_m);
            childHolder.getMNumber.setText(stringBuilder.toString());
        }else {
            childHolder.getMNumber.setTextColor(Color.parseColor("#e50011"));
            childHolder.getMNumber.setText(my_m+"");
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ParentHolder {
        @Bind(R.id.parent_title)
        TextView parentTitle;
        public ParentHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
    static class ChildHolder {
        @Bind(R.id.child_title)
        TextView childTitle;
        @Bind(R.id.m_time)
        TextView mTime;
        @Bind(R.id.get_m_number)
        TextView getMNumber;

        public ChildHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        int groupCount = parentItem.size();
        super.notifyDataSetChanged();
        for (int i=0; i<groupCount; i++) {
            expandableListView.expandGroup(i);
        }
    }
}
