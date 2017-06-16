package com.mbm.ftn.mbm.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Milos on 5/22/2017.
 */

public class AppPreference {

    public static final String APP_SETTINGS_NAME = "config";
    public static final String LAST_UPDATE_TIME_IN_MS = "last_update";

    public static long saveLastUpdateTimeMillis(Context context) {
        SharedPreferences sp = context.getSharedPreferences(APP_SETTINGS_NAME,
                Context.MODE_PRIVATE);
        long now = System.currentTimeMillis();
        sp.edit().putLong(LAST_UPDATE_TIME_IN_MS, now).apply();
        return now;
    }
}
