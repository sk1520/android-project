package com.example.sk.mtafare.mtamap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;

import com.example.sk.mtafare.BaseNavigationDrawerSetupActivity;
import com.example.sk.mtafare.R;

public class MapInfo extends BaseNavigationDrawerSetupActivity {

    TabLayout tablayoutService;
    TabItem tabitemSubwayFragment; /* Unused since changing from Button listener */
    TabItem tabitemRailFragment;                   /*    to    */
    TabItem tabitemBusFragment;               /* TabLayout listener */
    private static int tabCurrentPosition;

    public MapInfo() {}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        tabCurrentPosition = tablayoutService.getSelectedTabPosition(); // Save active tab position before device rotation to be able to restore it
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MTA Maps");
        setContentView(R.layout.activity_map_info);

        tablayoutService = findViewById(R.id.service_tablayout);
        tabitemSubwayFragment = findViewById(R.id.service_subway);
        tabitemRailFragment = findViewById(R.id.service_rail);
        tabitemBusFragment = findViewById(R.id.service_bus);

        tablayoutService.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getTabPositionClicked(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) { // Will refresh the fragment by removing and re-adding if reselected
                getTabPositionClicked(tab.getPosition());
            }
        });

        if (savedInstanceState == null) { // Default initial fragment view to MapSubwayFragment if none created / selected
            getTabPositionClicked(0);
        }
        else { // Grab last active tab position and restore it
            TabLayout.Tab tab = tablayoutService.getTabAt(tabCurrentPosition);
            if (tab != null) {
                tab.select();
            }
        }

        configureNavigationDrawer(this.getApplicationContext());
    }

    private void getTabPositionClicked(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {
            case 0:
                MapSubwayFragment mapSubwayFragment = new MapSubwayFragment();
                addCreatedFragment(fragmentTransaction, mapSubwayFragment);
                break;
            case 1:
                MapRailFragment mapRailFragment = new MapRailFragment();
                addCreatedFragment(fragmentTransaction, mapRailFragment);
                break;
            case 2:
                MapBusFragment mapBusFragment = new MapBusFragment();
                addCreatedFragment(fragmentTransaction, mapBusFragment);
                break;
            case 3:
                MapBridgesAndTunnelsFragment mapBridgeTunnelFragment = new MapBridgesAndTunnelsFragment();
                addCreatedFragment(fragmentTransaction, mapBridgeTunnelFragment);
                break;
        }
    }

    private void addCreatedFragment(FragmentTransaction fragmentTransaction, Fragment fragmentObject) {
        fragmentTransaction.add(R.id.service_fragment_container, fragmentObject); // Make sure to import the correct fragment package 'android.app.Fragment'
        fragmentTransaction.commit();
    }
}
