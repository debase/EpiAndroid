package epiandroid.eu.epitech.epiandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText loginField;
    private EditText passwordField;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.loginField = (EditText)findViewById(R.id.loginField);
        this.passwordField = (EditText)findViewById(R.id.passwordField);
        this.loginButton = (Button)findViewById(R.id.loginButton);
        this.loginButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginButton) {
            Ion.with(this).load("http://epitech-api.herokuapp.com/login")
                    .setBodyParameter("login", loginField.getText().toString())
                    .setBodyParameter("password", passwordField.getText().toString())
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            JSONObject objectResult = null;
                            try {
                                objectResult = new JSONObject(result);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                String token = objectResult.getString("token");
                                System.out.println("Token : " + token);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }
    }
}
