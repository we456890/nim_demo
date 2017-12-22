package com.netease.nim.demo.holiday.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.netease.nim.uikit.common.bean.Holiday;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_HOLIDAYATION_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_HOLIDAYGETBYIDATION_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_HOLIDAYGONEXTATION_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_TAG;

/**
 * 审批请假
 * Created by 78560 on 2017/10/20.
 */

public class HolidayConductorActivity extends UI{

    private static final String TAG = "wk_HolidayCA";

    @Bind(R.id.holiday_audit_cause)
    TextView holiday_audit_cause;
    @Bind(R.id.holiday_audit_alltime)
    TextView holiday_audit_alltime;
    @Bind(R.id.holiday_audit_praydays)
    TextView holiday_audit_praydays;
    @Bind(R.id.holiday_audit_time)
    TextView holiday_audit_time;
    @Bind(R.id.holiday_name)
    TextView holiday_name;
    @Bind(R.id.user_head)
    HeadImageView user_head;
    @Bind(R.id.holiday_audit_type)
    TextView holiday_audit_type;
    @Bind(R.id.hc_postil)
    EditText hc_postil;

    @Bind(R.id.hc_approval)
    RelativeLayout hc_approval;
    @Bind(R.id.hcrl_postil)
    RelativeLayout hcrl_postil;
    @Bind(R.id.hc_end)
    RelativeLayout hc_end;

    @Bind(R.id.hc_consent)
    Button hc_consent;
    @Bind(R.id.hc_reject)
    Button hc_reject;
    @Bind(R.id.holiady_audit_rv)
    RecyclerView holiady_audit_rv;

    private ACache acache;
    private UserDTO userDTO;
    private Holiday h;
    private List<FrameRecord> frameRecords;
    private BorrowCheckBMAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holiday_conductor);
        ButterKnife.bind(HolidayConductorActivity.this);
        init();
    }
    private void init() {
        acache = ACache.get(HolidayConductorActivity.this);
        holiady_audit_rv.setLayoutManager(new LinearLayoutManager(this));
        userDTO = (UserDTO) acache.getAsObject("user");
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.holiday_conductor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            String messageId = bundle.getString("messageId");
            Log.i(TAG, "onCreateMessageId: " + messageId + "");
            getIdContent(messageId);
        }
    }

    private void getIdContent(String messageId) {
        String url = PublicUtils.URL +
                URL_FILE_TAG +
                URL_FILE_HOLIDAYATION_TAG +
                URL_FILE_HOLIDAYGETBYIDATION_TAG;
        Map<String,String> m = new HashMap<>();
        m.put("id",messageId);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(HolidayConductorActivity.this)) {
                    Toast.makeText(HolidayConductorActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HolidayConductorActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Log.i(TAG, "onResponse: "+str);
                Gson gson = new Gson();
                h = gson.fromJson(str, Holiday.class);
                frameRecords=h.getRecordList();

                if(h!=null){
                    setText(h);
                }else{
//                    Toast.makeText(BorrowMoneyConductorActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setText(final Holiday h) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                holiday_audit_cause.setText(h.getReason());
                holiday_audit_alltime.setText("请假时间:"+h.getFromDays()+"至"+h.getToDays());
                holiday_audit_praydays.setText("共:"+h.getPrayDays()+"小时");
                holiday_name.setText("申请人:"+h.getPrayEr());
                long time = h.getCreateTime();
                holiday_audit_time.setText("申请时间:"+ StringUtil.ChangeTime(time));
                user_head.loadBuddyAvatar(h.getAccid());
                holiday_audit_type.setText("请假类型:"+h.getType());

                adapter = new BorrowCheckBMAdapter(R.layout.borrowmoney_check_item,frameRecords);
                holiady_audit_rv.setAdapter(adapter);
            }
        });
    }
    /**
     * 显示批注
     * @param v
     */
    @OnClick(R.id.hc_approval)
    public void hc_approval(View v) {
        hcrl_postil.setVisibility(View.VISIBLE);
        hc_approval.setVisibility(View.GONE);
    }

    /**
     *
     * 收起批注
     * @param v
     */
    @OnClick(R.id.hc_end)
    public void hc_end(View v) {
        if(hcrl_postil.getVisibility()==View.VISIBLE){
            hcrl_postil.setVisibility(View.GONE);
            hc_approval.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 同意
     * @param v
     */
    @OnClick(R.id.hc_consent)
    public void hc_consent(View v) {
        String context=hc_postil.getText().toString().trim();
            String url = PublicUtils.URL +
                    URL_FILE_TAG +
                    URL_FILE_HOLIDAYATION_TAG +
                    URL_FILE_HOLIDAYGONEXTATION_TAG;
            Map<String,String> m = new HashMap<>();
            m.put("holiday.id",h.getId());
            m.put("holiday.step",h.getStep());
            m.put("frameRecord.concatFrame",BORROW_FRAME);
            m.put("frameRecord.concatData",h.getId());
            m.put("frameRecord.processor",userDTO.getRealName());
            m.put("frameRecord.processorId",userDTO.getId());
            m.put("frameRecord.context",context);
            m.put("frameRecord.chosen","同意");
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (PublicUtils.isNetworkAvailable(HolidayConductorActivity.this)) {
                        Toast.makeText(HolidayConductorActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HolidayConductorActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String str=response.body().string();
                    Log.i(TAG, "onResponse: "+str);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(HolidayConductorActivity.this,"批复成功！",Toast.LENGTH_SHORT).show();

                            Log.i(TAG, "run: "+str);
                            finish();
                        }
                    });
                }
            });
    }

    /**
     * 拒绝
     * @param v
     */
    @OnClick(R.id.hc_reject)
    public void hc_reject(View v) {
        String context=hc_postil.getText().toString().trim();
        if(!TextUtils.isEmpty(context)){
            String url = PublicUtils.URL +
                    URL_FILE_TAG +
                    URL_FILE_HOLIDAYATION_TAG +
                    URL_FILE_HOLIDAYGONEXTATION_TAG;
            Map<String,String> m = new HashMap<>();
            m.put("holiday.id",h.getId());
            m.put("holiday.step",h.getStep());
            m.put("frameRecord.concatFrame",BORROW_FRAME);
            m.put("frameRecord.concatData",h.getId());
            m.put("frameRecord.processor",userDTO.getRealName());
            m.put("frameRecord.processorId",userDTO.getId());
            m.put("frameRecord.context",context);
            m.put("frameRecord.chosen","拒绝");
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (PublicUtils.isNetworkAvailable(HolidayConductorActivity.this)) {
                        Toast.makeText(HolidayConductorActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HolidayConductorActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String str=response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(HolidayConductorActivity.this,"批复成功！",Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "run: "+str);
                            finish();
                        }
                    });
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HolidayConductorActivity.this,"批注不能为空",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
