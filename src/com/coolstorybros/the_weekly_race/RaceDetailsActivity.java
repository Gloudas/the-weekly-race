package com.coolstorybros.the_weekly_race;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.coolstorybros.the_weekly_race.data.*;

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
    LinearLayout mLinearLayoutLeaderboard;

    Race mRace;
    int mRaceId;
    UserScore mUserScore;
    ArrayList<UserScore> mUserScores;
    boolean mIsOwner = false;
    UserScoreAdapter mLeaderboardAdapter;

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

            // Populate the leaderboard
            mLinearLayoutLeaderboard = (LinearLayout) findViewById(R.id.linearLayout_leaderboard);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (UserScore score : mUserScores) {
                View rowView = inflater.inflate(R.layout.user_score_list_item,null);
                TextView username = (TextView) rowView.findViewById(R.id.user_name);
                username.setText(score.getUsername());
                TextView userScore = (TextView) rowView.findViewById(R.id.user_score);
                userScore.setText("Score: " + score.getScore());
                mLinearLayoutLeaderboard.addView(rowView);
            }
        }
    }

    public void scanPurchasePressed(View v) {
        takePicture();
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        defaultUpdateUserScore();
        mLeaderboardAdapter.notifyDataSetChanged();
        init();
    }

    private void defaultUpdateUserScore() {

        if (mRace != null) {
            User currentUser = WeeklyRaceApplication.getCurrentUser();
            DatabaseManager dbManager = new DatabaseManager(this);
            dbManager.updateUserScore(mRace.getId(), currentUser.getId(), currentUser.getUsername(), 1);
        }
    }

}
