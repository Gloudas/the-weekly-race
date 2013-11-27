package com.coolstorybros.the_weekly_race;

import java.util.Calendar;
import java.util.Date;

/**
 * Describes the properties of a Weekly Race
 */
public class Race {

    private String mTitle;
    private String mDetails;
    private Calendar mStartDate;
    private int mPoints;
    private String mPrize;
    private int mNumWinners;

    public Race(String title, String details, Calendar date, int points, String prize, int numWinners) {
        mTitle = title;
        mDetails = details;
        mStartDate = date;
        mPoints = points;
        mPrize = prize;
        mNumWinners = numWinners;
    }
    
    public String getTitle()
    {
    	return mTitle;
    }
    public String getDetails()
    {
    	return mDetails;
    }
    public String getPrize()
    {
    	return mPrize;
    }
    public int getPoints()
    {
    	return mPoints;
    }
    public int getNumWinners()
    {
    	return mNumWinners;
    }
    public Calendar getStartDate()
    {
    	return mStartDate;
    }
    
    public void setTitle(String newTitle)
    {
    	mTitle = newTitle;
    }
    public void setDetails(String newDetails)
    {
    	mDetails = newDetails;
    }
    public void setPrize(String newPrize)
    {
    	mPrize = newPrize;
    }
    public void setPoints(int newPoints)
    {
    	mPoints = newPoints;
    }
    public void setNumWinners(int newNumWinners)
    {
    	mNumWinners = newNumWinners;
    }
    public void setStartDate(Calendar newStartDate)
    {
    	mStartDate = newStartDate;
    }
}
