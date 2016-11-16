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

import java.util.ArrayList;

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
        lineEntries.add(new Entry(5f, 21.3f));
        lineEntries.add(new Entry(6f, 23.3f));
        lineEntries.add(new Entry(7f, 25.3f));
        lineEntries.add(new Entry(8f, 28.3f));
        lineEntries.add(new Entry(9f, 21.3f));
        lineEntries.add(new Entry(10f, 23.3f));
        lineEntries.add(new Entry(11f, 25.3f));
        lineEntries.add(new Entry(12f, 18.3f));

        LineDataSet lineset = new LineDataSet(lineEntries, "Device Power Draw");
        lineset.setColor(Color.rgb(244, 66, 191));
        lineset.setValueTextColor(Color.rgb(66, 23, 66));

        LineData lineData = new LineData(lineset);
        lineset.setDrawFilled(true);


        lineChart.setData(lineData);
        lineChart.animateY(4000);
        lineChart.getLegend().setEnabled(false);


        return v;
    }
}