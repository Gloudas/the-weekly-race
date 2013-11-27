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
    	
    	final EditText detailsField = (EditText) findViewById(R.id.EditTextRaceDetails);  
    	String details = detailsField.getText().toString();  
    	
    	final EditText dateField = (EditText) findViewById(R.id.EditTextRaceDate);  
    	String datestring = dateField.getText().toString();
    	String[] datevals = datestring.split("[/]");
    	int month = Integer.parseInt(datevals[0]) - 1; //January is 0
    	int day = Integer.parseInt(datevals[1]);
    	int year = Integer.parseInt(datevals[2]);
    	
    	final EditText pointsField = (EditText) findViewById(R.id.EditTextRacePoints);  
    	int points = Integer.parseInt(pointsField.getText().toString());
    	
    	final EditText prizeField = (EditText) findViewById(R.id.EditTextRacePrizes);  
    	String prize = prizeField.getText().toString();
    	
    	final EditText winnersField = (EditText) findViewById(R.id.EditTextRaceWinners);  
    	int winners = Integer.parseInt(winnersField.getText().toString());

    	Calendar date = Calendar.getInstance();
    	date.set(year, month, day);
    	
    	Race newRace = new Race(title, details, date, points, prize, winners);

    	Intent intent = new Intent(this, ManageRaceActivity.class);
        startActivity(intent);
    }
}
