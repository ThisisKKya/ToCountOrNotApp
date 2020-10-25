package com.example.toaccountornot.ui.chart;

import android.util.Log;

import com.example.toaccountornot.utils.Accounts;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
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
        for(int i = 0;i < len;i++) {
            if(accounts.get(i).getInorout() == "out") {
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
        Log.d("hello_out", String.valueOf(len));
        //  赋值饼状图
        for (int i = 0; i < len; i++) {
            if(category == "一")     data.add(new PieEntry((float) (price.get(i)/all),first.get(i)));
            else data.add(new PieEntry((float) (price.get(i)/all),second.get(i)));
        }
        return data;
    }
}
