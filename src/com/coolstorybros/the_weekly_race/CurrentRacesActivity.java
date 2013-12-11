package com.coolstorybros.the_weekly_race;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;

public class CurrentRacesActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener {

	 private ViewPager viewPager;
	 private CurrentRacesPagerAdapter mAdapter;
	 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_races);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        mAdapter = new CurrentRacesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

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
