package com.coolstorybros.the_weekly_race.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Calendar;
import java.util.Date;

/**
 * Describes the properties of a Weekly Race
 */
public class Race {

    private int mId;    // this represents the ID of this race entry in the "races" database
    private String mTitle;
    private String mLocation;
    private String mDetails;
    private Calendar mStartDate;
    private Calendar mEndDate;
    private String mPrize;
    private int mNumWinners;

    public Race(String title, String location, String details, Calendar startDate, Calendar endDate, String prize, int numWinners) {
        mTitle = title;
        mLocation = location;
        mDetails = details;
        mStartDate = startDate;
        mEndDate = endDate;
        mPrize = prize;
        mNumWinners = numWinners;
    }

    /**
     * Used to populate a Race object from the database
     * @param cursor A cursor that is accessing the row in the Races DB corresponding to this race
     */
    public Race(Cursor cursor) {
        mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.RacesEntry._ID));
        mTitle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.RacesEntry.COLUMN_NAME_TITLE));
        mLocation = cursor.getString(cursor.getColumnIndex(DatabaseHelper.RacesEntry.COLUMN_NAME_LOCATION));
        mDetails = cursor.getString(cursor.getColumnIndex(DatabaseHelper.RacesEntry.COLUMN_NAME_DETAILS));
        mPrize = cursor.getString(cursor.getColumnIndex(DatabaseHelper.RacesEntry.COLUMN_NAME_PRIZE));
        mNumWinners = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.RacesEntry.COLUMN_NAME_NUM_WINNERS));

        // Load the start date
        String startDateString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.RacesEntry.COLUMN_NAME_START_DATE));
        int startMonth, startDay, startYear;
        if (startDateString == null || startDateString.equals("")){
            throw new RuntimeException("Start calendar field was null or empty in database");
        }
        String[] startDateVals = startDateString.split("[/]");
        if (startDateVals.length == 3) {
            startMonth = Integer.parseInt(startDateVals[0]) - 1; //January is 0
            startDay = Integer.parseInt(startDateVals[1]);
            startYear = Integer.parseInt(startDateVals[2]);
        } else {
            throw new RuntimeException("Had an error parsing the start calendar string from the database");
        }
        mStartDate = Calendar.getInstance();
        mStartDate.set(startYear, startMonth, startDay);

        // Load the end date
        String endDateString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.RacesEntry.COLUMN_NAME_END_DATE));
        int endMonth, endDay, endYear;
        if (endDateString == null || endDateString.equals("")){
            throw new RuntimeException("End calendar field was null or empty in database");
        }
        String[] endDateVals = endDateString.split("[/]");
        if (endDateVals.length == 3) {
            endMonth = Integer.parseInt(endDateVals[0]) - 1; //January is 0
            endDay = Integer.parseInt(endDateVals[1]);
            endYear = Integer.parseInt(endDateVals[2]);
        } else {
            throw new RuntimeException("Had an error parsing the end calendar string from the database");
        }
        mEndDate = Calendar.getInstance();
        mEndDate.set(endYear, endMonth, endDay);
    }

    /**
     * This method is called when the Race is actually inserted into database and assigned a record ID
     */
    public void setId(int id) {
        mId = id;
    }
    public int getId() {
        return mId;
    }

    public String getTitle()
    {
    	return mTitle;
    }
    public String getLocation()
    {
    	return mLocation;
    }
    public String getDetails()
    {
    	return mDetails;
    }
    public String getPrize()
    {
    	return mPrize;
    }
    public int getNumWinners()
    {
    	return mNumWinners;
    }
    public Calendar getStartDate()
    {
    	return mStartDate;
    }
    public Calendar getEndDate()
    {
    	return mEndDate;
    }
    public void setTitle(String newTitle)
    {
    	mTitle = newTitle;
    }
    public void setLocation(String newLocation)
    {
    	mTitle = newLocation;
    }
    public void setDetails(String newDetails)
    {
    	mDetails = newDetails;
    }
    public void setPrize(String newPrize)
    {
    	mPrize = newPrize;
    }
    public void setNumWinners(int newNumWinners)
    {
    	mNumWinners = newNumWinners;
    }
    public void setStartDate(Calendar newStartDate)
    {
    	mStartDate = newStartDate;
    }
    public void setEndDate(Calendar newEndDate)
    {
    	mEndDate = newEndDate;
    }

    public String getStartDateString() {
        return ((Integer)getStartDate().MONTH).toString() + "/" + ((Integer)getStartDate().DAY_OF_MONTH).toString() + "/" + ((Integer)getStartDate().YEAR).toString();
    }
    public String getEndDateString() {
        return ((Integer)getEndDate().MONTH).toString() + "/" + ((Integer)getEndDate().DAY_OF_MONTH).toString() + "/" + ((Integer)getEndDate().YEAR).toString();
    }

    /**
     * Creates a ContentValues object to insert into the database
     * @return ContentValues that represents all the fields of this Race
     */
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.RacesEntry.COLUMN_NAME_TITLE, getTitle());
        values.put(DatabaseHelper.RacesEntry.COLUMN_NAME_LOCATION, getLocation());
        values.put(DatabaseHelper.RacesEntry.COLUMN_NAME_DETAILS, getDetails());
        values.put(DatabaseHelper.RacesEntry.COLUMN_NAME_START_DATE, getStartDateString());
        values.put(DatabaseHelper.RacesEntry.COLUMN_NAME_END_DATE, getEndDateString());
        values.put(DatabaseHelper.RacesEntry.COLUMN_NAME_PRIZE, getPrize());
        values.put(DatabaseHelper.RacesEntry.COLUMN_NAME_NUM_WINNERS, getNumWinners());
        return values;
    }
}
