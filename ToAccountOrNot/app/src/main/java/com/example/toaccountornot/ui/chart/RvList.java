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

    public List<income> choice(int i, List<Accounts> accounts,String cate) {
        if(i == 0)  initincome(accounts);
        if(i == 1)  initoutcome(accounts,cate);
        if(i == 2)  initpeople();
        return myList;
    }

    // 饼状图收入的流水展示
    private void initincome(List<Accounts> accounts) {
        myList.clear();
        for(int i = 0;i < accounts.size();i++) {
            if(accounts.get(i).getInorout() == "in") {
                String first = accounts.get(i).getFirst();
                double price = accounts.get(i).getPrice();
                income income = new income(first,price,R.drawable.salary);
                myList.add(income);
            }
        }
    }

    // 饼状图支出的流水展示
    private void initoutcome(List<Accounts> accounts,String cate) {
        myList.clear();
        List<String> first = new ArrayList<>();
        List<Double> price = new ArrayList<>();
        String year;
        String month;
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        // 进行一级饼状图显示
        Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date_year=?" +
                            "and date_month=? and inorout=? group by first order by first desc",
                    year,
                    month,
                    "out");
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                first.add(title);
                double total = cursor.getDouble(1);
                price.add(total);
            } while (cursor.moveToNext());
        }
        if(!cate.equals("一")) {
            int it = Integer.parseInt(cate);
            String cate_one = first.get(it);
        }
        for(int i = 0;i < first.size();i++) {
            income income = new income(first.get(i),price.get(i),R.drawable.food);
            myList.add(income);
        }
    }

    // 柱状图成员流水展示
    private void initpeople() {
        myList.clear();
        income income1 = new income("我", 100f, R.drawable.me);
        myList.add(income1);
        income income2 = new income("爸爸", 100f, R.drawable.father);
        myList.add(income2);
        income income3 = new income("妈妈", 100f, R.drawable.mother);
        myList.add(income3);
    }
}
