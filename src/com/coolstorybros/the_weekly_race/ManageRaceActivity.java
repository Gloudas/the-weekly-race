package com.coolstorybros.the_weekly_race;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import com.coolstorybros.the_weekly_race.data.DatabaseManager;
import com.coolstorybros.the_weekly_race.data.Race;

public class ManageRaceActivity extends Activity {

    // for the prototype, just have a static Race variable that the user "creates"
    private static Race mRace = null;
	final Context context = this;
	
	ScrollView mCreateRace;
	ScrollView mEditRace;
    Button mCreateRaceButton;
    Button mEditRaceButton;
    EditText startDateField, endDateField;
	Calendar startDate, endDate;
	int val;
	boolean emptyFlag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_race);

        mCreateRace = (ScrollView) findViewById(R.id.createRaces_form_list);
        mEditRace = (ScrollView) findViewById(R.id.editRaces_form_list);
        mCreateRaceButton = (Button) findViewById(R.id.button_create_race);
        mEditRaceButton = (Button) findViewById(R.id.button_edit_race);
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        
        if(mRace != null)
        {
		    startDateField = (EditText) findViewById(R.id.EditTextRaceStartDate_e);
		    endDateField = (EditText) findViewById(R.id.EditTextRaceEndDate_e);
        }
        else
        {
        	startDateField = (EditText) findViewById(R.id.EditTextRaceStartDate);
		    endDateField = (EditText) findViewById(R.id.EditTextRaceEndDate);
        }
        
        updateViews();
        
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        	@Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                    int dayOfMonth) {
        			if(val==1)
        			{
	                 	startDate.set(Calendar.YEAR, year);
	                    startDate.set(Calendar.MONTH, monthOfYear);
	                    startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        			}
        			else
        			{
        				endDate.set(Calendar.YEAR, year);
	                    endDate.set(Calendar.MONTH, monthOfYear);
	                    endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        			}
                    updateLabel(val);
            }
        };
        
        startDateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ManageRaceActivity.this, date, startDate
                        .get(Calendar.YEAR), startDate.get(Calendar.MONTH),
                        startDate.get(Calendar.DAY_OF_MONTH)).show();
                val=1;
            }
        });
        endDateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ManageRaceActivity.this, date, endDate
                        .get(Calendar.YEAR), endDate.get(Calendar.MONTH),
                        endDate.get(Calendar.DAY_OF_MONTH)).show();
                val=2;
            }
        });
    }

    private void updateLabel(int val) {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if(val==1)
        	startDateField.setText(sdf.format(startDate.getTime()));
        else
        	endDateField.setText(sdf.format(endDate.getTime()));

    }
    
    /**
     * Called whenever there is a change to data that requires all views to be updated
     * This will be used later on to add things like user score-update requests
     */
    private void updateViews() {
        if (mRace != null) {
            mCreateRace.setVisibility(View.GONE);
            mEditRace.setVisibility(View.VISIBLE);

            EditText raceTitle = (EditText) findViewById(R.id.EditTextRaceTitle_e);
            raceTitle.setText(mRace.getTitle());
            
            TextView remainingTime = (TextView) findViewById(R.id.remainingTime);
            remainingTime.setText("End date: " + (mRace.getEndDate().get(Calendar.MONTH)+1) + "/" + ((Integer)mRace.getEndDate().get(Calendar.DAY_OF_MONTH)).toString() + "/" + ((Integer)mRace.getEndDate().get(Calendar.YEAR)).toString());
            
            EditText startDate = (EditText) findViewById(R.id.EditTextRaceStartDate_e);
            startDate.setText((mRace.getStartDate().get(Calendar.MONTH)+1) + "/" + ((Integer)mRace.getStartDate().get(Calendar.DAY_OF_MONTH)).toString() + "/" + ((Integer)mRace.getStartDate().get(Calendar.YEAR)).toString());
            
            EditText endDate = (EditText) findViewById(R.id.EditTextRaceEndDate_e);
            endDate.setText((mRace.getEndDate().get(Calendar.MONTH)+1) + "/" + ((Integer)mRace.getEndDate().get(Calendar.DAY_OF_MONTH)).toString() + "/" + ((Integer)mRace.getEndDate().get(Calendar.YEAR)).toString());
            
            EditText raceDetails = (EditText) findViewById(R.id.EditTextRaceDetails_e);
            raceDetails.setText(mRace.getDetails());
            
            EditText racePrize = (EditText) findViewById(R.id.EditTextRacePrizes_e);
            racePrize.setText(mRace.getPrize());
            
            EditText location = (EditText) findViewById(R.id.EditTextRaceLocation_e);
            location.setText(mRace.getLocation());
            
            EditText numWinners = (EditText) findViewById(R.id.EditTextRaceWinners_e);
            numWinners.setText("" + mRace.getNumWinners());
            
            /*
            TextView noCurrentRace = (TextView) findViewById(R.id.current_races_empty_text);
            noCurrentRace.setVisibility(View.GONE);*/
        } else {
            mCreateRace.setVisibility(View.VISIBLE);
            mEditRace.setVisibility(View.GONE);
        }
    }

    /**
     * Called when the user creates a new race
     * @param r The newly created race (as defined by the "Create Race" screen)
     */
    public static void setRace(Race r) {
        mRace = r;
    }
    
    public static Race getRace(){
    	return mRace;
    }

    
    public void createRaceButtonClicked(View v) {
    	emptyFlag = false;
    	
    	final EditText titleField = (EditText) findViewById(R.id.EditTextRaceTitle);  
    	String title = titleField.getText().toString();
    	if (title.equals("")){
    		emptyFlag = true;
    	}

    	final EditText locationField = (EditText) findViewById(R.id.EditTextRaceLocation);  
    	String location = locationField.getText().toString();  
    	if (location.equals("")){
    		emptyFlag = true;
    	}
    	
    	final EditText detailsField = (EditText) findViewById(R.id.EditTextRaceDetails);  
    	String details = detailsField.getText().toString();  
    	if (details.equals("")){
    		emptyFlag = true;
    	}
    	
    	String startdatestring = startDateField.getText().toString();
    	if (startdatestring.equals(""))
    		emptyFlag = true;
    	
    	String enddatestring = endDateField.getText().toString();
    	if (enddatestring.equals(""))
    		emptyFlag = true;
    	
    	final EditText prizeField = (EditText) findViewById(R.id.EditTextRacePrizes);  
    	String prize = prizeField.getText().toString();
    	if (title.equals("")){
    		emptyFlag = true;
    	}
    	
    	final EditText winnersField = (EditText) findViewById(R.id.EditTextRaceWinners);  
    	String winnersString = winnersField.getText().toString();
    	int winners = 0;
    	if (winnersString.equals("")){
    		emptyFlag = true;
    	} else {
    		winners = Integer.parseInt(winnersString);
    	}
    	
    	if (emptyFlag){
    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    		alertDialogBuilder.setTitle("Attention!");
     
    		alertDialogBuilder
    			.setMessage("Please make sure you have filled all the fields. All of the information is required!")
    			.setNegativeButton("Go back!", new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						// if this button is clicked, close
    						// current activity
    						dialog.cancel();
    					}
    			});
    				
    		AlertDialog alertDialog = alertDialogBuilder.create();
    		alertDialog.show();
    	} else {
    		
    		Race newRace = new Race(title, location, details, startDate, endDate, prize, winners);
    		ManageRaceActivity.setRace(newRace);
    		
    		updateViews();
    		
    		Intent intent = new Intent(this, RaceDetailsActivity.class);
    		startActivity(intent);
    	}
    }
    
    public void editRaceButtonClicked(View v) {
    	emptyFlag = false;
    	
    	final EditText titleField = (EditText) findViewById(R.id.EditTextRaceTitle_e);  
    	String title = titleField.getText().toString();
    	if (title.equals("")){
    		emptyFlag = true;
    	}

    	final EditText locationField = (EditText) findViewById(R.id.EditTextRaceLocation_e);  
    	String location = locationField.getText().toString();  
    	if (location.equals("")){
    		emptyFlag = true;
    	}
    	
    	final EditText detailsField = (EditText) findViewById(R.id.EditTextRaceDetails_e);  
    	String details = detailsField.getText().toString();  
    	if (details.equals("")){
    		emptyFlag = true;
    	}
    	
    	String startdatestring = startDateField.getText().toString();
    	if (startdatestring.equals(""))
    		emptyFlag = true;
    	
    	String enddatestring = endDateField.getText().toString();
    	if (enddatestring.equals(""))
    		emptyFlag = true;
    	
    	final EditText prizeField = (EditText) findViewById(R.id.EditTextRacePrizes_e);  
    	String prize = prizeField.getText().toString();
    	if (title.equals("")){
    		emptyFlag = true;
    	}
    	
    	final EditText winnersField = (EditText) findViewById(R.id.EditTextRaceWinners_e);  
    	String winnersString = winnersField.getText().toString();
    	int winners = 0;
    	if (winnersString.equals("")){
    		emptyFlag = true;
    	} else {
    		winners = Integer.parseInt(winnersString);
    	}
    	
    	if (emptyFlag){
    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    		alertDialogBuilder.setTitle("Attention!");
     
    		alertDialogBuilder
    			.setMessage("Please make sure you have filled all the fields. All of the information is required!")
    			.setNegativeButton("Go back!", new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {
    						// if this button is clicked, close
    						// current activity
    						dialog.cancel();
    					}
    			});
    				
    		AlertDialog alertDialog = alertDialogBuilder.create();
    		alertDialog.show();
    	} else {
    		
    		Race newRace = new Race(title, location, details, startDate, endDate, prize, winners);
    		ManageRaceActivity.setRace(newRace);

            // Insert the newly created race into the database - this will set newRace's ID variable
            // order is important here - need the race to be inserted into database before setting a user as the owner
            DatabaseManager dbManager = new DatabaseManager(this);
            dbManager.insertNewRace(newRace);
            dbManager.setRaceOwner(newRace.getId(), WeeklyRaceApplication.getCurrentUser());
    		
    		updateViews();
    		
    		Intent intent = new Intent(this, RaceDetailsActivity.class);
            intent.putExtra(RaceDetailsActivity.RACE_ID_EXTRA_KEY, newRace.getId());
    		startActivity(intent);
    	}
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
