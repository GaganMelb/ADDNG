package com.project.gagan.addng;

/**
 * Created by Gagan on 25-Apr-15.
 */
/*
This is the Summary class - which shows the total number of patients from different health
centres across Australia
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.text.NumberFormat;
import com.parse.ParseObject;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;


public class PieGraph extends Activity {

    double[] pieChartValues;
    String[] nameOfCentres;


    private double VALUE = 0;// Total number of patients across Australia
    public static final String TYPE = "type";
    private static int[] COLORS = new int[] {Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN,Color.RED };
    private CategorySeries mSeries = new CategorySeries("");
    private DefaultRenderer mRenderer = new DefaultRenderer();
    private GraphicalView mChartView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_graph);

        Intent intent = getIntent();

        Bundle b = intent.getExtras();
        ParseObject.registerSubclass(Patient.class);

         // Set up a progress dialog
        final ProgressDialog dlg = new ProgressDialog(PieGraph.this);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Preparing the Pie-Chart. Please wait.");
        dlg.show();


        nameOfCentres=b.getStringArray("nameOfCentres");
        pieChartValues= b.getDoubleArray("numberOfPatients");

       dlg.dismiss();

        for (int i = 0; i <pieChartValues.length; i++)
            VALUE += pieChartValues[i];

       drawPieChart();
    }


    public void drawPieChart(){

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.DKGRAY);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(getResources().getDimension(R.dimen.textsize));
        mRenderer.setLegendTextSize(getResources().getDimension(R.dimen.textsize));
        mRenderer.isInScroll();
        mRenderer.setDisplayValues(true);
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(90);

        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
            mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);
            layout.addView(mChartView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        } else {
            mChartView.repaint();
        }
        fillPieChart();
        mChartView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                if (seriesSelection == null) {
                    Toast.makeText(PieGraph.this, "No chart element selected", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    for (int i = 0; i < mSeries.getItemCount(); i++) {
                        mRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
                    }

                    mChartView.repaint();
                    Toast.makeText(
                            PieGraph.this,
                            "Selected"
                                    + " point value=" + (Math.round(seriesSelection.getValue()*100)) +"%", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void fillPieChart(){
        for(int i=0;i<pieChartValues.length;i++)
        {
            mSeries.add( nameOfCentres[i]+ " : "+pieChartValues[i], pieChartValues[i]/VALUE);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            renderer.setChartValuesFormat(NumberFormat.getPercentInstance());
            mRenderer.addSeriesRenderer(renderer);
            if (mChartView != null)
                mChartView.repaint();
        }
    }

}
