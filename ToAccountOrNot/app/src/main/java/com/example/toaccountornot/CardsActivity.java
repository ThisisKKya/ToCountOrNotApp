package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.Cards;
import com.example.toaccountornot.utils.CardsAdapter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class CardsActivity extends AppCompatActivity {
    private List<Cards> cardlist = new ArrayList<>();
    private CardsAdapter adapter;

    @Override
    protected void onResume(){
        super.onResume();
        if(adapter != null){
            cardlist.clear();
            initCards();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_view);
        initcarddata();
        Button createcard = findViewById(R.id.create_card);
        createcard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent();
               intent.setClass(CardsActivity.this, CreateCardActivity.class);
               startActivity(intent);
           }
       });
        Button deletecard = findViewById(R.id.delete_card);
        deletecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(Cards.class);
                LitePal.deleteAll(Accounts.class);
                Intent intenttest = new Intent();
                intenttest.setClass(CardsActivity.this, CardsActivity.class);
                startActivity(intenttest);
            }
        });
        Button querycard = findViewById(R.id.query_card);
        querycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Cards> cards = LitePal.findAll(Cards.class);
                for (Cards card:cards) {
                    Log.d("DatabaseActivity", "name" + card.getCard());
                    Log.d("DatabaseActivity", "income" + card.getIncome());
                    Log.d("DatabaseActivity", "outcome" + card.getOutcome());
                }
                List<Accounts> accounts = LitePal.findAll(Accounts.class);
                for (Accounts account:accounts) {
                    Log.d("DatabaseActivity", "card" + account.getCard());
                    Log.d("DatabaseActivity", "price" + account.getPrice());
                }
            }
        });
        
        adapter = new CardsAdapter(CardsActivity.this,
                R.layout.cards_list_item, cardlist);
        ListView listView = (ListView) findViewById(R.id.listview);//在视图中找到ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //switch (i){
                    //case 0:
                    Toast.makeText(CardsActivity.this,cardlist.get(i).getCard(),Toast.LENGTH_SHORT).show();
                    Intent cardintent = new Intent();
                    cardintent.setClass(CardsActivity.this, CardDetailActivity.class);
                    Bundle cardbundle = new Bundle();
                    cardbundle.putString("name",cardlist.get(i).getCard());
                    cardintent.putExtras(cardbundle);
                    startActivity(cardintent);
                    /*break;//当我们点击某一项就能吐司我们点了哪一项

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
                }*/
            }
        });
    }
    private void initcarddata(){
        List<Cards> wechat = LitePal.where("card = ?","微信").find(Cards.class);
        if (wechat.size()==0){
            Cards card = new Cards();
            card.setCard("微信");
            card.setCardid(R.drawable.wechat);
            card.save();
        }
        List<Cards> alipay  = LitePal.where("card = ?","支付宝").find(Cards.class);
        if (alipay.size()==0){
            Cards card = new Cards();
            card.setCard("支付宝");
            card.setCardid(R.drawable.alipay);
            card.save();
        }
        List<Cards> cash = LitePal.where("card = ?","现金").find(Cards.class);
        if (cash.size() == 0){
            Cards card = new Cards();
            card.setCard("现金");
            card.setCardid(R.drawable.cash);
            card.save();
        }
    }
    private void initCards() {
//        Cards test = new Cards();
//        test.setCards("测试","tesr",R.drawable.fruit,1.00,2.00,3.00);
//        cardlist.add(test);
//        Cards test1 = new Cards();
//        test1.setCards("测试","tesr",R.drawable.fruit,1.00,2.00,3.00);
//        cardlist.add(test1);
        List<Cards> list = LitePal.findAll(Cards.class);
        for (Cards card:list) {
            Cards extra = new Cards();
            extra.setCards(card.getCard(),card.getRemark(),card.getCardid(),card.getIncome(),card.getOutcome(),card.getSurplus());
            cardlist.add(extra);
        }
    }
}
