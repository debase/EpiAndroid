package epiandroid.eu.epitech.epiandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.model.MarksItem;

/**
 * Created by linard_f on 1/22/15.
 */
public class MarksViewAdapter extends ArrayAdapter<MarksItem> {

    private int resource;
    private Context context;
    private ArrayList<MarksItem> objects;
    private LinearLayout layout;

    public MarksViewAdapter(Context context, int resource, ArrayList<MarksItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            layout = (LinearLayout) row.findViewById(R.id.mark_item_layout);

            MarksItem item = objects.get(position);
            TextView label = new TextView(context);
            label.setText(item.getName());
            layout.addView(label);
        }

        return row;

    }
}
