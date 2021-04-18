package com.example.toaccountornot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.Cards;
import com.example.toaccountornot.utils.Day;
import com.example.toaccountornot.utils.DayCardAdapter;
import com.example.toaccountornot.utils.DayFlow;
import com.example.toaccountornot.utils.HttpUtil;
import com.example.toaccountornot.utils.Month;
import com.example.toaccountornot.utils.MonthAdapter;
import com.example.toaccountornot.utils.Single;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopupext.listener.TimePickerListener;
import com.lxj.xpopupext.popup.TimePickerPopup;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CardDetailActivity extends AppCompatActivity {

    private String year;
    private String month;
    private TextView label;
    private TextView label_year;
    private TextView label_month;
    private TextView label_time2;
    private TextView label_out;
    private TextView label_in;
    private TextView viewchoice;
    private LinearLayout choose_date;
    private LinearLayout choose_view;
    private RecyclerView rec_day;
    private List<Day> dayList = new ArrayList<>();
    private List<Month> monthList = new ArrayList<>();
    private String cardname;
    private BasePopupView datePicker;
    private ImageView return_bar;
    private ImageView delete_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        initView();
        initPicker_YM();
        initDaylist();
//        initInternetDayList();
        initClickListener();
    }
    void initView() {

        Bundle bundle = this.getIntent().getExtras();
        cardname = bundle.getString("name");
        label = findViewById(R.id.label);
        label_year = findViewById(R.id.label_year);
        label_month = findViewById(R.id.label_month);
        label_time2 = findViewById(R.id.label_time2);
        label_out = findViewById(R.id.label_out);
        label_in = findViewById(R.id.label_in);
        choose_date = findViewById(R.id.choose_date2);
        choose_view = findViewById(R.id.choose_view);
        viewchoice = findViewById(R.id.viewchoice);
        rec_day = findViewById(R.id.mainlist);
        return_bar = findViewById(R.id.return_bar);
        delete_bar = findViewById(R.id.delete_bar);
        viewchoice.setText("月");
        label.setText(cardname);
        if(label.getText().toString().equals("微信")||label.getText().toString().equals("支付宝")||label.getText().toString().equals("现金"))
            delete_bar.setVisibility(View.GONE);
        label_year.setText(year);
        label_month.setText(month);
        label_time2.setText("-");
        rec_day.setLayoutManager(new LinearLayoutManager(CardDetailActivity.this));

        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
                //Toast.makeText(CardDetailActivity.this,"你点击了按钮",Toast.LENGTH_SHORT).show();
            }
        });
        choose_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new XPopup.Builder(CardDetailActivity.this)
                    .asBottomList("请选择展示的时间单位", new String[]{"年","月"},
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                   // Toast.makeText(CardDetailActivity.this,"你点击了"+text,Toast.LENGTH_SHORT).show();
                                    viewchoice.setText(text);
                                    switch(viewchoice.getText().toString()){
                                        case "年":
                                            label_time2.setText("");
                                            label_month.setText("");
                                            initPicker_Y();
                                            initMonthlist();
                                            break;
                                        case "月":
                                            label_time2.setText("-");
                                            label_month.setText(month);
                                            initPicker_YM();
                                            initDaylist();
                                    }

                                }
                            })
                    .show();
            }
        });
    }
    void initClickListener() {
        return_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delete_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CardDetailActivity.this)
                        .setTitle("警告")
                        .setMessage("确定删除吗？(删除后将删除该账户的所有账款条目)")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LitePal.deleteAll(Cards.class,"card = ?",cardname);
                                LitePal.deleteAll(Accounts.class,"card = ?",cardname);
                                finish();
                            }
                        }).setNegativeButton("取消", null)
                        .create().show();
            }
        });
    }
    void initDaylist() {
        Log.d("test","daylist");
        dayList.clear();
        // 因为LitePal不支持group by, 故使用SQL语句查询
        Cursor cursor = LitePal.findBySQL("select card,date from Accounts where card like ?"+"and date_year=?" +
                        "and date_month=? group by date order by date desc",
                "%"+label.getText().toString()+"%",
                label_year.getText().toString(),
                label_month.getText().toString());
        if (cursor.moveToFirst()) {
            do {
                String card = cursor.getString(0);
                String date = cursor.getString(1);
                Log.d("DatabaseActivity", "card" + card);
                Log.d("DatabaseActivity", "date" + date);
                dayList.add(new Day(date,card));
                DayCardAdapter Adapter = new DayCardAdapter(dayList, CardDetailActivity.this);
                rec_day.setAdapter(Adapter);
            } while (cursor.moveToNext());

            double outcome = 0;
            double income = 0;
            for (Day day : dayList) {
                Log.d("Outcome",String.valueOf(outcome));
                outcome += day.getOutcome_day();
                Log.d("Outcome",String.valueOf(outcome));
                Log.d("Outcome_day", String.valueOf(day.getOutcome_day()));
                income += day.getIncome_day();
                DecimalFormat df = new DecimalFormat("#.##");

                label_out.setText(String.valueOf(df.format(outcome)));
                label_in.setText(String.valueOf(df.format(income)));
            }
        } else {
            DayCardAdapter Adapter = new DayCardAdapter(dayList, CardDetailActivity.this);
            rec_day.setAdapter(Adapter);
            label_out.setText("0");
            label_in.setText("0");
        }
    }
    void initMonthlist(){
        monthList.clear();
        Log.d("test","monthlist");
        // 因为LitePal不支持group by, 故使用SQL语句查询
        Cursor cursor = LitePal.findBySQL("select card, date_month ,date_year from Accounts where card = ?"+"and date_year=?" +
                        "group by date_month order by date_month asc",
                label.getText().toString(),
                label_year.getText().toString());
        if (cursor.moveToFirst()) {
            do {
                String card = cursor.getString(0);
                String month = cursor.getString(1);
                String year = cursor.getString(2);
                Log.d("test",card);
                Log.d("test",month);
                Log.d("test",year);
                monthList.add(new Month(year,month,card));
                MonthAdapter Adapter = new MonthAdapter(monthList, CardDetailActivity.this);
                rec_day.setAdapter(Adapter);
            } while (cursor.moveToNext());

            double outcome = 0;
            double income = 0;
            for (Month month : monthList) {
                outcome += month.getOutcome_month();
                income += month.getIncome_month();
                DecimalFormat df = new DecimalFormat("#.##");

                label_out.setText(String.valueOf(df.format(outcome)));
                label_in.setText(String.valueOf(df.format(income)));
            }
        } else {
            MonthAdapter Adapter = new MonthAdapter(monthList, CardDetailActivity.this);
            rec_day.setAdapter(Adapter);
            label_out.setText("0");
            label_in.setText("0");
        }
    }
    private void initPicker_YM() {//年
        TimePickerPopup timePickerPopup = new TimePickerPopup(CardDetailActivity.this)
                .setTimePickerListener(new TimePickerListener() {
                    @Override
                    public void onTimeChanged(Date date) {

                    }

                    @Override
                    public void onTimeConfirm(Date date, View view) {
                       //Toast.makeText(CardDetailActivity.this, "选择的时间："+date.toLocaleString(), Toast.LENGTH_SHORT).show();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        year = String.valueOf(calendar.get(Calendar.YEAR));
                        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                        label_year.setText(year);
                        label_month.setText(month);
                        label_time2.setText("-");
                        initDaylist();
                    }
                });
        timePickerPopup.setMode(TimePickerPopup.Mode.YM);
        datePicker = new XPopup.Builder(CardDetailActivity.this).asCustom(timePickerPopup);
    }
    private void initPicker_Y() {
        TimePickerPopup timePickerPopup = new TimePickerPopup(CardDetailActivity.this)
                .setTimePickerListener(new TimePickerListener() {
                    @Override
                    public void onTimeChanged(Date date) {

                    }

                    @Override
                    public void onTimeConfirm(Date date, View view) {
//                        Toast.makeText(CardDetailActivity.this, "选择的时间："+date.toLocaleString(), Toast.LENGTH_SHORT).show();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        year = String.valueOf(calendar.get(Calendar.YEAR));
                        label_year.setText(year);
                        label_month.setText("");
                        label_time2.setText("");
                        initMonthlist();
                    }
                });
        timePickerPopup.setMode(TimePickerPopup.Mode.Y);
        datePicker = new XPopup.Builder(CardDetailActivity.this).asCustom(timePickerPopup);
    }


    void initInternetDayList(){
        // 封装参数
        String url = "42.193.103.76:8888/flow/month/card";
        HashMap<String, String> params = new HashMap<>();
        params.put("card",label.getText().toString());
        params.put("year", label_year.getText().toString());
        params.put("month", label_month.getText().toString());

//        HttpUtil.sendGETRequestWithTokenCardDetailDay(url, params, new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                // 解析响应的数据
//                parseJSONWithFastjson(response.body().string());
//            }
//        });
    }
    void parseJSONWithFastjson(String jsonData) {
        dayList.clear();
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String data = object.getString("data");
        JSONObject dataObject = JSON.parseObject(data);
        final Double income = dataObject.getDouble("income") == null ? 0 : dataObject.getDouble("income");
        final Double expense = dataObject.getDouble("expense") == null ? 0 : dataObject.getDouble("expense");
        JSONArray list = dataObject.getJSONArray("list");

        // 调试信息
        System.out.println("=================CardDetailActivity.parseJSONWithFastjson()===================");
        System.out.println("code:"+code);
        System.out.println("message:"+message);
        System.out.println("data:"+data);
        System.out.println("income:"+income);
        System.out.println("expense:"+expense);

//        for (int i = 0; i < list.size(); i++) {
//            System.out.println("==========list"+i+"=============");
//            JSONObject jsonObject = list.getJSONObject(i);
//            String date = jsonObject.getString("date");
//            System.out.println("date:"+date);
//            Double dayIncome = jsonObject.getDouble("income") == null ? 0 : jsonObject.getDouble("income");
//            System.out.println("dayIncome:"+dayIncome);
//            Double dayExpense = jsonObject.getDouble("expense") == null ? 0: jsonObject.getDouble("expense");
//            System.out.println("dayExpense:"+dayExpense);
//            JSONArray accountList = jsonObject.getJSONArray("list");
//            ArrayList<Single> singleList = new ArrayList<>();
//            for (int j = 0; j < accountList.size(); j++) {
//                Accounts account = accountList.getObject(j, Accounts.class);
//                System.out.println(account);
//                Single single = new Single(account.getId(),
//                        account.getInorout(),
//                        account.getFirst(),
//                        account.getSecond(),
//                        account.getPrice(),
//                        account.getDate(),
//                        account.getCard(),
//                        account.getMember());
//                singleList.add(single);
//            }
//            DayFlow dayFlow = new DayFlow(date, dayIncome, dayExpense, singleList);
//            dayFlows.add(dayFlow);
//        }

//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                DecimalFormat df = new DecimalFormat("#.##");
//                label_in.setText(df.format(income));
//                label_out.setText(df.format(expense));
//                dayFlowAdapter.notifyDataSetChanged();
//            }
//        });

    }

}
