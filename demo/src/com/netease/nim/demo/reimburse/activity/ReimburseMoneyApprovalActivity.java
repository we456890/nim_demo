package com.netease.nim.demo.reimburse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.demo.R;
import com.netease.nim.demo.borrow.adapter.BorrowCheckBMAdapter;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.bean.FrameRecord;
import com.netease.nim.uikit.common.bean.OperateInfo;
import com.netease.nim.uikit.common.bean.Reimburse;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.io.IOException;
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
 * 报销单详情
 * Created by 78560 on 2017/10/10.
 */

public class ReimburseMoneyApprovalActivity extends UI{
    private static String TAG = "wk_ReimbureseMoneyCheck";
    @Bind(R.id.reimbursa_account)
    TextView reimbursa_account;
    @Bind(R.id.reimbursa_money)
    TextView reimbursa_money;
    @Bind(R.id.reimbursa_name)
    TextView reimbursa_name;
    @Bind(R.id.reimbursa_num)
    TextView reimbursa_num;
    @Bind(R.id.ra_postil)
    EditText ra_postil;

    @Bind(R.id.ra_consent)
    Button ra_consent;
    @Bind(R.id.ra_reject)
    Button ra_reject;

    @Bind(R.id.bill_content)
    RelativeLayout bill_content;
    @Bind(R.id.rl_approval)
    RelativeLayout rl_approval;
    @Bind(R.id.rl_postil)
    RelativeLayout rl_postil;
    @Bind(R.id.ll_end)
    LinearLayout ll_end;
    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

