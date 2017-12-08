package com.netease.nim.demo.login;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.config.preference.UserPreferences;
import com.netease.nim.demo.main.activity.MainActivity;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nim.uikit.permission.MPermission;
import com.netease.nim.uikit.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 登录/注册界面
 * <p/>
 * Created by huangjun on 2015/2/1.
 */
public class LoginActivity extends UI implements OnKeyListener{

    Context context;
    private ACache acache;
    private static final String TAG = "wk_LoginActivity";
    private static final String KICK_OUT = "KICK_OUT";
    private final int BASIC_PERMISSION_REQUEST_CODE = 110;


    private EditText loginAccountEdit;
    private EditText loginPasswordEdit;

    private Button login_tip;
//

    private View loginLayout;

    private AbortableFuture<LoginInfo> loginRequest;

    public static void start(Context context) {
        start(context, false);
    }

    public static void start(Context context, boolean kickOut) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(KICK_OUT, kickOut);
        context.startActivity(intent);
    }

    @Override
    protected boolean displayHomeAsUpEnabled() {
        return false;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        context = LoginActivity.this;
        acache = ACache.get(this);
        setupLoginPanel();

        if (DemoCache.getAccount() != null) {
            MainActivity.start(LoginActivity.this);
            finish();
        }
    }

    /**
     * 基本权限管理
     */

    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

//    private void requestBasicPermission() {
//        MPermission.with(LoginActivity.this)
//                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
//                .permissions(BASIC_PERMISSIONS)
//                .request();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
    }



    /**
     * 登录面板
     */
    private void setupLoginPanel() {
        login_tip = (Button) findViewById(R.id.login_tip);
        loginAccountEdit = findView(R.id.edit_login_account);
        loginPasswordEdit = findView(R.id.edit_login_password);
        loginPasswordEdit.setOnKeyListener(this);
        String account = Preferences.getUserAccount();
        if (account != null) {
            loginAccountEdit.setText(account);
        }
    }


    private void getMenu(String userid) {
        String urlGo = PublicUtils.URL
                + PublicUtils.URL_FILE_TAG
                + PublicUtils.URL_FILE_MENU_TAG
                + PublicUtils.URL_FILE_MENU_RESPONSE_TAG; //请求地址
        Log.i(TAG, "urlGo: " + urlGo);
        Map<String,String> m=new HashMap<>();
        m.put("userId", userid);
        HttpUtils.doPost(urlGo, getUserId(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(LoginActivity.this)) {
                    Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i(TAG, "str: " + str);
                acache.put("menu",str);
//                getMenugson(str);
            }
        });
    }

    private Map<String, String> getUserId() {
        UserDTO uu = (UserDTO) acache.getAsObject("user");
        Map<String, String> m = new HashMap<String, String>();
        m.put("userId", uu.getId());
        Log.i(TAG, "userId: " + uu.getId());
        return m;
    }

    @Override
    protected void onResume() {
        super.onResume();
        login_tip.setOnClickListener(lic);
    }

    View.OnClickListener lic = new OnClickListener() {
        @Override
        public void onClick(View view) {
//            String accid = getText().get("username");
//            String pwd = getText().get("password");
            Map<String, String> map = new HashMap<>();
            String accid = loginAccountEdit.getText().toString().trim();
            String pwd = loginPasswordEdit.getText().toString().trim();
            if (isEmpty(accid, pwd)) {
                map.put("username", accid);
                map.put("password", pwd);
                String urlGo = PublicUtils.URL + PublicUtils.URL_FILE_TAG + PublicUtils.URL_LOGIN_TAG; //请求地址
                HttpUtils.doPost(urlGo, map, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (PublicUtils.isNetworkAvailable(LoginActivity.this) == false) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "没有网络，请连接网络后再试。", Toast.LENGTH_LONG).show();
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                        String str = response.body().string();
                        Log.i(TAG, "onResponse"+str);
                        getLogingson(str);
                    }
                });
            } else {
                return;
            }
        }

    };

    /**
     * 解析json
     */
    private void getLogingson(String json) {
        Gson gson = new Gson();
        final UserDTO u = gson.fromJson(json, UserDTO.class);
        acache.put("user", u);
        Log.i("wk_login", u.toString() + "");

        if (u.getOperateSuccess() == "false") {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "没有权限", Toast.LENGTH_LONG).show();
                }
            });
        } else if (u.getStatus().equals("0")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "登录失败！", Toast.LENGTH_LONG).show();
                }
            });
        } else if (u.getStatus().equals("1")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "登录成功！", Toast.LENGTH_LONG).show();
                    getMenu(u.getId());
                }
            });
            login(u.getAccid(), u.getToken(), u);
            DemoCache.setUserId(u.getUserId());
