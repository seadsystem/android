package android.sead_systems.seads.page_management_main_menu;

import android.app.Activity;
import android.os.Bundle;
import android.sead_systems.seads.R;
import android.sead_systems.seads.page_management_main_menu.page_fragment_components.AdapterRecyclerViewDevices;
import android.sead_systems.seads.page_management_main_menu.page_fragment_components.DeviceViewInfo;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragmentFactory extends Fragment {
    private static final String ARG_PAGE = "ARG_PAGE";
    private final int FRAGMENT_DEVICES = 0;
    private final int FRAGMENT_ROOMS = 1;
    private final int FRAGMENT_OVERVIEW = 2;
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
        switch(mPage) {
            case FRAGMENT_DEVICES:
                view = inflater.inflate(R.layout.fragment_device_main_menu, container, false);
                break;
            case FRAGMENT_ROOMS:
                view = inflater.inflate(R.layout.fragment_room_main_menu, container, false);

                break;
            case FRAGMENT_OVERVIEW:
                view = inflater.inflate(R.layout.fragment_room_main_menu, container, false);
                break;
            default:
                // TODO replace with report error view
                view = inflater.inflate(R.layout.fragment_room_main_menu, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mPage == FRAGMENT_DEVICES) {
            final Activity parent = getActivity();
            RecyclerView recyclerView = (RecyclerView) parent.findViewById(R.id.my_recycler_view);
            DeviceViewInfo[] xdd = new DeviceViewInfo[3];
            // TODO populate this with real data. Panel names should come from server
            xdd[0] = new DeviceViewInfo("Panel 1");
            xdd[1] = new DeviceViewInfo("Panel 2");
            xdd[2] = new DeviceViewInfo("Panel 3");

            AdapterRecyclerViewDevices x = new AdapterRecyclerViewDevices(xdd);
//            recyclerView.addOnItemTouchListener(new DeviceRecyclerViewTouchListener(parent, recyclerView, new DeviceRecyclerViewClickListener() {
//                @Override
//                public void onClick(View view, int position) {
//                    Log.d("PageFragmentFactory", "in onClick!");
//                    Toast.makeText(parent, "position: " + position, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onLongClick(View view, int position) {
//                    Log.d("PageFragmentFactory", "in onLongClick!");
//                }
//            }));

            recyclerView.setAdapter(x);
            recyclerView.setLayoutManager(new LinearLayoutManager(parent));
        }

    }
}
