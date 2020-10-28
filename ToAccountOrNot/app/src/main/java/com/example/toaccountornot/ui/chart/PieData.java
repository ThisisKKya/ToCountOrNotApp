package com.example.toaccountornot.ui.chart;

import android.database.Cursor;
import android.util.Log;

import com.example.toaccountornot.utils.Accounts;
import com.github.mikephil.charting.data.PieEntry;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieData {
    public List<PieEntry> data;
    public CursorManager cursorManager = new CursorManager();


    public PieData() {}

    // 显示一级收入饼状图
    // flag  1= 昨天 0 = 今天 1 = 明天
    public List<PieEntry> income(String time,int flag) {
        int all = 0;
        List<String> first = new ArrayList<>();
        List<Double> price = new ArrayList<>();

        //CursorManager cursorManager = new CursorManager();
        //if(flag == 0)
        if(flag != 0)  {
            cursorManager.change_cur(flag,time);
        }
        Cursor cursor = cursorManager.initCur_one("in");
        //Log.d("hello",time);
        switch (time)
        {
            case "天":
                cursor = cursorManager.initCur_one_day("in");
                break;
            case "周":
                cursor = cursorManager.initCur_one_week("in");
                break;
            case "年" :
                //
                cursor = cursorManager.initCur_one_year("in");
                break;
            default:
                break;
        }
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                //Log.d("hello_li",title);
                first.add(title);
                double total = cursor.getDouble(1);
                //Log.d("hello", String.valueOf(total));
                price.add(total);
                all += total;
                // 设置饼状图
            } while (cursor.moveToNext());
        }
        int len = first.size();
        //  赋值饼状图
        data = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            data.add(new PieEntry((float) (price.get(i)/all),first.get(i)));
        }
        return data;
    }


    // 显示一级支出饼状图
    public  List<PieEntry> outcome(String time,int flag) {
        int all = 0;
        List<String> first = new ArrayList<>();
        List<Double> price = new ArrayList<>();


        if(flag != 0)  {
            cursorManager.change_cur(flag,time);
        }
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
                Log.d("hello_li",title);
                first.add(title);
                double total = cursor.getDouble(1);
                Log.d("hello_li", String.valueOf(total));
                price.add(total);
                all += total;
                // 设置饼状图
            } while (cursor.moveToNext());
        }

        int len = first.size();
        //  赋值饼状图
        data = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            data.add(new PieEntry((float) (price.get(i)/all),first.get(i)));
        }
        return data;
    }
}
