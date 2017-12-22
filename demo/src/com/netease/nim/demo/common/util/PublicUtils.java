package com.netease.nim.demo.common.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 78560 on 2017/7/28.
 */

public class PublicUtils {
//    public static String PATH ="/sdcard/";
    public static String[] BILL_TYPE={"test","餐饮","办公","交通","会议","住宿"};
    public static final String FILEDIR = "/sdcard/";
    public static final String FILENAME = "发票详情.pdf";
    public static final int ONE=1;
    public static final int TWO=2;
    public static final String PACKNAME="com.dynamixsoftware.printershare";   //printshare 包名
    public static final String APKNAME = "PrinterShare.apk";
    public static final String PACKURL="http://118.31.184.230:8000/gt_beta/officialWebsite/apks/PrinterShare.apk";


//    public static String URL = "http://192.168.1.104:8080/"; //测试地址
//    public static String URL_FILE_TAG = "manual_pure/";     //测试文件名

    public static String URL = "http://118.31.184.230:8000/";  //外网地址
    public static String URL_FILE_TAG = "gt_beta/";            //外网文件夹

//    http://localhost:8080/manual_pure/mobileCommon/mobileCommon!getAndroidVersion?version=1.0
//    /reversion/reversionAction!reportRevList
    /**
     * 二级文件夹
     */
    public static String URL_FRAMEWORK_TAG = "officialWebsite//index.html#";


    public static String URL_FILE_USER_TAG = "user/";                           //工作汇报
    public static String URL_FILE_AUTOSTATIS_TAG = "autoStatistics/";                           //工作汇报
    public static String URL_FILE_MENU_TAG = "menu/";                                          //菜单文件夹
    public static String URL_FILE_REPORTING_TAG = "reporting/";                                  //工作汇报数据
    public static String URL_FILE_UPDATE_TAG = "mobileCommon/";
    public static String URL_FILE_REVERSION_TAG ="reversion/";                                    //批复
    public static String URL_FILE_FRAMESET_TAG ="frameSet/";                                      //
    public static String URL_FILE_BORROW_TAG ="borrow/";                                           //借款
    public static String URL_FILE_REIMBURSE_TAG ="reimburse/";                                     //报销
    public static String URL_FILE_REIMBURSELIST_TAG ="reimburseList/";                             //发票
    public static String URL_FILE_WORKSTATION_TAG="workStation/";                                  //工作台
    public static String URL_FILE_HOLIDAYATION_TAG="holiday/";                                      //请假

