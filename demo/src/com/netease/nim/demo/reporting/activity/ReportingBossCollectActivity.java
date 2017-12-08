package com.netease.nim.demo.reporting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.demo.R;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/**
 * 工作汇报汇总页面
 */

/**
 * Created by 78560 on 2017/8/17.
 */


public class ReportingBossCollectActivity extends UI {
    private static String TAG = "wk_ReportingCollect";
    private ACache acache;
    @Bind(R.id.ar_collect_allnum)
    TextView ar_collect_allnum;
    @Bind(R.id.ar_collect_submitnum)
    TextView ar_collect_submitnum;
    @Bind(R.id.ar_collect_submitname)
    TextView ar_collect_submitname;
    @Bind(R.id.ar_collect_nullnum)
    TextView ar_collect_nullnum;
    @Bind(R.id.ar_collect_nullname)
    TextView ar_collect_nullname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bosscollect);
        ButterKnife.bind(ReportingBossCollectActivity.this);
        init();
    }

    private void init() {
        acache = ACache.get(this);
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText("汇报人数统计");
        doGetStastic();
    }


    private void doGetStastic() {
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_AUTOSTATIS_TAG +
                PublicUtils.URL_FILE_STATISTICREPORTING_RESPONSE_TAG;
        Log.i(TAG, "doGetStastic: " + url);
//        "http://192.168.1.110:8080/manual_pure/autoStatistics/autoStatisticsAction!statisticReporter";
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(ReportingBossCollectActivity.this)) {
                    Toast.makeText(ReportingBossCollectActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ReportingBossCollectActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i(TAG, "onResponse: " + str);
                AnalysisGson(str);
            }
        });
    }
    //获取数据并填充

    private void AnalysisGson(String str) {
        try {
//            JSONArray jsonArray = new JSONArray(str);

//            for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = new JSONObject(str);
            final String allnum = jsonObject.get("total").toString();
            final String submitNum = jsonObject.get("tijiaorenshuliang").toString();
            final String sumitName = jsonObject.get("tijiaoren").toString();
            final String UnSubmitNum = jsonObject.get("weitijiaorenshuliang").toString();
            final String UnSubmitName = jsonObject.get("weitijiaoren").toString();
            Log.i(TAG, "AnalysisGson: " + allnum);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ar_collect_allnum.setText(allnum);
                    ar_collect_submitnum.setText(submitNum);
                    ar_collect_submitname.setText(sumitName);
                    ar_collect_nullnum.setText(UnSubmitNum);
                    ar_collect_nullname.setText(UnSubmitName);
                }
            });
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //    页面跳转
    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, ReportingBossCollectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
}
