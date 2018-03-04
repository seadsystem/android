package com.seads.seadsv2.graph;

/**
 * Created by root on 3/2/18.
 */

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.seads.seadsv2.R;
import com.seads.seadsv2.http.WebInterface;
import com.seads.seadsv2.http.WebInterfacer;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


/*
    Dymanic real-time updated chart fragment used in conjunction with DynamicChartActivity and
    DemoBase.
 */

public class TabFragment6 extends Fragment implements WebInterface {

    PieChart mChart;
    private boolean killMe = false;
    private boolean running = false;
    private final long DAY_INT = 86400000;
    private Spinner mSpinner;
    private WebInterfacer webInterfacer;
    private int indexCount;
    private TextView textView_Peak;
    private TextView textView_Average;
    private Float power_data [];
    private int counter = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_overview_main_menu, container, false);
        mChart = (PieChart) v.findViewById(R.id.overview_chart);
        textView_Average = v.findViewById(R.id.graph_panel_avg);
        textView_Peak = v.findViewById(R.id.graph_panel_peak);
        Log.d("in overview", "lmfao");
        power_data = new Float[3];

        // enable description text
        mChart.getDescription().setEnabled(false);
        mChart.setUsePercentValues(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);
        webInterfacer = new WebInterfacer(this);
        queryChartData();

        //test.getJSONObject();

        return v;
    }

    public void queryChartData(){
        long current_time = System.currentTimeMillis();
        webInterfacer.getJSONObject(
                (current_time-current_time%DAY_INT-DAY_INT)/1000,
                (current_time-current_time%DAY_INT)/1000,
                "energy",
                60*60*12,
                "Panel1",
                "P"
        );
        webInterfacer.getJSONObject(
                (current_time-current_time%DAY_INT-DAY_INT)/1000,
                (current_time-current_time%DAY_INT)/1000,
                "energy",
                60*60*12,
                "Panel2",
                "P"
        );
        webInterfacer.getJSONObject(
                (current_time-current_time%DAY_INT-DAY_INT)/1000,
                (current_time-current_time%DAY_INT)/1000,
                "energy",
                60*60*12,
                "Panel3",
                "P"
        );
    }

    public void setChartData(){
        if(counter<3){
            return;
        }
        Log.d("Sucessfully got shit", "yay");
        ArrayList<PieEntry> entries = new ArrayList<>();

        for(int i = 0; i<power_data.length; i++){
            entries.add(new PieEntry(power_data[i], "Panel "+ i));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Power Data");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        PieData data = new PieData(dataSet);
        mChart.setData(data);
        mChart.notifyDataSetChanged();

    }



    @Override
    public void onJSONRetrieved(JSONObject result){
        try{
            JSONArray data= result.getJSONArray("data");
            JSONObject index0 = data.getJSONObject(0);
            Log.d("DashboardActivity","index0 time: "+index0.getString("time"));
            Log.d("DashboardActivity","index0 energy: "+index0.getString("energy"));
            power_data[counter++] = Float.parseFloat(index0.getString("energy"));
            setChartData();
            /*
            PieData lineData = mChart.getData();
            if (lineData == null){
                lineData = new PieData();
            }

            IPieDataSet iLineDataSet = lineData.getDataSetByIndex(0);
            if(iLineDataSet == null){
                iLineDataSet = createSet();
                lineData.addDataSet(iLineDataSet);
            }

            lineData.addEntry(new Entry(counter++, Float.parseFloat(index0.getString("energy"))), 0);

            lineData.notifyDataChanged();
            mChart.notifyDataSetChanged();
            */

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static String truncate(String value){
        return new BigDecimal(value)
                .setScale(2, RoundingMode.DOWN)
                .stripTrailingZeros()
                .toString();
    }

    private Double[] parseData(String data){

        Double values[] = new Double[StringUtils.countMatches(data, "time")];
        String tmp[] = data.split("\\[");
        tmp = tmp[1].split("\\]");
        tmp = tmp[0].split(",");
        int i = 0;
        for (String item : tmp){
            item = item.replaceAll("\\{", "");
            item = item.replaceAll("\\}", "");
            item = item.replaceAll("\"", "");
            double energy = Double.parseDouble(item.split(",")[1].split(":")[1]);
            values[i++] = energy;
        }
        return values;

    }

    private PieDataSet createSet() {
        PieDataSet set = new PieDataSet(null, "Energy Usage");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.RED);
        //set.setCircleColor(Color.BLACK);
        //set.setCircleRadius(2f);
        //set.setFillAlpha(100);
        //set.setFillColor(ColorTemplate.getHoloBlue());
        //set.setValueTextColor(Color.BLACK);
        //set.setValueTextSize(1f);
        set.setDrawValues(false);
        //set.setDrawValues(true);

        return set;
    }



}