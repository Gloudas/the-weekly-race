package com.coolstorybros.the_weekly_race.data;

import android.content.Context;
import java.util.ArrayList;

public class DatabaseManager {

    private static DatabaseHelper sDbHelper;

    public DatabaseManager(Context context) {
        if (sDbHelper == null) {
            sDbHelper = new DatabaseHelper(context);
        } 
    }

    public ArrayList<Race> getAllRaces() {
        return sDbHelper.getAllRaces();
    }

    public ArrayList<UserScore> getUserScoresByRace(int raceId) {
        return sDbHelper.getUserScoresByRace(raceId);
    }

    public Race getRace(int raceId) {
        return sDbHelper.getRace(raceId);
    }

    public User getUser(int userId) {
        return sDbHelper.getUser(userId);
    }

    /**
     * Returns a list of the Races that this user is participating in
     * @param userId Id of the user to find races for
     * @return Arraylist of the relevant Race objects
     */
    public ArrayList<Race> getRacesByUser(int userId) {
        return sDbHelper.getRacesByUser(userId);
    }

    public int getScoreByRaceAndUser(int raceId, int userId) {
        return sDbHelper.getScoreByRaceAndUser(raceId, userId);
    }

    public void insertNewRace(Race newRace) {
        sDbHelper.insertNewRace(newRace);
    }

    public void insertNewUser(User newUser) {
        sDbHelper.insertNewUser(newUser);
    }

    /**
     *
     * @param raceId
     * @param userId
     * @param username
     * @param scoreUpdate The change that is being made to the user's score. E.g, if the user just scored 2 points from a purchase, scoreUpdate would be 2
     */
    public void updateUserScore(int raceId, int userId, String username, int scoreUpdate) {
        sDbHelper.updateUserScore(raceId, userId, username, scoreUpdate);
    }

    public void updateRace(Race updatedRace) {
        sDbHelper.updateRace(updatedRace);
    }

    public void updateUser(User updatedUser) {
        sDbHelper.updateUser(updatedUser);
    }

    /**
     * Assigns the provided user as the creator of this race.
     * This will update the createdRaceId field in the provided variable and in the database
     * @param raceId Id of the race to be
     * @param user The user object to be set as the race creator
     */
    public void setRaceOwner(int raceId, User user) {
        if (raceId <= 0) {
            throw new RuntimeException("Passed an invalid raceId when setting the race owner");
        }
        sDbHelper.setRaceOwner(raceId, user);
    }

}
