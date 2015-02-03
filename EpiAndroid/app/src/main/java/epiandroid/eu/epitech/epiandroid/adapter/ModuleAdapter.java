package epiandroid.eu.epitech.epiandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.model.ModuleItem;
import epiandroid.eu.epitech.epiandroid.model.PlanningItem;

/**
 * Created by remihillairet on 03/02/15.
 */
public class ModuleAdapter extends ArrayAdapter<ModuleItem> {

    private int resource;

    public ModuleAdapter(Context context, int resource, ModuleItem[] objects) {
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

        ModuleItem moduleItem = getItem(position);

        if (moduleItem != null) {
            TextView titleLabel = (TextView) v.findViewById(R.id.adapterModuleTitle);
            TextView gradeLabel = (TextView) v.findViewById(R.id.adapterModuleGrade);

            if (titleLabel != null) {
                titleLabel.setText(moduleItem.getTitle());
            }

            if (gradeLabel != null) {
                gradeLabel.setText(moduleItem.getGrade());
            }
        }

        return v;
    }
}
