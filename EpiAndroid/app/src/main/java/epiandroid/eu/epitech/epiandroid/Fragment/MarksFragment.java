package epiandroid.eu.epitech.epiandroid.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.MarksViewAdapter;
import epiandroid.eu.epitech.epiandroid.model.MarksItem;


public class MarksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        view =inflater.inflate(R.layout.fragment_marks, container, false);
        ArrayList<MarksItem> list = new ArrayList<MarksItem>();

        list.add(new MarksItem("Bomberman", "21"));
        list.add(new MarksItem("Zappy", "38"));
        list.add(new MarksItem("42sh", "26"));
        list.add(new MarksItem("Zizou", "10"));

        MarksViewAdapter adapter = new MarksViewAdapter(getActivity(), R.layout.mark_item, list);
        ListView listView = (ListView) view.findViewById(R.id.mark_list);
        listView.setAdapter(adapter);

        return view;
    }

}
