package com.example.toaccountornot.ui.chart;

import android.app.Activity;
import android.database.Cursor;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.toaccountornot.R;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.HttpUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.PieEntry;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

public class RvList {
    public List<income> myList = new ArrayList<>();
    public  CursorManager cursorManager = new CursorManager();


    public static final String url_year = "http://42.193.103.76:8888/stat/first/year";
    public static final String url_month = "http://42.193.103.76:8888/stat/first/month";
    public static final String url_week = "http://42.193.103.76:8888/stat/first/week";
    public static final String url_date = "http://42.193.103.76:8888/stat/first/date";
    public static final String url_year_bar = "http://42.193.103.76:8888/stat/member/year";
    public static final String url_month_bar = "http://42.193.103.76:8888/stat/member/month";
    public static final String url_week_bar = "http://42.193.103.76:8888/stat/member/week";
    public static final String url_date_bar = "http://42.193.103.76:8888/stat/member/date";
    public static final String url_year_member = "http://42.193.103.76:8888/stat/first/member/year";
    public static final String url_month_member = "http://42.193.103.76:8888/stat/first/member/month";
    public static final String url_week_member = "http://42.193.103.76:8888/stat/first/member/week";
    public static final String url_date_member = "http://42.193.103.76:8888/stat/first/member/date";
    public static final String url_year_firstname = "http://42.193.103.76:8888/stat/second/year";
    public static final String url_month_firstname = "http://42.193.103.76:8888/stat/second/month";
    public static final String url_week_firstname = "http://42.193.103.76:8888/stat/second/week";
    public static final String url_date_firstname = "http://42.193.103.76:8888/stat/second/date";



    public List<String> temp_first = new ArrayList<>();
    public List<Double> temp_price_first = new ArrayList<>();
    public List<String> temp_second = new ArrayList<>();
    public List<Double> temp_price_second = new ArrayList<>();

    public List<String> temp_Member_in = new ArrayList<>();
    public List<String> temp_Member_out = new ArrayList<>();
    public List<Double> temp_in = new ArrayList<>();
    public List<Double> temp_out = new ArrayList<>();

    public boolean final_pie_firstRv;
    public boolean final_pie_secondRv;
    public boolean final_bar_out;
    public boolean final_bar_in;


    public RvList (List<income> myList) {
        this.myList = myList;
    }

    public List<income> choice(int i,String cate,String time,int flag,String member) {

        if(i == 0)  initincome(cate,time,flag,member);
        if(i == 1)  initoutcome(cate,time,flag,member);
        if(i == 2)  initpeople(time,flag);
        return myList;
    }

