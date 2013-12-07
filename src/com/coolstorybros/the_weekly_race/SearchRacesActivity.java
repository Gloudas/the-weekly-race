package com.coolstorybros.the_weekly_race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.coolstorybros.the_weekly_race.data.DatabaseManager;
import com.coolstorybros.the_weekly_race.data.Race;

import java.util.Calendar;

public class SearchRacesActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_races);
    }

    /**
     * Pressed when the "Your Current Races" button is clicked
     * @param v Button that was clicked
     */
    public void currentRacesButtonClicked(View v) {
        Intent intent = new Intent(this, CurrentRacesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /**
     * Pressed when the "Search for Races" button is clicked
     * @param v Button that was clicked
     */
    public void searchForRacesButtonClicked(View v) {
        // Already on the Search Races activity
    }

    /**
     * Pressed when the "Create/Manage Races" button is clicked
     * @param v Button that was clicked
     */
    public void createManageRaceButtonClicked(View v) {
        Intent intent = new Intent(this, ManageRaceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }



    /*      DEBUGGING      */
    public void insertNewRaceClicked(View v) {
        DatabaseManager dbManager = new DatabaseManager(this);

        String title = "La Vals Pizza Challenge";
        String location = "La Vals on Euclid";
        String details = "Each medium pizza gets you 1 point. Eat a lot.";
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 12, 12);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2014, 12, 12);
        String prize = "You get a whole lot of chicken wings!";
        int numWinners = 4;
        Race race = new Race(title, location, details, startDate, endDate, prize, numWinners);
        dbManager.insertNewRace(race);
    }

    public void getRaceClicked(View v) {

        DatabaseManager dbManager = new DatabaseManager(this);
        Race race = dbManager.getRace(1);
    }

    public void updateRaceClicked(View v) {

        DatabaseManager dbManager = new DatabaseManager(this);
        Race race = dbManager.getRace(1);
        race.setDetails("THIS HAS BEEN UPDATED");
        dbManager.updateRace(race);
    }
}
