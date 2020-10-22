package com.example.toaccountornot.ui.chart;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class PieData {
    public List<PieEntry> data;

    public PieData() {}

    public List<PieEntry> income() {
        int len = 3;
        String[] first = {"工资", "兼职", "礼金"};
        double[] price = {400f, 200f, 100f};

        data = new ArrayList<>();

        //  赋值饼状图
        for (int i = 0; i < len; i++) {
            data.add(new PieEntry((float) (price[i]/700),first[i]));
        }
        return data;
    }

    public  List<PieEntry> outcome() {
        int len = 3;
        String[] first = {"餐饮", "交通", "宠物"};
        double[] price = {25f, 5f, 100f};

        data = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            data.add(new PieEntry((float) (price[i]/130),first[i]));
        }
        return data;
    }
}
