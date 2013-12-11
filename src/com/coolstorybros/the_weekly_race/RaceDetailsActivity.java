package com.coolstorybros.the_weekly_race;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.coolstorybros.the_weekly_race.data.DatabaseManager;
import com.coolstorybros.the_weekly_race.data.Race;
import com.coolstorybros.the_weekly_race.data.User;
import com.coolstorybros.the_weekly_race.data.UserScore;

public class RaceDetailsActivity extends Activity {

    public final static String RACE_ID_EXTRA_KEY = "raceId";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    LinearLayout mUserScoreLayout;
    TextView mTextViewUserScore;
    TextView mRaceTitle;
    TextView mRemainingTime;
    TextView mRaceDetails;
    TextView mRaceWinners;
    TextView mRacePrize;
    TextView mLocation;

    Race mRace;
    int mRaceId;
    UserScore mUserScore;
    ArrayList<UserScore> mUserScores;
    boolean mIsOwner = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_details);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    /**
     * Initialize database information and variables
     */
    public void init() {

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
        mUserScores = dbManager.getUserScoresByRace(mRaceId);
        for (UserScore score : mUserScores) {
            if (score.getUserId() == currentUser.getId()) {
                mUserScore = score;
            }
        }

        initUi();
    }

    public void initUi() {
        if (mRace != null) {

            mUserScoreLayout = (LinearLayout) findViewById(R.id.linearLayout_user_score);
            mTextViewUserScore = (TextView) findViewById(R.id.textView_user_score);
            mUserScoreLayout.setVisibility(View.VISIBLE);
            if (mIsOwner) {
                mUserScoreLayout.setVisibility(View.GONE);
            }
            if (mUserScore != null) {
                mTextViewUserScore.setText(""+mUserScore.getScore());
            } else {
                mTextViewUserScore.setText(""+0);
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

    public void scanPurchasePressed(View v) {
        takePicture();
    }

    private void takePicture() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /*
        File f = null;
        try {
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }
        */
        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                savePhotoToStorage();
                savePhotoLocation();
                loadNearbyPhoto();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                throw new RuntimeException("HEY DOUG IT MESSED UP");
            } else {
                // Image capture failed, advise user
                throw new RuntimeException("HEY DOUG IT MESSED UP");
            }
        }
        Toast.makeText(this, "location! latitude: " + mLastKnownLocation.getLatitude() + " longitude: " + mLastKnownLocation.getLongitude(), Toast.LENGTH_LONG).show();
        */
        defaultUpdateUserScore();
        init();
    }

    private void defaultUpdateUserScore() {

        if (mRace != null) {
            User currentUser = WeeklyRaceApplication.getCurrentUser();
            DatabaseManager dbManager = new DatabaseManager(this);
            dbManager.updateUserScore(mRace.getId(), currentUser.getId(), currentUser.getUsername(), 1);
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
