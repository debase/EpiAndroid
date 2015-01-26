package epiandroid.eu.epitech.epiandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechServicePostResponseHandler;
import epiandroid.eu.epitech.epiandroid.preference.UserPreferenceHelper;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText loginField;
    private EditText passwordField;
    private Button loginButton;
    private boolean isUserLogedIn = false;
    private RelativeLayout loginSpinnerLayout = null;

    private EpitechServicePostResponseHandler epitechServicePostResponseHandler = new EpitechServicePostResponseHandler() {
        @Override
        public void onSuccess(int statusCode, JSONObject jsonObject) {
            try {
                Toast.makeText(LoginActivity.this, "Authantification success token : " + jsonObject.getString("token"), Toast.LENGTH_LONG).show();
                onFinishedAuthenticate(true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, JSONObject jsonObject) {
            Toast.makeText(LoginActivity.this, "Authantification failed with error code " + statusCode, Toast.LENGTH_LONG).show();
            onFinishedAuthenticate(true);
        }
     };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if user already loged in go to the home activity
        isUserLogedIn = UserPreferenceHelper.isUserLogedIn(this);
        if (isUserLogedIn) {
            String login = UserPreferenceHelper.getLogin(this);
            String passwd = UserPreferenceHelper.getPassword(this);

            // initialize the epitech service url api and token
            EpitechService.initialize("http://epitech-api.herokuapp.com/");

            setContentView(R.layout.login_spinner);

            loginSpinnerLayout = (RelativeLayout) findViewById(R.id.logging_layout_spinner);
            loginSpinnerLayout.setVisibility(View.VISIBLE);

            EpitechService.authenticate(login, passwd, epitechServicePostResponseHandler);
        } else {
            setContentView(R.layout.login_activity);

            // initialize the epitech service url api
            EpitechService.initialize("http://epitech-api.herokuapp.com/");

            loginSpinnerLayout = (RelativeLayout) findViewById(R.id.logging_layout_spinner);
            this.loginField = (EditText) findViewById(R.id.loginField);
            this.passwordField = (EditText) findViewById(R.id.passwordField);
            Button loginButton = (Button) findViewById(R.id.loginButton);
            loginButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginButton) {
            final String login = loginField.getText().toString();
            final String password = passwordField.getText().toString();

            if (login.length() == 0 || password.length() == 0)
                return;

            // get the layout login spinner and set to visible
            loginSpinnerLayout.setVisibility(View.VISIBLE);

            // launch authentification
            EpitechService.authenticate(login, password, epitechServicePostResponseHandler);
        }
    }

    // launch the home activity
    public void onFinishedAuthenticate(boolean result) {
        loginSpinnerLayout.setVisibility(View.GONE);

        if (!isUserLogedIn) {
            String login = loginField.getText().toString();
            String password = passwordField.getText().toString();

            // set user preference for futur launch
            UserPreferenceHelper.loginInfo(this, login, password);
            UserPreferenceHelper.setUserLogedIn(this, true);
        }

        if (result) {
            Intent epiAndroidIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(epiAndroidIntent);
            finish();
        } else {

        }
    }
}
