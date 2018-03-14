package com.seads.seadsv2.main_menu_pages.devices_fragment;

import android.content.Context;
import android.content.Intent;
import com.seads.seadsv2.BaseActivityWithDrawer;
import com.seads.seadsv2.R;
import com.seads.seadsv2.device_panel_page.DeviceAndRoomStatsActivity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

public class AdapterRecyclerViewDevices extends RecyclerView.Adapter<AdapterRecyclerViewDevices.ViewHolder> {

    private final int DEVICE_HEADER = 1;
    private final int DEVICE_ITEM = 0;
    private DeviceViewInfo[] mDataset;
    private Fragment mFragment;
    private HashMap<String, String> room_map;
    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View mView;
        private final Context mContext;
        public ViewHolder(View v) {
            super(v);
            this.mView = v;
            mContext = v.getContext();
        }
        @Override
        public void onClick(View v) {
            Log.d("Devices", "onClick " + getLayoutPosition());
            Intent intent = new Intent(mContext, DeviceAndRoomStatsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("Device", room_map.get(""+getLayoutPosition()));
            Log.d("Devices", room_map.toString());
            Log.d("Devices",mFragment.getActivity().getLocalClassName());
            mFragment.getActivity().startActivityForResult(intent, BaseActivityWithDrawer.REQUEST_CODE);
        }

    }

    /**
     * Recycler for main menu's devices list
     * @param dataset takes in a dataset to use to populate the reyclerview
     */
    public AdapterRecyclerViewDevices(DeviceViewInfo[] dataset, Fragment fragment) {
        mDataset = dataset;
        mFragment = fragment;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        this.room_map = new HashMap<>();
        this.room_map.put(""+1, "Panel1");
        this.room_map.put(""+3, "Panel2");
        this.room_map.put(""+4, "Panel3");

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LinearLayout linearLayout;
        switch(viewType) {
            case DEVICE_HEADER:
                linearLayout = (LinearLayout) layoutInflater
                        .inflate(R.layout.devices_item_title_bar, parent, false);
                break;
            case DEVICE_ITEM:
                linearLayout = (LinearLayout) layoutInflater
                        .inflate(R.layout.devices_item, parent, false);
                break;
            default:
                // TODO: This needs to be an error view!
                linearLayout = (LinearLayout) layoutInflater
                        .inflate(R.layout.devices_item, parent, false);
                break;
        }

        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(linearLayout);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(mDataset[position].isHeader()) {
            return DEVICE_HEADER;
        } else {
            return DEVICE_ITEM;
        }
    }

    /**
     * Set up the new view with proper text and images
     * Called after creating the new viewholders
     * @param holder Holds the view
     * @param position ViewHolder's associated position in the data set
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(!mDataset[position].isHeader()) {
            // On some viewholders we need a variable to indicate whether it's a ui element or a device
            ((ImageView) holder.mView.findViewById(R.id.devices_item_image)).setImageResource(R.drawable.rooms);

            ((TextView) holder.mView.findViewById(R.id.text_view_device_name)).setText(
                    mDataset[position].getDeviceName());
            ((TextView) holder.mView.findViewById(R.id.text_view_device_cost)).setText(
                    mDataset[position].getCostInPastDay());
            holder.mView.setOnClickListener(holder);
        } else {
            ((TextView) holder.mView.findViewById(R.id.text_view_devices_item_title)).setText(
                    mDataset[position].getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


}
