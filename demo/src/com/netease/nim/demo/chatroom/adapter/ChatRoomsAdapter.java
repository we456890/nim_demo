package com.netease.nim.demo.chatroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.chatroom.helper.ChatRoomHelper;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseQuickAdapter;
import com.netease.nim.uikit.common.ui.recyclerview.holder.BaseViewHolder;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;

/**
 * Created by huangjun on 2016/12/9.
 */
public class ChatRoomsAdapter extends BaseAdapter {
    private Context context;
    private int[] imagelist;
    private String[] textlist;

    public ChatRoomsAdapter(Context context, int[] imagelist, String[] textlist) {
        this.context = context;
        this.imagelist = imagelist;
        this.textlist = textlist;
    }

    @Override
    public int getCount() {
        return textlist.length;
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
            view = LayoutInflater.from(context).inflate(R.layout.chat_room_item, null);
            holder = new ViewHolder();
            holder.grid_text = (TextView) view.findViewById(R.id.grid_text);
            holder.imageView = (ImageView) view.findViewById(R.id.grid_image);
            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }
        holder.grid_text.setText(textlist[i]);
        holder.imageView.setImageResource(imagelist[i]);

        return view;
    }

    static class ViewHolder {
        TextView grid_text;
        ImageView imageView;
    }
}
