package com.seads.seadsv2.graph;


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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.seads.seadsv2.R;
import com.seads.seadsv2.http.WebInterface;
import com.seads.seadsv2.http.WebInterfacer;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;


/*
    Dymanic real-time updated chart fragment used in conjunction with DynamicChartActivity and
    DemoBase.
 */

public class TabFragment5 extends Fragment implements WebInterface {

    LineChart mChart;
    private boolean killMe = false;
    private boolean running = false;
    private final long DAY_INT = 86400000;
    private Spinner mSpinner;
    private WebInterfacer webInterfacer;
    private int indexCount;
    private TextView textView_Peak;
    private TextView textView_Average;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_5, container, false);
        mChart = (LineChart) v.findViewById(R.id.chart1);
        textView_Average = v.findViewById(R.id.graph_panel_avg);
        textView_Peak = v.findViewById(R.id.graph_panel_peak);

        // enable description text
        mChart.getDescription().setEnabled(false);
        setUpSpinner(v);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);

        LineData data = new LineData();
        data.setValueTextColor(Color.BLACK);

        // add empty data
        mChart.setData(data);
        YAxis rightAxis = mChart.getAxisRight();
        XAxis xAxis = mChart.getXAxis();
        //xAxis.
        rightAxis.setEnabled(false);

        //set button

        webInterfacer = new WebInterfacer(this);

        //test.getJSONObject();

        return v;
    }

    public void setUpSpinner(View v){
        mSpinner = (Spinner) v.findViewById(R.id.select_time_spinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
                getContext(),
                R.array.time_selection,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long current_time = System.currentTimeMillis();
                switch (position){
                    case 0:
                        webInterfacer.getJSONObject(
                                (current_time-current_time%DAY_INT-DAY_INT)/1000,
                                (current_time-current_time%DAY_INT)/1000,
                                "energy",
                                60/**/,
                                "Panel3",
                                "P"
                                );
                        indexCount = 1439;

                        break;
                    case 1:
                        Log.d("Selected:", parent.getItemAtPosition(position).toString());
                        webInterfacer.getJSONObject(
                                (current_time-current_time%DAY_INT-7*DAY_INT)/1000,
                                (current_time-current_time%DAY_INT)/1000,
                                "energy",
                                60*60*3,
                                "Panel3",
                                "P"
                        );
                        indexCount = 48;

                        break;
                    case 2:
                        Log.d("Selected:", parent.getItemAtPosition(position).toString());
                        webInterfacer.getJSONObject(
                                (current_time-current_time%DAY_INT-31*DAY_INT)/1000,
                                (current_time-current_time%DAY_INT)/1000,
                                "energy",
                                60*60*3*2*2*2,
                                "Panel3",
                                "P"
                        );
                        indexCount = 28;

                        break;
                    case 3:
                        Log.d("Selected:", parent.getItemAtPosition(position).toString());
                        webInterfacer.getJSONObject(
                                (current_time-current_time%DAY_INT-365*DAY_INT)/1000,
                                (current_time-current_time%DAY_INT)/1000,
                                "energy",
                                60*60*3*2*2*2*7,
                                "Panel3",
                                "P"
                        );
                        indexCount = 50;

                        break;
                    case 4:
                        Log.d("Selected:", parent.getItemAtPosition(position).toString());
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinner.setAdapter(adapter);

    }


    @Override
    public void onJSONRetrieved(JSONObject result){
        try{
            JSONArray data= result.getJSONArray("data");
            JSONObject index0 = data.getJSONObject(0);
            Log.d("DashboardActivity","index0 time: "+index0.getString("time"));
            Log.d("DashboardActivity","index0 energy: "+index0.getString("energy"));
            Float energy_values[] = new Float[indexCount];
            LineData lineData = mChart.getData();
            lineData.removeDataSet(0);
            ILineDataSet iLineDataSet = lineData.getDataSetByIndex(0);
            if(iLineDataSet == null){
                iLineDataSet = createSet();
                lineData.addDataSet(iLineDataSet);
            }
            double average = 0;
            double peak = 0;

            for(int i = 0; i<energy_values.length; i++) {
                energy_values[i] = Float.parseFloat(data.getJSONObject(i).getString("energy"));
                lineData.addEntry(new Entry(i, energy_values[i]), 0);
                if (energy_values[i] > peak)
                    peak = energy_values[i];
                average += energy_values[i];
            }
            average = average/indexCount;
            textView_Peak.setText("Peak:\n"+truncate(""+peak)+"kW");
            textView_Average.setText("Avg\n"+truncate(""+average)+"kW");
            lineData.notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.setVisibleXRangeMaximum(2000);
            mChart.moveViewToX(lineData.getEntryCount());

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

    private void addEntry() {
        LineData data = mChart.getData();
        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(120);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getEntryCount());
        }
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Energy Usage");
        set.setAxisDependency(AxisDependency.LEFT);
        set.setColor(Color.RED);
        set.setDrawCircles(false);
        //set.setCircleColor(Color.BLACK);
        set.setLineWidth(0.5f);
        //set.setCircleRadius(2f);
        //set.setFillAlpha(100);
        //set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(0, 0, 0));
        //set.setValueTextColor(Color.BLACK);
        //set.setValueTextSize(1f);
        set.setDrawValues(false);
        //set.setDrawValues(true);
        set.setDrawFilled(true);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_green);
        set.setFillDrawable(drawable);
        return set;
    }

    private Thread thread;

    private void feedMultiple() {

        if (thread != null)
            thread.interrupt();

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                addEntry();
            }
        };

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                //live feed continues will it is not stopped
                while(!killMe) {
                    // Don't generate garbage runnables inside the loop.
                    getActivity().runOnUiThread(runnable);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return;
            }
        });

        thread.start();
    }

    private void stopUIThread(){
        //thread.stop(); //do not use. kills UIthread and activity crash.
    }

}