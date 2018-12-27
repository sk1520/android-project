package com.example.sk.mtafare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.sk.mtafare.mtacontact.ContactInfo;
import com.example.sk.mtafare.mtamap.MapInfo;
import com.example.sk.mtafare.mtastatus.StatusInfo;
//import com.example.ryan.mtafare.mtaweb.BusTime;
import com.example.sk.mtafare.mtaweb.Feedback;
//import com.example.ryan.mtafare.mtaweb.SubwayTime;

/*
 NOTE: Good and basic feedback examples (for send feedback option):
           - https://stackoverflow.com/questions/21107643/how-to-include-feedback-in-android-app
           - https://stackoverflow.com/questions/11320685/how-to-implement-send-feedback-feature-in-android

 NOTE: FLAG_ACTIVITY_CLEAR_TOP        --> Brings activity to top if it exists and closes old and replaces with new ->
 NOTE: FLAG_ACTIVITY_REORDER_TO_FRONT --> Brings activity to top if it exists (prevents creating same intents)
 NOTE: overridePendingTransition(0,0) --> 0 means no animation for opening new intent
**/

@SuppressLint("Registered")
public class BaseNavigationDrawerSetupActivity extends AppCompatActivity implements DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayoutOptions;
    private ActionBarDrawerToggle barDrawerToggle;
    private String activityTitle;
    private final String busUrl = "https://bustime.mta.info/m/index?q=&l=&t=";
    private final String trainUrl = "http://subwaytime.mta.info/index.html#/app/home";

    public BaseNavigationDrawerSetupActivity() {}
    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
    CustomTabsIntent customTabsIntent = builder.build();

    public void configureNavigationDrawer(final Context context) {
        activityTitle = getTitle().toString();
        NavigationView nv = findViewById(R.id.main_navigation);
        drawerLayoutOptions = findViewById(R.id.optionsDrawerlayout);
        barDrawerToggle = new ActionBarDrawerToggle(this, drawerLayoutOptions, R.string.options_drawer_open, R.string.options_drawer_close);

        drawerLayoutOptions.addDrawerListener(barDrawerToggle);
        drawerLayoutOptions.addDrawerListener(this);
        barDrawerToggle.syncState();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayoutOptions.closeDrawer(GravityCompat.START, true);

                switch (menuItem.getItemId()) {
                    case (R.id.nav_home): //home pages
                        initActivityStart(MainActivity.class);
                        break;
                    case (R.id.nav_mta_prices): //mta prices
                        initActivityStart(MtaPrices.class);
                        break;
                    case (R.id.nav_mta_service_status): //service status
                        initActivityStart(StatusInfo.class);
                        break;
                    case (R.id.nav_mta_time_subway): //train time
                        //initActivityStart(SubwayTime.class);
                        customTabsIntent.launchUrl(context, Uri.parse(trainUrl));
                        break;
                    case (R.id.nav_mta_time_bus): //bus locator and time
                        //initActivityStart(BusTime.class);
                        customTabsIntent.launchUrl(context, Uri.parse(busUrl));
                        break;
                    case (R.id.nav_mta_maps): //MTA Maps
                        initActivityStart(MapInfo.class);
                        break;
                    case (R.id.nav_contact): //Contact Info
                        initActivityStart(ContactInfo.class);// TODO - fix spelling and long text
                        break;
                    case (R.id.nav_mta_links)://useful MTA Links
                        initActivityStart(MtaLinks.class);
                        break;
                    case (R.id.nav_mta_logs): //MTA Logs
                        initActivityStart(MtaLogs.class);
                        break;
//                    case (R.id.nav_settings): // TODO - create and implement
//                        Toast.makeText(context, "NOT IMPLEMENTED YET <SETTINGS>", Toast.LENGTH_SHORT).show();
//                        break;
                    case (R.id.nav_send_feedback): //user feedback
                        initActivityStart(Feedback.class);
                        break;
                }
                return true;
            }
        });
    }

    private void initActivityStart(Class classname) {
        Intent intent = new Intent(getApplicationContext(), classname);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {}

    @Override
    public void onDrawerOpened(View drawerView) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Menu");
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(activityTitle);
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (barDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
