package com.project.gagan.addng;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


/*
 * Created by Gagan on 11-May-15.
 */


/*
This is the home page of the application and shows more options to choose from. For example,
Centre-Reports, Summary, Settings (for push notifications),and Log out.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    // Declare UI elements
    ImageView centreRecords;
    ImageView summary;

    // Declare integers for counts
    int vic_MEL_RCH;
    int wa_PER_PMH;
    int nsw_SYD_CHW;
    int sa_ADL_WCH;
    int qld_BRS_LCH;



    // Declare Strings for centre_names
    String vic_MEL_RCH_n = "VIC-MEL-RCH";
    String wa_PER_PMH_n="WA-PER-PMH";
    String nsw_SYD_CHW_n="NSW-SYD-CHW";
    String sa_ADL_WCH_n="SA-ADL-WCH";
    String qld_BRS_LCH_n="QLD-BRS-LCH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject.registerSubclass(Centre.class);

        //Centre Name = WA-PER-PMH , id = oLg0S3YTvf
        ParseObject objWA_PER_PMH = ParseObject.createWithoutData("Centre", "oLg0S3YTvf");
        ParseQuery<ParseObject> queryWA = ParseQuery.getQuery("Patient");
        queryWA.whereEqualTo("centre_id", objWA_PER_PMH);
        //queryWA.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        queryWA.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    wa_PER_PMH = count;
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

        //Centre Name = VIC-MEL-RCH, id = s4Ou9IqU1t
        ParseObject objVIC_MEL_RCH = ParseObject.createWithoutData("Centre", "s4Ou9IqU1t");
        ParseQuery<ParseObject> queryVIC = ParseQuery.getQuery("Patient");
        queryVIC.whereEqualTo("centre_id", objVIC_MEL_RCH);
        //queryVIC.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        queryVIC.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    vic_MEL_RCH = count;
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

        //Centre Name = SA-ADL-WCH, id = VqtbTZkZEs
        ParseObject objSA_ADL_WCH = ParseObject.createWithoutData("Centre", "VqtbTZkZEs");
        ParseQuery<ParseObject> querySA = ParseQuery.getQuery("Patient");
       querySA.whereEqualTo("centre_id", objSA_ADL_WCH);
        //querySA.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        querySA.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    sa_ADL_WCH = count;
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

        //Centre Name = QLD-BRS-LCH, id = A0N3jdanIT
        ParseObject objQLD_BRS_LCH = ParseObject.createWithoutData("Centre", "A0N3jdanIT");
        ParseQuery<ParseObject> queryQLD =ParseQuery.getQuery("Patient");
        queryQLD.whereEqualTo("centre_id", objQLD_BRS_LCH);
        //queryQLD.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        queryQLD.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    qld_BRS_LCH = count;
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

        //Centre Name = NSW-SYD-CHW, id = mPVcmqXNvn
        ParseObject objNSW_SYD_CHW = ParseObject.createWithoutData("Centre", "mPVcmqXNvn");
        ParseQuery<ParseObject> queryNSW =ParseQuery.getQuery("Patient");
        queryNSW.whereEqualTo("centre_id", objNSW_SYD_CHW);
       // queryNSW.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        queryNSW.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    nsw_SYD_CHW = count;
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

        summary = (ImageView)findViewById(R.id.summary_button);
        summary.setOnClickListener(this);

        centreRecords = (ImageView)findViewById(R.id.centreR);
        centreRecords.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent();
        Bundle label = new Bundle();

        String[] centreNames={vic_MEL_RCH_n,sa_ADL_WCH_n,wa_PER_PMH_n,qld_BRS_LCH_n,nsw_SYD_CHW_n};
        double[] numberOfPatients= {vic_MEL_RCH,sa_ADL_WCH,wa_PER_PMH,qld_BRS_LCH,nsw_SYD_CHW};
        switch(view.getId()) {

            case R.id.summary_button: // R.id.summary_button (Total Patients in Pie-Charts : State-wise)

                intent=new Intent(MainActivity.this,PieGraph.class);
                //pass label
                label.putDoubleArray("numberOfPatients",numberOfPatients);

                label.putStringArray("nameOfCentres",centreNames);

                // add bundle to intent
                intent.putExtras(label);
                break;


            case R.id.centreR: // R.id.centreR (Centre Reports)
                intent=new Intent(MainActivity.this,CentreReports.class);

                break;
        }
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.action_settings:// Setting option to set for push notifications
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;

            case R.id.logout:
                // Call the Parse log out method
                ParseUser.logOut();
                // Start and intent for the dispatch activity
                Intent intent = new Intent(MainActivity.this, SessionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }



    }



}
