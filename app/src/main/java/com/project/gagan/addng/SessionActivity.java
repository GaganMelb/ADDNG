package com.project.gagan.addng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.parse.ParseUser;

/**
 * Created by Gagan on 22-Apr-15.
 */
/*
This is the Session class which acts as a gate-keeper between login activity and main activity
(which is also the home-page of the application).

It automatically launches the main activity page if the user is already logged in,
otherwise launches the login page if the user is using this
application for the very first time.
 */
public class SessionActivity  extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, SignUpOrLogin.class));
        }
    }
}





