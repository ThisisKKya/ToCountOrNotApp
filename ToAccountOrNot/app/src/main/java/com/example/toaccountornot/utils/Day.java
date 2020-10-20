package com.example.toaccountornot.utils;

import android.content.Context;

import org.litepal.LitePal;

import java.util.List;


public class Day {
    private String date;
    private double outcome_day = 0;
    private double income_day = 0;

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public double getOutcome_day() {
        return outcome_day;
    }
    public double getIncome_day() {
        return income_day;
    }

    public Day(String date, Context context) {
        this.date = date;
        List<Accounts> out = LitePal.select("sum(price)").where("inorout=? and date=?", "out", date).find(Accounts.class);
        this.outcome_day = out.get(0).getPrice();
        List<Accounts> in = LitePal.select("sum(price)").where("inorout=? and date=?", "in", date).find(Accounts.class);
        this.income_day = in.get(0).getPrice();
    }
}
