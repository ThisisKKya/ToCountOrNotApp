package com.example.toaccountornot.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.toaccountornot.NavigationActivity;
import com.example.toaccountornot.R;
import com.example.toaccountornot.button.NbButton;
import com.example.toaccountornot.utils.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    EditText user_name;
    EditText user_password;
    EditText password_check;
    TextView back_to_login;

    private NbButton confirm;
    private RelativeLayout rlContent;
    private Handler handler;
    private Animator animator;

    boolean activeFlag = false;

    public static final String url = "http://42.193.103.76:8888/user/register";

    public static final int FAILURE = 0;
    public static final int SUCCESS = 1;

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
        confirm = findViewById(R.id.signup_finish);

        rlContent=findViewById(R.id.rl_content);

        rlContent.getBackground().setAlpha(0);
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case FAILURE:
                        onSignUpFaild("用户名已存在");
                        break;
                    case SUCCESS:
                        onsignUpSuccess();
                        break;
                }
            }
        };

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


    private void gotoNew() {
        activeFlag = true;
        confirm.gotoNew();

        final Intent intent=new Intent(this,NavigationActivity.class);

        int xc=(confirm.getLeft()+confirm.getRight())/2;
        int yc=(confirm.getTop()+confirm.getBottom())/2;
        animator= ViewAnimationUtils.createCircularReveal(rlContent,xc,yc,0,1111);
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                    }
                },400);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
        rlContent.getBackground().setAlpha(255);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(activeFlag){
            animator.cancel();
            rlContent.getBackground().setAlpha(0);
            confirm.regainBackground("注册");
            activeFlag = false;
        }
    }
    void signUp() {
        if(!ifvalid(user_name.getId()) || !ifvalid(user_password.getId()) || !ifvalid(password_check.getId())) {
            onSignUpFaild("输入不合法");
            return;
        }
        // 防止重复点击
        confirm.setEnabled(false);

        // 封装数据
        HashMap<String, String> map = new HashMap<>();
        String username = user_name.getText().toString();
        String password = user_password.getText().toString();
        map.put("name", username);
        map.put("password", password);

        // 发请求
        HttpUtil.sendPOSTRequest(JSON.toJSONString(map), url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                // 解析响应的数据
                parseJSONWithFastjson(response.body().string());
            }
        });
    }

    void parseJSONWithFastjson(String jsonData) {
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String data = object.getString("data");
        System.out.println("=================SignUpActivity.parseJSONWithFastjson()===================");
        System.out.println("code:"+code);
        System.out.println("message:"+message);
        System.out.println("data:"+data);
        Message msg = new Message();
        if(message.equals("success")) {
            msg.what = SUCCESS;
        } else {
            msg.what = FAILURE;
        }
        handler.sendMessage(msg);
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
        confirm.startAnim();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //跳转
                gotoNew();
            }
        },300);
    }

    // 注册失败，显示提示信息
    public void onSignUpFaild(String failMessage) {
        Toast.makeText(getBaseContext(),failMessage,Toast.LENGTH_LONG).show();
        confirm.setEnabled(true);
    }

}
