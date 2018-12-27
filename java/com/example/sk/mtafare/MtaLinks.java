package com.example.sk.mtafare;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MtaLinks extends BaseNavigationDrawerSetupActivity {

    private ListView usefulLinksListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MTA Links");
        setContentView(R.layout.activity_mta_links);

        usefulLinksListView = (ListView) findViewById(R.id.usefulLinksListView);

        // Create ArrayList and populate all links into it
        final List<String> usefulLinksArray = new ArrayList<String>();
        usefulLinksArray.addAll(Arrays.asList(getResources().getStringArray(R.array.links_mta_links)));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview_textsize, usefulLinksArray) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position % 2 == 1) {
                    view.setBackgroundColor(Color.rgb(240,240,240));
                }
                else {
                    view.setBackgroundColor(Color.WHITE);
                }
                return view;
            }
        };

        usefulLinksListView.setAdapter(arrayAdapter);

        configureNavigationDrawer(this.getApplicationContext());
    }
}
