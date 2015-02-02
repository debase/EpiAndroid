package epiandroid.eu.epitech.epiandroid.epitech_service;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by debas on 20/01/15.
 */
public class EpitechService {
    private static String mToken = null;
    private static String BASE_URL;
    private static int TIME_OUT = 5 * 1000;

    private static AsyncHttpClient mClient = new AsyncHttpClient();

    public static void initialize(String urlApi) {
        BASE_URL = urlApi;
        mClient.setMaxRetriesAndTimeout(0, TIME_OUT);
    }

    public static void authenticate(String login, String password, final JsonHttpResponseHandler responseHandler) {
        final RequestParams requestParams = new RequestParams();
        requestParams.add("login", login);
        requestParams.add("password", password);
        mClient.post(BASE_URL + "login", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    mToken = response.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (responseHandler != null)
                    responseHandler.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                if (responseHandler != null)
                    responseHandler.onFailure(statusCode, headers, throwable, jsonObject);
            }
        });
    }

    public static void postRequest(String section, RequestParams requestParams, JsonHttpResponseHandler responseHandler) {
        if (mToken == null) {
            try {
                throw new EpitechServiceException("Your are not logged in [token invalid]");
            } catch (EpitechServiceException e) {
                e.printStackTrace();
            }
            return;
        }
        if (requestParams == null) {
            requestParams = new RequestParams();
        }
        requestParams.add("token", mToken);
        mClient.post(BASE_URL + section, requestParams, responseHandler);
    }

    public static void getRequest(String section, RequestParams requestParams, JsonHttpResponseHandler responseHandler) {
        if (mToken == null) {
            try {
                throw new EpitechServiceException("Your are not logged in [token invalid]");
            } catch (EpitechServiceException e) {
                e.printStackTrace();
            }
            return;
        }
        if (requestParams == null) {
            requestParams = new RequestParams();
        }
        requestParams.add("token", mToken);
        mClient.get(BASE_URL + section, requestParams, responseHandler);
    }
}
