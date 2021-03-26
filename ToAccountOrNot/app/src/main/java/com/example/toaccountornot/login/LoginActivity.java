package com.example.toaccountornot.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toaccountornot.R;

public class LoginActivity extends AppCompatActivity {
    ImageView login_back;
    EditText login_username;
    EditText login_psw;
    TextView to_sign_up;
    TextView login_finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    void initView(){
        login_back = findViewById(R.id.login_back);
        login_username = findViewById(R.id.login_username);
        login_psw = findViewById(R.id.login_psw);
        to_sign_up = findViewById(R.id.to_sign_up);
        login_finish = findViewById(R.id.login_finish);

        login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        to_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
