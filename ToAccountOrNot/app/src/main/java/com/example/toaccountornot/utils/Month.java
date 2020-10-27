package com.example.toaccountornot.utils;

import org.litepal.LitePal;

/**
 * 一月的支出和收入
 */
public class Month {
    private String year;
    private String month;
    private double outcome_month;
    private double income_month;
    private String card;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public double getOutcome_month() {
        return outcome_month;
    }
    public double getIncome_month() {
        return income_month;
    }

    public Month(String year,String month) {
        this.month = month;
        this.year = year;
        Double sum_out = LitePal.where("inorout=? and date_year=? and date_month=?", "out", year, month).sum(Accounts.class, "price", Double.TYPE);
        this.outcome_month = sum_out;
        Double sum_in = LitePal.where("inorout=? and date_year=? and date_month=?", "in", year, month).sum(Accounts.class, "price", Double.TYPE);
        this.income_month = sum_in;
    }
    public Month(String year,String month, String card) {
        this.month = month;
        this.year = year;
        Double sum_out = LitePal.where("inorout=? and date_year=? and date_month=? and card=?", "out", year, month, card).sum(Accounts.class, "price", Double.TYPE);
        this.outcome_month = sum_out;
        Double sum_in = LitePal.where("inorout=? and date_year=? and date_month=? and card=?", "in", year, month, card).sum(Accounts.class, "price", Double.TYPE);
        this.income_month = sum_in;
        this.card = card;
    }
}
