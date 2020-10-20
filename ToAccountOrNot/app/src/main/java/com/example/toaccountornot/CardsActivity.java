package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.toaccountornot.utils.Cards;
import com.example.toaccountornot.utils.CardsAdapter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class CardsActivity extends AppCompatActivity {
    private List<Cards> cardlist = new ArrayList<>();
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_view);
        Button createcard = findViewById(R.id.create_card);
        createcard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent();
               intent.setClass(CardsActivity.this, CreateCardActivity.class);
               startActivity(intent);
           }
       });
        initCards();
        CardsAdapter adapter = new CardsAdapter(CardsActivity.this,
                R.layout.cards_list_item, cardlist);
        ListView listView = (ListView) findViewById(R.id.listview);//在视图中找到ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Toast.makeText(CardsActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;//当我们点击某一项就能吐司我们点了哪一项

                    case 1:
                        Toast.makeText(CardsActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText(CardsActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        Toast.makeText(CardsActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        Toast.makeText(CardsActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    private void initCards() {
        Cards wechat = new Cards();
        wechat.setCards("微信",null,0,R.drawable.wechat, 1.11,0.00,1.11);
        cardlist.add(wechat);
        Cards alipay = new Cards();
        alipay.setCards("支付宝",null,0,R.drawable.alipay, 2.22,1.00,1.22);
        cardlist.add(alipay);
        Cards cash = new Cards();
        cash.setCards("现金",null,0,R.drawable.cash, 0.00,0.00,0.00);
        cardlist.add(cash);
        /*List<Cards> list = LitePal.findAll(Cards.class);
        int image = 0;
        for (Cards card:list) {
            Cards extra = new Cards();
            switch (card.getCardtype()) {
                case 0:
                    image = R.drawable.bankcard;
                    break;
                case 1:
                    image = R.drawable.creditcard;
                    break;
            }
            extra.setCards(card.getCard(),card.getRemark(),card.getCardtype(),image,card.getIncome(),card.getOutcome(),card.getSurplus());
            cardlist.add(extra);
        }*/
    }
}
