package com.seads.seadsv3.main_menu_pages;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.seads.seadsv3.R;
import com.seads.seadsv3.SeadsAppliance;
import com.seads.seadsv3.SeadsDevice;
import com.seads.seadsv3.SeadsRoom;
import com.seads.seadsv3.http.WebInterface;
import com.seads.seadsv3.http.WebInterfacer;
import com.seads.seadsv3.main_menu.EnumNavBarNames;
import com.seads.seadsv3.main_menu_pages.devices_fragment.AdapterRecyclerViewDevices;
import com.seads.seadsv3.main_menu_pages.devices_fragment.DeviceViewInfo;
import com.seads.seadsv3.main_menu_pages.devices_fragment.RecyclerViewItemDecoration;
import com.seads.seadsv3.main_menu_pages.overview_fragment.GridViewAdapter;
import com.seads.seadsv3.main_menu_pages.rooms_fragment.AdapterRecyclerViewRooms;
import com.seads.seadsv3.main_menu_pages.rooms_fragment.RoomViewInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Factory class for handling fragments
 */
public class PageFragmentFactory extends Fragment implements WebInterface{
    private static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private int counter = 0;
    Float power_data [];
    private ProgressBar progressBar;
    private PieChart mChart; // This is the holder for the overview activity's piechart, since it requires an activity
    private SeadsDevice seadsDevice;
    /**
     * Method PageFramnentFactory
     * This method parses the arguments from the main class and initializes the factory
     * @param pageFragmentConfig Configuration settings for fragments
     * @return Fragment of specified tab
     */
    public static PageFragmentFactory newInstance(PageFragmentConfig pageFragmentConfig, SeadsDevice seadsDevice) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageFragmentConfig.getPage());
        args.putParcelable("seads", seadsDevice);
        PageFragmentFactory fragment = new PageFragmentFactory();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Override method for creating fragment with tab argument specifier
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        seadsDevice = getArguments().getParcelable("seads");
    }

    /**
     * Override method for making the views associated with the fragments
     * @param inflater Which layout we want to populate
     * @param container The container for the fragment (subset of activity)
     * @param savedInstanceState Activity state with optional arguments
     * @return
     */
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

    /**
     * Main method for interfacing and instantiating fragments
     * @param savedInstanceState Passed from parent activity
     */
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


    /**
     * Method for handling the overview graph fragment
     * Use to populate graph with results from database server response
     * @param result the result of the HTTP response
     */
    @Override
    public void onJSONRetrieved(JSONObject result){
        try{
            JSONArray data= result.getJSONArray("data");
            JSONObject index0 = data.getJSONObject(0);
            Log.d("DashboardActivity","index0 time: "+index0.getString("time"));
            Log.d("DashboardActivity","index0 energy: "+index0.getString("energy"));
            power_data[counter++] = Float.parseFloat(index0.getString("energy"));
            setChartData();
            progressBar.setVisibility(View.INVISIBLE);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Sets up the pie chart for the overview fragment
     * Use in conjunction with the http request and set data
     * Careful not to populate with junk data since this will crash the app
     */
    private void setupOverviewFragment(){
        final Activity parent = getActivity();
        Log.d("SFragment", "Set up overview");
        mChart = (PieChart) parent.findViewById(R.id.overview_chart);
        progressBar = parent.findViewById(R.id.overview_progress_bar);
        power_data = new Float[3];

        // enable description text
        mChart.getDescription().setEnabled(false);
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
        mChart.setRotationEnabled(false);
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

    /**
     * Method for making the database request to get chart data
     * Note that this method only requests the data for the last day
     * for all 3 panels.
     * @param webInterfacer Interface for database request handler
     */
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

    /**
     * Method for setting the piechart data in the overview fragment
     * We only have 3 panels at this time, so all the energy usage of all 3
     * panels are stored in a float array
     * No need to average the data or convert it to percent values, the piechart class
     * handles all of that internally
     */
    public void setChartData(){
        if(counter<3){
            return;
        }
        counter = 0;
        ArrayList<PieEntry> entries = new ArrayList<>();

        for(int i = 0; i<power_data.length; i++){
            entries.add(new PieEntry(power_data[i], "Room "+ (i+1) ));
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
        mChart.animateXY(500, 500);
        mChart.invalidate();
    }

    /**
     * Fragment for making the awards page
     */
    private void setupAwardsFragment() {
        final Activity parent = getActivity();
        GridView gridview = (GridView) parent.findViewById(R.id.grid_view_about);
        gridview.setAdapter(new GridViewAdapter(parent));
    }

    /**
     * Internal method for instantiating the device page fragment. This will
     * need to be changed once we have disaggregration data
     */
    private void setupDevicesFragment() {
        final Activity parent = getActivity();
        RecyclerView recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view_devices);
        ArrayList<SeadsRoom> roomsList;

        if(seadsDevice == null){
            roomsList = null;
        }else {
            roomsList = seadsDevice.getRooms();
        }
        Log.d("WE HAVE", ""+roomsList.size()+" rooms");
        ArrayList<SeadsAppliance> appliances = new ArrayList<>();
        for(SeadsRoom room : roomsList){
            appliances.addAll(room.getApps());
        }
        Log.d("WE HAVE", ""+appliances.size()+roomsList.size()+" apps");


        DeviceViewInfo[] dummyData = new DeviceViewInfo[appliances.size()+roomsList.size()];
        // TODO populate this with real data. Panel names should come from server
        int j = 0;
        for(int i = 0; i<roomsList.size();i++){
            dummyData[j++] = new DeviceViewInfo("Appliances in "+roomsList.get(i).getRoomName(), true);
            for(SeadsAppliance app : roomsList.get(i).getApps()){
                dummyData[j++] = new DeviceViewInfo(app.getTag());
            }
        }
        AdapterRecyclerViewDevices adapterRecyclerViewDevices =
                new AdapterRecyclerViewDevices(dummyData, this, seadsDevice);

        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(parent));
        recyclerView.setAdapter(adapterRecyclerViewDevices);
        recyclerView.setLayoutManager(new LinearLayoutManager(parent));
    }

    /**
     * Rooms fragment instantiatior
     * This method creates all the rooms for which we have registered with the SEADS system
     */
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
