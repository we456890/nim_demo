package com.netease.nim.demo.reporting.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import com.netease.nim.uikit.common.bean.ReportingDto;
import com.netease.nim.uikit.common.bean.Reversion;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.model.ToolBarOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * 员工工作汇报查看详情页面
 */

/**
 * Created by 78560 on 2017/8/17.
 */

public class ReportingStaffCheckActivity extends UI {
    private static String TAG = "wk_staffcheck";
    private ACache acache;
    @Bind(R.id.ar_check_title)
    TextView ar_check_title;
    @Bind(R.id.ar_check_autotitle)
    TextView ar_check_autotitle;
    @Bind(R.id.ar_check_context)
    TextView ar_check_context;

    @Bind(R.id.lvstaff_reporing_command)
    ListView lvstaff_reporing_command;
    Reporting r;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reporting_check);
        ButterKnife.bind(ReportingStaffCheckActivity.this);
        init();
    }

    //获取当前时间
    String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    private void init() {
        acache = ACache.get(this);
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText("查看汇报");
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
                if (PublicUtils.isNetworkAvailable(ReportingStaffCheckActivity.this)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReportingStaffCheckActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReportingStaffCheckActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Log.i(TAG, "onResponse: "+str);
                Gson gson = new Gson();
                r = gson.fromJson(str, Reporting.class);
                if(r!=null) {
                    setText(r);
                    getComment(r);
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(ReportingStaffCheckActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private void setText(final Reporting r) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ar_check_title.setText(r.getAutoTitle());
                ar_check_autotitle.setText(r.getTitle());
                ar_check_context.setText(r.getContext());
//                getComment(r);
            }
        });
    }

    //得到批示
    private void getComment(Reporting r) {
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REVERSION_TAG +
                PublicUtils.URL_FILE_REVERSIONCOMMAND_RESPONSE_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("reversion.convertId",r.getId());
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(ReportingStaffCheckActivity.this)) {
                    Toast.makeText(ReportingStaffCheckActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReportingStaffCheckActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
//            Log.i(TAG, "strstr: "+str);
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
                            CommandAdapter adapter=new CommandAdapter(ReportingStaffCheckActivity.this, listre);
                            lvstaff_reporing_command.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
