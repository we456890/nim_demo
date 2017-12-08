package com.netease.nim.demo.borrow.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.demo.R;
import com.netease.nim.uikit.common.bean.FrameRecord;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 78560 on 2017/8/29.
 */

public class BorrowCheckBMAdapter extends BaseQuickAdapter<FrameRecord,BaseViewHolder> {

    public BorrowCheckBMAdapter(@LayoutRes int layoutResId, @Nullable List<FrameRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FrameRecord item) {
        if(item.getChosen().equals("拒绝")){
            helper.setBackgroundRes(R.id.image,R.mipmap.bmxt_reject);
            helper.setTextColor(R.id.reply_state, Color.parseColor("#FF0000"));
        }else{
            helper.setBackgroundRes(R.id.image,R.mipmap.bmxt_consent);
            helper.setTextColor(R.id.reply_state, Color.parseColor("#000000"));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long t=item.getCreateTime();
        String time=sdf.format(t);
        helper.setText(R.id.reply_state,item.getChosen())
                .setText(R.id.reply_cause, item.getContext().equals("") ? "\t\t\t\t同意" : "\t\t\t\t"+item.getContext())
//                .setText(R.id.reply_branch,item.getReason())
                .setText(R.id.reply_result,"批复结果:"+item.getChosen())
                .setText(R.id.reply_time,"批复时间:"+time)
                .setText(R.id.borrow_approvename,"批复人:"+item.getProcessor());
        HeadImageView headImageView=helper.getView(R.id.user_head);
        headImageView.loadBuddyAvatar(item.getProcessorAccount());

    }
}
