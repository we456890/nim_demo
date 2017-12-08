package com.netease.nim.demo.reimburse.activity;

import android.content.Intent;
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
 * 报销单申请
 * Created by 78560 on 2017/10/10.
 */

public class ReimburseMoneyAddActivity extends UI {
    private static String TAG = "wk_ReimbureseMoneyAdd";

    @Bind(R.id.reimburs_name)
    TextView reimburs_name;
    @Bind(R.id.reimburs_money)
    EditText reimburs_money;
    @Bind(R.id.reimburs_time)
    TextView reimburs_time;
//    @Bind(R.id.user_head)
//    HeadImageView user_head;
//    @Bind(R.id.borrow_approvename)
//    TextView borrow_approvename;
    @Bind(R.id.reimburs_next)
    Button reimburs_next;

    private ACache acache;
    private UserDTO userDTO;
    private FrameControl frameC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reimburese_add);
        ButterKnife.bind(ReimburseMoneyAddActivity.this);
        initView();
    }

    private void initView() {
        acache = ACache.get(ReimburseMoneyAddActivity.this);
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.reimburs_add);
        userDTO = (UserDTO) acache.getAsObject("user");
        if(userDTO!=null) {
            reimburs_name.setText(userDTO.getRealName());
            reimburs_time.setText(StringUtil.getNewTime());
        }else{
            Toast.makeText(ReimburseMoneyAddActivity.this, "请重新登录...", Toast.LENGTH_SHORT).show();
        }
    }



    @OnClick(R.id.reimburs_next)
    public void reimburs_next(View v) {
        String realname=reimburs_name.getText().toString().trim();
        String money=reimburs_money.getText().toString().trim();
        if (isEmpty(realname,money)) {
            String url = PublicUtils.URL +
                    PublicUtils.URL_FILE_TAG +
                    PublicUtils.URL_FILE_REIMBURSE_TAG +
                    PublicUtils.URL_FILE_REIMBURSEADDACTION_TAG;

            Map<String, String> m = new HashMap<>();
            m.put("reimburse.reimurser", realname);
            m.put("reimburse.reimurserId", userDTO.getId());
            m.put("reimburse.remiurseHole", money);
            m.put("reimburse.reimurseTime", reimburs_time.getText().toString().trim());
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (PublicUtils.isNetworkAvailable(ReimburseMoneyAddActivity.this)) {
                                Toast.makeText(ReimburseMoneyAddActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ReimburseMoneyAddActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(ReimburseMoneyAddActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                                String id=oi.getOperateCode();
                                Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString("reimburseId",id);
                                intent.putExtras(bundle);
                                intent.setClass(ReimburseMoneyAddActivity.this, ReimburseBillActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReimburseMoneyAddActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }else{
            Toast.makeText(ReimburseMoneyAddActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
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
