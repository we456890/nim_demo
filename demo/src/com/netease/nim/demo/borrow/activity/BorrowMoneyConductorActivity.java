package com.netease.nim.demo.borrow.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 审批借款
 * Created by 78560 on 2017/10/20.
 */

public class BorrowMoneyConductorActivity extends UI{

    private static final String TAG = "wk_BorrowMoneyAuditCA";

    @Bind(R.id.bc_cause)
    TextView bc_cause;
    @Bind(R.id.bc_money)
    TextView bc_money;
    @Bind(R.id.bc_postil)
    EditText bc_postil;
    @Bind(R.id.bc_time)
    TextView bc_time;
    @Bind(R.id.borrow_approvename)
    TextView bc_approvename;
    @Bind(R.id.user_head)
    HeadImageView bc_head;
    @Bind(R.id.borrow_flow)
    TextView borrow_flow;

    @Bind(R.id.bc_approval)
    RelativeLayout bc_approval;
    @Bind(R.id.bcrl_postil)
    RelativeLayout bcrl_postil;
    @Bind(R.id.bc_end)
    RelativeLayout ll_end;

    @Bind(R.id.bc_consent)
    Button bc_consent;
    @Bind(R.id.bc_reject)
    Button bc_reject;
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
        setContentView(R.layout.borrowmoney_conductor);
        ButterKnife.bind(BorrowMoneyConductorActivity.this);
        init();
    }
    private void init() {
        acache = ACache.get(BorrowMoneyConductorActivity.this);
        bmcheck_rv.setLayoutManager(new LinearLayoutManager(this));
        userDTO = (UserDTO) acache.getAsObject("user");
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
                if (PublicUtils.isNetworkAvailable(BorrowMoneyConductorActivity.this)) {
                    Toast.makeText(BorrowMoneyConductorActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BorrowMoneyConductorActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(BorrowMoneyConductorActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setText(final Borrow b) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bc_approvename.setText("申请人:"+b.getBorrower());
                bc_money.setText("借款金额:\t"+b.getAmount()+"\t元");
                bc_head.loadBuddyAvatar(b.getAccid());
                bc_time.setText("借款时间:"+b.getPrayTime());
                bc_cause.setText("    "+b.getReason());
                borrow_flow.setText(b.getFrameTyle());
                adapter = new BorrowCheckBMAdapter(R.layout.borrowmoney_check_item,frameRecords);
                bmcheck_rv.setAdapter(adapter);
            }
        });
    }
    /**
     * 显示批注
     * @param v
     */
    @OnClick(R.id.bc_approval)
    public void bc_approval(View v) {
        bcrl_postil.setVisibility(View.VISIBLE);
        bc_approval.setVisibility(View.GONE);
    }

    /**
     *
     * 收起批注
     * @param v
     */
    @OnClick(R.id.bc_end)
    public void bc_end(View v) {
        if(bcrl_postil.getVisibility()==View.VISIBLE){
            bcrl_postil.setVisibility(View.GONE);
            bc_approval.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 同意
     * @param v
     */
    @OnClick(R.id.bc_consent)
    public void bc_consent(View v) {
        String context=bc_postil.getText().toString().trim();
            String url = PublicUtils.URL +
                    PublicUtils.URL_FILE_TAG +
                    PublicUtils.URL_FILE_BORROW_TAG +
                    PublicUtils.URL_FILE_BORROWGONEXTACTION_TAG;
            Map<String,String> m = new HashMap<>();
            m.put("borrow.id",b.getId());
            m.put("borrow.step",b.getStep());
            m.put("frameRecord.concatFrame",BORROW_FRAME);
            m.put("frameRecord.concatData",b.getId());
            m.put("frameRecord.processor",userDTO.getRealName());
            m.put("frameRecord.processorId",userDTO.getId());
            m.put("frameRecord.context",context);
            m.put("frameRecord.chosen","同意");
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (PublicUtils.isNetworkAvailable(BorrowMoneyConductorActivity.this)) {
                        Toast.makeText(BorrowMoneyConductorActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BorrowMoneyConductorActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String str=response.body().string();
                    Log.i(TAG, "onResponse: "+str);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BorrowMoneyConductorActivity.this,"批复成功！",Toast.LENGTH_SHORT).show();

                            Log.i(TAG, "run: "+str);
                            finish();
                        }
                    });
                }
            });
    }

    /**
     * 拒绝
     * @param v
     */
    @OnClick(R.id.bc_reject)
    public void bc_reject(View v) {
        String context=bc_postil.getText().toString().trim();
        if(!TextUtils.isEmpty(context)){
            String url = PublicUtils.URL +
                    PublicUtils.URL_FILE_TAG +
                    PublicUtils.URL_FILE_BORROW_TAG +
                    PublicUtils.URL_FILE_BORROWREGETFACTION_TAG;
            Map<String,String> m = new HashMap<>();
            m.put("borrow.id",b.getId());
            m.put("borrow.step",b.getStep());
            m.put("frameRecord.concatFrame",BORROW_FRAME);
            m.put("frameRecord.concatData",b.getId());
            m.put("frameRecord.processor",userDTO.getRealName());
            m.put("frameRecord.processorId",userDTO.getId());
            m.put("frameRecord.context",context);
            m.put("frameRecord.chosen","拒绝");
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (PublicUtils.isNetworkAvailable(BorrowMoneyConductorActivity.this)) {
                        Toast.makeText(BorrowMoneyConductorActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BorrowMoneyConductorActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String str=response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BorrowMoneyConductorActivity.this,"批复成功！",Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "run: "+str);
                            finish();
                        }
                    });
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BorrowMoneyConductorActivity.this,"批注不能为空",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
