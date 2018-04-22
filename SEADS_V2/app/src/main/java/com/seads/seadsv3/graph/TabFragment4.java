package com.seads.seadsv3.graph;

import android.os.Bundle;
import com.seads.seadsv3.R;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;

import java.util.ArrayList;

/*
    Compary today's power usage with historical averages via radarchart
 */
@Deprecated
public class TabFragment4 extends Fragment {

    RadarChart radarChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_fragment_4, container, false);
        radarChart = (RadarChart) v.findViewById(R.id.radargraph);

        ArrayList<RadarEntry> radarEntries = new ArrayList<>();
        radarEntries.add(new RadarEntry(2.1f, 0));
        radarEntries.add(new RadarEntry(5.5f, 1));
        radarEntries.add(new RadarEntry(4.9f, 2));
        radarEntries.add(new RadarEntry(2.7f, 3));
        radarEntries.add(new RadarEntry(5.1f, 4));
        radarEntries.add(new RadarEntry(4.2f, 5));

        RadarDataSet radarDataSet =  new RadarDataSet(radarEntries, "Today");
        radarDataSet.setLabel("Device Power");
        radarDataSet.setColors(new int[] {android.R.color.holo_blue_bright}, getContext());
        radarDataSet.setDrawFilled(true);

        ArrayList<RadarEntry> radarEntries2 = new ArrayList<>();
        radarEntries2.add(new RadarEntry(3.1f, 0));
        radarEntries2.add(new RadarEntry(4.5f, 1));
        radarEntries2.add(new RadarEntry(5.9f, 2));
        radarEntries2.add(new RadarEntry(3.7f, 3));
        radarEntries2.add(new RadarEntry(4.1f, 4));
        radarEntries2.add(new RadarEntry(3.3f, 5));

        RadarDataSet radarDataSet2 =  new RadarDataSet(radarEntries2, "Yesterday");
        radarDataSet2.setLabel("Device Power");

        radarDataSet2.setColors(new int[] {android.R.color.holo_orange_light}, getContext());
        radarDataSet2.setDrawFilled(true);

        RadarData data = new RadarData(radarDataSet, radarDataSet2);

        radarChart.setData(data);

        radarChart.animateXY(5000, 5000);

        radarChart.setTouchEnabled(true);
        radarChart.getLegend().setEnabled(false);

        return v;
    }
}


