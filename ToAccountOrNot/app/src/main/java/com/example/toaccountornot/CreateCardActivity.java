package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.toaccountornot.utils.Cards;
import com.example.toaccountornot.utils.CardsAdapter;
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
                    case 0:
                        Toast.makeText(CreateCardActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;//当我们点击某一项就能吐司我们点了哪一项

                    case 1:
                        Toast.makeText(CreateCardActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText(CreateCardActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        Toast.makeText(CreateCardActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        Toast.makeText(CreateCardActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
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
