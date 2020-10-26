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

    public BarData(String time) {
        Member_in = new ArrayList<>();
        Member_out = new ArrayList<>();
        in = new ArrayList<>();
        out = new ArrayList<>();

        CursorManager cursorManager = new CursorManager();
        Cursor cursor = cursorManager.initCur_mem("out");
        switch (time)
        {
            case "天":
                cursor = cursorManager.initCur_mem_day("out");
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
        float xA = 0f;
        float[] x = new float[]{1f,4f,7f};
        for(int i = 0 ; i < Member_in.size() ; i++) {
            //Log.d("hello",Member_out.get(i));
            //Log.d("hello", String.valueOf(out.get(i)));
            switch (Member_in.get(i))
            {
                case"我":
                    xA = 1f;
                    //float xY = i;
                    break;
                case "爸爸":
                    //Log.d("hello","bababa");
                    xA = 4f;
                    //float xY = i;
                    break;
                case "妈妈":
                    xA = 7f;
                    //float xY = i;
                    break;
            }
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry y = new BarEntry(xA , Float.parseFloat(df.format(in.get(i))));
            data.add(y);
        }
        return data;
    }

    //  Barchart成员支出
    public List<BarEntry> outcome(List<Accounts> accounts) {
        data = new ArrayList<>();
        float xA = 0f;
        float[] x = new float[]{1f,4f,7f};
        for(int i = 0 ; i < Member_out.size() ; i++) {
            //Log.d("hello",Member_out.get(i));
            //Log.d("hello", String.valueOf(out.get(i)));
            switch (Member_out.get(i))
            {
                case"我":
                    xA = 1f;
                    //float xY = i;
                    break;
                case "爸爸":
                    //Log.d("hello","bababa");
                    xA = 4f;
                    //float xY = i;
                    break;
                case "妈妈":
                    xA = 7f;
                    //float xY = i;
                    break;
            }
            DecimalFormat df = new DecimalFormat("#.##");
            BarEntry y = new BarEntry(xA , Float.parseFloat(df.format(out.get(i))));
            data.add(y);
        }
        return data;
    }
}
