package com.netease.nim.demo.reporting.activity;

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
import com.netease.nim.demo.reporting.adapter.Fragmentadapter;
import com.netease.nim.demo.reporting.fragment.FragmentBossCheck;
import com.netease.nim.demo.reporting.fragment.FragmentBossCollect;
import com.netease.nim.demo.reporting.fragment.FragmentStaffCheck;
import com.netease.nim.demo.reporting.fragment.FragmentStaffInsert;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
/**
 * Boss工作汇报页面
 */

/**
 * Created by 78560 on 2017/8/17.
 */

public class ReportingBossActivity extends UI implements View.OnClickListener {

    private static final String TAG = "wk_bossReporting";
    private ViewPager mViewPager;
    List<Fragment> mFragmentList = new ArrayList<Fragment>();
    FragmentManager mFragmentManager;
    Fragmentadapter fpa;
    public LinearLayout checkll;
    public LinearLayout insertll;
    int[] icon=new int[]{R.mipmap.ar_check_icon,R.mipmap.ar_vital_icon};
    int[] iconon=new int[]{R.mipmap.ar_checkon_icon,R.mipmap.ar_vitalon_icon};
    String[] titleName = new String[]{"看汇报", "看统计"};
    public ImageView insertIamge,checkIamge;
    public TextView insertText,checkText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_boss_reporting);
        ButterKnife.bind(ReportingBossActivity.this);
        init();
        initFragment();
        fpa = new Fragmentadapter(getSupportFragmentManager(), mFragmentList);
        initView();
        initViewPager();
//        init();
//        initDatePicker();
    }
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
        mViewPager.addOnPageChangeListener(new ReportingBossActivity.ViewPagetOnPagerChangedLisenter());
        mViewPager.setAdapter(fpa);
        mViewPager.setCurrentItem(0);
        updateBottomLinearLayoutSelect(true, false);
    }

    private void updateBottomLinearLayoutSelect(boolean check, boolean insert) {
        checkll.setSelected(check);
        insertll.setSelected(insert);
        if(check==true){
            checkIamge.setImageResource(iconon[0]);
            checkText.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            insertIamge.setImageResource(icon[1]);
            insertText.setTextColor(this.getResources().getColor(R.color.black));
        }else{
            insertIamge.setImageResource(iconon[1]);
            insertText.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            checkIamge.setImageResource(icon[0]);
            checkText.setTextColor(this.getResources().getColor(R.color.black));
        }

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.page);
        checkll = (LinearLayout) findViewById(R.id.checkll);
        checkIamge= (ImageView) findViewById(R.id.checkImageView);
        checkText= (TextView) findViewById(R.id.checkTextView);
        checkText.setText(titleName[0]);
        checkll.setOnClickListener(this);



        insertll = (LinearLayout) findViewById(R.id.insertll);
        insertIamge= (ImageView) findViewById(R.id.insertImageView);
        insertText= (TextView) findViewById(R.id.insertTextView);
        insertText.setText(titleName[1]);
        insertll.setOnClickListener(this);
    }

    private void initFragment() {
        FragmentBossCheck check = new FragmentBossCheck();
        FragmentBossCollect collect = new FragmentBossCollect();
        mFragmentList.add(check);
        mFragmentList.add(collect);
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
    private void init() {
//        acache = ACache.get(this);
        ToolBarOptions options = new ToolBarOptions();
//        Toolbar tb= (Toolbar) findViewById(R.id.menu_toolbar);
//        tb.setOverflowIcon(Drawable.createFromPath(R.drawable.as_menu));
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.menu_reporting_title);

//        PostReporting();
//        staffmenu_listview.setOnItemClickListener(oic);
    }
}
//    ZProgressHUD progressHUD;
//
//    private String selecttime;
//
//    private static String TAG = "wk_ReportingActivity";
//    private ACache acache;
//    @Bind(R.id.ar_selecttime)
//    RelativeLayout ar_selecttime;
//    @Bind(R.id.menu_tv_searchTime)
//    TextView tvsearchTime;
//    @Bind(R.id.menu_tv_right_text)
//    TextView menutvrighttext;
//
//    @Bind(R.id.etdemo)
//    EditText etdemo;
//
//    @Bind(R.id.bosscheck_recyclerview)
//    RecyclerView bosscheck_recyclerview;
//
//    @Bind(R.id.bosscheck_easylayout)
//    EasyRefreshLayout bosscheck_easylayout;
//
//    ReportingDto r;
//    ReportingAdapter reportingAdapter;
//    int count = 2;
//    private int pages;
//    private CustomDatePicker customDatePicker;
//    UserDTO u;
//    List<Reporting> list;

