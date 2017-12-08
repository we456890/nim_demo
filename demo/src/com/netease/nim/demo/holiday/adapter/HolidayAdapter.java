package com.netease.nim.demo.holiday.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.demo.R;
import com.netease.nim.uikit.common.bean.Holiday;

import java.util.List;

/**
 * Created by 78560 on 2017/8/29.
 */

public class HolidayAdapter extends BaseQuickAdapter<Holiday,BaseViewHolder> {

    public HolidayAdapter(@LayoutRes int layoutResId, @Nullable List<Holiday> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Holiday item) {
//        double money=Double.parseDouble(item.getAmount().toString());
        helper.setText(R.id.holiday_type,"请假类型:"+item.getType())
//                .setText(R.id.holiday_flow,item.)
                .setText(R.id.holiday_cause,item.getReason())
                .setText(R.id.holiday_name,"申请人:"+item.getPrayEr())
                .setText(R.id.holiday_time,item.getFromDays()+"至"+item.getToDays())
                .setText(R.id.holiday_praydays,"共:"+item.getPrayDays()+"小时");
        helper.setTag(R.id.holiday_type,item.getId());
//        if(item.getFrameTyle().equals("同意")){
//            helper.setBackgroundRes(R.id.viewed,R.mipmap.bmxt_consent);
//            helper.setTextColor(R.id.borrow_money_flow, Color.parseColor("#4FD50D"));
//        }else if(item.getFrameTyle().equals("拒绝")){
//            helper.setBackgroundRes(R.id.viewed,R.mipmap.bmxt_reject);
//            helper.setTextColor(R.id.borrow_money_flow, Color.parseColor("#D32F5A"));
//        }else{
//            helper.setBackgroundRes(R.id.viewed,R.mipmap.bmxt_consent);
//            helper.setTextColor(R.id.borrow_money_flow, Color.parseColor("#FF9A2E"));
//        }
    }
}
