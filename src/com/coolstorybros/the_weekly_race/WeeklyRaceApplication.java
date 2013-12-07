package com.coolstorybros.the_weekly_race;

import android.app.Application;
import android.content.Context;

/**
 * Used to access a static context
 */
public class WeeklyRaceApplication extends Application {

    private static Context sContext;

    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return sContext;
    }
}
