package com.project.gagan.addng;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Gagan on 11-May-15.
 */
/*
This the Disease Type fragment - which is one of the report types. This is the page before the user
is able to view the patients' statistical information in the form of pie-chart and bar graphs.
 */
public class DiseaseType extends Fragment {

    //Widgets - GUI
    Spinner spPatientContent;

    ListView ageDis;

    Button getGraphs;

    TextView centre; // holds the user's centre-name

    String centreName;
    String future_content;

    public DiseaseType(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_disease, container, false);


        // Get Button object from xml
        getGraphs = (Button)rootView.findViewById(R.id.bttnGraph);

        // Get ListView object from xml
        ageDis = (ListView) rootView.findViewById(R.id.ageDistribution);

        // Print the user's centre-name
        centre = (TextView) rootView.findViewById(R.id.txtCName);
        ParseUser user = ParseUser.getCurrentUser();
        user.getParseObject("centre_id").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(e!=null){
                    centre.setText("User has not assigned any Centre : Please contact the Admin");
                }
                else {
                    centreName = parseObject.getString("centre_name");
                    centre.setText(centre.getText() + parseObject.getString("centre_name"));
                }
            }
        });

        // Defined Array values to show in ListView
        String[] values = new String[]{">=0.0 to <10.0",">=10.0 to <15.0",">=15.0 to <30.0"};

        // Define a new Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DiseaseType.super.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        ageDis.setAdapter(adapter);

        // Initialize Spinner
        spPatientContent = (Spinner)rootView.findViewById(R.id.patientsNotConsentedFutureResearch);

        // Item Selected Listener
        spPatientContent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                 future_content = adapter.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        // On the click of the button, user is able to view the statistics
        getGraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detaliedGraphs = new Intent(getActivity(), DetailedCharts.class);
                Bundle label = new Bundle();
                label.putString("Centre-Name",centreName);
                label.putString("Future-Patient",future_content);
                detaliedGraphs.putExtras(label);
                startActivity(detaliedGraphs);
            }
        });


        return rootView;
    }
}
