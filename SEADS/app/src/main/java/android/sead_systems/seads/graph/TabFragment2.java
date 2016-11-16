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
        barEntries.add(new BarEntry(2f, 3.5f));
        barEntries.add(new BarEntry(3f, 2.5f));
        barEntries.add(new BarEntry(4f, 2f));
        barEntries.add(new BarEntry(5f, 6f));
        barEntries.add(new BarEntry(6f, 4f));
        barEntries.add(new BarEntry(7f, 3.3f));
        barEntries.add(new BarEntry(8f, 3.5f));
        barEntries.add(new BarEntry(9f, 5f));
        barEntries.add(new BarEntry(10f, 6.6f));

        BarDataSet barDataSet =  new BarDataSet(barEntries, "Power Draw By Time");

        barDataSet.setColors(new int[] {android.R.color.holo_blue_dark, android.R.color.holo_purple, android.R.color.holo_red_light,
                                        android.R.color.holo_blue_bright, android.R.color.holo_orange_light, android.R.color.holo_blue_dark, android.R.color.holo_purple, android.R.color.holo_red_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light}, getContext());
        BarData data = new BarData( barDataSet);

        barChart.setData(data);

        barChart.animateXY(5000,5000);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setFitBars(true);
        //barChart.getLegend().setEnabled(false);

        return v;
    }
}