package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.toaccountornot.login.LoginActivity;
import com.example.toaccountornot.ui.detail.DetailFragment;
import com.example.toaccountornot.utils.First;

import org.litepal.LitePal;


import java.util.ArrayList;

import com.example.toaccountornot.utils.Cards;
import com.example.toaccountornot.utils.TotalBudget;

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

        First.initFirst();
        initcarddata();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    /**
     * 初始化预算
     */
//    public void initFirst() {
//        /**
//         * 修改需要初始化的类目后需要将下方使用删除所有First库中内容，才能初始化
//         * 修改完成后注释掉
//         */
////        LitePal.deleteAll(First.class);//这一句
//        TotalBudget totalBudget = LitePal.findFirst(TotalBudget.class);
//        if (totalBudget == null) {
//            TotalBudget initBudget = new TotalBudget(3000);
//            initBudget.save();
//        }
//            First updatefirst = new First();
//            updatefirst.setThisMonthCost(0);
//            updatefirst.setBudget(0);
//            updatefirst.updateAll();
//        }
//    }

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
