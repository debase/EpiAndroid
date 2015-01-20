package epiandroid.eu.epitech.epiandroid.epitech_service;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by debas on 20/01/15.
 */
public abstract class EpitechServiceResponseHandler extends JsonHttpResponseHandler {
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResponse) {
        onSuccess(statusCode, jsonResponse);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonErrorResponse) {
        onFailure(statusCode, jsonErrorResponse);
    }

    public abstract void onSuccess(int statusCode, JSONObject jsonObject);
    public abstract void onFailure(int statusCode, JSONObject jsonObject);
}
