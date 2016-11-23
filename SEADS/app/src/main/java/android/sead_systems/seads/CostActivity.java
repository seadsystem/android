package android.sead_systems.seads;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class CostActivity extends AppCompatActivity {
    private static String pricePerKWHour = "0.0";
    private boolean firstRunForBottomBar = true;
    private BarChart barChart;
    private BarDataSet barDataSet;
    private ArrayList<BarChartEntry> barChartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        final TextView priceTextView = (TextView) findViewById(R.id.electricity_cost);
        priceTextView.setText(pricePerKWHour + "¢ per kWh");
        final TextView dateTextView = (TextView) findViewById(R.id.electricity_date);
        dateTextView.setText("Current Cost: ");

        // BarChart should always have the current and next 7 times and costs
        barChart = (BarChart) findViewById(R.id.bar_graph);

        // Needs to be size 8
        ArrayList<String> times = new ArrayList<>();
        times.add("1 AM ");
        times.add("4 AM ");
        times.add("7 AM ");
        times.add("11 AM ");
        times.add("2 PM ");
        times.add("5 PM ");
        times.add("8 PM ");
        times.add("11 PM ");

        // Needs to be size  8
        ArrayList<Float> cost = new ArrayList<>();
        cost.add(11.76f);
        cost.add(12.79f);
        cost.add(13.66f);
        cost.add(16.4f);
        cost.add(17.74f);
        cost.add(15.11f);
        cost.add(13.49f);
        cost.add(11.88f);

        barChartList = new ArrayList<>();
        for (int i = 0; i < cost.size(); i++) {
            BarChartEntry bc = new BarChartEntry(times.get(i), cost.get(i), i);
            barChartList.add(bc);
        }

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i = 0; i < times.size() && i < cost.size(); i++) {
            barEntries.add(new BarEntry(i, cost.get(i)));
        }

        barDataSet = new BarDataSet(barEntries, "Power Draw By Time");
        setColorsOfBarChartList();

        BarData barData = new BarData(barDataSet);

        barChart.setData(barData);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(times));
        xAxis.setGranularity(1f);

        barChart.animateXY(2000,2000);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setFitBars(true);

        Description desc = new Description();
        desc.setText("");
        barChart.setDescription(desc);

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


    /**
     *
     */
    public void setColorsOfBarChartList() {

        // Sort By Cost
        Collections.sort(barChartList, new Comparator<BarChartEntry>() {
            @Override public int compare(BarChartEntry b1, BarChartEntry b2) {
                if (b1.getCost() > b2.getCost()) {
                    return 1;
                } else if (b1.getCost() < b2.getCost()) {
                    return -1;
                }
                return 0;
            }
        });

        int[] colorArray = new int[] {R.color.bar_chart_color_1, R.color.bar_chart_color_2,
                R.color.bar_chart_color_3, R.color.bar_chart_color_4, R.color.bar_chart_color_5,
                R.color.bar_chart_color_6, R.color.bar_chart_color_7, R.color.bar_chart_color_8};

        for (int i = 0; i < colorArray.length; i++) {
            barChartList.get(i).setColor(colorArray[i]);
        }

        // Sort By Position
        Collections.sort(barChartList, new Comparator<BarChartEntry>() {
            @Override public int compare(BarChartEntry b1, BarChartEntry b2) {
                if (b1.getPosition() > b2.getPosition()) {
                    return 1;
                } else if (b1.getPosition() < b2.getPosition()) {
                    return -1;
                }
                return 0;
            }
        });

        // Set colors for data set based off position
        for (int i = 0; i < colorArray.length; i++) {
            colorArray[i] = barChartList.get(i).getColor();
            System.out.println("Colors at: " + i + " " + colorArray[i]);
        }
        barDataSet.setColors(colorArray, getApplicationContext());
    }

    /**
     *
     */
    public class BarChartEntry {
        private String time = "";
        private float cost = 0.0f;
        private int color = 0;
        private int position = 0;

        public BarChartEntry (String time, float cost, int position) {
            this.time = time;
            this.cost = cost;
            this.position = position;
        }

        public void setColor(int color) { this.color = color; }

        public String getTime() {
            return time;
        }

        public float getCost() { return cost; }

        public int getColor() {
            return color;
        }

        public int getPosition() { return position; }

        public String toString() {
            return "Time: " + this.time + "\n" +
                    "Cost: " + this.cost + "\n" +
                    "Color: " + this.color + "\n" +
                    "Position: " + this.position + "\n";
        }
    }

    /**
     *
     */
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
