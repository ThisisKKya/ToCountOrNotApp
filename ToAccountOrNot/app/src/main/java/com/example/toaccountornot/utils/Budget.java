package com.example.toaccountornot.utils;

public class Budget {
    private  String budgetName;
    private  double budgetNum;
    private int imageId;

    public Budget(String name, int imageId,double money ) {
        this.budgetName = name;
        this.budgetNum = money;
        this.imageId = imageId;
    }
    public  String getBudgetName(){return  budgetName;}
    public double getBudgetNum(){return budgetNum;}
    public int getImageId(){return imageId;}
}
