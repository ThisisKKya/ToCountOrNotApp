package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.Cards;
import com.example.toaccountornot.utils.CardsAdapter;
import com.example.toaccountornot.utils.DayFlow;
import com.example.toaccountornot.utils.HttpUtil;
import com.example.toaccountornot.utils.ParseJsonUtil;
import com.example.toaccountornot.utils.Single;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CardsActivity extends AppCompatActivity {
    private List<Cards> cardlist = new ArrayList<>();
    private CardsAdapter adapter;
    private RecyclerView recyclerView;
    private double allin;
    private double allout;
    private double allsur;

    TextView label_in;
    TextView label_out;
    TextView label_sur;
    @Override
    protected void onResume(){
        super.onResume();
        initCards();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_view);

        recyclerView = findViewById(R.id.cards_view);
        adapter = new CardsAdapter(CardsActivity.this,cardlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(CardsActivity.this));
        recyclerView.setAdapter(adapter);
        
        label_in = findViewById(R.id.label_in);
        label_out = findViewById(R.id.label_out);
        label_sur = findViewById(R.id.label_sur);

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
    }
    private void initCards() {

        /*从服务器拿card的数据*/
        String url = "http://42.193.103.76:8888/card/all";
        HttpUtil.sendGETRequestWithTokenCard(url, null, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                // 解析响应的数据
                parseJSONWithFastjson(response.body().string(),"initcarddatea");
            }
        });

        /*用litepal读取card*/
//        List<Cards> list = LitePal.findAll(Cards.class);
//        for (Cards card:list) {
//            Cards extra = new Cards();
//            extra.setCards(card.getCard(),card.getRemark(),card.getImage(),card.getIncome(),card.getOutcome(),card.getSurplus());
//            cardlist.add(extra);
//            allin += card.getIncome();
//            allout += card.getOutcome();
//            allsur += card.getSurplus();
//        }
    }

    /**
     * 解析card并显示recycleview
     * */
    private void parseJSONWithFastjson (String jsonData,String fromWhere) {
        allin = 0;
        allout = 0;
        allsur = 0;
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String data = object.getString("data");
        System.out.println("=================parseJSONWithFastjson()===================");
        System.out.println(fromWhere);
        System.out.println("code:"+code);
        System.out.println("message:"+message);
        System.out.println("data:"+data);
        JSONArray list = JSON.parseArray(data);
//        if(adapter!=null)
        cardlist.clear();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("==========list"+i+"=============");
            JSONObject jsonObject = list.getJSONObject(i);
            double balance = jsonObject.getDouble("balance");
            System.out.println("balance:"+balance);
            double expense = jsonObject.getDouble("expense");
            System.out.println("expense:"+expense);
            int image = jsonObject.getInteger("image");
            System.out.println("image:"+image);
            double income = jsonObject.getDouble("income");
            System.out.println("income:"+income);
            String name = jsonObject.getString("name");
            System.out.println("name:"+name);
            String note = jsonObject.getString("note");
            System.out.println("note:"+note);
            Cards extra = new Cards();
            extra.setCards(name,note,image,income,expense,balance);
            cardlist.add(extra);
            allin += income;
            allout += expense;
            allsur += balance;
            System.out.println("allin:"+allin);
            System.out.println("allout:"+allout);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                    label_in.setText(String.valueOf(allin));
                    label_out.setText(String.valueOf(allout));
                    label_sur.setText(String.valueOf(allsur));
                }
            });
        }
    }
}
