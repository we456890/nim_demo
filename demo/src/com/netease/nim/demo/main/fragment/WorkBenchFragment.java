package com.netease.nim.demo.main.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.common.util.HttpUtils;
import com.netease.nim.demo.common.util.PublicUtils;
import com.netease.nim.demo.main.model.MainTab;
import com.netease.nim.demo.workbench.fragment.WorkBenchsFragment;
import com.netease.nim.uikit.cache.ACache;
import com.netease.nim.uikit.common.bean.Borrow;
import com.netease.nim.uikit.common.bean.Reimburse;
import com.netease.nim.uikit.common.bean.Reporting;
import com.netease.nim.uikit.common.bean.UserDTO;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.util.string.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_WORKSTATION_GETWORK_TAG;
import static com.netease.nim.demo.common.util.PublicUtils.URL_FILE_WORKSTATION_TAG;

/**
 * Created by wangkai on 2017/09/26.
 */
public class WorkBenchFragment extends MainTabFragment {
    private WorkBenchsFragment fragment;
    public WorkBenchFragment() {
        this.setContainerId(MainTab.WORK_BENCH.fragmentId);
    }
    private ACache acache;
    private UserDTO userDTO;
    //    private static final String TAG = ChatRoomsFragment.class.getSimpleName();
    private TextView work_time;
    private TextView work_name;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onCurrent();
    }
    @Override
    protected void onInit() {
        findViews();
//        getMessage();
        fragment = (WorkBenchsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.work_bench_fragment);

    }

    private void findViews() {
        acache = ACache.get(getActivity());
        userDTO = (UserDTO) acache.getAsObject("user");
        work_time=findView(R.id.work_time);
        work_name=findView(R.id.work_name);
        HeadImageView headImageView=findView(R.id.user_head);
        headImageView.loadBuddyAvatar(userDTO.getAccid());
        String name = userDTO.getRealName();
        work_name.setText(name);
        work_time.setText(StringUtil.getNewTime());
    }
    @Override
    public void onCurrent() {
        super.onCurrent();
        if (fragment != null) {
//            fragment.onCurrent();
        }
    }
    private void getMessage(){
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
                JSONArray array = null;
                try {
                    array = new JSONArray(str);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = (JSONObject) array.get(i);
                        String modle=object.getString("modle");
                        int number=object.getInt("number");
                        String showData=object.getString("showData");
//                        Log.i("Test1", "onResponse: "+modle);
//                        Log.i("Test2", "onResponse: "+number);
//                        Log.i("Test3", "onResponse: "+showData);
                        if(modle.equals("reporting")&&number>0){
                            Gson gson = new Gson();
                            List<Reporting> r =gson.fromJson(showData,new TypeToken<List<Reporting>>() {}.getType());
                        }
                        if(modle.equals("reimburse")&&number>0){
                            Gson gson = new Gson();
                            List<Reimburse> r =gson.fromJson(showData,new TypeToken<List<Reimburse>>() {}.getType());
                        }
                        if(modle.equals("borrow")&&number>0){
                            Gson gson = new Gson();
                            List<Borrow> b =gson.fromJson(showData,new TypeToken<List<Borrow>>() {}.getType());
                            Log.i("Test4", "onResponse: "+b.get(0).getAmount());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
