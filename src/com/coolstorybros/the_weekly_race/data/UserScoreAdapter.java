package com.coolstorybros.the_weekly_race.data;

import java.util.*;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coolstorybros.the_weekly_race.R;

public class UserScoreAdapter extends ArrayAdapter {
    private final Activity activity;
    private final ArrayList<UserScore> userscores;

    public UserScoreAdapter(Activity activity, ArrayList<UserScore> objects) {
        super(activity, R.layout.user_score_list_item, objects);
        this.activity = activity;
        this.userscores = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        UserScoreView usView = null;

        if(rowView == null)
        {
            // Get a new instance of the row layout view
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.user_score_list_item, null);

            // Hold the view objects in an object,
            // so they don't need to be re-fetched
            usView = new UserScoreView();
            usView.username = (TextView) rowView.findViewById(R.id.user_name);
            usView.userscore = (TextView) rowView.findViewById(R.id.user_score);

            // Cache the view objects in the tag,
            // so they can be re-accessed later
            rowView.setTag(usView);
        } else {
            usView = (UserScoreView) rowView.getTag();
        }

        // Transfer the stock data from the data object to the view objects
        UserScore currentUserScore = userscores.get(position);
        usView.username.setText(currentUserScore.getUsername());
        usView.userscore.setText("Score: " + currentUserScore.getScore());

        return rowView;
    }

    protected static class UserScoreView {
        protected TextView username;
        protected TextView userscore;
    }
}
