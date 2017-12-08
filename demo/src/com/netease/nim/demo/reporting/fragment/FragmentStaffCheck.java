package com.netease.nim.demo.reporting.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.netease.nim.demo.R;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.demo.reporting.activity.ReportingStaffCheckActivity;
import com.netease.nim.demo.reporting.adapter.ReportingAdapter;
import com.netease.nim.demo.reporting.widgets.CustomDatePicker;
import com.netease.nim.demo.reporting.widgets.ZProgressHUD;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.bean.Reporting;
import com.netease.nim.uikit.common.bean.ReportingDto;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.fragment.TFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 员工查看汇报
 * <p>
 * Created by 78560 on 2017/8/25.
 */

public class FragmentStaffCheck extends TFragment {
    private static String TAG = "wk_staffcheckr";
    ZProgressHUD progressHUD;
    private ACache acache;

    private String selecttime;

    @Bind(R.id.ar_selecttime)
    RelativeLayout ar_selecttime;

    @Bind(R.id.menu_tv_searchTime)
    TextView tvsearchTime;

    @Bind(R.id.staffcheck_recyclerview)
    RecyclerView staffcheck_recyclerview;

    @Bind(R.id.staffcheck_easylayout)
    EasyRefreshLayout staffcheck_easylayout;

    List<Reporting> list;

    ReportingDto r;

    ReportingAdapter reportingAdapter;
    int count = 2;
    private int pages;
    private CustomDatePicker customDatePicker;
    UserDTO u;

