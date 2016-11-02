package android.sead_systems.seads.graph;

/**
 * Created by Bob on 11/1/16.
 */
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
        lineEntries.add(new Entry(0.5f, 1.3f));
        lineEntries.add(new Entry(1.5f, 3.3f));
        lineEntries.add(new Entry(2.5f, 5.3f));
        lineEntries.add(new Entry(3.5f, 8.3f));

        LineDataSet lineset = new LineDataSet(lineEntries, "Tesla Cannon Efficiency");
        lineset.setColor(Color.rgb(32, 23, 66));
        lineset.setValueTextColor(Color.rgb(66, 23, 66));

        LineData lineData = new LineData(lineset);
        lineChart.setData(lineData);


        return v;
    }
}