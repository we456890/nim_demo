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
import com.netease.nim.uikit.common.bean.ReimburseList;
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
 * 发票修改
 * Created by 78560 on 2017/11/1.
 */

public class ReimburseBillUpdateActivity extends UI {
    private static String TAG = "wk_ReimburseBillAdd";

    @Bind(R.id.billcheck_money)
    EditText billcheck_money;
    @Bind(R.id.billcheck_time)
    TextView billcheck_time;
    @Bind(R.id.billcheck_type)
    EditText billcheck_type;
    @Bind(R.id.billcheck_cause)
    EditText billcheck_cause;
    @Bind(R.id.billcheck_delete)
    Button billcheck_delete;
    @Bind(R.id.billcheck_save)
    Button billcheck_save;

    private ACache acache;
    private UserDTO userDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reimburesebill_update);
        ButterKnife.bind(ReimburseBillUpdateActivity.this);
        initView();
    }

    private void initView() {
        acache = ACache.get(ReimburseBillUpdateActivity.this);
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.reimburs_updatebill);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = this.getIntent().getExtras();
        String billid = bundle.getString("billId");
        if(!billid.equals(null)) {
            billcheck_money.setTag(billid);
            getIdContent(billid);
        }
    }

    private void getIdContent(String billid) {
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REIMBURSELIST_TAG +
                PublicUtils.URL_FILE_REIMBURSELISTGETBYIDACTION_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("id", billid);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (PublicUtils.isNetworkAvailable(ReimburseBillUpdateActivity.this)) {
                            Toast.makeText(ReimburseBillUpdateActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReimburseBillUpdateActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Gson gson = new Gson();
                final ReimburseList r=gson.fromJson(str, ReimburseList.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        billcheck_money.setText(r.getAmount());
                        billcheck_time.setText(r.getPaydate());;
                        billcheck_type.setText(r.getStateType());;
                        billcheck_cause.setText(r.getBz());;
                    }
                });
            }
        });
    }

    /**
     * 删除
     * @param v
     */
    @OnClick(R.id.billcheck_delete)
    public void billcheck_delete(View v) {
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REIMBURSELIST_TAG +
                PublicUtils.URL_FILE_REIMBURSELISTDELETEACTION_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("ids", billcheck_money.getTag().toString());
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (PublicUtils.isNetworkAvailable(ReimburseBillUpdateActivity.this)) {
                            Toast.makeText(ReimburseBillUpdateActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReimburseBillUpdateActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ReimburseBillUpdateActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReimburseBillUpdateActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    /**
     * 修改
     * @param v
     */
    @OnClick(R.id.billcheck_save)
    public void billcheck_save(View v) {
        String money=billcheck_money.getText().toString().trim();
        String time=billcheck_time.getText().toString().trim();
        String type=billcheck_type.getText().toString().trim();
        String cause=billcheck_cause.getText().toString().trim();
        if (isEmpty(money,time,type,cause)) {
            String url = PublicUtils.URL +
                    PublicUtils.URL_FILE_TAG +
                    PublicUtils.URL_FILE_REIMBURSELIST_TAG +
                    PublicUtils.URL_FILE_REIMBURSELISTUPDATEACTION_TAG;

            Map<String, String> m = new HashMap<>();
            m.put("reimburseList.amount", money);
            m.put("reimburseList.paydate", time);
            m.put("reimburseList.stateType", type);
            m.put("reimburseList.bz", cause);
            m.put("reimburseList.id", billcheck_money.getTag().toString());
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (PublicUtils.isNetworkAvailable(ReimburseBillUpdateActivity.this)) {
                                Toast.makeText(ReimburseBillUpdateActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ReimburseBillUpdateActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(ReimburseBillUpdateActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReimburseBillUpdateActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }else{
            Toast.makeText(ReimburseBillUpdateActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
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

}
