package epiandroid.eu.epitech.epiandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.MarksViewAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.MarkModel;
import epiandroid.eu.epitech.epiandroid.model.MarksItem;


public class MarksFragment extends Fragment {

    private View view;

    private GsonResponseHandler<MarkModel> gsonResponseHandler = new GsonResponseHandler<MarkModel>(MarkModel.class) {

        @Override
        public void onSuccess(MarkModel markModel) {
            MarksViewAdapter adapter = new MarksViewAdapter(getActivity(), R.layout.mark_item, new ArrayList<MarksItem>(Arrays.asList(markModel.getMarksItem())));
            ListView listView = (ListView) view.findViewById(R.id.mark_list);
            listView.setAdapter(adapter);
            setTextListenerFromActionBar(adapter);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filterFeature();
        EpitechService.getRequest("marks", null, gsonResponseHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_marks, container, false);
        return view;
    }

    private void filterFeature() {
        ActionBarActivity activity = (ActionBarActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();

        actionBar.setCustomView(R.layout.filter_toolbar);
        actionBar.setTitle(getResources().getString(R.string.marks));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }

    private void setTextListenerFromActionBar(final MarksViewAdapter adapter) {
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        final SearchView searchView = (SearchView) actionBar.getCustomView().findViewById(R.id.filter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        
    }
}
