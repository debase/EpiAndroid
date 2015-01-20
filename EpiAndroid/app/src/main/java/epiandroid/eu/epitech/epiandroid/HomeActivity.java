package epiandroid.eu.epitech.epiandroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class HomeActivity extends ActionBarActivity {

    private TextView nameLabel;
    private TextView loginLabel;
    private TextView emailLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nameLabel = (TextView)findViewById(R.id.home_name);
        loginLabel = (TextView)findViewById(R.id.home_login);
        emailLabel = (TextView)findViewById(R.id.home_email);

        Ion.with(this).load("http://epitech-api.herokuapp.com/infos")
                .setBodyParameter("token", this.getIntent().getStringExtra("token"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        String name;
                        String login;
                        String internalEmail;
                        try {
                            name = result.get("infos").getAsJsonObject().get("title").getAsString();
                            login = result.get("infos").getAsJsonObject().get("login").getAsString();
                            internalEmail = result.get("infos").getAsJsonObject().get("internal_email").getAsString();
                        } catch (Exception errorExp) {
                            JsonObject error = result.get("error").getAsJsonObject();
                            System.out.println("Error : " + error.toString());
                            return ;
                        }
                        nameLabel.setText(name, TextView.BufferType.NORMAL);
                        loginLabel.setText(login, TextView.BufferType.NORMAL);
                        emailLabel.setText(internalEmail, TextView.BufferType.NORMAL);
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
}
