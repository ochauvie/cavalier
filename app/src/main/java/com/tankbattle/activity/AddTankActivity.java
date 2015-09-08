package com.tankbattle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tankbattle.R;
import com.tankbattle.model.Genre;
import com.tankbattle.model.Nation;
import com.tankbattle.model.Tank;
import com.tankbattle.service.TankService;

import java.util.ArrayList;

public class AddTankActivity extends Activity {

    private Spinner spinnerNation, spinnerGenre;
    private EditText editTextNom, editTextPv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tank);

        spinnerNation = (Spinner) findViewById(R.id.spNation);
        loadSpinnerNation();

        spinnerGenre = (Spinner) findViewById(R.id.spGenre);
        loadSpinnerGenre();

        editTextNom = (EditText)  findViewById(R.id.editTextNom);
        editTextPv = (EditText)  findViewById(R.id.editTextPv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_tank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_tank:
                onSave();
                return true;
            case R.id.action_close_tank:
                finish();
                return true;
        }
        return false;
    }

    private void loadSpinnerNation() {
        ArrayList<Nation> list = new ArrayList<Nation>();
        for (Nation nation:Nation.values()) {
            list.add(nation);
        }
        ArrayAdapter<Nation> dataAdapter = new ArrayAdapter<Nation>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNation.setAdapter(dataAdapter);
    }

    private void loadSpinnerGenre() {
        ArrayList<Genre> list = new ArrayList<Genre>();
        for (Genre genre:Genre.values()) {
            list.add(genre);
        }
        ArrayAdapter<Genre> dataAdapter = new ArrayAdapter<Genre>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(dataAdapter);
    }

    private void onSave() {
        Editable edName = editTextNom.getText();
        Editable edPv = editTextPv.getText();
        if (edName==null || "".equals(edName.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.nom_mandatory), Toast.LENGTH_LONG).show();
        } else {

            Tank newTank = new Tank();
            newTank.setNation((Nation)spinnerNation.getSelectedItem());
            newTank.setGenre((Genre) spinnerGenre.getSelectedItem());
            newTank.setNom(edName.toString());
            newTank.setPv(Integer.valueOf(edPv.toString()));

            newTank.save();
            Toast.makeText(getBaseContext(), getString(R.string.tank_save), Toast.LENGTH_LONG).show();

            Intent listTankActivity = new Intent(getApplicationContext(), ListTankActivity.class);
            startActivity(listTankActivity);
            finish();

        }
    }

}
