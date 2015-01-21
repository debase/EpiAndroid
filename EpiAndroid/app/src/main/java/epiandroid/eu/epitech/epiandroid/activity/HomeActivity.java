package epiandroid.eu.epitech.epiandroid.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.EpiAndroidNavigationAdapter;

/**
 * Created by debas on 20/01/15.
 */
public class HomeActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private BaseAdapter navigationDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);



        initView();
        if (toolbar != null) {
            toolbar.setTitle("Navigation Drawer");
            setSupportActionBar(toolbar);
        }
        initDrawer();

//        Ion.with(this).load("http://epitech-api.herokuapp.com/infos")
//                .setBodyParameter("token", this.getIntent().getStringExtra("token"))
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        String name;
//                        String login;
//                        String internalEmail;
//                        try {
//                            name = result.get("infos").getAsJsonObject().get("title").getAsString();
//                            login = result.get("infos").getAsJsonObject().get("login").getAsString();
//                            internalEmail = result.get("infos").getAsJsonObject().get("internal_email").getAsString();
//                        } catch (Exception errorExp) {
//                            JsonObject error = result.get("error").getAsJsonObject();
//                            System.out.println("Error : " + error.toString());
//                            return ;
//                        }
//                        nameLabel.setText(name, TextView.BufferType.NORMAL);
//                        loginLabel.setText(login, TextView.BufferType.NORMAL);
//                        emailLabel.setText(internalEmail, TextView.BufferType.NORMAL);
//                    }
//                });

    }

    private void initView() {
            leftDrawerList = (ListView) findViewById(R.id.left_drawer);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);


            navigationDrawerAdapter = new EpiAndroidNavigationAdapter(new ArrayList<View>());
            leftDrawerList.setAdapter(navigationDrawerAdapter);
    }

    private void initDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}