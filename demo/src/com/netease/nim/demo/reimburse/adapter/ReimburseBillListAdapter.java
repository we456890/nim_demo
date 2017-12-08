package com.netease.nim.demo.reimburse.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.demo.R;
import com.netease.nim.uikit.common.bean.ReimburseList;

import java.util.List;

/**
 * Created by 78560 on 2017/8/29.
 */

public class ReimburseBillListAdapter extends BaseQuickAdapter<ReimburseList,BaseViewHolder> {

    public ReimburseBillListAdapter(@LayoutRes int layoutResId, @Nullable List<ReimburseList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ReimburseList item) {
        helper.setText(R.id.rbitem_money,"金额:￥"+item.getAmount())
//                .setText(R.id.rbitem_num,item.getTitle())
                .setText(R.id.rbitem_cause,"说明:"+item.getBz())
                .setText(R.id.rbitem_type,"类别:"+item.getStateType())
                .setText(R.id.rbitem_time,"发票日期:"+item.getPaydate());
        helper.setTag(R.id.rbitem_money,item.getId());
    }
}
