package com.example.toaccountornot.utils;

import org.litepal.crud.LitePalSupport;

public class TotalBudget extends LitePalSupport {
    private long id;
    private double Budget;
    public TotalBudget(double budget) {
        this.Budget = budget;
    }
    public TotalBudget(){};
    public void setBudget(double budget) {
        this.Budget = budget;
    }
    public double getBudget() {
        return this.Budget;
    }
}