    // 饼状图收入的流水展示
    private void initincome(String cate,String time,int flag,String member) {
        System.out.println("***********111111111111111***********");
        myList.clear();
        List<String> first = new ArrayList<>();
        List<Double> price = new ArrayList<>();
        List<String> second = new ArrayList<>();
        List<Double> price_second = new ArrayList<>();
        final_pie_firstRv = false;
        final_pie_secondRv = false;

        if(flag != 0)  cursorManager.change_cur(flag,time);     // 修改日期

        //Cursor cursor = null;
        if(member == "null") {
            //cursor = cursorManager.initCur_one("in");
            //UpdateRvdata(url_month,"in","null","null");
            switch (time)
            {
                case "天":
                    //cursor = cursorManager.initCur_one_day("in");
                    Log.d("hello_name_date","**********");
                    UpdateRvdata(url_date,"in","null","null");
                    break;
                case "周":
                    //cursor = cursorManager.initCur_one_week("in");
                    UpdateRvdata(url_week,"in","null","null");
                    break;
                case "月" :
                    UpdateRvdata(url_month,"in","null","null");
                    break;
                case "年" :
                    //Log.d("hello","11111");
                    //cursor = cursorManager.initCur_one_year("in");
                    UpdateRvdata(url_year,"in","null","null");
                    break;
                default:
                    break;
            }
        }
        else {
            //cursor = cursorManager.initCur_one_mem("in",member);
            //UpdateRvdata(url_month_member,"in",member,"null");
            switch (time)
            {
                case "天":
                    //cursor = cursorManager.initCur_one_day_mem("in",member);
                    UpdateRvdata(url_date_member,"in",member,"null");
                    break;
                case "周":
                    //cursor = cursorManager.initCur_one_week_mem("in",member);
                    UpdateRvdata(url_week_member,"in",member,"null");
                    break;
                case "月":
                    //cursor = cursorManager.initCur_one_week_mem("in",member);
                    UpdateRvdata(url_month_member,"in",member,"null");
                    break;
                case "年" :
                    //Log.d("hello","11111");
                    //cursor = cursorManager.initCur_one_year_mem("in",member);
                    UpdateRvdata(url_year_member,"in",member,"null");
                    break;
                default:
                    break;
            }
        }
//        if (!cursor.moveToFirst()) {
//            Toast.makeText(getContext(),"选择该时间段无收入数据", Toast.LENGTH_SHORT).show();
//        }
//
//        if (cursor.moveToFirst()) {
//            do {
//                String title = cursor.getString(0);
//                first.add(title);
//                double total = cursor.getDouble(1);
//                price.add(total);
//            } while (cursor.moveToNext());
//        }


        while(!final_pie_firstRv);

        System.out.println("==========initRV!!!!!=============");
        System.out.println("temp_first"+temp_first);
        System.out.println("first"+first);
        System.out.println("temp_first_price"+temp_price_first);
        System.out.println("price"+price);

        // 二级流水显示
        if(!cate.equals("一")) {
            //Log.d("hello",cate);
            int first_name = Integer.parseInt(cate);
            Log.d("hello_name", String.valueOf(first_name));
            Log.d("hello_name_firstname", String.valueOf(temp_first.get(first_name)));
            //Cursor cursor2 = cursorManager.initCur_two("in",first.get(first_name));
            switch (time)
            {
                case "天":
                    Log.d("hello_day","1111");
                    //cursor2 = cursorManager.initCur_two_day("in",first.get(first_name));
                    UpdateRvdata(url_date_firstname,"in","null",temp_first.get(first_name));
                    break;
                case "周":
                    //cursor2 = cursorManager.initCur_two_week("in",first.get(first_name));
                    UpdateRvdata(url_week_firstname,"in","null",temp_first.get(first_name));
                    break;
                case "月":
                    //cursor2 = cursorManager.initCur_two_week("in",first.get(first_name));
                    UpdateRvdata(url_month_firstname,"in","null",temp_first.get(first_name));
                    break;
                case "年" :
                    //Log.d("hello","11111");
                    //cursor2 = cursorManager.initCur_two_year("in",first.get(first_name));
                    UpdateRvdata(url_year_firstname,"in","null",temp_first.get(first_name));
                    break;
                default:
                    break;
            }
//            if (cursor2.moveToFirst()) {
//                do {
//                    String title = cursor2.getString(0);
//                    //Log.d("hello_second",title);
//                    second.add(title);
//                    double total = cursor2.getDouble(1);
//                    //Log.d("hello_price", String.valueOf(total));
//                    price_second.add(total);
//                    // 设置饼状图
//                } while (cursor2.moveToNext());
//            }

            while (!final_pie_secondRv);

//            for(int i = 0;i < second.size();i++) {
//                income income = new income(second.get(i),price_second.get(i),R.drawable.food);
//                myList.add(income);
//            }
            for(int i = 0;i < temp_second.size();i++) {
                income income = new income(temp_second.get(i),temp_price_second.get(i),R.drawable.food);
                myList.add(income);
            }
        }
        else {
            for(int i = 0;i < temp_first.size();i++) {
                income income = new income(temp_first.get(i),temp_price_first.get(i),R.drawable.food);
                myList.add(income);
            }
        }

    }

