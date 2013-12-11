package com.coolstorybros.the_weekly_race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.coolstorybros.the_weekly_race.data.Race;

import java.util.ArrayList;

public class CurrentRacesActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner mSortBySpinner;
    TextView mCurrentRacesEmptyText;
    ListView mCurrentRacesList;

    // The list of race objects that we populate from the database and then display on screen
    ArrayList<Race> mRaces;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_races);

        mCurrentRacesList = (ListView) findViewById(R.id.current_races_list);
        mCurrentRacesEmptyText = (TextView) findViewById(R.id.current_races_empty_text);
        mCurrentRacesList.setEmptyView(mCurrentRacesEmptyText);
        // TODO - create an adapter that populates this list with appropriate views for each current race

        /*
        // Setup the "Sort By" spinner button
        mSortBySpinner = (Spinner) findViewById(R.id.currentRaces_sort_by_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_by_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortBySpinner.setAdapter(adapter);
        */
    }

    /**
     * Method where we update all View values, including the list of displayed Races
     */
    public void initUi() {

    }

    // Called when an item in the "Sort By" spinner is selected
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using parent.getItemAtPosition(pos)
        // For the prototype, don't actually handle this. Maybe implement it later.
    }

    // This method is needed for the OnItemSelected interface but isn't actually used
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * Pressed when the "Your Current Races" button is clicked
     * @param v Button that was clicked
     */
    public void currentRacesButtonClicked(View v) {
        // Already on the Current Races activity
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
        Intent intent = new Intent(this, ManageRaceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
