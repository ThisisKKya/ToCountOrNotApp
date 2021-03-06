package com.example.toaccountornot.utils;

import org.litepal.LitePal;

/**
 * 一天的支出和收入
 */
public class Day {
    private String date;
    private double outcome_day;
    private double income_day;
    private String card;

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public double getOutcome_day() {
        return outcome_day;
    }
    public double getIncome_day() {
        return income_day;
    }

    public Day(String date) {
        this.date = date;
        this.outcome_day = LitePal.where("inorout=? and date=?", "out", date).sum(Accounts.class, "price", Double.TYPE);
        this.income_day = LitePal.where("inorout=? and date=?", "in", date).sum(Accounts.class, "price", Double.TYPE);
    }
    public Day(String date,String card) {
        this.date = date;
        this.outcome_day = LitePal.where("inorout=? and date=?", "out", date).sum(Accounts.class, "price", Double.TYPE);
        this.income_day = LitePal.where("inorout=? and date=?", "in", date).sum(Accounts.class, "price", Double.TYPE);
        this.card = card;
    }
}
