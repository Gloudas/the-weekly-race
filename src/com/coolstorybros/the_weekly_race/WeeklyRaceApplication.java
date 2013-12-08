package com.coolstorybros.the_weekly_race;

import android.app.Application;
import android.content.Context;
import com.coolstorybros.the_weekly_race.data.User;

/**
 * Used to access a static context
 * Based on this stack overflow: http://stackoverflow.com/questions/2002288/static-way-to-get-context-on-android
 */
public class WeeklyRaceApplication extends Application {

    private static Context sContext;
    private static User sCurrentUser;

    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return sContext;
    }

    public static User getCurrentUser() {
        return sCurrentUser;
    }

    public static void setCurrentUser(User user) {
        sCurrentUser = user;
    }
}
