package epiandroid.eu.epitech.epiandroid.fragment;

import android.os.Bundle;
import android.app.Fragment;
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
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
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
        }
    };

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EpitechService.getRequest("marks", null, gsonResponseHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_marks, container, false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }
}
