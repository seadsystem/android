package android.sead_systems.seads.main_menu_pages.devices_fragment;

import android.view.View;


public interface DeviceRecyclerViewClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
