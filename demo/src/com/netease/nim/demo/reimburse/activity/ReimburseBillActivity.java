package com.netease.nim.demo.reimburse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.netease.nim.demo.R;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.demo.reimburse.adapter.ReimburseBillListAdapter;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.bean.OperateInfo;
import com.netease.nim.uikit.common.bean.Reimburse;
import com.netease.nim.uikit.common.bean.ReimburseList;
import com.netease.nim.uikit.common.bean.ReimburseListDto;
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
 * 发票展示
 * Created by 78560 on 2017/10/10.
 */

public class ReimburseBillActivity extends UI {
    private static String TAG = "wk_ReimbureseBill";

    @Bind(R.id.reimbursbill_name)
    TextView reimbursbill_name;
    @Bind(R.id.reimbursbill_money)
    EditText reimbursbill_money;
    @Bind(R.id.reimbursbill_time)
    TextView reimbursbill_time;
    @Bind(R.id.remiurseHole)
    TextView remiurseHole;
    @Bind(R.id.reimbursbill_add)
    RelativeLayout reimbursbill_add;
    @Bind(R.id.reimbursbill_submit)
    Button reimbursbill_submit;
    @Bind(R.id.reimbursbill_save)
    Button reimbursbill_save;
    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

    private ACache acache;
    private UserDTO userDTO;
    private Reimburse rl;
    private ReimburseListDto rld;
    private ReimburseBillListAdapter adapter;
    private List<ReimburseList> list;
    private double m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reimburesebill);
        ButterKnife.bind(ReimburseBillActivity.this);
        Log.i(TAG, "onCreate: ");
        initView();
    }

    private void initView() {
        acache = ACache.get(ReimburseBillActivity.this);
        recycler_view.setLayoutManager(new LinearLayoutManager(ReimburseBillActivity.this));
//        recycler_view.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getApplicationContext()));
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.reimbursebill);
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            String reimburseId = bundle.getString("reimburseId");
            Log.i(TAG, "initView: "+reimburseId);
            getIdByMessage(reimburseId);
            reimbursbill_name.setTag(reimburseId);
//            String billname = bundle.getString("billname");
//            double billmoney = Double.parseDouble(bundle.getString("billmoney"));
//            String billtime = bundle.getString("billtime");
//            reimbursbill_name.setText(billname);
//            reimbursbill_money.setText(billmoney+"");
//            reimbursbill_time.setText(billtime);
        }else{
            Toast.makeText(ReimburseBillActivity.this, "test", Toast.LENGTH_SHORT).show();
        }

    }

    private void getIdByMessage(String id) {
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REIMBURSE_TAG +
                PublicUtils.URL_FILE_REIMBURSEGETBYIDACTION_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("id", id);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (PublicUtils.isNetworkAvailable(ReimburseBillActivity.this)) {
                            Toast.makeText(ReimburseBillActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReimburseBillActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        double billmoney = Double.parseDouble(rl.getRemiurseHole());
                        reimbursbill_name.setText(rl.getReimurser().toString());
                        reimbursbill_money.setText(billmoney+"");
                        reimbursbill_time.setText(rl.getReimurseTime().toString());
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.i(TAG, "onResume: ");
        gteIdBillList(reimbursbill_name.getTag().toString());
    }

    private void gteIdBillList(String id) {
        String url = PublicUtils.URL +
                    PublicUtils.URL_FILE_TAG +
                    PublicUtils.URL_FILE_REIMBURSELIST_TAG +
                    PublicUtils.URL_FILE_REIMBURSELISTLISTACTION_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("reimburseList.relateData", id);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (PublicUtils.isNetworkAvailable(ReimburseBillActivity.this)) {
                                Toast.makeText(ReimburseBillActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ReimburseBillActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Gson gson = new Gson();
                rld = gson.fromJson(str, ReimburseListDto.class);
                if (rld.getTotal().equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(ReimburseBillActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "run: rld=0");
                            remiurseHole.setText("0.0");
                        }
                    });

                } else {
                    list = rld.getRows();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            double m = 0;
                            for (int i = 0; i < list.size(); i++) {
                                double j = Double.parseDouble(list.get(i).getAmount());
                                m+=j;
                            }
                            remiurseHole.setText(String.valueOf(m));
                            remiurseHole.setTag(list.size());
                            adapter=new ReimburseBillListAdapter(R.layout.reimbursebill_item,list);
                            recycler_view.setAdapter(adapter);
                            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(final BaseQuickAdapter adapter, View view, int position) {
                                    TextView textView = (TextView) view.findViewById(R.id.rbitem_money);
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("billId", textView.getTag() + "");
                                    intent.putExtras(bundle);
                                    intent.setClass(ReimburseBillActivity.this, ReimburseBillUpdateActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
            }
        });
    }
    @OnClick(R.id.reimbursbill_add)
    public void reimbursbill_add(View v) {
        Intent intent=new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("billid",reimbursbill_name.getTag().toString());
        intent.putExtras(bundle);
        intent.setClass(ReimburseBillActivity.this,ReimburseBillAddActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.reimbursbill_save)
    public void reimbursbill_save(View v) {
        String money = reimbursbill_money.getText().toString();
        String size = remiurseHole.getTag() == null ? "0" : remiurseHole.getTag().toString();
        if(!StringUtil.isEmpty(money)){
            String url = PublicUtils.URL +
                    PublicUtils.URL_FILE_TAG +
                    PublicUtils.URL_FILE_REIMBURSE_TAG +
                    PublicUtils.URL_FILE_REIMBURSEUPDATEACTION_TAG;
            Map<String, String> m = new HashMap<>();
            m.put("reimburse.id", reimbursbill_name.getTag().toString());
            m.put("reimburse.remiurseHole", reimbursbill_money.getText().toString());
            m.put("reimburse.docs",size);
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (PublicUtils.isNetworkAvailable(ReimburseBillActivity.this)) {
                                Toast.makeText(ReimburseBillActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ReimburseBillActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(ReimburseBillActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReimburseBillActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }else{
            Toast.makeText(ReimburseBillActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.reimbursbill_submit)
    public void reimbursbill_submit(View v) {
        String topmoney=reimbursbill_money.getText().toString();
        String bottommoney=remiurseHole.getText().toString();
        String size=remiurseHole.getTag().toString();
        Log.i(TAG, "reimbursbill_submit: "+topmoney+"--"+bottommoney);
        if(topmoney.equals(bottommoney)){
            String url = PublicUtils.URL +
                    PublicUtils.URL_FILE_TAG +
                    PublicUtils.URL_FILE_REIMBURSE_TAG +
                    PublicUtils.URL_FILE_REIMBURSEGOFIRTSTACTION_TAG;
            Map<String, String> m = new HashMap<>();
            m.put("reimburse.id", reimbursbill_name.getTag().toString());
            m.put("reimburse.remiurseHole", reimbursbill_money.getText().toString());
            m.put("reimburse.docs",size );
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (PublicUtils.isNetworkAvailable(ReimburseBillActivity.this)) {
                                Toast.makeText(ReimburseBillActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ReimburseBillActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(ReimburseBillActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReimburseBillActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }else{
            Toast.makeText(ReimburseBillActivity.this, "发票金额与报销单金额不符", Toast.LENGTH_SHORT).show();
        }

    }
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
}
