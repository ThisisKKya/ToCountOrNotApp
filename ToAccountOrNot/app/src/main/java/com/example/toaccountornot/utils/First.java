package com.example.toaccountornot.utils;

import com.example.toaccountornot.R;

import org.litepal.LitePal;
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

    public static void initFirst() {
        First firstFirst = LitePal.findFirst(First.class);
        System.out.println("===========initFirst===============");
        //数据库未初始化
        if (firstFirst == null) {
            System.out.println("=========== == nullll===============");
            //支出
            First food = new First("餐饮", R.drawable.food, "out");
            ArrayList<String> foodList = new ArrayList<String>();
            foodList.add("早饭");
            foodList.add("中饭");
            foodList.add("晚饭");
//            foodList.add("添加自定义");
            food.setSecond(foodList);
            food.setThisMonthCost(0);
            food.save();
            First shopping = new First("购物", R.drawable.shopping, "out");
            shopping.setThisMonthCost(0);
            ArrayList<String> shoppinglist = new ArrayList<String>();
            shoppinglist.add("服饰");
            shoppinglist.add("化妆品");
//            shoppinglist.add("添加自定义");
            shopping.setSecond(shoppinglist);
            shopping.save();
            First daily = new First("日用", R.drawable.daily, "out");
            daily.setThisMonthCost(0);
            ArrayList<String> dailylist = new ArrayList<String>();
            dailylist.add("洗衣液");
            dailylist.add("纸巾");
//            dailylist.add("添加自定义");
            daily.setSecond(dailylist);
            daily.save();
            First study = new First("学习", R.drawable.study, "out");
            study.setThisMonthCost(0);
            ArrayList<String> studylist = new ArrayList<String>();
            studylist.add("课本");
            studylist.add("文具");
//            studylist.add("添加自定义");
            study.setSecond(studylist);
            study.save();
            First transport = new First("交通", R.drawable.transport, "out");
            transport.setThisMonthCost(0);
            ArrayList<String> transportlist = new ArrayList<String>();
            transportlist.add("地铁");
            transportlist.add("公交");
//            transportlist.add("添加自定义");
            transport.setSecond(transportlist);
            transport.save();
            First snacks = new First("零食", R.drawable.snacks, "out");
            snacks.setThisMonthCost(0);
            ArrayList<String> snackslist = new ArrayList<String>();
            snackslist.add("饮料");
            snackslist.add("水果");
//            snackslist.add("添加自定义");
            snacks.setSecond(snackslist);
            snacks.save();
            First entertainment = new First("娱乐", R.drawable.entertainment, "out");
            ArrayList<String> enterlist = new ArrayList<String>();
            enterlist.add("电影");
            enterlist.add("聚会");
//            enterlist.add("添加自定义");
            entertainment.setSecond(enterlist);
            entertainment.save();
            First house = new First("住房", R.drawable.house, "out");
            ArrayList<String> houselist = new ArrayList<String>();
            houselist.add("租金");
            houselist.add("房贷");
//            houselist.add("添加自定义");
            house.setSecond(houselist);
            house.save();
            First doctor = new First("医疗", R.drawable.doctor, "out");
            ArrayList<String> doctorlist = new ArrayList<String>();
            doctorlist.add("药");
//            doctorlist.add("添加自定义");
            doctor.setSecond(doctorlist);
            doctor.save();
            First pet = new First("宠物", R.drawable.pet, "out");
            ArrayList<String> petlist = new ArrayList<String>();
            petlist.add("食物");
//            petlist.add("添加自定义");
            pet.setSecond(petlist);
            pet.save();
            First travel = new First("旅行", R.drawable.travel, "out");
            ArrayList<String> travellist = new ArrayList<String>();
            travellist.add("民宿");
//            travellist.add("添加自定义");
            travel.setSecond(travellist);
            travel.save();
            First sport = new First("运动", R.drawable.sport, "out");
            ArrayList<String> sportlist = new ArrayList<String>();
            sportlist.add("健身房");
//            sportlist.add("添加自定义");
            sport.setSecond(sportlist);
            sport.save();

            //收入

            First salary = new First("工资", R.drawable.salary, "in");
            ArrayList<String> salaryList = new ArrayList<String>();
//            salaryList.add("添加自定义");
            salary.setSecond(salaryList);
            salary.save();
            First parttime = new First("兼职", R.drawable.parttime, "in");
            ArrayList<String> parttimelist = new ArrayList<String>();
//            parttimelist.add("添加自定义");
            parttime.setSecond(parttimelist);
            parttime.save();
            First gift = new First("礼金", R.drawable.gift, "in");
            ArrayList<String> giftlist = new ArrayList<String>();
//            giftlist.add("添加自定义");
            gift.setSecond(giftlist);
            gift.save();
            //转账
            First transfer = new First("转账", R.drawable.transfer, "trans");
            transfer.save();
            //通用
//            First custom = new First("添加自定义", R.drawable.setting, "all");
//            custom.save();
        }
    }
}



