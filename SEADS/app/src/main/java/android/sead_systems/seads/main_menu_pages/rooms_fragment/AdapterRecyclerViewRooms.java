package android.sead_systems.seads.main_menu_pages.rooms_fragment;

import android.content.Context;
import android.sead_systems.seads.R;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;


public class AdapterRecyclerViewRooms extends RecyclerView.Adapter<AdapterRecyclerViewRooms.ViewHolder> {

    private RoomViewInfo mDataset[];
    private Fragment mFragment;


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
//            Log.d("Page", "onClick " + getLayoutPosition());
//            Intent intent = new Intent(mContext, DeviceAndRoomStatsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            intent.putExtra("room", getLayoutPosition());
//            Log.d("Adapter",mFragment.getActivity().getLocalClassName());
//            mFragment.getActivity().startActivityForResult(intent, BaseActivityWithDrawer.REQUEST_CODE);
        }

    }

    public AdapterRecyclerViewRooms(RoomViewInfo[] dataset, Fragment fragment) {
        mDataset = dataset;
        mFragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LinearLayout linearLayout;
        linearLayout = (LinearLayout) layoutInflater
                .inflate(R.layout.rooms_item, parent, false);
        AdapterRecyclerViewRooms.ViewHolder viewHolder = new AdapterRecyclerViewRooms.ViewHolder(linearLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("Adapter", "position" + position);
        ((ImageView) holder.mView.findViewById(R.id.rooms_item_image)).setImageResource(R.drawable.rounded_button);
        ((TextView) holder.mView.findViewById(R.id.text_view_room_name)).setText(
                mDataset[position].getTitle());
        ((TextView) holder.mView.findViewById(R.id.text_view_number_of_devices)).setText(
                "Devices: " + mDataset[position].getDevicesInRoom());
        ((TextView) holder.mView.findViewById(R.id.text_view_room_cost)).setText(
                mDataset[position].getCostInPastDay());
        holder.mView.setOnClickListener(holder);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
