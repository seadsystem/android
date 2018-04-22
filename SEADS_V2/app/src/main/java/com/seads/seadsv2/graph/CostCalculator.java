package com.seads.seadsv2.graph;

import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Home on 4/17/18.
 */

public class CostCalculator {
    private static double[] hourlyCost = {
            0.17918, 0.17918, 0.17918, // 12am 1am 2am
            0.17918, 0.17918, 0.17918, // 3am 4am 5am
            0.17918, 0.17918, 0.17918, // 6am 7am 8am
            0.17918, 0.25596 , 0.25596 , // 9am 10am 11am
            0.25596 , 0.37123, 0.37123, // 12pm 1pm 2pm
            0.37123, 0.37123, 0.37123, // 3pm 4pm 5pm
            0.37123, 0.25596 , 0.25596 , // 6pm 7pm 8pm
            0.25596 , 0.17918, 0.17918  // 9pm 10pm 11pm
    };
    public static double energyCost(double total_energy){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.HOUR_OF_DAY;
        double total_cost = total_energy*hourlyCost[hour];
        Log.d("Current Hour", hour+"");
        Log.d("Current Cost", total_energy * hourlyCost[hour]+"");
        return total_cost;
    }
}
