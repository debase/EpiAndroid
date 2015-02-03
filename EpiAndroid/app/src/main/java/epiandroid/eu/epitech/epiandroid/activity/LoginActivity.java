package epiandroid.eu.epitech.epiandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.preference.UserPreferenceHelper;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText loginField;
    private EditText passwordField;
    private Button loginButton;
    private boolean isUserLogedIn = false;
    private RelativeLayout loginSpinnerLayout = null;
    private RelativeLayout errorLayout = null;


    private JsonHttpResponseHandler epitechServicePostResponseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            onFinishedAuthenticate(true);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            onFinishedAuthenticate(false);

            if (isUserLogedIn) {
                notLogedIn();
            }
            loginSpinnerLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if user already loged in go to the home activity
        isUserLogedIn = UserPreferenceHelper.isUserLogedIn(this);
        if (isUserLogedIn) {
            alreadyLogedIn();
        } else {
            notLogedIn();
        }
    }

    public void alreadyLogedIn() {
        String login = UserPreferenceHelper.getLogin(this);
        String passwd = UserPreferenceHelper.getPassword(this);

        // initialize the epitech service url api and token
        EpitechService.initialize("http://epitech-api.herokuapp.com/");

        setContentView(R.layout.loading_spinner);

        TextView textView = (TextView) findViewById(R.id.text_view_spinner_loading);
        textView.setText(getResources().getString(R.string.loging_you_in));
        
        loginSpinnerLayout = (RelativeLayout) findViewById(R.id.layout_spinner_loading);
        loginSpinnerLayout.setVisibility(View.VISIBLE);

        EpitechService.authenticate(login, passwd, epitechServicePostResponseHandler);
    }

    public void notLogedIn() {
        setContentView(R.layout.login_activity);

        // initialize the epitech service url api
        EpitechService.initialize("http://epitech-api.herokuapp.com/");

        loginSpinnerLayout = (RelativeLayout) findViewById(R.id.layout_spinner_loading);
        errorLayout = (RelativeLayout) findViewById(R.id.layout_spinner_error_login);

        this.loginField = (EditText)findViewById(R.id.loginField);
        this.passwordField = (EditText)findViewById(R.id.passwordField);
        this.loginButton = (Button)findViewById(R.id.loginButton);
        this.loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginButton) {
            final String login = loginField.getText().toString();
            final String password = passwordField.getText().toString();

            if (login.length() == 0 || password.length() == 0)
                return;

            // error layout and set to gone
            errorLayout.setVisibility(View.GONE);

            // get the layout login spinner and set to visible
            loginSpinnerLayout.setVisibility(View.VISIBLE);
            TextView textView = (TextView) loginSpinnerLayout.findViewById(R.id.text_view_spinner_loading);
            textView.setText(getResources().getString(R.string.loging_you_in));

            // launch authentication
            EpitechService.authenticate(login, password, epitechServicePostResponseHandler);
        }
    }

    // launch the home activity
    public void onFinishedAuthenticate(boolean result) {
        loginSpinnerLayout.setVisibility(View.GONE);

        if (!isUserLogedIn) {
            errorLayout.setVisibility(View.GONE);

            String login = loginField.getText().toString();
            String password = passwordField.getText().toString();

            // set user preference for future launch
            UserPreferenceHelper.loginInfo(this, login, password);
        }

        if (result) {
            Intent epiAndroidIntent = new Intent(LoginActivity.this, HomeActivity.class);
            epiAndroidIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(epiAndroidIntent, 42);
        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 42)
        {
            if (UserPreferenceHelper.isUserLogedIn(this)) {
                finish();
            } else {
                notLogedIn();
            }
        }
    }
}
