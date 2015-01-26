package epiandroid.eu.epitech.epiandroid.epitech_service;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by debas on 26/01/15.
 */
public abstract class EpitechServiceGetResponseHandler extends JsonHttpResponseHandler {
    @Override
    public final void onSuccess(int statusCode, Header[] headers, JSONArray jsonResponse) {
        onSuccess(statusCode, jsonResponse);
    }

    @Override
    public final void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonErrorResponse) {
        onFailure(statusCode, jsonErrorResponse);
    }

    public abstract void onSuccess(int statusCode, JSONArray jsonArray);
    public abstract void onFailure(int statusCode, JSONObject jsonArray);
}