//            Log.d("wk___login", "accid: "+u.getUserId()+",token:"+u.getToken());
            // 进入主界面
            MainActivity.start(LoginActivity.this, null);
            finish();
        }
    }

    //输入是否为空
    boolean isEmpty(String accid, String pwd) {
        boolean isem = true;
        if (TextUtils.isEmpty(accid)) {
            Toast.makeText(LoginActivity.this, "账号为空...", Toast.LENGTH_SHORT).show();
            loginAccountEdit.requestFocus();
            isem = false;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(LoginActivity.this, "密码为空...", Toast.LENGTH_SHORT).show();
            loginPasswordEdit.requestFocus();
            isem = false;
        }
        return isem;
    }

    //获取输入文字
    Map<String, String> getText() {
        Map<String, String> map = new HashMap<>();
        String accid = loginAccountEdit.getText().toString().trim();
        String pwd = loginPasswordEdit.getText().toString().trim();
        map.put("username", "chengzehao");
        map.put("password", "5");
        return map;
    }

    /**
     * ***************************************** 登录 **************************************
     */

    private void login(final String account, final String token, final UserDTO userDTO) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogMaker.showProgressDialog(LoginActivity.this, null, getString(R.string.logining), true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (loginRequest != null) {
                            loginRequest.abort();
                            onLoginDone();
                        }
                    }
                }).setCanceledOnTouchOutside(false);
            }
        });


        // 云信只提供消息通道，并不包含用户资料逻辑。开发者需要在管理后台或通过服务器接口将用户帐号和token同步到云信服务器。
        // 在这里直接使用同步到云信服务器的帐号和token登录。
        // 这里为了简便起见，demo就直接使用了密码的md5作为token。
        // 如果开发者直接使用这个demo，只更改appkey，然后就登入自己的账户体系的话，需要传入同步到云信服务器的token，而不是用户密码。
//        final String account = loginAccountEdit.getEditableText().toString().toLowerCase();
//        final String token = tokenFromPassword(loginPasswordEdit.getEditableText().toString());
        // 登录
        loginRequest = NimUIKit.doLogin(new LoginInfo(account, token), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                LogUtil.i(TAG, "login success");

                onLoginDone();

                DemoCache.setAccount(account);

                saveLoginInfo(account, token, userDTO);

                // 初始化消息提醒配置
                initNotificationConfig();


            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
                Toast.makeText(LoginActivity.this, R.string.login_exception, Toast.LENGTH_LONG).show();
                onLoginDone();
            }
        });
    }

    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();

            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }

    private void onLoginDone() {
        loginRequest = null;
        DialogMaker.dismissProgressDialog();
    }

    /**
     * 缓存
     *
     * @param account 用户名
     * @param token
     * @param userDTO 返回信息类
     */
    private void saveLoginInfo(final String account, final String token, final UserDTO userDTO) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
        Preferences.saveUserDTO(userDTO);
    }

    //DEMO中使用 username 作为 NIM 的account ，md5(password) 作为 token
    //开发者需要根据自己的实际情况配置自身用户系统和 NIM 用户系统的关系
    private String tokenFromPassword(String password) {
        String appKey = readAppKey(this);
        boolean isDemo = "45c6af3c98409b18a84451215d0bdd6e".equals(appKey)
                || "e052d8e53b356b1d2944e82ad68d12b2".equals(appKey);
//        fe416640c8e8a72734219e1847ad2547

        return isDemo ? MD5.getStringMD5(password) : password;
    }

    private static String readAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
