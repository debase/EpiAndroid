package epiandroid.eu.epitech.epiandroid.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by debas on 20/01/15.
 */
public class EpiAndroidNavigationAdapter extends BaseAdapter {
    private List<View> mListView;

    public EpiAndroidNavigationAdapter(List<View> itemView) {
        mListView = itemView;
    }

    @Override
    public int getCount() {
        return mListView.size();
    }

    @Override
    public Object getItem(int position) {
        return mListView.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return mListView.get(position);
    }
}
