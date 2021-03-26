package com.example.toaccountornot.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toaccountornot.R;

import org.json.JSONObject;

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
        login_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIn();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /*登录逻辑*/
    void loginIn(){
        //防止重复登录
        login_finish.setEnabled(false);
        //获取username & password
        String userName = login_username.getText().toString();
        String password = login_psw.getText().toString();
        //登录逻辑
        Handler loginhandler = new Handler(Looper.myLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                String response = (msg != null) ? (String)msg.obj : null;
                if(response != null) {
//                    try {
////                        JSONObject jsonObject  = new JSONObject(response);
////                        String responseCode = jsonObject.getString("responseCode");
//                    }
                }
                return false;
            }
        });
    }
}
