package com.example.toaccountornot.ui.chart;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class BarData {
    public List<BarEntry> data;

    public BarData() {}

    //  Barchart成员支出
    public List<BarEntry> income() {
        data = new ArrayList<>();
        BarEntry x1 = new BarEntry(0f , 10000f) ;
        data.add(x1) ;
        BarEntry x2 = new BarEntry(3f , 11000f) ;
        data.add(x2) ;
        BarEntry x3 = new BarEntry(6f , 9000f) ;
        data.add(x3) ;
        return data;
    }

    //  Barchart成员收入
    public List<BarEntry> outcome() {
        data = new ArrayList<>();
        BarEntry y1 = new BarEntry(1f , 2000f) ;
        data.add(y1) ;
        BarEntry y2 = new BarEntry(4f , 15000f) ;
        data.add(y2) ;
        BarEntry y3 = new BarEntry(7f , 10500f) ;
        data.add(y3) ;
        return data;
    }
}
