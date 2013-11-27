package com.coolstorybros.the_weekly_race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class RaceDetailsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_details);
        
        Race mRace = ManageRaceActivity.getRace(); 
        
        TextView raceTitle = (TextView) findViewById(R.id.raceTitle);
        raceTitle.setText(mRace.getTitle());
        
        TextView remainingTime = (TextView) findViewById(R.id.remainingTime);
        remainingTime.setText("End date: " + ((Integer)mRace.getEndDate().MONTH).toString() + "/" + ((Integer)mRace.getEndDate().DAY_OF_MONTH).toString() + "/" + ((Integer)mRace.getEndDate().YEAR).toString());
        
        TextView raceDetails = (TextView) findViewById(R.id.raceDetails);
        raceDetails.setText(mRace.getDetails());
        
        TextView racePrize = (TextView) findViewById(R.id.prize);
        racePrize.setText(mRace.getPrize());
        
        TextView location = (TextView) findViewById(R.id.location);
        location.setText(mRace.getLocation());
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(this, ManageRaceActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
