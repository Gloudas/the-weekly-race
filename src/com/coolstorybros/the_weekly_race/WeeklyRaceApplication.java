package com.coolstorybros.the_weekly_race;

import android.app.Application;
import android.content.Context;
import com.coolstorybros.the_weekly_race.data.User;

/**
 * Used to access a static context
 */
public class WeeklyRaceApplication extends Application {

    private static Context sContext;
    private static User sCurrentUser;

    public static boolean databaseCreated = true;

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
