package android.sead_systems.seads.dashboard;

import android.content.Context;
import android.sead_systems.seads.R;
import android.sead_systems.seads.rooms.RoomManagerFactory;
import android.sead_systems.seads.rooms.RoomObject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for {@link DashboardActivity}
 * @author Talal Abou Haiba
 */

class DashboardAdapter extends BaseAdapter {
    private final List<RoomObject> mItems = RoomManagerFactory.getInstance().generateListOfRoomObjects();
    private final LayoutInflater mInflater;

    public DashboardAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public RoomObject getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).getImageId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        RoomObject item = getItem(i);

        picture.setImageResource(item.getImageId());
        name.setText(item.toString());

        return v;
    }

}
