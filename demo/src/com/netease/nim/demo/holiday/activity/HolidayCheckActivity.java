package com.netease.nim.demo.holiday.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_HOLIDAYATION_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_HOLIDAYGETBYIDATION_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_TAG;

/**
 * 查看自己申请
 * Created by 78560 on 2017/10/19.
 */

public class HolidayCheckActivity extends UI{

    private static final String TAG = "wk_HolidayCheckActivity";
    @Bind(R.id.user_head)
    HeadImageView hc_head;
    @Bind(R.id.holiday_name)
    TextView holiday_name;
    @Bind(R.id.holiday_check_time)
    TextView holiday_check_time;
    @Bind(R.id.holiday_check_praydays)
    TextView holiday_check_praydays;
    @Bind(R.id.holiday_check_cause)
    TextView holiday_check_cause;
    @Bind(R.id.holiday_check_alltime)
    TextView holiday_check_alltime;
    @Bind(R.id.holiday_check_type)
    TextView holiday_check_type;

    @Bind(R.id.holiady_check_rv)
    RecyclerView holiady_check_rv;

    private ACache acache;
    private UserDTO userDTO;
    private Holiday h;
    private List<FrameRecord> frameRecords;
    private BorrowCheckBMAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holiday_check);
        ButterKnife.bind(HolidayCheckActivity.this);
        init();
    }
    private void init() {
        holiady_check_rv.setLayoutManager(new LinearLayoutManager(this));
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.holiday_content);
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
                if (PublicUtils.isNetworkAvailable(HolidayCheckActivity.this)) {
                    Toast.makeText(HolidayCheckActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HolidayCheckActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(HolidayCheckActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setText(final Holiday h) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                holiday_check_cause.setText(h.getReason());
                holiday_check_alltime.setText("请假时间:"+h.getFromDays()+"至"+h.getToDays());
                holiday_check_praydays.setText("共:"+h.getPrayDays()+"小时");
                holiday_name.setText("申请人:"+h.getPrayEr());
                long time = h.getCreateTime();
                holiday_check_time.setText("申请时间:"+StringUtil.ChangeTime(time));
                holiday_check_type.setText("请假类型:"+h.getType());
                hc_head.loadBuddyAvatar(h.getAccid());

                adapter = new BorrowCheckBMAdapter(R.layout.reimburse_approval_item,frameRecords);
                holiady_check_rv.setAdapter(adapter);
            }
        });
    }

}
