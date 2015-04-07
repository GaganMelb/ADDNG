package com.project.gagan.addng;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.parse.FindCallback;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class dashboard_view extends ActionBarActivity{
    //ProgressDialog progressDialog;
    //ActionBarActivity actionBarActivity;


    private List<Centre> list_items;
    private ArrayAdapter<Centre> adapter;
   // private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String message = b.get("label").toString();


        this.setTitle(message);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_dashboard_view);


        //getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ListView mListView = (ListView) findViewById(R.id.list);
        list_items = new ArrayList<Centre>();
        adapter = new ArrayAdapter<Centre>(this, R.layout.list_item,R.id.centre_name,list_items );
        mListView.setAdapter(adapter);

        /*// Create a progressdialog
        progressDialog = new ProgressDialog(dashboard_view.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        // Show progressdialog
        progressDialog.show();*/

        refreshCentreList();
    }






    private void refreshCentreList() {


        ParseObject.registerSubclass(Centre.class);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Centre");
        query.orderByDescending("NumberOfPatients");
        query.

        //setProgressBarIndeterminateVisibility(true);
       // progressDialog.dismiss();

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> centreList, ParseException e) {
                //setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    // If there are results, update the list of centres
                    // and notify the adapter
                    list_items.clear();
                    for (ParseObject item : centreList) {
                        Centre centre = new Centre(item.getObjectId(), item.getString("Name"), item.getInt("NumberOfPatients"));
                        list_items.add(centre);
                    }
                    adapter.notifyDataSetChanged();
                    //((ArrayAdapter<Centre>) getAdapter()).notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
