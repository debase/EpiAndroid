package epiandroid.eu.epitech.epiandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import epiandroid.eu.epitech.epiandroid.R;
import navigation_drawer.NavigationDrawerItem;

/**
 * Created by debas on 20/01/15.
 */
public class EpiAndroidNavigationAdapter extends ArrayAdapter<NavigationDrawerItem> {

    public EpiAndroidNavigationAdapter(Context context, List<NavigationDrawerItem> itemView) {
        super(context, 0, itemView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavigationDrawerItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.navdrawer_section, parent, false);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.icon_section);
        TextView text = (TextView) convertView.findViewById(R.id.text_section);
        image.setImageResource(item.getSectionIcon());
        text.setText(item.getSectionString());

        return convertView;
    }
}
