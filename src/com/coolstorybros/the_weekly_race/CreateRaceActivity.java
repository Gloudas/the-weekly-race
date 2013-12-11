package com.coolstorybros.the_weekly_race;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.coolstorybros.the_weekly_race.data.DatabaseManager;
import com.coolstorybros.the_weekly_race.data.Race;

public class CreateRaceActivity extends Activity {
	
	final Context context = this;
	Button mCreateRaceButton;
	
	boolean emptyFlag = false;
	boolean invalidDateFlag = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_race);
        
        mCreateRaceButton = (Button) findViewById(R.id.button_create_race);
    }
    
    /**
     * Pressed when the "Create new race" button is clicked
     * @param v Button that was clicked
     */
    public void createRaceButtonClicked(View v) {
    	emptyFlag = false;
    	invalidDateFlag = false;
    	
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
    	
    	int startmonth = 0, startday = 0, startyear = 0;
    	final EditText startDateField = (EditText) findViewById(R.id.EditTextRaceStartDate);  
    	String startdatestring = startDateField.getText().toString();
    	if (startdatestring.equals("")){
    		emptyFlag = true;
    	} else {
    		String[] startdatevals = startdatestring.split("[/]");
    		if (startdatevals.length == 3) {
    			startmonth = Integer.parseInt(startdatevals[0]) - 1; //January is 0
    			startday = Integer.parseInt(startdatevals[1]);
    			startyear = Integer.parseInt(startdatevals[2]);
    		} else {
    	    	invalidDateFlag = true;
    		}
    	}
    	
    	int endmonth = 0, endday = 0, endyear = 0;
    	final EditText endDateField = (EditText) findViewById(R.id.EditTextRaceEndDate);  
    	String enddatestring = endDateField.getText().toString();
    	if (enddatestring.equals("")){
    		emptyFlag = true;
    	} else {
        	String[] enddatevals = enddatestring.split("[/]");
    		if (enddatevals.length == 3) {
    			endmonth = Integer.parseInt(enddatevals[0]) - 1; //January is 0
    			endday = Integer.parseInt(enddatevals[1]);
    			endyear = Integer.parseInt(enddatevals[2]);
    		} else {
    	    	invalidDateFlag = true;
    		}
    	}

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
    	} else if (invalidDateFlag) {
    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    		alertDialogBuilder.setTitle("Attention!");
     
    		alertDialogBuilder
    			.setMessage("Please make sure the dates that you entered are correct!")
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
    		Calendar startdate = Calendar.getInstance();
    		startdate.set(startyear, startmonth, startday);
    	
    		Calendar enddate = Calendar.getInstance();
    		enddate.set(endyear, endmonth, endday);
    		
    		Race newRace = new Race(title, location, details, startdate, enddate, prize, winners);
    		//ManageRaceActivity.setRace(newRace);

            // Insert the newly created race into the database - this will set newRace's ID variable
            // order is important here - need the race to be inserted into database before setting a user as the owner
            DatabaseManager dbManager = new DatabaseManager(this);
            dbManager.insertNewRace(newRace);
            dbManager.setRaceOwner(newRace.getId(), WeeklyRaceApplication.getCurrentUser());
    	
    		Intent intent = new Intent(this, RaceDetailsActivity.class);
    		startActivity(intent);
    	}
    }
}
