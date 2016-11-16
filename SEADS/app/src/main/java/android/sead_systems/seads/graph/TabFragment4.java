package android.sead_systems.seads.graph;


import android.graphics.Color;
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
        radarEntries.add(new RadarEntry(2.1f, "Lights"));
        radarEntries.add(new RadarEntry(5.5f, "Fridge"));
        radarEntries.add(new RadarEntry(4.9f, "AC"));
        radarEntries.add(new RadarEntry(2.7f, "Microwave"));
        radarEntries.add(new RadarEntry(5.1f, "Computer"));
        radarEntries.add(new RadarEntry(4.2f, "Dishwasher"));

        RadarDataSet radarDataSet =  new RadarDataSet(radarEntries, "Today");
        radarDataSet.setColor(Color.RED);
        radarDataSet.setDrawFilled(true);
        radarDataSet.setLabel("Device Power");

        radarDataSet.setColors(new int[] {android.R.color.holo_blue_bright, android.R.color.holo_orange_light, android.R.color.holo_blue_dark}, getContext());

        RadarData data = new RadarData(radarDataSet);

        radarChart.setData(data);

        radarChart.animateXY(5000, 5000);

        radarChart.setTouchEnabled(true);
        radarChart.getLegend().setEnabled(false);

        return v;
    }
}


