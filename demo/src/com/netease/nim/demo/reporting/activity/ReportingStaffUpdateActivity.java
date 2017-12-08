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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.demo.R;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.bean.Reporting;
import com.netease.nim.uikit.common.bean.ReportingDto;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
 * 工作汇报详情修改页面
 */

/**
 * Created by 78560 on 2017/8/17.
 */

public class ReportingStaffUpdateActivity extends UI {
    private static String TAG = "wk_staffupdate";
    private ACache acache;
    private UserDTO u;
    private String str;
    private int i;
    List<Reporting> list;
    @Bind(R.id.ar_update_autotitle)EditText ar_update_autotitle;
    @Bind(R.id.ar_update_context)EditText ar_update_context;
    @Bind(R.id.ar_update_submit)Button ar_update_submit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reporting_update);
        ButterKnife.bind(ReportingStaffUpdateActivity.this);
        init();
    }
    //获取当前时间
    String getTime(){
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
        Date curDate =  new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }
    private void init() {
        acache = ACache.get(this);
        Intent intent = getIntent();
        i = intent.getIntExtra("i",0);
        str=acache.getAsString("reporting");
//        Log.i(TAG, "init: "+str);
        Gson gson=new Gson();
        ReportingDto reportingDto=gson.fromJson(str, ReportingDto.class);
        list=reportingDto.getRows();

        u=(UserDTO)acache.getAsObject("user");
//        Log.i(TAG, "init: "+u.toString());
//        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, R.string.nullname,R.drawable.null_login);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText("修改汇报");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ar_update_autotitle.setText(list.get(i).getTitle());
                ar_update_context.setText(list.get(i).getContext());
            }
        });
    }

    /**
     * 修改提交
     *
     */
    private void UpdateSubmitReporting(){
        String context=ar_update_context.getText().toString();
        String title=ar_update_autotitle.getText().toString();
        String url = PublicUtils.URL +PublicUtils.URL_FILE_TAG +PublicUtils.URL_FILE_REPORTING_TAG +PublicUtils.URL_FILE_UPDATESUBMIT_RESPONSE_TAG;
//        Log.i(TAG, "UpdateSubmitReporting: "+url);
        Map<String,String> m = new HashMap<String,String>();
        m.put("reporting.title",title);
        m.put("reporting.context",context);
        m.put("reporting.id",list.get(i).getId());
        if(list.get(i).getInformre() == null){
            m.put("reporting.informre"," ");
        }else{
            m.put("reporting.informre",list.get(i).getInformre());
        }

        m.put("reporting.reporter",list.get(i).getReporter());
        m.put("reporting.department",list.get(i).getDepartment());
        m.put("reporting.reportTime",list.get(i).getReportTime());
        m.put("reporting.reportType",list.get(i).getReportType());
        m.put("nowUser",u.getId());
//        Log.i(TAG, "UpdateSubmitReporting: "+m);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(ReportingStaffUpdateActivity.this)) {
                    Toast.makeText(ReportingStaffUpdateActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ReportingStaffUpdateActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ReportingStaffUpdateActivity.this, "提交成功！！！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 修改
     */
    private void UpdateReporting(){
        String context=ar_update_context.getText().toString().trim();
        String title=ar_update_autotitle.getText().toString().trim();
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REPORTING_TAG +
                PublicUtils.URL_FILE_UPDATE_RESPONSE_TAG;
        Map<String,String> m = new HashMap<>();
        m.put("reporting.id",list.get(i).getId());
        if(list.get(i).getInformre() == null){
            m.put("reporting.informre"," ");
        }else{
            m.put("reporting.informre",list.get(i).getInformre());
        }
        m.put("reporting.title",title);
        m.put("reporting.reporter",list.get(i).getReporter());
        m.put("reporting.department",list.get(i).getDepartment());
        m.put("reporting.reportTime",list.get(i).getReportTime());
        m.put("reporting.reportType",list.get(i).getReportType());
        m.put("reporting.context",context);
        m.put("nowUser",u.getId());
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(ReportingStaffUpdateActivity.this)) {
                    Toast.makeText(ReportingStaffUpdateActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReportingStaffUpdateActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string=response.toString().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ReportingStaffUpdateActivity.this, "保存到草稿成功！！！", Toast.LENGTH_SHORT).show();
                    }
                });
//                Log.i(TAG, "onResponse: "+string);
            }
        });
    }

    @OnClick(R.id.ar_update_submit)
    public void ar_update_submit(View v){
        showNormalDialog();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            saveNormalDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    //提交日报
    private void showNormalDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否提交?"); //设置内容
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UpdateSubmitReporting();
                dialog.dismiss(); //关闭dialog
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }
    //保存日报
    private void saveNormalDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否保存到草稿?"); //设置内容
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UpdateReporting();
                dialog.dismiss(); //关闭dialog
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }
}
