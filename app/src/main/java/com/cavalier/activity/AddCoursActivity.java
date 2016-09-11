package com.cavalier.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class AddCoursActivity extends Activity implements CoursListener, DatePickerDialog.OnDateSetListener {

    private Spinner spinnerMoniteur, spinnerCavalier, spinnerMonture, spinnerLieu;
    private EditText editTextDuree, editTextObservation;
    private TextView textDate;
    private ImageButton selectDate;
    private DatePickerDialog datePickerDialog = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private Cours cours = null;

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


        editTextDuree = (EditText)  findViewById(R.id.editTextDuree);
        editTextDuree.setText("1");
        editTextObservation = (EditText)  findViewById(R.id.editTextObservation);
        textDate = (TextView)  findViewById(R.id.textViewDate);
        //textDate.setEnabled(false);
        textDate.setText(sdf.format(new Date()));

        selectDate = (ImageButton) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sDate = textDate.getText().toString();
                String[] ssDate = sDate.split("/");
                datePickerDialog = new DatePickerDialog(v.getContext(),
                        AddCoursActivity.this,
                        Integer.parseInt(ssDate[2]),
                        Integer.parseInt(ssDate[1])-1,
                        Integer.parseInt(ssDate[0]));
                datePickerDialog.show();
            }
        });
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
        spinner.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.light_custom_spinner));
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
            try {
                cours.setDate(sdf.parse(textDate.getText().toString()));
            } catch (ParseException pe) {
                cours.setDate(new Date());
            }
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
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String day = String.valueOf(dayOfMonth);
        if (day.length()<2) {day = "0" + day;}
        String month = String.valueOf(monthOfYear+1);
        if (month.length()<2) {month = "0" + month;}
        String y = String.valueOf(year);
        if (y.length()<2) {y = "0" + y;}
        textDate.setText(day + "/" + month + "/" + y);
        datePickerDialog.hide();
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
