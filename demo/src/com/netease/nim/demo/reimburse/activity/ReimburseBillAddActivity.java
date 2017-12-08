package com.netease.nim.demo.reimburse.activity;

import android.os.Bundle;
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
import com.netease.nim.uikit.common.bean.OperateInfo;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 借款申请
 * Created by 78560 on 2017/10/10.
 */

public class ReimburseBillAddActivity extends UI {
    private static String TAG = "wk_ReimburseBillAdd";

    @Bind(R.id.billadd_money)
    EditText billadd_money;
    @Bind(R.id.billadd_time)
    TextView billadd_time;
    @Bind(R.id.billadd_type)
    EditText billadd_type;
    @Bind(R.id.billadd_cause)
    EditText billadd_cause;
    @Bind(R.id.billadd_done)
    Button billadd_done;

    private ACache acache;
    private UserDTO userDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reimburesebill_add);
        ButterKnife.bind(ReimburseBillAddActivity.this);
        initView();
    }

    private void initView() {
        acache = ACache.get(ReimburseBillAddActivity.this);
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.reimburs_addbill);
        userDTO = (UserDTO) acache.getAsObject("user");
        if(userDTO!=null) {
            billadd_time.setText(StringUtil.getNewTime());
        }else{
            Toast.makeText(ReimburseBillAddActivity.this, "请重新登录...", Toast.LENGTH_SHORT).show();
        }
    }



    @OnClick(R.id.billadd_done)
    public void billadd_done(View v) {
        String money=billadd_money.getText().toString().trim();
        String time=billadd_time.getText().toString().trim();
        String type=billadd_type.getText().toString().trim();
        String cause=billadd_cause.getText().toString().trim();
        Bundle bundle = this.getIntent().getExtras();
        String billid = bundle.getString("billid");
        if (isEmpty(money,time,type,cause)) {
            String url = PublicUtils.URL +
                    PublicUtils.URL_FILE_TAG +
                    PublicUtils.URL_FILE_REIMBURSELIST_TAG +
                    PublicUtils.URL_FILE_REIMBURSELISTADDACTION_TAG;

            Map<String, String> m = new HashMap<>();
            m.put("reimburseList.amount", money);
            m.put("reimburseList.paydate", time);
            m.put("reimburseList.stateType", type);
            m.put("reimburseList.bz", cause);
            m.put("reimburseList.relateData", billid);
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (PublicUtils.isNetworkAvailable(ReimburseBillAddActivity.this)) {
                                Toast.makeText(ReimburseBillAddActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ReimburseBillAddActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    final OperateInfo oi = gson.fromJson(str, OperateInfo.class);
                    if (oi.isOperateSuccess()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReimburseBillAddActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReimburseBillAddActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }else{
            Toast.makeText(ReimburseBillAddActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 非空判断
     */
    private boolean isEmpty(String money,String time,String type,String cause){
        boolean empty;
        if(StringUtil.isEmpty(money)||StringUtil.isEmpty(time)||StringUtil.isEmpty(type)||StringUtil.isEmpty(cause)){
            empty=false;
        }else {
            empty=true;
        }
        return empty;
    }

//    ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, PublicUtils.BILL_TYPE);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//    private void getUser_head() {
//        String url = PublicUtils.URL +
//                PublicUtils.URL_FILE_TAG +
//                PublicUtils.URL_FILE_FRAMESET_TAG +
//                PublicUtils.URL_FILE_FRAMESETACTION_TAG;
//        Map<String, String> m = new HashMap<>();
//        m.put("reId", PublicUtils.BORROW_FRAME);
//        HttpUtils.doPost(url, m, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (PublicUtils.isNetworkAvailable(ReimburseMoneyAddActivity.this)) {
//                    Toast.makeText(ReimburseMoneyAddActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ReimburseMoneyAddActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String str = response.body().string();
//                Gson gson = new Gson();
//                frameC = gson.fromJson(str, FrameControl.class);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        borrow_name.setText(userDTO.getRealName());
//                        borrow_time.setText(StringUtil.getNewTime());
//                        user_head.loadBuddyAvatar(frameC.getDesinerAccount());
//                        borrow_approvename.setText(frameC.getDesinerName());
//                    }
//                });
//            }
//        });
//    }
}
