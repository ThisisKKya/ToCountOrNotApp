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

    public BarData() {
        String year;
        String month;
        Member_in = new ArrayList<>();
        Member_out = new ArrayList<>();
        in = new ArrayList<>();
        out = new ArrayList<>();
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
        Cursor cursor_in = LitePal.findBySQL("select member,sum(price) from Accounts where date_year=?" +
                        "and date_month=? and inorout=? group by member order by member desc",
                year,
                month,
                "in");
        if (cursor_in.moveToFirst()) {
            do {
                String member = cursor.getString(0);
                Member_in.add(member);
                double total = cursor.getDouble(1);
                Log.d("hello_mem", String.valueOf(total));
                in.add(total);
                // 设置饼状图
            } while (cursor.moveToNext());
        }
    }

    //  Barchart成员收入
    public List<BarEntry> income(List<Accounts> accounts) {
        data = new ArrayList<>();
        float[] x = new float[]{0f,3f,6f};
        BarEntry me = new BarEntry(0f , 100f) ;   // "爸爸"的收入
        data.add(me) ;
        BarEntry father = new BarEntry(3f , 110f) ;   // “我”的收入
        data.add(father) ;
        BarEntry mather = new BarEntry(6f , 90f) ;    // “妈妈”的收入
        data.add(mather) ;
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
