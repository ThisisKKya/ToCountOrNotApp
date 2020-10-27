package com.example.toaccountornot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.Utils;

import org.litepal.LitePal;

public class DetailActivity extends AppCompatActivity {

    long id;
    String inorout;
    String first;
    String second;
    double price;
    String date;

    ImageView imageProperty;
    TextView textFirst;
    TextView textPrice;
    TextView textDate;
    TextView textSecond;
    TextView textCard;
    TextView textMember;
    ImageView return_bar;
    ImageView delete_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initClickListener();
    }

    void initView() {
        imageProperty = findViewById(R.id.image_property);
        textFirst = findViewById(R.id.text_first);
        textSecond = findViewById(R.id.text_second);
        textPrice = findViewById(R.id.text_price);
        textDate = findViewById(R.id.text_date);
        textCard = findViewById(R.id.text_card);
        textMember = findViewById(R.id.text_member);
        return_bar = findViewById(R.id.return_bar);
        delete_bar = findViewById(R.id.delete_bar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getLong("id");
        inorout = bundle.getString("inorout");
        first = bundle.getString("first");
        second = bundle.getString("second");
        price = bundle.getDouble("price");
        date = bundle.getString("date");
        String card = bundle.getString("card");
        String member = bundle.getString("member");

        Utils.imageSwitch(first, imageProperty);
        textFirst.setText(first);
        textSecond.setText(second);
        textPrice.setText(String.valueOf(price));
        textDate.setText(date);
        textCard.setText(card);
        textMember.setText(member);
    }

    void initClickListener() {
        return_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delete_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailActivity.this)
                        .setTitle(R.string.warning)
                        .setMessage(R.string.msg_delete)
                        .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LitePal.delete(Accounts.class, id);
                                finish();
                            }
                        }).setNegativeButton(R.string.btn_negative, null)
                        .create().show();
            }
        });
    }
}
