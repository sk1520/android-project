package com.example.sk.mtafare.mtastatus;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
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

public class RailFragment extends Fragment {

    private TextView textviewUpdateTime;
    private TextView textviewLLIR_Names;
    private TextView textviewMetroNorth_Names;
    private TextView textviewLLIR_Status;
    private TextView textviewMetroNorth_Status;
    private final String statusGood       = "Good Service";
    private final String statusDelay      = "Delays";
    private final String statusDelayMinor = "Minor Delays";
    private final String statusWork       = "Planned Work";
    private final String statusDetour     = "Planned Detour";
    private final String statusChange     = "Service Change";
    private final String statusSuspended  = "Suspended";

    public RailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View returnView = inflater.inflate(R.layout.fragment_rail_layout, container, false);

        textviewUpdateTime = (TextView) returnView.findViewById(R.id.statusUpdateTimeTextView);
        textviewLLIR_Names = (TextView) returnView.findViewById(R.id.status_rail_LLIR_names);
        textviewMetroNorth_Names = (TextView) returnView.findViewById(R.id.status_rail_MetroNorth_names);
        textviewLLIR_Status = (TextView) returnView.findViewById(R.id.status_rail_LIRR_status);
        textviewMetroNorth_Status = (TextView) returnView.findViewById(R.id.status_rail_MetroNorth_status);

        getTransportationStatus();

        return returnView;
    }

    private void getTransportationStatus() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StyleableSpannableStringBuilder lastUpdate = new StyleableSpannableStringBuilder();
                final StyleableSpannableStringBuilder builderNameLLIR = new StyleableSpannableStringBuilder();
                final StyleableSpannableStringBuilder builderNameMetroNorth = new StyleableSpannableStringBuilder();
                final StyleableSpannableStringBuilder builderStatusLLIR = new StyleableSpannableStringBuilder();
                final StyleableSpannableStringBuilder builderStatusMetroNorth = new StyleableSpannableStringBuilder();

                try {
                    if (Internet.isActiveInternetConnection(getActivity().getApplicationContext())) {
                        Document doc = Jsoup.connect("http://web.mta.info/status/serviceStatus.txt").get();
                        Document oldDoc = Jsoup.connect("http://service.mta.info/ServiceStatus/status.html").get();

                        Elements updateTime = oldDoc.select("td.statusdatetime");
                        Elements lirrName = doc.select("LIRR name");
                        Elements lirrStatus = doc.select("LIRR line status");
                        Elements metroNorthName = doc.select("MetroNorth name");
                        Elements metroNorthStatus = doc.select("MetroNorth line status");

                        lastUpdate.appendWithStyle(new ForegroundColorSpan(Color.rgb(0, 85, 170)),
                                (getResources().getString(R.string.service_status_last_update_time, updateTime.text())));

                        for (Element e : lirrName) {
                            builderNameLLIR.append(e.text()).append("\n");
                        }

                        for (Element e : metroNorthName) {
                            builderNameMetroNorth.append(e.text()).append("\n");
                        }

                        for (Element e : lirrStatus) {
                            if (e.text().equalsIgnoreCase(statusGood)) {
                                builderStatusLLIR.appendWithStyle(new ForegroundColorSpan(Color.rgb(11, 104, 11)), statusGood + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusDelay)) {
                                builderStatusLLIR.appendWithStyle(new ForegroundColorSpan(Color.rgb(153, 18, 40)), statusDelay + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusDelayMinor)) {
                                builderStatusLLIR.appendWithStyle(new ForegroundColorSpan(Color.rgb(153, 18, 40)), statusDelayMinor + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusDetour)) {
                                builderStatusLLIR.appendWithStyle(new ForegroundColorSpan(Color.rgb(184, 124, 2)), statusDetour + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusWork)) {
                                builderStatusLLIR.appendWithStyle(new ForegroundColorSpan(Color.rgb(184, 124, 2)), statusWork + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusChange)) {
                                builderStatusLLIR.appendWithStyle(new ForegroundColorSpan(Color.rgb(184, 124, 2)), statusChange + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusSuspended)) {
                                builderStatusLLIR.appendWithStyle(new ForegroundColorSpan(Color.rgb(60, 19, 159)), statusSuspended + "\n");
                            }
                        }

                        for (Element e : metroNorthStatus) {
                            if (e.text().equalsIgnoreCase(statusGood)) {
                                builderStatusMetroNorth.appendWithStyle(new ForegroundColorSpan(Color.rgb(11, 104, 11)), statusGood + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusDelay)) {
                                builderStatusMetroNorth.appendWithStyle(new ForegroundColorSpan(Color.rgb(153, 18, 40)), statusDelay + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusDelayMinor)) {
                                builderStatusMetroNorth.appendWithStyle(new ForegroundColorSpan(Color.rgb(153, 18, 40)), statusDelayMinor + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusDetour)) {
                                builderStatusMetroNorth.appendWithStyle(new ForegroundColorSpan(Color.rgb(184, 124, 2)), statusDetour + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusWork)) {
                                builderStatusMetroNorth.appendWithStyle(new ForegroundColorSpan(Color.rgb(184, 124, 2)), statusWork + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusChange)) {
                                builderStatusMetroNorth.appendWithStyle(new ForegroundColorSpan(Color.rgb(184, 124, 2)), statusChange + "\n");
                            }
                            else if (e.text().equalsIgnoreCase(statusSuspended)) {
                                builderStatusMetroNorth.appendWithStyle(new ForegroundColorSpan(Color.rgb(60, 19, 159)), statusSuspended + "\n");
                            }
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textviewUpdateTime.setText(lastUpdate, TextView.BufferType.SPANNABLE);
                                textviewLLIR_Names.setText(builderNameLLIR, TextView.BufferType.SPANNABLE);
                                textviewMetroNorth_Names.setText(builderNameMetroNorth, TextView.BufferType.SPANNABLE);
                                textviewLLIR_Status.setText(builderStatusLLIR, TextView.BufferType.SPANNABLE);
                                textviewMetroNorth_Status.setText(builderStatusMetroNorth, TextView.BufferType.SPANNABLE);
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
