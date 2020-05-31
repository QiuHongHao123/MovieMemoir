package com.example.mymovie.Fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymovie.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportsFragment extends Fragment {
    private TextView textView;
    private DatePicker startdate;
    private DatePicker enddate;
    private Button b_confire;
    private int start_year;
    private int end_year;
    private int start_month;
    private int end_month;
    private int start_day;
    private int end_day;
    private int[] colours=new int[]{Color.BLUE,Color.RED,Color.YELLOW,Color.GRAY,Color.DKGRAY,
                            Color.BLACK,Color.LTGRAY,Color.CYAN,Color.CYAN,Color.WHITE, Color.RED,Color.LTGRAY};
    private List<String> pie_titles;
    private List<String> bar_titles;
    private int[] bar_date={0,0,1,3,0,0,0,0,0,0,0,0};
    private double[] pie_data={0.5,0.35,0.15};
    public ReportsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.reports_fragement, container, false);
        startdate=view.findViewById(R.id.startdate);
        enddate=view.findViewById(R.id.enddate);
        b_confire=view.findViewById(R.id.b_date_confirm);
        pie_titles=new ArrayList<String>();
        bar_titles=new ArrayList<String>();
        LinearLayout pie_view=view.findViewById(R.id.pie_chart);
        LinearLayout bar_view=view.findViewById(R.id.line_chart);
        bar_titles.add("Jan");
        bar_titles.add("Feb");
        bar_titles.add("Mar");
        bar_titles.add("Apr");
        bar_titles.add("May");
        bar_titles.add("Jun");
        bar_titles.add("Jul");
        bar_titles.add("Aug");
        bar_titles.add("Sept");
        bar_titles.add("Oct");
        bar_titles.add("Nov");
        bar_titles.add("Dec");

        pie_titles.add("location 1000");
        pie_titles.add("location 2164");
        pie_titles.add("location 3000");

        textView=view.findViewById(R.id.tvreport);
        textView.setText("This is reports");

        pie_view.removeAllViews();
        bar_view.removeAllViews();



        DefaultRenderer renderer = buildCategoryRenderer(colours); // 把分布的颜色传给渲染器
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(40);
        renderer.setInScroll(true);
        renderer.setZoomButtonsVisible(false);
        View pie_draw = ChartFactory.getPieChartView(getContext(), buildCategoryDataset("Location distribute", pie_data), renderer);
        pie_draw.setBackgroundColor(Color.WHITE);
        pie_view.addView(pie_draw);

        XYMultipleSeriesRenderer xyMultipleSeriesRenderer=buildBarRenderer(colours);
        XYMultipleSeriesDataset mybarset=buildBarDate(bar_date);
        View bar_draw = ChartFactory.getBarChartView(getContext(), mybarset, xyMultipleSeriesRenderer, BarChart.Type.DEFAULT);
        bar_view.addView(bar_draw);
        startdate.init(2000, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                start_year=year;
                start_month=monthOfYear+1;
                start_day=dayOfMonth;
            }
        });
        enddate.init(2000, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                end_year=year;
                end_month=monthOfYear+1;
                end_day=dayOfMonth;
            }
        });
        b_confire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start_dateval=start_year+"-"+start_month+"-"+start_day;
                String end_dayval=end_year+"-"+end_month+"-"+end_day;

            }
        });
        return view;
    }
    /**
     * 饼图(单数据) : 创建只包含一个饼图的图表数据集
     *
     * @param title 饼图的标题
     * @param values 饼图的值组成的数组
     * @return 返回饼图
     */
    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        CategorySeries series = new CategorySeries(title);	/* 创建单个饼状图数据, 传入饼状图标题 */
        for (int i=0;i<values.length;i++) {
            series.add(pie_titles.get(i), values[i]); 				/* 键值对, 键是饼图元素的标题, 值是大小 */
        }

        return series;
    }

    protected DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();		/* 默认的饼图图表渲染器 */
        renderer.setLabelsTextSize(15);							/* 设置标签文字大小 */
        renderer.setLegendTextSize(15);							/* 设置说明文字大小 */
        renderer.setMargins(new int[] { 20, 30, 15, 0 });		/* 设置边距 */
        for (int i=0 ;i<pie_data.length ;i++) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();	/* 饼状图中单个数据的颜色渲染器 */
            r.setColor(colors[i]);
            renderer.addSeriesRenderer(r);						/* 将单个元素渲染器设置到饼图图表渲染器中 */
        }
        return renderer;
    }

    /**
     * 柱状图(渲染器) : 创建柱状图渲染器
     *
     * @param colors 柱状图的颜色数组
     * @return 返回柱状图渲染器colors
     */
    protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();	/* 创建图表渲染器 */
        renderer.setAxisTitleTextSize(16);									/* 设置坐标轴标题字体大小 */
        renderer.setChartTitleTextSize(20);									/* 设置图表标题字体大小 */
        renderer.setLabelsTextSize(15);										/* 设置标签字体大小 */
        renderer.setLegendTextSize(15);										/* 设置说明文字字体大小 */
        int length = colors.length;											/* 获取图表中柱状图个数 */
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();				/* 单个柱状图渲染器 */
            r.setColor(colors[i]);											/* 为单个柱状图渲染器设置颜色 */
            renderer.addSeriesRenderer(r);									/* 将单个柱状图渲染器设置给图表渲染器 */
        }
        renderer.setXLabelsAlign(Paint.Align.LEFT);// 数据从左到右显示
        renderer.setYLabelsAlign(Paint.Align.LEFT);
        renderer.setPanEnabled(true, false);
        renderer.setZoomEnabled(true);
        renderer.setZoomButtonsVisible(true);// 显示放大缩小功能按钮
        renderer.setZoomRate(1.1f);
        renderer.setBarSpacing(0.5f);// 柱形图间隔

        return renderer;
    }
    /**
     * 构造数据
     * @return
     */
    protected XYMultipleSeriesDataset buildBarDate(int[] data) {
        // 构造数据
        XYMultipleSeriesDataset barDataset = new XYMultipleSeriesDataset();
        CategorySeries barSeries = new CategorySeries("Mounth distribute");
        for(int i=0;i<data.length;i++)
        {
            barSeries.add(bar_titles.get(i),data[i]);
            barDataset.addSeries(barSeries.toXYSeries());
        }

        return barDataset;
    }

}
