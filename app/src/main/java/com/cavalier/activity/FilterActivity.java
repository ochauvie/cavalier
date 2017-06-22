package com.cavalier.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.cavalier.R;
import com.cavalier.adapter.MontureSpinnerAdapter;
import com.cavalier.adapter.PersonneSpinnerAdapter;
import com.cavalier.model.CoursFilter;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.cavalier.model.TypePersonne;
import com.cavalier.service.MontureService;
import com.cavalier.service.PersonneService;
import com.cavalier.tools.SpinnerTool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilterActivity extends Activity implements DatePickerDialog.OnDateSetListener {

    private Spinner spinnerMoniteur, spinnerCavalier, spinnerMonture;
    private TextView textStartDate, textEndDate;
    private ImageButton startDate, endDate, deleteStartDate, deleteEndDate;
    private DatePickerDialog datePickerDialog = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private CoursFilter coursFilter;

    private final String DATE_START = "start";
    private final String DATE_END = "end";
    private final String EMPTY_FILTER = "Tous";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_filter);

        spinnerCavalier = (Spinner) findViewById(R.id.spCavalier);
        loadSpinnerCavalier(spinnerCavalier);
        spinnerMoniteur = (Spinner) findViewById(R.id.spMoniteur);
        loadSpinnerMoniteur(spinnerMoniteur);
        spinnerMonture = (Spinner) findViewById(R.id.spMonture);
        loadSpinnerMonture(spinnerMonture);

        textStartDate = (TextView)  findViewById(R.id.textViewStartDate);
        startDate = (ImageButton) findViewById(R.id.startDate);
        startDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickDateListener(v, DATE_START);
            }
        });
        deleteStartDate = (ImageButton) findViewById(R.id.deleteStartDate);
        deleteStartDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textStartDate.setText("");
            }
        });

        textEndDate = (TextView)  findViewById(R.id.textViewEndDate);
        endDate = (ImageButton) findViewById(R.id.endDate);
        endDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickDateListener(v, DATE_END);
            }
        });
        deleteEndDate = (ImageButton) findViewById(R.id.deleteEndDate);
        deleteEndDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textEndDate.setText("");
            }
        });

        initFilter();

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_applay:
                onApplay();
                Intent listActivity = new Intent(getApplicationContext(), ListCoursActivity.class);
                listActivity.putExtra("CoursFilter", coursFilter);
                startActivity(listActivity);
                finish();
                return true;
            case R.id.action_close:
                Intent activity = new Intent(getApplicationContext(), ListCoursActivity.class);
                startActivity(activity);
                finish();
                return true;
        }
        return false;
    }

    private void loadSpinnerCavalier(Spinner spinner) {
        List<Personne> personnes = new ArrayList<>();
        personnes.add(new Personne("", EMPTY_FILTER, null, null, null));
        personnes.addAll(PersonneService.findByType(TypePersonne.CAVALIER));
        spinner.setAdapter(new PersonneSpinnerAdapter(this, personnes));
    }

    private void loadSpinnerMoniteur(Spinner spinner) {
        List<Personne> personnes = new ArrayList<>();
        personnes.add(new Personne("", EMPTY_FILTER, null, null, null));
        personnes.addAll(PersonneService.findByType(TypePersonne.MONITEUR));
        spinner.setAdapter(new PersonneSpinnerAdapter(this, personnes));
    }

    private void loadSpinnerMonture(Spinner spinner) {
        List<Monture> montures = new ArrayList<>();
        montures.add(new Monture(EMPTY_FILTER));
        montures.addAll(MontureService.getAll());
        spinner.setAdapter(new MontureSpinnerAdapter(this, montures));
    }

    private void onApplay() {
        if (coursFilter == null) {
            coursFilter = new CoursFilter();
        }
        try {
            if (textStartDate.getText() != null && !"".equals(textStartDate.getText())) {
                coursFilter.setStartDate(sdf.parse(textStartDate.getText().toString()));
            } else {
                coursFilter.setStartDate(null);
            }
            if (textEndDate.getText() != null && !"".equals(textEndDate.getText())) {
                coursFilter.setEndDate(sdf.parse(textEndDate.getText().toString()));
            } else {
                coursFilter.setEndDate(null);
            }
        } catch (ParseException pe) {
            // Nothings
        }
        coursFilter.setCavalierId(((Personne) spinnerCavalier.getSelectedItem()).getId());
        coursFilter.setMoniteurId(((Personne) spinnerMoniteur.getSelectedItem()).getId());
        coursFilter.setMontureId(((Monture) spinnerMonture.getSelectedItem()).getId());

    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String day = String.valueOf(dayOfMonth);
        if (day.length()<2) {day = "0" + day;}
        String month = String.valueOf(monthOfYear+1);
        if (month.length()<2) {month = "0" + month;}
        String y = String.valueOf(year);
        if (y.length()<2) {y = "0" + y;}
        if (DATE_START.equals(view.getTag())) {
            textStartDate.setText(day + "/" + month + "/" + y);
        } else {
            textEndDate.setText(day + "/" + month + "/" + y);
        }
        datePickerDialog.hide();
    }

    private void onClickDateListener(View v, String typeDate) {
        String sDate = "01/01/2016";
        if (DATE_START.equals(typeDate) && textStartDate.getText() != null && !"".equals(textStartDate.getText())) {
                sDate = textStartDate.getText().toString();

        } else if (textEndDate.getText() != null && !"".equals(textEndDate.getText())) {
            sDate = textEndDate.getText().toString();

        }
        String[] ssDate = sDate.split("/");
        datePickerDialog = new DatePickerDialog(v.getContext(),
                FilterActivity.this,
                Integer.parseInt(ssDate[2]),
                Integer.parseInt(ssDate[1])-1,
                Integer.parseInt(ssDate[0]));
        datePickerDialog.getDatePicker().setTag(typeDate);
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        // Nothings
    }

    private void initFilter() {
        coursFilter = (CoursFilter)getIntent().getSerializableExtra("CoursFilter");
        if (coursFilter != null) {
            if (coursFilter.getCavalierId() != null) {
                SpinnerTool.SelectSpinnerItemByValue(spinnerCavalier, PersonneService.getById(coursFilter.getCavalierId()));
            }
            if (coursFilter.getMoniteurId() != null) {
                SpinnerTool.SelectSpinnerItemByValue(spinnerMoniteur, PersonneService.getById(coursFilter.getMoniteurId()));
            }
            if (coursFilter.getMontureId() != null) {
                SpinnerTool.SelectSpinnerItemByValue(spinnerMonture, MontureService.getById(coursFilter.getMontureId()));
            }
            if (coursFilter.getStartDate() != null) {
                textStartDate.setText(sdf.format(coursFilter.getStartDate()));
            }
            if (coursFilter.getEndDate() != null) {
                textEndDate.setText(sdf.format(coursFilter.getEndDate()));
            }
        }
    }

}
