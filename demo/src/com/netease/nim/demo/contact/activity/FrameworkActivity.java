package com.netease.nim.demo.contact.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nim.demo.R;
import com.netease.nim.demo.Utility;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.demo.reporting.activity.ReportingBossCheckActivity;
import com.netease.nim.demo.reporting.activity.ReportingBossCollectActivity;
import com.netease.nim.demo.reporting.adapter.ReportingAdapter;
import com.netease.nim.demo.reporting.widgets.CustomDatePicker;
import com.netease.nim.demo.reporting.widgets.ZProgressHUD;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.bean.Reporting;
import com.netease.nim.uikit.common.bean.ReportingDto;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/**
 * Boss工作汇报页面
 */

/**
 * Created by 78560 on 2017/8/17.
 */

public class FrameworkActivity extends UI {


    @Bind(R.id.webView)
    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framework);
        ButterKnife.bind(FrameworkActivity.this);
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void init() {
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.framework);
        WebView mWebView=(WebView)findViewById(R.id.webView);
        WebSettings webSettings = mWebView .getSettings();
        webSettings.setJavaScriptEnabled(true); //支持js
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setDefaultTextEncodingName("utf-8"); //设置编码格式
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//适应屏幕，内容将自动缩放
        String url=PublicUtils.URL+
                   PublicUtils.URL_FILE_TAG+
                   PublicUtils.URL_FRAMEWORK_TAG;
//        String url= "192.168.1.110:8080/manual_pure/officialWebsite//index.html#";
        mWebView.loadUrl(url);//"http://"+
        mWebView.setWebViewClient(new webViewClient ());
    }
    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}