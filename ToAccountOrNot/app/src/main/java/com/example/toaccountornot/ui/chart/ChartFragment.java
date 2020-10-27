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

import java.util.ArrayList;
import java.util.Calendar;
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
    private List<income> myList = new ArrayList<>();
    private View fab;
    private View menuLayout;
    private ArcLayout arcLayout;
    private List<Accounts> accounts = new ArrayList<>();
    private int num = 1;    // 判断点击哪个饼状图;
    private int click_left = 0;     // 点击
    private String menu_day = "月";    // 保存菜单点击属性

    private String my_year;
    private String my_month;
    private String my_week;
    private String my_day;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart,container,false);
        return view;

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
        fab = (View) getActivity().findViewById(R.id.fab1);
        menuLayout = (View) getActivity().findViewById(R.id.menu_layout1);
        arcLayout = (ArcLayout) getActivity().findViewById(R.id.arc_layout1);


        barChart.setNoDataText("");
        pieChart.setNoDataText("");

        initaccouts();
        initPieChart_income("一","月",0);
        initRV(1,"一","月",0);

        Calendar calendar = Calendar.getInstance();
        my_year = String.valueOf(calendar.get(Calendar.YEAR));
        my_month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        //initdate();



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
                    }
                    else if(num ==2) {
                        //Log.d("hello",mytime[finalI]);
                        initPieChart_outcome("一",mytime[finalI],0);
                        initRV(num,"一",mytime[finalI],0);
                    }
                    else {
                        Log.d("hello",mytime[finalI]);
                        initBarchart(mytime[finalI],0);
                        initRV(num,"一",mytime[finalI],0);
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
                barChart.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                initPieChart_income("一","月",0);
                initRV(1,"一","月",0);      // 收入的流水一级展示
                num = 1;
            }
        });


        one_pei_outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hello" ,"click outcome");
                barChart.setVisibility(View.GONE);      // 隐藏柱状图
                pieChart.setVisibility(View.VISIBLE);
                initPieChart_outcome("一","月",0);
                initRV(2,"一","月",0);      // 支出的一级流水展示
                num = 2;
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChart.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
                initBarchart("月",0);
                initRV(3,"一","月",0);      // 柱状图的流水展示
                num = 3;
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
                //Log.d("goodbye", String.valueOf(num));
                //Toast.makeText(getContext(),"111111",Toast.LENGTH_SHORT).show();
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
                click_left = click_left + 1;
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



    // 收入饼状图
    // cate:一级or二级
    private void initPieChart_income(String cate,String time,int flag) {
        Log.d("hello","1111");
        // 获取饼状图收入数据
        PieData pieData = new PieData();
        List<PieEntry> yVals = pieData.income(time,flag);

        // 饼状图颜色获取
        PieColor pieColor = new PieColor();
        List<Integer> colors = pieColor.initcolor(accounts,"in",yVals.size());

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
        List<Integer> colors = pieColor.initcolor(accounts,"out",yVals.size());

        PieChartManager pieChartManager = new PieChartManager(pieChart);
        pieChartManager.showSolidPieChart(yVals,colors);
        pieChart.postInvalidate();
    }


    // 柱状图
    void initBarchart(String time,int flag) {
        //填充数据
        BarData barData = new BarData(time,flag);
        List<BarEntry> barEntry1 = barData.income(accounts) ;  //成员的收入
        List<BarEntry> barEntry2 = barData.outcome(accounts) ;  //成员的支出

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
        if(i == 1)    myList = rvList.choice(0,accounts,cate,time,flag);        // 流水展示饼状图收入类
        else if (i == 2)    myList = rvList.choice(1,accounts,cate,time,flag);  // 流水展示饼状图支出类
        else    myList = rvList.choice(2,accounts,cate,time,flag);              // 流水展示柱状图
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

    private void initaccouts() {
        Accounts x1 = new Accounts();
        x1.setFirst("工资");
        x1.setSecond("工资1");
        x1.setMember("我");
        x1.setPrice(1000f);
        x1.setInorout("in");
        x1.setDate("1");
        x1.setDate_month("7");
        x1.setDate_year("2020");
        Accounts x2 = new Accounts();
        x2.setFirst("工资");
        x2.setSecond("工资2");
        x2.setMember("我");
        x2.setPrice(1500f);
        x2.setInorout("in");
        x2.setDate("2");
        x2.setDate_month("7");
        x2.setDate_year("2020");
        Accounts x9 = new Accounts();
        x9.setFirst("工资");
        x9.setSecond("工资2");
        x9.setMember("我");
        x9.setPrice(1500f);
        x9.setInorout("in");
        x9.setDate("2");
        x9.setDate_month("7");
        x9.setDate_year("2020");
        Accounts x3 = new Accounts();
        x3.setFirst("兼职");
        x3.setSecond("兼职1");
        x3.setMember("妈妈");
        x3.setPrice(1000f);
        x3.setInorout("in");
        x3.setDate("3");
        x3.setDate_month("7");
        x3.setDate_year("2020");
        Accounts x4 = new Accounts();
        x4.setFirst("礼金");
        x4.setSecond("礼金1");
        x4.setMember("爸爸");
        x4.setPrice(1000f);
        x4.setInorout("in");
        x4.setDate("4");
        x4.setDate_month("7");
        x4.setDate_year("2020");
        accounts.add(x1);
        accounts.add(x2);
        accounts.add(x3);
        accounts.add(x4);
        accounts.add(x9);

        Accounts x5 = new Accounts();
        x5.setFirst("餐饮");
        x5.setSecond("餐饮1");
        x5.setMember("我");
        x5.setPrice(100f);
        x5.setInorout("out");
        x5.setDate("1");
        x5.setDate_month("7");
        x5.setDate_year("2020");
        Accounts x6 = new Accounts();
        x6.setFirst("餐饮");
        x6.setSecond("餐饮2");
        x6.setMember("妈妈");
        x6.setPrice(300f);
        x6.setInorout("out");
        x6.setDate("2");
        x6.setDate_month("7");
        x6.setDate_year("2020");
        Accounts x7 = new Accounts();
        x7.setFirst("购物");
        x7.setSecond("购物1");
        x7.setMember("妈妈");
        x7.setPrice(200f);
        x7.setInorout("out");
        x7.setDate("3");
        x7.setDate_month("7");
        x7.setDate_year("2020");
        Accounts x8 = new Accounts();
        x8.setFirst("日用");
        x8.setSecond("日用1");
        x8.setMember("爸爸");
        x8.setPrice(500f);
        x8.setInorout("out");
        x8.setDate("4");
        x8.setDate_month("7");
        x8.setDate_year("2020");
        accounts.add(x5);
        accounts.add(x6);
        accounts.add(x7);
        accounts.add(x8);
    }

}