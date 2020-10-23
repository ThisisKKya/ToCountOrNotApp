package com.example.toaccountornot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toaccountornot.utils.Accounts;

import org.litepal.LitePal;

public class DetailActivity extends AppCompatActivity {

    long id;
    String inorout;
    String first;
    String second;
    double price;
    String date;
    private String card;
    private String member;

    ImageView imageProperty;
    TextView textFirst;
    TextView textPrice;
    TextView textDate;
    TextView textSecond;
    TextView textCard;
    TextView textMember;
    ImageView return_bar;
    ImageView delete_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initClicklistener();
    }

    void initView() {
        imageProperty = findViewById(R.id.image_property);
        textFirst = findViewById(R.id.text_first);
        textSecond = findViewById(R.id.text_second);
        textPrice = findViewById(R.id.text_price);
        textDate = findViewById(R.id.text_date);
        textCard = findViewById(R.id.text_card);
        textMember = findViewById(R.id.text_member);
        return_bar = findViewById(R.id.return_bar);
        delete_bar = findViewById(R.id.delete_bar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getLong("id");
        inorout = bundle.getString("inorout");
        first = bundle.getString("first");
        second = bundle.getString("second");
        price = bundle.getDouble("price");
        date = bundle.getString("date");
        card = bundle.getString("card");
        member = bundle.getString("member");

        imageSwitch(first, imageProperty);
        textFirst.setText(first);
        textSecond.setText(second);
        textPrice.setText(String.valueOf(price));
        textDate.setText(date);
        textCard.setText(card);
        textMember.setText(member);
    }

    void imageSwitch(String first, ImageView imageProperty) {
        switch (first)
        {
            case"餐饮":
                imageProperty.setImageResource(R.drawable.food);
                break;
            case "购物":
                imageProperty.setImageResource(R.drawable.shopping);
                break;
            case "日用":
                imageProperty.setImageResource(R.drawable.daily);
                break;
            case "学习":
                imageProperty.setImageResource(R.drawable.study);
                break;
            case "交通":
                imageProperty.setImageResource(R.drawable.transport);
                break;
            case "水果":
                imageProperty.setImageResource(R.drawable.fruit);
                break;
            case "零食":
                imageProperty.setImageResource(R.drawable.snacks);
                break;
            case "运动":
                imageProperty.setImageResource(R.drawable.sport);
                break;
            case "娱乐":
                imageProperty.setImageResource(R.drawable.entertainment);
                break;
            case "住房":
                imageProperty.setImageResource(R.drawable.house);
                break;
            case "聚会":
                imageProperty.setImageResource(R.drawable.dating);
                break;
            case "旅行":
                imageProperty.setImageResource(R.drawable.travel);
                break;
            case "医疗":
                imageProperty.setImageResource(R.drawable.doctor);
                break;
            case "宠物":
                imageProperty.setImageResource(R.drawable.pet);
                break;
            case "工资":
                imageProperty.setImageResource(R.drawable.salary);
                break;
            case "兼职":
                imageProperty.setImageResource(R.drawable.parttime);
                break;
            case "礼金":
                imageProperty.setImageResource(R.drawable.gift);
                break;
            case "转账":
                imageProperty.setImageResource(R.drawable.transfer);
                break;
            default:
                imageProperty.setImageResource(R.drawable.setting);
                break;
        }
    }

    void initClicklistener() {
        return_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delete_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailActivity.this)
                        .setTitle("警告")
                        .setMessage("确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LitePal.delete(Accounts.class, id);
                                finish();
                            }
                        }).setNegativeButton("取消", null)
                        .create().show();
            }
        });
    }
}
