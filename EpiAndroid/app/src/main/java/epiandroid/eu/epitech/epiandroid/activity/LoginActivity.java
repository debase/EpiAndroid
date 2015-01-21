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
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechServiceResponseHandler;
import epiandroid.eu.epitech.epiandroid.preference.UserPreference;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText loginField;
    private EditText passwordField;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if user already loged in go to the home activity
        boolean isUserLogedIn = UserPreference.isUserLogedIn(this);
        if (isUserLogedIn) {
            String token = UserPreference.getUserToken(this);

            // initialize the epitech service url api and token
            EpitechService.initialize("http://epitech-api.herokuapp.com/", token);
            lauchHomeActivity();
        }

        setContentView(R.layout.login_activity);

        // initialize the epitech service url api
        EpitechService.initialize("http://epitech-api.herokuapp.com/");

        this.loginField = (EditText)findViewById(R.id.loginField);
        this.passwordField = (EditText)findViewById(R.id.passwordField);
        this.loginButton = (Button)findViewById(R.id.loginButton);
        this.loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginButton) {
            String login = loginField.getText().toString();
            String password = passwordField.getText().toString();

            if (login.length() == 0 || password.length() == 0)
                return;

            // get the layout login spinner and set to visible
            final RelativeLayout loginSpinnerLayout = (RelativeLayout) findViewById(R.id.logging_layout_spinner);
            loginSpinnerLayout.setVisibility(View.VISIBLE);

            // launch authentification
            EpitechService.authanticate(login, password, new EpitechServiceResponseHandler() {
                @Override
                public void onSuccess(int statusCode, JSONObject jsonObject) {
                    try {
                        Toast.makeText(LoginActivity.this, "Authantification success token : " + jsonObject.getString("token"), Toast.LENGTH_LONG).show();
                        String token = EpitechService.getToken();

                        // set user preference for futur launch
                        UserPreference.setUserToken(LoginActivity.this, token);
                        UserPreference.setUserLogedIn(LoginActivity.this, true);
                        loginSpinnerLayout.setVisibility(View.GONE);
                        lauchHomeActivity();
                    } catch (JSONException e) {
                        loginSpinnerLayout.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, JSONObject jsonObject) {
                    Toast.makeText(LoginActivity.this, "Authantification failed with error code " + statusCode, Toast.LENGTH_LONG).show();
                    loginSpinnerLayout.setVisibility(View.GONE);
                }
            });
        }
    }

    // launch the home activity
    public void lauchHomeActivity() {
        Intent epiAndroidIntent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(epiAndroidIntent);
        finish();
    }
}
