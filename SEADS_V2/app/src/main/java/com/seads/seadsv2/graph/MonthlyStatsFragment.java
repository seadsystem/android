package com.seads.seadsv2.graph;

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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
    public HashMap<Integer, String> data_point_date_map;
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
        data_point_date_map = new HashMap<>();

        webInterfacer = new WebInterfacer(this);
        setUpQuery();

        //test.getJSONObject();

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
    public void fillXAxis(long start_time, long end_time, int granularity, int nPoints, int type){
        Log.d("Viz/Time", new Date(start_time).toString());
        Log.d("Viz/Time", new Date(start_time).toString().split(" ")[3]);
        data_point_date_map.clear();
        long current_time = start_time;
        switch (type){
            case 0:
                for(int i = 0;i<nPoints; i++){
                    String hour_min_sec= new Date(current_time).toString().split(" ")[3];
                    hour_min_sec = hour_min_sec.split(":")[0] + ":" +hour_min_sec.split(":")[1];
                    this.data_point_date_map.put(i, hour_min_sec);
                    current_time = current_time+(granularity*1000);
                }
                break;
            case 1:
                for(int i = 0;i<nPoints; i++){
                    String hour_min_sec= new Date(current_time).toString().split(" ")[0];
                    this.data_point_date_map.put(i, hour_min_sec);
                    current_time = current_time+(granularity*1000);
                }
                break;
            case 2:
                for(int i = 0;i<nPoints; i++){
                    String[] hour_min_sec= new Date(current_time).toString().split(" ");
                    String dates = hour_min_sec[1] + " " +hour_min_sec[2];
                    this.data_point_date_map.put(i, dates);
                    current_time = current_time+(granularity*1000);
                }
                break;
        }
    }

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
        long start_time = (current_time-current_time%DAY_INT-1*DAY_INT)/1000;
        long end_time = (current_time-current_time%DAY_INT)/1000;
        Log.d("Timing", "start_time="+start_time);
        Log.d("Timing", "end_time="+end_time);
        Log.d("Timing", "granularity="+end_time);

        webInterfacer.getJSONObject(
                start_time,
                end_time,
                "energy",
                (int)(end_time-start_time)/2,
                panel,
                "P"
        );
        indexCount = 1;
        fillXAxis(
                (current_time-current_time%DAY_INT-31*DAY_INT)/1000,
                (current_time-current_time%DAY_INT),
                60*60*3*2*2*2*31,
                indexCount,
                2
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

            BarData barData = new BarData();
            ArrayList<BarEntry> barEntryList = new ArrayList<>();
            barData.addEntry(new BarEntry(0, (float) CostCalculator.energyCost(total_energy)), 0);
            barData.addEntry(new BarEntry(1, (float) total_energy), 0);

            mBarChart.setData(barData);
            IBarDataSet iBarDataSet = barData.getDataSetByIndex(0);
            if(iBarDataSet == null){
                iBarDataSet = createSet(barEntryList);
                barData.addDataSet(iBarDataSet);
            }

            //textView_Peak.setText("Peak:\n"+truncate(""+peak)+"kW");
            //textView_Average.setText("Avg\n"+truncate(""+average)+"kW");
            mBarChart.notifyDataSetChanged();
            mBarChart.setVisibleXRangeMaximum(1500);
            mBarChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return data_point_date_map.get((int)value);
                }
            });
            mBarChart.moveViewToX(barData.getEntryCount());

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * Creates the dataset for filling the chart
     * @return LineDataSet for use in the chart
     */
    public BarDataSet createSet(ArrayList barValues) {
        BarDataSet set = new BarDataSet(barValues, "Energy Usage");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.RED);
        set.setHighLightColor(Color.rgb(0, 0, 0));
        set.setDrawValues(false);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_green);
        return set;
    }

}
