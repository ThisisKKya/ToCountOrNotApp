package com.example.toaccountornot.ui.detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.toaccountornot.R;
import com.example.toaccountornot.baidu_ocr.BaiduOcrActivity;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.Day;
import com.example.toaccountornot.utils.DayAdapter;
import com.example.toaccountornot.utils.DayFlow;
import com.example.toaccountornot.utils.DayFlowAdapter;
import com.example.toaccountornot.utils.HttpUtil;
import com.example.toaccountornot.utils.Single;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
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


/**
 * 明细
 */
public class DetailFragment extends Fragment {

    private View view;
    private TextView label_year;
    private TextView label_month;
    private TextView label_out;
    private TextView label_in;
    private RecyclerView rec_day;
    private final List<Day> dayList = new ArrayList<>();
    private final List<DayFlow> dayFlows = new ArrayList<>();
    private DayFlowAdapter dayFlowAdapter;
    private String year;
    private String month;
    private BasePopupView datePicker;

    public static final String url = "http://10.0.2.2:8080/flow/month";

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        initView(inflater, container);
        initPicker();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("hello","11Resume");
//        initDayList();
        initDayListWithInternet();
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        label_year = view.findViewById(R.id.label_year);
        label_month = view.findViewById(R.id.label_month);
        label_out = view.findViewById(R.id.label_out);
        label_in = view.findViewById(R.id.label_in);
        LinearLayout choose_date = view.findViewById(R.id.choose_date);
        rec_day = view.findViewById(R.id.mainlist);
        LinearLayout budget_layout = view.findViewById(R.id.budget_layout);
        ImageView camera = view.findViewById(R.id.photo);
        label_year.setText(year);
        label_month.setText(month);
        rec_day.setLayoutManager(new LinearLayoutManager(getContext()));
        dayFlowAdapter = new DayFlowAdapter(getContext(), dayFlows);
        rec_day.setAdapter(dayFlowAdapter);

        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });
//        budget_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), BudgetActivity.class);
//                intent.putExtra("outcome", label_out.getText());
//                startActivity(intent);
//            }
//        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进相册or拍照
                //Intent intent = new Intent(getContext(), PhotoActivity.class);
                Intent intent = new Intent(getContext(), BaiduOcrActivity.class);
                startActivity(intent);
                }
        });

    }

    private void initDayList() {
        dayList.clear();
        // 因为LitePal不支持group by, 故使用SQL语句查询
        Cursor cursor = LitePal.findBySQL("select date from Accounts where date_year=?" +
                        "and date_month=? group by date order by date desc",
                label_year.getText().toString(),
                label_month.getText().toString());
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0);
                dayList.add(new Day(date));
                DayAdapter dayAdapter = new DayAdapter(dayList, getContext());
                rec_day.setAdapter(dayAdapter);
            } while (cursor.moveToNext());

            double outcome = 0;
            double income = 0;
            for (Day day : dayList) {
                outcome += day.getOutcome_day();
                income += day.getIncome_day();
                DecimalFormat df = new DecimalFormat("#.##");

                label_out.setText(df.format(outcome));
                label_in.setText(df.format(income));
            }
        } else {
            DayAdapter dayAdapter = new DayAdapter(dayList, getContext());
            rec_day.setAdapter(dayAdapter);
            label_out.setText("0");
            label_in.setText("0");
        }

    }

    private void initDayListWithInternet() {

        // 封装参数
        HashMap<String, String> params = new HashMap<>();
        params.put("year", label_year.getText().toString());
        params.put("month", label_month.getText().toString());

        HttpUtil.sendGETRequestWithToken(url, params, new Callback() {
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
        dayFlows.clear();
        JSONObject object = JSON.parseObject(jsonData);
        Integer code = object.getInteger("code");
        String message = object.getString("message");
        String data = object.getString("data");
        JSONObject dataObject = JSON.parseObject(data);
        final Double income = dataObject.getDouble("income") == null ? 0 : dataObject.getDouble("income");
        final Double expense = dataObject.getDouble("expense") == null ? 0 : dataObject.getDouble("expense");
        JSONArray list = dataObject.getJSONArray("list");

        // 调试信息
        System.out.println("=================DetailFragment.parseJSONWithFastjson()===================");
        System.out.println("code:"+code);
        System.out.println("message:"+message);
        System.out.println("data:"+data);
        System.out.println("income:"+income);
        System.out.println("expense:"+expense);

        for (int i = 0; i < list.size(); i++) {
            System.out.println("==========list"+i+"=============");
            JSONObject jsonObject = list.getJSONObject(i);
            String date = jsonObject.getString("date");
            System.out.println("date:"+date);
            Double dayIncome = jsonObject.getDouble("income") == null ? 0 : jsonObject.getDouble("income");
            System.out.println("dayIncome:"+dayIncome);
            Double dayExpense = jsonObject.getDouble("expense") == null ? 0: jsonObject.getDouble("expense");
            System.out.println("dayExpense:"+dayExpense);
            JSONArray accountList = jsonObject.getJSONArray("list");
            ArrayList<Single> singleList = new ArrayList<>();
            for (int j = 0; j < accountList.size(); j++) {
                Accounts account = accountList.getObject(j, Accounts.class);
                System.out.println(account);
                Single single = new Single(account.getId(),
                        account.getInorout(),
                        account.getFirst(),
                        account.getSecond(),
                        account.getPrice(),
                        account.getDate(),
                        account.getCard(),
                        account.getMember());
                singleList.add(single);
            }
            DayFlow dayFlow = new DayFlow(date, dayIncome, dayExpense, singleList);
            dayFlows.add(dayFlow);
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DecimalFormat df = new DecimalFormat("#.##");
                label_in.setText(df.format(income));
                label_out.setText(df.format(expense));
                dayFlowAdapter.notifyDataSetChanged();
            }
        });

    }

    private void initPicker() {
        TimePickerPopup timePickerPopup = new TimePickerPopup(getContext())
                .setTimePickerListener(new TimePickerListener() {
                    @Override
                    public void onTimeChanged(Date date) {

                    }

                    @Override
                    public void onTimeConfirm(Date date, View view) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        year = String.valueOf(calendar.get(Calendar.YEAR));
                        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                        label_year.setText(year);
                        label_month.setText(month);
//                        initDayList();
                        initDayListWithInternet();
                    }
                });
        timePickerPopup.setMode(TimePickerPopup.Mode.YM);
        datePicker = new XPopup.Builder(getContext()).asCustom(timePickerPopup);
    }
}