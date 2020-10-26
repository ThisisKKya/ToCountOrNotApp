package com.example.toaccountornot.ui.chart;

import android.database.Cursor;
import android.util.Log;

import com.example.toaccountornot.R;
import com.example.toaccountornot.utils.Accounts;
import com.github.mikephil.charting.charts.BarChart;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RvList {
    public List<income> myList = new ArrayList<>();

    public RvList (List<income> myList) {
        this.myList = myList;
    }

    public List<income> choice(int i, List<Accounts> accounts,String cate,String time) {

        if(i == 0)  initincome(accounts,cate,time);
        if(i == 1)  initoutcome(accounts,cate,time);
        if(i == 2)  initpeople();
        return myList;
    }

    // 饼状图收入的流水展示
    private void initincome(List<Accounts> accounts,String cate,String time) {
        myList.clear();
        List<String> first = new ArrayList<>();
        List<Double> price = new ArrayList<>();
        List<String> second = new ArrayList<>();
        List<Double> price_second = new ArrayList<>();


        CursorManager cursorManager = new CursorManager();
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
    private void initoutcome(List<Accounts> accounts,String cate,String time) {
        myList.clear();
        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();
        List<Double> price = new ArrayList<>();
        List<Double> price_second = new ArrayList<>();
        CursorManager cursorManager = new CursorManager();
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
    private void initpeople() {
        myList.clear();/*
        String year;
        String month;
        List<String>Member_in = new ArrayList<>();
        List<String>Member_out = new ArrayList<>();
        List<Double>in = new ArrayList<>();
        List<Double>out = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        Cursor cursor = LitePal.findBySQL("select member,sum(price) from Accounts where date_year=?" +
                        "and date_month=? and inorout=? group by member order by member desc",
                year,
                month,
                "out");
        if (cursor.moveToFirst()) {
            do {
                String member = cursor.getString(0);
                Member_out.add(member);
                Log.d("hello_mem",member);
                double total = cursor.getDouble(1);
                out.add(total);
                Log.d("hello_mem", String.valueOf(total));
                // 设置饼状图
            } while (cursor.moveToNext());
        }

        // 按照“in”查找数据获得成员所有收入
        Cursor cursor_in = LitePal.findBySQL("select member,sum(price) from Accounts where date_year=?" +
                        "and date_month=? and inorout=? group by member order by member desc",
                year,
                month,
                "in");
        if (cursor_in.moveToFirst()) {
            do {
                String member = cursor_in.getString(0);
                Member_in.add(member);
                double total = cursor_in.getDouble(1);
                Log.d("hello_mem", String.valueOf(total));
                in.add(total);
                // 设置饼状图
            } while (cursor_in.moveToNext());
        }*/
        income income1 = new income("我", 100f, R.drawable.me);
        myList.add(income1);
        income income2 = new income("爸爸", 100f, R.drawable.father);
        myList.add(income2);
        income income3 = new income("妈妈", 100f, R.drawable.mother);
        myList.add(income3);
    }
}
