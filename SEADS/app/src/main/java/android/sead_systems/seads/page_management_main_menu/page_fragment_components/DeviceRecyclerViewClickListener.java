package android.sead_systems.seads.page_management_main_menu.page_fragment_components;

import android.view.View;

/**
 * Created by jungk on 2/12/2018.
 */

public interface DeviceRecyclerViewClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
