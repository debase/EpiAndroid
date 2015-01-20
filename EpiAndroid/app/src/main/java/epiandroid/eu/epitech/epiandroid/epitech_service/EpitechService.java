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

    private static AsyncHttpClient mClient = new AsyncHttpClient();

    public static void initialize(String urlApi) {
        BASE_URL = urlApi;
    }

    public static void authanticate(String login, String password, final EpitechServiceResponseHandler responseHandler) {
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
                responseHandler.onSuccess(statusCode, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                responseHandler.onFailure(statusCode, jsonObject);
            }
        });
    }

    public static void postRequest(String section, RequestParams requestParams, JsonHttpResponseHandler responseHandler) throws EpitechServiceException {
        if (mToken == null) {
            throw new EpitechServiceException("Your are not loged in [token invalid]");
        }
        mClient.post(BASE_URL + section, requestParams, responseHandler);
    }
}
