package com.seads.seadsv3.graph;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by root on 6/2/18.
 */

public class TimeAxisFormatter implements IAxisValueFormatter{
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Log.d("VALUE", value+"");
        return "Week "+(int)(value+1);
    }
}
