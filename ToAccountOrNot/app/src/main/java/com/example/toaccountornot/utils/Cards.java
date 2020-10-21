package com.example.toaccountornot.utils;

import org.litepal.crud.LitePalSupport;

public class Cards extends LitePalSupport {
    private String card;    //账户名
    private String remark;  //备注
    private int cardtype;    //卡的类型
    private int cardid;     //卡的id
    private double income;     //收入
    private double outcome;    //支出
    private double surplus;    //结余

    public void setCards(String card, String remark, int cardtype, int cardid, double income, double outcome, double surplus) {
        this.card = card;
        this.remark = remark;
        this.cardtype = cardtype;
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

    public void setCard(String card) {
        this.card = card;
    }

    public void setCardid(int cardid) {
        this.cardid = cardid;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setOutcome(double outcome) {
        this.outcome = outcome;
    }

    public void setSurplus(double surplus) {
        this.surplus = surplus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCardtype() {
        return cardtype;
    }

    public void setCardtype(int cardtype) {
        this.cardtype = cardtype;
    }
}
