package com.netease.nim.demo.session.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nim.demo.R;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by hzxuwen on 2016/12/14.
 */

public class FileDownloadActivity extends UI {
    private static final String INTENT_EXTRA_DATA = "INTENT_EXTRA_DATA";

    private TextView fileNameText;
    private Button fileDownloadBtn;
    private Button openDownloadBtn;
    private IMMessage message;

    public static void start(Context context, IMMessage message) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_EXTRA_DATA, message);
        intent.setClass(context, FileDownloadActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_download_activity);

        onParseIntent();
        findViews();

        updateUI();
        registerObservers(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerObservers(false);
    }

    private void onParseIntent() {
        this.message = (IMMessage) getIntent().getSerializableExtra(INTENT_EXTRA_DATA);
    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    private void findViews() {
        fileNameText = findView(R.id.file_name);
        fileDownloadBtn = findView(R.id.download_btn);
        openDownloadBtn = findView(R.id.open_file_btn);

        fileDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOriginDataHasDownloaded(message)) {
                    return;
                }
                downloadFile();

            }
        });

        openDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("wk_file", message+"1111");
//                Log.d("wk_file", message.getAttachment()+"2222");
                String path = String.valueOf(fileNameText.getTag());
//                Log.i("wk_file", path);
                File file = new File(path);
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                //设置intent的Action属性
                intent.setAction(Intent.ACTION_VIEW);
                //获取文件file的MIME类型
                String type = getMIMEType(file);
                Log.i("wk_file", "type: "+type);
                //设置intent的data和Type属性。
                intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
//                //跳转
                startActivity(intent);
            }
        });

    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private final String[][] MIME_MapTable = {
//{后缀名，MIME类型}
//            {".3gp", "video/3gpp"},
//            {".apk", "application/vnd.android.package-archive"},
//            {".asf", "video/x-ms-asf"},
//            {".avi", "video/x-msvideo"},
//            {".bin", "application/octet-stream"},
//            {".bmp", "image/bmp"},
//            {".c", "text/plain"},
//            {".class", "application/octet-stream"},
//            {".conf", "text/plain"},
//            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
//            {".exe", "application/octet-stream"},
//            {".gif", "image/gif"},
//            {".gtar", "application/x-gtar"},
//            {".gz", "application/x-gzip"},
//            {".h", "text/plain"},
//            {".htm", "text/html"},
//            {".html", "text/html"},
//            {".jar", "application/java-archive"},
//            {".java", "text/plain"},
//            {".jpeg", "image/jpeg"},
//            {".jpg", "image/jpeg"},
//            {".js", "application/x-JavaScript"},
//            {".log", "text/plain"},
//            {".m3u", "audio/x-mpegurl"},
//            {".m4a", "audio/mp4a-latm"},
//            {".m4b", "audio/mp4a-latm"},
//            {".m4p", "audio/mp4a-latm"},
//            {".m4u", "video/vnd.mpegurl"},
//            {".m4v", "video/x-m4v"},
//            {".mov", "video/quicktime"},
//            {".mp2", "audio/x-mpeg"},
//            {".mp3", "audio/x-mpeg"},
//            {".mp4", "video/mp4"},
//            {".mpc", "application/vnd.mpohun.certificate"},
//            {".mpe", "video/mpeg"},
//            {".mpeg", "video/mpeg"},
//            {".mpg", "video/mpeg"},
//            {".mpg4", "video/mp4"},
//            {".mpga", "audio/mpeg"},
//            {".msg", "application/vnd.ms-outlook"},
//            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
//            {".png", "image/png"},
//            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
//            {".prop", "text/plain"},
//            {".rc", "text/plain"},
//            {".rmvb", "audio/x-pn-realaudio"},
//            {".rtf", "application/rtf"},
//            {".sh", "text/plain"},
//            {".tar", "application/x-tar"},
//            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
//            {".wav", "audio/x-wav"},
//            {".wma", "audio/x-ms-wma"},
//            {".wmv", "audio/x-ms-wmv"},
//            {".wps", "application/vnd.ms-works"},
//            {".xml", "text/plain"},
//            {".z", "application/x-compress"},
//            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

    private void updateUI() {
        FileAttachment attachment = (FileAttachment) message.getAttachment();
        if (attachment != null) {
            fileNameText.setText(attachment.getDisplayName());
//            Log.i("wk_file", "md5: "+attachment.getMd5());
        }

        if (isOriginDataHasDownloaded(message)) {
            onDownloadSuccess();
        } else {
            onDownloadFailed();
        }
    }

    private boolean isOriginDataHasDownloaded(final IMMessage message) {
        if (!TextUtils.isEmpty(((FileAttachment) message.getAttachment()).getPath())) {
            return true;
        }

        return false;
    }

    private void downloadFile() {
        DialogMaker.showProgressDialog(this, "下载中...");
        NIMClient.getService(MsgService.class).downloadAttachment(message, false);
    }

    /**
     * ********************************* 下载 ****************************************
     */

    private void registerObservers(boolean register) {
        NIMClient.getService(MsgServiceObserve.class).observeMsgStatus(statusObserver, register);
    }

    private Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage msg) {
            if (!msg.isTheSame(message) || isDestroyedCompatible()) {
                return;
            }

            if (msg.getAttachStatus() == AttachStatusEnum.transferred && isOriginDataHasDownloaded(msg)) {
                DialogMaker.dismissProgressDialog();
                onDownloadSuccess();
            } else if (msg.getAttachStatus() == AttachStatusEnum.fail) {
                DialogMaker.dismissProgressDialog();
                Toast.makeText(FileDownloadActivity.this, "download failed", Toast.LENGTH_SHORT).show();
                onDownloadFailed();

            }
        }
    };
    private void copyopen(){
        FileAttachment attachment = (FileAttachment) message.getAttachment();
        String path=attachment.getPath();
        int m=path.lastIndexOf("/");
        String s=path.substring(0,m);
        String pathname=s+"/"+attachment.getDisplayName();
        Log.i("wk_file", "path: "+s);
        copyFile(attachment.getPath(),pathname);
        fileNameText.setTag(pathname);
    }
    private void onDownloadSuccess() {
        fileDownloadBtn.setVisibility(View.GONE);
        openDownloadBtn.setVisibility(View.VISIBLE);
        copyopen();
    }


    private void onDownloadFailed() {
        fileDownloadBtn.setText("下载文件");
        fileDownloadBtn.setEnabled(true);
        fileDownloadBtn.setBackgroundResource(R.drawable.nim_team_create_btn_selector);

    }
}
