package com.cavalier.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.adapter.IDataSpinnerAdapter;
import com.cavalier.adapter.MontureSpinnerAdapter;
import com.cavalier.adapter.PersonneSpinnerAdapter;
import com.cavalier.listner.CoursListener;
import com.cavalier.model.Cours;
import com.cavalier.model.IRefData;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.cavalier.model.TypeLieu;
import com.cavalier.model.TypePersonne;
import com.cavalier.service.MontureService;
import com.cavalier.service.PersonneService;
import com.cavalier.tools.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class AddCoursActivity extends Activity implements CoursListener {

    private Spinner spinnerMoniteur, spinnerCavalier, spinnerMonture, spinnerLieu;
    private EditText editTextDuree, editTextObservation;
    private Cours cours = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_add_cours);

        spinnerCavalier = (Spinner) findViewById(R.id.spMoniteur);
        loadSpinnerMoniteur(spinnerCavalier);
        spinnerMoniteur = (Spinner) findViewById(R.id.spCavalier);
        loadSpinnerCavalier(spinnerMoniteur);
        spinnerMonture = (Spinner) findViewById(R.id.spMonture);
        loadSpinnerMonture(spinnerMonture);
        spinnerLieu = (Spinner) findViewById(R.id.spLieu);
        loadSpinnerLieu(spinnerLieu);


        editTextDuree = (EditText)  findViewById(R.id.editTextDuree);
        editTextObservation = (EditText)  findViewById(R.id.editTextObservation);

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_add_cours, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemS = menu.findItem(R.id.action_add_cours);
        Utils.enableItem(itemS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_cours:
                if (onSave()) {
//                    Intent listActivity = new Intent(getApplicationContext(), ListCoursActivity.class);
//                    startActivity(listActivity);
                    finish();
                    return true;
                }
                return false;
            case R.id.action_close_cours:
//                Intent listActivity = new Intent(getApplicationContext(), ListCoursActivity.class);
//                startActivity(listActivity);
                finish();
                return true;
        }
        return false;
    }

    private void loadSpinnerCavalier(Spinner spinner) {
        spinner.setAdapter(new PersonneSpinnerAdapter(this, PersonneService.findByType(TypePersonne.CAVALIER)));
    }

    private void loadSpinnerMoniteur(Spinner spinner) {
        spinner.setAdapter(new PersonneSpinnerAdapter(this, PersonneService.findByType(TypePersonne.MONITEUR)));
    }

    private void loadSpinnerMonture(Spinner spinner) {
        spinner.setAdapter(new MontureSpinnerAdapter(this, MontureService.getAll()));
    }

    private void loadSpinnerLieu(Spinner spinner) {
        ArrayList<IRefData> list = new ArrayList<IRefData>();
        Collections.addAll(list, TypeLieu.values());
        spinner.setAdapter(new IDataSpinnerAdapter(this, list));
    }


    private boolean onSave() {
        Editable edDuree = editTextDuree.getText();
        Editable edObservation = editTextObservation.getText();
        if (edDuree==null || "".equals(edDuree.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.duree_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (cours == null) {
                cours = new Cours();
            }
            cours.setDate(new Date());
            cours.setCavalier((Personne) spinnerCavalier.getSelectedItem());
            cours.setMoniteur((Personne) spinnerMoniteur.getSelectedItem());
            cours.setMonture((Monture) spinnerMonture.getSelectedItem());
            cours.setTypeLieu((TypeLieu) spinnerLieu.getSelectedItem());
            cours.setDuree(Integer.valueOf(edDuree.toString()));
            cours.setObservation(editTextObservation.toString());

            cours.save();
            Toast.makeText(getBaseContext(), getString(R.string.cours_save), Toast.LENGTH_LONG).show();
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        // Nothings
    }

    @Override
    public void onClick(Cours item, int position) {
        // Nothings
    }



}
