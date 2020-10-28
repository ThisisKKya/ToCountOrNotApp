package com.example.toaccountornot.ui.chart;

import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.List;


// 显示圆环的管理类
public class PieChartManager  {
    public PieChart pieChart;

    public PieChartManager (PieChart pieChart) {
        this.pieChart = pieChart;
        initPieChart();
    }

    // 初始化
    private void initPieChart() {
        //  是否显示中间的洞
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleRadius(40f);//设置中间洞的大小
        // 半透明圈
        pieChart.setTransparentCircleRadius(30f);
        pieChart.setTransparentCircleColor(Color.WHITE); //设置半透明圆圈的颜色
        pieChart.setTransparentCircleAlpha(125); //设置半透明圆圈的透明度

        //饼状图中间可以添加文字
        pieChart.setDrawCenterText(false);
        pieChart.setCenterText("收入"); //设置中间文字
        pieChart.setCenterTextColor(Color.parseColor("#a1a1a1")); //中间问题的颜色
        pieChart.setCenterTextSizePixels(36);  //中间文字的大小px
        pieChart.setCenterTextRadiusPercent(1f);
        pieChart.setCenterTextTypeface(Typeface.DEFAULT); //中间文字的样式
        pieChart.setCenterTextOffset(0, 0); //中间文字的偏移量


        pieChart.setRotationAngle(0);// 初始旋转角度
        pieChart.setRotationEnabled(true);// 可以手动旋转
        pieChart.setUsePercentValues(true);//显示成百分比
        pieChart.getDescription().setEnabled(false); //取消右下角描述

        //是否显示每个部分的文字描述
        pieChart.setDrawEntryLabels(false);
        pieChart.setEntryLabelColor(Color.RED); //描述文字的颜色
        pieChart.setEntryLabelTextSize(14);//描述文字的大小
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD); //描述文字的样式

        //图相对于上下左右的偏移
        pieChart.setExtraOffsets(10, 0, 10, 2);
        //图标的背景色
        pieChart.setBackgroundColor(Color.TRANSPARENT);
//        设置pieChart图表转动阻力摩擦系数[0,1]
        pieChart.setDragDecelerationFrictionCoef(0.75f);

        //获取图例
        Legend legend = pieChart.getLegend();
//        legend.setOrientation(Legend.LegendOrientation.VERTICAL);  //设置图例水平显示

// 水平：居中
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); //顶部
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); //右对其
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);  //左下边显示
        legend.setXEntrySpace(10f);//x轴条目的间距
        legend.setYEntrySpace(7f); //y轴的间距
        legend.setYOffset(7f);  //图例的y偏移量
        legend.setXOffset(7f);  //图例x的偏移量
        legend.setFormToTextSpace(4f);
        legend.setTextColor(Color.parseColor("#a1a1a1")); //图例文字的颜色
        legend.setTextSize(13);  //图例文字的大小
        legend.setWordWrapEnabled(true);

    }

    // 显示实心圆
    public void showSolidPieChart(List<PieEntry> yvals, List<Integer> colors) {
        //数据集合
        PieDataSet dataset = new PieDataSet(yvals, "");
        //填充每个区域的颜色
        dataset.setColors(colors);
        //是否在图上显示数值
        dataset.setDrawValues(true);
//        文字的大小
        dataset.setValueTextSize(14);
//        文字的颜色
        dataset.setValueTextColor(Color.BLACK);
//        文字的样式
        dataset.setValueTypeface(Typeface.DEFAULT_BOLD);

//      当值位置为外边线时，表示线的前半段长度。
        dataset.setValueLinePart1Length(0.4f);
//      当值位置为外边线时，表示线的后半段长度。
        dataset.setValueLinePart2Length(0.4f);
//      当ValuePosits为OutsiDice时，指示偏移为切片大小的百分比
        dataset.setValueLinePart1OffsetPercentage(80f);
        // 当值位置为外边线时，表示线的颜色。
        dataset.setValueLineColor(Color.parseColor("#a1a1a1"));
//        设置Y值的位置是在圆内还是圆外
        dataset.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        设置Y轴描述线和填充区域的颜色一致
        dataset.setUsingSliceColorAsValueLineColor(false);
//        设置每条之前的间隙
        dataset.setSliceSpace(0);

        //设置饼状Item被选中时变化的距离
        dataset.setSelectionShift(5f);
        //填充数据
        PieData pieData = new PieData(dataset);
//        格式化显示的数据为%百分比
        pieData.setValueFormatter(new PercentFormatter());
//        显示试图
        pieChart.setData(pieData);

        // 设置动画
        pieChart.animateX(600);
    }

}
