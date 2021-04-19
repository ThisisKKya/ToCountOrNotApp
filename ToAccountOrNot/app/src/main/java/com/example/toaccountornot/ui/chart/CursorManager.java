package com.example.toaccountornot.ui.chart;

import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.toaccountornot.utils.Utils;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.litepal.LitePalApplication.getContext;

public class CursorManager {
    public String year;
    public String month;
    public String week;
    public String date;
    public Date mCurrentDate;
    public Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    public int order;



    public CursorManager () {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        week = String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
        mCurrentDate = calendar.getTime();
        date = simpleDateFormat.format(calendar.getTime());
    }

    public void change_cur(int flag,String time) {

        if(flag != 0) {
            calendar.setTime(mCurrentDate);
            switch (time) {
                case "月":
                    calendar.add(Calendar.MONTH, flag);
                    mCurrentDate = calendar.getTime();
                    month = String.valueOf(calendar.get(Calendar.MONTH)+1);
                    break;
                case "年":
                    calendar.add(Calendar.YEAR, flag);
                    mCurrentDate = calendar.getTime();
                    year = String.valueOf(calendar.get(Calendar.YEAR));
                    break;
                case "周":
                    calendar.add(Calendar.WEEK_OF_YEAR, flag);
                    mCurrentDate = calendar.getTime();
                    week = String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
                    break;
                case "天":
                    calendar.add(Calendar.DATE, flag);
                    mCurrentDate = calendar.getTime();
                    date = simpleDateFormat.format(calendar.getTime());
                    break;
            }



//            Log.d("hello_month",month);
            //Toast.makeText(getContext(),month, Toast.LENGTH_SHORT).show();
        }
    }

    // by the same month
    // use by piechart and RV.one
    public Cursor initCur_one(String inorout) {
        //Log.d("hello",year);
        //Log.d("hello",month);
        Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date_year=?" +
                        "and date_month=? and inorout=? group by first order by first desc",
                year,
                month,
                inorout);
        if (cursor.moveToFirst()) Log.d("hello","true");
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

    public Cursor initCur_one_week(String inorout) {
        Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date_week=?" +
                        "and inorout=? group by first order by first desc",
                week,
                inorout);
        return cursor;
    }

    // 点击柱状图流水展示
    public Cursor initCur_one_mem(String inorout,String member) {
        Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date_year=?" +
                        "and date_month=? and inorout=? and member=? group by first order by first desc",
                year,
                month,
                inorout,
                member);
        if (cursor.moveToFirst()) Log.d("hello","true");
        return cursor;
    }


    public Cursor initCur_one_year_mem(String inorout,String member) {
        Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date_year=?" +
                        "and inorout=? and member=? group by first order by first desc",
                year,
                inorout,
                member);
        return cursor;
    }

    public Cursor initCur_one_day_mem(String inorout,String member) {
        Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date=?" +
                        "and inorout=? and member=? group by first order by first desc",
                date,
                inorout,
                member);
        return cursor;
    }

    public Cursor initCur_one_week_mem(String inorout,String member) {
        Cursor cursor = LitePal.findBySQL("select first,sum(price) from Accounts where date_week=?" +
                        "and inorout=? and member=? group by first order by first desc",
                week,
                inorout,
                member);
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

    public Cursor initCur_two_year(String inorout,String first_name) {
        Cursor cursor = LitePal.findBySQL("select second,sum(price) from Accounts where date_year=?" +
                        "and inorout=? and first=? group by first order by first desc",
                year,
                inorout,
                first_name);
        return cursor;
    }

    public Cursor initCur_two_day(String inorout,String first_name) {
        Cursor cursor = LitePal.findBySQL("select second,sum(price) from Accounts where date=?" +
                        "and inorout=? and first=? group by first order by first desc",
                date,
                inorout,
                first_name);
        return cursor;
    }

    public Cursor initCur_two_week(String inorout,String first_name) {
        Cursor cursor = LitePal.findBySQL("select second,sum(price) from Accounts where date_week=?" +
                        "and inorout=? and first=? group by first order by first desc",
                week,
                inorout,
                first_name);
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

    public Cursor initCur_mem_week(String inorout) {
        Cursor cursor = LitePal.findBySQL("select member,sum(price) from Accounts where date_week=?" +
                        "and inorout=? group by member order by member desc",
                week,
                inorout);
        return cursor;
    }
}
