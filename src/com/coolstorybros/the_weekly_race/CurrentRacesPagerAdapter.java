package com.coolstorybros.the_weekly_race;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class CurrentRacesPagerAdapter extends FragmentPagerAdapter {
	public CurrentRacesPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
        switch (index) {
        case 0:
            return new CurrentRacesListFragment();
        case 1:
            return new RaceDetailsFragment();
        }
        return null;
    }
 
    @Override
    public int getCount() {
        return 2;
    }
}