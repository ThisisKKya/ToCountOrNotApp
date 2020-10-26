package com.example.toaccountornot.ui.chart;

import android.graphics.Color;
import android.util.Log;

import com.example.toaccountornot.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

// 柱状图的管理类
public class BarChartManager{
    public BarChart barChart;

    public BarChartManager (BarChart barChart) {
        this.barChart = barChart;
        initBarChart();
    }

    // 初始化
    private void initBarChart() {
        barChart.setPinchZoom(true);//设置按比例放缩柱状图

        // x轴
        XAxis xAxis=barChart.getXAxis();
        xAxis.setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);        //X轴所在位置   默认为上面
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setGranularity(1f);   //设置最小间隔，防止当放大时，出现重复标签。
        barChart.getXAxis().setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）

        //右侧是否显示Y轴数值
        barChart.getAxisRight().setDrawLabels(false);
        //是否显示最右侧竖线
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setDrawGridLines(false);  //是否绘制Y轴上的网格线（背景里面的横线）

        barChart.setScaleEnabled(false);// 是否可以缩放
        barChart.getDescription().setEnabled(false);//隐藏右下角英文
    }

    // 显示
    public void showBarChart(List<BarEntry> income,List<BarEntry> outcome) {
        //创建BarDataSet 对象 将数据传入对象中
        BarDataSet set1 = new BarDataSet(income , "收入") ;
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.rgb(67,205,128));    //柱体颜色

        BarDataSet set2 = new BarDataSet(outcome , "支出") ;
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(Color.rgb(205,51,51));

        //使用接口IBarDataSet
        List<IBarDataSet> list = new ArrayList<IBarDataSet>() ;
        //添加数据
        list.add(set1) ;
        list.add(set2) ;
        //绘制图表
        BarData data = new BarData(list) ;
        //柱体宽度 这里设为0.9f那么柱体之间的间距就为0.1f
        data.setBarWidth(0.9f);

        // x轴标签
        List<String> xAxisValue = new ArrayList<>();
        xAxisValue.add("爸爸");
        xAxisValue.add("我");
        xAxisValue.add("妈妈");
        //xAxisValue.add("didi");
        XAxis xAxis=barChart.getXAxis();
        xAxis.setLabelCount(xAxisValue.size());     //设置x轴显示的标签个数
        xAxis.setCenterAxisLabels(true);//设置标签居中
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValue));

        //设置图表
        barChart.setData(data);
        //使x轴完全适合所有酒吧
        barChart.setFitBars(true);

        // x轴标签
        float groupSpace = 0.04f;
        float barSpace = 0.03f;
        float barWidth = 0.45f;
        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * xAxisValue.size() + 0);
        barChart.groupBars(0, groupSpace, barSpace);
    }

}
