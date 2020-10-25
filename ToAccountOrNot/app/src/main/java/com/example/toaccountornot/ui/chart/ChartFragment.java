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

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment{
    private ImageView one_pei_income;
    private ImageView one_pei_outcome;
    private ImageView people;
    private PieChart pieChart;
    private BarChart barChart;
    private RecyclerView recyclerView;
    private Button button;
    private List<income> myList = new ArrayList<>();
    private View fab;
    private View menuLayout;
    private ArcLayout arcLayout;
    private List<Accounts> accounts = new ArrayList<>();
    private List<PieEntry> counts = new ArrayList<>();

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
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        pieChart = (PieChart) getActivity().findViewById(R.id.pc);
        button = (Button) getActivity().findViewById(R.id.menu);
        barChart = (BarChart) getActivity().findViewById(R.id.bc);
        barChart.setNoDataText("");
        initaccouts();
        initPieChart_income("一");
        initRV(1);



        fab = (View) getActivity().findViewById(R.id.fab1);
        menuLayout = (View) getActivity().findViewById(R.id.menu_layout1);
        arcLayout = (ArcLayout) getActivity().findViewById(R.id.arc_layout1);


        // 菜单的点击事件
        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                initPieChart_income("一");
                initRV(1);      // 收入的流水一级展示
            }
        });

        one_pei_outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hello" ,"click outcome");
                barChart.setVisibility(View.GONE);      // 隐藏柱状图
                pieChart.setVisibility(View.VISIBLE);   // 显示饼状图
                initPieChart_outcome("一");
                initRV(2);
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChart.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
                initBarchart();
                initRV(3);
            }
        });
//        pieChart.setonCh
        // 饼状图的点击事件
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
//                for (int i = 0; i < counts.size(); i++) {
//                    float x = counts.get(ee';     // 获得饼状图百分比
//                    if (x == e.getY()) {
//                        Log.d("hello", h.getDataIndex(););
//                        //break;
//                    }
//                }

                Log.d("hello", String.valueOf(h.getX()));


            }
            @Override
            public void onNothingSelected () {

            }
        });

    }



    // 收入饼状图
    // cate:一级or二级
    private void initPieChart_income(String cate) {
        Log.d("hello","1111");
        // 获取饼状图收入数据
        PieData pieData = new PieData();
        List<PieEntry> yVals = pieData.income(accounts,cate);
        counts = yVals;

        // 饼状图颜色获取
        PieColor pieColor = new PieColor();
        List<Integer> colors = pieColor.initcolor(accounts,"in");

        PieChartManager pieChartManager = new PieChartManager(pieChart);
        pieChartManager.showSolidPieChart(yVals,colors);
        pieChart.postInvalidate();
    }

    // 支出饼状图
    private void initPieChart_outcome(String cate) {
        // 获得饼状图支出数据
        PieData pieData = new PieData();
        List<PieEntry> yVals = pieData.outcome(accounts,cate);

        PieColor pieColor = new PieColor();
        List<Integer> colors = pieColor.initcolor(accounts,"out");

        PieChartManager pieChartManager = new PieChartManager(pieChart);
        pieChartManager.showSolidPieChart(yVals,colors);
        pieChart.postInvalidate();
    }


    // 柱状图
    void initBarchart() {
        //填充数据
        BarData barData = new BarData();
        List<BarEntry> barEntry1 = barData.income() ;  //成员的收入
        List<BarEntry> barEntry2 = barData.outcome() ;  //成员的支出

        BarChartManager barChartManager = new BarChartManager(barChart);
        barChartManager.showBarChart(barEntry1,barEntry2);
        //刷新
        barChart.invalidate();
    }


    // 流水展示
    private void initRV(int i) {
        RvList rvList = new RvList(myList);
        if(i == 1)    myList = rvList.choice(0,accounts);        // 流水展示饼状图收入类
        else if (i == 2)    myList = rvList.choice(1,accounts);  // 流水展示饼状图支出类
        else    myList = rvList.choice(2,accounts);              // 流水展示柱状图
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