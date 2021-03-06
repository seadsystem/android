package android.sead_systems.seads.energy_cost;

import android.content.Intent;

import android.sead_systems.seads.R;
import android.sead_systems.seads.SettingsActivity;
import android.sead_systems.seads.dashboard.DashboardActivity;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Displays a graph containing the cost of electricity over a set period of time.
 * @author Chris Persons
 */

public class EnergyCostActivity extends AppCompatActivity {

    private static String mPricePerKWHour = "0.0";
    private boolean mFirstRunForBottomBar = true;
    private BarChart mBarChart;
    private BarDataSet mBarDataSet;
    private ArrayList<BarChartEntry> mBarChartList;
    private ArrayList<Float> cost;
    private ArrayList<String> times;
    private TextView priceTextView, highTextView, lowTextView;
    private BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_cost);

        priceTextView = (TextView) findViewById(R.id.electricity_cost);
        if (priceTextView != null) {
            String priceText = mPricePerKWHour + "¢ per kWh";
            priceTextView.setText(priceText);
        }

        highTextView = (TextView) findViewById(R.id.electricity_high);
        lowTextView = (TextView) findViewById(R.id.electricity_low);

        // BarChart should always have the current and next 7 times and costs
        mBarChart = (BarChart) findViewById(R.id.bar_graph);

        // Needs to be size 8
        times = new ArrayList<>();
        times.add("1 AM ");
        times.add("4 AM ");
        times.add("7 AM ");
        times.add("11 AM ");
        times.add("2 PM ");
        times.add("5 PM ");
        times.add("8 PM ");
        times.add("11 PM ");

        // Needs to be size  8
        cost = new ArrayList<>();
        cost.add(11.76f);
        cost.add(12.79f);
        cost.add(13.66f);
        cost.add(16.4f);
        cost.add(17.74f);
        cost.add(15.11f);
        cost.add(13.49f);
        cost.add(11.88f);

        setPriceAndTimeTextViews();

        mBarChartList = new ArrayList<>();
        for (int i = 0; i < cost.size(); i++) {
            BarChartEntry bc = new BarChartEntry(times.get(i), cost.get(i), i);
            mBarChartList.add(bc);
        }

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i = 0; i < times.size() && i < cost.size(); i++) {
            barEntries.add(new BarEntry(i, cost.get(i)));
        }

        mBarDataSet = new BarDataSet(barEntries, "Energy Cost By Time");
        setColorsOfBarChartList();
        BarData barData = new BarData(mBarDataSet);
        mBarChart.setData(barData);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(times));
        xAxis.setGranularity(1f);

        mBarChart.animateXY(2000,2000);
        mBarChart.setTouchEnabled(true);
        mBarChart.setDragEnabled(false);
        mBarChart.setScaleEnabled(false);
        mBarChart.setFitBars(true);

        Description desc = new Description();
        desc.setText("");
        mBarChart.setDescription(desc);
        mBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                priceTextView.setText(e.getY() + "¢ per kWh at " + times.get(Math.round(e.getX())));
            }

            @Override
            public void onNothingSelected() {
                priceTextView.setText("0.0¢ per kWh");
            }
        });

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabAtPosition(1);
        if (bottomBar != null) {
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    if(!mFirstRunForBottomBar){
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
                        mFirstRunForBottomBar = false;
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

    @Override
    public void onResume() {
        super.onResume();
        bottomBar.selectTabAtPosition(1);
    }


    /**
     *  Sets the colors of each of the elements in the mBarChartList by sorting them from lowest to
     *  highest by cost and applying colors based off those costs. Then the function sorts back to
     *  the original order of the list and applies the proper colors for the Bar Chart to be shown.
     */
    public void setColorsOfBarChartList() {

        // Sort By Cost lowest to highest
        Collections.sort(mBarChartList, new Comparator<BarChartEntry>() {
            @Override public int compare(BarChartEntry b1, BarChartEntry b2) {
                if (b1.getCost() > b2.getCost()) {
                    return 1;
                } else if (b1.getCost() < b2.getCost()) {
                    return -1;
                }
                return 0;
            }
        });

        // Get colors and apply them by cost to the list of BarChartEntries
        int[] colorArray = new int[] {R.color.bar_chart_color_1, R.color.bar_chart_color_2,
                R.color.bar_chart_color_3, R.color.bar_chart_color_4, R.color.bar_chart_color_5,
                R.color.bar_chart_color_6, R.color.bar_chart_color_7, R.color.bar_chart_color_8};
        for (int i = 0; i < colorArray.length; i++) {
            mBarChartList.get(i).setColor(colorArray[i]);
        }

        // Sort By Position lowest to highest
        Collections.sort(mBarChartList, new Comparator<BarChartEntry>() {
            @Override public int compare(BarChartEntry b1, BarChartEntry b2) {
                if (b1.getPosition() > b2.getPosition()) {
                    return 1;
                } else if (b1.getPosition() < b2.getPosition()) {
                    return -1;
                }
                return 0;
            }
        });

        // Rebuild color array off BarChartEntries by position and apply to bar chart data set
        for (int i = 0; i < colorArray.length; i++) {
            colorArray[i] = mBarChartList.get(i).getColor();
        }
        mBarDataSet.setColors(colorArray, getApplicationContext());
    }

    /**
     *  MyXAxisValueFormatter is a class that is used to format the X-Axis values.
     *  The floats of the X-Axis will be replaced with Strings that represent times of the day.
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


    /**
     * Method to return the index of the highest cost in the cost array
     */
    public int getHighestCostIndex() {
        int index = 0;
        double highCost = 0.0;
        if (cost.size() > 0) {
            highCost = cost.get(0);
        }
        for (int i = 1; i < cost.size(); i++) {
            if(cost.get(i) > highCost) {
                highCost = cost.get(i);
                index = i;
            }
        }
        return index;
    }

    /**
     * Method to return the index of the lowest cost in the cost array
     */
    public int getLowestCostIndex() {
        int index = 0;

        // Needed to initiate it to an unreasonably high value for getting the lowest value
        double lowCost = 500000;
        if (cost.size() > 0) {
            lowCost = cost.get(0);
        }
        for (int i = 1; i < cost.size(); i++) {
            if(cost.get(i) < lowCost) {
                lowCost = cost.get(i);
                index = i;
            }
        }
        return index;
    }


    /**
     * Sets the price and time of both highest and lowest text views.
     */
    public void setPriceAndTimeTextViews() {
        int highestIndex = getHighestCostIndex();
        int lowestIndex = getLowestCostIndex();

        String highCostText = "Highest Cost: " + cost.get(highestIndex) + "¢ per kWh at "
                + times.get(highestIndex);
        highTextView.setText(highCostText);

        String lowCostText = "Lowest Cost: " + cost.get(lowestIndex) + "¢ per kWh at "
                + times.get(lowestIndex);
        lowTextView.setText(lowCostText);

    }
}
