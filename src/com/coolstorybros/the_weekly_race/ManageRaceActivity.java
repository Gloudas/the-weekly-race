package com.coolstorybros.the_weekly_race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageRaceActivity extends Activity {

    // for the prototype, just have a static Race variable that the user "creates"
    public static Race mRace = null;

    Button mCreateRaceButton;
    Button mEditRaceButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_race);

        mCreateRaceButton = (Button) findViewById(R.id.button_create_new_race);
        mEditRaceButton = (Button) findViewById(R.id.button_edit_weekly_race);
        updateViews();
    }

    /**
     * Called whenever there is a change to data that requires all views to be updated
     * This will be used later on to add things like user score-update requests
     */
    private void updateViews() {
        if (mRace != null) {
            mCreateRaceButton.setVisibility(View.GONE);
            mEditRaceButton.setVisibility(View.VISIBLE);
        } else {
            mCreateRaceButton.setVisibility(View.VISIBLE);
            mEditRaceButton.setVisibility(View.GONE);
        }
    }

    /**
     * Called when the user creates a new race
     * @param r The newly created race (as defined by the "Create Race" screen)
     */
    public void setRace(Race r) {
        mRace = r;
    }

    public void createRaceButtonClicked(View v) {
        Intent intent = new Intent(this, CreateRaceActivity.class);
        startActivity(intent);
    }

    public void editRaceButtonClicked(View v) {
        Intent intent = new Intent(this, EditRaceActivity.class);
        startActivity(intent);
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
        Intent intent = new Intent(this, SearchRacesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /**
     * Pressed when the "Create/Manage Races" button is clicked
     * @param v Button that was clicked
     */
    public void createManageRaceButtonClicked(View v) {
        // Already on the Manage Races activity
    }
}
