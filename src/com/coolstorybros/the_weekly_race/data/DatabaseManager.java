package com.coolstorybros.the_weekly_race.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class DatabaseManager {

    private static DatabaseHelper mDbHelper;

    public DatabaseManager(Context context) {
        mDbHelper = new DatabaseHelper(context);
    }

    public ArrayList<Race> getAllRaces() {
        ArrayList<Race> output = new ArrayList<Race>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
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
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.UserScoresEntry.TABLE_NAME,
                null,
                DatabaseHelper.UserScoresEntry.COLUMN_NAME_RACE_ID+"=" + raceId,
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

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.RacesEntry.TABLE_NAME,
                null,
                DatabaseHelper.RacesEntry._ID+"=" + raceId,
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

    /**
     * Returns a list of the Races that this user is participating in
     * @param userId Id of the user to find races for
     * @return Arraylist of the relevant Race objects
     */
    public ArrayList<Race> getRacesByUser(int userId) {
        ArrayList<Race> races = new ArrayList<Race>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.UserScoresEntry.TABLE_NAME,
                null,
                DatabaseHelper.UserScoresEntry.COLUMN_NAME_USER_ID + "=" + userId,
                new String[]{},
                null,
                null,
                null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0, n = cursor.getCount(); i < n; i++) {
                // For each UserScore record, query for the corresponding Race using the raceID
                int raceId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.UserScoresEntry.COLUMN_NAME_RACE_ID));
                Race race = getRace(raceId);
                races.add(race);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return races;
    }

    public int getScoreByRaceAndUser(int raceId, int userId) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.UserScoresEntry.TABLE_NAME,
                null,
                DatabaseHelper.UserScoresEntry.COLUMN_NAME_RACE_ID + "=" + raceId + " AND " +
                        DatabaseHelper.UserScoresEntry.COLUMN_NAME_USER_ID + "=" + userId,
                new String[]{},
                null,
                null,
                null);
        if (cursor.getCount() <= 0) {
            throw new RuntimeException("Couldn't find the user score in the database");
        }
        cursor.moveToFirst();
        int score = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.UserScoresEntry.COLUMN_NAME_USER_SCORE));
        cursor.close();
        return score;
    }

    public void insertNewRace(Race newRace) {

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = newRace.getValues();

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                DatabaseHelper.RacesEntry.TABLE_NAME,
                null,
                values);
        newRace.setId((int) newRowId);
    }

    public void insertNewUser(User newUser) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = newUser.getValues();

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(DatabaseHelper.RacesEntry.TABLE_NAME, null, values);
        newUser.setId((int) newRowId);
    }

    /**
     *
     * @param raceId
     * @param userId
     * @param username
     * @param scoreUpdate The change that is being made to the user's score. E.g, if the user just scored 2 points from a purchase, scoreUpdate would be 2
     */
    public void updateUserScore(int raceId, int userId, String username, int scoreUpdate) {

        UserScore userScore = new UserScore();
        userScore.setRaceId(raceId);
        userScore.setUserId(userId);
        userScore.setUsername(username);
        userScore.setScore(scoreUpdate);
        ContentValues values = userScore.getValues();

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String Query = "SELECT * FROM " + DatabaseHelper.UserScoresEntry.TABLE_NAME +
                " WHERE " + DatabaseHelper.UserScoresEntry.COLUMN_NAME_RACE_ID + "=" + raceId +
                " AND " + DatabaseHelper.UserScoresEntry.COLUMN_NAME_USER_ID + "=" + userId;

        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            // First time the user has participated in this race - create a new UserScore record for it
            long newRowId = db.insert(DatabaseHelper.UserScoresEntry.TABLE_NAME, null, values);
        } else {
            // Update the user score with the provided score update
            db.update(DatabaseHelper.UserScoresEntry.TABLE_NAME, values,
                    DatabaseHelper.UserScoresEntry.COLUMN_NAME_RACE_ID+"=" + raceId + " AND " +
                    DatabaseHelper.UserScoresEntry.COLUMN_NAME_USER_ID+"=" + userId,
                    new String []{});
        }
    }

    public void updateRace(Race updatedRace) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = updatedRace.getValues();
        // Update the user score with the provided score update
        db.update(DatabaseHelper.RacesEntry.TABLE_NAME, values,
                DatabaseHelper.RacesEntry._ID +"=" + updatedRace.getId(),
                new String []{});

    }

    /*

    Use this line to access the DB:
    FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());

    The operations we will need for this app

    getAllRaces()
    getUserScoresByRace(int raceID)
    getRace(int raceID)
    getsRacesByUser(int userID)
    getUserScoreForRace(int raceId, int userId)

    insertNewRace(Race newRace)
    insertNewUser(User newUser)
    updateUserScore(int userID, int scoreUpdate)    //
    updateRace(Race updatedRace)



     */



}
