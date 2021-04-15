package com.example.toaccountornot.utils;

import java.util.List;

public class DayFlow {
    private String date;
    private double income;
    private double expense;
    private List<Single> list;

    public DayFlow(String date, double income, double expense, List<Single> list) {
        this.date = date;
        this.income = income;
        this.expense = expense;
        this.list = list;
    }

    public String getDate() {
        return date;
    }

    public double getIncome() {
        return income;
    }

    public double getExpense() {
        return expense;
    }

    public List<Single> getList() {
        return list;
    }
}
