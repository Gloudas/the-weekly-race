package com.coolstorybros.the_weekly_race.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import com.coolstorybros.the_weekly_race.WeeklyRaceApplication;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String dbName = "weeklyraceDB";
    static final int dbVersion = 1;     // update this when changes are made to the database schema

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    public DatabaseHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RacesEntry.SQL_CREATE_RACES_TABLE);
        db.execSQL(UserScoresEntry.SQL_CREATE_USER_SCORES_TABLE);
        db.execSQL(UsersEntry.SQL_CREATE_USERS_TABLE);

        seedDatabase(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RacesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserScoresEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UsersEntry.TABLE_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
        this.getWritableDatabase();
    }

    /* Defines the contents of the Races table */
    public static abstract class RacesEntry implements BaseColumns {

        private static final String SQL_CREATE_RACES_TABLE =
                "CREATE TABLE " + RacesEntry.TABLE_NAME + " (" +
                        RacesEntry._ID + " INTEGER PRIMARY KEY," +
                        RacesEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                        RacesEntry.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP +
                        RacesEntry.COLUMN_NAME_DETAILS + TEXT_TYPE + COMMA_SEP +
                        RacesEntry.COLUMN_NAME_START_DATE + TEXT_TYPE + COMMA_SEP +
                        RacesEntry.COLUMN_NAME_END_DATE + TEXT_TYPE + COMMA_SEP +
                        RacesEntry.COLUMN_NAME_PRIZE + TEXT_TYPE + COMMA_SEP +
                        RacesEntry.COLUMN_NAME_NUM_WINNERS + INTEGER_TYPE +
                        " )";

        public static final String TABLE_NAME = "races";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_DETAILS = "details";
        public static final String COLUMN_NAME_START_DATE = "start_date";
        public static final String COLUMN_NAME_END_DATE = "end_date";
        public static final String COLUMN_NAME_PRIZE = "prize";
        public static final String COLUMN_NAME_NUM_WINNERS = "num_winners";
    }

    /* Defines the contents of the UserScores table */
    public static abstract class UserScoresEntry implements BaseColumns {

        private static final String SQL_CREATE_USER_SCORES_TABLE =
                "CREATE TABLE " + UserScoresEntry.TABLE_NAME + " (" +
                        UserScoresEntry._ID + " INTEGER PRIMARY KEY," +
                        UserScoresEntry.COLUMN_NAME_RACE_ID + INTEGER_TYPE + COMMA_SEP +
                        UserScoresEntry.COLUMN_NAME_USER_ID + INTEGER_TYPE + COMMA_SEP +
                        UserScoresEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                        UserScoresEntry.COLUMN_NAME_USER_SCORE + INTEGER_TYPE +
                        " )";

        public static final String TABLE_NAME = "user_scores";
        public static final String COLUMN_NAME_RACE_ID = "race_id";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_USER_SCORE = "user_score";
    }

    /* Defines the contents of the Users table */
    public static abstract class UsersEntry implements BaseColumns {

        private static final String SQL_CREATE_USERS_TABLE =
                "CREATE TABLE " + UsersEntry.TABLE_NAME + " (" +
                        UsersEntry._ID + " INTEGER PRIMARY KEY," +
                        UsersEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                        UsersEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
                        UsersEntry.COLUMN_NAME_CREATED_RACE_ID + INTEGER_TYPE +
                        " )";

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_CREATED_RACE_ID = "created_race_id";
    }

    /**
     * Populate the database with placeholder data.
     * This is implemented kind of hacky given that most methods are implemented in DatabaseManager
     * @param db
     */
    private void seedDatabase(SQLiteDatabase db) {

        Context context = WeeklyRaceApplication.getAppContext();
        if (context == null)
            throw new RuntimeException("WeeklyRaceApplication returned null-Context");
        DatabaseManager dbManager = new DatabaseManager(context);


    }
}
