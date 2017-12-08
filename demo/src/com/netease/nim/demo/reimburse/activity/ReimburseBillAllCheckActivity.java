package com.netease.nim.demo.reimburse.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.demo.R;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.demo.reimburse.adapter.ReimburseBillListAdapter;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.bean.ReimburseList;
import com.netease.nim.uikit.common.bean.ReimburseListDto;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 发票列表
 * Created by 78560 on 2017/11/1.
 */

public class ReimburseBillAllCheckActivity extends UI {
    private static String TAG = "wk_RBillAllCheck";

    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

    private ACache acache;
    private ReimburseListDto rld;
    private List<ReimburseList> list;
    private ReimburseBillListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reimburesebill_allcheck);
        ButterKnife.bind(ReimburseBillAllCheckActivity.this);
        initView();
    }

    private void initView() {
        acache = ACache.get(ReimburseBillAllCheckActivity.this);
        recycler_view.setLayoutManager(new LinearLayoutManager(ReimburseBillAllCheckActivity.this));
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.reimbursebill_content);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = this.getIntent().getExtras();
        String billid = bundle.getString("billId");
        if(!billid.equals(null)) {
            getIdBillContent(billid);
        }
    }

    private void getIdBillContent(String billid) {
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_REIMBURSELIST_TAG +
                PublicUtils.URL_FILE_REIMBURSELISTLISTACTION_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("reimburseList.relateData", billid);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (PublicUtils.isNetworkAvailable(ReimburseBillAllCheckActivity.this)) {
                            Toast.makeText(ReimburseBillAllCheckActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReimburseBillAllCheckActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
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
                        }
                    });

                } else {
                    list = rld.getRows();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new ReimburseBillListAdapter(R.layout.reimbursebill_item,list);
                            recycler_view.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }
}
