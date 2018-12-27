package com.example.sk.mtafare.mtaweb;

import android.os.Bundle;

import com.example.sk.mtafare.BaseNavigationDrawerSetupActivity;
import com.example.sk.mtafare.R;

import java.util.ArrayList;

public class Feedback extends BaseNavigationDrawerSetupActivity {

    static final ArrayList<String> badElements = new ArrayList<String>();
    static final String beginningWebURL = "https://goo.gl/forms/scIILD333Wctqazw1";
    static final String endingWebURL = "formResponse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Feedback");
        setContentView(R.layout.activity_feedback);

        badElements.add("'freebirdFormviewerViewFeedbackSubmitFeedbackButton')[0].style.display='none'"); // Remove google javascript image clickable redirect
        badElements.add("'freebirdFormviewerViewFooterDisclaimer')[0].css('pointer-events','none'"); // Remove ['Report Abuse', 'TOS', 'Additional Terms'] clickable redirect
        badElements.add("'freebirdFormviewerViewFooterImageContainer')[0].style.display='none'"); // Remove 'Google Forms' javascript clickable redirect

        WebCreator webCreator = new WebCreator(this, badElements, beginningWebURL, endingWebURL);
        webCreator.setWebViewInitialScale(100);
        webCreator.initWebView();

        configureNavigationDrawer(this.getApplicationContext());
    }

}
