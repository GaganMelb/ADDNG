package com.project.gagan.addng;
import android.app.Application;
import com.parse.Parse;

/**
 * Created by Gagan on 31-Mar-15.
 */

public class AddnGAppApplication extends Application{


    //@Override
    public void onCreate() {
        super.onCreate();

        // Connection with Parse through application ID and client Key
        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_id));

    }
}
