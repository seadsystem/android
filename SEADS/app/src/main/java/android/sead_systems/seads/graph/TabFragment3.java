package android.sead_systems.seads.graph;


import android.graphics.Color;
import android.os.Bundle;
import android.sead_systems.seads.R;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/*
    Displays a room/device's power usage over time in detail
 */
public class TabFragment3 extends Fragment {

    LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_fragment_3, container, false);
        lineChart = (LineChart) v.findViewById(R.id.linegraph);

        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        //ideally use a loop to cycle through data and add points
        //manually adding dummy data
        lineEntries.add(new Entry(1f, 21.3f));
        lineEntries.add(new Entry(2f, 23.3f));
        lineEntries.add(new Entry(3f, 25.3f));
        lineEntries.add(new Entry(4f, 28.3f));
        lineEntries.add(new Entry(5f, 28.2f));
        lineEntries.add(new Entry(6f, 27.3f));
        lineEntries.add(new Entry(7f, 28.3f));
        lineEntries.add(new Entry(8f, 25.3f));
        lineEntries.add(new Entry(9f, 24.3f));
        lineEntries.add(new Entry(10f, 24.7f));
        lineEntries.add(new Entry(11f, 25.3f));
        lineEntries.add(new Entry(12f, 19.9f));
        lineEntries.add(new Entry(13f, 20.3f));
        lineEntries.add(new Entry(14f, 21.3f));
        lineEntries.add(new Entry(15f, 23.1f));
        lineEntries.add(new Entry(16f, 23.3f));
        lineEntries.add(new Entry(17f, 25.0f));
        lineEntries.add(new Entry(18f, 24.2f));
        lineEntries.add(new Entry(19f, 24.3f));
        lineEntries.add(new Entry(20f, 21.5f));

        LineDataSet lineset = new LineDataSet(lineEntries, "Device Power Draw");
        lineset.setColor(Color.rgb(244, 66, 191));
        lineset.setValueTextColor(Color.rgb(66, 23, 66));

        lineset.setDrawFilled(true);
        lineset.setFillColor(Color.rgb(244, 66, 149));
        LineData lineData = new LineData(lineset);



        lineChart.setData(lineData);
        lineChart.animateY(4000);
        lineChart.getLegend().setEnabled(false);


        return v;
    }
}