    @Bind(R.id.etdemo)
    EditText etdemo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.staffcheck, container, false);
        ButterKnife.bind(this, view);
        Log.i(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");
        initDatePicker();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        acache = ACache.get(getActivity());
//        ToolBarOptions options = new ToolBarOptions();
//        setToolBar(R.id.menu_toolbar, R.,null);
        setTitle(R.string.nullname);
//        TextView textView = findView(R.id.menu_textview);
//        textView.setText(R.string.menu_reporting_title);
        staffcheck_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressHUD = ZProgressHUD.getInstance(getActivity());
                progressHUD.setMessage("加载中");
                progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                progressHUD.show();
                getPageReporting(1, 0, selecttime);
            }
        });
    }

    /**
     * 时间选择器
     */
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tvsearchTime.setText(now.split(" ")[0]);
        tvsearchTime.setText(now);

        customDatePicker = new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(final String time) { // 回调接口，获得选中的时间
                tvsearchTime.setText(time.split(" ")[0]);
                selecttime = time.split(" ")[0];
                getPageReporting(1, 0, selecttime);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    @OnClick(R.id.ar_selecttime)
    public void ar_selecttime(View view) {
        customDatePicker.show(tvsearchTime.getText().toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive(etdemo)) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        staffcheck_easylayout.setLoadMoreModel(LoadModel.NONE);
        staffcheck_easylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
        staffcheck_easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            /**
             * 下拉刷新
             */
            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        selecttime = null;
                        getPageReporting(1, 0, selecttime);
                        staffcheck_easylayout.refreshComplete();
                    }
                }, 1000);
            }
            /**
             * 上拉加载
             */
            @Override
            public void onLoadMore() {
                int i = Integer.parseInt(r.getTotal());
                pages = (i % 10 != 0 ? i / 10 + 1 : i / 10);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (count > pages) {
                            staffcheck_easylayout.loadMoreComplete(new EasyRefreshLayout.Event() {
                                @Override
                                public void complete() {

                                }
                            }, 500);
//                            Toast.makeText(getActivity(), selecttime, Toast.LENGTH_SHORT).show();
                            staffcheck_easylayout.loadNothing();
                        } else {
                            if (selecttime != null) {
                                getPageReporting(count, 1, selecttime);
                            } else {
                                getPageReporting(count, 1, selecttime);
                            }
                            staffcheck_easylayout.loadMoreComplete(new EasyRefreshLayout.Event() {
                                @Override
                                public void complete() {
                                    reportingAdapter.notifyDataSetChanged();
                                    count++;
                                }
                            }, 500);
                        }
                    }
                }, 2000);
            }
        });
    }

    /**
     * 加载页面数据
     *
     * @param page      页码
     * @param status    状态（上拉1或者下拉0）
     * @param time      时间
     *
     */
    private void getPageReporting(int page, final int status, String time) {
        u = (UserDTO) acache.getAsObject("user");
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REPORTING_TAG +
                PublicUtils.URL_FILE_REPORTING_RESPONSE_TAG;
        Map<String, String> m = new HashMap<>();
        if (u != null) {
            m.put("reporting.checkerAnd", "1");
            m.put("reporting.checker", u.getDepartmentId());
            m.put("reporting.reportinf", u.getId());
            m.put("reporting.owner", u.getId());
            m.put("page", page + "");
            if (time != null) {
                m.put("reporting.reportTime", time);
            }
        } else {
            m = null;
        }
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(getActivity())) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "请求失败...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i("test", "onResponse: " + str);
//                acache.put("reporting", str);/
                GSONReporting(str, status);
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        progressHUD.dismiss();//关闭loading
                    }
                });
            }
        });
    }


    /**
     * @param json   数据
     * @param status 状态（上拉1或者下拉0）
     */
    private void GSONReporting(String json, final int status) {
//        Log.i(TAG, "GSONReporting: " + json);
        Gson gson = new Gson();
        r = gson.fromJson(json, ReportingDto.class);
//        Log.i(TAG, "GSONReporting: " + r.toString());
        if (r.getTotal().equals("0")) {
            postRunnable(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            list = r.getRows();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (pages == 0 && status == 0) {
                        reportingAdapter = new ReportingAdapter(R.layout.activity_reporting_item, list);
                        staffcheck_recyclerview.setAdapter(reportingAdapter);
                    } else if (pages >= 2 && status == 1) {
                        reportingAdapter.addData(list);
                    } else if (pages >= 2 && status == 0) {
                        reportingAdapter = new ReportingAdapter(R.layout.activity_reporting_item, list);
                        staffcheck_recyclerview.setAdapter(reportingAdapter);
                        count = 2;
                    }
                    reportingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            TextView textView = (TextView) view.findViewById(R.id.reporing_item_title);
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("messageId", textView.getTag() + "");
                            intent.putExtras(bundle);
//                            intent.putExtra("i", position);
//                            String step = list.get(position).getSteps().toString();
//                            Log.i(TAG, "onItemClick: " + step);
                            intent.setClass(getActivity(), ReportingStaffCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        progressHUD.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}


    /**
     * 根据时间查询数据
     */
//    private void getTiemReporting(String time) {
//        u = (UserDTO) acache.getAsObject("user");
//        String url = PublicUtils.URL +
//                PublicUtils.URL_FILE_TAG +
//                PublicUtils.URL_FILE_REPORTING_TAG +
//                PublicUtils.URL_FILE_REPORTING_RESPONSE_TAG;
//        Map<String, String> m = new HashMap<>();
//        m.put("reporting.checkerAnd", "1");
//        m.put("reporting.checker", u.getDepartmentId());
//        m.put("reporting.reportinf", u.getId());
//        m.put("reporting.owner", u.getId());
//        m.put("reportTime", time);
//        HttpUtils.doPost(url, m, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (PublicUtils.isNetworkAvailable(getActivity())) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getActivity(), "请求失败...", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String str = response.body().string();
//                Log.i(TAG, "onResponse: " + str);
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
//                    }
//                });
////                ReportingDto rr = Utility.reportingBossResponse(str);
////                acache.put("reporting", str);
////                Log.i(TAG, "onResponse: " + rr.getRows().size());
//            }
//        });
//    }
//    AdapterView.OnItemClickListener oic = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            Intent intent = new Intent();
//            intent.putExtra("i", i);
////            String step = list.get(i).getSteps().toString();
////            Log.i(TAG, "onItemClick: " + step);
//            intent.setClass(getActivity(), ReportingStaffCheckActivity.class);
//            startActivity(intent);
//
//        }
//    };

//    /**
//     * 获取信息
//     *
//     * @return
//     */
//    private Map<String, String> getReporingContent() {
//        u = (UserDTO) acache.getAsObject("user");
//        Map<String, String> m = new HashMap<>();
//        if (u != null) {
//            m.put("reporting.checkerAnd", "1");
//            m.put("reporting.checker", u.getDepartmentId());
//            m.put("reporting.reportinf", u.getId());
//            m.put("reporting.owner", u.getId());
//            m.put("page", "1");
//        } else {
//            m = null;
//        }
//        return m;
//    }

//    /**
//     * 获取工作汇报详细
//     */
//    private void PostReporting() {
//        String url = PublicUtils.URL +
//                PublicUtils.URL_FILE_TAG +
//                PublicUtils.URL_FILE_REPORTING_TAG +
//                PublicUtils.URL_FILE_REPORTING_RESPONSE_TAG;
////        Log.i(TAG, "url: " + url+getReporingContent());
//        HttpUtils.doPost(url, getReporingContent(), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (PublicUtils.isNetworkAvailable(getActivity())) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
//                            progressHUD.dismiss();//关闭loading
//                        }
//                    });
//                }
//                Toast.makeText(getActivity(), "请求失败...", Toast.LENGTH_SHORT).show();
//                progressHUD.dismiss();//关闭loading
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                Log.i(TAG, "str:" + str);
//                acache.put("reporting", str);
//                GSONReporting(str);
//                postRunnable(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressHUD.dismiss();//关闭loading
//                    }
//                });
//
//            }
//        });
//    }