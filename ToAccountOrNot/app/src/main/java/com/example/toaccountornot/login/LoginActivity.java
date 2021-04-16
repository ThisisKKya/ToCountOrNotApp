package com.example.toaccountornot.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class LoginActivity extends AppCompatActivity {
    ImageView login_back;
    EditText login_username;
    EditText login_psw;
    TextView to_sign_up;
    private NbButton button;
    private RelativeLayout rlContent;
    private Handler handler;
    private Animator animator;

    boolean activeFlag = false;

    public static final String url = "http://10.0.2.2:8080/user/login";

    public static final int SUCCESS = 1;
    public static final int NOT_EXIST = 2;
    public static final int WRONG_PASSWORD = 3;

    void initView(){
        login_back = findViewById(R.id.login_back);
        login_username = findViewById(R.id.login_username);
        login_psw = findViewById(R.id.login_psw);
        to_sign_up = findViewById(R.id.to_sign_up);
        button = findViewById(R.id.login_finish);
        rlContent=findViewById(R.id.rl_content);

        rlContent.getBackground().setAlpha(0);
        handler=new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case SUCCESS:
                        onLoginSucceess();
                        break;
                    case NOT_EXIST:
                        onLoginFail("用户不存在");
                        break;
                    case WRONG_PASSWORD:
                        onLoginFail("密码错误！请重试");
                        break;
                }
            }
        };

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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIn();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void gotoNew() {
        activeFlag = true;
        button.gotoNew();

        final Intent intent=new Intent(this,NavigationActivity.class);

        int xc=(button.getLeft()+button.getRight())/2;
        int yc=(button.getTop()+button.getBottom())/2;
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
            button.regainBackground("登录");
            activeFlag = false;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /*登录逻辑*/
    void loginIn(){
        //防止重复登录
        button.setEnabled(false);
        //获取username & password
        String userName = login_username.getText().toString();
        String password = login_psw.getText().toString();
        if(userName.isEmpty()){
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("警告")
                    .setMessage("用户名不能为空")
                    .setNegativeButton("返回",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            button.setEnabled(true);
                        }
                    })
                    .create().show();
        }else if(password.isEmpty()){
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("警告")
                    .setMessage("密码不能为空")
                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            button.setEnabled(true);
                        }
                    })
                    .create().show();
        }else{
            //登录逻辑
            // Handler loginhandler = new Handler(Looper.myLooper(), new Handler.Callback() {
//            @Override
//            public boolean handleMessage(@NonNull Message msg) {
//                String response = (msg != null) ? (String)msg.obj : null;
//                if(response != null) {
////                    try {
//////                        JSONObject jsonObject  = new JSONObject(response);
//////                        String responseCode = jsonObject.getString("responseCode");
////                    }
//                }
//                return false;
//            }
//        });
//            String correct_psw = "user";//服务器传来正确密码
//            if(correct_psw.equals(password)){
//                onLoginSucceess();
//            }
//            else{
//                onLoginFail();
//            }
            // 封装数据
            HashMap<String, String> map = new HashMap<>();
            map.put("name", userName);
            map.put("password", password);

            System.out.println(JSON.toJSONString(map));

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
    }

    void parseJSONWithFastjson(String jsonData) {
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String data = object.getString("data");
        // 保存token
        SharedPreferences sharedPreferences = getSharedPreferences("myConfig", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", data);
        editor.commit();
        // 调试信息
        System.out.println("=================LoginActivity.parseJSONWithFastjson()===================");
        System.out.println("code:"+code);
        System.out.println("message:"+message);
        System.out.println("data:"+data);
        System.out.println("已保存token:"+sharedPreferences.getString("token", ""));
        Message msg = new Message();
        switch (message) {
            case "success":
                msg.what = SUCCESS;
                break;
            case "User does not exist":
                msg.what = NOT_EXIST;
                break;
            case "Wrong password":
                msg.what = WRONG_PASSWORD;
                break;
        }
        handler.sendMessage(msg);
    }

    void onLoginSucceess() {
        button.setEnabled(true);
        button.startAnim();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //跳转
                gotoNew();
            }
        },300);
    }

    void onLoginFail(String msg) {
        button.setEnabled(true);
        System.out.println("==============onLoginFail===============");
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("警告")
                .setMessage(msg)
                .create().show();
    }
}
