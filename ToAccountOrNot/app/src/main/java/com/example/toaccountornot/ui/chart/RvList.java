package com.example.toaccountornot.ui.chart;

import com.example.toaccountornot.R;
import com.example.toaccountornot.utils.Accounts;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.List;

public class RvList {
    public List<income> myList = new ArrayList<>();

    public RvList (List<income> myList) {
        this.myList = myList;
    }

    public List<income> choice(int i, List<Accounts> accounts) {
        if(i == 0)  initincome(accounts);
        if(i == 1)  initoutcome();
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
    private void initoutcome() {
        myList.clear();
        income income1 = new income("餐饮", 100f, R.drawable.food);
        myList.add(income1);
        income income2 = new income("购物", 100f, R.drawable.shopping);
        myList.add(income2);
        income income3 = new income("日用", 100f, R.drawable.daily);
        myList.add(income3);
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
