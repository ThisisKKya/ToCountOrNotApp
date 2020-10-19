package com.example.toaccountornot.utils;

public class Cards {
    private String card;    //账户名
    private int cardid;     //卡的id
    private double income;     //收入
    private double outcome;    //支出
    private double surplus;    //结余

    public Cards(String card, int cardid, double income, double outcome, double surplus) {
        this.card = card;
        this.cardid = cardid;
        this.income = income;
        this.outcome = outcome;
        this.surplus = surplus;
    }

    public String getCard() {
        return card;
    }

    public int getCardid() {
        return cardid;
    }

    public double getIncome() {
        return income;
    }

    public double getOutcome() {
        return outcome;
    }

    public double getSurplus() {
        return surplus;
    }
}
