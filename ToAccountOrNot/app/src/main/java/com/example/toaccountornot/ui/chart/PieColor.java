package com.example.toaccountornot.ui.chart;

import android.graphics.Color;
import android.util.Log;

import com.example.toaccountornot.R;
import com.example.toaccountornot.utils.Accounts;

import java.util.ArrayList;
import java.util.List;

public class PieColor {
    public List<Integer> color;

    public PieColor () {
    }

    public List<Integer> initcolor(String cate,int len) {

        String[] c = {
                "#d8e2dc","#ffe5d9","#ffcad4","#A8DEE0","#F9E2AE",
                "#FBC78D","#A7D676","#A0C3E2","#EABEBF","#75CCE8",
                "#A5DEE5","#F9E2AE","#C3DBC2","#A7D1DF","#E5C1CD",
                "#A8E0B7","#FFA0A0","#9DC3E2","#9DD2DB","#FADCE4"
        };

        color = new ArrayList<>();
        for(int i = 0;i < len;i++) {
            color.add(Color.parseColor(c[i]));
        }

        return  color;
    }

}
