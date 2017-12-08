package com.netease.nim.demo.reimburse.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.demo.R;
import com.netease.nim.uikit.common.bean.Reimburse;

import java.util.List;

/**
 * Created by 78560 on 2017/8/29.
 */

public class ReimburseMoneyAdapter extends BaseQuickAdapter<Reimburse,BaseViewHolder> {

    public ReimburseMoneyAdapter(@LayoutRes int layoutResId, @Nullable List<Reimburse> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Reimburse item) {
        helper.setText(R.id.remiurserjob,"报销单编号:"+item.getRemiurserJob())
//                .setText(R.id.frameTyle,item.getFrameTyle())
                .setText(R.id.remiurseHole,"总金额:￥"+item.getRemiurseHole())
                .setText(R.id.docs,"发票数:"+item.getDocs())
                .setText(R.id.reimurser,"报销人:"+item.getReimurser())
                .setText(R.id.reimurseTime,"申请时间:"+item.getReimurseTime());
        helper.setTag(R.id.remiurserjob,item.getId());
        helper.setBackgroundRes(R.id.viewed,R.mipmap.bmxt_consent);
        if(item.getDocs()==null) {
            helper.setText(R.id.docs,"发票数:0");
        }else{
            helper.setText(R.id.docs,"发票数:"+item.getDocs());
        }
        if(item.getFrameTyle()==null) {
            helper.setText(R.id.frameTyle,"待提交");
        }else if(item.getFrameTyle().equals("拒绝")){
            helper.setBackgroundRes(R.id.viewed,R.mipmap.bmxt_reject);
            helper.setText(R.id.frameTyle,item.getFrameTyle());
        }else{
            helper.setText(R.id.frameTyle,item.getFrameTyle());
        }

    }
}
