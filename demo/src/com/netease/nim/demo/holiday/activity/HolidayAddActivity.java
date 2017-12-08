package com.netease.nim.demo.holiday.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nim.demo.R;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.demo.reporting.widgets.CustomDatePicker;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.bean.FrameControl;
import com.netease.nim.uikit.common.bean.OperateInfo;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_HOLIDAYADDATION_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_HOLIDAYATION_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_TAG;

/**
 * 借款申请
 * Created by 78560 on 2017/10/10.
 */

public class HolidayAddActivity extends UI {
    private static String TAG = "wk_HolidayAddActivity";

    @Bind(R.id.user_head)
    HeadImageView user_head;
    @Bind(R.id.holiday_add_name)
    TextView holiday_add_name;
    @Bind(R.id.holiday_add_fromdays)
    TextView holiday_add_fromdays;
    @Bind(R.id.holiday_add_todays)
    TextView holiday_add_todays;
    @Bind(R.id.holiday_add_time)
    EditText holiday_add_time;
    @Bind(R.id.holiday_add_type)
    TextView holiday_add_type;
    @Bind(R.id.holiday_add_reason)
    EditText holiday_add_reason;
    @Bind(R.id.holiday_add_next)
    Button holiday_add_next;

    private ACache acache;
    private UserDTO userDTO;
    private FrameControl frameC;
    private CustomDatePicker customfDatePicker;
    private CustomDatePicker customtDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holiday_add);
        ButterKnife.bind(HolidayAddActivity.this);
        initView();
        initFromDatePicker();
        initTodayDatePicker();
//        getUser_head();
    }

    private void initView() {
        acache = ACache.get(HolidayAddActivity.this);
        ToolBarOptions options = new ToolBarOptions();
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.borrow_apply);
        userDTO = (UserDTO) acache.getAsObject("user");
        holiday_add_name.setText(userDTO.getRealName());
        holiday_add_fromdays.setText(StringUtil.getNewTime());
        holiday_add_todays.setText(StringUtil.getNewTime());
    }

    /**
     * 时间选择器
     */
    private void initFromDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        holiday_add_fromdays.setText(now.split(" ")[0]);
        holiday_add_fromdays.setText(now);

        customfDatePicker = new CustomDatePicker(HolidayAddActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(final String time) { // 回调接口，获得选中的时间
                holiday_add_fromdays.setText(time.split(" ")[0]);
            }
        }, now, "2020-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customfDatePicker.showSpecificTime(false); // 不显示时和分
        customfDatePicker.setIsLoop(false); // 不允许循环滚动
    }
    /**
     * 时间选择器
     */
    private void initTodayDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        holiday_add_todays.setText(now.split(" ")[0]);
        holiday_add_todays.setText(now);

        customtDatePicker = new CustomDatePicker(HolidayAddActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(final String time) { // 回调接口，获得选中的时间
                holiday_add_todays.setText(time.split(" ")[0]);
            }
        },now, "2020-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customtDatePicker.showSpecificTime(false); // 不显示时和分
        customtDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    @OnClick(R.id.holiday_add_fromdays)
    public void holiday_add_fromdays(View view) {
        customfDatePicker.show(holiday_add_fromdays.getText().toString());
    }
    @OnClick(R.id.holiday_add_todays)
    public void holiday_add_todays(View view) {
        customtDatePicker.show(holiday_add_fromdays.getText().toString());
    }


    @OnClick(R.id.holiday_add_next)
    public void borrow_submit(View v) {
        String reason=holiday_add_reason.getText().toString().trim();
        String time=holiday_add_time.getText().toString().trim();
        String type=holiday_add_type.getText().toString().trim();
        if (isEmpty(reason,time,type)) {
            String url = PublicUtils.URL +
                    URL_FILE_TAG +
                    URL_FILE_HOLIDAYATION_TAG +
                    URL_FILE_HOLIDAYADDATION_TAG;
            Map<String, String> m = new HashMap<>();
            m.put("userId", userDTO.getId());
            m.put("holiday.prayEr", userDTO.getRealName());
            m.put("holiday.prayErId", userDTO.getId());
            m.put("holiday.prayDays", time);
            m.put("holiday.reason", reason);
            m.put("holiday.fromDays", holiday_add_fromdays.getText().toString().trim());
            m.put("holiday.toDays", holiday_add_todays.getText().toString().trim());
            m.put("holiday.type", holiday_add_type.getText().toString().trim());
            HttpUtils.doPost(url, m, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (PublicUtils.isNetworkAvailable(HolidayAddActivity.this)) {
                        Toast.makeText(HolidayAddActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HolidayAddActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    final OperateInfo oi = gson.fromJson(str, OperateInfo.class);
                    if (oi.isOperateSuccess()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(HolidayAddActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(HolidayAddActivity.this, oi.getOperateMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }
            });
        }else{
            Toast.makeText(HolidayAddActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUser_head() {
        String url = PublicUtils.URL +
                URL_FILE_TAG +
                PublicUtils.URL_FILE_FRAMESET_TAG +
                PublicUtils.URL_FILE_FRAMESETACTION_TAG;
        Map<String, String> m = new HashMap<>();
        m.put("reId", BORROW_FRAME);
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (PublicUtils.isNetworkAvailable(HolidayAddActivity.this)) {
                    Toast.makeText(HolidayAddActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HolidayAddActivity.this, "请求失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                Gson gson = new Gson();
                frameC = gson.fromJson(str, FrameControl.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        borrow_name.setText(userDTO.getRealName());
//                        borrow_time.setText(StringUtil.getNewTime());
//                        user_head.loadBuddyAvatar(frameC.getDesinerAccount());
//                        borrow_approvename.setText(frameC.getDesinerName());
                    }
                });
            }
        });
    }

    /**
     * 非空判断
     */
    private boolean isEmpty(String name,String cause,String money){
        boolean empty;
        if(StringUtil.isEmpty(name)||StringUtil.isEmpty(cause)||StringUtil.isEmpty(money)){
            empty=false;
        }else {
            empty=true;
        }
        return empty;
    }
}
