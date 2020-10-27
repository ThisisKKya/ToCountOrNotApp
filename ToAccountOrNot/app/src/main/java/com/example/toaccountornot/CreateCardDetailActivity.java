package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toaccountornot.utils.Cards;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

public class CreateCardDetailActivity extends AppCompatActivity {
    LinearLayout cardnumbershow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card_detail);
        CardView save = findViewById(R.id.save);
        final TextView cardtype = findViewById(R.id.choose_cardtype);
        final TextView banknameshow = findViewById(R.id.bankname_show);
        final EditText bankname = findViewById(R.id.bankname);
        final EditText cardnumber = findViewById(R.id.cardnumber);
        final EditText remark = findViewById(R.id.remark);
        cardnumbershow = findViewById(R.id.cardnumber_show);
        cardtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(CreateCardDetailActivity.this)
                        .asBottomList("请选择账户类型", new String[]{"储蓄卡","信用卡","自定义"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        cardtype.setText(text);
                                        switch(cardtype.getText().toString()){
                                            case "自定义":
                                                banknameshow.setText("账户名称");
                                                cardnumbershow.setVisibility(View.GONE);
                                                break;
                                            default:
                                                banknameshow.setText("所在银行");
                                                cardnumbershow.setVisibility(View.VISIBLE);
                                                break;
                                        }

                                    }
                                })
                        .show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CreateCardDetailActivity.this, CardsActivity.class);
                startActivity(intent);
                if(TextUtils.isEmpty(bankname.getText()))
                    Toast.makeText(CreateCardDetailActivity.this,"输入无效",Toast.LENGTH_LONG).show();
                else if(cardnumber.getText().toString().length()!=4)
                    Toast.makeText(CreateCardDetailActivity.this,"输入卡号非4位",Toast.LENGTH_LONG).show();
                else{
                    Cards card = new Cards();
                    if (TextUtils.isEmpty(cardnumber.getText()))
                        card.setCard(bankname.getText().toString());
                    else
                        card.setCard(bankname.getText().toString() + "(" + cardnumber.getText().toString() + ")");
                    card.setRemark(remark.getText().toString());
                    switch (cardtype.getText().toString()) {
                        case "储蓄卡":
                            card.setImage(R.drawable.bankcard);
                            break;
                        case "信用卡":
                            card.setImage(R.drawable.creditcard);
                            break;
                        default:
                            card.setImage(R.drawable.customize);
                            break;
                    }
                    card.save();
                }
            }
        });
    }
}

