package com.coolstorybros.the_weekly_race;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import com.coolstorybros.the_weekly_race.data.DatabaseManager;
import com.coolstorybros.the_weekly_race.data.Race;

import java.util.ArrayList;

public class SearchRacesActivity extends Activity {

    MySimpleArrayAdapter mAdapter;
    DatabaseManager dbManager = new DatabaseManager(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_races);
        Spinner sortSpinner = (Spinner)findViewById(R.id.search_sort);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_value_array, android.R.layout.simple_spinner_item); 
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
        sortSpinner.setOnItemSelectedListener(new SortValueSelectedListener());
    
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
    
    
    @SuppressLint("NewApi") 
    public void searchRaceClicked(View v) {
       EditText searchEditText   = (EditText)findViewById(R.id.searchQuery);
       String searchQuery = searchEditText.getText().toString();
       
       ArrayList<Race> races = dbManager.getAllRaces();
       ArrayList<ArrayList<String>> convertedRaces = new ArrayList<ArrayList<String>>();
       for (Race race : races) {
           if (searchQuery.isEmpty() || race.getTitle().toLowerCase().contains(searchQuery) || race.getDetails().toLowerCase().contains(searchQuery)) {
               ArrayList<String> convertedRace = new ArrayList<String>();
               convertedRace.add(race.getTitle());
               convertedRace.add(race.getEndDateString());
               convertedRace.add(""+race.getId());
               convertedRaces.add(convertedRace);
           }
       }
       
       mAdapter = new MySimpleArrayAdapter(this, convertedRaces);
       
       ListView searchResultView = (ListView)findViewById(R.id.searchResult);
       searchResultView.setAdapter(mAdapter);
       searchResultView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> raceViewValues = mAdapter.getItem(position);
                int raceId = Integer.parseInt(raceViewValues.get(2));
                raceDetailsClicked(raceId);
            }
        });
       
       InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
       imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

    public void raceDetailsClicked(int raceId) {
        Intent intent = new Intent(this, RaceDetailsActivity.class);
        intent.putExtra(RaceDetailsActivity.RACE_ID_EXTRA_KEY, raceId);
        startActivity(intent);
    }
    
    class MySimpleArrayAdapter extends ArrayAdapter<ArrayList<String>> {
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

    
    class SortValueSelectedListener implements OnItemSelectedListener {
    	 
    	  @SuppressLint("NewApi") public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
    		  String sortType = parent.getItemAtPosition(pos).toString();
    		  
    	      EditText searchEditText   = (EditText)findViewById(R.id.searchQuery);
    		  String searchQuery = searchEditText.getText().toString();
    	       
    	       ArrayList<Race> races = dbManager.getAllRaces();
    	       races = sortRace(races, sortType);
    	       ArrayList<ArrayList<String>> convertedRaces = new ArrayList<ArrayList<String>>();
    	       for (Race race : races) {
    	           if (searchQuery.isEmpty() || race.getTitle().toLowerCase().contains(searchQuery) || race.getDetails().toLowerCase().contains(searchQuery)) {
    	               ArrayList<String> convertedRace = new ArrayList<String>();
    	               convertedRace.add(race.getTitle());
    	               convertedRace.add(race.getEndDateString());
    	               convertedRace.add(""+race.getId());
    	               convertedRaces.add(convertedRace);
    	           }
    	       }

    	       mAdapter = new MySimpleArrayAdapter(SearchRacesActivity.this, convertedRaces);
    	       
    	       ListView searchResultView = (ListView)findViewById(R.id.searchResult);
    	       searchResultView.setAdapter(mAdapter);
    	       searchResultView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    	            @Override
    	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	                ArrayList<String> raceViewValues = mAdapter.getItem(position);
    	                int raceId = Integer.parseInt(raceViewValues.get(2));
    	                raceDetailsClicked(raceId);
    	            }
    	        });
    	       
    	       InputMethodManager imm = (InputMethodManager)getSystemService(SearchRacesActivity.this.INPUT_METHOD_SERVICE);
    	       imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    	       
    	       
    	  }
    	  
    	  
    	  private ArrayList<Race> sortRace(ArrayList<Race> races, String criteria) {
    		  if (races.size() <= 1) {
    			  return races;
    		  }
    		  
    		  ArrayList<Race> left = new ArrayList<Race>();
    		  ArrayList<Race> right = new ArrayList<Race>();
    		  int middle = (int) Math.floor(races.size() / 2);
    		  for (int i = 0; i < middle; i ++) {
    			  left.add(races.get(i));
    		  }
    		  for (int i = middle; i < races.size(); i ++) {
    			  right.add(races.get(i));
    		  }
    		  left = sortRace(left, criteria);
    		  right = sortRace(right, criteria);
    		  return mergeHelper(left, right, criteria);
    	  }
    	      	  
    	  private ArrayList<Race> mergeHelper(ArrayList<Race> left, ArrayList<Race> right, String criteria) {
    		  
    		  ArrayList<Race> result = new ArrayList<Race>();
    		  
    		  while (left.size() > 0 || right.size() > 0) {
    			  if (left.size() > 0 && right.size() > 0) {
    				  
    				  if (left.get(0).lessThen(right.get(0), criteria)) {
    					  result.add(left.get(0));
    					  left.remove(0);
    				  } else {
    					  result.add(right.get(0));
    					  right.remove(0);
    				  }
    				  
    			  } else if (left.size() > 0) {
    				  result.add(left.get(0));
    				  left.remove(0);
    			  } else if (right.size() > 0) {
    				  result.add(right.get(0));
    				  right.remove(0);
    			  }
    		  }
    		  
    		  return result;
    		  
    	  }
    	  
    	  
    	  @Override
    	  public void onNothingSelected(AdapterView<?> arg0) {
    		// TODO Auto-generated method stub
    	  }
    	 
    }
}
