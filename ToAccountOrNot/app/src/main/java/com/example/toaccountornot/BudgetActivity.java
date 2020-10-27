package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BudgetActivity extends AppCompatActivity {

    TextView textDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        textDate = findViewById(R.id.text_date);
        Intent intent = getIntent();
        textDate.setText(intent.getStringExtra("outcome"));

    }

}
