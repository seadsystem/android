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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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
        dateTextView.setText("August 2016");

        // Line Chart
        LineChart lineChart = (LineChart) findViewById(R.id.line_graph);

        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        lineEntries.add(new Entry(1, 17.76f));
        lineEntries.add(new Entry(2, 17.69f));
        lineEntries.add(new Entry(3, 17.66f));
        lineEntries.add(new Entry(4, 12.4f));
        lineEntries.add(new Entry(5, 17.74f));
        lineEntries.add(new Entry(6, 18.11f));
        lineEntries.add(new Entry(7, 18.49f));
        lineEntries.add(new Entry(8, 18.88f));

        LineDataSet lineset = new LineDataSet(lineEntries, "Device Power Draw");
        lineset.setColor(Color.rgb(244, 66, 191));
        lineset.setValueTextColor(Color.rgb(66, 23, 66));

        LineData lineData = new LineData(lineset);
        lineset.setDrawFilled(true);

        lineChart.setData(lineData);
        lineChart.animateY(2000);
        lineChart.getLegend().setEnabled(false);
        Description desc = new Description();
        desc.setText("");
        lineChart.setDescription(desc);
        // Line Chart

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                System.out.println(h.getX());
                System.out.println(h.getY());
                priceTextView.setText(h.getY() + "¢ per kWh");
                dateTextView.setText(getMonthString(h.getX()) + " 2016");
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
                            finish();
                        } else if (tabId == R.id.tab_center) {

                        } else if (tabId == R.id.tab_right) {

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
                        finish();
                    } else if (tabId == R.id.tab_center) {

                    } else if (tabId == R.id.tab_right) {

                    }
                }
            });
        }

    }

    private String getMonthString (float floatDate) {
        int date =  Math.round(floatDate);
        switch(date){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";
        }
    }

}
