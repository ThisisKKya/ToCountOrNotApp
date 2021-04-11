package com.example.toaccountornot.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toaccountornot.NavigationActivity;
import com.example.toaccountornot.R;

public class SignUpActivity extends AppCompatActivity {
    EditText user_name;
    EditText user_password;
    EditText password_check;
    TextView back_to_login;
    TextView confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
    }

    void initView() {
        user_name = findViewById(R.id.signup_username);
        user_password = findViewById(R.id.signup_psw);
        password_check = findViewById(R.id.signup_psw_check);
        back_to_login = findViewById(R.id.goto_login);
        confirm = findViewById(R.id.btn_create_account);

        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }


    void signUp() {
        if(!ifvalid(user_name.getId()) || !ifvalid(user_password.getId()) || !ifvalid(password_check.getId())) {
            onSignUpFaild("输入不合法");
            return;
        }
        // 防止重复点击
        confirm.setEnabled(false);
        onsignUpSuccess();

    }


    // 判断输入的账号，密码是否合法
    public boolean ifvalid(int viewId) {
        boolean valid = true;
        String username = user_name.getText().toString();
        String password = user_password.getText().toString();
        String repassword = password_check.getText().toString();
        switch (viewId) {
            case R.id.signup_username:
                if(username.isEmpty()) {
                    new AlertDialog.Builder(SignUpActivity.this)
                            .setTitle("警告")
                            .setMessage("账号不能为空")
                            .setNegativeButton("返回",null)
                            .create().show();
                    valid = false;
                }
                // 还需判断账号是否重复
                else {
                    user_name.setError(null);
                }
                break;
            case R.id.signup_psw:
                if(password.isEmpty()) {
                    new AlertDialog.Builder(SignUpActivity.this)
                            .setTitle("警告")
                            .setMessage("密码不能为空")
                            .setNegativeButton("返回",null)
                            .create().show();
                    valid = false;
                }
                else if(password.length() < 8 || password.length() > 16) {
                    new AlertDialog.Builder(SignUpActivity.this)
                            .setTitle("警告")
                            .setMessage("密码必须在8-16位之间")
                            .setNegativeButton("返回",null)
                            .create().show();
                    valid = false;
                }
                else {
                    user_password.setError(null);
                }
                break;
            case R.id.signup_psw_check:
                if(repassword == null || repassword.equals("") || !repassword.equals(password)) {
                    new AlertDialog.Builder(SignUpActivity.this)
                            .setTitle("警告")
                            .setMessage("两次输入密码不一致")
                            .setNegativeButton("返回",null)
                            .create().show();
                    valid = false;
                }
                else {
                    password_check.setError(null);
                }
                break;
        }
        return valid;
    }

    /**
     * 注册成功提示
     */
    public void onsignUpSuccess() {
        confirm.setEnabled(true);
//        Toast.makeText(getBaseContext(),"注册成功！", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
        finish();
    }

    // 注册失败，显示提示信息
    public void onSignUpFaild(String failMessage) {
//        Toast.makeText(getBaseContext(),failMessage,Toast.LENGTH_LONG).show();
        confirm.setEnabled(true);
    }

}
