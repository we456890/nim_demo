package com.netease.nim.demo.reporting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.uikit.common.bean.Reversion;

import java.util.List;

/**
 * Created by 78560 on 2017/8/18.
 */

public class CommandAdapter extends BaseAdapter {
    Context context;
    List<Reversion> r;


    public CommandAdapter(Context context, List<Reversion> r) {
        this.context = context;
        this.r = r;
    }
    public CommandAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        if(r==null){
            return 4;
        }else {
            return r.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.repoting_command_item, null);
            holder = new CommandAdapter.ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.ar_detail_name);
            holder.command = (TextView) view.findViewById(R.id.ar_detail_tvcomment);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(r.get(i).getReverName());
        holder.command.setText(r.get(i).getContext());
        return view;
    }

    static class ViewHolder {
        TextView name;
        TextView command;
    }
}
