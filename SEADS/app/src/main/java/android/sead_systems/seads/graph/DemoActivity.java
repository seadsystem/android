package android.sead_systems.seads.graph;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.graphics.Color;
import android.sead_systems.seads.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class DemoActivity extends Activity {

    BarChart barChart;
    PieChart pieChart;
    LineChart lineChart;
    public static  final int[] MY_COLORS = {
            Color.rgb(84,124,101), Color.rgb(64,64,64), Color.rgb(153,19,0),
            Color.rgb(38,40,53), Color.rgb(215,60,55)
    };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        //dummy bar chart
        barChart = (BarChart) findViewById(R.id.bargraph);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        //ideally use a loop to cycle through data and add points
        //manually adding dummy data for this example
        barEntries.add(new BarEntry(1f, 1f));
        barEntries.add(new BarEntry(2f, 1f));
        barEntries.add(new BarEntry(3f, 2f));
        barEntries.add(new BarEntry(4f, 3f));
        barEntries.add(new BarEntry(5f, 4f));
        barEntries.add(new BarEntry(6f, 5f));

        BarDataSet barDataSet =  new BarDataSet(barEntries, "Fridge Carbon Footprint");
        BarData data = new BarData( barDataSet);
        barChart.setData(data);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setFitBars(true);

        //dummy pie chart
        pieChart = (PieChart) findViewById(R.id.piegraph);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(15.0f, "Car"));
        entries.add(new PieEntry(12.0f, "Bike"));
        entries.add(new PieEntry(15.8f, "Fridge"));
        entries.add(new PieEntry(65.0f, "Tesla Cannon"));
        entries.add(new PieEntry(11.2f, "TV"));

        PieDataSet pieset = new PieDataSet(entries, "Usage Breakdown");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : MY_COLORS)
            colors.add(c);
        pieset.setColors(colors);
        PieData piedata = new PieData(pieset);
        pieChart.setData(piedata);

        //dummy line chart
        lineChart = (LineChart) findViewById(R.id.linegraph);
        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        lineEntries.add(new Entry(0.5f, 1.3f));
        lineEntries.add(new Entry(1.5f, 3.3f));
        lineEntries.add(new Entry(2.5f, 5.3f));
        lineEntries.add(new Entry(3.5f, 8.3f));

        LineDataSet lineset = new LineDataSet(lineEntries, "Tesla Cannon Efficiency");
        lineset.setColor(Color.rgb(32, 23, 66));
        lineset.setValueTextColor(Color.rgb(66, 23, 66));

        LineData lineData = new LineData(lineset);
        lineChart.setData(lineData);
    }

}
