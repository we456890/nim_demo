package com.netease.nim.demo.borrow.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.demo.R;
import com.netease.nim.uikit.common.bean.Borrow;

import java.util.List;

/**
 * Created by 78560 on 2017/8/29.
 */

public class BorrowAdapter extends BaseQuickAdapter<Borrow,BaseViewHolder> {

    public BorrowAdapter(@LayoutRes int layoutResId, @Nullable List<Borrow> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Borrow item) {
        double money=Double.parseDouble(item.getAmount().toString());
        helper.setText(R.id.borrow_money_time,item.getPrayTime())
                .setText(R.id.borrow_money_flow,item.getFrameTyle())
                .setText(R.id.borrow_money_cause,"借款事由:"+item.getReason())
                .setText(R.id.borrow_money_name,"借款人:"+item.getBorrower())
                .setText(R.id.borrow_money_money,"金额:￥"+money);
        helper.setTag(R.id.borrow_money_time,item.getId());
        if(item.getFrameTyle().equals("同意")){
            helper.setBackgroundRes(R.id.viewed,R.mipmap.bmxt_consent);
            helper.setTextColor(R.id.borrow_money_flow, Color.parseColor("#4FD50D"));
        }else if(item.getFrameTyle().equals("拒绝")){
            helper.setBackgroundRes(R.id.viewed,R.mipmap.bmxt_reject);
            helper.setTextColor(R.id.borrow_money_flow, Color.parseColor("#D32F5A"));
        }else{
            helper.setBackgroundRes(R.id.viewed,R.mipmap.bmxt_consent);
            helper.setTextColor(R.id.borrow_money_flow, Color.parseColor("#FF9A2E"));
        }
    }
}
