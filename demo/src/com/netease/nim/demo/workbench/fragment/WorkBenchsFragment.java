package com.netease.nim.demo.workbench.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.borrow.activity.BorrowMoneyActivity;
import com.netease.nim.demo.borrow.activity.BorrowMoneyConductorActivity;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.demo.holiday.activity.HolidayActivity;
import com.netease.nim.demo.holiday.activity.HolidayConductorActivity;
import com.netease.nim.demo.reimburse.activity.ReimburseMoneyActivity;
import com.netease.nim.demo.reimburse.activity.ReimburseMoneyApprovalActivity;
import com.netease.nim.demo.reporting.activity.ReportingBossActivity;
import com.netease.nim.demo.reporting.activity.ReportingBossCheckActivity;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.bean.Borrow;
import com.netease.nim.uikit.common.bean.Holiday;
import com.netease.nim.uikit.common.bean.Reimburse;
import com.netease.nim.uikit.common.bean.Reporting;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.common.util.string.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_WORKSTATION_GETWORK_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_WORKSTATION_TAG;

/**
 * 工作台显示页面
 * <p>
 * Created by wangkai on 2017/09/26.
 */
public class WorkBenchsFragment extends TFragment {
    private static String TAG = "wk_WorkBenchFragment";
    /**
     * 汇报
     */
    @Bind(R.id.work_reporting)
    View work_reporting;
    @Bind(R.id.work_reporting_num)
    TextView work_reporting_num;
    @Bind(R.id.work_reporting_much)
    TextView work_reporting_much;
    @Bind(R.id.work_reporting_one)
    View work_reporting_one;
    @Bind(R.id.work_reporting_title1)
    TextView work_reporting_title1;
    @Bind(R.id.work_reporting_subtitle1)
    TextView work_reporting_subtitle1;
    @Bind(R.id.work_reporting_message1)
    TextView work_reporting_message1;
    @Bind(R.id.work_reporting_name1)
    TextView work_reporting_name1;
    @Bind(R.id.work_reporting_time1)
    TextView work_reporting_time1;
    @Bind(R.id.work_reporting_two)
    View work_reporting_two;
    @Bind(R.id.work_reporting_title2)
    TextView work_reporting_title2;
    @Bind(R.id.work_reporting_subtitle2)
    TextView work_reporting_subtitle2;
    @Bind(R.id.work_reporting_message2)
    TextView work_reporting_message2;
    @Bind(R.id.work_reporting_name2)
    TextView work_reporting_name2;
    @Bind(R.id.work_reporting_time2)
    TextView work_reporting_time2;
    /**
     * 报销
     */
    @Bind(R.id.work_reimbur)
    View work_reimburse;
    @Bind(R.id.work_reimburse_num)
    TextView work_reimburse_num;
    @Bind(R.id.work_reimburse_much)
    TextView work_reimburse_much;
    @Bind(R.id.work_reimburse_one)
    View work_reimburse_one;
    @Bind(R.id.work_reimburse_title1)
    TextView work_reimburse_title1;
    @Bind(R.id.work_reimburse_subtitle1)
    TextView work_reimburse_subtitle1;
    @Bind(R.id.work_reimburse_message1)
    TextView work_reimburse_message1;
    @Bind(R.id.work_reimburse_name1)
    TextView work_reimburse_name1;
    @Bind(R.id.work_reimburse_time1)
    TextView work_reimburse_time1;
    @Bind(R.id.work_reimburse_two)
    View work_reimburse_two;
    @Bind(R.id.work_reimburse_title2)
    TextView work_reimburse_title2;
    @Bind(R.id.work_reimburse_subtitle2)
    TextView work_reimburse_subtitle2;
    @Bind(R.id.work_reimburse_message2)
    TextView work_reimburse_message2;
    @Bind(R.id.work_reimburse_name2)
    TextView work_reimburse_name2;
    @Bind(R.id.work_reimburse_time2)
    TextView work_reimburse_time2;
    /**
     * 借款
     */
    @Bind(R.id.work_borrow)
    View work_borrow;
    @Bind(R.id.work_borrow_num)
    TextView work_borrow_num;
    @Bind(R.id.work_borrow_much)
    TextView work_borrow_much;
    @Bind(R.id.work_borrow_one)
    View work_borrow_one;
    @Bind(R.id.work_borrow_title1)
    TextView work_borrow_title1;
    @Bind(R.id.work_borrow_subtitle1)
    TextView work_borrow_subtitle1;
    @Bind(R.id.work_borrow_message1)
    TextView work_borrow_message1;
    @Bind(R.id.work_borrow_name1)
    TextView work_borrow_name1;
    @Bind(R.id.work_borrow_time1)
    TextView work_borrow_time1;
    @Bind(R.id.work_borrow_two)
    View work_borrow_two;
    @Bind(R.id.work_borrow_title2)
    TextView work_borrow_title2;
    @Bind(R.id.work_borrow_subtitle2)
    TextView work_borrow_subtitle2;
    @Bind(R.id.work_borrow_message2)
    TextView work_borrow_message2;
    @Bind(R.id.work_borrow_name2)
    TextView work_borrow_name2;
    @Bind(R.id.work_borrow_time2)
    TextView work_borrow_time2;

