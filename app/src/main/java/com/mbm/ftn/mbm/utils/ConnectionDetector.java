package com.mbm.ftn.mbm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Milos on 5/22/2017.
 */

public class ConnectionDetector {

    private Context mContext;

    public ConnectionDetector(Context context) {
        mContext = context;
    }

    public boolean isNetworkAvailableAndConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}