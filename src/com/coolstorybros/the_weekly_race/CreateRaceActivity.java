package com.coolstorybros.the_weekly_race;

import java.util.Calendar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateRaceActivity extends Activity {
	
	Button mCreateRaceButton;
	
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
    	final EditText titleField = (EditText) findViewById(R.id.EditTextRaceTitle);  
    	String title = titleField.getText().toString();

    	final EditText locationField = (EditText) findViewById(R.id.EditTextRaceLocation);  
    	String location = locationField.getText().toString();  

    	final EditText detailsField = (EditText) findViewById(R.id.EditTextRaceDetails);  
    	String details = detailsField.getText().toString();  
    	
    	final EditText startDateField = (EditText) findViewById(R.id.EditTextRaceStartDate);  
    	String startdatestring = startDateField.getText().toString();
    	String[] startdatevals = startdatestring.split("[/]");
    	int startmonth = Integer.parseInt(startdatevals[0]) - 1; //January is 0
    	int startday = Integer.parseInt(startdatevals[1]);
    	int startyear = Integer.parseInt(startdatevals[2]);
    	
    	final EditText endDateField = (EditText) findViewById(R.id.EditTextRaceEndDate);  
    	String enddatestring = endDateField.getText().toString();
    	String[] enddatevals = enddatestring.split("[/]");
    	int endmonth = Integer.parseInt(enddatevals[0]) - 1; //January is 0
    	int endday = Integer.parseInt(enddatevals[1]);
    	int endyear = Integer.parseInt(enddatevals[2]);
    	

    	final EditText prizeField = (EditText) findViewById(R.id.EditTextRacePrizes);  
    	String prize = prizeField.getText().toString();
    	
    	final EditText winnersField = (EditText) findViewById(R.id.EditTextRaceWinners);  
    	int winners = Integer.parseInt(winnersField.getText().toString());

    	Calendar startdate = Calendar.getInstance();
    	startdate.set(startyear, startmonth, startday);
    	
    	Calendar enddate = Calendar.getInstance();
    	enddate.set(endyear, endmonth, endday);
    	
    	Race newRace = new Race(title, location, details, startdate, enddate, prize, winners);
    	ManageRaceActivity.setRace(newRace);
    	
    	Intent intent = new Intent(this, RaceDetailsActivity.class);
        startActivity(intent);
    }
}
