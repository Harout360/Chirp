package com.har8.scratchlogin2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class Homepage extends ListActivity implements SwipeRefreshLayout.OnRefreshListener {


    protected List<ParseObject> mStatus;
    protected ParseUser currentUser;
    protected SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Parse.initialize(this, "3ErDoBMVha14GaLpgCgUV1uyAJ1aRWRXq1EZPs3l", "WXxeBDznQMhigZhptheOnzODvY66fvDfUSgcDqZ7");


        currentUser = ParseUser.getCurrentUser();

        updateList();

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.Homepage);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.updateStatus:
                //take user to the update status activity
                Intent intent = new Intent(this, UpdateStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.logoutUser:
                //logout
                ParseUser.logOut();

                //take back to login screen

                Intent takeToLoginPage = new Intent(this, LoginAcitivity.class);
                startActivity(takeToLoginPage);
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject statusObject = mStatus.get(position);
        String objectId = statusObject.getObjectId();

        Intent goToDetailView = new Intent(Homepage.this, StatusDetailViewActivity.class);
        //PASS INFO FROM ACTIVITY TO ACTIVITY
        goToDetailView.putExtra("ObjectID", objectId);
        startActivity(goToDetailView);
    }

    protected void updateList() {


        if (currentUser != null)

        {
            //use the logged in user... lol
            //show user the homepage statuses
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Status");

            //Make the newest status be at the top
            query.orderByDescending("createdAt");

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> status, ParseException e) {
                    if (e == null) {
                        //success
                        mStatus = status;

                        StatusAdapter adapter = new StatusAdapter(getListView().getContext(), mStatus);
                        setListAdapter(adapter);
                    } else {
                        //error alert user
                    }
                }
            });
        } else

        {
            //there's nobody logged in SO go to homepage
            Intent takeUserToLogin = new Intent(Homepage.this, LoginAcitivity.class);
            startActivity(takeUserToLogin);
        }
    }

    //SWIPE TO REFRESH
    @Override
    public void onRefresh() {
        updateList();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 2000);


    }

}
