package com.netease.nim.demo.reimburse.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.netease.nim.uikit.common.bean.FrameRecord;
import com.netease.nim.uikit.common.bean.Reimburse;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.netease.nim.demo.common.util.PublicUtils.APKNAME;
import static com.netease.nim.demo.common.util.PublicUtils.FILEDIR;
import static com.netease.nim.demo.common.util.PublicUtils.FILENAME;
import static com.netease.nim.demo.common.util.PublicUtils.ONE;
import static com.netease.nim.demo.common.util.PublicUtils.PACKNAME;
import static com.netease.nim.demo.common.util.PublicUtils.PACKURL;
import static com.netease.nim.demo.common.util.PublicUtils.TWO;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_UPDATE_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_MOBILECOMMON_PDF_TAG;

/**
 * 报销单详情
 * Created by 78560 on 2017/11/2.
 */

public class ReimburseMoneyCheckActivity extends UI {
    private static String TAG = "wk_ReimbureseMoneyCheck";
    @Bind(R.id.reimburscheck_account)
    TextView reimburscheck_account;
    @Bind(R.id.reimburscheck_money)
    TextView reimburscheck_money;
    @Bind(R.id.reimburscheck_name)
    TextView reimburscheck_name;
    @Bind(R.id.reimburscheck_num)
    TextView reimburscheck_num;
    @Bind(R.id.bill_content)
    RelativeLayout bill_content;
    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

//    @Bind(R.id.user_head)
//    HeadImageView user_head;
//    @Bind(R.id.borrow_approvename)
//    TextView borrow_approvename;

    private ACache acache;
    private UserDTO userDTO;
    private Reimburse rl;
    private List<FrameRecord> list;
    private BorrowCheckBMAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reimburese_check);
        ButterKnife.bind(ReimburseMoneyCheckActivity.this);
        initView();
    }

    private void initView() {
        acache = ACache.get(ReimburseMoneyCheckActivity.this);
        recycler_view.setLayoutManager(new LinearLayoutManager(ReimburseMoneyCheckActivity.this));
        ImageView print=(ImageView)findViewById(R.id.print);
        print.setVisibility(View.VISIBLE);
        print.setOnClickListener(printoc);
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.reimburse_expenses);
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null) {
            String reimburseId = bundle.getString("reimburseId");
            Log.i(TAG, "initView: " + reimburseId);
            getIdByMessage(reimburseId);
            reimburscheck_account.setTag(reimburseId);
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ONE:
                    Toast.makeText(ReimburseMoneyCheckActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setPackage("com.dynamixsoftware.printershare");
                    Uri data_uri = Uri.parse("file://"+FILEDIR+FILENAME);
                    String data_type = "application/pdf";
                    i.setDataAndType(data_uri, data_type);
                    startActivity(i);
                    break;
                case TWO:
                    Toast.makeText(ReimburseMoneyCheckActivity.this,"请安装打印插件",Toast.LENGTH_SHORT).show();
                    PublicUtils.openFile(ReimburseMoneyCheckActivity.this,new File(FILEDIR+APKNAME));
                    break;
            }
        }
    };


    /**
     * 打印事件
     */
    View.OnClickListener printoc=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(PublicUtils.isAvilible(ReimburseMoneyCheckActivity.this,PublicUtils.PACKNAME)){
                String id=reimburscheck_account.getTag().toString();
                downfilebill(id);
            }else{
                Toast.makeText(ReimburseMoneyCheckActivity.this,"正在下载打印插件",Toast.LENGTH_SHORT).show();
                downapk();
            }
        }
    };

    /**
     * 获取数据
     * @param reimburseId
     */
    private void getIdByMessage(String reimburseId) {
        String url = PublicUtils.URL +
                URL_FILE_TAG +
                PublicUtils.URL_FILE_REIMBURSE_TAG +
                PublicUtils.URL_FILE_REIMBURSEGETBYIDACTION_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("id", reimburseId);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (PublicUtils.isNetworkAvailable(ReimburseMoneyCheckActivity.this)) {
                            Toast.makeText(ReimburseMoneyCheckActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReimburseMoneyCheckActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Log.i(TAG, "onResponse: "+str);
                Gson gson = new Gson();
                rl = gson.fromJson(str, Reimburse.class);
                list=rl.getRecordList();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        double billmoney = Double.parseDouble(rl.getRemiurseHole());
                        reimburscheck_account.setText(rl.getRemiurserJob());
                        reimburscheck_money.setText("￥"+billmoney);
                        reimburscheck_name.setText(rl.getReimurser().toString());
                        reimburscheck_num.setText(rl.getDocs().toString()+"张");
                        adapter = new BorrowCheckBMAdapter(R.layout.reimburse_approval_item,list);
                        recycler_view.setAdapter(adapter);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 下载报销单
     * @param id
     */
    private void downfilebill(String id){
        String url =PublicUtils.URL
                +URL_FILE_TAG
                +URL_FILE_UPDATE_TAG
                +URL_MOBILECOMMON_PDF_TAG;
//        String url = "http://192.168.1.100:8080/manual_pure/mobileCommon/mobileCommon!reimbursePDF";
        Map<String,String> m =new HashMap<>();
        m.put("id",id);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(FILEDIR, FILENAME);
                    fos = new FileOutputStream(file);
                    //---增加的代码---
                    //计算进度
                    long totalSize = response.body().contentLength();
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        sum += len;
                        //progress就是进度值
                        int progress = (int) (sum * 1.0f/totalSize * 100);
                        //---增加的代码---
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    Message message = new Message();
                    message.what = PublicUtils.ONE;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
        });
//            HttpUtils.downFile(url, FILE_PATH, "Test1.pdf");
    }
    /**
     * 下载printshare
     */
    private void downapk(){
        HttpUtils.doGet(PACKURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(FILEDIR, APKNAME);
                    fos = new FileOutputStream(file);
                    //---增加的代码---
                    //计算进度
                    long totalSize = response.body().contentLength();
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        sum += len;
                        //progress就是进度值
                        int progress = (int) (sum * 1.0f/totalSize * 100);
                        //---增加的代码---
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    Message message = new Message();
                    message.what = PublicUtils.TWO;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
        });
    }

    @OnClick(R.id.bill_content)
    public void bill_content(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("billId",reimburscheck_account.getTag().toString());
        intent.putExtras(bundle);
        intent.setClass(ReimburseMoneyCheckActivity.this, ReimburseBillAllCheckActivity.class);
        startActivity(intent);
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
