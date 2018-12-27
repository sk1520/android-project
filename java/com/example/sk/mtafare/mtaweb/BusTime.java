/*package com.example.ryan.mtafare.mtaweb;

import android.os.Bundle;

import com.example.ryan.mtafare.BaseNavigationDrawerSetupActivity;
import com.example.ryan.mtafare.R;

import java.util.ArrayList;

public class BusTime extends BaseNavigationDrawerSetupActivity {

    static final ArrayList<String> badElements = new ArrayList<String>();
    static final String beginningWebURL = "https://bustime.mta.info/m/index?q=&l=&t=";
    static final String endingWebURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MTA Bus Tracker");
        setContentView(R.layout.activity_feedback);

        WebCreator webCreator = new WebCreator(this, badElements, beginningWebURL, endingWebURL);
        webCreator.setWebViewInitialScale(0);
        webCreator.initWebView();

        configureNavigationDrawer(this.getApplicationContext());


    }

}*/
