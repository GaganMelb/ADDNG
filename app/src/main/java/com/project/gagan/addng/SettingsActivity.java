package com.project.gagan.addng;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import java.util.Arrays;

/**
 * Created by Gagan on 24-Apr-15.
 */
/*
This is the Setting class. In this, user can enable or disable the push notifications and also
log out
 */
public class SettingsActivity extends Activity {

    CheckBox bt; // check button for new updates
    Button btnSubscribe;
    Button btnUnSubscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        // Set up the notifications for different channels
        bt = (CheckBox)findViewById(R.id.checkBox);
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value", false);
        if (checkBoxValue) {
            bt.setChecked(true);
        } else {
            bt.setChecked(false);
        }
        btnSubscribe=(Button)findViewById(R.id.buttonSubscribe);
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bt.isChecked()) {
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.addAllUnique("channels", Arrays.asList("Updates"));
                    installation.put("user",ParseUser.getCurrentUser());
                    installation.saveInBackground();
                    Toast.makeText(SettingsActivity.this,
                            "Awesome! You subscribed", Toast.LENGTH_LONG).show();
                    savePreferences("CheckBox_Value", bt.isChecked());

                }else{
                    Toast.makeText(SettingsActivity.this,
                            ":( Oops! Please select one option", Toast.LENGTH_LONG).show();
                }

            }
        });



        // Un-subscribe
        btnUnSubscribe= (Button)findViewById(R.id.buttonUnSub);
        btnUnSubscribe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ParsePush.unsubscribeInBackground("CHANNEL_Summary");
                savePreferences("CheckBox_Value", false);
                bt.setChecked(false);
                Toast.makeText(SettingsActivity.this,
                        "You are no longer subscribe for the updates", Toast.LENGTH_LONG).show();
            }
        });

        // Set up the log out button click event
        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Call the Parse log out method
                ParseUser.logOut();
                // Start and intent for the dispatch activity
                Intent intent = new Intent(SettingsActivity.this, SessionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }

    // save the check box state after subscribing or un-subscribing from the channel
    private void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