    private ACache acache;
    private UserDTO userDTO;
    private Reimburse rl;
    private List<FrameRecord> list;
    private BorrowCheckBMAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reimburese_approval);
        ButterKnife.bind(ReimburseMoneyApprovalActivity.this);
        initView();
    }

    private void initView() {
        acache = ACache.get(ReimburseMoneyApprovalActivity.this);
        recycler_view.setLayoutManager(new LinearLayoutManager(ReimburseMoneyApprovalActivity.this));
        userDTO = (UserDTO) acache.getAsObject("user");
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.reimburse_expenses);
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null) {
            String reimburseId = bundle.getString("reimburseId");
            Log.i(TAG, "initView: " + reimburseId);
            getIdByMessage(reimburseId);
            reimbursa_account.setTag(reimburseId);
        }
    }

    private void getIdByMessage(String reimburseId) {
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REIMBURSE_TAG +
                PublicUtils.URL_FILE_REIMBURSEGETBYIDACTION_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("id", reimburseId);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (PublicUtils.isNetworkAvailable(ReimburseMoneyApprovalActivity.this)) {
                            Toast.makeText(ReimburseMoneyApprovalActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReimburseMoneyApprovalActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Log.i(TAG, "onResponse: "+str);
                Gson gson = new Gson();
                rl = gson.fromJson(str, Reimburse.class);
                list=rl.getRecordList();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter=new BorrowCheckBMAdapter(R.layout.reimburse_approval_item,list);
                        recycler_view.setAdapter(adapter);
                        double billmoney = Double.parseDouble(rl.getRemiurseHole());
                        reimbursa_account.setText(rl.getRemiurserJob());
                        reimbursa_money.setText("￥"+billmoney);
                        reimbursa_name.setText(rl.getReimurser().toString());
                        reimbursa_num.setText(rl.getDocs().toString()+"张");
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 查看发票列表
     * @param v
     */
    @OnClick(R.id.bill_content)
    public void bill_content(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("billId",reimbursa_account.getTag().toString());
        intent.putExtras(bundle);
        intent.setClass(ReimburseMoneyApprovalActivity.this, ReimburseBillAllCheckActivity.class);
        startActivity(intent);
    }

    /**
     * 显示批注
     * @param v
     */
    @OnClick(R.id.rl_approval)
    public void rl_approval(View v) {
        rl_postil.setVisibility(View.VISIBLE);
        rl_approval.setVisibility(View.GONE);
    }

    /**
     *
     * 收起批注
     * @param v
     */
    @OnClick(R.id.ll_end)
    public void ll_end(View v) {
        if(rl_postil.getVisibility()==View.VISIBLE){
            rl_postil.setVisibility(View.GONE);
            rl_approval.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 同意
     * @param v
     */
    @OnClick(R.id.ra_consent)
    public void ra_consent(View v){
        String reimburseId=reimbursa_account.getTag().toString();
        String context=ra_postil.getText().toString().trim();
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REIMBURSE_TAG +
                PublicUtils.URL_FILE_REIMBURSEGONEXTACTION_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("reimburse.id", reimburseId);
        m.put("reimburse.step",rl.getStep());
        m.put("frameRecord.concatFrame",BORROW_FRAME);
        m.put("frameRecord.concatData",rl.getId());
        m.put("frameRecord.processor",userDTO.getRealName());
        m.put("frameRecord.processorId",userDTO.getId());
        if(context.equals("")){
            m.put("frameRecord.context","同意");
        }else{
            m.put("frameRecord.context",context);
        }
        m.put("frameRecord.chosen","同意");
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (PublicUtils.isNetworkAvailable(ReimburseMoneyApprovalActivity.this)) {
                            Toast.makeText(ReimburseMoneyApprovalActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReimburseMoneyApprovalActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Gson gson = new Gson();
                final OperateInfo oi = gson.fromJson(str, OperateInfo.class);
                if (oi.isOperateSuccess()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReimburseMoneyApprovalActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReimburseMoneyApprovalActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    /**
     * 拒绝
     * @param v
     */
    @OnClick(R.id.ra_reject)
    public void ra_reject(View v){
        String reimburseId=reimbursa_account.getTag().toString();
        String context=ra_postil.getText().toString().trim();
        if(!context.equals("")){
            String url=PublicUtils.URL +
                    PublicUtils.URL_FILE_TAG +
                    PublicUtils.URL_FILE_REIMBURSE_TAG +
                    PublicUtils.URL_FILE_REIMBURSEGOREGETACTION_TAG;
            Map<String, String> m = new HashMap<>();
            m.put("reimburse.id", reimburseId);
            m.put("reimburse.step",rl.getStep());
            m.put("frameRecord.concatFrame",BORROW_FRAME);
            m.put("frameRecord.concatData",rl.getId());
            m.put("frameRecord.processor",userDTO.getRealName());
            m.put("frameRecord.processorId",userDTO.getId());
            m.put("frameRecord.chosen","拒绝");
            m.put("frameRecord.context",context);
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (PublicUtils.isNetworkAvailable(ReimburseMoneyApprovalActivity.this)) {
                                Toast.makeText(ReimburseMoneyApprovalActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ReimburseMoneyApprovalActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str=response.body().string();
                    Gson gson = new Gson();
                    final OperateInfo oi = gson.fromJson(str, OperateInfo.class);
                    if (oi.isOperateSuccess()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReimburseMoneyApprovalActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReimburseMoneyApprovalActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ReimburseMoneyApprovalActivity.this, "拒绝理由不能为空", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
//    @Override
//    public void onClick(View view) {
//        String url = null;
//        String reimburseId=reimbursa_account.getTag().toString();
//        String context=ra_postil.getText().toString().trim();
//        Map<String, String> m = new HashMap<>();
//        String tag="0";
//        switch (view.getId()){
//            //拒绝
//            case R.id.ra_reject:
//                url=PublicUtils.URL +
//                        PublicUtils.URL_FILE_TAG +
//                        PublicUtils.URL_FILE_REIMBURSE_TAG +
//                        PublicUtils.URL_FILE_REIMBURSEGOREGETACTION_TAG;
//                m.put("reimburse.id", reimburseId);
//                m.put("reimburse.step",rl.getStep());
//                m.put("frameRecord.concatFrame",PublicUtils.BORROW_FRAME);
//                m.put("frameRecord.concatData",rl.getId());
//                m.put("frameRecord.processor",userDTO.getRealName());
//                m.put("frameRecord.processorId",userDTO.getId());
//                m.put("frameRecord.chosen","同意");
//                if(context.equals("")){
//                    tag="1";
//                }else{
//                    m.put("frameRecord.context",context);
//                }
//                break;
//        }
//        if(tag.equals("0")){
//            HttpUtils.doPost(url, m, new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (PublicUtils.isNetworkAvailable(ReimburseMoneyApprovalActivity.this)) {
//                                Toast.makeText(ReimburseMoneyApprovalActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(ReimburseMoneyApprovalActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String str=response.body().string();
//                    Gson gson = new Gson();
//                    final OperateInfo oi = gson.fromJson(str, OperateInfo.class);
//                    if (oi.isOperateSuccess()) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(ReimburseMoneyApprovalActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        });
//                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(ReimburseMoneyApprovalActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//            });
//        }else{
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(ReimburseMoneyApprovalActivity.this, "拒绝理由不能为空", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
//    }
    /**
     * 非空判断
     */
    private boolean isEmpty(String name,String money){
        boolean empty;
        if(StringUtil.isEmpty(name)||StringUtil.isEmpty(money)){
            empty=false;
        }else {
            empty=true;
        }
        return empty;
    }

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
