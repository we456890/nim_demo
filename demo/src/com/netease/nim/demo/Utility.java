package com.netease.nim.demo;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.netease.nim.uikit.common.bean.ReportingDto;

/**
 * Created by 78560 on 2017/9/4.
 * 解析和处理服务器返回的数据
 */

public class Utility {
    /**
     *
     */
    public static ReportingDto reportingBossResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                Gson gson = new Gson();
                ReportingDto r = gson.fromJson(response, ReportingDto.class);
                return r;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
