package edu.berkeley.cs160.bonghyunkim.getawaycam;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	RelativeLayout pictureModeLayout;
	RelativeLayout viewModeLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pictureModeLayout = (RelativeLayout)findViewById(R.id.pictureModeLayout);
		viewModeLayout = (RelativeLayout)findViewById(R.id.viewModeLayout);
		pictureModeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onPictureModeClicked(v);
			}
		});
		viewModeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onViewModeClicked(v);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    public void onPictureModeClicked(View v) {
        Intent intent = new Intent(this, PictureModeActivity.class);
        startActivity(intent);
    }

    public void onViewModeClicked(View v) {
        Intent intent = new Intent(this, ViewModeActivity.class);
        startActivity(intent);
    }
    
}

