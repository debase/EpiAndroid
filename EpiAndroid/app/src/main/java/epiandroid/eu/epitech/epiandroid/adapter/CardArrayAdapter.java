package epiandroid.eu.epitech.epiandroid.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import epiandroid.eu.epitech.epiandroid.CircleTransform;
import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.model.InfoModel.HistoryItem;


/**
 * Created by debas on 30/01/15.
 */
public class CardArrayAdapter extends ArrayAdapter<HistoryItem> {

    private static final String TAG = "HistoryItemArrayAdapter";
    private int mRessources;

    public CardArrayAdapter(Context context, int resource, HistoryItem[] objects) {
        super(context, resource, objects);
        mRessources = resource;
    }

    static class HistoryItemViewHolder {
        TextView title;
        TextView content;
        TextView userName;
        TextView historyDate;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HistoryItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(mRessources, parent, false);
            viewHolder = new HistoryItemViewHolder();
            viewHolder.title = (TextView) row.findViewById(R.id.title);
            viewHolder.content = (TextView) row.findViewById(R.id.content);
            viewHolder.userName = (TextView) row.findViewById(R.id.user_name);
            viewHolder.historyDate = (TextView) row.findViewById(R.id.history_date);
            viewHolder.imageView = (ImageView) row.findViewById(R.id.history_image);
            row.setTag(viewHolder);
        } else {
            viewHolder = (HistoryItemViewHolder)row.getTag();
        }
        HistoryItem card = getItem(position);
        viewHolder.title.setText(Html.fromHtml(card.title));
        viewHolder.content.setText(Html.fromHtml(card.content));
        viewHolder.userName.setText(card.user.title);
        viewHolder.historyDate.setText(card.historyDate);

        if (card.user.picture != null) {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(card.user.picture)
                    .transform(new CircleTransform())
                    .error(R.drawable.person_image_empty)
                    .into(viewHolder.imageView);
        } else {
            viewHolder.imageView.setVisibility(View.GONE);
        }
        return row;
    }
}
