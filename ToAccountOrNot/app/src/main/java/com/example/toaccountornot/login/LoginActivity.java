package com.example.toaccountornot.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toaccountornot.CardDetailActivity;
import com.example.toaccountornot.NavigationActivity;
import com.example.toaccountornot.R;
import com.example.toaccountornot.button.NbButton;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.Cards;

import org.json.JSONObject;
import org.litepal.LitePal;

public class LoginActivity extends AppCompatActivity {
    ImageView login_back;
    EditText login_username;
    EditText login_psw;
    TextView to_sign_up;
    private NbButton button;
    private RelativeLayout rlContent;
    private Handler handler;
    private Animator animator;

    void initView(){
        login_back = findViewById(R.id.login_back);
        login_username = findViewById(R.id.login_username);
        login_psw = findViewById(R.id.login_psw);
        to_sign_up = findViewById(R.id.to_sign_up);
        button = findViewById(R.id.login_finish);
        rlContent=findViewById(R.id.rl_content);

        rlContent.getBackground().setAlpha(0);
        handler=new Handler();

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
        animator.cancel();
        rlContent.getBackground().setAlpha(0);
        button.regainBackground();
    }
//

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
            String correct_psw = "user";//服务器传来正确密码
            if(correct_psw.equals(password)){
                onLoginSucceess();
            }
            else{
                onLoginFail();
            }
        }

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

    void onLoginFail() {
        button.setEnabled(true);
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("警告")
                .setMessage("密码错误！请重试")
                .create().show();
    }
}
