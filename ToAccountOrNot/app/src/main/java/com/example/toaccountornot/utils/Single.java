package com.example.toaccountornot.utils;

public class Single {

    private long id = 0;
    private double income = 0;
    private double outcome = 0;
    private String inorout="";
    private String date = "";
    private String remark = "";
    private String pay_method = "";
    private String first = "";

    public Single(String first, double income, double outcome, String inorout, String pay_method, String remark, String date, long id){

        this.id = id;
        this.income = income;
        this.outcome = outcome;
        this.inorout = inorout;
        this.date = date;
        this.remark = remark;
        this.pay_method = pay_method;
        this.first = first;

    }

    public long getId() {
        return id;
    }

    public double getIncome() {
        return income;
    }

    public double getOutcome() {
        return outcome;
    }

    public String getInorout() {
        return inorout;
    }

    public String getDate() {
        return date;
    }

    public String getRemark() {
        return remark;
    }

    public String getPay_method() {
        return pay_method;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

}
