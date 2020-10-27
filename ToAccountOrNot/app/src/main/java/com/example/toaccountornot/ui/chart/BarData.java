package com.example.toaccountornot.ui.chart;

import android.accounts.Account;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.toaccountornot.utils.Accounts;
import com.github.mikephil.charting.data.BarEntry;

import org.litepal.LitePal;
import org.litepal.exceptions.DataSupportException;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BarData {
    public List<BarEntry> data;
    public List<String> Member_in;
    public List<String> Member_out;
    public List<Double> in;
    public List<Double> out;
    public CursorManager cursorManager = new CursorManager();

    public BarData(String time,int flag) {
        Member_in = new ArrayList<>();
        Member_out = new ArrayList<>();
        in = new ArrayList<>();
        out = new ArrayList<>();

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
        if (cursor.moveToFirst()) {
            do {
                String member = cursor.getString(0);
                Member_out.add(member);
                //Log.d("hello_mem",member);
                double total = cursor.getDouble(1);
                out.add(total);
                //Log.d("hello_mem", String.valueOf(total));
                // 设置饼状图
            } while (cursor.moveToNext());
        }

        // 按照“in”查找数据获得成员所有收入
        //CursorManager cursorManager = new CursorManager();
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
                String member = cursor_in.getString(0);
                Member_in.add(member);
                double total = cursor_in.getDouble(1);
                Log.d("hello_mem", String.valueOf(total));
                in.add(total);
                // 设置饼状图
            } while (cursor_in.moveToNext());
        }
    }


    //  Barchart成员收入
    public List<BarEntry> income(List<Accounts> accounts) {
        data = new ArrayList<>();
        int father = find_mem_in("爸爸");
        int mather = find_mem_in("妈妈");
        int me = find_mem_in("我");

        Log.d("hello_mem_father", String.valueOf(father));
        Log.d("hello_mem_mather", String.valueOf(mather));
        Log.d("hello_mem_me", String.valueOf(me));
        if(father == -1) {
            BarEntry   y1 = new BarEntry(1f,0);
            data.add(y1);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry   y1 = new BarEntry(1f , Float.parseFloat(df.format(in.get(father))));
            data.add(y1);
        }

        if(me == -1) {
            BarEntry   y1 = new BarEntry(4f , 0);
            data.add(y1);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry   y1 = new BarEntry(4f , Float.parseFloat(df.format(in.get(me))));
            data.add(y1);
        }
        if(mather == -1) {
            BarEntry  y1 = new BarEntry(7f , 0);
            data.add(y1);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry   y1 = new BarEntry(7f , Float.parseFloat(df.format(in.get(mather))));
            data.add(y1);
        }


        return data;
    }

    //  Barchart成员支出
    public List<BarEntry> outcome(List<Accounts> accounts) {
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
            BarEntry   y1 = new BarEntry(2f , Float.parseFloat(df.format(out.get(father))));
            data.add(y1);
        }
        if(me == -1) {
            BarEntry   y1 = new BarEntry(5f , 0);
            data.add(y1);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry   y1 = new BarEntry(5f , Float.parseFloat(df.format(out.get(me))));
            data.add(y1);
        }
        if(mather == -1) {
            BarEntry  y1 = new BarEntry(8f , 0);
            data.add(y1);
        }
        else {
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry   y1 = new BarEntry(8f , Float.parseFloat(df.format(out.get(mather))));
            data.add(y1);
        }


        /*float xA = 0f;
        float[] x = new float[]{1f,4f,7f};
        for(int i = 0 ; i < Member_out.size() ; i++) {
            DecimalFormat df = new DecimalFormat("#.##");
            switch (Member_out.get(i))
            {
                case"我":
                    BarEntry y1 = new BarEntry(2f , Float.parseFloat(df.format(out.get(i))));
                    data.add(y1);
                    break;
                case "爸爸":
                    BarEntry y2 = new BarEntry(5f , Float.parseFloat(df.format(out.get(i))));
                    data.add(y2);
                    //float xY = i;
                    break;
                case "妈妈":
                    BarEntry y3 = new BarEntry(8f, Float.parseFloat(df.format(out.get(i))));
                    data.add(y3);
                    break;
            }

        }*/
        return data;
    }

    // 找不到成员返回-1
    private int find_mem_in(String name) {
        int i;
        int flag = 0;
        for(i = 0 ; i < Member_in.size();i++) {
            Log.d("hello_member",Member_in.get(i));
            Log.d("hello_member",name);
            if(name.equals(Member_in.get(i))) {
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
        for(i = 0 ; i < Member_out.size();i++) {
            if(name.equals(Member_out.get(i))) {
                flag = 1;
                break;
            }
        }
        if(flag == 1)   return i;
        else return -1;
    }
}
