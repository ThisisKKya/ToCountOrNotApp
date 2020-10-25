package com.example.toaccountornot.ui.chart;

import android.database.Cursor;

import org.litepal.LitePal;

import java.util.Calendar;

public class CursorManager {
    public String year;
    public String month;
    public Calendar calendar;

    public CursorManager () {
        calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
    }

    // by the same month
    // use by piechart and RV.one
    public Cursor initCur_one(String inorout) {
        Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date_year=?" +
                        "and date_month=? and inorout=? group by first order by first desc",
                year,
                month,
                inorout);
        return cursor;
    }


    // by the month
    // use by RV.two
    public Cursor initCur_two(String inorout,String first_name) {
        Cursor cursor = LitePal.findBySQL("select second,sum(price) from Accounts where date_year=?" +
                        "and date_month=? and inorout=? and first=? group by second order by second desc",
                year,
                month,
                inorout,
                first_name
        );
        return cursor;
    }


}
