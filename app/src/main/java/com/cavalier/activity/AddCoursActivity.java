package com.cavalier.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddCoursActivity extends Activity implements CoursListener {

    private Spinner spinnerMoniteur, spinnerCavalier, spinnerMonture, spinnerLieu;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText editTextDuree, editTextObservation;
    private Cours cours = null;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private final SimpleDateFormat sdfh = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.FRANCE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_add_cours);

        spinnerCavalier = (Spinner) findViewById(R.id.spCavalier);
        loadSpinnerCavalier(spinnerCavalier);
        spinnerMoniteur = (Spinner) findViewById(R.id.spMoniteur);
        loadSpinnerMoniteur(spinnerMoniteur);
        spinnerMonture = (Spinner) findViewById(R.id.spMonture);
        loadSpinnerMonture(spinnerMonture);
        spinnerLieu = (Spinner) findViewById(R.id.spLieu);
        loadSpinnerLieu(spinnerLieu);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        datePicker = (DatePicker) findViewById(R.id.datePicker);


        editTextDuree = (EditText)  findViewById(R.id.editTextDuree);
        editTextDuree.setText("1");
        editTextObservation = (EditText)  findViewById(R.id.editTextObservation);

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Scrool to top
        final ScrollView scrollview = (ScrollView)findViewById(R.id.cours_scrollView);
        scrollview.post(new Runnable() {
            public void run() {
                scrollview.scrollTo(0, 0);
            }
        });

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
        ArrayList<IRefData> list = new ArrayList<>();
        Collections.addAll(list, TypeLieu.values());
        spinner.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.light_custom_spinner));
    }


    private boolean onSave() {
        Editable edDuree = editTextDuree.getText();
        if (edDuree==null || "".equals(edDuree.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.duree_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (cours == null) {
                cours = new Cours();
            }
            int hour = timePicker.getCurrentHour();
            int min = timePicker.getCurrentMinute();
            int day = datePicker.getDayOfMonth();
            int month = (datePicker.getMonth());
            int year = datePicker.getYear();
            Date date = new GregorianCalendar(year, month, day, hour, min).getTime();
            cours.setDate(date);

            cours.setCavalier((Personne) spinnerCavalier.getSelectedItem());
            cours.setMoniteur((Personne) spinnerMoniteur.getSelectedItem());
            cours.setMonture((Monture) spinnerMonture.getSelectedItem());
            cours.setTypeLieu((TypeLieu) spinnerLieu.getSelectedItem());
            cours.setDuree(Integer.valueOf(edDuree.toString()));
            cours.setObservation(editTextObservation.getText().toString());

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
    public void onSelectDate(Cours item, int position) {
        // TODO
    }

    @Override
    public void onShowObservation(Cours item, int position) {
        // Nothings
    }

    @Override
    public void onDelete(Cours item, int position) {
        // Nothings
    }

    @Override
    public void onSelectCavalier(Cours item, int position) {
        // Nothings
    }

    @Override
    public void onSelectMonture(Cours item, int position) {
        // Nothings
    }

    @Override
    public void onSelectMoniteur(Cours item, int position) {

    }

    @Override
    public void onSelectLieu(Cours item, int position) {

    }
}
