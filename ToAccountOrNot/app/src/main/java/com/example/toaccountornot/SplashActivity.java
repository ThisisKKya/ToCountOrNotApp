package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.toaccountornot.utils.First;

import org.litepal.LitePal;

import java.util.ArrayList;

/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initDateBase();
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
            ArrayList<String> foodList = new ArrayList<String>();
            foodList.add("早饭");
            foodList.add("中饭");
            foodList.add("晚饭");
            foodList.add("添加自定义");
            food.setSecond(foodList);
            food.save();
            //收入
            First salary = new First();
            salary.setName("工资");
            salary.setImage(R.drawable.salary);
            salary.setInorout("in");
            ArrayList<String> salaryList = new ArrayList<String>();
            salaryList.add("添加自定义");
            salary.setSecond(salaryList);
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

}
