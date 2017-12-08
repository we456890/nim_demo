package com.netease.nim.demo.reporting.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.demo.R;
import com.netease.nim.uikit.common.bean.Reporting;

import java.util.List;

/**
 * Created by 78560 on 2017/8/29.
 */

public class ReportingAdapter extends BaseQuickAdapter<Reporting,BaseViewHolder> {

    public ReportingAdapter(@LayoutRes int layoutResId, @Nullable List<Reporting> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Reporting item) {
        helper.setText(R.id.reporing_item_title,item.getAutoTitle())
                .setText(R.id.reporing_item_subtitle,item.getTitle())
                .setText(R.id.reporing_item_content,item.getContext())
                .setText(R.id.reporing_item_name,item.getReporter())
                .setText(R.id.reporing_item_time,item.getReportTime());
        helper.setTag(R.id.reporing_item_title,item.getId());
    }
}
