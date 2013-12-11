package edu.berkeley.cs160.bonghyunkim.getawaycam;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ViewModeActivity extends Activity {
	private final static String DIRECTORY_NAME = "GACC_DIRECTORY";
    private LinearLayout photoGallery;
    private SharedPreferences preferences;
	Location currentLocation;
    private FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT);
    public static final File MEDIA_STORAGE_DIR = new File(
            Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), DIRECTORY_NAME);

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mode_activity);
        photoGallery = (LinearLayout) findViewById(R.id.photo_gallery_layout);
    	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String providerName = locationManager.getBestProvider(new Criteria(), true);
        currentLocation = locationManager.getLastKnownLocation(providerName);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadPhotos();
    }
    
    private void loadPhotos() {
        for (File photo : MEDIA_STORAGE_DIR.listFiles()) {
        	if (photo.isFile()) {
        		Log.d("asdf","a");
                String photoJson = preferences.getString(photo.getAbsolutePath(), null);
        		Log.d("asdf","b");
                try {
					JSONObject locationJson = new JSONObject(photoJson);
	        		Log.d("asdf","c");
	            	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        		Log.d("asdf","d");
	            	String providerName = locationManager.getBestProvider(new Criteria(), true);
	        		Log.d("asdf","e");
	            	Location photoLocation = new Location(providerName);
	        		Log.d("asdf","f");
	            	photoLocation.setLatitude(locationJson.getDouble("LAT"));
	        		Log.d("asdf","g");
	            	photoLocation.setLongitude(locationJson.getDouble("LONG"));
	        		Log.d("asdf","h");
	            	float distance = photoLocation.distanceTo(currentLocation);
	                if (distance > 1000) {
	            		Log.d("asdf","i");
	                	showPhoto(photo);
	                } else {
	            		Log.d("asdf","j");
	                	showNothing();
	                }
				} catch (JSONException e) {
					e.printStackTrace();
				}		
        	}
        }
        
    }
    
    void showPhoto(File photo) {
        ImageView photoView = new ImageView(this);
        Uri uri = Uri.fromFile(photo);
        photoView.setImageURI(uri);
        photoView.setVisibility(View.VISIBLE);
        photoGallery.addView(photoView, params);
    }
    
    void showNothing() {
        ImageView nopic = new ImageView(this);
        nopic.setImageDrawable(getResources().getDrawable(R.drawable.nopic));
        photoGallery.addView(nopic, params);
    }
    
}