    // 饼状图支出的流水展示
    private void initoutcome(String cate,String time,int flag,String member) {
        myList.clear();
//        List<String> first = new ArrayList<>();
//        List<String> second = new ArrayList<>();
//        List<Double> price = new ArrayList<>();
//        List<Double> price_second = new ArrayList<>();
        final_pie_firstRv = false;
        final_pie_secondRv = false;

        if(flag != 0)  {
            cursorManager.change_cur(flag,time);
        }
        //CursorManager cursorManager = new CursorManager();
        //Cursor cursor = null ;

        if(member == "null") {  // 无人员参数
            //cursor = cursorManager.initCur_one("out");
            switch (time)
            {
                case "天":
                    //cursor = cursorManager.initCur_one_day("out");
                    UpdateRvdata(url_date,"out","null","null");
                    break;
                case "周":
                    //cursor = cursorManager.initCur_one_week("out");
                    UpdateRvdata(url_week,"out","null","null");
                    break;
                case "月":
                    //cursor = cursorManager.initCur_one_week("out");
                    UpdateRvdata(url_month,"out","null","null");
                    break;
                case "年" :
                    //Log.d("hello","11111");
                    //cursor = cursorManager.initCur_one_year("out");
                    UpdateRvdata(url_year,"out","null","null");
                    break;
                default:
                    break;
            }
        }
       else {
            //cursor = cursorManager.initCur_one_mem("out",member);
            switch (time)
            {
                case "天":
                    //cursor = cursorManager.initCur_one_day_mem("out",member);
                    UpdateRvdata(url_date_member,"out",member,"null");
                    break;
                case "周":
                    //cursor = cursorManager.initCur_one_week_mem("out",member);
                    UpdateRvdata(url_week_member,"out",member,"null");
                    break;
                case "月":
                    //cursor = cursorManager.initCur_one_week_mem("out",member);
                    UpdateRvdata(url_month_member,"out",member,"null");
                    break;
                case "年" :
                    //Log.d("hello","11111");
                    //cursor = cursorManager.initCur_one_year_mem("out",member);
                    UpdateRvdata(url_year_member,"out",member,"null");
                    break;
                default:
                    break;
            }
        }

//        if (!cursor.moveToFirst()) {
//            Toast.makeText(getContext(),"选择该时间段无支出数据", Toast.LENGTH_SHORT).show();
//        }
//        if (cursor.moveToFirst()) {
//            do {
//                String title = cursor.getString(0);
//                first.add(title);
//                double total = cursor.getDouble(1);
//                price.add(total);
//            } while (cursor.moveToNext());
//        }

        while(!final_pie_firstRv);
        // 二级流水显示
        if(!cate.equals("一")) {
            //Log.d("hello",cate);
            int first_name = Integer.parseInt(cate);
            //Log.d("hello", String.valueOf(first_name));
            //Cursor cursor2 = cursorManager.initCur_two("out",first.get(first_name));
            //UpdateRvdata(url_month_firstname,"out","null",temp_first.get(first_name));
            switch (time)
            {
                case "天":
                    //cursor2 = cursorManager.initCur_two_day("out",first.get(first_name));
                    UpdateRvdata(url_date_firstname,"out","null",temp_first.get(first_name));
                    break;
                case "周":
                    //cursor2 = cursorManager.initCur_two_week("out",first.get(first_name));
                    UpdateRvdata(url_week_firstname,"out","null",temp_first.get(first_name));
                    break;
                case "月":
                    //cursor2 = cursorManager.initCur_two_week("out",first.get(first_name));
                    UpdateRvdata(url_month_firstname,"out","null",temp_first.get(first_name));
                    break;
                case "年" :
                    //Log.d("hello","11111");
                    //cursor2 = cursorManager.initCur_two_year("out",first.get(first_name));
                    UpdateRvdata(url_year_firstname,"out","null",temp_first.get(first_name));
                    break;
                default:
                    break;
            }
//            if (cursor2.moveToFirst()) {
//                do {
//                    String title = cursor2.getString(0);
//                    //Log.d("hello_second",title);
//                    second.add(title);
//                    double total = cursor2.getDouble(1);
//                    //Log.d("hello_price", String.valueOf(total));
//                    price_second.add(total);
//                    // 设置饼状图
//                } while (cursor2.moveToNext());
//            }

            while (!final_pie_secondRv);
            for(int i = 0;i < temp_second.size();i++) {
                income income = new income(temp_second.get(i),temp_price_second.get(i),R.drawable.food);
                myList.add(income);
            }
        }
        else {
            for(int i = 0;i < temp_first.size();i++) {
                income income = new income(temp_first.get(i),temp_price_first.get(i),R.drawable.food);
                myList.add(income);
            }
        }
    }

