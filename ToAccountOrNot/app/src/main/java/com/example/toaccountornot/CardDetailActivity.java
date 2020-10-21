package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class CardDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        Bundle bundle = this.getIntent().getExtras();
        final String cardname = bundle.getString("name");
        Toast.makeText(CardDetailActivity.this,cardname,Toast.LENGTH_LONG).show();
    }
}
