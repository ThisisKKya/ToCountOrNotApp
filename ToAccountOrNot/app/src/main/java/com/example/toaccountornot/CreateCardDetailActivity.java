package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.Cards;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class CreateCardDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card_detail);
        Bundle bundle = this.getIntent().getExtras();
        final int type = bundle.getInt("type");
        Button save = findViewById(R.id.save);
        final EditText bankname = findViewById (R.id.bankname);
        final EditText cardnumber = findViewById (R.id.cardnumber);
        final EditText remark = findViewById (R.id.remark);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent();
                //intent.setClass(CreateCardDetailActivity.this, CardsActivity.class);
                //startActivity(intent);
                Connector.getDatabase();
                Cards card = new Cards();
                if (TextUtils.isEmpty(cardnumber.getText()))
                    card.setCard(bankname.getText().toString());
                else
                    card.setCard(bankname.getText().toString()+"("+cardnumber.getText().toString()+")");
                card.setRemark(remark.getText().toString());
                card.setCardtype(type);
                card.setIncome(0);
                card.setOutcome(0);
                card.setSurplus(0);
                card.save();
                //LitePal.deleteAll(Cards.class);
                List<Cards> list = LitePal.findAll(Cards.class);
                for (Cards c:list) {
                    Log.d("DatabaseActivity","name" + c.getCard());
                    Log.d("DatabaseActivity","remark" + c.getRemark());
                    Log.d("DatabaseActivity","name" + c.getIncome());
                }
            }
        });
    }
}
