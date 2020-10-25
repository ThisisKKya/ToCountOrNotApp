package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toaccountornot.utils.Cards;

public class CreateCardDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card_detail);
        Bundle bundle = this.getIntent().getExtras();
        final int type = bundle.getInt("type");
        Button save = findViewById(R.id.save);
        final EditText bankname = findViewById(R.id.bankname);
        final EditText cardnumber = findViewById(R.id.cardnumber);
        final EditText remark = findViewById(R.id.remark);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CreateCardDetailActivity.this, CardsActivity.class);
                startActivity(intent);
                if(TextUtils.isEmpty(bankname.getText()))
                    Toast.makeText(CreateCardDetailActivity.this,"输入无效",Toast.LENGTH_LONG).show();
                else{
                    Cards card = new Cards();
                    if (TextUtils.isEmpty(cardnumber.getText()))
                        card.setCard(bankname.getText().toString());
                    else
                        card.setCard(bankname.getText().toString() + "(" + cardnumber.getText().toString() + ")");
                    card.setRemark(remark.getText().toString());
                    switch (type) {
                        case 0:
                            card.setCardid(R.drawable.bankcard);
                            break;
                        case 1:
                            card.setCardid(R.drawable.creditcard);
                            break;
                    }
                    card.save();
                }
            }
        });
    }
}

