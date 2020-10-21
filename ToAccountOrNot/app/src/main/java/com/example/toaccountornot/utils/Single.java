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

    public Single(long id, String inorout, String first, String second, double price, String date){

        this.id = id;
        this.inorout = inorout;
        this.first = first;
        this.second = second;
        this.price = price;
        this.date = date;

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

    public void setFirst(String first) {
        this.first = first;
    }

}
