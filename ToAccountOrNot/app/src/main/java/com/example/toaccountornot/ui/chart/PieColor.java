package com.example.toaccountornot.ui.chart;

import android.graphics.Color;
import android.util.Log;

import com.example.toaccountornot.utils.Accounts;

import java.util.ArrayList;
import java.util.List;

public class PieColor {
    public List<Integer> color;

    public PieColor () {
    }

    public List<Integer> initcolor(List<Accounts> accounts,String cate,int len) {

        String[] c = {"#d8e2dc","#ffe5d9","#ffcad4","#A8DEE0","#F9E2AE","#FBC78D","#A7D676"
                        ,"#D6A3DC","#EABEBF","75CCE8","A5DEE5"};

        color = new ArrayList<>();

        for(int i = 0;i < len;i++) {
            //if(cate == accounts.get(i).getInorout()) {
                color.add(Color.parseColor(c[i]));
            //}
        }

        return  color;
    }

}
