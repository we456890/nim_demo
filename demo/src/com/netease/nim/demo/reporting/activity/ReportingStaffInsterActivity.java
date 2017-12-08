package com.netease.nim.demo.reporting.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.demo.R;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.bean.Draft;
import com.netease.nim.uikit.common.bean.DraftDto;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.model.ToolBarOptions;

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
 * 工作汇报新增页面
 */

/**
 * Created by 78560 on 2017/8/17.
 */

public class ReportingStaffInsterActivity extends UI {
    private static String TAG = "wk_staffinsert";
    private ACache acache;
    UserDTO u;
    String type;

    @Bind(R.id.ar_inster_autotitle)
    TextView ar_inster_autotitle;
    @Bind(R.id.ar_inster_context)
    EditText ar_inster_context;
    @Bind(R.id.ar_inster_submit)
    Button ar_inster_submit;
    @Bind(R.id.ar_inster_time)
    TextView ar_inster_time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reporting_inster);
        ButterKnife.bind(ReportingStaffInsterActivity.this);
        init();
    }
    private void init() {
        acache = ACache.get(this);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        u = (UserDTO) acache.getAsObject("user");
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText("新增汇报");
        ar_inster_time.setText(StringUtil.getNewTime());

        //--------------------------------------

        DraftDto dd = null;
        if (type.equals("日报")) {
            dd = (DraftDto) acache.getAsObject("daily");
            if (dd != null) {
                if (dd.getId().equals(u.getId())) {
                    ar_inster_autotitle.setText(dd.getList().get(0).getTitle());
                    ar_inster_context.setText(dd.getList().get(0).getContext());
                } else {
                    return;
                }
            }else{
                return;
            }
        } else if (type.equals("月报")) {
            dd = (DraftDto) acache.getAsObject("monthly");
            if (dd != null) {
                if (dd.getId().equals(u.getId())) {
                    ar_inster_autotitle.setText(dd.getList().get(0).getTitle());
                    ar_inster_context.setText(dd.getList().get(0).getContext());
                } else {
                    return;
                }
            }else{
                return;
            }
        }

        //-------------------------------------
    }

    private void insertReporting() {
        String context = ar_inster_context.getText().toString().trim();
        String title = ar_inster_autotitle.getText().toString().trim();
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REPORTING_TAG +
                PublicUtils.URL_FILE_ADDSUBMIT_RESPONSE_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("reporting.title", title);
        m.put("reporting.reporter", u.getRealName());
        m.put("reporting.department", u.getDepartmentId());
        m.put("reporting.reportTime", ar_inster_time.getText().toString());
        m.put("reporting.reportType", type);
        m.put("reporting.context", context);
        m.put("nowUser", u.getId());
        m.put("reporting.owner", u.getId());
        m.put("reporting.ownerName", u.getRealName());
        m.put("reporting.created", u.getId());
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(ReportingStaffInsterActivity.this)) {
                    Toast.makeText(ReportingStaffInsterActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReportingStaffInsterActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                if (type.equals("日报")) {
                    acache.remove("daily");
                } else if (type.equals("月报")) {
                    acache.remove("monthly");
                }
                Log.i(TAG, "onResponse: " + str);
            }
        });
    }

    //缓存日报月报
    private void saveReporting() {
        String context = ar_inster_context.getText().toString().trim();
        String title = ar_inster_autotitle.getText().toString().trim();
        String id = u.getId();
        Draft draft = new Draft(title, context);
        List<Draft> list = new ArrayList<>();
        list.add(draft);
        final DraftDto dd = new DraftDto(id, list);
        if (type.equals("日报")) {
            //如果类型是日报缓存到日报里
            acache.put("daily", dd);
        } else if (type.equals("月报")) {
            //反之
            acache.put("monthly", dd);
        }
        Log.i(TAG, "saveReporting: " + dd);
    }

    @OnClick(R.id.ar_inster_submit)
    public void ar_inster_submit(View v) {
        showNormalDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveReporting();
    }


    //提交日报
    private void showNormalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        LayoutInflater inflater = LayoutInflater.from(this);
        View convertView = inflater.inflate(com.loveplusplus.update.R.layout.dialogtext,null);
        TextView title = (TextView) convertView.findViewById(com.loveplusplus.update.R.id.title);
        TextView message = (TextView) convertView.findViewById(com.loveplusplus.update.R.id.message);
        title.setText("提示");
        message.setText("是否提交?");
        builder.setView(convertView);
//        builder.setTitle("提示"); //设置标题
//        builder.setMessage("是否提交?"); //设置内容
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertReporting();
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

}
