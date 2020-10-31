package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.toaccountornot.utils.First;

import org.litepal.LitePal;


import java.util.ArrayList;

import com.example.toaccountornot.utils.Cards;

import org.litepal.LitePal;

import java.util.List;


/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initFirst();
        initcarddata();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, PatternLockActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    /**
     * 初始化数据库
     */
    public void initFirst() {
        /**
         * 修改需要初始化的类目后需要将下方使用删除所有First库中内容，才能初始化
         * 修改完成后注释掉
         */
        LitePal.deleteAll(First.class);//这一句
        First firstFirst = LitePal.findFirst(First.class);
        //数据库未初始化
        if (firstFirst == null) {
            //Toast.makeText(SplashActivity.this, "未初始化", Toast.LENGTH_LONG).show();
           //支出

            First food = new First("餐饮",R.drawable.food,"out");
            ArrayList<String> foodList = new ArrayList<String>();
            foodList.add("早饭");
            foodList.add("中饭");
            foodList.add("晚饭");
            foodList.add("添加自定义");
            food.setSecond(foodList);
            food.save();
            First shopping = new First("购物",R.drawable.shopping,"out");
            ArrayList<String> shoppinglist = new ArrayList<String>();
            shoppinglist.add("服饰");
            shoppinglist.add("化妆品");
            shoppinglist.add("添加自定义");
            shopping.setSecond(shoppinglist);
            shopping.save();
            First daily = new First("日用",R.drawable.daily,"out");
            ArrayList<String> dailylist = new ArrayList<String>();
            dailylist.add("洗衣液");
            dailylist.add("纸巾");
            dailylist.add("添加自定义");
            daily.setSecond(dailylist);
            daily.save();
            First study = new First("学习",R.drawable.study,"out");
            ArrayList<String> studylist = new ArrayList<String>();
            studylist.add("课本");
            studylist.add("文具");
            studylist.add("添加自定义");
            study.setSecond(studylist);
            study.save();
            First transport = new First("交通",R.drawable.transport,"out");
            ArrayList<String> transportlist = new ArrayList<String>();
            transportlist.add("地铁");
            transportlist.add("公交");
            transportlist.add("添加自定义");
            transport.setSecond(transportlist);
            transport.save();
            First snacks = new First("零食",R.drawable.snacks,"out");
            ArrayList<String> snackslist = new ArrayList<String>();
            snackslist.add("饮料");
            snackslist.add("水果");
            snackslist.add("添加自定义");
            snacks.setSecond(snackslist);
            snacks.save();
            First entertainment = new First("娱乐",R.drawable.entertainment,"out");
            ArrayList<String> enterlist = new ArrayList<String>();
            enterlist.add("电影");
            enterlist.add("聚会");
            enterlist.add("添加自定义");
            entertainment.setSecond(enterlist);
            entertainment.save();
            First house = new First("住房",R.drawable.house,"out");
            ArrayList<String> houselist = new ArrayList<String>();
            houselist.add("租金");
            houselist.add("房贷");
            houselist.add("添加自定义");
            house.setSecond(houselist);
            house.save();
            First doctor = new First("医疗",R.drawable.doctor,"out");
            ArrayList<String> doctorlist = new ArrayList<String>();
            doctorlist.add("药");
            doctorlist.add("添加自定义");
            doctor.setSecond(doctorlist);
            doctor.save();
            First pet = new First("宠物",R.drawable.pet,"out");
            ArrayList<String> petlist = new ArrayList<String>();
            petlist.add("食物");
            petlist.add("添加自定义");
            pet.setSecond(petlist);
            pet.save();
            First travel = new First("旅行",R.drawable.travel,"out");
            ArrayList<String> travellist = new ArrayList<String>();
            travellist.add("民宿");
            travellist.add("添加自定义");
            travel.setSecond(travellist);
            travel.save();
            First sport = new First("运动",R.drawable.sport,"out");
            ArrayList<String> sportlist = new ArrayList<String>();
            sportlist.add("健身房");
            sportlist.add("添加自定义");
            sport.setSecond(sportlist);
            sport.save();

            //收入

            First salary = new First("工资",R.drawable.salary,"in");
            ArrayList<String> salaryList = new ArrayList<String>();
            salaryList.add("添加自定义");
            salary.setSecond(salaryList);
            salary.save();
            First parttime = new First("兼职",R.drawable.parttime,"in");
            ArrayList<String> parttimelist = new ArrayList<String>();
            parttimelist.add("添加自定义");
            parttime.setSecond(parttimelist);
            parttime.save();
            First gift = new First("礼金",R.drawable.gift,"in");
            ArrayList<String> giftlist = new ArrayList<String>();
            giftlist.add("添加自定义");
            gift.setSecond(giftlist);
            gift.save();
            //转账
            First transfer = new First("转账",R.drawable.transfer,"trans");
            transfer.save();
            //通用
            First custom = new First("添加自定义",R.drawable.setting,"all");
            custom.save();
        }
    }

    private void initcarddata(){
        List<Cards> wechat = LitePal.where("card = ?","微信").find(Cards.class);
        if (wechat.size()==0){
            Cards card = new Cards();
            card.setCard("微信");
            card.setImage(R.drawable.wechat);
            card.save();
        }
        List<Cards> alipay  = LitePal.where("card = ?","支付宝").find(Cards.class);
        if (alipay.size()==0){
            Cards card = new Cards();
            card.setCard("支付宝");
            card.setImage(R.drawable.alipay);
            card.save();
        }
        List<Cards> cash = LitePal.where("card = ?","现金").find(Cards.class);
        if (cash.size() == 0){
            Cards card = new Cards();
            card.setCard("现金");
            card.setImage(R.drawable.cash);
            card.save();
        }
    }

}
