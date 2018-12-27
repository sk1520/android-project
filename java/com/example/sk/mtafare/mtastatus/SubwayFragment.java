package com.example.sk.mtafare.mtastatus;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sk.mtafare.Internet;
import com.example.sk.mtafare.R;
import com.example.sk.mtafare.StyleableSpannableStringBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SubwayFragment extends Fragment {

    private int MAX_SUBWAY_LINES = 11;
    private TextView textviewUpdateTime;
    private TextView[] textviewSubways = new TextView[MAX_SUBWAY_LINES];
    private final String statusGood       = "Good Service";
    private final String statusDelay      = "Delays";
    private final String statusDelayMinor = "Minor Delays";
    private final String statusWork       = "Planned Work";
    private final String statusDetour     = "Planned Detour";
    private final String statusChange     = "Service Change";
    private final String statusSuspended  = "Suspended";

    public SubwayFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View returnView = inflater.inflate(R.layout.fragment_subway_layout, container, false);

        textviewUpdateTime = (TextView) returnView.findViewById(R.id.statusUpdateTimeTextView);
        textviewSubways[0] = (TextView) returnView.findViewById(R.id.status_subway_1_2_3_TextView);
        textviewSubways[1] = (TextView) returnView.findViewById(R.id.status_subway_4_5_6_TextView);
        textviewSubways[2] = (TextView) returnView.findViewById(R.id.status_subway_7_TextView);
        textviewSubways[3] = (TextView) returnView.findViewById(R.id.status_subway_A_C_E_TextView);
        textviewSubways[4] = (TextView) returnView.findViewById(R.id.status_subway_B_D_F_M_TextView);
        textviewSubways[5] = (TextView) returnView.findViewById(R.id.status_subway_G_TextView);
        textviewSubways[6] = (TextView) returnView.findViewById(R.id.status_subway_J_Z_TextView);
        textviewSubways[7] = (TextView) returnView.findViewById(R.id.status_subway_L_TextView);
        textviewSubways[8] = (TextView) returnView.findViewById(R.id.status_subway_N_Q_R_W_TextView);
        textviewSubways[9] = (TextView) returnView.findViewById(R.id.status_subway_S_TextView);
        textviewSubways[10] = (TextView) returnView.findViewById(R.id.status_subway_SIR_TextView);

        getTransportationStatus();

        return returnView;
    }

    /**
     * Scrapes MTA website and sets relevant information in specific widgets.
     *
     * @noparam
     * @noreturn
     */
    private void getTransportationStatus() {
        new Thread(new Runnable() {
            @Override
                public void run() {
                final StyleableSpannableStringBuilder[] builder = new StyleableSpannableStringBuilder[MAX_SUBWAY_LINES];
                final StyleableSpannableStringBuilder lastUpdate = new StyleableSpannableStringBuilder();

                for (int i = 0; i < builder.length; i++) {
                    builder[i] = new StyleableSpannableStringBuilder();
                }

                try {
                    if (Internet.isActiveInternetConnection(getActivity().getApplicationContext())) {
                        Document doc = Jsoup.connect("http://service.mta.info/ServiceStatus/status.html").get();
                        Elements data = doc.select("td.subwayCategory");
                        Elements updateTime = doc.select("td.statusdatetime");

                        lastUpdate.appendWithStyle(new ForegroundColorSpan(Color.rgb(0, 85, 170)),
                                (getResources().getString(R.string.service_status_last_update_time, updateTime.text())));

                        for (Element e : data) {
                            builder[data.indexOf(e)].append(e.text());
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < builder.length; i++) {
                                    textviewSubways[i].setText(builder[i].toString());

                                    if (builder[i].toString().equalsIgnoreCase(statusGood)) {
                                        textviewSubways[i].setTextColor(Color.rgb(11,104,11));
                                    }
                                    else if (builder[i].toString().equalsIgnoreCase(statusDelay) || builder[i].toString().equalsIgnoreCase(statusDelayMinor)) {
                                        textviewSubways[i].setTextColor(Color.rgb(153,18,40));
                                    }
                                    else if (builder[i].toString().equalsIgnoreCase(statusDetour) ||
                                             builder[i].toString().equalsIgnoreCase(statusWork) ||
                                             builder[i].toString().equalsIgnoreCase(statusChange)) {
                                        textviewSubways[i].setTextColor(Color.rgb(184,124,2));
                                    }
                                    else if (builder[i].toString().equalsIgnoreCase(statusSuspended)) {
                                        textviewSubways[i].setTextColor(Color.rgb(60,19,159));
                                    }
                                }
                                textviewUpdateTime.setText(lastUpdate, TextView.BufferType.SPANNABLE);
                            }
                        });
                    }
                    else {
                        textviewUpdateTime.setText(R.string.service_status_no_internet_connection);
                    }
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }).start();
    }

}
