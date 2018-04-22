package com.seads.seadsv3.graph;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.seads.seadsv2.R;
import com.seads.seadsv2.http.WebInterface;
import com.seads.seadsv2.http.WebInterfacer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Home on 4/17/18.
 */

public class MonthlyStatsFragment extends Fragment implements WebInterface {

    private BarChart mBarChart;
    private final long DAY_INT = 86400000;
    private WebInterfacer webInterfacer;
    private int indexCount;
    private String panel;
    private int recv=0;
    private ArrayList<BarEntry> barEntryList;
    public HashMap<Integer, String> data_point_date_map;
    private TextView daily_cost;
    private TextView yesterday_cost;
    private TextView last_week_cost;
    private ProgressBar progressBar;
    /**
     * 1523923200
     * 86400
     * 43200
     * 1523059200
     */

    /**
     * Populate the layout with the chart and instantiate data aggregation
     * @param inflater Base layout
     * @param container Which view this belongs to
     * @param savedInstanceState Arguments passed to fragment
     * @return Populated view
     */

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.monthly_stats_fragment, container, false);
        mBarChart = (BarChart) v.findViewById(R.id.MonthlyStatsChart);
        panel = getArguments().getString("device");
        mBarChart.setMaxVisibleValueCount(60);
        mBarChart.setDrawGridBackground(false);
        barEntryList = new ArrayList<>();
        mBarChart.setDrawBarShadow(false);
        webInterfacer = new WebInterfacer(this);
        progressBar = (ProgressBar) v.findViewById(R.id.weekly_progress);



        //test.getJSONObject();
        //mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.setDrawBarShadow(true);
        mBarChart.getDescription().setEnabled(false);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);
        mBarChart.invalidate();

        setUpQuery();


        return v;
    }

    /**
     * Creates the x axis formatted in a more user-friendly readable manner
     * @param start_time Time of start in unix milliseconds
     * @param end_time Time of end in unix milliseconds
     * @param granularity space between points in seconds
     * @param nPoints How many points there are in this dataset
     * @param type Hour, Day, Date of month
     */


    /**
     * Sets up drop down menu for selecting the data set to be analyzed
     */
    public void setUpQuery(){
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
                getContext(),
                R.array.time_selection,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        long current_time = System.currentTimeMillis();
        long start_time = (current_time-current_time%DAY_INT-21*DAY_INT)/1000;
        long end_time = (current_time-current_time%DAY_INT-14)/1000;
        Log.d("Timing", "start_time="+start_time);
        Log.d("Timing", "end_time="+end_time);
        Log.d("Timing", "granularity="+(end_time-start_time)/2);


        webInterfacer.getJSONObject(
                start_time,
                end_time,
                "energy",
                (int)(end_time-start_time)/2,
                panel,
                "P"
        );

        start_time = (current_time-current_time%DAY_INT-14*DAY_INT)/1000;
        end_time = (current_time-current_time%DAY_INT-7*DAY_INT)/1000;
        webInterfacer.getJSONObject(
                start_time,
                end_time,
                "energy",
                (int)(end_time-start_time)/2,
                panel,
                "P"
        );
        start_time = (current_time-current_time%DAY_INT-7*DAY_INT)/1000;
        end_time = (current_time-current_time%DAY_INT)/1000;
        webInterfacer.getJSONObject(
                start_time,
                end_time,
                "energy",
                (int)(end_time-start_time)/2,
                panel,
                "P"
        );

    }

    /**
     * What to do when we get a response from the server
     * In this case we populate the chart with the data we get back,
     * taking into account the different granularies and formattings
     * @param result the result of the HTTP response
     */
    @Override
    public void onJSONRetrieved(JSONObject result){
        try{
            JSONArray data= result.getJSONArray("data");
            JSONObject index0 = data.getJSONObject(0);
            Log.d("Got:", ""+index0);
            double total_energy = index0.getDouble("energy");

            //mBarChart.get

            //barEntryList.add(new BarEntry(recv++, (float) CostCalculator.energyCost(total_energy)));
            barEntryList.add(new BarEntry(recv++, (float) total_energy));


            IBarDataSet iBarDataSet = new BarDataSet(barEntryList, "Test");
            iBarDataSet.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(iBarDataSet);
            BarData barData = new BarData(dataSets);
            barData.setValueTextSize(14f);

            mBarChart.setData(barData);
            if(recv==3) {
                BarData tmp = mBarChart.getData();
                mBarChart.setDrawBarShadow(false);
                mBarChart.invalidate();
                progressBar.setVisibility(View.INVISIBLE);
            }
            //textView_Peak.setText("Peak:\n"+truncate(""+peak)+"kW");
            //textView_Average.setText("Avg\n"+truncate(""+average)+"kW");
            Log.d("JSONRET", "done");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
