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

public class BridgesAndTunnelsFragment extends Fragment{

    private TextView textviewUpdateTime;
    private TextView textviewBridge_Names;
    private TextView textviewBridge_Status;
    private final String statusGood       = "Good Service";
    private final String statusDelay      = "Delays";
    private final String statusDelayMinor = "Minor Delays";
    private final String statusWork       = "Planned Work";
    private final String statusDetour     = "Planned Detour";
    private final String statusChange     = "Service Change";
    private final String statusSuspended  = "Suspended";

    public BridgesAndTunnelsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View returnView = inflater.inflate(R.layout.fragment_bridges_and_tunnels, container, false);

        textviewUpdateTime = (TextView) returnView.findViewById(R.id.statusUpdateTimeTextView);
        textviewBridge_Names = (TextView) returnView.findViewById(R.id.status_bt_names);
        textviewBridge_Status = (TextView) returnView.findViewById(R.id.status_bt_status);

        getTransportationStatus();

        return returnView;
    }

    private void getTransportationStatus() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StyleableSpannableStringBuilder lastUpdate = new StyleableSpannableStringBuilder();
                final StyleableSpannableStringBuilder builderNameBridgeTunnel = new StyleableSpannableStringBuilder();
                final StyleableSpannableStringBuilder builderStatusBridgeTunnel = new StyleableSpannableStringBuilder();

                try {
                    if (Internet.isActiveInternetConnection(getActivity().getApplicationContext())) {
                        Document doc = Jsoup.connect("http://web.mta.info/status/serviceStatus.txt").get();
                        Document oldDoc = Jsoup.connect("http://service.mta.info/ServiceStatus/status.html").get();

                        Elements updateTime = oldDoc.select("td.statusdatetime");
                        Elements btName = doc.select("bt name");
                        Elements btStatus = doc.select("bt line status");

                        lastUpdate.appendWithStyle(new ForegroundColorSpan(Color.rgb(0, 85, 170)),
                                (getResources().getString(R.string.service_status_last_update_time, updateTime.text())));

                        for (Element e : btName) {
                            builderNameBridgeTunnel.append(e.text()).append("\n\n");
                        }

                        for (Element e : btStatus) {
                            if (e.text().equalsIgnoreCase(statusGood)) {
                                builderStatusBridgeTunnel.appendWithStyle(new ForegroundColorSpan(Color.rgb(11, 104, 11)), statusGood + "\n\n");
                            } else if (e.text().equalsIgnoreCase(statusDelay)) {
                                builderStatusBridgeTunnel.appendWithStyle(new ForegroundColorSpan(Color.rgb(153, 18, 40)), statusDelay + "\n\n");
                            } else if (e.text().equalsIgnoreCase(statusDelayMinor)) {
                                builderStatusBridgeTunnel.appendWithStyle(new ForegroundColorSpan(Color.rgb(153, 18, 40)), statusDelayMinor + "\n\n");
                            } else if (e.text().equalsIgnoreCase(statusDetour)) {
                                builderStatusBridgeTunnel.appendWithStyle(new ForegroundColorSpan(Color.rgb(184, 124, 2)), statusDetour + "\n\n");
                            } else if (e.text().equalsIgnoreCase(statusWork)) {
                                builderStatusBridgeTunnel.appendWithStyle(new ForegroundColorSpan(Color.rgb(184, 124, 2)), statusWork + "\n\n");
                            } else if (e.text().equalsIgnoreCase(statusChange)) {
                                builderStatusBridgeTunnel.appendWithStyle(new ForegroundColorSpan(Color.rgb(184, 124, 2)), statusChange + "\n\n");
                            } else if (e.text().equalsIgnoreCase(statusSuspended)) {
                                builderStatusBridgeTunnel.appendWithStyle(new ForegroundColorSpan(Color.rgb(60, 19, 159)), statusSuspended + "\n\n");
                            }

                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textviewUpdateTime.setText(lastUpdate, TextView.BufferType.SPANNABLE);
                                textviewBridge_Names.setText(builderNameBridgeTunnel.toString());
                                textviewBridge_Status.setText(builderStatusBridgeTunnel, TextView.BufferType.SPANNABLE);
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
