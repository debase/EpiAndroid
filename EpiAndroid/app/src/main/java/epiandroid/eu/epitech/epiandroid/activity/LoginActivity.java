package epiandroid.eu.epitech.epiandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechServiceResponseHandler;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText loginField;
    private EditText passwordField;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

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
            EpitechService.authanticate(login, password, new EpitechServiceResponseHandler() {
                @Override
                public void onSuccess(int statusCode, JSONObject jsonObject) {
                    try {
                        Toast.makeText(LoginActivity.this, "Authantification success token : " + jsonObject.getString("token"), Toast.LENGTH_LONG).show();
                        Intent epiAndroidIntent = new Intent(LoginActivity.this, EpiAndroidActivity.class);
                        startActivity(epiAndroidIntent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, JSONObject jsonObject) {
                    Toast.makeText(LoginActivity.this, "Authantification failed with error code " + statusCode, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
