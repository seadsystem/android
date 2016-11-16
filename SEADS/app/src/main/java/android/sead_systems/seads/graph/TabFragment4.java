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
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;

import java.util.ArrayList;

public class TabFragment4 extends Fragment {

    RadarChart radarChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_fragment_4, container, false);
        radarChart = (RadarChart) v.findViewById(R.id.radargraph);

        ArrayList<RadarEntry> radarEntries = new ArrayList<>();
        radarEntries.add(new RadarEntry(3.5f, 0));
        radarEntries.add(new RadarEntry(4.5f, 1));
        radarEntries.add(new RadarEntry(2.7f, 2));
        radarEntries.add(new RadarEntry(5.5f, 3));
        radarEntries.add(new RadarEntry(3.2f, 4));
        radarEntries.add(new RadarEntry(2.4f, 5));
        /*
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Lights");
        labels.add("AC");
        labels.add("Fridge");
        labels.add("Microwave");
        labels.add("Computer");
        labels.add("Dishwasher");
        */

        RadarDataSet radarDataSet =  new RadarDataSet(radarEntries, "Device Power Draw");

        radarDataSet.setColors(new int[] {android.R.color.holo_blue_dark, android.R.color.holo_purple, android.R.color.holo_red_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light, android.R.color.holo_blue_dark}, getContext());
        RadarData data = new RadarData(radarDataSet);

        radarChart.setData(data);

        radarChart.animateXY(5000, 5000);

        radarChart.setTouchEnabled(true);

        return v;
    }
}


