package com.example.toaccountornot.ui.chart;

import android.accounts.Account;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toaccountornot.CardsActivity;
import com.example.toaccountornot.R;
import com.example.toaccountornot.utils.Accounts;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ogaclejapan.arclayout.ArcLayout;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChartFragment extends Fragment{
    private ImageView one_pei_income;
    private ImageView one_pei_outcome;
    private ImageView people;
    private ImageView left;
    private ImageView right;
    private PieChart pieChart;
    private BarChart barChart;
    private RecyclerView recyclerView;
    private TextView date_year;
    private TextView date_lab1;
    private TextView date_month;
    private TextView date_lab2;
    private TextView date_weekorday;
    private TextView bottom1;
    private TextView bottom2;
    private TextView bottom3;
    private List<income> myList = new ArrayList<>();
    private View fab;
    private View menuLayout;
    private ArcLayout arcLayout;
    private int num = 1;    // 判断点击哪个饼状图;
    private int click_left = 0;     // 点击
    private String menu_day = "月";    // 保存菜单点击属性

    private String my_year;
    private String my_month;
    private String my_week;
    private String my_day;
    private View view;
    private SimpleDateFormat simpleDateFormat;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart,container,false);
        //initall(inflater, container);
        return view;

    }

    public void initall(LayoutInflater inflater, ViewGroup container) {
//        view = inflater.inflate(R.layout.fragment_chart,container,false);


//        barChart.setNoDataText("");
//        pieChart.setNoDataText("");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        one_pei_income = (ImageView) getActivity().findViewById(R.id.view_income);
        one_pei_outcome = (ImageView) getActivity().findViewById(R.id.view_outcome);
        people = (ImageView) getActivity().findViewById(R.id.people);
        left = (ImageView) getActivity().findViewById(R.id.left);
        right = (ImageView) getActivity().findViewById(R.id.right);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        pieChart = (PieChart) getActivity().findViewById(R.id.pc);
        barChart = (BarChart) getActivity().findViewById(R.id.bc);
        date_year = (TextView) getActivity().findViewById(R.id.lab_year);
        date_lab1 = (TextView) getActivity().findViewById(R.id.lab_1);
        date_month = (TextView) getActivity().findViewById(R.id.lab_month);
        date_lab2 = (TextView) getActivity().findViewById(R.id.lab_2);
        date_weekorday = (TextView) getActivity().findViewById(R.id.lab_weekORday);
        bottom1 = (TextView) getActivity().findViewById(R.id.bottom_1);
        bottom2 = (TextView) getActivity().findViewById(R.id.bottom_2);
        bottom3 = (TextView) getActivity().findViewById(R.id.bottom_3);
        fab = (View) getActivity().findViewById(R.id.fab1);
        menuLayout = (View) getActivity().findViewById(R.id.menu_layout1);
        arcLayout = (ArcLayout) getActivity().findViewById(R.id.arc_layout1);

        barChart.setNoDataText("");
        pieChart.setNoDataText("");

        initPieChart_income("一","月",0);
        initRV(1,"一","月",0);
        initdate("月",0);
        bottom1.setVisibility(View.VISIBLE);
        bottom2.setVisibility(View.GONE);
        bottom3.setVisibility(View.GONE);


        final String[] mytime = new String[]{"天","周","月","年"};

        right.setVisibility(View.GONE);


        // 菜单的点击事件
        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            final int finalI = i;
            arcLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Log.d("hello", String.valueOf(finalI));
                    // finalI(天):0
                    menu_day = mytime[finalI];
                    click_left = 0;
                    if(num == 1) {
                        Log.d("hello","click menu_income");
                        initPieChart_income("一",mytime[finalI],0);
                        initRV(num,"一",mytime[finalI],0);
                        initdate(mytime[finalI],0);
                    }
                    else if(num ==2) {
                        //Log.d("hello",mytime[finalI]);
                        initPieChart_outcome("一",mytime[finalI],0);
                        initRV(num,"一",mytime[finalI],0);
                        initdate(mytime[finalI],0);
                    }
                    else {
                        Log.d("hello",mytime[finalI]);
                        initBarchart(mytime[finalI],0);
                        initRV(num,"一",mytime[finalI],0);
                        initdate(mytime[finalI],0);
                    }
                    hideMenu();
                }
            });
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick(v);
            }
        });


        // 点击事件
        one_pei_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hello","click income");
                if(num != 1) {
                    bottom1.setVisibility(View.VISIBLE);
                    bottom2.setVisibility(View.GONE);
                    bottom3.setVisibility(View.GONE);
                    barChart.setVisibility(View.GONE);
                    pieChart.setVisibility(View.VISIBLE);
                    initPieChart_income("一","月",0);
                    initRV(1,"一","月",0);      // 收入的流水一级展示
                    initdate("月",0);
                    num = 1;
                }
            }
        });


        one_pei_outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num != 2) {
                    bottom1.setVisibility(View.GONE);
                    bottom2.setVisibility(View.VISIBLE);
                    bottom3.setVisibility(View.GONE);
                    Log.d("hello" ,"click outcome");
                    barChart.setVisibility(View.GONE);      // 隐藏柱状图
                    pieChart.setVisibility(View.VISIBLE);
                    initPieChart_outcome("一","月",0);
                    initRV(2,"一","月",0);      // 支出的一级流水展示
                    initdate("月",0);
                    num = 2;
                }
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num != 3) {
                    bottom1.setVisibility(View.GONE);
                    bottom2.setVisibility(View.GONE);
                    bottom3.setVisibility(View.VISIBLE);
                    pieChart.setVisibility(View.GONE);
                    barChart.setVisibility(View.VISIBLE);
                    initBarchart("月",0);
                    initRV(3,"一","月",0);      // 柱状图的流水展示
                    initdate("月",0);
                    num = 3;
                }
            }
        });


