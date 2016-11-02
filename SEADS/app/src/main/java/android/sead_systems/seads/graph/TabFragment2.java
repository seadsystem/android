package android.sead_systems.seads.graph;

/**
 * Created by Bob on 11/1/16.
 */

import android.os.Bundle;
import android.sead_systems.seads.R;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class TabFragment2 extends Fragment {

    BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_2, container, false);

        barChart = (BarChart) v.findViewById(R.id.bargraph);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
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
        //barChart.setDescription("Last Week's Energy Use");

        return v;
    }
}