package com.coolstorybros.the_weekly_race.data;

import android.content.Context;
import java.util.ArrayList;

public class DatabaseManager {

    private static DatabaseHelper sDbHelper;

    /**
     * Construct a database manager to access the database
     * @param context
     */
    public DatabaseManager(Context context) {
        if (sDbHelper == null) {
            sDbHelper = new DatabaseHelper(context);
        } 
    }

    /**
     * Returns a list of all the Races in the database (used by SearchRaces screen)
     * @return ArrayList<Race>
     */
    public ArrayList<Race> getAllRaces() {
        return sDbHelper.getAllRaces();
    }

    /**
     * Given a race, returns a list of all the participating users and their scores
     *
     * @param raceId The id of the race
     * @return List of UserScore objects which contain information about each user and their score for this race
     */
    public ArrayList<UserScore> getUserScoresByRace(int raceId) {
        return sDbHelper.getUserScoresByRace(raceId);
    }

    /**
     * Returns the information for this Race from the database
     * @param raceId Id of the race
     * @return Race object representing the database information for this race
     */
    public Race getRace(int raceId) {
        return sDbHelper.getRace(raceId);
    }

    /**
     * Returns the information for this User from the database
     * @param userId Id of the user
     * @return User object representing the database information for this user
     */
    public User getUser(int userId) {
        return sDbHelper.getUser(userId);
    }

    /**
     * Returns a list of the Races that this user is participating in
     *
     * @param userId Id of the user to find races for
     * @return ArrayList of Race objects
     */
    public ArrayList<Race> getRacesByUser(int userId) {
        return sDbHelper.getRacesByUser(userId);
    }

    /**
     * Retrieves a user's score for a particular race.
     *
     * @param raceId Id of the race to get the score from
     * @param userId Id of the user that is participating in this race
     * @return int representing the current score of the user
     */
    public int getScoreByRaceAndUser(int raceId, int userId) {
        return sDbHelper.getScoreByRaceAndUser(raceId, userId);
    }

    /**
     * Creates a new Race entry in the database for a given race
     * @param newRace A Race object representing the values to be inserted into the database
     */
    public void insertNewRace(Race newRace) {
        sDbHelper.insertNewRace(newRace);
    }

    /**
     * Creates a new User entry in the database for a user
     * @param newUser A User object representing the values to be inserted into the database
     */
    public void insertNewUser(User newUser) {
        sDbHelper.insertNewUser(newUser);
    }

    /**
     * Updates the user score for a given race.
     * If this is the user's first time participating in the race, it will create a new UserScore entry in the database with scoreUpdate as the starting score
     *
     * Examples:
     * To add first myUser to a race with a starting score of 2, you would call updateUserScore(race.getId(), myUser.getId(), myUser.getUsername(), 2);
     * To add 5 more to myUser's score, you would call updateUserScore(race.getId(), myUser.getId(), myUser.getUsername(), 5)
     *
     * @param raceId Id of the race
     * @param userId Id of the user
     * @param username The user's username
     * @param scoreUpdate The change that is being made to the user's score. E.g, if the user just scored 3 points from a purchase, scoreUpdate would be 3
     */
    public void updateUserScore(int raceId, int userId, String username, int scoreUpdate) {
        sDbHelper.updateUserScore(raceId, userId, username, scoreUpdate);
    }

    /**
     * Updates the database entry for this particular race with its new values.
     *
     * @param updatedRace A Race object representing the updated values to be put into the database
     */
    public void updateRace(Race updatedRace) {
        sDbHelper.updateRace(updatedRace);
    }

    /**
     * Updates the database entry for this particular user with its new values.
     *
     * @param updatedUser A User object representing the updated values to be put into the database
     */
    public void updateUser(User updatedUser) {
        sDbHelper.updateUser(updatedUser);
    }

    /**
     * Assigns the provided user as the creator of this race.
     * This will both update the provided User instance and the User's corresponding database record
     *
     * @param raceId Id of the race
     * @param user The user object to be set as the race's creator
     */
    public void setRaceOwner(int raceId, User user) {
        if (raceId <= 0) {
            throw new RuntimeException("Passed an invalid raceId when setting the race owner");
        }
        sDbHelper.setRaceOwner(raceId, user);
    }

}
