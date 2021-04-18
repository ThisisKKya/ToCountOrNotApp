package com.example.toaccountornot.utils;

import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.annotation.JSONField;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class Cards extends LitePalSupport {
    @JSONField(name = "name")
    private String card;    //账户名
    @JSONField(name = "note")
    private String remark;  //备注
    private int image;     //图片
    private double income;     //收入
    @JSONField(name = "expense")
    private double outcome;    //支出
    @JSONField(name = "balance")
    private double surplus;    //结余

    public void setCards(String card, String remark, int image, double income, double outcome, double surplus) {
        this.card = card;
        this.remark = remark;
        this.image = image;
        this.income = income;
        this.outcome = outcome;
        this.surplus = surplus;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCard() {
        return card;
    }

    public double getIncome() {
//        income = 0;
//        List<Accounts> accounts = LitePal.where("card = ? and inorout = ?" ,card,"in")
//                                    .find(Accounts.class);
//        for (Accounts account : accounts) {
//            income += account.getPrice();
//        }
//        List<Accounts> trans = LitePal.where("inorout = ? and second like ?","trans","%->"+card)
//                                    .find(Accounts.class);
//        for (Accounts account : trans) {
//            income += account.getPrice();
//            Log.d("trans",account.getSecond());
//            Log.d("trans",card);
//        }
        return income;
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

    public double getOutcome() {
//        outcome = 0;
//        List<Accounts> accounts = LitePal.where("card = ? and inorout = ?" ,card,"out")
//                .find(Accounts.class);
//        for (Accounts account : accounts) {
//            outcome += account.getPrice();
//        }
//        List<Accounts> trans = LitePal.where("inorout = ? and second like ?","trans",card+"->%")
//                .find(Accounts.class);
//        for (Accounts account : trans) {
//            outcome += account.getPrice();
//            Log.d("trans",account.getSecond());
//            Log.d("trans",card);
//        }
        return outcome;
    }

    public double getSurplus() {
//        surplus = getIncome()-getOutcome();
        return surplus;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
