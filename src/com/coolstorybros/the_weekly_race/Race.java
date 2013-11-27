package com.coolstorybros.the_weekly_race;

import java.util.Calendar;
import java.util.Date;

/**
 * Describes the properties of a Weekly Race
 */
public class Race {

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
}
