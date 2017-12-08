package com.netease.nim.demo.main.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.demo.R;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.login.LoginActivity;
import com.netease.nim.demo.login.LogoutHelper;
import com.netease.nim.demo.reporting.activity.ReportingBossActivity;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.bean.OperateInfo;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

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
 * Created by hzxuwen on 2015/6/26.
 */
public class UpdatePwdActivity extends UI {
    private static String TAG = "wk_UpdatePwdActivity";
    private ACache acache;

    @Bind(R.id.et_updatepnew1)
    EditText et_updatepnew1;
    @Bind(R.id.et_updatepnew2)
    EditText et_updatepnew2;
    @Bind(R.id.et_updatepraw)
    EditText et_updatepraw;
    @Bind(R.id.btn_updatep)
    Button btn_updatep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_pwd_activity);
        ButterKnife.bind(UpdatePwdActivity.this);
        acache = ACache.get(this);

        ToolBarOptions options = new ToolBarOptions();
        options.titleId = R.string.updatepwd;
        setToolBar(R.id.toolbar, options);
    }

    public boolean isEm(String e1, String e2, String e3) {
        boolean tf = false;
        if (!TextUtils.isEmpty(e1) && !TextUtils.isEmpty(e2) && !TextUtils.isEmpty(e3)) {
            tf = true;
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(UpdatePwdActivity.this, "输入密码不能为空！", Toast.LENGTH_SHORT).show();
                }
            });
            tf = false;
        }
        return tf;
    }

    @OnClick(R.id.btn_updatep)
    public void btn_updatep(View v) {
        String e1 = et_updatepnew1.getText().toString().trim();
        String e2 = et_updatepnew2.getText().toString().trim();
        String eu = et_updatepraw.getText().toString().trim();
        UserDTO u = (UserDTO) acache.getAsObject("user");
        if (e1.equals(e2) && isEm(e1, e2, eu)) {
            String url = PublicUtils.URL +
                    PublicUtils.URL_FILE_TAG +
                    PublicUtils.URL_FILE_USER_TAG +
                    PublicUtils.URL_FILE_UPDATEPWD_USER_RESPONSE_TAG;
            Map<String, String> m = new HashMap<>();
            m.put("newPassword", e1);
            m.put("oldPassword", eu);
            m.put("user.id", u.getId());
            m.put("user.username", u.getRealName());
            m.put("user.password", eu);
            m.put("user.plainPassword", e1);
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (PublicUtils.isNetworkAvailable(UpdatePwdActivity.this)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UpdatePwdActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();

                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UpdatePwdActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String str = response.body().string();
                    Gson gson=new Gson();
                    final OperateInfo operateInfo=gson.fromJson(str,OperateInfo.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UpdatePwdActivity.this, operateInfo.getOperateMessage(), Toast.LENGTH_SHORT).show();
                            UpdatePwdActivity.this.finish();
//                            LogoutHelper.logout();
                            // 启动登录
//                            LoginActivity.start(this);
                        }
                    });
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(UpdatePwdActivity.this, "输入有误检查后再试", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * 注销
     */
    public void logout() {
        removeLoginState();
        MainActivity.logout(UpdatePwdActivity.this, false);
        ACache aCache=ACache.get(this);
        aCache.clear();
        finish();
        NIMClient.getService(AuthService.class).logout();
    }

    /**
     * 清除登陆状态
     */
    private void removeLoginState() {
        Preferences.saveUserToken("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // android2.3以下版本 布局混乱的问题
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
