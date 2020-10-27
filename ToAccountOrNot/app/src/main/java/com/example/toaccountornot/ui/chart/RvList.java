package com.example.toaccountornot.ui.chart;

import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.toaccountornot.R;
import com.example.toaccountornot.utils.Accounts;
import com.github.mikephil.charting.charts.BarChart;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class RvList {
    public List<income> myList = new ArrayList<>();
    public  CursorManager cursorManager = new CursorManager();

    public RvList (List<income> myList) {
        this.myList = myList;
    }

    public List<income> choice(int i,String cate,String time,int flag) {

        if(i == 0)  initincome(cate,time,flag);
        if(i == 1)  initoutcome(cate,time,flag);
        if(i == 2)  initpeople(time,flag);
        return myList;
    }

    // 饼状图收入的流水展示
    private void initincome(String cate,String time,int flag) {
        myList.clear();
        List<String> first = new ArrayList<>();
        List<Double> price = new ArrayList<>();
        List<String> second = new ArrayList<>();
        List<Double> price_second = new ArrayList<>();


        if(flag != 0)  {
            cursorManager.change_cur(flag,time);
        }
        Cursor cursor = cursorManager.initCur_one("in");
        switch (time)
        {
            case "天":
                cursor = cursorManager.initCur_one_day("in");
                break;
            case "周":
                cursor = cursorManager.initCur_one_week("in");
                break;
            case "年" :
                //Log.d("hello","11111");
                cursor = cursorManager.initCur_one_year("in");
                break;
            default:
                break;
        }
        if (!cursor.moveToFirst()) {
            Toast.makeText(getContext(),"选择该时间段无收入数据", Toast.LENGTH_SHORT).show();
        }

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                first.add(title);
                double total = cursor.getDouble(1);
                price.add(total);
            } while (cursor.moveToNext());
        }

        // 二级流水显示
        if(!cate.equals("一")) {
            //Log.d("hello",cate);
            int first_name = Integer.parseInt(cate);
            Log.d("hello", String.valueOf(first_name));
            Cursor cursor2 = cursorManager.initCur_two("in",first.get(first_name));
            switch (time)
            {
                case "天":
                    Log.d("hello_day","1111");
                    cursor2 = cursorManager.initCur_two_day("in",first.get(first_name));
                    break;
                case "周":
                    cursor2 = cursorManager.initCur_two_week("in",first.get(first_name));
                    break;
                case "年" :
                    //Log.d("hello","11111");
                    cursor2 = cursorManager.initCur_two_year("in",first.get(first_name));
                    break;
                default:
                    break;
            }
            if (cursor2.moveToFirst()) {
                do {
                    String title = cursor2.getString(0);
                    //Log.d("hello_second",title);
                    second.add(title);
                    double total = cursor2.getDouble(1);
                    //Log.d("hello_price", String.valueOf(total));
                    price_second.add(total);
                    // 设置饼状图
                } while (cursor2.moveToNext());
            }
            for(int i = 0;i < second.size();i++) {
                income income = new income(second.get(i),price_second.get(i),R.drawable.food);
                myList.add(income);
            }
        }
        else {
            for(int i = 0;i < first.size();i++) {
                income income = new income(first.get(i),price.get(i),R.drawable.food);
                myList.add(income);
            }
        }

    }

    // 饼状图支出的流水展示
    private void initoutcome(String cate,String time,int flag) {
        myList.clear();
        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();
        List<Double> price = new ArrayList<>();
        List<Double> price_second = new ArrayList<>();

        if(flag != 0)  {
            cursorManager.change_cur(flag,time);
        }
        //CursorManager cursorManager = new CursorManager();
        Cursor cursor = cursorManager.initCur_one("out");

        switch (time)
        {
            case "天":
                cursor = cursorManager.initCur_one_day("out");
                break;
            case "周":
                cursor = cursorManager.initCur_one_week("out");
                break;
            case "年" :
                //Log.d("hello","11111");
                cursor = cursorManager.initCur_one_year("out");
                break;
            default:
                break;
        }

        if (!cursor.moveToFirst()) {
            Toast.makeText(getContext(),"选择该时间段无支出数据", Toast.LENGTH_SHORT).show();
        }
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                first.add(title);
                double total = cursor.getDouble(1);
                price.add(total);
            } while (cursor.moveToNext());
        }
        // 二级流水显示
        if(!cate.equals("一")) {
            //Log.d("hello",cate);
            int first_name = Integer.parseInt(cate);
            //Log.d("hello", String.valueOf(first_name));
            Cursor cursor2 = cursorManager.initCur_two("out",first.get(first_name));
            switch (time)
            {
                case "天":
                    cursor2 = cursorManager.initCur_two_day("out",first.get(first_name));
                    break;
                case "周":
                    cursor2 = cursorManager.initCur_two_week("out",first.get(first_name));
                    break;
                case "年" :
                    //Log.d("hello","11111");
                    cursor2 = cursorManager.initCur_two_year("out",first.get(first_name));
                    break;
                default:
                    break;
            }
            if (cursor2.moveToFirst()) {
                do {
                    String title = cursor2.getString(0);
                    //Log.d("hello_second",title);
                    second.add(title);
                    double total = cursor2.getDouble(1);
                    //Log.d("hello_price", String.valueOf(total));
                    price_second.add(total);
                    // 设置饼状图
                } while (cursor2.moveToNext());
            }
            for(int i = 0;i < second.size();i++) {
                income income = new income(second.get(i),price_second.get(i),R.drawable.food);
                myList.add(income);
            }
        }
        else {
            for(int i = 0;i < first.size();i++) {
                income income = new income(first.get(i),price.get(i),R.drawable.food);
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

        if(flag != 0)  {
            cursorManager.change_cur(flag,time);
        }

        Cursor cursor = cursorManager.initCur_mem("out");
        switch (time)
        {
            case "天":
                cursor = cursorManager.initCur_mem_day("out");
                break;
            case "周":
                cursor = cursorManager.initCur_mem_week("out");
                break;
            case "年" :
                //Log.d("hello","11111");
                cursor = cursorManager.initCur_mem_year("out");
                break;
            default:
                break;
        }
        if (!cursor.moveToFirst()) {
            Toast.makeText(getContext(),"选择该时间段无数据", Toast.LENGTH_SHORT).show();
        }
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                Member_out.add(title);
                double total = cursor.getDouble(1);
                out.add(total);
            } while (cursor.moveToNext());
        }

        Cursor cursor_in = cursorManager.initCur_mem("in");
        switch (time)
        {
            case "天":
                cursor_in = cursorManager.initCur_mem_day("in");
                break;
            case "周":
                cursor_in = cursorManager.initCur_mem_week("in");
                break;
            case "年" :
                //Log.d("hello","11111");
                cursor_in = cursorManager.initCur_mem_year("in");
                break;
            default:
                break;
        }
        if (cursor_in.moveToFirst()) {
            do {
                String title = cursor_in.getString(0);
                Member_in.add(title);
                double total = cursor_in.getDouble(1);
                in.add(total);
            } while (cursor_in.moveToNext());
        }

        float me = 0f;
        float fath  = 0f;
        float math = 0f;
        for(int i = 0;i < Member_in.size() ; i++) {
            switch (Member_in.get(i))
            {
                case "我" :
                    me = (float) (me + in.get(i));
                    break;
                case "爸爸" :
                    fath = (float) (fath + in.get(i));
                    break;
                case "妈妈" :
                    math = (float) (math + in.get(i));
                    break;
                default:
                    break;
            }
        }
        for(int i = 0;i < Member_out.size() ; i++) {
            switch (Member_out.get(i))
            {
                case "我" :
                    me = (float) (me - out.get(i));
                    break;
                case "爸爸" :
                    fath = (float) (fath - out.get(i));
                    break;
                case "妈妈" :
                    math = (float) (math - out.get(i));
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
}
