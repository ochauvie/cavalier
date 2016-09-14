package com.cavalier.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.adapter.CoursListAdapter;
import com.cavalier.adapter.MontureListAdapter;
import com.cavalier.listner.CoursListener;
import com.cavalier.listner.MontureListener;
import com.cavalier.model.Cours;
import com.cavalier.model.CoursFilter;
import com.cavalier.model.Monture;
import com.cavalier.service.CoursService;
import com.cavalier.service.MontureService;
import com.cavalier.tools.Chart;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ListCoursActivity extends ListActivity implements MyDialogInterface.DialogReturn, CoursListener, View.OnClickListener {

    private View header;
    private ListView listView;
    private TextView totalText, headerMonture, headerCavalier, headerDate, headerMoniteur, headerLieu;
    private CoursListAdapter coursListAdapter;
    private MyDialogInterface myInterface;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private Cours selectedCours;
    List<Cours> coursList;
    private CoursFilter coursFilter = new CoursFilter();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_list_cours);

        coursList = CoursService.getAll();
        listView = getListView();

        header = findViewById(R.id.header_layout);
        headerMonture = (TextView) header.findViewById(R.id.monture);
        headerCavalier = (TextView) header.findViewById(R.id.cavalier);
        headerMoniteur = (TextView) header.findViewById(R.id.moniteur);
        headerLieu = (TextView) header.findViewById(R.id.lieu);
        headerDate = (TextView) header.findViewById(R.id.date);
        header.setOnClickListener(this);

        View footer = findViewById(R.id.footer_layout);
        totalText = (TextView) footer.findViewById(R.id.totalText);
        totalText.setText(String.valueOf(coursList.size()));

        // Creation et initialisation de l'Adapter
        coursListAdapter = new CoursListAdapter(this, coursList);
        coursListAdapter.addListener(this);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        //Initialisation de la liste avec les donnees
        setListAdapter(coursListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_list_cours, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close_list:
                finish();
                return true;
            case R.id.barchartbycavalier:
                viewBarChart(Chart.CHART_BY_CAVALIER);
                return true;
            case R.id.barchartbymonture:
                viewBarChart(Chart.CHART_BY_MONTURE);
                return true;
        }
        return false;
    }

    @Override
    public void onShowObservation(Cours cours, int position) {
        showDetail(cours);
    }

    @Override
    public void onSelectCavalier(Cours item, int position) {
        if (coursList!=null) {
            for (int i=coursList.size()-1; i>=0; i--) {
                coursList.remove(i);
            }
        }
        coursListAdapter.notifyDataSetChanged();
        coursList.addAll(CoursService.getByCavalier(item.getCavalier()));

        headerCavalier.setTextColor(Color.rgb(219, 23, 2));
        headerDate.setTextColor(Color.WHITE);
        headerMonture.setTextColor(Color.WHITE);
        if (headerMoniteur != null) {
            headerMoniteur.setTextColor(Color.WHITE);
        }
        if (headerLieu != null) {
            headerLieu.setTextColor(Color.WHITE);
        }

        coursListAdapter.notifyDataSetChanged();
        totalText.setText(String.valueOf(coursList.size()));
    }

    @Override
    public void onSelectMonture(Cours item, int position) {
        if (coursList!=null) {
            for (int i=coursList.size()-1; i>=0; i--) {
                coursList.remove(i);
            }
        }
        coursListAdapter.notifyDataSetChanged();
        coursList.addAll(CoursService.getByMonture(item.getMonture()));

        headerCavalier.setTextColor(Color.WHITE);
        headerDate.setTextColor(Color.WHITE);
        headerMonture.setTextColor(Color.rgb(219, 23, 2));
        if (headerMoniteur != null) {
            headerMoniteur.setTextColor(Color.WHITE);
        }
        if (headerLieu != null) {
            headerLieu.setTextColor(Color.WHITE);
        }

        coursListAdapter.notifyDataSetChanged();
        totalText.setText(String.valueOf(coursList.size()));
    }

    @Override
    public void onSelectMoniteur(Cours item, int position) {
        if (coursList!=null) {
            for (int i=coursList.size()-1; i>=0; i--) {
                coursList.remove(i);
            }
        }
        coursListAdapter.notifyDataSetChanged();
        coursList.addAll(CoursService.getByMoniteur(item.getMoniteur()));

        headerMoniteur.setTextColor(Color.rgb(219, 23, 2));
        headerLieu.setTextColor(Color.WHITE);
        headerCavalier.setTextColor(Color.WHITE);
        headerDate.setTextColor(Color.WHITE);
        headerMonture.setTextColor(Color.WHITE);

        coursListAdapter.notifyDataSetChanged();
        totalText.setText(String.valueOf(coursList.size()));
    }

    @Override
    public void onSelectLieu(Cours item, int position) {
        if (coursList!=null) {
            for (int i=coursList.size()-1; i>=0; i--) {
                coursList.remove(i);
            }
        }
        coursListAdapter.notifyDataSetChanged();
        coursList.addAll(CoursService.getByTypeLieu(item.getTypeLieu()));

        headerMoniteur.setTextColor(Color.WHITE);
        headerLieu.setTextColor(Color.rgb(219, 23, 2));
        headerCavalier.setTextColor(Color.WHITE);
        headerDate.setTextColor(Color.WHITE);
        headerMonture.setTextColor(Color.WHITE);

        coursListAdapter.notifyDataSetChanged();
        totalText.setText(String.valueOf(coursList.size()));
    }

    @Override
    public void onSelectDate(Cours item, int position) {
        if (coursList!=null) {
            for (int i=coursList.size()-1; i>=0; i--) {
                coursList.remove(i);
            }
        }
        coursListAdapter.notifyDataSetChanged();
        coursList.addAll(CoursService.getByDate(item.getDate()));

        headerCavalier.setTextColor(Color.WHITE);
        headerDate.setTextColor(Color.rgb(219, 23, 2));
        headerMonture.setTextColor(Color.WHITE);
        if (headerMoniteur != null) {
            headerMoniteur.setTextColor(Color.WHITE);
        }
        if (headerLieu != null) {
            headerLieu.setTextColor(Color.WHITE);
        }

        coursListAdapter.notifyDataSetChanged();
        totalText.setText(String.valueOf(coursList.size()));
    }

        @Override
    public void onDelete(Cours cours, int position) {
        if (cours != null) {
            selectedCours = cours;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(sdf.format(cours.getDate()) + " - "
                    + cours.getCavalier().getPrenom()
                    + " - " + cours.getMonture().getNom());
            builder.setMessage("Observation: " + cours.getObservation());
            builder.setInverseBackgroundForced(true);
            builder.setNegativeButton(R.string.action_close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(R.string.action_delete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(true, null);
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onBackPressed() {
        // Nothings
    }

    private boolean showDetail(Cours cours) {
        if (cours != null) {
            selectedCours = cours;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.details);
            builder.setTitle(sdf.format(cours.getDate()) + " - "
                    + cours.getCavalier().getPrenom()
                    + " - " + cours.getMonture().getNom());
            builder.setMessage("Observation: " + cours.getObservation());
            builder.setInverseBackgroundForced(true);
            builder.setNegativeButton(R.string.action_close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return true;
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if (answer && selectedCours!=null) {
            selectedCours.delete();
            Toast.makeText(getBaseContext(), getString(R.string.cours_delete), Toast.LENGTH_LONG).show();
            Intent listActivity = new Intent(getApplicationContext(), ListCoursActivity.class);
            startActivity(listActivity);
            finish();
        }
    }

    @Override
    // Suppression des filtres
    public void onClick(View v) {
        if (coursList!=null) {
            for (int i=coursList.size()-1; i>=0; i--) {
                coursList.remove(i);
            }
        }
        coursListAdapter.notifyDataSetChanged();
        coursList.addAll(CoursService.getAll());
        coursListAdapter.notifyDataSetChanged();

        headerCavalier.setTextColor(Color.WHITE);
        headerDate.setTextColor(Color.WHITE);
        headerMonture.setTextColor(Color.WHITE);
        if (headerMoniteur != null) {
            headerMoniteur.setTextColor(Color.WHITE);
        }
        if (headerLieu != null) {
            headerLieu.setTextColor(Color.WHITE);
        }
        totalText.setText(String.valueOf(coursList.size()));
    }

    /**
     * Make bar chart
     * @param chartType
     */
    private void viewBarChart(String chartType) {
        String title = getString(R.string.title_activity_chart_cavalier);
        if (Chart.CHART_BY_MONTURE.equals(chartType)) {
            title = getString(R.string.title_activity_chart_monture);
        }
        Chart chart = new Chart(getBaseContext(), coursList, chartType, title);
        startActivity(chart.getIntentChart());
    }


    /**
     * Make pie chart
     * @param chartType
     */
    private void viewPieChart(String chartType) {
//        String title = getString(R.string.title_activity_chart_nb);
//        if (Chart.CHART_TIME.equals(chartType)) {
//            title = getString(R.string.title_activity_chart_time);
//        }
//        Chart chart = new Chart(getBaseContext(), coursList, chartType, title);
//        startActivity(chart.getIntentPieChart());
    }
}
