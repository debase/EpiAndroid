package epiandroid.eu.epitech.epiandroid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.activity.HomeActivity;
import epiandroid.eu.epitech.epiandroid.adapter.MarksViewAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.MarkModel;
import epiandroid.eu.epitech.epiandroid.model.MarksItem;


public class MarksFragment extends LoadingFragment {

    private View view;
    private ListView mMarkListView;
    private Activity mActivity = null;
    private String mCurrentSearch = null;
    private MarksViewAdapter mMarksViewAdapter = null;

    private GsonResponseHandler<MarkModel> gsonResponseHandler = new GsonResponseHandler<MarkModel>(MarkModel.class) {

        @Override
        public void onSuccess(MarkModel markModel) {
            if (mActivity == null)
                return;

            mMarksViewAdapter = new MarksViewAdapter(mActivity, R.layout.mark_item, new ArrayList<MarksItem>(Arrays.asList(markModel.getMarksItem())));
            ListView listView = (ListView) view.findViewById(R.id.mark_list);
            listView.setAdapter(mMarksViewAdapter);
            if (mCurrentSearch != null) {
                mMarksViewAdapter.filter(mCurrentSearch);
            }
            showLoading(false, null);
            showbaseView(true);
        }

        @Override
        public void onFailure(Throwable throwable, JSONObject errorResponse) {
            if (mActivity == null)
                return;

            showLoading(false, null);
            showError(true, getResources().getString(R.string.error_fetching_data));
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EpitechService.getRequest("marks", null, gsonResponseHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_marks, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        if (mActivity == null)
            return;

        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = new SearchView(((HomeActivity) mActivity).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mCurrentSearch = query;
                if (mMarksViewAdapter != null) {
                    mMarksViewAdapter.filter(mCurrentSearch);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCurrentSearch = newText;
                if (mMarksViewAdapter != null) {
                    mMarksViewAdapter.filter(mCurrentSearch);
                }
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMarkListView = (ListView) view.findViewById(R.id.mark_list);
        setLoadingView(mMarkListView, view.findViewById(R.id.layout_spinner_loading), view.findViewById(R.id.layout_spinner_error));
        view.findViewById(R.id.button_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showError(false, null);
                load();
            }
        });
        load();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    @Override
    public void load() {
        showbaseView(false);
        showLoading(true, getResources().getString(R.string.loading_data));
        EpitechService.getRequest("marks", null, gsonResponseHandler);
    }
}
