package com.example.toaccountornot.ui.chart;

import android.provider.ContactsContract;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class income  {
    private String name;    // 分类名称
    private double price;   // 账目金额
    //private Date time;      // 记账时间
    private int imageId;

    public income(String name,double price,int imageId) {
        this.name = name;
        this.price = price;
        //this.time = time;
        this.imageId = imageId;
    }

    public String getName() {return name;}

    public double getPrice() {return price;}

    //public Date getTime() {return time;}

    public int getImageId() {return imageId;}
}
