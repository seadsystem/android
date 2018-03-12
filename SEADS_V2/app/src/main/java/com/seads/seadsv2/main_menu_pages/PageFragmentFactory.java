package com.seads.seadsv2.main_menu_pages;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.seads.seadsv2.R;
import com.seads.seadsv2.http.WebInterface;
import com.seads.seadsv2.http.WebInterfacer;
import com.seads.seadsv2.main_menu.EnumNavBarNames;
import com.seads.seadsv2.main_menu_pages.devices_fragment.AdapterRecyclerViewDevices;
import com.seads.seadsv2.main_menu_pages.devices_fragment.DeviceViewInfo;
import com.seads.seadsv2.main_menu_pages.devices_fragment.RecyclerViewItemDecoration;
import com.seads.seadsv2.main_menu_pages.overview_fragment.GridViewAdapter;
import com.seads.seadsv2.main_menu_pages.rooms_fragment.AdapterRecyclerViewRooms;
import com.seads.seadsv2.main_menu_pages.rooms_fragment.RoomViewInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PageFragmentFactory extends Fragment implements WebInterface{
    private static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private int counter = 0;
    Float power_data [];
    private PieChart mChart;

    public static PageFragmentFactory newInstance(PageFragmentConfig pageFragmentConfig) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageFragmentConfig.getPage());
        PageFragmentFactory fragment = new PageFragmentFactory();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(mPage == EnumNavBarNames.DEVICES.getIndex()) {
            view = inflater.inflate(EnumNavBarNames.DEVICES.getLayoutId(), container, false);
        } else if (mPage == EnumNavBarNames.ROOMS.getIndex()) {
            view = inflater.inflate(EnumNavBarNames.ROOMS.getLayoutId(), container, false);
        } else if (mPage == EnumNavBarNames.OVERVIEW.getIndex()) {
            view = inflater.inflate(EnumNavBarNames.OVERVIEW.getLayoutId(), container, false);
        }else if (mPage == EnumNavBarNames.AWARDS.getIndex()) {
            view = inflater.inflate(EnumNavBarNames.AWARDS.getLayoutId(), container, false);
        }else if (mPage == EnumNavBarNames.SETTINGS.getIndex()) {
            view = inflater.inflate(EnumNavBarNames.SETTINGS.getLayoutId(), container, false);
        } else {
            // TODO replace with error view
            view = inflater.inflate(EnumNavBarNames.ROOMS.getLayoutId(), container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mPage == EnumNavBarNames.DEVICES.getIndex()) {
            setupDevicesFragment();
        } else if (mPage == EnumNavBarNames.ROOMS.getIndex()) {
            // Setup rooms fragment
            setupRoomsFragment();
        } else if(mPage == EnumNavBarNames.OVERVIEW.getIndex()){
            setupOverviewFragment();
        } else if(mPage == EnumNavBarNames.AWARDS.getIndex()) {
            setupAwardsFragment();
        }
    }

    @Override
    public void onJSONRetrieved(JSONObject result){
        try{
            JSONArray data= result.getJSONArray("data");
            JSONObject index0 = data.getJSONObject(0);
            Log.d("DashboardActivity","index0 time: "+index0.getString("time"));
            Log.d("DashboardActivity","index0 energy: "+index0.getString("energy"));
            power_data[counter++] = Float.parseFloat(index0.getString("energy"));
            setChartData();
            /*
            PieData lineData = mChart.getData();
            if (lineData == null){
                lineData = new PieData();
            }

            IPieDataSet iLineDataSet = lineData.getDataSetByIndex(0);
            if(iLineDataSet == null){
                iLineDataSet = createSet();
                lineData.addDataSet(iLineDataSet);
            }

            lineData.addEntry(new Entry(counter++, Float.parseFloat(index0.getString("energy"))), 0);

            lineData.notifyDataChanged();
            mChart.notifyDataSetChanged();
            */

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setupOverviewFragment(){
        final Activity parent = getActivity();
        Log.d("SFragment", "Set up overview");
        mChart = (PieChart) parent.findViewById(R.id.overview_chart);
        power_data = new Float[3];

        // enable description text
        mChart.getDescription().setEnabled(false);
        mChart.setUsePercentValues(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        Log.d("charter", ""+mChart.isShown());

        // entry label styling
        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTextSize(14f);
        WebInterfacer webInterfacer = new WebInterfacer(this);
        queryChartData(webInterfacer);
        //WebInterfacer webInterfacer = new WebInterfacer(this);
        //queryChartData();
    }

    public void queryChartData(WebInterfacer webInterfacer){
        long current_time = System.currentTimeMillis();
        int DAY_INT = 86400000;

        webInterfacer.getJSONObject(
                (current_time-current_time%DAY_INT-DAY_INT)/1000,
                (current_time-current_time%DAY_INT)/1000,
                "energy",
                60*60*12,
                "Panel1",
                "P"
        );
        webInterfacer.getJSONObject(
                (current_time-current_time%DAY_INT-DAY_INT)/1000,
                (current_time-current_time%DAY_INT)/1000,
                "energy",
                60*60*12,
                "Panel2",
                "P"
        );
        webInterfacer.getJSONObject(
                (current_time-current_time%DAY_INT-DAY_INT)/1000,
                (current_time-current_time%DAY_INT)/1000,
                "energy",
                60*60*12,
                "Panel3",
                "P"
        );
    }

    public void setChartData(){
        if(counter<3){
            return;
        }
        counter = 0;
        ArrayList<PieEntry> entries = new ArrayList<>();

        for(int i = 0; i<power_data.length; i++){
            entries.add(new PieEntry(power_data[i], "Panel "+ (i+1) ));
        }
        PieDataSet dataSet = new PieDataSet(entries, "% Power Usage");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        PieData data = new PieData(dataSet);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.BLACK);
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();

        mChart.notifyDataSetChanged();
    }

    private void setData(int count, float range, PieChart mChart) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    "test"));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);
        mChart.notifyDataSetChanged();

        mChart.invalidate();
    }

    private void setupAwardsFragment() {
        final Activity parent = getActivity();
        GridView gridview = (GridView) parent.findViewById(R.id.grid_view_about);
        gridview.setAdapter(new GridViewAdapter(parent));
    }

    private void setupDevicesFragment() {
        final Activity parent = getActivity();
        RecyclerView recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view_devices);
        DeviceViewInfo[] dummyData = new DeviceViewInfo[5];
        // TODO populate this with real data. Panel names should come from server
        dummyData[0] = new DeviceViewInfo("Devices in Sample Room", true);
        dummyData[1] = new DeviceViewInfo("Panel 1");
        dummyData[2] = new DeviceViewInfo("Devices with no room assigned", true);
        dummyData[3] = new DeviceViewInfo("Panel 2");
        dummyData[4] = new DeviceViewInfo("Panel 3");

        AdapterRecyclerViewDevices adapterRecyclerViewDevices =
                new AdapterRecyclerViewDevices(dummyData, this);

        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(parent));
        recyclerView.setAdapter(adapterRecyclerViewDevices);
        recyclerView.setLayoutManager(new LinearLayoutManager(parent));
    }

    private void setupRoomsFragment() {
        final Activity parent = getActivity();
        RecyclerView recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view_room);
        RoomViewInfo[] dummyData = new RoomViewInfo[2];
        // TODO populate this with real data. Panel names should come from server
        dummyData[0] = new RoomViewInfo("Sample room", 1);
        dummyData[1] = new RoomViewInfo("Empty room" , 0);

        AdapterRecyclerViewRooms adapterRecyclerViewRooms =
                new AdapterRecyclerViewRooms(dummyData, this);

        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(parent));
        recyclerView.setAdapter(adapterRecyclerViewRooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(parent));
    }
}
