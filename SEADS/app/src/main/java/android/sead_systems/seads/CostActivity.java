package android.sead_systems.seads;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;


public class CostActivity extends AppCompatActivity {
    private static String pricePerKWHour = "0.0";
    private boolean firstRunForBottomBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        final TextView priceTextView = (TextView) findViewById(R.id.electricity_cost);
        priceTextView.setText(pricePerKWHour + "¢ per kWh");
        final TextView dateTextView = (TextView) findViewById(R.id.electricity_date);
        dateTextView.setText("Please choose a point");

        // Line Chart
        LineChart lineChart = (LineChart) findViewById(R.id.line_graph);

        ArrayList<String> times = new ArrayList<>();
        times.add("1:00 AM");
        times.add("4:00 AM");
        times.add("7:00 AM");
        times.add("11:00 AM");
        times.add("2:00 PM");
        times.add("5:00 PM");
        times.add("8:00 PM");
        times.add("11:00 PM");

        ArrayList<Float> cost = new ArrayList<>();
        cost.add(17.76f);
        cost.add(17.79f);
        cost.add(17.66f);
        cost.add(12.4f);
        cost.add(17.74f);
        cost.add(18.11f);
        cost.add(18.49f);
        cost.add(18.88f);

        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        for(int i = 0; i < times.size() && i < cost.size(); i++) {
            lineEntries.add(new Entry(i, cost.get(i)));
        }

        LineDataSet lineSet = new LineDataSet(lineEntries, "Device Power Draw");
        lineSet.setColor(Color.rgb(244, 66, 191));
        lineSet.setValueTextColor(Color.rgb(66, 23, 66));

        LineData lineData = new LineData(lineSet);
        lineSet.setDrawFilled(true);


        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(times));
        xAxis.setGranularity(1f);

        lineChart.animateY(2000);
        lineChart.getLegend().setEnabled(false);
        lineChart.setPinchZoom(false);
        Description desc = new Description();
        desc.setText("");
        lineChart.setDescription(desc);

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
               /* System.out.println(h.getX());
                System.out.println(h.getY());
                priceTextView.setText(h.getY() + "¢ per kWh");
                dateTextView.setText(getMonthString(h.getX()) + " 2016");*/
            }

            @Override
            public void onNothingSelected() {

            }
        });

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabAtPosition(1);
        if (bottomBar != null) {
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    if(!firstRunForBottomBar){
                        if (tabId == R.id.tab_left) {
                            Intent intent = new Intent(getApplicationContext(),
                                    DashboardActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        } else if (tabId == R.id.tab_center) {
                            // Current Activity -> do nothing
                        } else if (tabId == R.id.tab_right) {
                            Intent intent = new Intent(getApplicationContext(),
                                    SettingsActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    } else {
                        firstRunForBottomBar = false;
                    }
                }
            });
        }

        if (bottomBar != null) {
            bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
                @Override
                public void onTabReSelected(@IdRes int tabId) {
                    if (tabId == R.id.tab_left) {
                        Intent intent = new Intent(getApplicationContext(),
                                DashboardActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    } else if (tabId == R.id.tab_center) {
                        // Current Activity -> do nothing
                    } else if (tabId == R.id.tab_right) {
                        Intent intent = new Intent(getApplicationContext(),
                                SettingsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            });
        }

    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {
        private ArrayList<String> mValues;

        public MyXAxisValueFormatter (ArrayList<String> values) {
            mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis){
            return mValues.get((int)value);
        }

        @Override
        public int getDecimalDigits() { return 0; }
    }
}
