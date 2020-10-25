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

    public PieData() {}

    public List<PieEntry> income(List<Accounts> accounts,String category) {

        int len = accounts.size();
        int all = 0;
        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();
        List<Double> price = new ArrayList<>();
        for(int i = 0;i < len;i++) {
            if(accounts.get(i).getInorout() == "in") {
                first.add(accounts.get(i).getFirst());
                second.add(accounts.get(i).getSecond());
                price.add(accounts.get(i).getPrice());
                all += accounts.get(i).getPrice();
            }
        }
        data = new ArrayList<>();
        if(category == "一") {
            len = first.size();
        }
        else len = second.size();
        //  赋值饼状图

        for (int i = 0; i < len; i++) {
            if(category == "一")     data.add(new PieEntry((float) (price.get(i)/all),first.get(i)));
            else data.add(new PieEntry((float) (price.get(i)/all),second.get(i)));
        }
        return data;
    }



    public  List<PieEntry> outcome(List<Accounts> accounts,String category) {
        int len = accounts.size();
        int all = 0;
        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();
        List<Double> price = new ArrayList<>();


        String year;
        String month;
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        // 进行一级饼状图显示
        // 从数据库拿出支出的数据，并且将重复一级分类合并
        if(category == "一") {
            Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date_year=?" +
                            "and date_month=? and inorout=? group by first order by first desc",
                    year,
                    month,
                    "out");
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
        }

        if(category == "一") {
            len = first.size();
        }
        else len = second.size();
        //  赋值饼状图

        data = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            if(category == "一") {
                data.add(new PieEntry((float) (price.get(i)/all),first.get(i)));
            }
            else data.add(new PieEntry((float) (price.get(i)/all),second.get(i)));
        }
        return data;
    }
}
