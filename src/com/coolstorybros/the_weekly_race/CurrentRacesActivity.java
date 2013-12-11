package com.coolstorybros.the_weekly_race;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.coolstorybros.the_weekly_race.data.DatabaseManager;
import com.coolstorybros.the_weekly_race.data.Race;
import com.coolstorybros.the_weekly_race.data.User;

import java.util.ArrayList;

public class CurrentRacesActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner mSortBySpinner;
    TextView mCurrentRacesEmptyText;
    ListView mCurrentRacesList;
    MySimpleArrayAdapter mAdapter;

    // The list of race objects that we populate from the database and then display on screen
    ArrayList<Race> mRaces;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_races);

        mCurrentRacesList = (ListView) findViewById(R.id.current_races_list);
        mCurrentRacesEmptyText = (TextView) findViewById(R.id.current_races_empty_text);
        mCurrentRacesList.setEmptyView(mCurrentRacesEmptyText);

        /*
        // Setup the "Sort By" spinner button
        mSortBySpinner = (Spinner) findViewById(R.id.currentRaces_sort_by_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_by_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortBySpinner.setAdapter(adapter);
        */
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    public void init() {

        DatabaseManager dbManager = new DatabaseManager(this);
        User currentUser = WeeklyRaceApplication.getCurrentUser();
        ArrayList<Race> races = dbManager.getRacesByUser(currentUser.getId());
        ArrayList<ArrayList<String>> convertedRaces = new ArrayList<ArrayList<String>>();
        for (Race race : races) {
            ArrayList<String> convertedRace = new ArrayList<String>();
            convertedRace.add(race.getTitle());
            convertedRace.add(race.getEndDateString());
            convertedRace.add(""+race.getId());
            convertedRaces.add(convertedRace);
        }

        mAdapter = new MySimpleArrayAdapter(this, convertedRaces);

        ListView currentRacesList = (ListView)findViewById(R.id.current_races_list);
        TextView emptyText = (TextView) findViewById(R.id.current_races_empty_text);
        currentRacesList.setEmptyView(emptyText);
        currentRacesList.setAdapter(mAdapter);
        currentRacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> raceViewValues = mAdapter.getItem(position);
                int raceId = Integer.parseInt(raceViewValues.get(2));
                raceDetailsClicked(raceId);
            }
        });

        initUi();
    }

    /**
     * Method where we update all View values, including the list of displayed Races
     */
    public void initUi() {

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














    // Called when an item in the "Sort By" spinner is selected
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using parent.getItemAtPosition(pos)
        // For the prototype, don't actually handle this. Maybe implement it later.
    }

    // This method is needed for the OnItemSelected interface but isn't actually used
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * Pressed when the "Your Current Races" button is clicked
     * @param v Button that was clicked
     */
    public void currentRacesButtonClicked(View v) {
        // Already on the Current Races activity
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
        Intent intent = new Intent(this, ManageRaceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
