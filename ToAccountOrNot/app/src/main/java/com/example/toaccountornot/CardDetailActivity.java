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

import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.Cards;
import com.example.toaccountornot.utils.Day;
import com.example.toaccountornot.utils.DayCardAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopupext.listener.TimePickerListener;
import com.lxj.xpopupext.popup.TimePickerPopup;

import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CardDetailActivity extends AppCompatActivity {

    private String year;
    private String month;
    private TextView label;
    private TextView label_year;
    private TextView label_month;
    private TextView label_out;
    private TextView label_in;
    private LinearLayout choose_date;
    private RecyclerView rec_day;
    private List<Day> dayList = new ArrayList<>();
    private String cardname;
    private BasePopupView datePicker;
    private ImageView return_bar;
    private ImageView delete_bar;

    @Override
    protected void onResume(){
        super.onResume();
        initDaylist();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        initView();
        initPicker();
        initClickListener();
    }
    void initView() {
        Bundle bundle = this.getIntent().getExtras();
        cardname = bundle.getString("name");
        label = findViewById(R.id.label);
        label_year = findViewById(R.id.label_year);
        label_month = findViewById(R.id.label_month);
        label_out = findViewById(R.id.label_out);
        label_in = findViewById(R.id.label_in);
        choose_date = findViewById(R.id.choose_date2);
        rec_day = findViewById(R.id.mainlist);
        return_bar = findViewById(R.id.return_bar);
        delete_bar = findViewById(R.id.delete_bar);
        label.setText(cardname);
        label_year.setText(year);
        label_month.setText(month);
        rec_day.setLayoutManager(new LinearLayoutManager(CardDetailActivity.this));

        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
                Toast.makeText(CardDetailActivity.this,"你点击了按钮",Toast.LENGTH_SHORT).show();
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
        dayList.clear();
        // 因为LitePal不支持group by, 故使用SQL语句查询
        Cursor cursor = LitePal.findBySQL("select card,date from Accounts where card = ?"+"and date_year=?" +
                        "and date_month=? group by date order by date desc",
                label.getText().toString(),
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
                outcome += day.getOutcome_day();
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
    private void initPicker() {
        TimePickerPopup timePickerPopup = new TimePickerPopup(CardDetailActivity.this)
                .setTimePickerListener(new TimePickerListener() {
                    @Override
                    public void onTimeChanged(Date date) {

                    }

                    @Override
                    public void onTimeConfirm(Date date, View view) {
                        Toast.makeText(CardDetailActivity.this, "选择的时间："+date.toLocaleString(), Toast.LENGTH_SHORT).show();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        year = String.valueOf(calendar.get(Calendar.YEAR));
                        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                        label_year.setText(year);
                        label_month.setText(month);
                        initDaylist();
                    }
                });
        timePickerPopup.setMode(TimePickerPopup.Mode.YM);
        datePicker = new XPopup.Builder(CardDetailActivity.this).asCustom(timePickerPopup);
    }
}
