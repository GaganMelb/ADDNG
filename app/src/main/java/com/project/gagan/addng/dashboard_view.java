package com.project.gagan.addng;

/*
This class is not in-use
 */
/*
Created by Gagan 01-May-2015
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;


public class dashboard_view extends ActionBarActivity{

    int value;
    private List<Patient> list_items;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private String centreString;
    private ListView mListView;
   private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        String message = b.get("label").toString();
        this.setTitle(message);
        setContentView(R.layout.activity_dashboard_view);
        mListView = (ListView) findViewById(R.id.list);
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item,R.id.centre_name,list);
        mListView.setAdapter(adapter);

        refreshCentreList();
    }

    private void refreshCentreList() {


        ParseObject.registerSubclass(Centre.class);
        ParseObject.registerSubclass(Patient.class);


        ParseQuery<ParseObject> queryTest= new ParseQuery<ParseObject>("Patient");
        queryTest.whereEqualTo("active_flag",true);
        queryTest.include("centre_name");
        queryTest.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> patientList, ParseException e) {
                if (e == null) {
                    // If there are results, update the list of centres
                    // and notify the adapter
                    list.clear();

                    for (ParseObject item : patientList) {
                        counter=0;
                        item.getParseObject("centre_name").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                counter = counter+1;
                                centreString = parseObject.getString("Name");

                                list.add(counter+"."+ " " +centreString);
                            }
                        });

                    }
                    adapter.notifyDataSetChanged();

                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });




    }



}
