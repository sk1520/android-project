package com.example.sk.mtafare.mtacontact;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.sk.mtafare.R;

import java.util.ArrayList;

public class EmailContactFragment extends Fragment {
    private static final ArrayList<String> badElements = new ArrayList<String>();
    private static final String beginningWebURL = "http://mta-nyc.custhelp.com/app/ask";
    private static final String endingWebURL = "";
    private ProgressBar progressBarLoading;//TODO- implement loading bar into this fragment
    private boolean isLoading = true;
   // private WebView webView;

    public EmailContactFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View returnView = inflater.inflate(R.layout.fragment_contact_email_layout, container, false);

        progressBarLoading = (ProgressBar) returnView.findViewById(R.id.feedback_loading_ProgressBar);
        //webView = (WebView) returnView.findViewById(R.id.mtaEmailWebView); //set into webview
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse(beginningWebURL));
        //setEmailData(); //call function
        return returnView;
    }
    /*private void setEmailData() {
        badElements.add("rn_MobileNavigationMenu_0_Link");//remove menu option so users cant go back to the MTA home page or other pages using their navigation menu (ID)
        badElements.add("footer");//removes the footer so users wont be able to click links to go anywhere else (Tag Name).
        badElements.add("rn_FileAttachmentUpload_14");//removes the image upload because it doesn't work in webview (ID)
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
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

                for (int i = 0; i < badElements.size(); i++) {
                    webView.loadUrl("javascript: document.getElementById('"+ badElements.get(i) + "').remove();"); //finds and removes by Id
                }
                for (int i = 0; i < badElements.size(); i++) {
                    webView.loadUrl("javascript: document.querySelector('."+ badElements.get(i) + "').remove();"); // removes by class name
                }
               for (int i = 0; i < badElements.size(); i++) {
                    webView.loadUrl("javascript:var element = document.getElementsByTagName('" + badElements.get(i) + "'), index;" +
                            "for (index = element.length - 1; index >= 0; index--) {" +
                            "element[index].parentNode.removeChild(element[index]);" +
                            "};"); //removes elements by tag name
                }

               webView.loadUrl("javascript:(function() { document.getElementById('rn_PageTitle').getElementsByTagName('h1')[0].innerHTML = 'E-Mail MTA support team a question'; })();");//Replaces "Ask our support team a question" to "E-Mail MTA support team a question"
               webView.loadUrl("javascript:(function() { document.getElementsByClassName('yui3-js-enabled')[0].style.background = 'url()'; })();"); //removes MTA background image, now background will be white instead of their color
               webView.loadUrl("javascript: var a = document.querySelector('a[href=\"http://m.mta.info\"]'); " +
                       "if (a) { " +
                       "a.setAttribute('href', '"+beginningWebURL+"') }" //top left MTA.info image link is replaced so user cant go out the e-mail page
               );
            };

        });//TODO - implement WebCreator

        webView.loadUrl(beginningWebURL);

    }*/
 }


