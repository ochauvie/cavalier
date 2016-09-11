package com.cavalier.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.cavalier.model.Monture;
import com.cavalier.service.CoursService;
import com.cavalier.service.MontureService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ListCoursActivity extends ListActivity implements MyDialogInterface.DialogReturn, CoursListener{

    private ListView listView;
    private TextView totalText;
    private CoursListAdapter coursListAdapter;
    private MyDialogInterface myInterface;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private Cours selectedCours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_list_cours);

        List<Cours> coursList = CoursService.getAll();
        listView = getListView();

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
        }

        return false;
    }

    @Override
    public void onClick(Cours cours, int position) {
        showDetail(cours);
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
}
