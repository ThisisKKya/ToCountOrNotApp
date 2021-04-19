package com.example.toaccountornot.ui.chart;

import android.app.Activity;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.HttpUtil;
import com.github.mikephil.charting.data.PieEntry;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PieData {
    public List<PieEntry> data;
    public CursorManager cursorManager = new CursorManager();
    List<String> temp_first = new ArrayList<>();
    List<Double> temp_price = new ArrayList<>();
    private android.os.Handler handler = null;
    private boolean waitfordata;

    public static final String url_year = "http://42.193.103.76:8888/stat/first/year";
    public static final String url_month = "http://42.193.103.76:8888/stat/first/month";
    public static final String url_week = "http://42.193.103.76:8888/stat/first/week";
    public static final String url_date = "http://42.193.103.76:8888/stat/first/date";


    public PieData() {}

    // 显示一级收入饼状图
    // flag  1= 昨天 0 = 今天 1 = 明天
    public List<PieEntry> income(String time,int flag) {
        waitfordata = false;
//        List<String> first = new ArrayList<>();
//        List<Double> price = new ArrayList<>();
//        handler = new android.os.Handler() {
//            public void handleMessage(Message msg) {
//                Log.d("hellopie","55555555555555");
//            }
//        };

        //if(flag == 0)
        if(flag != 0)  {
            cursorManager.change_cur(flag,time);
        }
        //Cursor cursor = cursorManager.initCur_one("in");        // month
        //UpdatePiedata(url_month,"in");
        Log.d("hellopie","11111111111111111111");
        //Log.d("hello",time);
        switch (time)
        {
            case "天":
                //cursor = cursorManager.initCur_one_day("in");
                UpdatePiedata(url_date,"in");
                break;
            case "周":
                //cursor = cursorManager.initCur_one_week("in");
                UpdatePiedata(url_week,"in");
                break;
            case "月":
                //cursor = cursorManager.initCur_one_week("in");
                UpdatePiedata(url_month,"in");
                break;
            case "年" :
                //
                //cursor = cursorManager.initCur_one_year("in");
                UpdatePiedata(url_year,"in");
                break;
            default:
                break;
        }
//        if (cursor.moveToFirst()) {
//            do {
//                System.out.println("===========cursor=========");
//                String title = cursor.getString(0);
//                //Log.d("hello_li",title);
//                first.add(title);
//                double total = cursor.getDouble(1);
//                //Log.d("hello", String.valueOf(total));
//                price.add(total);
//                all += total;
//                // 设置饼状图
//                System.out.println("first:"+title);
//                System.out.println("price"+total);
//            } while (cursor.moveToNext());
//        }
//        int len = first.size();
        System.out.println("=====temp_price==="+temp_price);
        Log.d("hellopie","2222222222222222");
        while(!waitfordata);
        return data;
    }

    // 显示一级支出饼状图
    public  List<PieEntry> outcome(String time,int flag) {
        waitfordata = false;


        if(flag != 0)  {
            cursorManager.change_cur(flag,time);
        }
        //UpdatePiedata(url_month,"out");
        switch (time)
        {
            case "天":
                UpdatePiedata(url_date,"out");
                break;
            case "周":
                UpdatePiedata(url_week,"out");
                break;
            case "月":
                UpdatePiedata(url_month,"out");
                break;
            case "年" :
                UpdatePiedata(url_year,"out");
                break;
            default:
                break;
        }
        while(!waitfordata);
        return data;
    }

    public void UpdatePiedata(String url, String type) {
        HashMap<String, String> params = new HashMap<>();
        switch (url) {
            case url_year:
                params.put("year", cursorManager.year);
                break;
            case url_month:
                params.put("year", cursorManager.year);
                params.put("month", cursorManager.month);
                break;
            case url_week:
                params.put("year", cursorManager.year);
                params.put("week", cursorManager.week);
                break;
            case url_date:
                params.put("date", cursorManager.date);
                break;
        }
        params.put("type", type);

        HttpUtil.sendGETRequestWithToken_Pie_Bar(url, params,"null","null", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                parseJSONWithFastjson(response.body().string());
            }
        });
    }


    public void parseJSONWithFastjson(String jsonData) {
        temp_first.clear();
        temp_price.clear();
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String datajson = object.getString("data");
        JSONArray list = JSON.parseArray(datajson);

        // 调试
        /*
        System.out.println("=================PieData================");
        System.out.println("PieData:"+datajson);
        System.out.println("code:"+code);
        System.out.println("message:"+message);
        //System.out.println("PieData:"+dataObject);
        System.out.println("list:"+list);*/

        for(int i = 0 ; i < list.size() ; i++) {
            //System.out.println("============list"+i+"=========");
            JSONObject jsonObject = list.getJSONObject(i);
            Double price = jsonObject.getDouble("balance") == null ? 0 : jsonObject.getDouble("balance");
            //System.out.println("price"+price);
            temp_price.add(price);
            String first = jsonObject.getString("first") == null ? "null" : jsonObject.getString("first");
            //System.out.println("first"+first);
            temp_first.add(first);
        }
        int len = temp_first.size();
        data = new ArrayList<>();
        //  赋值饼状图
        int sum = 0;
        for(int j = 0 ; j < temp_price.size() ; j++) {
            sum += temp_price.get(j);
        }
        for (int i = 0; i < len; i++) {
            data.add(new PieEntry((float) (temp_price.get(i)/sum),temp_first.get(i)));
        }
        Log.d("hellopie","33333333333");
        waitfordata = true;

//        Runnable runnableUI  = new Runnable() {
//            @Override
//            public void run() {
//                Log.d("hellopie","444444444444444444");
//                Message msg = new Message();
//                msg.what = 1;
//                handler.sendMessage(msg);
//
//            }
//        };

        new Thread() {
            @Override
            public void run() {
                Log.d("hellopie","444444444444444444");
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        };

    }


}
