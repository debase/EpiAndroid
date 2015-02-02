package epiandroid.eu.epitech.epiandroid.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import epiandroid.eu.epitech.epiandroid.R;


import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.model.PlanningItem;

/**
 * Created by remihillairet on 28/01/15.
 */
public class PlanningAdapter extends ArrayAdapter<PlanningItem> implements View.OnClickListener {

    private int resource;
    private PlanningItem planningItem;

    private JsonHttpResponseHandler requestHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Log.i("Token Validation Success", response.toString());
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Log.i("Token Validation Error", responseString);
        }
    };

    public PlanningAdapter(Context context, int resource, List<PlanningItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(this.resource, null);
        }

        PlanningItem planningItem = getItem(position);

        if (planningItem != null) {
            TextView titleLabel = (TextView) v.findViewById(R.id.adapterPlanningTitle);
            TextView scheduleLabel = (TextView) v.findViewById(R.id.adapterPlanningSchedule);
            Button btnToken = (Button) v.findViewById(R.id.btnToken);

            if (titleLabel != null) {
                titleLabel.setText(planningItem.getTitle());
            }

            if (scheduleLabel != null) {
                scheduleLabel.setText(planningItem.getSchedule());
            }

            if (btnToken != null) {
                btnToken.setOnClickListener(this);
            }

            this.planningItem = planningItem;
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        final PlanningItem tokenPlanningItem = this.planningItem;
        new AlertDialog.Builder(getContext())
                .setTitle(getContext().getResources().getString(R.string.enter_token))
                .setView(input)
                .setPositiveButton(getContext().getResources().getString(R.string.validate), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String token = input.getText().toString();

                        RequestParams params = new RequestParams();
                        params.put("scolaryear", tokenPlanningItem.getScolaryear());
                        params.put("codemodule", tokenPlanningItem.getCodemodule());
                        params.put("codeinstance", tokenPlanningItem.getCodeinstance());
                        params.put("codeacti", tokenPlanningItem.getCodeacti());
                        params.put("codeevent", tokenPlanningItem.getCodeevent());
                        params.put("tokenvalidationcode", token);

                        EpitechService.postRequest("token", params, requestHandler);
                    }
                })
                .setNegativeButton(getContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }).show();
    }
}
