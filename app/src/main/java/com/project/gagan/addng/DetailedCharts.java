package com.project.gagan.addng;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import java.text.NumberFormat;
import java.util.Calendar;



/**
 * Created by Gagan on 14-May-15.
 */

/*
This is the Detailed Charts class which shows the patients' data distribution based on gender
(pie-chart), age-group (bar-chart) and insulin regimen (pie-chart)
 */

public class DetailedCharts extends Activity {

    private String title; // name of the centre user belongs to
    private String patient_consent;
    private boolean p_consent;
    private ProgressDialog mProgressDialog;



    // for gender
    private double[] gender ={0.0,0.0};
    private double VALUE=0;
    private String[] genderTitle= {"Female","Male"};
    private static int[] COLORS_Gender = new int[] {Color.BLUE ,Color.MAGENTA};
    private CategorySeries mSeries = new CategorySeries("");
    private DefaultRenderer mRenderer = new DefaultRenderer();
    private GraphicalView mChartView;

    // for insulin
    private double[] insulin={0.0,0.0,0.0};
    private double total =0;
    private String[] insulinRegimen = {"MDI","CSII","BD/Twice Daily"};
    private static int[] COLORS_Insulin = new int[] {Color.RED ,Color.CYAN,Color.GREEN};
    private CategorySeries mSeriesInsulin = new CategorySeries("");
    private DefaultRenderer mRendererInsulin = new DefaultRenderer();
    private GraphicalView mChartViewInsulin;

    // for age-distribution - get the current calender dates
    private Calendar calCurrent= Calendar.getInstance();
    private Calendar subCal10 = Calendar.getInstance();
    private Calendar subCal15 = Calendar.getInstance();
    private Calendar subCal30 = Calendar.getInstance();

    // Array for age-distribution
    private int ageGroup[]={0,0,0};
    private View mChart;
    private String ageRange[]={"0-10","10-15","15-30"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_graphs);

        ParseObject.registerSubclass(Patient.class);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        title = b.getString("Centre-Name");
        patient_consent = b.getString("Future-Patient");

        setTitle(title + " : "+"Centre-Report Charts");

        // Age group - 10 years old from the current calender date
        subCal10.add(Calendar.YEAR,-10);

        // Age group - 15 years old from the current calender date
        subCal15.add(Calendar.YEAR,-15);

        // Age group - 30 years old from the current calender date
        subCal30.add(Calendar.YEAR,-30);

        // Assign Yes/No according to users' selective value - patient consented for future research
        if(patient_consent.equalsIgnoreCase("Yes")){
            p_consent = true;
        }else {
            p_consent = false;
        }