    /**
     * 请假
     */
    @Bind(R.id.work_holiday)
    View work_holiday;
    @Bind(R.id.work_holiday_num)
    TextView work_holiday_num;
    @Bind(R.id.work_holiday_much)
    TextView work_holiday_much;
    @Bind(R.id.work_holiday_one)
    View work_holiday_one;
    @Bind(R.id.work_holiday_title1)
    TextView work_holiday_title1;
    @Bind(R.id.work_holiday_subtitle1)
    TextView work_holiday_subtitle1;
    @Bind(R.id.work_holiday_message1)
    TextView work_holiday_message1;
    @Bind(R.id.work_holiday_name1)
    TextView work_holiday_name1;
    @Bind(R.id.work_holiday_time1)
    TextView work_holiday_time1;
    @Bind(R.id.work_holiday_two)
    View work_holiday_two;
    @Bind(R.id.work_holiday_title2)
    TextView work_holiday_title2;
    @Bind(R.id.work_holiday_subtitle2)
    TextView work_holiday_subtitle2;
    @Bind(R.id.work_holiday_message2)
    TextView work_holiday_message2;
    @Bind(R.id.work_holiday_name2)
    TextView work_holiday_name2;
    @Bind(R.id.work_holiday_time2)
    TextView work_holiday_time2;
    private ACache acache;
    private UserDTO userDTO;
    //    private static final String TAG = ChatRoomsFragment.class.getSimpleName();
    private TextView work_time;
    private TextView work_name;
//    private View v;
    LinearLayout rootLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.work_benchs, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
    }

    private void findViews() {
//        acache = ACache.get(getActivity());
//        userDTO = (UserDTO) acache.getAsObject("user");
//        work_time=findView(R.id.work_time);
//        work_name=findView(R.id.work_name);
////        lvlv=findView(R.id.lvlv);
////        TestAdapter adapter = new TestAdapter(getContext(),null);
////        lvlv.setAdapter(adapter);
//        HeadImageView headImageView=findView(R.id.user_head);
//        headImageView.loadBuddyAvatar(userDTO.getAccid());
    }

    private void getMessage(){
        acache = ACache.get(getActivity());
        userDTO = (UserDTO) acache.getAsObject("user");
        String url= PublicUtils.URL+
                URL_FILE_TAG+
                URL_FILE_WORKSTATION_TAG+
                URL_FILE_WORKSTATION_GETWORK_TAG;
//                "http://118.31.184.230:8000/gt_beta/workStation/workStationAction!getWorkStationDataByUser?id=c1c6a1663d1c43408";
        Map<String,String> m = new HashMap<>();
        m.put("id",userDTO.getId());
        HttpUtils.doPost(url, m, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Log.i("Test", "onResponse: "+str);
                getGson(str);
            }
        });
    }

    private void getGson(String str) {
        JSONArray array = null;
        try {
            array = new JSONArray(str);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = (JSONObject) array.get(i);
                String modle = object.getString("modle");
                final int number = object.getInt("number");
                String showData = object.getString("showData");
                switch (modle){
                    case "reporting":
                        if (number > 0) {
                            Gson gson = new Gson();
                            final List<Reporting> r = gson.fromJson(showData, new TypeToken<List<Reporting>>() {
                            }.getType());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setWork_reporting(r,number);
                                }
                            });
                        }else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    work_reporting.setVisibility(View.GONE);
                                }
                            });
                        }
                        break;
                    case "reimburse":
                        if (number > 0) {
                            Gson gson = new Gson();
                            final List<Reimburse> r = gson.fromJson(showData, new TypeToken<List<Reimburse>>() {
                            }.getType());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setWork_reimburse(r,number);
                                }
                            });
                        }else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    work_reimburse.setVisibility(View.GONE);
                                }
                            });
                        }
                        break;
                    case "borrow":
                        if (number > 0) {
                            Gson gson = new Gson();
                            final List<Borrow> b = gson.fromJson(showData, new TypeToken<List<Borrow>>() {
                            }.getType());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setWork_borrow(b,number);
                                }
                            });
