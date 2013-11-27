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
        
        TextView raceTitle = (TextView) findViewById(R.id.raceTitle);
        raceTitle.setText(ManageRaceActivity.mRace.getTitle());
        
        TextView remainingTime = (TextView) findViewById(R.id.remainingTime);
        remainingTime.setText("End date: ");
        
        TextView raceDetails = (TextView) findViewById(R.id.raceDetails);
        raceDetails.setText(ManageRaceActivity.mRace.getDetails());
        
        TextView racePrize = (TextView) findViewById(R.id.prize);
        racePrize.setText(ManageRaceActivity.mRace.getPrize());
        
        TextView location = (TextView) findViewById(R.id.location);
        location.setText("dummy location");
        
        
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
