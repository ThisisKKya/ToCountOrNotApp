package com.example.toaccountornot.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toaccountornot.NavigationActivity;
import com.example.toaccountornot.R;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                sendRequestWithOkHttp();
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

    void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\"name\":\"jerry\",\"password\":\"I am jerry\"}");
                Request request = new Request.Builder()
                        .url("https://73bc7477-f9ff-4dfc-bafe-6db7da6c9429.mock.pstmn.io/user/register")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    System.out.println("====================================");
                    System.out.println(response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                    user_name.setError("账号不能为空");
                    valid = false;
                }
                // 还需判断账号是否重复
                else {
                    user_name.setError(null);
                }
                break;
            case R.id.signup_psw:
                if(password.isEmpty()) {
                    user_password.setError("密码不能为空");
                    valid = false;
                }
                else if(password.length() < 8 || password.length() > 16) {
                    user_password.setError("密码须在8到16位之间");
                    valid = false;
                }
                else {
                    user_password.setError(null);
                }
                break;
            case R.id.signup_psw_check:
                if(repassword == null || repassword.equals("") || !repassword.equals(password)) {
                    password_check.setError("两次输入的密码不一致");
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
        Toast.makeText(getBaseContext(),"注册成功！", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
        finish();
    }

    // 注册失败，显示提示信息
    public void onSignUpFaild(String failMessage) {
        Toast.makeText(getBaseContext(),failMessage,Toast.LENGTH_LONG).show();
        confirm.setEnabled(true);
    }

}
