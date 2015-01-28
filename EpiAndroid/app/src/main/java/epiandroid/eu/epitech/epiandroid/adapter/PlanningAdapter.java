package epiandroid.eu.epitech.epiandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import epiandroid.eu.epitech.epiandroid.R;


import epiandroid.eu.epitech.epiandroid.model.PlanningItem;

/**
 * Created by remihillairet on 28/01/15.
 */
public class PlanningAdapter extends ArrayAdapter<PlanningItem> {

    private int resource;

    public PlanningAdapter(Context context, int resource, List<PlanningItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(this.resource, null);
        }

        PlanningItem planningItem = getItem(position);

        if (planningItem != null) {
            TextView titleLabel = (TextView) v.findViewById(R.id.adapterPlanningTitle);
            TextView scheduleLabel = (TextView) v.findViewById(R.id.adapterPlanningSchedule);

            if (titleLabel != null) {
                titleLabel.setText(planningItem.getTitle());
            }

            if (scheduleLabel != null) {
                scheduleLabel.setText(planningItem.getSchedule());
            }
        }

        return v;
    }
}
