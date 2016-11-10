package android.sead_systems.seads;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by camwh on 10/28/2016.
 */

public class Adapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;


    public Adapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.devicelist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.devicelist, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(
                R.id.text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        return rowView;
    };
}
