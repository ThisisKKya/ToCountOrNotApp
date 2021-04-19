package com.example.toaccountornot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.toaccountornot.button.NbButton;
import com.example.toaccountornot.login.LoginActivity;
import com.example.toaccountornot.utils.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangePwActivity extends AppCompatActivity {
    private EditText user_name;
    private EditText check_psw;
    private EditText input_psw;
    private NbButton begin_chagne;
    private Handler handler;

    public static final String check_url = "http://42.193.103.76:8888/user/login";
    public static final String update_url = "http://42.193.103.76:8888/user/update";

    public static final int SUCCESS = 1;
    public static final int NOT_EXIST = 2;
    public static final int WRONG_PASSWORD = 3;
    public static final int SUCCESS_TO_CHANGE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepw);
        initView();
    }


    void initView() {
        user_name = findViewById(R.id.change_username);
        check_psw = findViewById(R.id.check_psw);
        input_psw = findViewById(R.id.new_psw);
        begin_chagne = findViewById(R.id.begin_change);

        SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = sp.getString("userId","");
        user_name.setText(name);

        handler=new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case SUCCESS:
                        onChangeSucceess();
                        break;
                    case NOT_EXIST:
                        onChangeFail("用户不存在");
                        break;
                    case WRONG_PASSWORD:
                        onChangeFail("密码错误！请重试");
                        break;
                    case SUCCESS_TO_CHANGE:
                        return_to_Login();
                        break;
                }
            }
        };

        begin_chagne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }

    // 判断密码是否正确
    public void check() {
        begin_chagne.setEnabled(false);
        String old_psw = check_psw.getText().toString();
        String userName = user_name.getText().toString();
        if(old_psw.isEmpty()) {
            new AlertDialog.Builder(ChangePwActivity.this)
                    .setTitle("警告")
                    .setMessage("密码不能为空")
                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            begin_chagne.setEnabled(true);
                        }
                    })
                    .create().show();
        }
        else {
            HashMap<String,String> map = new HashMap<>();
            map.put("name",userName);
            map.put("password",old_psw);

            // 发请求
            HttpUtil.sendPOSTRequest(JSON.toJSONString(map), check_url, new Callback() {
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
        Message msg = new Message();
        switch (message) {
            case "success":
                msg.what = SUCCESS;
                break;
            case "User does not exist":
                msg.what = NOT_EXIST;
                break;
            default:
                msg.what = WRONG_PASSWORD;
                break;
        }
        handler.sendMessage(msg);
    }

    // 密码正确，可修改密码
    void onChangeSucceess() {
        HashMap<String,String> map = new HashMap<>();
        String password = input_psw.getText().toString();
        String userid = user_name.getText().toString();
        map.put("name",userid);
        map.put("password",password);
        HttpUtil.sendPutUpdate(JSON.toJSONString(map), update_url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JSONObject object = JSON.parseObject(response.body().string());
                Integer code = object.getInteger("code");
                String message = object.getString("message");
                String data = object.getString("data");
                System.out.println("============ChangePw=============");
                System.out.println("code:"+code);
                System.out.println("message:"+message);
                System.out.println("data:"+data);
                Message msg = new Message();
                msg.what = SUCCESS_TO_CHANGE;
                handler.sendMessage(msg);
            }
        });

    }

    // 密码错误
    void onChangeFail(String msg) {
        begin_chagne.setEnabled(true);
        new AlertDialog.Builder(ChangePwActivity.this)
                .setTitle("警告")
                .setMessage(msg)
                .create().show();
    }

    // 成功更新密码
    void return_to_Login() {
        Intent intent = new Intent(ChangePwActivity.this,LoginActivity.class);
        startActivity(intent);
    }


}
