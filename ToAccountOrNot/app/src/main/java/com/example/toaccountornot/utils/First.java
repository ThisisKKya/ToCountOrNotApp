package com.example.toaccountornot.utils;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;

public class First extends LitePalSupport {
    private String name;
    private int image;
    private ArrayList<String> second = new ArrayList<>();
    private String inorout;//转账trans，收入in，支出out，通用all(如自定义)
    private Double budget;
    private Double thisMonthCost;


    public First(String name,int image,String inorout){
        this.name = name;
        this.image = image;
        this.inorout = inorout;
    }
    public First(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ArrayList getSecond() {
        return second;
    }

    public void setSecond(ArrayList<String> second){
        this.second = second;
    }

    public String getInorout() {
        return inorout;
    }

    public void setInorout(String inorout) {
        this.inorout = inorout;
    }
    public  void setThisMonthCost(double cost) {
        this.thisMonthCost = cost;
    }
    public void setBudget(double budget){
        this.budget = budget;
    }

    public double getBudget(){return this.budget;}
    public double getCost(){return  this.thisMonthCost;}
}



