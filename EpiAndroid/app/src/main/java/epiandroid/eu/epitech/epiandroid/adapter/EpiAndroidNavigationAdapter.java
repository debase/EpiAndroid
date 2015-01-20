package epiandroid.eu.epitech.epiandroid.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by debas on 20/01/15.
 */
public class EpiAndroidNavigationAdapter extends BaseAdapter {

    private List<View> mItemView;

    public EpiAndroidNavigationAdapter(List<View> itemView) {
        mItemView = itemView;
    }

    @Override
    public int getCount() {
        return mItemView.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemView.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return  mItemView.get(position);
    }
}
