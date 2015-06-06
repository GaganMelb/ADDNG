package com.project.gagan.addng;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Gagan on 08-May-15.
 */

/*
This class is for Centre-Reports with the side-bar. In the side-bar, there are different report
styles to choose from. Sliding from left to right shows the side-bar in the Centre-Report.

 */
public class CentreReports extends Activity {

    private ListView reportList;
    private ArrayAdapter<String> rAdapter;
    private ActionBarDrawerToggle rDrawerToggle;
    private DrawerLayout rDrawerLayout;
    private String rTitle;
    private String[] rItems;

     String[] fragments ={
             "com.project.gagan.addng.HomeFragment",
            "com.project.gagan.addng.DiseaseType"};


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.centre_reports);

        //load side bar menu items
        rItems= getResources().getStringArray(R.array.sideBarReportType);

        reportList = (ListView)findViewById(R.id.reportType);
        rDrawerLayout = (DrawerLayout)findViewById(R.id.report_layout);
        rTitle = getTitle().toString();

        // List Items in the side bar drawer
        addItems();

        // Side Bar
        sideBarSetUp();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


 }

    private void addItems() {
                rAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rItems);
                reportList.setAdapter(rAdapter);
                reportList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        rDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                            @Override
                            public void onDrawerClosed(View drawerView) {
                                super.onDrawerClosed(drawerView);
                                FragmentManager tx = getFragmentManager();
                                tx.beginTransaction().replace(R.id.f_container,Fragment.instantiate(CentreReports.this, fragments[position])).commit();
                            }
                        });
                            getActionBar().setTitle(rItems[position]);
                            rDrawerLayout.closeDrawer(reportList);


                    }
                });
                FragmentManager tx = getFragmentManager();
                tx.beginTransaction().replace(R.id.f_container,Fragment.instantiate(CentreReports.this, fragments[0])).commit();

            }

    private void sideBarSetUp() {
                rDrawerToggle = new ActionBarDrawerToggle(this, rDrawerLayout, R.string.open_SideBar, R.string.close_SideBar) {

                    /** Called when the side bar has settled in a completely open state. */
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                        getActionBar().setTitle("Select Report Type");
                        invalidateOptionsMenu();
                    }

                    /** Called when the side bar has settled in a completely closed state. */
                    public void onDrawerClosed(View view) {
                        super.onDrawerClosed(view);
                        getActionBar().setTitle(rTitle);
                        invalidateOptionsMenu();
                    }
                };

                rDrawerToggle.setDrawerIndicatorEnabled(true);
                rDrawerLayout.setDrawerListener(rDrawerToggle);
            }

            @Override
            protected void onPostCreate(Bundle savedInstanceState) {
                super.onPostCreate(savedInstanceState);
                // Sync the toggle state after onRestoreInstanceState has occurred.
                rDrawerToggle.syncState();
            }

            @Override
            public void onConfigurationChanged(Configuration rConfig) {
                super.onConfigurationChanged(rConfig);
                rDrawerToggle.onConfigurationChanged(rConfig);
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_centre_reports, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {

                // Activate the navigation drawer toggle
                if (rDrawerToggle.onOptionsItemSelected(item)) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }



        }