//        pieChart.setonCh
        // 饼状图的点击事件
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int a = (int)h.getX();
                Log.d("hello",menu_day);
                initRV(num,String.valueOf(a),menu_day,click_left);

            }
            @Override
            public void onNothingSelected () {
                initRV(num,"一",menu_day,click_left);
            }
        });


        // 菜单左右点击事件
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right.setVisibility(View.VISIBLE);
                click_left = click_left - 1;
                initdate(menu_day,click_left);

                if(num == 1) {      // 收入
                    //Log.d("hello","111111");
                    //Toast.makeText(getContext(),"111111",Toast.LENGTH_SHORT).show();
                    Log.d("hello", String.valueOf(click_left));
                    initPieChart_income("一",menu_day,click_left);
                    initRV(1,"一",menu_day,click_left);
                }
                else if(num == 2) {     // 支出
                    Log.d("hello", String.valueOf(click_left));
                    initPieChart_outcome("一",menu_day,click_left);
                    initRV(2,"一",menu_day,click_left);
                }
                else {      // 成员
                    initBarchart(menu_day,click_left);
                    initRV(3,"一",menu_day,click_left);
                }
            }
        });


        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left.setVisibility(View.VISIBLE);
                click_left = click_left + 1;
                initdate(menu_day,click_left);

                if(click_left == 0) right.setVisibility(View.GONE);

                if(num == 1) {      // 收入
                    //Log.d("hello","111111");
                    Log.d("hello", String.valueOf(click_left));
                    initPieChart_income("一",menu_day,click_left);
                    initRV(1,"一",menu_day,click_left);
                }
                else if(num == 2) {     // 支出
                    initPieChart_outcome("一",menu_day,click_left);
                    initRV(2,"一",menu_day,click_left);
                }
                else {      // 成员
                    initBarchart(menu_day,click_left);
                    initRV(3,"一",menu_day,click_left);
                }
            }
        });


    }

    //  初始化时间栏
    private void initdate(String time,int click) {
        Calendar calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        switch (time)
        {
            case "月" :
                date_year.setVisibility(View.VISIBLE);
                date_lab1.setVisibility(View.VISIBLE);
                date_month.setVisibility(View.VISIBLE);
                date_lab2.setVisibility(View.GONE);
                date_weekorday.setVisibility(View.GONE);
                my_year = String.valueOf(calendar.get(Calendar.YEAR));
                date_year.setText(my_year);
                calendar.add(Calendar.MONTH,click);
                my_month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                date_month.setText(my_month);
                if(Integer.parseInt(my_month) == 1)  left.setVisibility(View.GONE);
                break;
            case "年" :
                date_year.setVisibility(View.VISIBLE);
                date_lab1.setVisibility(View.GONE);
                date_month.setVisibility(View.GONE);
                date_lab2.setVisibility(View.GONE);
                date_weekorday.setVisibility(View.GONE);
                calendar.add(Calendar.YEAR,click);
                my_year = String.valueOf(calendar.get(Calendar.YEAR));
                date_year.setText(my_year);
                if(Integer.parseInt(my_year) == 1)  left.setVisibility(View.GONE);
                break;
            case "天" :
                date_year.setVisibility(View.GONE);
                date_lab1.setVisibility(View.GONE);
                date_month.setVisibility(View.GONE);
                date_lab2.setVisibility(View.GONE);
                date_weekorday.setVisibility(View.VISIBLE);
                calendar.add(Calendar.DATE,click);
                my_day = simpleDateFormat.format(calendar.getTime());
                date_weekorday.setText(my_day);
                if(calendar.get(Calendar.DAY_OF_MONTH) == 1)    left.setVisibility(View.GONE);
                break;
            case "周":
                date_year.setVisibility(View.GONE);
                date_lab1.setVisibility(View.GONE);
                date_month.setVisibility(View.GONE);
                date_lab2.setVisibility(View.GONE);
                date_weekorday.setVisibility(View.VISIBLE);
//                date_year.setText(my_year);
//                date_month.setText(my_month);
                calendar.add(Calendar.WEEK_OF_YEAR, click);
                my_week = String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
                date_weekorday.setText(my_week + "周");
                if(Integer.parseInt(my_week) == 1)  left.setVisibility(View.GONE);
                break;
        }

    }



    // 收入饼状图
    // cate:一级or二级
    private void initPieChart_income(String cate,String time,int flag) {
        Log.d("hello","1111");
        // 获取饼状图收入数据
        PieData pieData = new PieData();
        List<PieEntry> yVals = pieData.income(time,flag);

        // 饼状图颜色获取
        PieColor pieColor = new PieColor();
        List<Integer> colors = pieColor.initcolor("in",yVals.size());

        PieChartManager pieChartManager = new PieChartManager(pieChart);
        pieChartManager.showSolidPieChart(yVals,colors);
        pieChart.postInvalidate();
    }

    // 支出饼状图
    private void initPieChart_outcome(String cate,String time,int flag) {
        // 获得饼状图支出数据
        PieData pieData = new PieData();
        List<PieEntry> yVals = pieData.outcome(time,flag);

        PieColor pieColor = new PieColor();
        List<Integer> colors = pieColor.initcolor("out",yVals.size());

        PieChartManager pieChartManager = new PieChartManager(pieChart);
        pieChartManager.showSolidPieChart(yVals,colors);
        pieChart.postInvalidate();
    }


    // 柱状图
    void initBarchart(String time,int flag) {
        //填充数据
        BarData barData = new BarData(time,flag);
        List<BarEntry> barEntry1 = barData.income() ;  //成员的收入
        List<BarEntry> barEntry2 = barData.outcome() ;  //成员的支出

        BarChartManager barChartManager = new BarChartManager(barChart);
        barChartManager.showBarChart(barEntry1,barEntry2);
        //刷新
        barChart.invalidate();
    }


    // 流水展示
    // int i:1(income),2(outcome),3(people)
    // cate:"一"，"二"
    private void initRV(int i,String cate,String time,int flag) {
        RvList rvList = new RvList(myList);
        myList = rvList.choice(i-1,cate,time,flag);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        incomeAdapter adapter = new incomeAdapter(myList);
        recyclerView.setAdapter(adapter);
    }


    private void onFabClick(View v) {
        if (v.isSelected()) {
            hideMenu();
        } else {
            showMenu();
        }
        v.setSelected(!v.isSelected());
    }

    private void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    @SuppressWarnings("NewApi")
    private void hideMenu() {

        List<Animator> animList = new ArrayList<>();

        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();

    }

    private Animator createShowItemAnimator(View item) {

        float dx = fab.getX() - item.getX();
        float dy = fab.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        return anim;
    }

    private Animator createHideItemAnimator(final View item) {
        float dx = fab.getX() - item.getX();
        float dy = fab.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }


}