    // 柱状图成员流水展示
    private void initpeople(String time,int flag) {
        myList.clear();
        List<String>Member_in = new ArrayList<>();
        List<String>Member_out = new ArrayList<>();
        List<Double>in = new ArrayList<>();
        List<Double>out = new ArrayList<>();

        final_bar_out = false;
        final_bar_in = false;


        if(flag != 0)  {
            cursorManager.change_cur(flag,time);
        }

        //Cursor cursor = cursorManager.initCur_mem("out");
        //UpdateRvdata(url_month_bar,"out","null","null");

        switch (time)
        {
            case "天":
                //cursor = cursorManager.initCur_mem_day("out");
                UpdateRvdata(url_date_bar,"out","null","null");
                break;
            case "周":
                //cursor = cursorManager.initCur_mem_week("out");
                UpdateRvdata(url_week_bar,"out","null","null");
                break;
            case "月":
                //cursor = cursorManager.initCur_mem_week("out");
                UpdateRvdata(url_month_bar,"out","null","null");
                break;
            case "年" :
                //Log.d("hello","11111");
                //cursor = cursorManager.initCur_mem_year("out");
                UpdateRvdata(url_year_bar,"out","null","null");
                break;
            default:
                break;
        }
//        if (!cursor.moveToFirst()) {
//            Toast.makeText(getContext(),"选择该时间段无数据", Toast.LENGTH_SHORT).show();
//        }
//        if (cursor.moveToFirst()) {
//            do {
//                String title = cursor.getString(0);
//                Member_out.add(title);
//                double total = cursor.getDouble(1);
//                out.add(total);
//            } while (cursor.moveToNext());
//        }

        while (!final_bar_out);

        //Cursor cursor_in = cursorManager.initCur_mem("in");
        //UpdateRvdata(url_month_bar,"in","null","null");

        switch (time)
        {
            case "天":
                //cursor_in = cursorManager.initCur_mem_day("in");
                UpdateRvdata(url_date_bar,"in","null","null");
                break;
            case "周":
                //cursor_in = cursorManager.initCur_mem_week("in");
                UpdateRvdata(url_week_bar,"in","null","null");
                break;
            case "月":
                //cursor_in = cursorManager.initCur_mem_week("in");
                UpdateRvdata(url_month_bar,"in","null","null");
                break;
            case "年" :
                //Log.d("hello","11111");
                //cursor_in = cursorManager.initCur_mem_year("in");
                UpdateRvdata(url_year_bar,"in","null","null");
                break;
            default:
                break;
        }
//        if (cursor_in.moveToFirst()) {
//            do {
//                String title = cursor_in.getString(0);
//                Member_in.add(title);
//                double total = cursor_in.getDouble(1);
//                in.add(total);
//            } while (cursor_in.moveToNext());
//        }

        while(!final_bar_in);

        float me = 0f;
        float fath  = 0f;
        float math = 0f;
        for(int i = 0;i < temp_Member_in.size() ; i++) {
            switch (temp_Member_in.get(i))
            {
                case "我" :
                    me = (float) (me + temp_in.get(i));
                    break;
                case "爸爸" :
                    fath = (float) (fath + temp_in.get(i));
                    break;
                case "妈妈" :
                    math = (float) (math + temp_in.get(i));
                    break;
                default:
                    break;
            }
        }
        for(int i = 0;i < temp_Member_out.size() ; i++) {
            switch (temp_Member_out.get(i))
            {
                case "我" :
                    me = (float) (me - temp_out.get(i));
                    break;
                case "爸爸" :
                    fath = (float) (fath - temp_out.get(i));
                    break;
                case "妈妈" :
                    math = (float) (math - temp_out.get(i));
                    break;
                default:
                    break;
            }
        }
        income income1 = new income("我", me, R.drawable.me);
        myList.add(income1);
        income income2 = new income("爸爸", fath, R.drawable.father);
        myList.add(income2);
        income income3 = new income("妈妈", math, R.drawable.mother);
        myList.add(income3);
    }

