package com.netease.nim.demo.reporting.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.demo.R;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.demo.reporting.adapter.CommandAdapter;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.bean.Reporting;
import com.netease.nim.uikit.common.bean.Reversion;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.model.ToolBarOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/**
 * 工作汇报详情页面
 */

/**
 * Created by 78560 on 2017/8/17.
 */

public class ReportingBossCheckActivity extends UI {
    private static String TAG = "wk_ReportingDetail";
    private ACache acache;
    Reporting r;
//    List<Reporting> list;
    int i;

    @Bind(R.id.ar_detail_title)
    TextView ar_detail_title;
    @Bind(R.id.ar_detail_autotitle)
    TextView ar_detail_autotitle;
    @Bind(R.id.ar_detail_context)
    TextView ar_detail_context;
    @Bind(R.id.ar_detail_reporter)
    TextView ar_detail_reporter;
    @Bind(R.id.ar_detail_reporttime)
    TextView ar_detail_reporttime;
    @Bind(R.id.ar_detail_submit)
    Button ar_detail_submit;
    @Bind(R.id.ar_detail_comment)
    EditText ar_detail_comment;
    @Bind(R.id.lvboss_reporing_command)
    ListView lvboss_reporing_command;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_reporting_check);
        ButterKnife.bind(ReportingBossCheckActivity.this);
        init();
    }

    private void init() {
        acache = ACache.get(this);
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText("汇报详情");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            String messageId = bundle.getString("messageId");
            Log.i(TAG, "onCreateMessageId: " + messageId + "");
            getIdContent(messageId);
        }
    }

    //得到详情
    private void getIdContent(String id){
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REPORTING_TAG +
                PublicUtils.URL_FILE_REPORTINGGETBYID_RESPONSE_TAG;
        Map<String,String> m = new HashMap<>();
        m.put("id",id);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(ReportingBossCheckActivity.this)) {
                    Toast.makeText(ReportingBossCheckActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReportingBossCheckActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Log.i(TAG, "onResponse: "+str);
                Gson gson = new Gson();
                r = gson.fromJson(str, Reporting.class);

                if(r!=null){
                    setText(r);
                    getComment(r);
                }else{
//                    Toast.makeText(ReportingBossCheckActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //得到批示
    private void getComment(Reporting r){
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REVERSION_TAG +
                PublicUtils.URL_FILE_REVERSIONCOMMAND_RESPONSE_TAG;
        Map<String,String> m = new HashMap<>();
        m.put("reversion.convertId",r.getId());
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(ReportingBossCheckActivity.this)) {
                    Toast.makeText(ReportingBossCheckActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReportingBossCheckActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Log.i(TAG, "strstr: "+str);
                final List<Reversion> listre;
                try {
                    listre=new ArrayList<Reversion>();
                    JSONArray jsonArray = new JSONArray(str);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Reversion re=new Reversion();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        re.setContext(jsonObject.getString("context"));
                        re.setConvertId(jsonObject.getString("convertId"));
                        re.setReType(jsonObject.getString("reType"));
                        re.setRever(jsonObject.getString("rever"));
                        re.setReverAccount(jsonObject.getString("reverAccount"));
                        re.setReverName(jsonObject.getString("reverName"));
                        listre.add(re);
                    }
                    Log.i(TAG, "strstr: "+listre);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CommandAdapter adapter=new CommandAdapter(ReportingBossCheckActivity.this, listre);
                            lvboss_reporing_command.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    //得到数据并显示到页面上
    private void setText(final Reporting r) {
//            Toast.makeText(ReportingBossCheckActivity.this,"有信息",Toast.LENGTH_SHORT).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ar_detail_title.setText(r.getAutoTitle());
                    ar_detail_autotitle.setText(r.getTitle());
                    ar_detail_context.setText(r.getContext());
                    ar_detail_reporter.setText(r.getReporter());
                    ar_detail_reporttime.setText(r.getReportTime());
                }
            });
    }
    @OnClick(R.id.ar_detail_submit)
    public void ar_detail_submit(View v){
        String step=r.getSteps().toString();
        Log.i(TAG, "ar_detail_submit: "+step);
            String commet = ar_detail_comment.getText().toString().trim();
//            Log.i(TAG, "ar_detail_submit: " + commet);
            if (!TextUtils.isEmpty(commet)) {
                final String url = PublicUtils.URL +
                        PublicUtils.URL_FILE_TAG +
                        PublicUtils.URL_FILE_REVERSION_TAG +
                        PublicUtils.URL_FILE_REVERSIONADD_RESPONSE_TAG;
                r.setSteps("3");
                UserDTO u = (UserDTO) acache.getAsObject("user");
                Map<String, String> m = new HashMap<>();
                m.put("reportingId", r.getId());
                m.put("owner", r.getOwner());
                m.put("reversion.convertId", r.getId());
                m.put("reversion.rever", u.getId());
                m.put("reporting.informre", r.getInformre());
                m.put("reversion.reType", "已阅，并附意见");
                m.put("reversion.context", ar_detail_comment.getText().toString().trim());
                HttpUtils.doPost(url, m, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (PublicUtils.isNetworkAvailable(ReportingBossCheckActivity.this)) {
                            Toast.makeText(ReportingBossCheckActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReportingBossCheckActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
//                        Log.i(TAG, "onResponse: " + str);
                        try {
                            JSONObject jsonObject = new JSONObject(str);
                            final String msg = jsonObject.get("operateMessage").toString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ReportingBossCheckActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    getComment(r);
                                    ar_detail_comment.setText("");
//                                    finish();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(ReportingBossCheckActivity.this, "请输入评论意见...", Toast.LENGTH_SHORT).show();
            }
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(ReportingBossCheckActivity.this,url+"",Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
