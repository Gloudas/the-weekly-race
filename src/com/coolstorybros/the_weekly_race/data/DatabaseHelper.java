package com.coolstorybros.the_weekly_race.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import com.coolstorybros.the_weekly_race.WeeklyRaceApplication;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Defines database schemas and provides implementation of all database methods
 *
 * Written with help from these sources:
 * http://developer.android.com/training/basics/data-storage/databases.html
 * http://www.codeproject.com/Articles/119293/Using-SQLite-Database-with-Android
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    static final String dbName = "weeklyraceDB";
    static final int dbVersion = 1;     // update this when changes are made to the database schema
    private SQLiteDatabase mDb;

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
        mDb = db;
        seedDatabase();
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

    private SQLiteDatabase getDb() {
        if (mDb == null) {
            mDb = getWritableDatabase();
        }
        return mDb;
    }

    public ArrayList<Race> getAllRaces() {
        ArrayList<Race> output = new ArrayList<Race>();
        SQLiteDatabase db = getDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.RacesEntry.TABLE_NAME, new String[] {});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0, n = cursor.getCount(); i < n; i++) {
                Race race = new Race(cursor);
                output.add(race);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return output;
    }

    public ArrayList<UserScore> getUserScoresByRace(int raceId) {

        ArrayList<UserScore> userScores = new ArrayList<UserScore>();
        SQLiteDatabase db = getDb();
        Cursor cursor = db.query(UserScoresEntry.TABLE_NAME,
                null,
                UserScoresEntry.COLUMN_NAME_RACE_ID+"=" + raceId,
                new String[]{},
                null,
                null,
                null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0, n = cursor.getCount(); i < n; i++) {
                UserScore userScore = new UserScore(cursor);
                userScores.add(userScore);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return userScores;
    }

    public Race getRace(int raceId) {

        SQLiteDatabase db = getDb();
        Cursor cursor = db.query(RacesEntry.TABLE_NAME,
                null,
                RacesEntry._ID+"=" + raceId,
                new String[]{},
                null,
                null,
                null);
        if (cursor.getCount() <= 0) {
            throw new RuntimeException("Couldn't find the race in the database");
        }
        cursor.moveToFirst();
        Race race = new Race(cursor);
        cursor.close();
        return race;
    }

    public User getUser(int userId) {
        SQLiteDatabase db = getDb();
        Cursor cursor = db.query(UsersEntry.TABLE_NAME,
                null,
                UsersEntry._ID+"=" + userId,
                new String[]{},
                null,
                null,
                null);
        if (cursor.getCount() <= 0) {
            throw new RuntimeException("Couldn't find the user in the database");
        }
        cursor.moveToFirst();
        User user = new User(cursor);
        cursor.close();
        return user;
    }

    public ArrayList<Race> getRacesByUser(int userId) {
        ArrayList<Race> races = new ArrayList<Race>();
        SQLiteDatabase db = getDb();
        Cursor cursor = db.query(UserScoresEntry.TABLE_NAME,
                null,
                UserScoresEntry.COLUMN_NAME_USER_ID + "=" + userId,
                new String[]{},
                null,
                null,
                null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0, n = cursor.getCount(); i < n; i++) {
                // For each UserScore record, query for the corresponding Race using the raceID
                int raceId = cursor.getInt(cursor.getColumnIndex(UserScoresEntry.COLUMN_NAME_RACE_ID));
                Race race = getRace(raceId);
                races.add(race);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return races;
    }

    public int getScoreByRaceAndUser(int raceId, int userId) {

        SQLiteDatabase db = getDb();
        Cursor cursor = db.query(UserScoresEntry.TABLE_NAME,
                null,
                UserScoresEntry.COLUMN_NAME_RACE_ID + "=" + raceId + " AND " +
                        UserScoresEntry.COLUMN_NAME_USER_ID + "=" + userId,
                new String[]{},
                null,
                null,
                null);
        if (cursor.getCount() <= 0) {
            throw new RuntimeException("Couldn't find the user score in the database");
        }
        cursor.moveToFirst();
        int score = cursor.getInt(cursor.getColumnIndex(UserScoresEntry.COLUMN_NAME_USER_SCORE));
        cursor.close();
        return score;
    }

    public void insertNewRace(Race newRace) {

        // Gets the data repository in write mode
        SQLiteDatabase db = getDb();

        // Create a new map of values, where column names are the keys
        ContentValues values = newRace.getValues();

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                RacesEntry.TABLE_NAME,
                null,
                values);
        newRace.setId((int) newRowId);
    }

    public void insertNewUser(User newUser) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getDb();

        // Create a new map of values, where column names are the keys
        ContentValues values = newUser.getValues();

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(UsersEntry.TABLE_NAME, null, values);
        newUser.setId((int) newRowId);
    }

    public void updateUserScore(int raceId, int userId, String username, int scoreUpdate) {

        UserScore userScore = new UserScore();
        userScore.setRaceId(raceId);
        userScore.setUserId(userId);
        userScore.setUsername(username);
        userScore.setScore(scoreUpdate);

        SQLiteDatabase db = getDb();
        String Query = "SELECT * FROM " + UserScoresEntry.TABLE_NAME +
                " WHERE " + UserScoresEntry.COLUMN_NAME_RACE_ID + "=" + raceId +
                " AND " + UserScoresEntry.COLUMN_NAME_USER_ID + "=" + userId;

        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            // First time the user has participated in this race - create a new UserScore record for it
            ContentValues values = userScore.getValues();
            long newRowId = db.insert(UserScoresEntry.TABLE_NAME, null, values);
        } else {
            // Update the user score with the provided score update
            cursor.moveToFirst();
            int previousScore = cursor.getInt(cursor.getColumnIndex(UserScoresEntry.COLUMN_NAME_USER_SCORE));
            userScore.setScore(previousScore + scoreUpdate);
            ContentValues values = userScore.getValues();
            db.update(UserScoresEntry.TABLE_NAME, values,
                    UserScoresEntry.COLUMN_NAME_RACE_ID+"=" + raceId + " AND " +
                            UserScoresEntry.COLUMN_NAME_USER_ID+"=" + userId,
                    new String []{});
        }
        cursor.close();
    }

    public void updateRace(Race updatedRace) {

        SQLiteDatabase db = getDb();
        ContentValues values = updatedRace.getValues();
        // Update the user score with the provided score update
        db.update(RacesEntry.TABLE_NAME, values,
                RacesEntry._ID +"=" + updatedRace.getId(),
                new String []{});

    }

    public void updateUser(User updatedUser) {

        SQLiteDatabase db = getDb();
        ContentValues values = updatedUser.getValues();
        // Update the user score with the provided score update
        db.update(UsersEntry.TABLE_NAME, values,
                UsersEntry._ID +"=" + updatedUser.getId(),
                new String []{});

    }

    public void setRaceOwner(int raceId, User user) {
        user.setCreatedRaceId(raceId);
        updateUser(user);
    }

    /**
     * Populate the database with placeholder data.
     */
    private void seedDatabase() {

        Context context = WeeklyRaceApplication.getAppContext();
        if (context == null)
            throw new RuntimeException("WeeklyRaceApplication returned null-Context");

        User newUser1 = new User("User1", "password1");
        insertNewUser(newUser1);
        User newUser2 = new User("User2", "password2");
        insertNewUser(newUser2);
        User newUser3 = new User("User3", "password3");
        insertNewUser(newUser3);
        User newUser4 = new User("User4", "password4");
        insertNewUser(newUser4);
        newUser4.setPassword("updatedPassword4");
        updateUser(newUser4);
        User dbTest0 = getUser(newUser4.getId()); // test that insertion/updating of users works

        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 12, 12);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2013, 12, 12);
        Race newRace1 = new Race(
                "Tangerine Race",
                "Whole Foods Market, 2778 Telegraph Avenue, Berkeley CA",
                "Celebrate vitamin C! For every bag of tangerines you buy in our store, earn a point towards the competition.",
                startDate,
                endDate,
                "A $50 gift certificate to our store!",
                3);
        insertNewRace(newRace1);
        setRaceOwner(newRace1.getId(), newUser2);
        User dbTest1 = getUser(newUser2.getId()); // test that createdRaceId got set
        Race newRace2 = new Race("Race2", "Race Location 2", "race details 2", startDate, endDate, "race prize 2", 3);
        insertNewRace(newRace2);
        setRaceOwner(newRace2.getId(), newUser3);
        Race newRace3 = new Race("Race3", "Race Location 3", "race details 3", startDate, endDate, "race prize 3", 3);
        insertNewRace(newRace3);
        setRaceOwner(newRace3.getId(), newUser4);

        updateUserScore(newRace1.getId(), newUser2.getId(), newUser2.getUsername(), 1);
        int dbTest2 = getScoreByRaceAndUser(newRace1.getId(), newUser2.getId()); // test that score gets inserted && that getScoreByRaceAndUser works
        updateUserScore(newRace1.getId(), newUser2.getId(), newUser2.getUsername(), 3);
        int dbTest3 = getScoreByRaceAndUser(newRace1.getId(), newUser2.getId()); // test that score got updated
        updateUserScore(newRace1.getId(), newUser3.getId(), newUser3.getUsername(), 1);
        ArrayList<UserScore> dbTest4 = getUserScoresByRace(newRace1.getId()); // test that getUserScoresByRace gets all participating users
        updateUserScore(newRace2.getId(), newUser2.getId(), newUser2.getUsername(), 2);
        ArrayList<Race> dbTest5 = getRacesByUser(newUser2.getId()); // test that getRacesByUser() works
        ArrayList<Race> dbTest6 = getAllRaces(); // test that getAllRaces() works
        newRace1.setDetails("updated race details1");
        updateRace(newRace1);
        Race dbTest7 = getRace(newRace1.getId()); // test that race was correctly updated in the DB
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
}
