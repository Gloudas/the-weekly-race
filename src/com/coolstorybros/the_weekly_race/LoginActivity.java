package com.coolstorybros.the_weekly_race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.coolstorybros.the_weekly_race.data.DatabaseManager;
import com.coolstorybros.the_weekly_race.data.User;

public class LoginActivity extends Activity
{
    Button mLoginButton;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.button_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonClicked();
            }
        });
	}


    private void loginButtonClicked() {

        // Set the application's logged in user
        DatabaseManager dbManager = new DatabaseManager(this);
        User currentUser = dbManager.getUser(1); // just get the first user in the database for prototype purposes
        WeeklyRaceApplication.setCurrentUser(currentUser);

        Intent intent = new Intent(this, CurrentRacesActivity.class);
        startActivity(intent);
    }
}
