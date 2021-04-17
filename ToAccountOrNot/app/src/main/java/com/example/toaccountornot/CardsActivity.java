package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
    private RecyclerView recyclerView;
    private double allin;
    private double allout;
    private double allsur;
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
        //LitePal.deleteAll(Cards.class);
        //LitePal.deleteAll(Accounts.class);
        //initcarddata();
        initCards();
        Log.d("card","have finished");
        recyclerView = findViewById(R.id.cards_view);
        adapter = new CardsAdapter(CardsActivity.this,cardlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(CardsActivity.this));
        recyclerView.setAdapter(adapter);
        
        TextView label_in = findViewById(R.id.label_in);
        TextView label_out = findViewById(R.id.label_out);
        TextView label_sur = findViewById(R.id.label_sur);
        label_in.setText(String.valueOf(allin));
        label_out.setText(String.valueOf(allout));
        label_sur.setText(String.valueOf(allsur));


        ImageView return_bar = findViewById(R.id.return_bar);
        return_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView createcard = findViewById(R.id.create_card);
        createcard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent();
               intent.setClass(CardsActivity.this, CreateCardDetailActivity.class);
               startActivity(intent);
           }
       });
//        Button deletecard = findViewById(R.id.delete_card);
//        deletecard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LitePal.deleteAll(Cards.class);
//                //LitePal.deleteAll(Accounts.class);
//                Intent intenttest = new Intent();
//                intenttest.setClass(CardsActivity.this, CardsActivity.class);
//                startActivity(intenttest);
//            }
//        });
//        Button querycard = findViewById(R.id.query_card);
//        querycard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<Cards> cards = LitePal.findAll(Cards.class);
//                for (Cards card:cards) {
//                    Log.d("DatabaseActivity", "name" + card.getCard());
//                    Log.d("DatabaseActivity", "income" + card.getIncome());
//                    Log.d("DatabaseActivity", "outcome" + card.getOutcome());
//                }
//                List<Accounts> accounts = LitePal.findAll(Accounts.class);
//                for (Accounts account:accounts) {
//                    Log.d("DatabaseActivity", "card" + account.getCard());
//                    Log.d("DatabaseActivity", "price" + account.getPrice());
//                }
//            }
//        });
    }
//    private void initcarddata(){
//        List<Cards> wechat = LitePal.where("card = ?","微信").find(Cards.class);
//        if (wechat.size()==0){
//            Cards card = new Cards();
//            card.setCard("微信");
//            card.setImage(R.drawable.wechat);
//            card.save();
//        }
//        List<Cards> alipay  = LitePal.where("card = ?","支付宝").find(Cards.class);
//        if (alipay.size()==0){
//            Cards card = new Cards();
//            card.setCard("支付宝");
//            card.setImage(R.drawable.alipay);
//            card.save();
//        }
//        List<Cards> cash = LitePal.where("card = ?","现金").find(Cards.class);
//        if (cash.size() == 0){
//            Cards card = new Cards();
//            card.setCard("现金");
//            card.setImage(R.drawable.cash);
//            card.save();
//        }
//    }
    private void initCards() {
//        Cards test = new Cards();
//        test.setCards("测试","tesr",R.drawable.fruit,1.00,2.00,3.00);
//        cardlist.add(test);
//        Cards test1 = new Cards();
//        test1.setCards("测试","tesr",R.drawable.fruit,1.00,2.00,3.00);
//        cardlist.add(test1);
        allin = 0;
        allout = 0;
        allsur = 0;
        List<Cards> list = LitePal.findAll(Cards.class);
        for (Cards card:list) {
            Cards extra = new Cards();
            extra.setCards(card.getCard(),card.getRemark(),card.getImage(),card.getIncome(),card.getOutcome(),card.getSurplus());
            cardlist.add(extra);
            allin += card.getIncome();
            allout += card.getOutcome();
            allsur += card.getSurplus();
        }
    }
}
