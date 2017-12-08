package com.netease.nim.demo.reporting.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.reporting.adapter.Fragmentadapter;
import com.netease.nim.demo.reporting.fragment.FragmentStaffCheck;
import com.netease.nim.demo.reporting.fragment.FragmentStaffInsert;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
/**
 * 工作汇报页面
 */

/**
 * Created by 78560 on 2017/8/17.
 */

public class ReportingStaffActivity extends UI implements View.OnClickListener{
//    private static String TAG = "wk_staffReporting";
//    private ACache acache;
//    @Bind(R.id.ar_selecttime)
//    RelativeLayout ar_selecttime;
//
//    @Bind(R.id.menu_tv_searchTime)
//    TextView tvsearchTime;
//
//    @Bind(R.id.staffmenu_listview)
//    ListView staffmenu_listview;

//    @Bind(R.id.btnRight)
//    Button btnRight;

//    List<Reporting> list;
//
//    @Bind(R.id.etdemo)
//    EditText etdemo;

//    private CustomDatePicker customDatePicker;

    private static final String TAG = "wk_staffReporting";
    private ViewPager mViewPager;
    List<Fragment> mFragmentList = new ArrayList<Fragment>();
    FragmentManager mFragmentManager;
    Fragmentadapter fpa;
    public LinearLayout checkll;
    public LinearLayout insertll;
    String[] titleName = new String[]{"看汇报", "写汇报"};
    public ImageView insertIamge,checkIamge;
    public TextView insertText,checkText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_staff_reporting);
        ButterKnife.bind(ReportingStaffActivity.this);
        init();
        initFragment();
        fpa = new Fragmentadapter(getSupportFragmentManager(), mFragmentList);
        initView();
        initViewPager();
//        init();
//        initDatePicker();
    }
    class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //            Log.d(TAG,"onPageScrooled");
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected");
            boolean[] state = new boolean[titleName.length];
            state[position] = true;
            updateBottomLinearLayoutSelect(state[0], state[1]);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(TAG, "onPageScrollStateChanged");
        }
    }


    private void initViewPager() {
        mViewPager.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());
        mViewPager.setAdapter(fpa);
        mViewPager.setCurrentItem(0);
        updateBottomLinearLayoutSelect(true, false);
    }

    private void updateBottomLinearLayoutSelect(boolean check, boolean insert) {
        checkll.setSelected(check);
        insertll.setSelected(insert);
        if(check==true){
            checkIamge.setImageResource(R.mipmap.ar_checkon_icon);
            checkText.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            insertIamge.setImageResource(R.mipmap.ar_insert_icon);
            insertText.setTextColor(this.getResources().getColor(R.color.black));

        }else{
            checkIamge.setImageResource(R.mipmap.ar_check_icon);
            checkText.setTextColor(this.getResources().getColor(R.color.black));
            insertIamge.setImageResource(R.mipmap.ar_inserton_icon);
            insertText.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        }

    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.page);
        checkll = (LinearLayout) findViewById(R.id.checkll);
        checkIamge= (ImageView) findViewById(R.id.checkImageView);
        checkText= (TextView) findViewById(R.id.checkTextView);
        checkText.setText(titleName[0]);
        checkll.setOnClickListener(this);



        insertll = (LinearLayout) findViewById(R.id.insertll);
        insertIamge= (ImageView) findViewById(R.id.insertImageView);
        insertText= (TextView) findViewById(R.id.insertTextView);
        insertText.setText(titleName[1]);
        insertll.setOnClickListener(this);
    }

    private void initFragment() {
        FragmentStaffCheck check = new FragmentStaffCheck();
        FragmentStaffInsert insert = new FragmentStaffInsert();
        mFragmentList.add(check);
        mFragmentList.add(insert);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkll:
                mViewPager.setCurrentItem(0);
                updateBottomLinearLayoutSelect(true, false);
                break;

            case R.id.insertll:
                mViewPager.setCurrentItem(1);
                updateBottomLinearLayoutSelect(false, true);
                break;

            default:
                break;
        }
    }
    private void init() {
//        acache = ACache.get(this);
        ToolBarOptions options = new ToolBarOptions();
//        Toolbar tb= (Toolbar) findViewById(R.id.menu_toolbar);
//        tb.setOverflowIcon(Drawable.createFromPath(R.drawable.as_menu));
        setToolBar(R.id.menu_toolbar, options);
        setTitle(R.string.nullname);
        TextView textView = (TextView) findViewById(R.id.menu_textview);
        textView.setText(R.string.menu_reporting_title);

//        PostReporting();
//        staffmenu_listview.setOnItemClickListener(oic);
    }
}


