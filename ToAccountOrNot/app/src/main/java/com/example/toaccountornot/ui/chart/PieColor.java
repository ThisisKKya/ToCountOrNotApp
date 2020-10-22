package com.example.toaccountornot.ui.chart;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class PieColor {
    public List<Integer> color;

    public PieColor () {
    }

    public List<Integer> initcolor() {
        int len = 3;
        String[] c = {"#d8e2dc","#ffe5d9","#ffcad4"};

        color = new ArrayList<>();

        for(int i = 0;i < len;i++) {
            color.add(Color.parseColor(c[i]));
        }
        return  color;
    }
}
