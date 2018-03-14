package com.seads.seadsv2.graph;

import com.seads.seadsv2.R;
import com.seads.seadsv2.http.WebInterface;
import com.seads.seadsv2.http.WebInterfacer;

import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by root on 3/14/18.
 */
public class RoomVisualizationFragmentTest implements WebInterface{
    @Test
    public void confirmCorrectMinutes() throws Exception {
        WebInterfacer test = new WebInterfacer(this);
        test.getJSONObject(1519862400,1519948800,"energy",60,"Panel3", "P");
        RoomVisualizationFragment  sut = new RoomVisualizationFragment();
        sut.data_point_date_map = new HashMap<>();
        sut.fillXAxis(
                (long)1519862400*1000,
                (long)1519948800*1000,
                1,
                10,
                0
        );
        System.out.println(sut.data_point_date_map.toString());
        assertEquals("16:00", sut.data_point_date_map.get(0));
    }

    @Test
    public void confirmCorrectDay() throws Exception {
        WebInterfacer test = new WebInterfacer(this);
        test.getJSONObject(1519862400,1519948800,"energy",60,"Panel3", "P");
        RoomVisualizationFragment  sut = new RoomVisualizationFragment();
        sut.data_point_date_map = new HashMap<>();
        sut.fillXAxis(
                (long)1519862400*1000,
                (long)1519948800*1000,
                1,
                10,
                0
        );
        assertEquals("16:00", sut.data_point_date_map.get(0));
    }

    @Test
    public void confirmCorrectMonthDate() throws Exception {
        WebInterfacer test = new WebInterfacer(this);
        test.getJSONObject(1519862400,1519948800,"energy",60,"Panel3", "P");
        RoomVisualizationFragment  sut = new RoomVisualizationFragment();
        sut.data_point_date_map = new HashMap<>();
        sut.fillXAxis(
                (long)1519862400*1000,
                (long)1519948800*1000,
                1,
                10,
                0
        );
        assertEquals("16:00", sut.data_point_date_map.get(0));
    }

    @Override
    public void onJSONRetrieved(JSONObject result) {

        System.out.printf("Test success");
    }


}