    //////
    public void UpdateRvdata(final String url, final String type, final String member, final String firstname) {
        HashMap<String, String> params = new HashMap<>();
        switch (url) {
            case url_year:
            case url_year_bar:
            case url_year_member:
            case url_year_firstname:
                params.put("year", cursorManager.year);
                break;
            case url_month:
            case url_month_bar:
            case url_month_member:
            case url_month_firstname:
                params.put("year", cursorManager.year);
                params.put("month", cursorManager.month);
                break;
            case url_week:
            case url_week_bar:
            case url_week_member:
            case url_week_firstname:
                params.put("year", cursorManager.year);
                params.put("week", cursorManager.week);
                break;
            case url_date:
            case url_date_bar:
            case url_date_member:
            case url_date_firstname:
                Log.d("hello_name_date",cursorManager.date);
                params.put("date", cursorManager.date);
                break;
        }
        params.put("type", type);
        if(member != "null")    params.put("member",member);
        if(firstname != "null")     params.put("first",firstname);

        HttpUtil.sendGETRequestWithToken_Pie_Bar(url, params,member,firstname,new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(firstname == "null" && member == "null") {
                    switch (url){
                        case url_year:
                        case url_month:
                        case url_week:
                        case url_date:
                            parseJSONWithFastjson(response.body().string());
                            break;
                        case url_year_bar:
                        case url_month_bar:
                        case url_week_bar:
                        case url_date_bar:
                            if(type == "out")   parseJSONWithFastjson_out(response.body().string());
                            else    parseJSONWithFastjson_in(response.body().string());
                            break;
                    }
                }
                if(firstname == "null" && member != "null")     parseJSONWithFastjson(response.body().string());
                if(firstname != "null" && member == "null")     parseJSONWithFastjson_second(response.body().string());

            }
        });
    }


    public void parseJSONWithFastjson(String jsonData) {
        temp_first.clear();
        temp_price_first.clear();
        Log.d("hello_name_date","parseJson!!!!!!");
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String datajson = object.getString("data");
        JSONArray list = JSON.parseArray(datajson);

        for(int i = 0 ; i < list.size() ; i++) {
            //System.out.println("============list"+i+"=========");
            Log.d("hello_name_date_list?",String.valueOf(i));
            JSONObject jsonObject = list.getJSONObject(i);
            Double price = jsonObject.getDouble("balance") == null ? 0 : jsonObject.getDouble("balance");
            //System.out.println("price"+price);
            Log.d("hello_name_date",String.valueOf(price));
            temp_price_first.add(price);
            String first = jsonObject.getString("first") == null ? "null" : jsonObject.getString("first");
            //System.out.println("first"+first);
            Log.d("hello_name_date",first);
            temp_first.add(first);
        }
        Log.d("hello_name_date",String.valueOf(temp_first));
        Log.d("hello_name_date",String.valueOf(temp_price_first));
        final_pie_firstRv = true;


    }


    public void parseJSONWithFastjson_second(String jsonData) {
        temp_second.clear();
        temp_price_second.clear();
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String datajson = object.getString("data");
        JSONArray list = JSON.parseArray(datajson);

        Log.d("hello_name",datajson);

        for(int i = 0 ; i < list.size() ; i++) {
            //System.out.println("============list"+i+"=========");
            JSONObject jsonObject = list.getJSONObject(i);
            Double price = jsonObject.getDouble("balance") == null ? 0 : jsonObject.getDouble("balance");
            //System.out.println("price"+price);
            Log.d("hello_name_price",String.valueOf(price));
            temp_price_second.add(price);
            String second = jsonObject.getString("second") == null ? "null" : jsonObject.getString("second");
            //System.out.println("first"+first);
            Log.d("hello_name_second",second);
            temp_second.add(second);
        }
        Log.d("hellopie","33333333333");
        final_pie_secondRv = true;

    }

    public void parseJSONWithFastjson_out(String jsonData) {
        temp_out.clear();
        temp_Member_out.clear();
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String datajson = object.getString("data");
        JSONArray list = JSON.parseArray(datajson);

        System.out.println(datajson);

        for(int i = 0 ; i < list.size() ; i++) {
            //System.out.println("============list"+i+"=========");
            JSONObject jsonObject = list.getJSONObject(i);
            Double price = jsonObject.getDouble("balance") == null ? 0 : jsonObject.getDouble("balance");
            //System.out.println("price"+price);
            temp_out.add(price);
            String member = jsonObject.getString("member") == null ? "null" : jsonObject.getString("member");
            //System.out.println("first"+first);
            temp_Member_out.add(member);
        }
        Log.d("hellopie","33333333333");
        final_bar_out = true;

    }

    public void parseJSONWithFastjson_in(String jsonData) {
        temp_in.clear();
        temp_Member_in.clear();
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String datajson = object.getString("data");
        JSONArray list = JSON.parseArray(datajson);

        for(int i = 0 ; i < list.size() ; i++) {
            //System.out.println("============list"+i+"=========");
            JSONObject jsonObject = list.getJSONObject(i);
            Double price = jsonObject.getDouble("balance") == null ? 0 : jsonObject.getDouble("balance");
            //System.out.println("price"+price);
            temp_in.add(price);
            String member = jsonObject.getString("member") == null ? "null" : jsonObject.getString("member");
            //System.out.println("first"+first);
            temp_Member_in.add(member);
        }
        Log.d("hellopie","33333333333");
        final_bar_in = true;

    }

}


