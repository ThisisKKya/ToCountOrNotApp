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

public class CardCustomizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_customize);
        Button save = findViewById(R.id.savec);
        final EditText name = findViewById(R.id.name);
        final EditText remark = findViewById(R.id.remarkc);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CardCustomizeActivity.this, CardsActivity.class);
                startActivity(intent);
                if(TextUtils.isEmpty(name.getText()))
                    Toast.makeText(CardCustomizeActivity.this,"输入无效",Toast.LENGTH_LONG).show();
                else{
                    Cards card = new Cards();
                    card.setCard(name.getText().toString());
                    card.setRemark(remark.getText().toString());
                    card.setCardid(R.drawable.customize);
                    card.setIncome(0);
                    card.setOutcome(0);
                    card.setSurplus(0);
                    card.save();
                }
            }
        });
    }
}
