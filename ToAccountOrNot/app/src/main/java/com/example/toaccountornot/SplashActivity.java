package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.toaccountornot.utils.First;

import org.litepal.LitePal;

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
            food.setSecond("早餐");
            food.setSecond("午餐");
            food.setSecond("晚餐");
            food.setSecond("下午茶");
            food.setSecond("自定义");
            food.save();
            First shopping = new First("购物",R.drawable.shopping,"out");
            shopping.setSecond("服饰");
            shopping.setSecond("化妆品");
            shopping.setSecond("自定义");
            shopping.save();
            First daily = new First("日用",R.drawable.daily,"out");
            daily.setSecond("洗衣液");
            daily.setSecond("纸巾");
            daily.setSecond("自定义");
            daily.save();
            First study = new First("学习",R.drawable.study,"out");
            study.setSecond("课本");
            study.setSecond("文具");
            study.setSecond("自定义");
            study.save();
            First transport = new First("交通",R.drawable.transport,"out");
            transport.setSecond("地铁");
            transport.setSecond("公交");
            transport.setSecond("自定义");
            transport.save();
            First snacks = new First("零食",R.drawable.snacks,"out");
            snacks.setSecond("饮料");
            snacks.setSecond("水果");
            snacks.setSecond("自定义");
            snacks.save();
            First entertainment = new First("娱乐",R.drawable.entertainment,"out");
            entertainment.setSecond("电影");
            entertainment.setSecond("聚会");
            entertainment.setSecond("自定义");
            entertainment.save();
            First house = new First("住房",R.drawable.house,"out");
            house.setSecond("租金");
            house.setSecond("房贷");
            house.setSecond("自定义");
            house.save();
            First doctor = new First("医疗",R.drawable.doctor,"out");
            doctor.setSecond("药");
            doctor.setSecond("自定义");
            doctor.save();
            First pet = new First("宠物",R.drawable.pet,"out");
            pet.setSecond("食物");
            pet.setSecond("自定义");
            pet.save();
            First travel = new First("旅行",R.drawable.travel,"out");
            travel.setSecond("民宿");
            travel.setSecond("自定义");
            travel.save();
            First sport = new First("运动",R.drawable.sport,"out");
            sport.setSecond("健身房");
            sport.setSecond("自定义");
            sport.save();

            //收入
            First salary = new First("工资",R.drawable.salary,"in");
            salary.setSecond("月薪");
            salary.setSecond("奖金");
            salary.setSecond("自定义");
            salary.save();
            First parttime = new First("兼职",R.drawable.parttime,"in");
            parttime.setSecond("自定义");
            parttime.save();
            First gift = new First("礼金",R.drawable.gift,"in");
            gift.setSecond("自定义");
            gift.save();
            //转账
            First transfer = new First("转账",R.drawable.transfer,"trans");
            transfer.save();
            //通用
            First custom = new First("自定义",R.drawable.setting,"all");
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
