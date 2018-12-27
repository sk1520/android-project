/*package com.example.ryan.mtafare.mtaweb;

import android.os.Bundle;

import com.example.ryan.mtafare.BaseNavigationDrawerSetupActivity;
import com.example.ryan.mtafare.R;

import java.util.ArrayList;

public class SubwayTime extends BaseNavigationDrawerSetupActivity {

    static final ArrayList<String> badElements = new ArrayList<String>();
    static final String beginningWebURL = "http://subwaytime.mta.info/index.html#/app/home";
    static final String endingWebURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MTA Train Tracker");
        setContentView(R.layout.activity_feedback);

        badElements.add("span"); //remove hamburger icon so user cant click other options besides going back
        WebCreator webCreator = new WebCreator(this, badElements, beginningWebURL, endingWebURL);
        webCreator.setWebViewInitialScale(0);
        webCreator.initWebView();

        configureNavigationDrawer(this.getApplicationContext());
    }

}*/
