package com.cavalier.tools;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import com.cavalier.R;
import com.cavalier.model.Cours;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Chart {

    public static final String CHART_TIME = "TIME";
    public static final String CHART_NB = "NB";
    public static final String CHART_BY_CAVALIER = "CAVALIER";
    public static final String CHART_BY_MONTURE = "MONTURE";

    private Context context;
    private List<Cours> coursList;
    private String chartType;
    private String chartTitle;

    public Chart(Context context, List<Cours> coursList, String chartType, String chartTitle) {
        this.context = context;
        this.coursList = coursList;
        this.chartType = chartType;
        this.chartTitle = chartTitle;
    }

    /**
     * Return pie chart
     * @return
     */
    public Intent getIntentPieChart() {
        CategorySeries distributionSeries = new CategorySeries(" Cours ");
        Map<Personne, Double> cavalierMap = new HashMap<>();
        Map<Monture, Double> montureMap = new HashMap<>();

        if (coursList !=null) {
            for (Cours cours : coursList) {
                if (cavalierMap.containsKey(cours.getCavalier())) {
                    cavalierMap.put(cours.getCavalier(), cavalierMap.get(cours.getCavalier()) + 1);
                } else {
                    cavalierMap.put(cours.getCavalier(), Double.valueOf(1));
                }
                if (montureMap.containsKey(cours.getMonture())) {
                    montureMap.put(cours.getMonture(), montureMap.get(cours.getMonture()) + 1);
                } else {
                    montureMap.put(cours.getMonture(), Double.valueOf(1));
                }

            }
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        defaultRenderer.setChartTitle(context.getString(R.string.menu_chart));
        defaultRenderer.setChartTitleTextSize(25);
        defaultRenderer.setZoomButtonsVisible(true);
        defaultRenderer.setShowLabels(false);
        defaultRenderer.setLabelsTextSize(25);
        defaultRenderer.setLegendTextSize(25);
        defaultRenderer.setDisplayValues(true);

        if (CHART_BY_CAVALIER.equals(chartType)) {
            for (Personne personne : cavalierMap.keySet()) {
                distributionSeries.add(personne.getPrenom() + " " + personne.getNom(), cavalierMap.get(personne));
                SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
                seriesRenderer.setColor(randomColor());
                seriesRenderer.setDisplayChartValues(true);
                defaultRenderer.addSeriesRenderer(seriesRenderer);
            }
        } else {
            for (Monture monture : montureMap.keySet()) {
                distributionSeries.add(monture.getNom(), montureMap.get(monture));
                SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
                seriesRenderer.setColor(randomColor());
                seriesRenderer.setDisplayChartValues(true);
                defaultRenderer.addSeriesRenderer(seriesRenderer);
            }
        }

        return ChartFactory.getPieChartIntent(context, distributionSeries, defaultRenderer, chartTitle);
    }


    /**
     * Retrun bar chart
     * @return
     */
    public Intent getIntentChart() {

        // Instantiating a renderer
        XYMultipleSeriesRenderer defaultRenderer  = new XYMultipleSeriesRenderer ();
        defaultRenderer.setZoomButtonsVisible(false);
        defaultRenderer.setLabelsTextSize(25);
        defaultRenderer.setShowLegend(false);
        defaultRenderer.setMargins(new int[]{20, 30, 15, 0});
        defaultRenderer.setBarSpacing(0.1);
        defaultRenderer.setYAxisMin(0);
        defaultRenderer.setYLabelsAlign(Paint.Align.LEFT);
        defaultRenderer.setXAxisMin(0);
        defaultRenderer.setXLabels(0);
        defaultRenderer.setBackgroundColor(Color.BLACK);
        defaultRenderer.setApplyBackgroundColor(true);
        defaultRenderer.setShowGrid(true);


        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        if (Chart.CHART_BY_MONTURE.equals(chartType)) {
            r.setColor(Color.rgb(219,23,2));
        } else {
            r.setColor(Color.rgb(31,160,85));
        }
        r.setDisplayChartValues(true);
        r.setChartValuesTextSize(25);
        r.setChartValuesTextAlign(Paint.Align.CENTER);
        defaultRenderer.addSeriesRenderer(r);

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        CategorySeries series = new CategorySeries("");
        Hashtable hashItems = new Hashtable();

        if (coursList !=null) {
            for (Cours cours : coursList) {
                String key = cours.getCavalier().getPrenom() + " - " + cours.getCavalier().getNom();
                if (CHART_BY_MONTURE.equals(chartType)) {
                    key = cours.getMonture().getNom();
                }
                if (hashItems.containsKey(key)) {
                    int old = (Integer) hashItems.get(key);
                    hashItems.put(key, 1 + old);

                } else {
                    hashItems.put(key, 1);
                }
            }
        }

        Iterator iter = hashItems.keySet().iterator();
        int i=0;
        int maxY = 0;
        while (iter.hasNext())
        {
            String clef = (String)iter.next();
            int value = (Integer) hashItems.get(clef);
            if (value>maxY) {
                maxY = value;
            }
            series.add(value);
            i++;
            defaultRenderer.addXTextLabel(i, clef);
            defaultRenderer.setXLabelsAngle(45);
        }
        defaultRenderer.setXAxisMax(hashItems.size()+1);
        defaultRenderer.setYAxisMax(maxY + 10 * maxY / 100);
        dataSet.addSeries(series.toXYSeries());

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        return ChartFactory.getBarChartIntent(context, dataSet, defaultRenderer, BarChart.Type.DEFAULT, chartTitle);
    }

    private int randomColor()
    {
        Random random=new Random();
        int red=random.nextInt(256);
        int green=random.nextInt(256);
        int blue=random.nextInt(256);
        return Color.rgb(red, green, blue);
    }

}
