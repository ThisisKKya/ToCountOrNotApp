package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.toaccountornot.utils.CreateCard;
import com.example.toaccountornot.utils.CreateCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class CreateCardActivity extends AppCompatActivity {
    private List<CreateCard> createcardlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        initCreateCards();
        CreateCardAdapter adapter = new CreateCardAdapter(CreateCardActivity.this,
                R.layout.createcard_list_item, createcardlist);
        ListView listView = findViewById(R.id.listview);//在视图中找到ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: //储蓄卡
                        Intent intent1 = new Intent();
                        intent1.setClass(CreateCardActivity.this, CreateCardDetailActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("type",0);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                        break;//当我们点击某一项就能吐司我们点了哪一项

                    case 1: //信用卡
                        Intent intent2 = new Intent();
                        intent2.setClass(CreateCardActivity.this, CreateCardDetailActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("type",1);
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;

                    case 2: //自定义
                        Intent intent3 = new Intent();
                        intent3.setClass(CreateCardActivity.this, CardCustomizeActivity.class);
                        startActivity(intent3);
                        break;

                }
            }
        });
    }
    private void initCreateCards() {
        CreateCard bankcard = new CreateCard("储蓄卡",R.drawable.bankcard,R.drawable.returnsignal);
        createcardlist.add(bankcard);
        CreateCard creditcard = new CreateCard("信用卡",R.drawable.creditcard,R.drawable.returnsignal);
        createcardlist.add(creditcard);
        CreateCard customize = new CreateCard("自定义账户",R.drawable.customize,R.drawable.returnsignal);
        createcardlist.add(customize);

    }
}
