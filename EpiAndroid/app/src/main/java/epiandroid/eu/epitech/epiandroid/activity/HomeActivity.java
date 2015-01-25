package epiandroid.eu.epitech.epiandroid.activity;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import epiandroid.eu.epitech.epiandroid.CircleTransform;
import epiandroid.eu.epitech.epiandroid.Fragment.MarksFragment;
import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.EpiAndroidNavigationAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechServiceResponseHandler;
import navigation_drawer.NavigationDrawerItem;

/**
 * Created by debas on 20/01/15.
 */
public class HomeActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private ImageView mPictureView;
    private TextView mLogin, mMail;
    private BaseAdapter navigationDrawerAdapter;
    private List<NavigationDrawerItem> mNavigationArray = new ArrayList<>();

    private EpitechServiceResponseHandler mEpitechServiceResponseHandler = new EpitechServiceResponseHandler() {
        @Override
        public void onSuccess(int statusCode, JSONObject jsonObject) {
            try {
                JSONObject infos = jsonObject.getJSONObject("infos");
                String urlProfilPicture = "https://cdn.local.epitech.eu/userprofil/" + infos.getString("picture");
                mLogin.setText(infos.getString("title"));
                mMail.setText(infos.getString("internal_email"));
                Picasso.with(HomeActivity.this)
                        .load(urlProfilPicture)
                        .transform(new CircleTransform())
                        .error(R.drawable.person_image_empty)
                        .into(mPictureView);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, JSONObject jsonObject) {

        }
    };

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

    }

    private void initView() {
        leftDrawerList = (ListView) findViewById(R.id.navdrawer_listview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);


        mNavigationArray.add(new NavigationDrawerItem(R.drawable.marks, getResources().getString(R.string.marks)));
        navigationDrawerAdapter = new EpiAndroidNavigationAdapter(this, mNavigationArray);
        leftDrawerList.setAdapter(navigationDrawerAdapter);

        mPictureView = (ImageView) findViewById(R.id.profile_image);
        mLogin = (TextView) findViewById(R.id.login_textview);
        mMail = (TextView) findViewById(R.id.mail_textview);

        EpitechService.postRequest("infos", null, mEpitechServiceResponseHandler);

        //Fragment provisoire
        Fragment marksFragment = new MarksFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, marksFragment).commit();


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