//                            Log.i("Test4", "onResponse: " + b.get(0).getAmount());
                        }else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    work_borrow.setVisibility(View.GONE);
                                }
                            });
                        }
                        break;
                    case "holiday":
                        if (number > 0) {
                            Gson gson = new Gson();
                            final List<Holiday> h = gson.fromJson(showData, new TypeToken<List<Holiday>>() {
                            }.getType());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setWork_holiday(h,number);
                                }
                            });
//                            Log.i("Test4", "onResponse: " + b.get(0).getAmount());
                        }else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    work_holiday.setVisibility(View.GONE);
                                }
                            });
                        }
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 汇报
     * @param r
     * @param number
     */
    private void setWork_reporting(List<Reporting> r,int number){
        work_reporting_num.setText(number+"条未读");
        work_reporting_one.setTag(r.get(0).getId().toString());
        work_reporting_title1.setText(r.get(0).getAutoTitle().toString());
        work_reporting_subtitle1.setText(r.get(0).getTitle().toString());
        work_reporting_message1.setText(r.get(0).getRecontext()+"");
        work_reporting_name1.setText(r.get(0).getReporter()+"");
        work_reporting_time1.setText(r.get(0).getReportTime()+"");
        if(number>1){
            work_reporting_two.setTag(r.get(1).getId().toString());
            work_reporting_title2.setText(r.get(1).getAutoTitle().toString());
            work_reporting_subtitle2.setText(r.get(1).getTitle().toString());
            work_reporting_message2.setText(r.get(1).getRecontext()+"");
            work_reporting_name2.setText(r.get(1).getReporter()+"");
            work_reporting_time2.setText(r.get(1).getReportTime()+"");
        }else{
            work_reporting_two.setVisibility(View.GONE);
        }
    }

    /**
     * 报销
     * @param r
     * @param number
     */
    public void setWork_reimburse(List<Reimburse> r,int number) {
        work_reimburse_num.setText(number+"条未读");
        work_reimburse_one.setTag(r.get(0).getId().toString());
        work_reimburse_title1.setText("报销单号:"+r.get(0).getRemiurserJob().toString());
        work_reimburse_subtitle1.setText("总金额:￥"+r.get(0).getRemiurseHole().toString());
        work_reimburse_message1.setText("发票数:"+r.get(0).getDocs());
        work_reimburse_name1.setText("报销人:"+r.get(0).getReimurser().toString());
        work_reimburse_time1.setText("报销时间:"+r.get(0).getReimurseTime()+"");
        if(number>1){
            work_reimburse_two.setTag(r.get(1).getId().toString());
            work_reimburse_title2.setText("报销单号:"+r.get(1).getRemiurserJob().toString());
            work_reimburse_subtitle2.setText("总金额:￥"+r.get(1).getRemiurseHole().toString());
            work_reimburse_message2.setText("发票数:"+r.get(1).getDocs());
            work_reimburse_name2.setText("报销人:"+r.get(1).getReimurser().toString());
            work_reimburse_time2.setText("报销时间:"+r.get(1).getReimurseTime()+"");
        }else{
            work_reimburse_two.setVisibility(View.GONE);
        }
    }

    /**
     * 借款
     * @param b
     * @param number
     */
    public void setWork_borrow(List<Borrow> b,int number) {
        work_borrow_num.setText(number+"条未读");
        work_borrow_one.setTag(b.get(0).getId().toString());
        work_borrow_title1.setText("GT201710300001");
        work_borrow_subtitle1.setText("借款金额:￥"+b.get(0).getAmount().toString());
        work_borrow_message1.setText("借款事由:"+b.get(0).getReason());
        work_borrow_name1.setText("借款人:"+b.get(0).getBorrower().toString());
        work_borrow_time1.setText("借款时间:"+b.get(0).getPrayTime()+"");
        if(number>1){
            work_borrow_two.setTag(b.get(1).getId().toString());
            work_borrow_title2.setText("GT201710300001");
            work_borrow_subtitle2.setText("借款金额:￥"+b.get(1).getAmount().toString());
            work_borrow_message2.setText("借款事由:"+b.get(1).getReason());
            work_borrow_name2.setText("借款人:"+b.get(1).getBorrower().toString());
            work_borrow_time2.setText("借款时间:"+b.get(1).getPrayTime()+"");
        }else{
            work_borrow_two.setVisibility(View.GONE);
        }
    }

    /**
     * 请假
     * @param h
     * @param number
     */
    public void setWork_holiday(List<Holiday> h,int number) {
        work_holiday_num.setText(number+"条未读");
        work_holiday_one.setTag(h.get(0).getId().toString());
        work_holiday_title1.setText(h.get(0).getFromDays()+"至"+h.get(0).getToDays());
        work_holiday_subtitle1.setText("共:"+h.get(0).getPrayDays()+"小时");
        work_holiday_message1.setText("请假事由:"+h.get(0).getReason());
        work_holiday_name1.setText("申请人:"+h.get(0).getPrayEr());
        long time1 = h.get(0).getCreateTime();
        work_holiday_time1.setText("申请时间:"+ StringUtil.ChangeTime(time1));
        if(number>1){
            work_holiday_two.setTag(h.get(1).getId().toString());
            work_holiday_title2.setText(h.get(1).getFromDays()+"至"+h.get(0).getToDays());
            work_holiday_subtitle2.setText("共:"+h.get(1).getPrayDays()+"小时");
            work_holiday_message2.setText("请假事由:"+h.get(1).getReason());
            work_holiday_name2.setText("申请人:"+h.get(1).getPrayEr());
            long time2 = h.get(1).getCreateTime();
            work_holiday_time2.setText("申请时间:"+ StringUtil.ChangeTime(time1));
        }else{
            work_holiday_two.setVisibility(View.GONE);
        }
    }
    @OnClick(R.id.work_reporting_one)
    public void work_reporting_one(View v){
        String id = work_reporting_one.getTag().toString();
        reportingIntent(id);
    }
    @OnClick(R.id.work_reporting_two)
    public void work_reporting_two(View v){
        String id = work_reporting_two.getTag().toString();
        reportingIntent(id);
    }
    //汇报跳转
    private void reportingIntent(String id){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("messageId", id + "");
        intent.putExtras(bundle);
        intent.setClass(getActivity(), ReportingBossCheckActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.work_reimburse_one)
    public void work_reimburse_one(View v){
        String id = work_reimburse_one.getTag().toString();
        Log.i(TAG, "work_borrow_one: "+id);
        reimburseIntent(id);
    }
    @OnClick(R.id.work_reimburse_two)
    public void work_reimburse_two(View v){
        String id = work_reimburse_two.getTag().toString();
        Log.i(TAG, "work_borrow_one: "+id);
        reimburseIntent(id);
    }
    //报销跳转
    private void reimburseIntent(String id){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("reimburseId",id );
        intent.putExtras(bundle);
        intent.setClass(getActivity(), ReimburseMoneyApprovalActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.work_borrow_one)
    public void work_borrow_one(View v){
        String id = work_borrow_one.getTag().toString();
        borrowIntent(id);
    }
    @OnClick(R.id.work_borrow_two)
    public void work_borrow_two(View v){
        String id = work_borrow_two.getTag().toString();
        borrowIntent(id);
    }
    //借款跳转
    private void borrowIntent(String id){
        Intent intent  = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("messageId", id + "");
        intent.putExtras(bundle);
        intent.setClass(getActivity(), BorrowMoneyConductorActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.work_holiday_one)
    public void work_holiday_one(View v){
        String id = work_holiday_one.getTag().toString();
        holidayIntent(id);
    }
    @OnClick(R.id.work_holiday_two)
    public void work_holiday_two(View v){
        String id = work_holiday_two.getTag().toString();
        holidayIntent(id);
    }
    //请假跳转
    private void holidayIntent(String id){
        Intent intent  = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("messageId", id + "");
        intent.putExtras(bundle);
        intent.setClass(getActivity(), HolidayConductorActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.work_reporting_much)
    public void work_reporting_much(View v){
        Intent intent = new Intent(getActivity(), ReportingBossActivity.class);
        getActivity().startActivity(intent);
    }
    @OnClick(R.id.work_reimburse_much)
    public void work_reimburse_much(View v){
        Intent intent = new Intent(getActivity(), ReimburseMoneyActivity.class);
        getActivity().startActivity(intent);
    }
    @OnClick(R.id.work_borrow_much)
    public void work_borrow_much(View v){
        Intent intent = new Intent(getActivity(), BorrowMoneyActivity.class);
        getActivity().startActivity(intent);
    }
    @OnClick(R.id.work_holiday_much)
    public void work_holiday_much(View v){
        Intent intent = new Intent(getActivity(), HolidayActivity.class);
        getActivity().startActivity(intent);
    }
    //
    public void onCurrent() {
//        fetchData();
    }
//    private void remove(View v){
//        rootLayout.removeView(v);
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        getMessage();
        Log.i(TAG, "onResume: ");
//        userDTO = (UserDTO) acache.getAsObject("user");
//        String name = userDTO.getRealName();
//        work_name.setText(name);
//        work_time.setText(StringUtil.getNewTime());
//        getAcache();
    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View convertView = inflater.inflate(R.layout.dialog_progress,null);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView message = (TextView) convertView.findViewById(R.id.message);
        title.setText("我是一个普通Dialog");
        message.setText("你要点击哪一个按钮呢?");
        normalDialog.setView(convertView);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    /**
     //     * 创建待办事项模块
     //     */
//    private void CreateMoudle(View v,String modle,String num,int color,List list){
//        rootLayout =(LinearLayout)v.findViewById(R.id.work_subfield);
//        v = getActivity().getLayoutInflater().inflate(R.layout.work_reporting_item, null);
//        TextView tvtitle = (TextView) v.findViewById(R.id.work2);
//        TextView tvnum = (TextView) v.findViewById(R.id.work_num);
//        View ibv = (View) v.findViewById(R.id.work1);
//        RecyclerView rv = (RecyclerView) v.findViewById(R.id.work_rv);
//        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        switch (modle) {
//            case "reporting":
//                ibv.setBackgroundResource(R.mipmap.work_reportingicon);
//                tvtitle.setText("汇报");
//                tvnum.setText(num + "条未处理");
//                setMessage(v, color);
//                ReportingAdapter reportingAdapter = new ReportingAdapter(R.layout.reimburse_main_item, list);
//                rv.setAdapter(reportingAdapter);
//                break;
//            case "reimburse":
//                ibv.setBackgroundResource(R.mipmap.work_borrowicon);
//                tvtitle.setText("报销");
//                tvnum.setText(num + "条未处理");
//                setMessage(v, color);
//                ReimburseMoneyAdapter reimburseMoneyAdapter = new ReimburseMoneyAdapter(R.layout.reimburse_main_item, list);
//                rv.setAdapter(reimburseMoneyAdapter);
//                break;
//            case "borrow":
//                ibv.setBackgroundResource(R.mipmap.work_borrowicon);
//                tvtitle.setText("借款");
//                tvnum.setText(num + "条未处理");
//                setMessage(v, color);
//                HolidayAdapter borrowAdapter = new HolidayAdapter(R.layout.borrowmoney_item, list);
//                rv.setAdapter(borrowAdapter);
//                break;
//        }
//        rootLayout.addView(v);
//    }
//    private void setMessage(View v,int color){
//        View iv= (View) v.findViewById(R.id.bg);
//        if(color== ONE){
//            iv.setBackgroundResource(R.mipmap.work_yellow);
//        }else{
//            iv.setBackgroundResource(R.mipmap.work_borrowicon);
//        }
//    }
}
