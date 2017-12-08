package com.netease.nim.demo.holiday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.holiday.fragment.FragmentHolidayListAudit;
import com.netease.nim.demo.holiday.fragment.FragmentHolidayListCheck;
import com.netease.nim.demo.reporting.adapter.Fragmentadapter;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 借款
 * Created by 78560 on 2017/10/10.
 */

public class HolidayActivity extends UI implements View.OnClickListener{
    private static final String TAG = "wk_HolidayActivity";
    private ViewPager mViewPager;
    List<Fragment> mFragmentList = new ArrayList<Fragment>();
    FragmentManager mFragmentManager;
    Fragmentadapter fpa;
    public LinearLayout addll;
    public LinearLayout auditll;
    String[] titleName = new String[]{"我的申请", "我的审核"};
    public ImageView checkIamge,auditIamge;
    public TextView checkText,auditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        setContentView(R.layout.borrowmoney);
        init();
        initFragment();
        fpa = new Fragmentadapter(getSupportFragmentManager(), mFragmentList);
        initView();
        initViewPager();
    }

    private void init() {
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        ImageView add_inseter=(ImageView)findViewById(R.id.add_inseter);
        add_inseter.setVisibility(View.VISIBLE);
        add_inseter.setOnClickListener(addinsert);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.holiday);
    }
    View.OnClickListener addinsert=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HolidayActivity.this,HolidayAddActivity.class);
            startActivity(intent);
        }
    };
    class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //            Log.d(TAG,"onPageScrooled");
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected");
            boolean[] state = new boolean[titleName.length];
            state[position] = true;
            updateBottomLinearLayoutSelect(state[0], state[1]);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(TAG, "onPageScrollStateChanged");
        }
    }
    private void initViewPager() {
        mViewPager.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(fpa);
        mViewPager.setCurrentItem(0);
        updateBottomLinearLayoutSelect(true, false);
    }

    private void updateBottomLinearLayoutSelect(boolean add, boolean audit) {
        addll.setSelected(add);
        auditll.setSelected(audit);
        if(add==true){
            checkIamge.setImageResource(R.mipmap.bm_addon_icon);
            checkText.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            auditIamge.setImageResource(R.mipmap.bm_audit_icon);
            auditText.setTextColor(this.getResources().getColor(R.color.black));
        }else{
            checkIamge.setImageResource(R.mipmap.bm_add_icon);
            checkText.setTextColor(this.getResources().getColor(R.color.black));
            auditIamge.setImageResource(R.mipmap.bm_auditon_icon);
            auditText.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        }

    }

    private void initFragment() {
        FragmentHolidayListCheck check = new FragmentHolidayListCheck();
        FragmentHolidayListAudit audit = new FragmentHolidayListAudit();
        mFragmentList.add(check);
        mFragmentList.add(audit);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.page);

        addll = (LinearLayout) findViewById(R.id.checkll);
        checkIamge= (ImageView) findViewById(R.id.checkImageView);
        checkText= (TextView) findViewById(R.id.checkTextView);
        checkText.setText(titleName[0]);
        addll.setOnClickListener(this);



        auditll = (LinearLayout) findViewById(R.id.insertll);
        auditIamge= (ImageView) findViewById(R.id.insertImageView);
        auditText= (TextView) findViewById(R.id.insertTextView);
        auditText.setText(titleName[1]);
        auditll.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkll:
                mViewPager.setCurrentItem(0);
                updateBottomLinearLayoutSelect(true, false);
                break;

            case R.id.insertll:
                mViewPager.setCurrentItem(1);
                updateBottomLinearLayoutSelect(false, true);
                break;

            default:
                break;
        }
    }
}
