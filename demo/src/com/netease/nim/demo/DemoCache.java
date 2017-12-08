package com.netease.nim.demo;

import android.content.Context;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;

/**
 * Created by jezhee on 2/20/15.
 */
public class DemoCache {

    private static UserDTO userDTO;

    private static String useeid;

    private static Context context;

    private static String account;

    private static StatusBarNotificationConfig notificationConfig;

    public static void clear() {
        account = null;
    }

    public static String getAccount() {
        return account;
    }

    public static String getUserId() {
        return useeid;
    }

    private static boolean mainTaskLaunching;

    public static void setUserDTO(UserDTO u) {
        DemoCache.userDTO = u;
    }

    public static UserDTO getUserDTO() {
        return userDTO;
    }


    public static void setAccount(String account) {
        DemoCache.account = account;
        NimUIKit.setAccount(account);
    }

    public static void setUserId(String useeid) {
        DemoCache.useeid = useeid;
    }

    public static void setNotificationConfig(StatusBarNotificationConfig notificationConfig) {
        DemoCache.notificationConfig = notificationConfig;
    }

    public static StatusBarNotificationConfig getNotificationConfig() {
        return notificationConfig;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DemoCache.context = context.getApplicationContext();
    }

    public static void setMainTaskLaunching(boolean mainTaskLaunching) {
        DemoCache.mainTaskLaunching = mainTaskLaunching;
    }

    public static boolean isMainTaskLaunching() {
        return mainTaskLaunching;
    }
}
