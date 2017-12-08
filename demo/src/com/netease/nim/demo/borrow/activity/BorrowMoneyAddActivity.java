package com.netease.nim.demo.borrow.activity;

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
import com.netease.nim.uikit.common.bean.FrameControl;
import com.netease.nim.uikit.common.bean.OperateInfo;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
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

public class BorrowMoneyAddActivity extends UI {
    private static String TAG = "wk_BorrowMoneyApply";

    @Bind(R.id.borrow_name)
    TextView borrow_name;
    @Bind(R.id.borrow_money)
    EditText borrow_money;
    @Bind(R.id.borrow_time)
    TextView borrow_time;
    @Bind(R.id.borrow_cause)
    EditText borrow_cause;
    @Bind(R.id.user_head)
    HeadImageView user_head;
    @Bind(R.id.borrow_approvename)
    TextView borrow_approvename;
    @Bind(R.id.borrow_submit)
    Button borrow_submit;

    private ACache acache;
    private UserDTO userDTO;
    private FrameControl frameC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrowmoney_add);
        ButterKnife.bind(BorrowMoneyAddActivity.this);
        initView();
        getUser_head();
    }

    private void initView() {
        acache = ACache.get(BorrowMoneyAddActivity.this);
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.borrow_apply);
        userDTO = (UserDTO) acache.getAsObject("user");
    }



    @OnClick(R.id.borrow_submit)
    public void borrow_submit(View v) {
        String realname=borrow_name.getText().toString().trim();
        String cause=borrow_cause.getText().toString().trim();
        String money=borrow_money.getText().toString().trim();
        if (isEmpty(realname,cause,money)) {
            String url = PublicUtils.URL +
                    PublicUtils.URL_FILE_TAG +
                    PublicUtils.URL_FILE_BORROW_TAG +
                    PublicUtils.URL_FILE_BORROWADDACTION_TAG;

            Map<String, String> m = new HashMap<>();
            m.put("borrow.borrower", userDTO.getRealName());
            m.put("borrow.borrowerId", userDTO.getId());
            m.put("borrow.reason", borrow_cause.getText().toString().trim());
            m.put("borrow.amount", borrow_money.getText().toString().trim());
            m.put("borrow.prayTime", borrow_time.getText().toString().trim());
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (PublicUtils.isNetworkAvailable(BorrowMoneyAddActivity.this)) {
                        Toast.makeText(BorrowMoneyAddActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BorrowMoneyAddActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                    }
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

                                Toast.makeText(BorrowMoneyAddActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BorrowMoneyAddActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }
            });
        }else{
            Toast.makeText(BorrowMoneyAddActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 非空判断
     */
    private boolean isEmpty(String name,String cause,String money){
        boolean empty;
        if(StringUtil.isEmpty(name)||StringUtil.isEmpty(cause)||StringUtil.isEmpty(money)){
            empty=false;
        }else {
            empty=true;
        }
        return empty;
    }

    private void getUser_head() {
        String url = PublicUtils.URL +
                PublicUtils.URL_FILE_TAG +
                PublicUtils.URL_FILE_FRAMESET_TAG +
                PublicUtils.URL_FILE_FRAMESETACTION_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("reId", BORROW_FRAME);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(BorrowMoneyAddActivity.this)) {
                    Toast.makeText(BorrowMoneyAddActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BorrowMoneyAddActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                Gson gson = new Gson();
                frameC = gson.fromJson(str, FrameControl.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        borrow_name.setText(userDTO.getRealName());
                        borrow_time.setText(StringUtil.getNewTime());
                        user_head.loadBuddyAvatar(frameC.getDesinerAccount());
                        borrow_approvename.setText(frameC.getDesinerName());
                    }
                });
            }
        });
    }
}
