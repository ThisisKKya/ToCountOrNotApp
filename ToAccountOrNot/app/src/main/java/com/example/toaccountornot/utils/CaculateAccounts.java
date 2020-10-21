package com.example.toaccountornot.utils;

import org.litepal.LitePal;

import java.util.List;

public class CaculateAccounts {
    private double account_sum_in = 0;
    private double account_sum_out = 0;

    private double cal_in(String cardname){
        List<Cards> cards = LitePal.select("card").where("card = ?",cardname).find(Cards.class);
        for (Cards card:cards)
        {
            List<Accounts> incomes = LitePal.select("price")
                    .where("inorout = ? and card = ?","in"+card)
                    .find(Accounts.class);
            for (Accounts income:incomes)
                account_sum_in += income.getPrice();
        }
        return account_sum_in;
    }

    private double cal_out(String cardname){
        List<Cards> cards = LitePal.select("card").where("card = ?",cardname).find(Cards.class);
        for (Cards card:cards)
        {
            List<Accounts> outcomes = LitePal.select("price")
                    .where("inorout = ? and card = ?","out"+card)
                    .find(Accounts.class);
            for (Accounts outcome:outcomes)
                account_sum_out += outcome.getPrice();
        }
        return account_sum_out;
    }

    private double cal_sur(String cardname){
        return cal_in(cardname)-cal_out(cardname);
    }

    public double getaccount_in(String cardname) {
        return cal_in(cardname);
    }

    public double getaccount_out(String cardname) {
        return cal_out(cardname);
    }

    public double getaccount_sur(String cardname) {
        return cal_sur(cardname);
    }
}

