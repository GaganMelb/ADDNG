package com.project.gagan.addng;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    // Declare UI elements
    TextView tvTotal;
    TextView tvActive;
    TextView tvType1;
    TextView tvType2;
    TextView tvOther;
    TextView tvInactive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject.registerSubclass(Patient.class);

        tvTotal = (TextView)findViewById(R.id.total);
        ParseQuery<ParseObject> queryTotal = new ParseQuery<ParseObject>("Patient");
        queryTotal.countInBackground(new CountCallback(){
            public void done(int count, ParseException e) {
                if (e == null) {
                    tvTotal.setText(tvTotal.getText().toString()+"--"+count);
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
        tvTotal.setOnClickListener(this);


        tvActive = (TextView)findViewById(R.id.active);
        ParseQuery<ParseObject> queryActive = new ParseQuery<ParseObject>("Patient");
        queryActive.whereEqualTo("active_flag",true);
        queryActive.countInBackground(new CountCallback(){
            public void done(int count, ParseException e) {
                if (e == null) {
                    tvActive.setText(tvActive.getText().toString()+"--"+count);
                     } else {
                         Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                         }
                         }
    });
        tvActive.setOnClickListener(this);


        tvType1 = (TextView)findViewById(R.id.type1);
        ParseQuery<ParseObject> queryType1 = new ParseQuery<ParseObject>("Patient");
        queryType1.whereEqualTo("diabetes_type","type 1");
        queryType1.countInBackground(new CountCallback(){
            public void done(int count, ParseException e) {
                if (e == null) {
                    tvType1.setText(tvType1.getText().toString()+System.getProperty("line.separator")+"--"+count);
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
        tvType1.setOnClickListener(this);

        tvType2 = (TextView)findViewById(R.id.type2);
        ParseQuery<ParseObject> queryType2 = new ParseQuery<ParseObject>("Patient");
        queryType2.whereEqualTo("diabetes_type","type 2");
        queryType2.countInBackground(new CountCallback(){
            public void done(int count, ParseException e) {
                if (e == null) {
                    tvType2.setText(tvType2.getText().toString()+System.getProperty("line.separator")+"--"+count);
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
        tvType2.setOnClickListener(this);

        tvOther = (TextView)findViewById(R.id.other);
        tvOther.setOnClickListener(this);

        tvInactive = (TextView)findViewById(R.id.inactive);
        ParseQuery<ParseObject> queryInActive = new ParseQuery<ParseObject>("Patient");
        queryInActive.whereEqualTo("active_flag",false);
        queryInActive.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    tvInactive.setText(Html.fromHtml(tvInactive.getText().toString() + "<br/>" + "<font color=#ffd700>" + count + "</font>"))
                    ;
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
        tvInactive.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,dashboard_view.class);
        Bundle label = new Bundle();
        switch(view.getId()) {
            case R.id.total: // R.id.textView0 (Total Patients)
                // pass label
                label.putString("label",tvTotal.getText().toString());
                // add bundle to intent
                intent.putExtras(label);
                break;

            case R.id.active: // R.id.textView1 (Active Patients)

                // pass label
                label.putString("label",tvActive.getText().toString());
                // add bundle to intent
                intent.putExtras(label);
                break;

            case R.id.type1: // R.id.textView2 (Type 1 Patients)


                // pass label
                label.putString("label",tvType1.getText().toString());
                // add bundle to intent
                intent.putExtras(label);
                break;
            case R.id.type2: // R.id.textView3 (Type 2 Patients)

                // pass label
                label.putString("label",tvType2.getText().toString());
                // add bundle to intent
                intent.putExtras(label);
                break;
            case R.id.other: // R.id.textView4 (Other Patients)

                // pass label
                label.putString("label",tvOther.getText().toString());
                // add bundle to intent
                intent.putExtras(label);
                break;
            case R.id.inactive: // R.id.textView5 (Inactive Patients)

                // pass label
                label.putString("label",tvInactive.getText().toString());
                // add bundle to intent
                intent.putExtras(label);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