//    /**
//     * 时间选择器
//     */
//    private void initDatePicker() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//        String now = sdf.format(new Date());
//        tvsearchTime.setText(now.split(" ")[0]);
//        tvsearchTime.setText(now);
//
//        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
//            @Override
//            public void handle(String time) { // 回调接口，获得选中的时间
//                tvsearchTime.setText(time.split(" ")[0]);
//            }
//        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
//        customDatePicker.showSpecificTime(false); // 不显示时和分
//        customDatePicker.setIsLoop(false); // 不允许循环滚动
//    }

//    @OnClick(R.id.ar_selecttime)
//    public void ar_selecttime(View view){
//        customDatePicker.show(tvsearchTime.getText().toString());
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if(imm.isActive(etdemo)){
//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//        //重新获取缓存数据
//        init();
//    }



//    AdapterView.OnItemClickListener oic=new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            Intent intent = new Intent();
//            intent.putExtra("i",i);
//            String step=list.get(i).getSteps().toString();
//            Log.i(TAG, "onItemClick: "+step);
//            if(step.equals("1")) {
//                intent.setClass(ReportingStaffActivity.this, ReportingStaffUpdateActivity.class);
//                startActivity(intent);
//            }else if(step.equals("2")){
//                intent.setClass(ReportingStaffActivity.this, ReportingStaffCheckActivity.class);
//                startActivity(intent);
//            }else if(step.equals("3")){
//                intent.setClass(ReportingStaffActivity.this, ReportingStaffCheckActivity.class);
//                startActivity(intent);
//            }
//        }
//    };

//    private Map<String, String> getReporingContent() {
//        UserDTO u = (UserDTO) acache.getAsObject("user");
//        Map<String, String> m = new HashMap<>();
//        if (u != null) {
//            m.put("reporting.checkerAnd", "1");
//            m.put("reporting.checker",u.getDepartmentId());
//            m.put("reporting.reportinf", u.getId());
//            m.put("reporting.owner",u.getId());
//        } else {
//            m = null;
//        }
//        return m;
//    }
//
//    /**
//     * 获取工作汇报详细
//     */
//    private void PostReporting() {
//        String url = PublicUtils.URL +
//                PublicUtils.URL_FILE_TAG +
//                PublicUtils.URL_FILE_REPORTING_TAG +
//                PublicUtils.URL_FILE_REPORTING_RESPONSE_TAG;
////        Log.i(TAG, "url: " + url+getReporingContent());
//        HttpUtils.doPost(url, getReporingContent(), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (PublicUtils.isNetworkAvailable(ReportingStaffActivity.this)) {
//                    Toast.makeText(ReportingStaffActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String str = response.body().string();
//                Log.i(TAG, "str:" + str);
//                acache.put("reporting",str);
//                GSONReporting(str);
//            }
//        });
//    }
//
//    private void GSONReporting(String json) {
//        Gson gson = new Gson();
//        ReportingDto r = gson.fromJson(json, ReportingDto.class);
//        Log.i(TAG, "GSONReporting: "+r.toString());
//        if (r.getTotal().equals("0")) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(ReportingStaffActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            list = r.getRows();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    CommandAdapter menuHuiBAdapter=new CommandAdapter(ReportingStaffActivity.this,list);
//                    staffmenu_listview.setAdapter(menuHuiBAdapter);
//                }
//            });
//        }
//
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.as_toolbar, menu);
//        super.onCreateOptionsMenu(menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent = new Intent();
//        switch (item.getItemId()){
//            //日报
//            case R.id.asmenu_daily:
//                intent.putExtra("type","日报");
//                intent.setClass(ReportingStaffActivity.this,ReportingStaffInsterActivity.class);
//                startActivity(intent);
////                Toast.makeText(ReportingStaffActivity.this, "ribao", Toast.LENGTH_SHORT).show();
//                break;
//            //月报
//            case R.id.asmenu_monthly:
//                intent = new Intent();
//                intent.putExtra("type","月报");
//                intent.setClass(ReportingStaffActivity.this,ReportingStaffInsterActivity.class);
//                startActivity(intent);
////                Toast.makeText(ReportingStaffActivity.this, "yuebao", Toast.LENGTH_SHORT).show();
//                break;
//            default:break;
//        }
//        return true;
//
//    }


    //    protected void setToolbarRight(String text, @Nullable Integer icon, View.OnClickListener btnClick){
//        if(text!=null)
//        {
//            btnRight.setText(text);
//        }
//        if (icon != null) {
//            btnRight.setBackgroundResource(icon.intValue());
//            ViewGroup.LayoutParams linearParams = btnRight.getLayoutParams();
//            linearParams.height= 26;
//            linearParams.width=26;
//            btnRight.setLayoutParams(linearParams);
//        }
//        btnRight.setOnClickListener(btnClick);
//    }
//        setToolbarRight(null,R.drawable.as_menu, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ReportingStaffActivity.this, "aaaa", Toast.LENGTH_SHORT).show();
//            }
//        });

