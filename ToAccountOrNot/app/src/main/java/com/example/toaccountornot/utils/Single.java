package com.example.toaccountornot.utils;

/**
 * 某天中的一条记录
 */
public class Single {

    private long id;
    private String inorout;
    private String first;
    private String second;
    private double price;
    private String date;
    private String card;
    private String member;
    private int showday;//是否展示

    public Single(long id, String inorout, String first, String second, double price, String date, String card, String member){

        this.id = id;
        this.inorout = inorout;
        this.first = first;
        this.second = second;
        this.price = price;
        this.date = date;
        this.card = card;
        this.member = member;
    }
    public Single(long id, String inorout, String first, String second, double price, String date, String card, String member, int showday){

        this.id = id;
        this.inorout = inorout;
        this.first = first;
        this.second = second;
        this.price = price;
        this.date = date;
        this.card = card;
        this.member = member;
        this.showday = showday;
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getInorout() {
        return inorout;
    }

    public String getDate() {
        return date;
    }

    public String getSecond() {
        return second;
    }

    public String getFirst() {
        return first;
    }

    public String getCard() {
        return card;
    }

    public String getMember() {
        return member;
    }

    public int getShowday() {
        return showday;
    }

}
