package android.sead_systems.seads.main_menu_pages;

import android.app.Activity;
import android.os.Bundle;
import android.sead_systems.seads.R;
import android.sead_systems.seads.main_menu.EnumNavBarNames;
import android.sead_systems.seads.main_menu_pages.devices_fragment.AdapterRecyclerViewDevices;
import android.sead_systems.seads.main_menu_pages.devices_fragment.DeviceViewInfo;
import android.sead_systems.seads.main_menu_pages.devices_fragment.RecyclerViewItemDecoration;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        if(mPage == EnumNavBarNames.DEVICES.getIndex()) {
            setupDevicesFragment();
        }
    }

    private void setupDevicesFragment() {
        final Activity parent = getActivity();
        RecyclerView recyclerView = (RecyclerView) parent.findViewById(R.id.my_recycler_view);
        DeviceViewInfo[] dummyData = new DeviceViewInfo[10];
        // TODO populate this with real data. Panel names should come from server
        dummyData[0] = new DeviceViewInfo("Devices in Sample Room", true);
        dummyData[1] = new DeviceViewInfo("Panel 1");
        dummyData[2] = new DeviceViewInfo("Devices with no room assigned", true);
        dummyData[3] = new DeviceViewInfo("Panel 2");
        dummyData[4] = new DeviceViewInfo("Panel 3");
        dummyData[5] = new DeviceViewInfo("Panel 4");
        dummyData[6] = new DeviceViewInfo("Panel 5");
        dummyData[7] = new DeviceViewInfo("Panel 6");
        dummyData[8] = new DeviceViewInfo("Panel 7");
        dummyData[9] = new DeviceViewInfo("Panel 8");

        AdapterRecyclerViewDevices adapterRecyclerViewDevices =
                new AdapterRecyclerViewDevices(dummyData);

        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(parent));
        recyclerView.setAdapter(adapterRecyclerViewDevices);
        recyclerView.setLayoutManager(new LinearLayoutManager(parent));
    }
}
