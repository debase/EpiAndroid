package epiandroid.eu.epitech.epiandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.PlanningAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.PlanningItem;

public class PlanningFragment extends Fragment implements View.OnClickListener {

    private ListView planningList;
    private TextView dateLabel;
    private Calendar dateCal = Calendar.getInstance();
    private PlanningAdapter listAdapter;
    private Date currentDate = new Date();
    private String[] monthsNb = new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] months;

    private GsonResponseHandler<PlanningItem[]> gsonResponseHandler = new GsonResponseHandler<PlanningItem[]>(PlanningItem[].class) {
        @Override
        public void onSuccess(PlanningItem[] planningItems) {
            ArrayList<PlanningItem> contentList = new ArrayList<>();
            listAdapter = new PlanningAdapter(getActivity(), R.layout.adapter_planning, contentList);

            if (planningItems != null) {
                for (PlanningItem planningItem : planningItems) {
                    if (planningItem.getRegistered() != null && planningItem.getRegistered().equals("registered")) {
                        listAdapter.add(planningItem);
                    }
                }
                planningList.setAdapter(listAdapter);
            } else {
                if (listAdapter != null) {
                    listAdapter.clear();
                    planningList.setAdapter(listAdapter);
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };

    public PlanningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        months = new String[] {getResources().getString(R.string.january),
                getResources().getString(R.string.february),
                getResources().getString(R.string.march),
                getResources().getString(R.string.april),
                getResources().getString(R.string.may),
                getResources().getString(R.string.june),
                getResources().getString(R.string.july),
                getResources().getString(R.string.august),
                getResources().getString(R.string.september),
                getResources().getString(R.string.october),
                getResources().getString(R.string.november),
                getResources().getString(R.string.december)};

        RequestParams params = new RequestParams();
        String dateStr = dateCal.get(Calendar.YEAR) + "-" + monthsNb[dateCal.get(Calendar.MONTH)] + "-" + dateCal.get(Calendar.DAY_OF_MONTH);
        params.put("start", dateStr);
        params.put("end", dateStr);
        EpitechService.getRequest("planning", params, gsonResponseHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_planning, container, false);
        this.planningList = (ListView) V.findViewById(R.id.planningList);
        this.dateLabel = (TextView) V.findViewById(R.id.planningDate);
        ImageButton prevBtn = (ImageButton) V.findViewById(R.id.btnPrevDay);
        ImageButton nextBtn = (ImageButton) V.findViewById(R.id.btnNextDay);
        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        dateCal.setTime(currentDate);
        this.dateLabel.setText(dateCal.get(Calendar.DAY_OF_MONTH) + " " + months[dateCal.get(Calendar.MONTH)] + " " + dateCal.get(Calendar.YEAR));
        return V;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.planningList = null;
        this.dateLabel = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNextDay) {
            dateCal.add(Calendar.DATE, 1);
        } else if (v.getId() == R.id.btnPrevDay) {
            dateCal.add(Calendar.DATE, -1);
        }
        this.dateLabel.setText(dateCal.get(Calendar.DAY_OF_MONTH) + " " + months[dateCal.get(Calendar.MONTH)] + " " + dateCal.get(Calendar.YEAR));
        RequestParams params = new RequestParams();
        String dateStr = dateCal.get(Calendar.YEAR) + "-" + monthsNb[dateCal.get(Calendar.MONTH)] + "-" + dateCal.get(Calendar.DAY_OF_MONTH);
        params.put("start", dateStr);
        params.put("end", dateStr);
        if (listAdapter != null) {
            listAdapter.clear();
            planningList.setAdapter(listAdapter);
        }
        EpitechService.getRequest("planning", params, gsonResponseHandler);
    }
}