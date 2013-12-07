package com.coolstorybros.the_weekly_race.data;


import android.content.ContentValues;
import android.database.Cursor;

/**
 * A class representing a User's score for a specific race.
 * This data is used to provide a list of all users for a race and their respective scores
 */
public class UserScore {

    private int mRaceId;
    private int mUserId;
    private String mUsername;
    private int mScore;

    public UserScore() {
    }

    public UserScore(Cursor cursor) {
        mRaceId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.UserScoresEntry.COLUMN_NAME_RACE_ID));
        mUserId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.UserScoresEntry.COLUMN_NAME_USER_ID));
        mUsername = cursor.getString(cursor.getColumnIndex(DatabaseHelper.UserScoresEntry.COLUMN_NAME_USERNAME));
        mScore = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.UserScoresEntry.COLUMN_NAME_USER_SCORE));
    }

    public int getRaceId() {
        return mRaceId;
    }
    public void setRaceId(int raceId) {
        mRaceId = raceId;
    }

    public int getUserId() {
        return mUserId;
    }
    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getUsername() {
        return mUsername;
    }
    public void setUsername(String username) {
        mUsername = username;
    }

    public int getScore() {
        return mScore;
    }
    public void setScore(int score) {
        mScore = score;
    }

    /**
     * Creates a ContentValues object to insert into the database
     * @return
     */
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.UserScoresEntry.COLUMN_NAME_RACE_ID, getRaceId());
        values.put(DatabaseHelper.UserScoresEntry.COLUMN_NAME_USER_ID, getUserId());
        values.put(DatabaseHelper.UserScoresEntry.COLUMN_NAME_USERNAME, getUsername());
        values.put(DatabaseHelper.UserScoresEntry.COLUMN_NAME_USER_SCORE, getScore());
        return values;
    }

}
