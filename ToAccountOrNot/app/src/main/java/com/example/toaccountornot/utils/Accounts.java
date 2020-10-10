package com.example.toaccountornot.utils;

import org.litepal.crud.LitePalSupport;
import java.util.Date;

public class Accounts extends LitePalSupport {

    private long id; //账目专属id号
    private String first;   //一级分类
    private String second;  //二级分类
    private double price;    //账目金额
    private Date time;      //记账时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
