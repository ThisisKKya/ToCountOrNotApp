package com.example.toaccountornot.ui.chart;

import android.database.Cursor;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CursorManager {
    public String year;
    public String month;
    public String date;
    public Calendar calendar;

    public CursorManager () {
        calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(calendar.getTime());
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


    public Cursor initCur_one_year(String inorout) {
        Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date_year=?" +
                        "and inorout=? group by first order by first desc",
                year,
                inorout);
        return cursor;
    }

    public Cursor initCur_one_day(String inorout) {
        Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date=?" +
                        "and inorout=? group by first order by first desc",
                date,
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

    public Cursor initCur_two_year(String inorout) {
        Cursor cursor = LitePal.findBySQL("select second,sum(price) from Accounts where date_year=?" +
                        "and inorout=? group by first order by first desc",
                year,
                inorout);
        return cursor;
    }

    public Cursor initCur_two_day(String inorout) {
        Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date=?" +
                        "and inorout=? group by first order by first desc",
                date,
                inorout);
        return cursor;
    }

    public Cursor initCur_mem(String inorout) {
        Cursor cursor = LitePal.findBySQL("select member,sum(price) from Accounts where date_year=?" +
                        "and date_month=? and inorout=? group by member order by member desc",
                year,
                month,
                inorout);
        return cursor;
    }

    public Cursor initCur_mem_year(String inorout) {
        Cursor cursor = LitePal.findBySQL("select member,sum(price) from Accounts where date_year=?" +
                        "and inorout=? group by member order by member desc",
                year,
                inorout);
        return cursor;
    }

    public Cursor initCur_mem_day(String inorout) {
        Cursor cursor = LitePal.findBySQL("select member,sum(price) from Accounts where date=?" +
                        "and inorout=? group by member order by member desc",
                date,
                inorout);
        return cursor;
    }

}
