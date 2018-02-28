package android.sead_systems.seads.graph;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.sead_systems.seads.R;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

/*
    Dymanic real-time updated chart fragment used in conjunction with DynamicChartActivity and
    DemoBase.
 */

public class TabFragment5 extends Fragment implements View.OnClickListener {

    LineChart mChart;
    private boolean killMe = false;
    private boolean running = false;
    private Spinner mSpinner;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_5, container, false);
        mChart = (LineChart) v.findViewById(R.id.chart1);

        // enable description text
        mChart.getDescription().setEnabled(false);
        setUpSpinner(v);
        mSpinner.setOnItemSelectedListener(new SpinnerHandler());

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

        return v;
    }

    public void setUpSpinner(View v){
        mSpinner = (Spinner) v.findViewById(R.id.select_time_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_selection, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.actionFeedMultiple:
                killMe = false;
                if (running == true){

                }
                else {
                    running = true;
                    Context context = this.getContext();
                    CharSequence text = "Connected to Live";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    feedMultiple();
                }
                break;

            case R.id.stopThread:
                killMe = true;
                running = false;
                Context context2 = this.getContext();
                CharSequence text2 = "Disconnected";
                int duration2 = Toast.LENGTH_SHORT;
                Toast toast2 = Toast.makeText(context2, text2, duration2);
                toast2.show();
                stopUIThread();
                break;
        }
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
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(AxisDependency.LEFT);
        set.setColor(Color.GREEN);
        set.setCircleColor(Color.BLACK);
        set.setLineWidth(0.5f);
        set.setCircleRadius(2f);
        set.setFillAlpha(100);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(0, 0, 0));
        set.setValueTextColor(Color.BLACK);
        set.setValueTextSize(1f);
        set.setDrawValues(true);
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