package com.example.sk.mtafare.mtaweb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.sk.mtafare.BaseNavigationDrawerSetupActivity;
import com.example.sk.mtafare.Internet;
import com.example.sk.mtafare.R;

import java.util.ArrayList;

@SuppressLint("Registered")
public class WebCreator extends BaseNavigationDrawerSetupActivity {

    private Activity activity;
    private ArrayList badElements;
    private String beginningWebURL;
    private String endingWebURL;

    private WebView webView;
    private ProgressBar progressBarLoading;
    private boolean isLoading = true;

    private int webViewInitialScale = 100;


    public WebCreator(Activity activity, ArrayList<String> badElements, String beginningWebURL, String endingWebURL) {
        this.activity = activity;
        this.badElements = badElements;
        this.beginningWebURL = beginningWebURL;
        this.endingWebURL = endingWebURL;

        webView = (WebView) this.activity.findViewById(R.id.feedback_WebView);
        progressBarLoading = (ProgressBar) this.activity.findViewById(R.id.feedback_loading_ProgressBar);
    }



    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView() {
        webView.setWebViewClient(new WebViewClient());
        webView.setInitialScale(webViewInitialScale);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);



        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (isLoading) {
                    view.setVisibility(View.INVISIBLE);
                }
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                isLoading = false;
                progressBarLoading.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);

              //  webView.loadUrl("javascript: document.getElementById('rn_MobileNavigationMenu_0_Link').remove();");
                if (Internet.isActiveInternetConnection(activity)) {
                    if (!badElements.isEmpty()) {
                        removeElements();
                    }

                    if (view.getUrl().contains(endingWebURL)) { // Reached final URL; Finish activity after delay ends
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 2000);
                    }
                }

            }
        });

        webView.loadUrl(beginningWebURL);
    }

    private void removeElements() {
        for (int i = 0; i < badElements.size(); i++) {
            webView.loadUrl("javascript: document.getElementByClassName('"+ badElements.get(i) + "').remove();");//finds and removes by class name
        }
        for (int i = 0; i < badElements.size(); i++) {
            webView.loadUrl("javascript: document.getElementById('"+ badElements.get(i) + "').remove();"); //finds and removes by Id
        }
        for (int i = 0; i < badElements.size(); i++) {
            webView.loadUrl("javascript: (function() {var element = document.getElementsByTagName('" + badElements.get(i) + "'), index;" +
                    "for (index = element.length - 1; index >= 0; index--) {" +
                    "element[index].parentNode.removeChild(element[index]);" +
                    "};" +
                    "})();"); //removes elements by tag name
        }
    }

    public void setWebViewInitialScale(int webViewInitialScale) {
        this.webViewInitialScale = webViewInitialScale;
    }

    public int getWebViewInitialScale() {
        return this.webViewInitialScale;
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
