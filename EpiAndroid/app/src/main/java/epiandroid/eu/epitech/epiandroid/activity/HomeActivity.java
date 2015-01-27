package epiandroid.eu.epitech.epiandroid.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import epiandroid.eu.epitech.epiandroid.CircleTransform;
import epiandroid.eu.epitech.epiandroid.Fragment.MarksFragment;
import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.EpiAndroidNavigationAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.preference.UserPreferenceHelper;
import epiandroid.eu.epitech.epiandroid.utils.Utils;

/**
 * Created by debas on 20/01/15.
 */
public class HomeActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView listDrawer;
    private ImageView mPictureView;
    private TextView mLogin, mMail;
    private BaseAdapter navigationDrawerAdapter;
    private List<View> mNavigationArray = new ArrayList<>();
    private View currentSectionSelected = null;
    private int textSectionColor = 0;

    private JsonHttpResponseHandler mEpitechServicePostResponseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                JSONObject infos = response.getJSONObject("infos");
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

    public View builSectionView(int iconRes, int stringRes) {
        View view = LayoutInflater.from(this).inflate(R.layout.navdrawer_section, listDrawer, false);
        ImageView image = (ImageView) view.findViewById(R.id.icon_section);
        TextView text = (TextView) view.findViewById(R.id.text_section);
        image.setImageResource(iconRes);
        text.setText(stringRes);

        return view;
    }

    private void initView() {
        listDrawer = (ListView) findViewById(R.id.navdrawer_listview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        EpitechService.postRequest("infos", null, mEpitechServicePostResponseHandler);

        /* set the list for navdrawer */
        mNavigationArray.add(builSectionView(R.drawable.home, R.string.home));
        mNavigationArray.add(builSectionView(R.drawable.marks, R.string.marks));
        mNavigationArray.add(builSectionView(R.drawable.calendar, R.string.calendar));

        navigationDrawerAdapter = new EpiAndroidNavigationAdapter(mNavigationArray);
        listDrawer.setAdapter(navigationDrawerAdapter);

        mPictureView = (ImageView) findViewById(R.id.profile_image);
        mLogin = (TextView) findViewById(R.id.login_textview);
        mMail = (TextView) findViewById(R.id.mail_textview);

        listDrawer.setOnItemClickListener(this);

        changeSelection(mNavigationArray.get(1), 1);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        changeSelection(view, position);
        navigationDrawerAdapter.notifyDataSetChanged();
    }

    public void changeSelection(View view, int position) {
        ImageView imageView = (ImageView) view.findViewById(R.id.icon_section);
        TextView textView = (TextView) view.findViewById(R.id.text_section);

        if (view == currentSectionSelected) {
            return;
        }

        if (currentSectionSelected != null) {
            ImageView oldImageView = (ImageView) currentSectionSelected.findViewById(R.id.icon_section);
            TextView oldTextView = (TextView) currentSectionSelected.findViewById(R.id.text_section);
            oldImageView.clearColorFilter();
            oldTextView.setTextColor(textSectionColor);
        } else {
            textSectionColor = textView.getCurrentTextColor();
        }
        imageView.setColorFilter(getResources().getColor(R.color.primaryColor));
        textView.setTextColor(getResources().getColor(R.color.primaryColor));
        currentSectionSelected = view;

        Fragment fragment = null;
        /* change fragment */
        switch (position) {
            case 0:
                break;
            case 1:
                fragment = new MarksFragment();
                break;
            case 2:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
        drawerLayout.closeDrawers();
    }

    public void logout(View view) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.logout)
                .setMessage(R.string.really_logout)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Stop the activity
                        Utils.makeText(HomeActivity.this, "Logout !");
                        UserPreferenceHelper.logout(HomeActivity.this);
                        HomeActivity.this.finish();
                    }

                })
                .setNegativeButton(R.string.no, null)
                .show();
            }
}