package com.coolstorybros.the_weekly_race;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import com.coolstorybros.the_weekly_race.data.DatabaseManager;
import com.coolstorybros.the_weekly_race.data.Race;
import com.coolstorybros.the_weekly_race.data.User;

public class RaceDetailsActivity extends Activity {

    public final static String RACE_ID_EXTRA_KEY = "raceId";

    TextView mRaceTitle;
    TextView mRemainingTime;
    TextView mRaceDetails;
    TextView mRaceWinners;
    TextView mRacePrize;
    TextView mLocation;

    Race mRace;
    int mRaceId;
    boolean mIsOwner = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_details);

        DatabaseManager dbManager = new DatabaseManager(this);
        Intent intent = getIntent();
        int intentValue = intent.getIntExtra("raceId", -1);
        if (intentValue <= 0) {
            // Activity was passed an invalid race id
            finish();
        } else {
            mRaceId = intentValue;
            mRace = dbManager.getRace(mRaceId);
        }
        User currentUser = WeeklyRaceApplication.getCurrentUser();
        if (currentUser.getCreatedRaceId() == mRaceId) {
            mIsOwner = true;
        } else {
            mIsOwner = false;
        }

        initUi();
    }

    public void initUi() {
        if (mRace != null) {

            if (!mIsOwner) {
                // TODO -- user score stuff
            }

            mRaceTitle = (TextView) findViewById(R.id.raceTitle);
            mRaceTitle.setText(mRace.getTitle());

            mRemainingTime = (TextView) findViewById(R.id.remainingTime);
            mRemainingTime.setText("End date: " + (mRace.getEndDate().get(Calendar.MONTH)+1) + "/" + ((Integer)mRace.getEndDate().get(Calendar.DAY_OF_MONTH)).toString() + "/" + ((Integer)mRace.getEndDate().get(Calendar.YEAR)).toString());

            mRaceDetails = (TextView) findViewById(R.id.raceDetails);
            mRaceDetails.setText(mRace.getDetails());

            mRaceWinners = (TextView) findViewById(R.id.raceWinners);
            mRaceWinners.setText("" + mRace.getNumWinners());

            mRacePrize = (TextView) findViewById(R.id.prize);
            mRacePrize.setText(mRace.getPrize());

            mLocation = (TextView) findViewById(R.id.location);
            mLocation.setText(mRace.getLocation());

            // TODO - populate the leaderboard
        }
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return false;
    } */

}
