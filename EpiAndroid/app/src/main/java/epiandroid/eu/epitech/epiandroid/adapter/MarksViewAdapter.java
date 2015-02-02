package epiandroid.eu.epitech.epiandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.model.MarksItem;

/**
 * Created by linard_f on 1/22/15.
 */
public class MarksViewAdapter extends ArrayAdapter<MarksItem> {

    private int resource;
    private Context context;
    private ArrayList<MarksItem> objects;

    static class ViewHolder {
        public TextView projectName;
        public TextView mark;
        public TextView rater;
    }

    public MarksViewAdapter(Context context, int resource, ArrayList<MarksItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        Collections.reverse(objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.projectName = (TextView) row.findViewById(R.id.project_name);
            viewHolder.mark = (TextView) row.findViewById(R.id.mark);
            viewHolder.rater = (TextView) row.findViewById(R.id.rater);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MarksItem item = objects.get(position);
        viewHolder.projectName.setText(item.getName());
        viewHolder.mark.setText(item.getMark());
        viewHolder.rater.setText(item.getRater());
        return row;
    }
}
