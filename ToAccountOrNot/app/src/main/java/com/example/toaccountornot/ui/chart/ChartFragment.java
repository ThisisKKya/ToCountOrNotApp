package com.example.toaccountornot.ui.chart;

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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
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
    private Toast toast = null;

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
        initPieChart_income();
        initRV(1);


        fab = (View) getActivity().findViewById(R.id.fab1);
        menuLayout = (View) getActivity().findViewById(R.id.menu_layout1);
        arcLayout = (ArcLayout) getActivity().findViewById(R.id.arc_layout1);

        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast((Button) v);
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
                barChart.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                initPieChart_income();
                initRV(1);
            }
        });

        one_pei_outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hello" ,"1111");
                barChart.setVisibility(View.GONE);      // 隐藏柱状图
                pieChart.setVisibility(View.VISIBLE);   // 显示饼状图
                initPieChart_outcome();
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Demo demo = Demo.values()[11];
                demo.startActivity(getActivity());
            }
        });

    }


    // 收入饼状图
    private void initPieChart_income() {
        // 获取饼状图收入数据
        PieData pieData = new PieData();
        List<PieEntry> yVals = pieData.income();

        // 饼状图颜色获取
        PieColor pieColor = new PieColor();
        List<Integer> colors = pieColor.initcolor();

        PieChartManager pieChartManager = new PieChartManager(pieChart);
        pieChartManager.showSolidPieChart(yVals,colors);
        pieChart.postInvalidate();
    }

    // 支出饼状图
    private void initPieChart_outcome() {
        // 获得饼状图支出数据
        PieData pieData = new PieData();
        List<PieEntry> yVals = pieData.outcome();

        PieColor pieColor = new PieColor();
        List<Integer> colors = pieColor.initcolor();

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
        if(i == 1)    myList = rvList.choice(0);        // 流水展示饼状图收入类
        else if (i == 2)    myList = rvList.choice(1);  // 流水展示饼状图支出类
        else    myList = rvList.choice(2);              // 流水展示柱状图
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        incomeAdapter adapter = new incomeAdapter(myList);
        recyclerView.setAdapter(adapter);
    }

    private void showToast(Button btn) {
        if (toast != null) {
            toast.cancel();
        }

        String text = "Clicked: " + btn.getText();
        //toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        //toast.show();

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