//      @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_boss_reporting);
//        ButterKnife.bind(ReportingBossActivity.this);
//        init();
//    }
//
//
//    @OnClick(R.id.menu_tv_right_text)
//    public void menu_tv_right_text(View view) {
//        FragmentBossCollect.start(ReportingBossActivity.this);
//    }
//
//    private void initDatePicker() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//        String now = sdf.format(new Date());
//        tvsearchTime.setText(now.split(" ")[0]);
//        tvsearchTime.setText(now);
//
//        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
//            @Override
//            public void handle(String time) { // 回调接口，获得选中的时间
//                tvsearchTime.setText(time.split(" ")[0]);
//                selecttime = time.split(" ")[0];
//                getPageReporting(1, 0, selecttime);
//            }
//        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
//        customDatePicker.showSpecificTime(false); // 不显示时和分
//        customDatePicker.setIsLoop(false); // 不允许循环滚动
//    }
//
//    @OnClick(R.id.ar_selecttime)
//    public void ar_selecttime(View view) {
//        customDatePicker.show(tvsearchTime.getText().toString());
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        getAllReporting();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm.isActive(etdemo)) {
//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//        initDatePicker();
//        getPageReporting(1, 0, selecttime);
//        bosscheck_easylayout.setLoadMoreModel(LoadModel.NONE);
//        bosscheck_easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
//
//        bosscheck_easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
//            /**
//             * 下拉刷新
//             */
//            @Override
//            public void onRefreshing() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        selecttime = null;
//                        getPageReporting(1, 0, selecttime);
//                        bosscheck_easylayout.refreshComplete();
////                        Toast.makeText(getApplicationContext(), "refresh success", Toast.LENGTH_SHORT).show();
//                    }
//                }, 1000);
//            }
//
//            /**
//             * 上拉加载
//             */
//            @Override
//            public void onLoadMore() {
//                int i = Integer.parseInt(r.getTotal());
//                pages = (i % 10 != 0 ? i / 10 + 1 : i / 10);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (count > pages) {
//                            bosscheck_easylayout.loadMoreComplete(new EasyRefreshLayout.Event() {
//                                @Override
//                                public void complete() {
//
//                                }
//                            });
//                            bosscheck_easylayout.loadNothing();
//                        } else {
//                            if (selecttime != null) {
//                                getPageReporting(count, 1, selecttime);
//                            } else {
//                                getPageReporting(count, 1, selecttime);
//                            }
//                            bosscheck_easylayout.loadMoreComplete(new EasyRefreshLayout.Event() {
//                                @Override
//                                public void complete() {
//                                    reportingAdapter.notifyDataSetChanged();
//                                    count++;
//                                }
//                            }, 500);
//                        }
//                    }
//                }, 2000);
//            }
//        });
//
//    }
//
//    private void init() {
//        acache = ACache.get(this);
//        ToolBarOptions options = new ToolBarOptions();
//        setToolBar(R.id.menu_toolbar, options);
//        setTitle(R.string.nullname);
//        TextView textView = (TextView) findViewById(R.id.menu_textview);
//        textView.setText(R.string.menu_reporting_title);
//        bosscheck_recyclerview.setLayoutManager(new LinearLayoutManager(ReportingBossActivity.this));
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                progressHUD = ZProgressHUD.getInstance(ReportingBossActivity.this);
//                progressHUD.setMessage("加载中");
//                progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
//                progressHUD.show();
//                Getresponse();
//                getPageReporting(1, 0, selecttime);
//            }
//        });
//    }
//
//    /**
//     * 加载页面数据
//     *
//     * @param page   页码
//     * @param status 状态（上拉1或者下拉0）
//     * @param time   时间
//     */
//    private void getPageReporting(int page, final int status, String time) {
//        u = (UserDTO) acache.getAsObject("user");
//        String url = PublicUtils.URL +
//                PublicUtils.URL_FILE_TAG +
//                PublicUtils.URL_FILE_REPORTING_TAG +
//                PublicUtils.URL_FILE_REPORTING_RESPONSE_TAG;
//        Map<String, String> m = new HashMap<>();
//        if (u != null) {
//            m.put("reporting.checkerAnd", "1");
//            m.put("reporting.checker", u.getDepartmentId());
//            m.put("reporting.reportinf", u.getId());
//            m.put("page", page + "");
//            if (time != null) {
//                m.put("reporting.reportTime", time);
//            }
//        } else {
//            m = null;
//        }
//        HttpUtils.doPost(url, m, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (PublicUtils.isNetworkAvailable(ReportingBossActivity.this)) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(ReportingBossActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
//                            return;
//
//                        }
//                    });
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(ReportingBossActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
//                            progressHUD.dismiss();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                Log.i("test", "onResponse: " + str);
//                GSONReporting(str, status);
//            }
//        });
//    }
//
//    /**
//     * @param json   数据
//     * @param status 状态（上拉1或者下拉0）
//     */
//    private void GSONReporting(String json, final int status) {
////        Gson gson = new Gson();
////        r = gson.fromJson(json, ReportingDto.class);
//        r = Utility.reportingBossResponse(json);
//        if (r.getTotal().equals("0")) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(ReportingBossActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
//                    progressHUD.dismiss();
//                }
//            });
//        } else {
//            list = r.getRows();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (pages == 0 && status == 0) {
//                        reportingAdapter = new ReportingAdapter(R.layout.activity_reporting_item, list);
//                        bosscheck_recyclerview.setAdapter(reportingAdapter);
////                        Toast.makeText(ReportingBossActivity.this, list.size()+"", Toast.LENGTH_SHORT).show();
//                    } else if (pages >= 2 && status == 1) {
//                        reportingAdapter.addData(list);
////                        Toast.makeText(ReportingBossActivity.this, list.size()+"", Toast.LENGTH_SHORT).show();
//                    } else if (pages >= 2 && status == 0) {
//                        reportingAdapter = new ReportingAdapter(R.layout.activity_reporting_item, list);
//                        bosscheck_recyclerview.setAdapter(reportingAdapter);
////                        Toast.makeText(ReportingBossActivity.this, list.size()+"", Toast.LENGTH_SHORT).show();
//                        count = 2;
//                    }
//                    reportingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                            TextView textView = (TextView) view.findViewById(R.id.reporing_item_title);
//                            Intent intent = new Intent();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("messageId", textView.getTag() + "");
//                            intent.putExtras(bundle);
////                            intent.putExtra("i", position);
//                            intent.setClass(ReportingBossActivity.this, ReportingBossCheckActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    progressHUD.dismiss();
//                }
//            });
//        }
//
//    }
//
//
//    /**
//     * 获取工作汇报统计
//     */
//    private void Getresponse() {
//        String url = PublicUtils.URL +
//                PublicUtils.URL_FILE_TAG +
//                PublicUtils.URL_FILE_AUTOSTATIS_TAG +
//                PublicUtils.URL_FILE_AUTOSTATIS_RESPONSE_TAG;
////        String url = "http://192.168.1.110:8080/manual_pure/autoStatistics/autoStatisticsAction!statisticReport";
////        Log.i(TAG, "url: " + url);
//        HttpUtils.doGet(url, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (PublicUtils.isNetworkAvailable(ReportingBossActivity.this)) {
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(ReportingBossActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
//                            progressHUD.dismiss();
//                            return;
//                        }
//                    });
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(ReportingBossActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
//                            progressHUD.dismiss();
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String str = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        Toast.makeText(ReportingBossActivity.this, str, Toast.LENGTH_SHORT).show();
//                        menutvrighttext.setText(str);
//                    }
//                });
//            }
//        });
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        progressHUD.dismiss();
//    }
//}


















//    /**
//     * 加载页面数据
//     */
//    private void getAllReporting() {
//        u = (UserDTO) acache.getAsObject("user");
//        String url = PublicUtils.URL +
//                PublicUtils.URL_FILE_TAG +
//                PublicUtils.URL_FILE_REPORTING_TAG +
//                PublicUtils.URL_FILE_REPORTING_RESPONSE_TAG;
//        Map<String, String> m = new HashMap<>();
//        m.put("reporting.checkerAnd", "1");
//        m.put("reporting.checker", u.getDepartmentId());
//        m.put("reporting.reportinf", u.getId());
//        m.put("rows", 99999999 + "");
//        HttpUtils.doPost(url, m, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (PublicUtils.isNetworkAvailable(ReportingBossActivity.this)) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(ReportingBossActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                Toast.makeText(ReportingBossActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
////                ReportingDto rr = Utility.reportingBossResponse(str);
//                acache.put("reporting", str);
////                Log.i(TAG, "onResponse: " + rr.getRows().size());
//            }
//        });
//    }

/**
 * url追加对象信息
 *
 * @return 获取工作汇报详细
 * <p>
 * 获取工作汇报详细
 * <p>
 * 获取工作汇报详细
 * <p>
 * 获取工作汇报详细
 * <p>
 * 获取工作汇报详细
 * <p>
 * 获取工作汇报详细
 * <p>
 * 获取工作汇报详细
 */
//    private Map<String, String> getReporingContent() {
//        u = (UserDTO) acache.getAsObject("user");
//        Map<String, String> m = new HashMap<>();
//        if (u != null) {
//            m.put("reporting.checkerAnd", "1");
//            m.put("reporting.checker", u.getDepartmentId());
//            m.put("reporting.reportinf", u.getId());
//            m.put("page", "1");
//        } else {
//            m = null;
//        }
//        return m;
//    }

/**
 * 获取工作汇报详细
 */
//    private void PostReporting() {
//        String url = PublicUtils.URL +
//                PublicUtils.URL_FILE_TAG +
//                PublicUtils.URL_FILE_REPORTING_TAG +
//                PublicUtils.URL_FILE_REPORTING_RESPONSE_TAG;
////        Log.i(TAG, "url: " + url+getReporingContent());
//        HttpUtils.doPost(url, getReporingContent(), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (PublicUtils.isNetworkAvailable(ReportingBossActivity.this)) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(ReportingBossActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
//                            progressHUD.dismiss();
//                        }
//                    });
//                }
//                Toast.makeText(ReportingBossActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                Log.i(TAG, "str:" + str);
//                acache.put("reporting", str);
//                GSONReporting(str);
//            }
//        });
//    }