        // run the task in the background and get values from Parse.com cloud account
        new RemoteDataCount().execute();
    }

    private class RemoteDataCount extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DetailedCharts.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Parse.com Load Data in Charts");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try{

                ParseQuery<ParseObject> queryCentre = ParseQuery.getQuery("Centre");
                queryCentre.whereEqualTo("centre_name", title);

                // Count the number of Females - based on Centre user
                ParseQuery<ParseObject> queryFemale = ParseQuery.getQuery("Patient");
                queryFemale.whereMatchesQuery("centre_id",queryCentre );
                queryFemale.whereEqualTo("consent_for_future_research",p_consent);
                queryFemale.whereEqualTo("gender","female");
                gender[0]=queryFemale.count();

                // Count the number of Males - based on Centre user
                ParseQuery<ParseObject> queryMale = ParseQuery.getQuery("Patient");
                queryMale.whereMatchesQuery("centre_id",queryCentre);
                queryMale.whereEqualTo("consent_for_future_research",p_consent);
                queryMale.whereEqualTo("gender","male");
                gender[1] = queryMale.count();

                //count the number of patients - MDI
                ParseQuery<ParseObject> queryMDI = ParseQuery.getQuery("Patient");
                queryMDI.whereMatchesQuery("centre_id",queryCentre);
                queryMDI.whereEqualTo("consent_for_future_research", p_consent);
                queryMDI.whereEqualTo("insulin_regimen","MDI");
                insulin[0]=queryMDI.count();

                //count the number of patients - CSII
                ParseQuery<ParseObject> queryCSII = ParseQuery.getQuery("Patient");
                queryCSII.whereMatchesQuery("centre_id",queryCentre);
                queryCSII.whereEqualTo("consent_for_future_research",p_consent);
                queryCSII.whereEqualTo("insulin_regimen","CSII");
                insulin[1]=queryCSII.count();

                //count the number of patients - BD/Twice Daily
                ParseQuery<ParseObject> queryBD = ParseQuery.getQuery("Patient");
                queryBD.whereMatchesQuery("centre_id",queryCentre);
                queryBD.whereEqualTo("consent_for_future_research",p_consent);
                queryBD.whereEqualTo("insulin_regimen","BD/Twice Daily");
                insulin[2]=queryBD.count();

                // count the number of patients - Age (0-10)
                ParseQuery<ParseObject> queryAge0_10 = ParseQuery.getQuery("Patient");
                queryAge0_10.whereMatchesQuery("centre_id",queryCentre);
                queryAge0_10.whereGreaterThan("date_of_birth", subCal10.getTime());
                queryAge0_10.whereLessThan("date_of_birth", calCurrent.getTime());
                ageGroup[0]=queryAge0_10.count();

                // count the number of patients - Age (10-15)
                ParseQuery<ParseObject> queryAge10_15 = ParseQuery.getQuery("Patient");
                queryAge10_15.whereMatchesQuery("centre_id",queryCentre);
                queryAge10_15.whereGreaterThan("date_of_birth",subCal15.getTime());
                queryAge10_15.whereLessThan("date_of_birth", subCal10.getTime());
                ageGroup[1]=queryAge10_15.count();

                // count the number of patients - Age (15-30)
                ParseQuery<ParseObject> queryAge15_30 = ParseQuery.getQuery("Patient");
                queryAge15_30.whereMatchesQuery("centre_id",queryCentre);
                queryAge15_30.whereGreaterThan("date_of_birth",subCal30.getTime());
                queryAge15_30.whereLessThan("date_of_birth", subCal15.getTime());
                ageGroup[2]=queryAge15_30.count();
            }
            catch (ParseException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            // Count the total patients - gender wise
            for (int i = 0; i <gender.length; i++)
                VALUE += gender[i];

            // Draw the pie-chart - for gender
            drawPieChartGender();

            // Draw the Age-group bar chart
            drawAgeBarChart();

            // Count the total patients - for insulin Regimen
            for (int i = 0; i <insulin.length; i++)
                total += insulin[i];

            // Draw the pie-Chart - for Insulin Regimen
            drawPieChartInsulinRegimen();

            // Close the progressdialog
            mProgressDialog.dismiss();

        }
    }

    public void drawAgeBarChart(){
        XYSeries series = new XYSeries("");
        for(int i=0; i < ageGroup.length; i++){
            series.add(i,ageGroup[i]);
        }

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        dataSet.addSeries(series);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(Color.RED);
        renderer.setDisplayChartValues(true);
        renderer.setFillPoints(true);
        renderer.setChartValuesSpacing((float) 5.5d);
        renderer.setLineWidth((float) 10.5d);
        renderer.setChartValuesTextSize(getResources().getDimension(R.dimen.textsize));

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setChartTitle("Age Distribution");
        mRenderer.setXTitle("Age");
        mRenderer.setYTitle("Patients");
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setShowLegend(true);
        mRenderer.setChartTitleTextSize(getResources().getDimension(R.dimen.textsize));
        mRenderer.setLabelsTextSize(getResources().getDimension(R.dimen.textsize));
        mRenderer.setLegendTextSize(getResources().getDimension(R.dimen.textsize));
        mRenderer.setShowGridX(true);
        mRenderer.setShowGridY(true);
        mRenderer.setAntialiasing(true);
        mRenderer.setBarSpacing(.5);
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.BLACK);
        mRenderer.setPanEnabled(true, false);
        mRenderer.setXAxisMin(-2);
        mRenderer.setYAxisMin(0);
        mRenderer.setXAxisMax(5);
        mRenderer.setYAxisMax(50);
        mRenderer.setXLabels(0);
        mRenderer.setMargins(new int[]{30, 30, 30, 30});
        for(int i=0; i< ageGroup.length;i++){
            mRenderer.addXTextLabel(i, ageRange[i]);
        }



        // Display the bar chart
        LinearLayout ageBarChartContainer = (LinearLayout) findViewById(R.id.ageChart);
        // Draw the bar chart
        mChart = ChartFactory.getBarChartView(DetailedCharts.this, dataSet, mRenderer, BarChart.Type.DEFAULT);
        ageBarChartContainer.addView(mChart);
    }

    public void drawPieChartGender(){

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setChartTitle("Gender Distribution");
        mRenderer.setBackgroundColor(Color.DKGRAY);
        mRenderer.setChartTitleTextSize(getResources().getDimension(R.dimen.textsize));
        mRenderer.setLabelsTextSize(getResources().getDimension(R.dimen.textsize));
        mRenderer.setLegendTextSize(getResources().getDimension(R.dimen.textsize));
        mRenderer.isInScroll();
        mRenderer.setDisplayValues(true);
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(90);

        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.genderChart);
            mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);
            layout.addView(mChartView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            mChartView.repaint();
        }

        mChartView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                if (seriesSelection == null) {
                    Toast.makeText(DetailedCharts.this, "No chart element selected", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    for (int i = 0; i < mSeries.getItemCount(); i++) {
                        mRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
                    }

                    mChartView.repaint();
                    Toast.makeText(
                            DetailedCharts.this,
                            "Selected"
                                    + " point value=" + Math.round(seriesSelection.getValue() * 100) +"%", Toast.LENGTH_SHORT).show();
                }
            }


        });
        fillPieChartGender();
    }

    public void fillPieChartGender(){

        for(int i=0;i<gender.length;i++)
        {
            mSeries.add( genderTitle[i]+ " : " +gender[i] , gender[i]/VALUE);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS_Gender[(mSeries.getItemCount() - 1) % COLORS_Gender.length]);
            renderer.setChartValuesFormat(NumberFormat.getPercentInstance());
            mRenderer.addSeriesRenderer(renderer);
            if (mChartView != null)
                mChartView.repaint();
        }
    }

    // for insulin
    public void drawPieChartInsulinRegimen(){

        mRendererInsulin.setApplyBackgroundColor(true);
        mRendererInsulin.setChartTitle("Insulin Regimen");
        mRendererInsulin.setBackgroundColor(Color.DKGRAY);
        mRendererInsulin.setChartTitleTextSize(getResources().getDimension(R.dimen.textsize));
        mRendererInsulin.setLabelsTextSize(getResources().getDimension(R.dimen.textsize));
        mRendererInsulin.setLegendTextSize(getResources().getDimension(R.dimen.textsize));
        mRendererInsulin.isInScroll();
        mRendererInsulin.setDisplayValues(true);
        mRendererInsulin.setZoomButtonsVisible(true);
        mRendererInsulin.setStartAngle(90);

        if (mChartViewInsulin == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.insulinRegimen);
            mChartViewInsulin = ChartFactory.getPieChartView(this, mSeriesInsulin, mRendererInsulin);
            mRendererInsulin.setClickEnabled(true);
            mRendererInsulin.setSelectableBuffer(10);
            layout.addView(mChartViewInsulin, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            mChartViewInsulin.repaint();
        }

        mChartViewInsulin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SeriesSelection seriesSelection = mChartViewInsulin.getCurrentSeriesAndPoint();
                if (seriesSelection == null) {
                    Toast.makeText(DetailedCharts.this, "No chart element selected", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    for (int i = 0; i < mSeriesInsulin.getItemCount(); i++) {
                        mRendererInsulin.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
                    }

                    mChartViewInsulin.repaint();
                    Toast.makeText(
                            DetailedCharts.this,
                            "Selected"
                                    + " point value=" + Math.round(seriesSelection.getValue() * 100) +"%", Toast.LENGTH_SHORT).show();
                }
            }


        });
        fillPieChartInsulinRegimen();
    }

    public void fillPieChartInsulinRegimen(){

        for(int i=0;i<insulin.length;i++)
        {
            mSeriesInsulin.add( insulinRegimen[i]+ " : " +insulin[i] , insulin[i]/total);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS_Insulin[(mSeriesInsulin.getItemCount() - 1) % COLORS_Insulin.length]);
            renderer.setChartValuesFormat(NumberFormat.getPercentInstance());
            mRendererInsulin.addSeriesRenderer(renderer);
            if (mChartViewInsulin != null)
                mChartViewInsulin.repaint();
        }
    }

}
