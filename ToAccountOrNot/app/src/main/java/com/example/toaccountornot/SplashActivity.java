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

        initDateBase();
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
    public void initDateBase() {
        /**
         * 修改需要初始化的类目后需要将下方使用删除所有First库中内容，才能初始化
         * 修改完成后注释掉
         */
        LitePal.deleteAll(First.class);//这一句
        First firstFirst = LitePal.findFirst(First.class);
        //数据库未初始化
        if (firstFirst == null) {
            Toast.makeText(SplashActivity.this, "未初始化", Toast.LENGTH_LONG).show();
           //支出
            First food = new First();
            food.setName("餐饮");
            food.setImage(R.drawable.food);
            food.setInorout("out");
            food.setSecond("早饭");
            food.setSecond("中饭");
            food.save();
            //收入
            First salary = new First();
            salary.setName("工资");
            salary.setImage(R.drawable.salary);
            salary.setInorout("in");
            salary.save();
            //转账
            First wechart = new First();
            wechart.setName("转到微信");
            wechart.setImage(R.drawable.transfer);
            wechart.setInorout("trans");
            wechart.save();
            //通用
            First custom = new First();
            custom.setName("自定义");
            custom.setImage(R.drawable.setting);
            custom.setInorout("all");
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
