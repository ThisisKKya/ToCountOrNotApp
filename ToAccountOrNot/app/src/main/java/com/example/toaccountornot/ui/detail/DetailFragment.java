package com.example.toaccountornot.ui.detail;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toaccountornot.R;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.Day;
import com.example.toaccountornot.utils.DayAdapter;

import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 明细
 */
public class DetailFragment extends Fragment {

    private View view;
    private TextView label;
    private TextView label_year;
    private TextView label_month;
    private TextView label_out;
    private TextView label_in;
    private LinearLayout choose_date;
    private RecyclerView rec_day;
    private List<Day> dayList = new ArrayList<>();
    private String year;
    private String month;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        initView(inflater, container);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initDaylist();
    }

    void initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        label = view.findViewById(R.id.label);
        label_year = view.findViewById(R.id.label_year);
        label_month = view.findViewById(R.id.label_month);
        label_out = view.findViewById(R.id.label_out);
        label_in = view.findViewById(R.id.label_in);
        choose_date = view.findViewById(R.id.choose_date);
        rec_day = view.findViewById(R.id.mainlist);

        label_year.setText(year);
        label_month.setText(month);
        rec_day.setLayoutManager(new LinearLayoutManager(getContext()));

        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 待补充 使用DatePicker
            }
        });
    }

    void initDaylist() {
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

                label_out.setText(String.valueOf(df.format(outcome)));
                label_in.setText(String.valueOf(df.format(income)));
            }
        } else {
            DayAdapter dayAdapter = new DayAdapter(dayList, getContext());
            rec_day.setAdapter(dayAdapter);
            label_out.setText("0");
            label_in.setText("0");
        }
    }
}