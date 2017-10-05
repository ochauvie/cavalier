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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.adapter.PersonneListAdapter;
import com.cavalier.model.Carte;
import com.cavalier.model.Personne;
import com.cavalier.service.PersonneService;
import com.cavalier.tools.SpinnerTool;
import com.cavalier.tools.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddCarteActivity extends Activity implements DatePickerDialog.OnDateSetListener {

    private Spinner spinnerPersonne;
    private EditText editTextNom, editTextCapacite;
    private TextView textDate;
    private ImageButton selectDate;
    private DatePickerDialog datePickerDialog = null;
    private Personne personne;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_add_carte);

        spinnerPersonne = (Spinner) findViewById(R.id.spPersonne);
        loadSpinnerPersonne(spinnerPersonne);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            long personneId = bundle.getLong(Personne.ID_PERSONNE);
            personne = Personne.load(Personne.class, personneId);
            if (personne != null) {
                SpinnerTool.SelectSpinnerItemByValue(spinnerPersonne, personne);
                spinnerPersonne.setEnabled(false);
            }
        }

        editTextNom = (EditText)  findViewById(R.id.editTextNom);
        editTextCapacite = (EditText)  findViewById(R.id.editTextCapacite);
        textDate = (TextView)  findViewById(R.id.textViewDate);
        //textDate.setEnabled(false);
        textDate.setText(sdf.format(new Date()));

        selectDate = (ImageButton) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sDate = textDate.getText().toString();
                String[] ssDate = sDate.split("/");
                datePickerDialog = new DatePickerDialog(v.getContext(),
                        AddCarteActivity.this,
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
        getMenuInflater().inflate(R.menu.menu_cavalier_add_carte, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemS = menu.findItem(R.id.action_add);
        Utils.enableItem(itemS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                if (onSave()) {
                    Intent myIntent = new Intent(getApplicationContext(), AddPersonneActivity.class);
                    myIntent.putExtra(Personne.ID_PERSONNE, personne.getId());
                    startActivity(myIntent);
                    finish();
                }
                return true;
            case R.id.action_close:
                Intent intent = new Intent(getApplicationContext(), AddPersonneActivity.class);
                intent.putExtra(Personne.ID_PERSONNE, personne.getId());
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    }

    private void loadSpinnerPersonne(Spinner spinner) {
        spinner.setAdapter(new PersonneListAdapter(this, PersonneService.getAll()));
    }



    private boolean onSave() {

        if (editTextCapacite.getText()==null || "".equals(editTextCapacite.getText().toString())
         || editTextNom.getText()==null || "".equals(editTextNom.getText().toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.carte_mandatory), Toast.LENGTH_LONG).show();
            return false;

        } else {
            Carte carte = new Carte();
            try {
                carte.setDateOuverture(sdf.parse(textDate.getText().toString()));
            } catch (ParseException pe) {
                carte.setDateOuverture(new Date());
            }
            personne = (Personne) spinnerPersonne.getSelectedItem();
            carte.setCavalier(personne);
            carte.setNom(editTextNom.getText().toString());
            carte.setCapacite(Integer.valueOf(editTextCapacite.getText().toString()));
            carte.setPointRestant(carte.getCapacite());

            carte.save();
            Toast.makeText(getBaseContext(), getString(R.string.carte_save), Toast.LENGTH_LONG).show();

            return true;
        }
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

}

