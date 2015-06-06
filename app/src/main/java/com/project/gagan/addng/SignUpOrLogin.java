package com.project.gagan.addng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Gagan on 22-Apr-15.
 */

/*
This is the page before the login page, which gives user the option to select to login
or sign up (which is not required at the moment) for the application
 */

public class SignUpOrLogin extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login);

        // Log in button click handler
        ((Button) findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent of the log in activity
                startActivity(new Intent(SignUpOrLogin.this, LoginActivity.class));
            }
        });

        /* Sign up button click handler
        ((Button) findViewById(R.id.signup)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                startActivity(new Intent(SignUpOrLogin.this, SignUp.class));
            }
        });*/
    }
}
