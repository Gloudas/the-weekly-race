package com.coolstorybros.the_weekly_race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
}
