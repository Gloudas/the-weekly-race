package com.coolstorybros.the_weekly_race.data;

import android.content.ContentValues;

public class User {

    private int mId;    // this represents the ID of this user entry in the "users" database
    private String mUsername;
    private String mPassword;
    // the id of the race that this user owns. Value is -1 if the user is not currently running a race.
    private int mCreatedRaceId = -1;

    public User(String username, String password, int createdRaceId) {
        mUsername = username;
        mPassword = password;
        mCreatedRaceId = createdRaceId;
    }

    /**
     * This method is called when the Race is actually inserted into database and assigned a record ID
     */
    public void setId(int id) {
        mId = id;
    }
    public int getId() {
        return mId;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public int getCreatedRaceId() {
        return mCreatedRaceId;
    }

    /**
     * Creates a ContentValues object to insert into the database
     * @return
     */
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.UsersEntry.COLUMN_NAME_USERNAME, getUsername());
        values.put(DatabaseHelper.UsersEntry.COLUMN_NAME_PASSWORD, getPassword());
        values.put(DatabaseHelper.UsersEntry.COLUMN_NAME_CREATED_RACE_ID, getCreatedRaceId());
        return values;
    }
}
