package epiandroid.eu.epitech.epiandroid.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import epiandroid.eu.epitech.epiandroid.CircleTransform;
import epiandroid.eu.epitech.epiandroid.R;
import epiandroid.eu.epitech.epiandroid.adapter.EpiAndroidNavigationAdapter;
import epiandroid.eu.epitech.epiandroid.epitech_service.EpitechService;
import epiandroid.eu.epitech.epiandroid.epitech_service.GsonResponseHandler;
import epiandroid.eu.epitech.epiandroid.fragment.DashboardFragment;
import epiandroid.eu.epitech.epiandroid.fragment.MarksFragment;
import epiandroid.eu.epitech.epiandroid.fragment.PlanningFragment;
import epiandroid.eu.epitech.epiandroid.model.InfoModel;
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
    private int tabIndex = 0;
    private InfoModel mInfoModel = null;

    private GsonResponseHandler<InfoModel> mInfoItemGsonResponseHandler = new GsonResponseHandler<InfoModel>(InfoModel.class) {
        @Override
        public void onSuccess(InfoModel infoItem) {
            mInfoModel = infoItem;
            setUpInfoNav(infoItem);
        }
    };

    private void setUpInfoNav(InfoModel infoItem) {
        String urlProfilPicture = "https://cdn.local.epitech.eu/userprofil/profilview/" + infoItem.infos.login + ".jpg";
        mLogin.setText(infoItem.infos.title);
        mMail.setText(infoItem.infos.mail);
        Picasso.with(HomeActivity.this)
                .load(urlProfilPicture)
                .transform(new CircleTransform())
                .error(R.drawable.person_image_empty)
                .into(mPictureView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        initView(savedInstanceState);
        if (toolbar != null) {
            toolbar.setTitle("Dashboard");
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

    private void initView(Bundle saveInstanceState) {
        listDrawer = (ListView) findViewById(R.id.navdrawer_listview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        /* set the list for navdrawer */
        mNavigationArray.add(builSectionView(R.drawable.home, R.string.home));
        mNavigationArray.add(builSectionView(R.drawable.marks, R.string.marks));
        mNavigationArray.add(builSectionView(R.drawable.calendar, R.string.calendar));

        navigationDrawerAdapter = new EpiAndroidNavigationAdapter(mNavigationArray);
        listDrawer.setAdapter(navigationDrawerAdapter);

        /* get ui element */
        mPictureView = (ImageView) findViewById(R.id.profile_image);
        mLogin = (TextView) findViewById(R.id.login_textview);
        mMail = (TextView) findViewById(R.id.mail_textview);

        listDrawer.setOnItemClickListener(this);

        if (saveInstanceState == null) {
            EpitechService.postRequest("infos", null, mInfoItemGsonResponseHandler);
        } else {
            mInfoModel = (InfoModel) saveInstanceState.getSerializable("info");
            tabIndex = saveInstanceState.getInt("tab_index");
            Utils.makeText(this, "Not null : " + mInfoModel.infos.mail);
            setUpInfoNav(mInfoModel);
        }

        changeSelection(mNavigationArray.get(tabIndex), tabIndex);
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
        drawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.primaryColorDark));
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

        tabIndex = position;
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

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        /* change fragment */
        switch (position) {
            case 0:
                if ((fragment = fragmentManager.findFragmentByTag(DashboardFragment.class.getSimpleName())) == null) {
                    fragment = new DashboardFragment();
                }
                toolbar.setTitle(getResources().getString(R.string.dashboard));
                break;
            case 1:
                if ((fragment = fragmentManager.findFragmentByTag(MarksFragment.class.getSimpleName())) == null) {
                    fragment = new MarksFragment();
                }
                toolbar.setTitle(getResources().getString(R.string.marks));
                break;
            case 2:
                if ((fragment = fragmentManager.findFragmentByTag(PlanningFragment.class.getSimpleName())) == null) {
                    fragment = new PlanningFragment();
                }
                toolbar.setTitle(getResources().getString(R.string.navdrawer_planning));
                break;
        }
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName()).commit();
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
    @Override
    protected void onSaveInstanceState(Bundle state) {
        Utils.makeText(this, "saving instance");
        state.putSerializable("info", mInfoModel);
        state.putInt("tab_index", tabIndex);
    }
}