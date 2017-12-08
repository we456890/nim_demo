package com.netease.nim.demo.borrow.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.demo.R;
import com.netease.nim.demo.borrow.adapter.BorrowCheckBMAdapter;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.bean.Borrow;
import com.netease.nim.uikit.common.bean.FrameRecord;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
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
 * 查看自己申请
 * Created by 78560 on 2017/10/19.
 */

public class BorrowMoneyCheckActivity extends UI{

    private static final String TAG = "wk_BorrowMoneyCheckA";
    @Bind(R.id.user_head)
    HeadImageView bc_head;
    @Bind(R.id.borrow_name)
    TextView borrow_name;
    @Bind(R.id.borrow_money)
    TextView borrow_money;
    @Bind(R.id.borrow_time)
    TextView borrow_time;
    @Bind(R.id.borrow_cause)
    TextView borrow_cause;
    @Bind(R.id.borrow_flow)
    TextView borrow_flow;

    @Bind(R.id.bmcheck_rv)
    RecyclerView bmcheck_rv;

    private ACache acache;
    private UserDTO userDTO;
    private Borrow b;
    private List<FrameRecord> frameRecords;
    private BorrowCheckBMAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrowmoney_check);
        ButterKnife.bind(BorrowMoneyCheckActivity.this);
        init();
    }
    private void init() {
        bmcheck_rv.setLayoutManager(new LinearLayoutManager(this));
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.borrow_apply);
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

    private void getIdContent(String messageId) {
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_BORROW_TAG +
                PublicUtils.URL_FILE_BORROWGTEBYIDACTION_TAG;
        Map<String,String> m = new HashMap<>();
        m.put("id",messageId);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(BorrowMoneyCheckActivity.this)) {
                    Toast.makeText(BorrowMoneyCheckActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BorrowMoneyCheckActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Log.i(TAG, "onResponse: "+str);
                Gson gson = new Gson();
                b = gson.fromJson(str, Borrow.class);
                frameRecords=b.getRecordList();
                if(b!=null){
                    setText(b);
                }else{
                    Toast.makeText(BorrowMoneyCheckActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setText(final Borrow b) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                borrow_name.setText("申请人:"+b.getBorrower());
                borrow_money.setText("借款金额:\t"+b.getAmount()+"\t元");
                borrow_time.setText("借款时间:"+b.getPrayTime());
                borrow_cause.setText("    "+b.getReason());
                bc_head.loadBuddyAvatar(b.getAccid());
                borrow_flow.setText(b.getFrameTyle());
                adapter = new BorrowCheckBMAdapter(R.layout.reimburse_approval_item,frameRecords);
                bmcheck_rv.setAdapter(adapter);
            }
        });
    }

}
