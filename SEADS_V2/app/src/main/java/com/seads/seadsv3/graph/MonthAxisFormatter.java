package com.seads.seadsv3.graph;
import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Ric on 02/06/18.
 */
public class MonthAxisFormatter implements IAxisValueFormatter
{
    private final long DAY_INT = 86400;

    private long referenceTimestamp; // minimum timestamp in your data set
    private DateFormat mDataFormat;
    private Date mDate;

    public MonthAxisFormatter(long referenceTimestamp) {
        this.referenceTimestamp = referenceTimestamp;
        this.mDataFormat = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
        this.mDataFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.mDate = new Date();
    }


    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return
     */
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // convertedTimestamp = originalTimestamp - referenceTimestamp
        long convertedTimestamp = (long) (value*DAY_INT*7);

        // Retrieve original timestamp
        long originalTimestamp = referenceTimestamp + convertedTimestamp;
        long current_time = System.currentTimeMillis()/1000;

        // Convert timestamp to hour:minute
        return getHour(originalTimestamp)+"-"+getHour((long)((originalTimestamp+(value+1)*DAY_INT*6)>current_time?current_time:originalTimestamp+(value+1)*DAY_INT*6));
    }


    private String getHour(long timestamp){
        try{
            Log.d("FORMAT", timestamp+"");
            mDate.setTime(timestamp*1000);
            return mDataFormat.format(mDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}