    /**
     * 请求
     */
    public static String URL_FILE_WORKSTATION_GETWORK_TAG = "workStationAction!getWorkStationDataByUser";
    public static String URL_MOBILECOMMON_PDF_TAG = "mobileCommon!reimbursePDF";                    //获取pdf
    public static String URL_FILE_UPDATEPWD_USER_RESPONSE_TAG = "userUpdateAction!updateUserPwd";
    public static String URL_FILE_REVERSIONCOMMAND_RESPONSE_TAG = "reversionAction!reportRevList";                 //批复列表
    public static String URL_FILE_REVERSIONADD_RESPONSE_TAG = "reversionAction!add";                 //批复列表
    public static String URL_FILE_UPDATESUBMIT_RESPONSE_TAG = "reportingAction!updateSubmit";           //提交修改
    public static String URL_FILE_UPDATE_RESPONSE_TAG = "reportingAction!update";                 //修改保存
    public static String URL_FILE_REPORTING_RESPONSE_TAG = "reportingAction!list";                //工作汇报数据请求
    public static String URL_FILE_REPORTINGGETBYID_RESPONSE_TAG = "reportingAction!getById";                //getByid请求详细数据
    public static String URL_FILE_SEEREVIEW_RESPONSE_TAG = "reportingAction!seeReview";         //请求
    public static String URL_FILE_INSTREV_RESPONSE_TAG = "reportingAction!instRev";         //批复请求
    public static String URL_FILE_ADD_RESPONSE_TAG = "reportingAction!add";                    //提交草稿
    public static String URL_FILE_ADDSUBMIT_RESPONSE_TAG = "reportingAction!addSubmit";        //提交
    public static String URL_LOGIN_TAG = "login!loginApps.action";                          //登录请求
    public static String URL_FILE_MENU_RESPONSE_TAG = "menuAction!getMenuByUser.action";       //菜单请求
    public static String URL_FILE_AUTOSTATIS_RESPONSE_TAG = "autoStatisticsAction!statisticReport";    //boss汇报总结请求
    public static String URL_FILE_STATISTICREPORTING_RESPONSE_TAG = "autoStatisticsAction!statisticReporter";   //汇报情况
    public static String URL_FILE_UPDATEVERSION_TAG = "mobileCommon!getAndroidVersion";             //获取版本号
    public static String URL_FILE_FRAMESETACTION_TAG = "frameSetAction!getFirst";               //获取审批人
    public static String URL_FILE_BORROWADDACTION_TAG = "borrowAction!add";                        //借款新增
    public static String URL_FILE_BORROWLISTACTION_TAG = "borrowAction!myList";                        //借款列表
    public static String URL_FILE_BORROWJUDGELISTACTION_TAG = "borrowAction!judgeList";                 //借款申请列表
    public static String URL_FILE_BORROWGTEBYIDACTION_TAG = "borrowAction!getById";                 //借款详情
    public static String URL_FILE_BORROWGONEXTACTION_TAG = "borrowAction!goNext";                   //审批同意下一步
    public static String URL_FILE_BORROWREGETFACTION_TAG = "borrowAction!regetFrame";               //审批拒绝打回
    public static String URL_FILE_BORROWBREAKFACTION_TAG = "borrowAction!breakFrame";               //审批删除
    public static String URL_FILE_REIMBURSEADDACTION_TAG = "reimburseAction!add";                   //报销新增
    public static String URL_FILE_REIMBURSEUPDATEACTION_TAG = "reimburseAction!update";               //报销单保存
    public static String URL_FILE_REIMBURSELISTACTION_TAG = "reimburseAction!list";               //报销单列表
    public static String URL_FILE_REIMBURSEGETBYIDACTION_TAG = "reimburseAction!getById";          //提交报销单
    public static String URL_FILE_REIMBURSEGOFIRTSTACTION_TAG = "reimburseAction!goFirtst";          //提交报销单
    public static String URL_FILE_REIMBURSEGONEXTACTION_TAG = "reimburseAction!goNext";                 //批复同意
    public static String URL_FILE_REIMBURSEGOREGETACTION_TAG = "reimburseAction!regetFrame";          //批复拒绝
    public static String URL_FILE_REIMBURSELISTADDACTION_TAG = "reimburseListAction!add";               //发票新增
    public static String URL_FILE_REIMBURSELISTLISTACTION_TAG = "reimburseListAction!list";              //发票列表
    public static String URL_FILE_REIMBURSELISTDELETEACTION_TAG = "reimburseListAction!delete";         //删除发票
    public static String URL_FILE_REIMBURSELISTUPDATEACTION_TAG = "reimburseListAction!update";         //修改发票
    public static String URL_FILE_REIMBURSELISTGETBYIDACTION_TAG = "reimburseListAction!getById";          //发票详情
    public static String URL_FILE_HOLIDAYADDATION_TAG = "holidayAction!add";                            //新增请假
    public static String URL_FILE_HOLIDAYLISTATION_TAG = "holidayAction!list";                           //个人申请列表
    public static String URL_FILE_HOLIDAYGETBYIDATION_TAG = "holidayAction!getById";                       //个人请假详情
    public static String URL_FILE_HOLIDAYDONERATION_TAG = "holidayAction!list";                //审批列表
    public static String URL_FILE_HOLIDAYGONEXTATION_TAG = "holidayAction!goNext";                //审批





//    public static void toast(Context context, int s){
//        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
//    }

//    public static String postUrlresponse(String urlGo, Map<String, String> map, final String TAG) {
//        final String[] str = {null};
//        HttpUtils.doPost(urlGo, map, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                str[0] = response.body().string();
//                Log.i(TAG, "str1212: " + str[0]);
//            }
//        });
//        return str[0];
//    }

    /**
     * 打开下载好的apk
     * @param file
     * @param context
     */
    public static  void openFile(Context context,File file) {
        // TODO Auto-generated method stub
//        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 判断是否有这个app
     * packageName 为包名
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();//获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名
        //从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }



    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
