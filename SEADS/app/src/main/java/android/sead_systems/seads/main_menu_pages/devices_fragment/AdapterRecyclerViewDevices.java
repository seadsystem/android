package android.sead_systems.seads.main_menu_pages.devices_fragment;

import android.content.Context;
import android.content.Intent;
import android.sead_systems.seads.R;
import android.sead_systems.seads.device_panel_page.DeviceAndRoomStatsActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterRecyclerViewDevices extends RecyclerView.Adapter<AdapterRecyclerViewDevices.ViewHolder> {

    private final int DEVICE_HEADER = 1;
    private final int DEVICE_ITEM = 0;
    private DeviceViewInfo[] mDataset;

    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View mView;
        private final Context mContext;
        public ViewHolder(View v) {
            super(v);
            this.mView = v;
            mContext = v.getContext();
        }
        @Override
        public void onClick(View v) {
            Log.d("Page", "onClick " + getLayoutPosition());
            Intent intent = new Intent(mContext, DeviceAndRoomStatsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("device", getLayoutPosition());
            mContext.startActivity(intent);
        }
    }

    /**
     * Recycler for main menu's devices list
     * @param dataset takes in a dataset to use to populate the reyclerview
     */
    public AdapterRecyclerViewDevices(DeviceViewInfo[] dataset) {
        mDataset = dataset;
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
            ((ImageView) holder.mView.findViewById(R.id.devices_item_image)).setImageResource(R.drawable.rounded_button);

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
