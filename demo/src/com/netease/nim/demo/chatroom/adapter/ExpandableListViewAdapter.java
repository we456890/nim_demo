package com.netease.nim.demo.chatroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.demo.R;

/**
 * Created by 78560 on 2017/11/4.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    Context context;
    private int[] image;
    private String[] groupStrings;
    public String[][] childStrings;

    public ExpandableListViewAdapter(Context context, String[] groupStrings, String[][] childStrings, int[] image) {
        this.context = context;
        this.groupStrings = groupStrings;
        this.childStrings = childStrings;
        this.image=image;
    }

    //        获取分组的个数
    @Override
    public int getGroupCount() {
        return groupStrings.length;
    }

    //        获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return childStrings[groupPosition].length;
    }

    //        获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return groupStrings[groupPosition];
    }

    //        获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childStrings[groupPosition][childPosition];
    }

    //        获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //        获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //        分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //        获取显示指定分组的视图
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expand_group, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
            groupViewHolder.img=(ImageView) convertView.findViewById(R.id.img_group);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tvTitle.setText(groupStrings[groupPosition]);
        groupViewHolder.img.setImageResource(image[groupPosition]);
        return convertView;
    }

    //        获取显示指定分组中的指定子选项的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expand_child, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_child);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tvTitle.setText(childStrings[groupPosition][childPosition]);
        return convertView;
    }

    //        指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvTitle;
        ImageView img;
    }

    static class ChildViewHolder {
        TextView tvTitle;
    }
}