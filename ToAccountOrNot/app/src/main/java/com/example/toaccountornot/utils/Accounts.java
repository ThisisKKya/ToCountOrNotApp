package com.example.toaccountornot.utils;

import com.alibaba.fastjson.annotation.JSONField;

import org.litepal.crud.LitePalSupport;
import java.util.Date;

public class Accounts extends LitePalSupport {

    @JSONField(serialize = false)
    private long id; //账目专属id号
    private String first;   //一级分类
    private String second;  //二级分类
    private double price;    //账目金额
//    private Date time;      //记账时间
    private String card;    //账户
    private String member;  //成员
    // 为测试流水新增的
    private String date;
    @JSONField(name = "year")
    private String date_year;
    @JSONField(name = "month")
    private String date_month;
    @JSONField(name = "week")
    private String date_week;
    @JSONField(name = "type")
    private String inorout; //收入支出类型

    /*
    public Accounts(long id,String first,String second,double price,String card,
                    String member,String date,String date_year,String date_month,String inorout) {
        this.id = id;
        this.first = first;
        this.second = second;
        this.price = price;
        this.card = card;
        this.member = member;
        this.date = date;
        this.date_year = date_year;
        this.date_month = date_month;
        this.inorout = inorout;
    }*/

    public String getDate_week() {
        return date_week;
    }

    public void setDate_week(String date_week) {
        this.date_week = date_week;
    }

    public String getDate_year() {
        return date_year;
    }

    public void setDate_year(String date_year) {
        this.date_year = date_year;
    }

    public String getDate_month() {
        return date_month;
    }

    public void setDate_month(String date_month) {
        this.date_month = date_month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInorout() {
        return inorout;
    }

    public void setInorout(String inorout) {
        this.inorout = inorout;
    }

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

//    public Date getTime() {
//        return time;
//    }
//
//    public void setTime(Date time) {
//        this.time = time;
//    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "id=" + id +
                ", first='" + first + '\'' +
                ", second='" + second + '\'' +
                ", price=" + price +
                ", card='" + card + '\'' +
                ", member='" + member + '\'' +
                ", date='" + date + '\'' +
                ", date_year='" + date_year + '\'' +
                ", date_month='" + date_month + '\'' +
                ", date_week='" + date_week + '\'' +
                ", inorout='" + inorout + '\'' +
                '}';
    }
}
