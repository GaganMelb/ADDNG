package com.project.gagan.addng;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Gagan on 31-Mar-15.
 */
public class AddnGAppApplication extends Application{


    //@Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "uS7kC6URsVdbi1wjg9VV0c60gTY2QlCLE1hThV60", "R1RwULBimSve8D1sXU35w91681sxsLnHTY5f4on2");


    }

}
