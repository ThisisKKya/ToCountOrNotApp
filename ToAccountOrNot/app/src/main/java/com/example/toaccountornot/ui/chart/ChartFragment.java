package com.example.toaccountornot.ui.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import com.example.toaccountornot.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {
    private ImageView income;
    private ImageView outcome;
    private PieChart pieChart;
    private TextView money;

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
        income = (ImageView) getActivity().findViewById(R.id.view_income);
        outcome = (ImageView) getActivity().findViewById(R.id.view_outcome);
        pieChart = (PieChart) getActivity().findViewById(R.id.pc);
        initPieChart_income();

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initPieChart_income();
            }
        });

        outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPieChart_outcome();
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
        /*
        PieDataSet pieDataSet = new PieDataSet(yVals,"");
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);

        // 更改饼状图的大小
        pieChart.setExtraOffsets(0f,32f,0f,32f);
        pieChart.setData(pieData);
        // 更新数据
        pieChart.postInvalidate();*/
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

}