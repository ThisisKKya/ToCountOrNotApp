package com.example.toaccountornot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.Cards;
import com.example.toaccountornot.utils.HttpUtil;
import com.example.toaccountornot.utils.ParseJsonUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreateCardDetailActivity extends AppCompatActivity {
    LinearLayout cardnumbershow;

    private static final int FAILURE = 0;
    private static final int SUCCESS = 1;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card_detail);
        CardView save = findViewById(R.id.save);
        final TextView cardtype = findViewById(R.id.choose_cardtype);
        final TextView banknameshow = findViewById(R.id.bankname_show);
        final EditText bankname = findViewById(R.id.bankname);
        final EditText cardnumber = findViewById(R.id.cardnumber);
        final EditText remark = findViewById(R.id.remark);
        ImageView return_bar = findViewById(R.id.return_bar);
        return_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cardnumbershow = findViewById(R.id.cardnumber_show);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case FAILURE:
                        onFaild("用户名已存在");
                        break;
                    case SUCCESS:
                        onSuccess();
                        break;
                }
            }
        };

        cardtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(CreateCardDetailActivity.this)
                        .asBottomList("请选择账户类型", new String[]{"储蓄卡","信用卡","自定义"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        cardtype.setText(text);
                                        switch(cardtype.getText().toString()){
                                            case "自定义":
                                                banknameshow.setText("账户名称");
                                                cardnumbershow.setVisibility(View.GONE);
                                                cardnumber.setText(null);
                                                break;
                                            default:
                                                banknameshow.setText("所在银行");
                                                cardnumbershow.setVisibility(View.VISIBLE);
                                                break;
                                        }

                                    }
                                })
                        .show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(bankname.getText())){
                    String sInfoFormat = getResources().getString(R.string.no_card_name);
                    String warning = String.format(sInfoFormat,banknameshow.getText().toString());
                    new AlertDialog.Builder(CreateCardDetailActivity.this)
                            .setTitle(R.string.reminding)
                            .setMessage(warning)
                            .setPositiveButton(R.string.returnback, null)
                            .setNegativeButton(R.string.quitout, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setClass(CreateCardDetailActivity.this, CardsActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .create().show();
                }
                else if ((cardnumber.getText().toString().length()!=4)&&(cardnumber.getText().toString().length()!=0)){
                    new AlertDialog.Builder(CreateCardDetailActivity.this)
                            .setTitle(R.string.reminding)
                            .setMessage(R.string.cardnumber_wrong)
                            .setPositiveButton(R.string.returnback, null)
                            .setNegativeButton(R.string.quitout, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setClass(CreateCardDetailActivity.this, CardsActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .create().show();
                }
                else{
                    //存储自定义的卡

                    // 封装数据
                    HashMap<String, String> map = new HashMap<>();
                    String name;
                    if (TextUtils.isEmpty(cardnumber.getText()))
                        name = bankname.getText().toString() + cardtype.getText().toString();
                    else
                        name = bankname.getText().toString() + cardtype.getText().toString()+ "(" + cardnumber.getText().toString() + ")";

                    String note = remark.getText().toString();

                    int image;
                    switch (cardtype.getText().toString()) {
                        case "储蓄卡":
                            image = R.drawable.bankcard;
                            break;
                        case "信用卡":
                            image = R.drawable.creditcard;
                            break;
                        default:
                            image = R.drawable.customize;
                            break;
                    }

                    map.put("name", name);
                    map.put("note", note);
                    map.put("image",String.valueOf(image));

                    // 发请求
                    String url = "http://42.193.103.76:8888/card/insert";
                    HttpUtil.sendPOSTRequestWithToken(JSON.toJSONString(map), url, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            // 解析响应的数据
                            parseJSONWithFastjson(response.body().string());
                        }

                    });
                    /*
                    Cards card = new Cards();
                    if (TextUtils.isEmpty(cardnumber.getText()))
                        card.setCard(bankname.getText().toString());
                    else
                        card.setCard(bankname.getText().toString() + "(" + cardnumber.getText().toString() + ")");
                    card.setRemark(remark.getText().toString());
                    switch (cardtype.getText().toString()) {
                        case "储蓄卡":
                            card.setImage(R.drawable.bankcard);
                            break;
                        case "信用卡":
                            card.setImage(R.drawable.creditcard);
                            break;
                        default:
                            card.setImage(R.drawable.customize);
                            break;
                    }
                    card.save();
                     */
                }
            }
        });
    }

    private void parseJSONWithFastjson(String jsonData) {
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String data = object.getString("data");
        System.out.println("=================CreateCardDetailActivity.parseJSONWithFastjson()===================");
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

    private void onFaild(String failMessage){
        Toast.makeText(CreateCardDetailActivity.this,failMessage,Toast.LENGTH_LONG).show();
    }
    private void onSuccess() {
        Intent intent = new Intent();
        intent.setClass(CreateCardDetailActivity.this, CardsActivity.class);
        startActivity(intent);
    }
}

