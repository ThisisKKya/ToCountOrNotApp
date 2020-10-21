package com.example.toaccountornot.ui.chart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

import com.example.toaccountornot.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment{
    private ImageView one_pei_income;
    private ImageView one_pei_outcome;
    private ImageView people;
    private PieChart pieChart;
    private BarChart barChart;
    private RecyclerView recyclerView;
    private List<income> myList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //pieChart = (PieChart) getActivity().findViewById(R.id.pc);
        one_pei_income = (ImageView) getActivity().findViewById(R.id.view_income);
        one_pei_outcome = (ImageView) getActivity().findViewById(R.id.view_outcome);
        people = (ImageView) getActivity().findViewById(R.id.people);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);

        pieChart = (PieChart) getActivity().findViewById(R.id.pc);
        barChart = (BarChart) getActivity().findViewById(R.id.bc);
        barChart.setNoDataText("");
        initPieChart_income();
        initRV(1);

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


    }


    private void initPieChart_income() {
        List<PieEntry> yVals = getPieChartData_income();
        List<Integer> colors = getcolor();
        PieChartManager pieChartManager = new PieChartManager(pieChart);
        pieChartManager.showSolidPieChart(yVals,colors);
        pieChart.postInvalidate();
    }

    private void initPieChart_outcome() {
        List<PieEntry> yVals = getPieChartData_outcome();
        List<Integer> colors = getcolor();
        PieChartManager pieChartManager = new PieChartManager(pieChart);
        pieChartManager.showSolidPieChart(yVals,colors);
        pieChart.postInvalidate();
    }


    // 获取收入数据
    private List<PieEntry> getPieChartData_income() {
        int len = 3;
        String[] first = {"工资", "兼职", "礼金"};
        double[] price = {400f, 200f, 100f};

        List<PieEntry> mPie = new ArrayList<>();

        //  赋值饼状图
        for (int i = 0; i < len; i++) {
            mPie.add(new PieEntry((float) (price[i]/700),first[i]));
        }

        return mPie;
    }

    // 获取支出数据
    private List<PieEntry> getPieChartData_outcome() {
        int len = 3;
        String[] first = {"餐饮", "交通", "宠物"};
        double[] price = {25f, 5f, 100f};

        List<PieEntry> mPie = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            mPie.add(new PieEntry((float) (price[i]/130),first[i]));
        }

        return mPie;
    }

    // 饼状图着色
    private List<Integer> getcolor() {
        int len = 3;
        String[] c = {"#F0C9DC","#FCDED9","#E4CFB4"};

        List<Integer> colors = new ArrayList<>();

        for(int i = 0;i < len;i++) {
            colors.add(Color.parseColor(c[i]));
        }
        return  colors;
    }

    void initBarchart() {
        //填充数据
        List<BarEntry> barEntry1 = get_Barchart_income() ;  //成员的收入
        List<BarEntry> barEntry2 = get_Barchart_outcome() ;  //成员的支出

        BarChartManager barChartManager = new BarChartManager(barChart);
        barChartManager.showBarChart(barEntry1,barEntry2);
        //刷新
        barChart.invalidate();
    }

    private List<BarEntry> get_Barchart_income() {
        List<BarEntry> barEntry1 = new ArrayList<>();
        BarEntry x1 = new BarEntry(0f , 10000f) ;
        barEntry1.add(x1) ;
        BarEntry x2 = new BarEntry(3f , 11000f) ;
        barEntry1.add(x2) ;
        BarEntry x3 = new BarEntry(6f , 9000f) ;
        barEntry1.add(x3) ;
        //BarEntry x4 = new BarEntry(9f , 12000f) ;
        //barEntry1.add(x4) ;
        return barEntry1;
    }

    private List<BarEntry> get_Barchart_outcome() {
        List<BarEntry> barEntry2 = new ArrayList<>();
        BarEntry y1 = new BarEntry(1f , 2000f) ;
        barEntry2.add(y1) ;
        BarEntry y2 = new BarEntry(4f , 15000f) ;
        barEntry2.add(y2) ;
        BarEntry y3 = new BarEntry(7f , 10500f) ;
        barEntry2.add(y3) ;
        //BarEntry y4 = new BarEntry(10f , 17000f) ;
        //barEntry2.add(y4) ;
        return barEntry2;
    }


    private void initRV(int i) {
        RvList rvList = new RvList(myList);
        if(i == 1)    myList = rvList.choice(0);
        else if (i == 2)    myList = rvList.choice(1);
        else    myList = rvList.choice(2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        incomeAdapter adapter = new incomeAdapter(myList);
        recyclerView.setAdapter(adapter);
    }

}