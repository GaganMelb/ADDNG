package com.project.gagan.addng;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Gagan on 11-May-15.
 */
/*
The is the Home fragment. This is the user home page for Centre-Reports and shows user's
information and also name of the centre user belongs to
 */
public class HomeFragment extends Fragment {
    private TextView centreName;
    private TextView username;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        username = (TextView) rootView.findViewById(R.id.user);
        centreName = (TextView) rootView.findViewById(R.id.centre);

        // Print username
        ParseUser user = ParseUser.getCurrentUser();
        username.setText(username.getText() + user.getString("username"));

        // Welcome message with centre name (Centre Name - which user belongs to)
        user.getParseObject("centre_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(e!=null){
                    centreName.setText("User has not assigned any Centre : Please contact the Admin");
                }
                else {
                    centreName.setText(centreName.getText() + parseObject.getString("centre_name"));
                }
            }
        });


        return rootView;
    }


}
