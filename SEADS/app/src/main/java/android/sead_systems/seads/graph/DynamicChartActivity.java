package android.sead_systems.seads.graph;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

/*
    This is the activity that supports the Real Time Chart Fragment (TabFragment5) to add
    entries to graph in real-time with a separate thread. Currently we are not connected to API
    so data is generated randomly.
 */

public class  DynamicChartActivity extends Activity {
    private LineChart mChart;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    private void addEntry() {
        LineData data = mChart.getData();

        if(data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
            data.notifyDataChanged();

            //Updates Linechart with new Dataset
            mChart.notifyDataSetChanged();
            mChart.setVisibleXRangeMaximum(200f);
            mChart.setVisibleYRangeMaximum(50f, YAxis.AxisDependency.LEFT);

            //Move View to Latest Entry
            mChart.moveViewToX(data.getEntryCount());
        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (thread != null) {
            thread.interrupt();
        }
    }

}
