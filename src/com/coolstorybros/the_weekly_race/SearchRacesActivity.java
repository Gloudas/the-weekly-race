package com.coolstorybros.the_weekly_race;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.coolstorybros.the_weekly_race.data.DatabaseManager;
import com.coolstorybros.the_weekly_race.data.Race;

import java.util.ArrayList;

public class SearchRacesActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_races);
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
        // Already on the Search Races activity
    }

    /**
     * Pressed when the "Create/Manage Races" button is clicked
     * @param v Button that was clicked
     */
    public void createManageRaceButtonClicked(View v) {
        Intent intent = new Intent(this, ManageRaceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    
    
    public void searchRaceClicked(View v) {
       EditText searchEditText   = (EditText)findViewById(R.id.searchQuery);
       String searchQuery = searchEditText.getText().toString();
       DatabaseManager dbManager = new DatabaseManager(this);
       
       ArrayList<Race> races = dbManager.getAllRaces();
       ArrayList<ArrayList<String>> convertedRaces = new ArrayList<ArrayList<String>>();
       for (Race race : races) {
    	   ArrayList<String> convertedRace = new ArrayList<String>();
    	   convertedRace.add(race.getTitle());
    	   convertedRace.add(race.getEndDateString());
           convertedRace.add(""+race.getId());
    	   convertedRaces.add(convertedRace);
       }
       
       MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, convertedRaces);
       
       ListView searchResultView = (ListView)findViewById(R.id.searchResult);
       searchResultView.setAdapter(adapter);
       
       InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
       imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

    public void raceDetailsClicked(int raceId) {
        Intent intent = new Intent(this, RaceDetailsActivity.class);
        intent.putExtra(RaceDetailsActivity.RACE_ID_EXTRA_KEY, raceId);
        startActivity(intent);
    }
    
    public class MySimpleArrayAdapter extends ArrayAdapter<ArrayList<String>> {
        private final Context context;
        private final ArrayList<ArrayList<String>> values;
        ArrayList<String> content;

        public MySimpleArrayAdapter(Context context, ArrayList<ArrayList<String>> values) {
            super(context, R.layout.search_result_text_row, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.search_result_text_row, parent, false);

            content = values.get(position);

            TextView textView1 = (TextView) rowView.findViewById(R.id.search_content_title);
            textView1.setText(content.get(0));
            TextView textView2 = (TextView) rowView.findViewById(R.id.search_content_end_date);
            textView2.setText("Ends " + content.get(1));

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    int raceId = Integer.parseInt(content.get(2));
                    raceDetailsClicked(raceId);
                }
            });

            return rowView;
        }

        @Override
        public int getCount() {
            return values.size();
        }

        @Override
        public ArrayList<String> getItem(int position) {
            return values.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    } 

}
