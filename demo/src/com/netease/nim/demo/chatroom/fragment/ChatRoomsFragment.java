package com.netease.nim.demo.chatroom.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.borrow.activity.BorrowMoneyActivity;
import com.netease.nim.demo.chatroom.adapter.ExpandableListViewAdapter;
import com.netease.nim.demo.holiday.activity.HolidayActivity;
import com.netease.nim.demo.reimburse.activity.ReimburseMoneyActivity;
import com.netease.nim.demo.reporting.activity.ReportingBossActivity;
import com.netease.nim.demo.reporting.activity.ReportingStaffActivity;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.bean.MenuVO;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.fragment.TFragment;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 菜单显示页面
 * <p>
 * Created by huangjun on 2015/12/11.
 */
public class ChatRoomsFragment extends TFragment {
    private static String TAG = "wk_ChatRoomsFragment";
    private ACache acache;
    private String business;
//    private AppBarLayout app_bar_layout;
    private TextView menu_name;
    private ImageView iv;
//    private LinearLayout menu_rellwork, menu_llwork,menu_llborrow,menu_llreimburese;
//    private LinearLayout notread, null1, null2, null3, null4, null5, null6, null7, null8, null9;
    private ExpandableListView expandableListView;
    public String[] groupStrings = {"汇报管理", "财务管控","人事管理"};
    public String[][] childStrings = {
            {"工作汇报"},
            {"借款申请", "费用报销"},
            {"请假"},
    };
    public int[] images={R.drawable.menu_one,R.drawable.menu_two,R.drawable.menu_three};
    //    private static final String TAG = ChatRoomsFragment.class.getSimpleName();
//
//    private ChatRoomsAdapter adapter;
//    private PullToRefreshLayout swipeRefreshLayout;
//    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_rooms, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
    }

    public void onCurrent() {
//        fetchData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 解析json
     */
    private void getMenuGson(String json) {
        Log.i(TAG, "json1234: " + json);
        Gson gson = new Gson();
        Type type = new TypeToken<List<MenuVO>>() {
        }.getType();
        List<MenuVO> menuVOList = gson.fromJson(json, type);

    }

    /**
     * 获取缓存数据
     */
    private void getAcache() {
        try {
            acache = ACache.get(getActivity());
            String str = acache.getAsString("menu");
            if (str != null) {
                Log.i(TAG, "str1212: " + str);
                getMenuGson(str);
            } else {
                Toast.makeText(getActivity(), "请重新登录", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UserDTO userDTO = (UserDTO) acache.getAsObject("user");
        if (!userDTO.equals(null)) {
            business = userDTO.getBusiness();
            ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(getActivity(),groupStrings,childStrings,images);
            expandableListView.setAdapter(adapter);
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    String strtitle=childStrings[groupPosition][childPosition];
                    Intent intent = null;
                    switch (strtitle){
                        case "工作汇报":
                            if (business.equals("1")) {
                                /**
                                 * 员工页面
                                 */
                                intent = new Intent(getActivity(), ReportingStaffActivity.class);
                                
                            } else if (business.equals("6")) {
                                /**
                                 * Boss页面
                                 */
                                intent = new Intent(getActivity(), ReportingBossActivity.class);
                            }
                            break;
                        case "借款申请":
                            intent = new Intent(getActivity(), BorrowMoneyActivity.class);
                            break;
                        case "费用报销":
                            intent = new Intent(getActivity(), ReimburseMoneyActivity.class);
                            break;
                        case "请假":
//                            Toast.makeText(getActivity(), "请假", Toast.LENGTH_LONG).show();
                            intent = new Intent(getActivity(), HolidayActivity.class);
                            break;
                    }
                    getActivity().startActivity(intent);
//                    Toast.makeText(getActivity(), childStrings[groupPosition][childPosition], Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }else{
            Toast.makeText(getActivity(),"请重新登录！",Toast.LENGTH_SHORT).show();
        }
//        getAcache();
    }

//

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
    private void findViews() {
        acache = ACache.get(getActivity());
        expandableListView=findView(R.id.expand_list);
    }
}
//    /**
//     * 借款申请
//     */
//    View.OnClickListener borrowocl = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), BorrowMoneyActivity.class);
//            getActivity().startActivity(intent);
//        }
//    };
//    /**
//     * 报销申请
//     */
//    View.OnClickListener reimbureseocl = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), ReimburseMoneyActivity.class);
//            getActivity().startActivity(intent);
//        }
//    };
//
//    View.OnClickListener workocl = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            if (business.equals("1")) {
//                /**
//                 * 员工页面
//                 */
//                Intent intent = new Intent(getActivity(), ReportingStaffActivity.class);
//                getActivity().startActivity(intent);
//            } else if (business.equals("6")) {
//                /**
//                 * Boss页面
//                 */
//                Intent intent = new Intent(getActivity(), ReportingBossActivity.class);
//                getActivity().startActivity(intent);
//            }
//        }
//    };

//            String name = userDTO.getRealName();
//            menu_name.setText(name);


//            menu_llwork.setOnClickListener(workocl);
//            menu_rellwork.setOnClickListener(workocl);
//            menu_llborrow.setOnClickListener(borrowocl);
//            menu_llreimburese.setOnClickListener(reimbureseocl);
//            if (business.equals("1")) {
//                /**
//                 * 员工页面
//                 */
//                satffActivity(true);
//            } else if (business.equals("6")) {
//                /**
//                 * Boss页面
//                 */
//                satffActivity(false);
//            }
//        app_bar_layout=findView(R.id.app_bar_layout);
//        menu_rellwork = findView(menu_rellwork);
//        menu_llwork = findView(R.id.menu_llwork);
//        menu_llborrow=findView(menu_llborrow);
//        menu_llreimburese=findView(menu_llreimburese);
//        menu_name = findView(R.id.menu_name);
//        app_bar_layout.setVisibility(View.GONE);
//        iv=findView(R.id.iv);
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                showNormalDialog();
//            }
//        });
//--------------------------------
//        notread = findView(R.id.notread);
//        null1 = findView(R.id.null1);
//        null2 = findView(R.id.null2);
//        null3 = findView(R.id.null3);
//        null4 = findView(R.id.null4);
//        null5 = findView(R.id.null5);
//        null6 = findView(R.id.null6);
//        null7 = findView(R.id.null7);
//        null8 = findView(R.id.null8);
//        null9 = findView(null9);
//--------------------------------
//        UserDTO userDTO=(UserDTO)acache.getAsObject("user");
//        business=userDTO.getBusiness();
//        gridView = findView(R.id.gridView);
//        if(business.equals("1")){
//            ChatRoomsAdapter gridViewAdapter = new ChatRoomsAdapter(getActivity(), staffimagelist, stafftextlist);
//            gridView.setAdapter(gridViewAdapter);
//        }else if (business.equals("6")) {
//            ChatRoomsAdapter gridViewAdapter = new ChatRoomsAdapter(getActivity(), bossimagelist, bosstextlist);
//            gridView.setAdapter(gridViewAdapter);
//        }
//        gridView.setOnItemClickListener(lic);



//        // recyclerView
//        recyclerView = findView(R.id.recycler_view);
//        adapter = new ChatRoomsAdapter(recyclerView);
//        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        recyclerView.addItemDecoration(new SpacingDecoration(ScreenUtil.dip2px(10), ScreenUtil.dip2px(10), true));
//        recyclerView.addOnItemTouchListener(new OnItemClickListener<ChatRoomsAdapter>() {
//            @Override
//            public void onItemClick(ChatRoomsAdapter adapter, View view, int position) {
//                ChatRoomInfo room = adapter.getItem(position);
//                ChatRoomActivity.start(getActivity(), room.getRoomId());
//            }
//        });
//    }
//
//    private void fetchData() {
//        ChatRoomHttpClient.getInstance().fetchChatRoomList(new ChatRoomHttpClient.ChatRoomHttpCallback<List<ChatRoomInfo>>() {
//            @Override
//            public void onSuccess(List<ChatRoomInfo> rooms) {
//                onFetchDataDone(true, rooms);
//            }
//
//            @Override
//            public void onFailed(int code, String errorMsg) {
//                onFetchDataDone(false, null);
//                if (getActivity() != null) {
//                    Toast.makeText(getActivity(), "fetch chat room list failed, code=" + code, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


//    private void onFetchDataDone(final boolean success, final List<ChatRoomInfo> data) {
//        Activity context = getActivity();
//        if (context != null) {
//            context.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    swipeRefreshLayout.setRefreshing(false); // 刷新结束
//
//                    if (success) {
//                        adapter.setNewData(data); // 刷新数据源
//
//                        postRunnable(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapter.closeLoadAnimation();
//                            }
//                        });
//                    }
//                }
//            });
//        }
//    }

    //        swipeRefreshLayout = findView(R.id.swipe_refresh);
//        swipeRefreshLayout.setPullUpEnable(false);
//        swipeRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onPullDownToRefresh() {
//                //刷新
////                fetchData();
//                Toast.makeText(getActivity(), "我是刷新", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPullUpToRefresh() {
//
//            }
//        });
//        swipeRefreshLayout.setRefreshing(false); // 刷新结束
//    private void satffActivity(boolean b) {
//        if (b == true) {
////            notread.setVisibility(View.GONE);
//            null1.setVisibility(View.INVISIBLE);
//            null2.setVisibility(View.INVISIBLE);
////            null3.setVisibility(View.INVISIBLE);
////            null4.setVisibility(View.INVISIBLE);
//            null5.setVisibility(View.INVISIBLE);
//            null6.setVisibility(View.INVISIBLE);
//            null7.setVisibility(View.INVISIBLE);
//            null8.setVisibility(View.INVISIBLE);
//            null9.setVisibility(View.INVISIBLE);
//        }
//        null1
//                null2
//        null3
//                null4
//        null5
//                null6
//        null7
//                null8
//        null9
//    }

