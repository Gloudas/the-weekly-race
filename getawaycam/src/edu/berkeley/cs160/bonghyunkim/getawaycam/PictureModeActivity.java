package edu.berkeley.cs160.bonghyunkim.getawaycam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PictureModeActivity extends Activity {
	private final static String DIRECTORY_NAME = "GACC_DIRECTORY";
	private final int CAPTURE_PHOTO_REQUEST = 100;
	private final String FILE_PREFIX = "GAC";
	private final String API_KEY = "1bd95d88de188f4167ce4acb29e55c12";
	private File photoPath;
    private SharedPreferences preferences;
	Location currentLocation;
	RelativeLayout nextButtonLayout;
    public static final File MEDIA_STORAGE_DIR = new File(
            Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), DIRECTORY_NAME);
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_mode_activity);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ensureMediaStorageDirExists();
        takePhoto();
        nextButtonLayout = (RelativeLayout)findViewById(R.id.nextButtonLayout); 
		nextButtonLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nextButtonClicked(v);
			}
		});
    }
    
   private void ensureMediaStorageDirExists() {
       if (! MEDIA_STORAGE_DIR.exists()){
           if (! MEDIA_STORAGE_DIR.mkdirs()){
        	   Log.d("ERROR", "ERROR");
           }
       }
   }
    
    public void nextButtonClicked(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
    private void takePhoto() {
        Intent capturePhotoIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        String timeStamp = new SimpleDateFormat("yyyyMM_ddHHmmss").format(new Date());
        String imageFileName = FILE_PREFIX + timeStamp + "_";
        try {
			File file = File.createTempFile(imageFileName, ".jpg", MEDIA_STORAGE_DIR);
	        photoPath = file;
	        capturePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
	        startActivityForResult(capturePhotoIntent, CAPTURE_PHOTO_REQUEST);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_PHOTO_REQUEST) {
            if (resultCode == RESULT_OK) {
            	savePhoto();
            	saveLocation();
            	showRelatedPhoto();
            }
        }
    }
    
    private void savePhoto() {
    	Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
    	Log.d("ASDF",photoPath.getAbsolutePath());
        Uri photoUri = Uri.fromFile(photoPath);
        mediaScanIntent.setData(photoUri);
        this.sendBroadcast(mediaScanIntent);
    }
 
    private void saveLocation() {
    	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String providerName = locationManager.getBestProvider(new Criteria(), true);
        currentLocation = locationManager.getLastKnownLocation(providerName);
        Editor editor = preferences.edit();
        JSONObject locationJson = new JSONObject();
        try {
			locationJson.put("LAT", currentLocation.getLatitude());
	        locationJson.put("LONG", currentLocation.getLongitude());
		} catch (JSONException e) {
			e.printStackTrace();
		}
        editor.putString(photoPath.getAbsolutePath(), locationJson.toString());
        editor.commit();
    }
    
    private void showRelatedPhoto() {
		String url = "http://api.flickr.com/services/rest/?" + "method=flickr.photos.search&api_key=" + API_KEY +
				"&lat=" + Double.toString(currentLocation.getLatitude()) + 
				"&lon=" + Double.toString(currentLocation.getLongitude()) +
				"&radius=1&format=rest";
		FlickrRequest flickrTask = new FlickrRequest();
		flickrTask.execute(url);
    }
    
	private class FlickrRequest extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        
		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			HttpResponse response = null;
			HttpClient httpclient = new DefaultHttpClient();
			String responseJson = "";

			try {
				response = httpclient.execute(new HttpGet(url));
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					responseJson = out.toString();
				} else{
					response.getEntity().getContent().close();
					throw new IOException(response.getStatusLine().getReasonPhrase());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return responseJson;
		}

		protected void onPostExecute(String response) {
			try {
				response = response.substring(response.indexOf("photo id")+10);
				String id = response.substring(0,response.indexOf("\""));
				response = response.substring(response.indexOf("owner")+7);
				String owner = response.substring(0,response.indexOf("\""));
				response = response.substring(response.indexOf("secret")+8);
				String secret = response.substring(0,response.indexOf("\""));
				response = response.substring(response.indexOf("server")+8);
				String server= response.substring(0,response.indexOf("\""));
				response = response.substring(response.indexOf("farm")+6);
				String farm = response.substring(0,response.indexOf("\""));
				String urlString = "http://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
				
				FindImageTask showPic = new FindImageTask();
				showPic.execute(urlString);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private class FindImageTask extends AsyncTask<String, Void, Drawable> {

			protected Drawable doInBackground(String... urls) {
				URL url;
				try {
					url = new URL(urls[0]);
					InputStream	content	=(InputStream)url.getContent();	
					Drawable d = Drawable.createFromStream(content,"src");
					return d;
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			protected void onPostExecute(Drawable d) {
				ImageView imageViewer = (ImageView)findViewById(R.id.viewRelatedPhoto);
				imageViewer.setImageDrawable(d);
			}
		}
		
	}
}
