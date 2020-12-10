package com.followyourcoins.app;

import android.graphics.Color;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class CubicLineChartActivity extends BaseActivity {

    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_coin_details);
        initToolbar("Coin Graph", 0);

        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setBackgroundColor(Color.TRANSPARENT);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);

        XAxis x = mChart.getXAxis();
        x.setTextColor(Color.RED);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setAxisLineColor(Color.BLUE);
        x.setDrawGridLines(false);
        x.setAxisLineWidth(2f);

        YAxis y = mChart.getAxisRight();
//        y.setTypeface();
        y.setLabelCount(6, false);
        y.setTextColor(Color.RED);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.BLUE);
        y.setAxisLineWidth(2f);

        mChart.getAxisLeft().setEnabled(false);

        // add data
        setData(45, 100);
        mChart.getLegend().setEnabled(false);
        mChart.animateX(1000);

        // dont forget to refresh the drawing
        mChart.invalidate();


        if (mChart.getData() != null) {
            mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
            mChart.invalidate();
        }
    }



    private void setData(int count, float range) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 20;// + (float)
            // ((mult *0.1) / 10);
            yVals.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(2f);
            set1.setCircleRadius(4f);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(ContextCompat.getColor(this, R.color.colorGraph));
            set1.setFillAlpha(50);
            set1.setDrawFilled(true);
            set1.setFillColor(ContextCompat.getColor(this, R.color.colorGraph));
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data = new LineData(set1);
//            data.setValueTypeface(mTfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);
        }
    }
}