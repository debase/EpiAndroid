package epiandroid.eu.epitech.epiandroid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.MarksViewAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.MarkModel;
import epiandroid.eu.epitech.epiandroid.model.MarksItem;


public class MarksFragment extends LoadingFragment {

    private View view;
    private ListView mMarkListView;
    private Activity mActivity = null;

    private GsonResponseHandler<MarkModel> gsonResponseHandler = new GsonResponseHandler<MarkModel>(MarkModel.class) {

        @Override
        public void onSuccess(MarkModel markModel) {
            if (mActivity == null)
                return;

            MarksViewAdapter adapter = new MarksViewAdapter(mActivity, R.layout.mark_item, new ArrayList<>(Arrays.asList(markModel.getMarksItem())));
            mMarkListView.setAdapter(adapter);

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

    private JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {
            super.onSuccess(statusCode, headers, responseString);

            try {
                JSONArray jsonArray = responseString.getJSONArray("notes");
                Gson gson = new Gson();
                MarksItem[] marksItems = gson.fromJson(jsonArray.toString(), MarksItem[].class);

                MarksViewAdapter adapter = new MarksViewAdapter(getActivity(), R.layout.mark_item, new ArrayList<MarksItem>(Arrays.asList(marksItems)));
                ListView listView = (ListView) view.findViewById(R.id.mark_list);
                listView.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_marks, container, false);
        return view;
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
