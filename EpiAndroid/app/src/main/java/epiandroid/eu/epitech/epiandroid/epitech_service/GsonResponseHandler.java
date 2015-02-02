package epiandroid.eu.epitech.epiandroid.epitech_service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by linard_f on 1/28/15.
 */
public abstract class GsonResponseHandler<Model> extends JsonHttpResponseHandler {

    private final Class<Model> type;

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);

        onSuccess(getModelFromJson(response.toString()));
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);

        onSuccess(getModelFromJson(response.toString()));
    }

    public GsonResponseHandler(Class<Model> type) {
        this.type = type;
    }

    private Model getModelFromJson(String json) {
        Gson gson = new Gson();

        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            return null;
        }
    }

    public abstract void onSuccess(Model model);
}
