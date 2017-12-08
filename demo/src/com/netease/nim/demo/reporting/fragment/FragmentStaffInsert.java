package com.netease.nim.demo.reporting.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.demo.R;
import com.netease.nim.demo.reporting.activity.ReportingStaffInsterActivity;
import com.netease.nim.uikit.common.fragment.TFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 员工新增汇报
 *
 * Created by 78560 on 2017/8/25.
 *
 */

public class FragmentStaffInsert extends TFragment {

    @Bind(R.id.insert_daily)
    LinearLayout insert_daily;
    @Bind(R.id.insert_weekly)
    LinearLayout insert_weekly;
    @Bind(R.id.insert_monthly)
    LinearLayout insert_monthly;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.staffinster, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.insert_daily)
    public void insert_daily(View view) {
        Intent intent = new Intent();
        intent.putExtra("type", "日报");
        intent.setClass(getActivity(), ReportingStaffInsterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.insert_monthly)
    public void insert_monthly(View view) {
        Intent intent = new Intent();
        intent = new Intent();
        intent.putExtra("type", "月报");
        intent.setClass(getActivity(), ReportingStaffInsterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.insert_weekly)
    public void insert_weekly(View view) {
        postRunnable(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
