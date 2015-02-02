package epiandroid.eu.epitech.epiandroid.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import epiandroid.eu.epitech.epiandroid.R;


import epiandroid.eu.epitech.epiandroid.model.PlanningItem;

/**
 * Created by remihillairet on 28/01/15.
 */
public class PlanningAdapter extends ArrayAdapter<PlanningItem> implements View.OnClickListener {

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
            Button btnToken = (Button) v.findViewById(R.id.btnToken);

            if (titleLabel != null) {
                titleLabel.setText(planningItem.getTitle());
            }

            if (scheduleLabel != null) {
                scheduleLabel.setText(planningItem.getSchedule());
            }

            if (btnToken != null) {
                btnToken.setOnClickListener(this);
            }
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(getContext())
                .setTitle("Enter your token")
                .setView(input)
                .setPositiveButton("Validate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Send token
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do nothing.
            }
        }).show();
    }
}
