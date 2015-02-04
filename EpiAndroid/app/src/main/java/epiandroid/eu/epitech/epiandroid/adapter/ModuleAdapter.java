package epiandroid.eu.epitech.epiandroid.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.model.ModuleItem;

/**
 * Created by remihillairet on 03/02/15.
 */
public class ModuleAdapter extends ArrayAdapter<ModuleItem> {

    private int resource;
    private ArrayList<ModuleItem> objects;
    private ArrayList<ModuleItem> listCopy;

    public ModuleAdapter(Context context, int resource, ArrayList<ModuleItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.objects = objects;
        this.listCopy = new ArrayList<>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(this.resource, null);
        }

        ModuleItem moduleItem = objects.get(position);

        String[] months = new String[] {getContext().getResources().getString(R.string.january),
                getContext().getResources().getString(R.string.february),
                getContext().getResources().getString(R.string.march),
                getContext().getResources().getString(R.string.april),
                getContext().getResources().getString(R.string.may),
                getContext().getResources().getString(R.string.june),
                getContext().getResources().getString(R.string.july),
                getContext().getResources().getString(R.string.august),
                getContext().getResources().getString(R.string.september),
                getContext().getResources().getString(R.string.october),
                getContext().getResources().getString(R.string.november),
                getContext().getResources().getString(R.string.december)};

        if (moduleItem != null) {
            TextView titleLabel = (TextView) v.findViewById(R.id.adapterModuleTitle);
            TextView gradeLabel = (TextView) v.findViewById(R.id.adapterModuleGrade);
            TextView dateLabel = (TextView) v.findViewById(R.id.adapterModuleDateSubscribed);
            TextView credits = (TextView) v.findViewById(R.id.adapterModuleCredits);

            if (titleLabel != null) {
                titleLabel.setText(moduleItem.getTitle());
            }

            if (gradeLabel != null) {
                String grade = moduleItem.getGrade();
                if (grade.equalsIgnoreCase("acquis")) {
                    grade = getContext().getResources().getString(R.string.module_acquired);
                }
                if (grade.equalsIgnoreCase("echec")) {
                    grade = getContext().getResources().getString(R.string.module_fail);
                }
                gradeLabel.setText(grade);
            }

            if (dateLabel != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date;
                try {
                    date = format.parse(moduleItem.getDateSubscribed());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    dateLabel.setText(cal.get(Calendar.DAY_OF_MONTH) + " " + months[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (credits != null) {
                credits.setText(moduleItem.getCredits());
            }
        }

        return v;
    }

    public void filter(String charText) {
        Log.v("FILTER", "[" + charText + "]");
        objects.clear();
        if (charText.length() == 0) {
            objects.addAll(listCopy);
        } else {
            for (ModuleItem item : listCopy) {
                if (item.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    objects.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
