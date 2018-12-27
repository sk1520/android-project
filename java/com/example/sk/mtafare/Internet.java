package com.example.sk.mtafare;

import android.content.Context;
import android.net.ConnectivityManager;

public class Internet {

    public static boolean isActiveInternetConnection(Context context) {
        ConnectivityManager con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager == null) throw new AssertionError();
        return (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected());
    }
}

