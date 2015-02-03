package epiandroid.eu.epitech.epiandroid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.PlanningAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.PlanningItem;

public class PlanningFragment extends LoadingFragment implements View.OnClickListener {

    private ListView planningList;
    private TextView dateLabel;
    private TextView noEventLabel;
    private Calendar dateCal = Calendar.getInstance();
    private PlanningAdapter listAdapter;
    private Date currentDate = new Date();
    private String[] monthsNb = new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] months;
    private Activity mActivity = null;

    private GsonResponseHandler<PlanningItem[]> gsonResponseHandler = new GsonResponseHandler<PlanningItem[]>(PlanningItem[].class) {
        @Override
        public void onSuccess(PlanningItem[] planningItems) {
            if (mActivity == null)
                return;

            ArrayList<PlanningItem> contentList = new ArrayList<>();
            listAdapter = new PlanningAdapter(getActivity(), R.layout.adapter_planning, contentList);

            showLoading(false, null);
            showbaseView(true);
            if (planningItems != null) {
                for (PlanningItem planningItem : planningItems) {
                    if (planningItem.getRegistered() != null && planningItem.getRegistered().equals("registered")) {
                        listAdapter.add(planningItem);
                    }
                }
                if (listAdapter.getCount() == 0) {
                    noEventLabel.setVisibility(View.VISIBLE);
                }
                planningList.setAdapter(listAdapter);
            } else {
                noEventLabel.setVisibility(View.VISIBLE);
                listAdapter.clear();
                planningList.setAdapter(listAdapter);
            }
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_planning, container, false);
        this.planningList = (ListView) v.findViewById(R.id.planningList);
        this.dateLabel = (TextView) v.findViewById(R.id.planningDate);
        this.noEventLabel = (TextView) v.findViewById(R.id.planningNoEvent);
        this.noEventLabel.setVisibility(View.GONE);
        ImageButton prevBtn = (ImageButton) v.findViewById(R.id.btnPrevDay);
        ImageButton nextBtn = (ImageButton) v.findViewById(R.id.btnNextDay);
        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        dateCal.setTime(currentDate);
        this.dateLabel.setText(dateCal.get(Calendar.DAY_OF_MONTH) + " " + months[dateCal.get(Calendar.MONTH)] + " " + dateCal.get(Calendar.YEAR));

        setLoadingView(planningList, v.findViewById(R.id.layout_spinner_loading), v.findViewById(R.id.layout_spinner_error));
        v.findViewById(R.id.button_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showError(false, null);
                load();
            }
        });
        load();
        return v;
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
        load();
    }

    @Override
    public void load() {
        showbaseView(false);
        showLoading(true, getResources().getString(R.string.loading_data));
        this.dateLabel.setText(dateCal.get(Calendar.DAY_OF_MONTH) + " " + months[dateCal.get(Calendar.MONTH)] + " " + dateCal.get(Calendar.YEAR));
        RequestParams params = new RequestParams();
        String dateStr = dateCal.get(Calendar.YEAR) + "-" + monthsNb[dateCal.get(Calendar.MONTH)] + "-" + dateCal.get(Calendar.DAY_OF_MONTH);
        params.put("start", dateStr);
        params.put("end", dateStr);
        if (listAdapter != null) {
            listAdapter.clear();
            planningList.setAdapter(listAdapter);
        }
        this.noEventLabel.setVisibility(View.GONE);
        EpitechService.getRequest("planning", params, gsonResponseHandler);
    }
}
