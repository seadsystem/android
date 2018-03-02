package com.seads.seadsv2.main_menu_pages;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.seads.seadsv2.R;
import com.seads.seadsv2.main_menu.EnumNavBarNames;
import com.seads.seadsv2.main_menu_pages.devices_fragment.AdapterRecyclerViewDevices;
import com.seads.seadsv2.main_menu_pages.devices_fragment.DeviceViewInfo;
import com.seads.seadsv2.main_menu_pages.devices_fragment.RecyclerViewItemDecoration;
import com.seads.seadsv2.main_menu_pages.overview_fragment.GridViewAdapter;
import com.seads.seadsv2.main_menu_pages.rooms_fragment.AdapterRecyclerViewRooms;
import com.seads.seadsv2.main_menu_pages.rooms_fragment.RoomViewInfo;

public class PageFragmentFactory extends Fragment {
    private static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

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
        } else if (mPage == EnumNavBarNames.AWARDS.getIndex()) {
            // Setup rooms fragment
            setupAwardsFragment();
        }
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
