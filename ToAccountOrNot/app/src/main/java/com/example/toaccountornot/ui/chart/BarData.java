package com.example.toaccountornot.ui.chart;

import android.accounts.Account;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.HttpUtil;
import com.github.mikephil.charting.data.BarEntry;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;
import org.litepal.exceptions.DataSupportException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BarData {
    public List<BarEntry> data;
    //public List<String> Member_in;
    //public List<String> Member_out;
    //public List<Double> in;
    //public List<Double> out;
    public CursorManager cursorManager = new CursorManager();
    public boolean final_out;
    public boolean final_in;

    public static final String url_year = "http://42.193.103.76:8888/stat/member/year";
    public static final String url_month = "http://42.193.103.76:8888/stat/member/month";
    public static final String url_week = "http://42.193.103.76:8888/stat/member/week";
    public static final String url_date = "http://42.193.103.76:8888/stat/member/date";

    public List<String> temp_Member_in = new ArrayList<>();
    public List<String> temp_Member_out = new ArrayList<>();
    public List<Double> temp_in = new ArrayList<>();
    public List<Double> temp_out = new ArrayList<>();


    public BarData(String time,int flag) {
        final_out = false;
        final_in = false;
        //Member_in = new ArrayList<>();
        //Member_out = new ArrayList<>();
        //in = new ArrayList<>();
        //out = new ArrayList<>();

        if(flag != 0)  {
            cursorManager.change_cur(flag,time);
        }
        //Cursor cursor = cursorManager.initCur_mem("out");
        //UpdateBardata(url_month,"out");
        switch (time)
        {
            case "天":
                //cursor = cursorManager.initCur_mem_day("out");
                UpdateBardata(url_date,"out");
                break;
            case "周":
                //cursor = cursorManager.initCur_mem_week("out");
                UpdateBardata(url_week,"out");
                break;
            case "月":
                //cursor = cursorManager.initCur_mem_week("out");
                UpdateBardata(url_month,"out");
                break;
            case "年" :
                //Log.d("hello","11111");
                //cursor = cursorManager.initCur_mem_year("out");
                UpdateBardata(url_year,"out");
                break;
            default:
                break;
        }
//        if (cursor.moveToFirst()) {
//            do {
//                String member = cursor.getString(0);
//                Member_out.add(member);
//                //Log.d("hello_mem",member);
//                double total = cursor.getDouble(1);
//                out.add(total);
//                //Log.d("hello_mem", String.valueOf(total));
//                // 设置饼状图
//            } while (cursor.moveToNext());
//        }
        while (!final_out);

        // 按照“in”查找数据获得成员所有收入
        //CursorManager cursorManager = new CursorManager();
        //Cursor cursor_in = cursorManager.initCur_mem("in");
        //UpdateBardata(url_month,"in");
        switch (time)
        {
            case "天":
                //cursor_in = cursorManager.initCur_mem_day("in");
                UpdateBardata(url_date,"in");
                break;
            case "周":
                //cursor_in = cursorManager.initCur_mem_week("in");
                UpdateBardata(url_week,"in");
                break;
            case "月":
                //cursor_in = cursorManager.initCur_mem_week("in");
                UpdateBardata(url_month,"in");
                break;
            case "年" :
                //Log.d("hello","11111");
                //cursor_in = cursorManager.initCur_mem_year("in");
                UpdateBardata(url_year,"in");
                break;
            default:
                break;
        }
//        if (cursor_in.moveToFirst()) {
//            do {
//                String member = cursor_in.getString(0);
//                Member_in.add(member);
//                double total = cursor_in.getDouble(1);
//                Log.d("hello_mem", String.valueOf(total));
//                in.add(total);
//                // 设置饼状图
//            } while (cursor_in.moveToNext());
//        }

        while (!final_in);

        /*
        System.out.println("==========*********=============");
        System.out.println("temp_Member_in"+temp_Member_in);
        //System.out.println("Member_in"+Member_in);
        System.out.println("temp_Member_out"+temp_Member_out);
        //System.out.println("Member_out"+Member_out);
        System.out.println("temp_in"+temp_in);
        //System.out.println("in"+in);
        System.out.println("temp_out"+temp_out);
        //System.out.println("out"+out);
        */



    }


    //  Barchart成员收入
    public List<BarEntry> income() {
        data = new ArrayList<>();
        int father = find_mem_in("爸爸");
        int mather = find_mem_in("妈妈");
        int me = find_mem_in("我");

        if(father == -1) {
            BarEntry   y1 = new BarEntry(1f,0);
            data.add(y1);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry   y1 = new BarEntry(1f , Float.parseFloat(df.format(temp_in.get(father))));
            data.add(y1);
        }

        if(me == -1) {
            BarEntry   y1 = new BarEntry(4f , 0);
            data.add(y1);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry   y1 = new BarEntry(4f , Float.parseFloat(df.format(temp_in.get(me))));
            data.add(y1);
        }
        if(mather == -1) {
            BarEntry  y1 = new BarEntry(7f , 0);
            data.add(y1);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry   y1 = new BarEntry(7f , Float.parseFloat(df.format(temp_in.get(mather))));
            data.add(y1);
        }


        return data;
    }

    //  Barchart成员支出
    public List<BarEntry> outcome() {
        data = new ArrayList<>();
        int father = find_mem_out("爸爸");
        int mather = find_mem_out("妈妈");
        int me = find_mem_out("我");

        if(father == -1) {
            BarEntry y1 = new BarEntry(2f , 0);
            data.add(y1);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry   y1 = new BarEntry(2f , Float.parseFloat(df.format(temp_out.get(father))));
            data.add(y1);
        }
        if(me == -1) {
            BarEntry   y1 = new BarEntry(5f , 0);
            data.add(y1);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry   y1 = new BarEntry(5f , Float.parseFloat(df.format(temp_out.get(me))));
            data.add(y1);
        }
        if(mather == -1) {
            BarEntry  y1 = new BarEntry(8f , 0);
            data.add(y1);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry   y1 = new BarEntry(8f , Float.parseFloat(df.format(temp_out.get(mather))));
            data.add(y1);
        }


        return data;
    }

    // 找不到成员返回-1
    private int find_mem_in(String name) {
        int i;
        int flag = 0;
        for(i = 0 ; i < temp_Member_in.size();i++) {
            Log.d("hello_member",temp_Member_in.get(i));
            Log.d("hello_member",name);
            if(name.equals(temp_Member_in.get(i))) {
                Log.d("hello_member","getin");
                flag = 1;
                return i;
            }
        }
        if(flag == 1)   return i;
        else return -1;
    }

    private int find_mem_out(String name) {
        int i;
        int flag = 0;
        for(i = 0 ; i < temp_Member_out.size();i++) {
            if(name.equals(temp_Member_out.get(i))) {
                flag = 1;
                break;
            }
        }
        if(flag == 1)   return i;
        else return -1;
    }

    public void UpdateBardata(String url, final String type) {
        HashMap<String,String> params = new HashMap<>();
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
                if(type.equals("out"))  parseJSONWithFastjson_out(response.body().string());
                else    parseJSONWithFastjson_in(response.body().string());
            }
        });
    }

    public void parseJSONWithFastjson_out(String jsonData) {
        temp_out.clear();
        temp_Member_out.clear();
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String datajson = object.getString("data");
        JSONArray list = JSON.parseArray(datajson);
        // 调试
        System.out.println("====================BarData===============");
        System.out.println("PieData:"+datajson);
        System.out.println("code:"+code);
        System.out.println("message:"+message);
        System.out.println("list"+list);
        for(int i = 0 ; i < list.size() ; i++) {
            System.out.println("============list"+i+"=========");
            JSONObject jsonObject = list.getJSONObject(i);
            Double price = jsonObject.getDouble("balance") == null ? 0 : jsonObject.getDouble("balance");
            System.out.println("price"+price);
            String member = jsonObject.getString("member") == null ? "null" : jsonObject.getString("member");
            System.out.println("member"+member);
            temp_Member_out.add(member);
            temp_out.add(price);
        }
        final_out = true;



    }

    public void parseJSONWithFastjson_in(String jsonData) {
        temp_in.clear();
        temp_Member_in.clear();
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String datajson = object.getString("data");
        JSONArray list = JSON.parseArray(datajson);
        // 调试
        /*
        System.out.println("====================BarData===============");
        System.out.println("PieData:"+datajson);
        System.out.println("code:"+code);
        System.out.println("message:"+message);
        System.out.println("list"+list);
         */
        for(int i = 0 ; i < list.size() ; i++) {
            //System.out.println("============list"+i+"=========");
            JSONObject jsonObject = list.getJSONObject(i);
            Double price = jsonObject.getDouble("balance") == null ? 0 : jsonObject.getDouble("balance");
            //System.out.println("price"+price);
            String member = jsonObject.getString("member") == null ? "null" : jsonObject.getString("member");
            //System.out.println("member"+member);
            temp_Member_in.add(member);
            temp_in.add(price);
        }
        final_in = true;



    }

}
