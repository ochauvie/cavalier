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
import com.tankbattle.adapter.IDataSpinnerAdapter;
import com.tankbattle.model.Genre;
import com.tankbattle.model.IRefData;
import com.tankbattle.model.Nation;
import com.tankbattle.model.Tank;
import com.tankbattle.service.TankService;
import com.tankbattle.tools.SpinnerTool;

import java.util.ArrayList;

public class AddTankActivity extends Activity {

    private Spinner spinnerNation, spinnerGenre;
    private EditText editTextNom, editTextPv;
    private Tank tank = null;

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

        initView();

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
                if (onSave()) {
                    Intent listTankActivity = new Intent(getApplicationContext(), ListTankActivity.class);
                    startActivity(listTankActivity);
                    finish();
                    return true;
                }
            case R.id.action_close_tank:
                Intent listTankActivity = new Intent(getApplicationContext(), ListTankActivity.class);
                startActivity(listTankActivity);
                finish();
                return true;
            case R.id.action_delete_tank:
                if (onDelete()) {
                    listTankActivity = new Intent(getApplicationContext(), ListTankActivity.class);
                    startActivity(listTankActivity);
                    finish();
                    return true;
                }
        }
        return false;
    }

    private void loadSpinnerNation() {
        ArrayList<IRefData> list = new ArrayList<IRefData>();
        for (Nation nation:Nation.values()) {
            list.add(nation);
        }
//        ArrayAdapter<Nation> dataAdapter = new ArrayAdapter<Nation>(this, android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerNation.setAdapter(dataAdapter);
        spinnerNation.setAdapter(new IDataSpinnerAdapter(this, list));
    }

    private void loadSpinnerGenre() {
        ArrayList<IRefData> list = new ArrayList<IRefData>();
        for (Genre genre:Genre.values()) {
            list.add(genre);
        }
//        ArrayAdapter<Genre> dataAdapter = new ArrayAdapter<Genre>(this, android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerGenre.setAdapter(dataAdapter);
        spinnerGenre.setAdapter(new IDataSpinnerAdapter(this, list));
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            //tank = (Tank)bundle.getSerializable(Tank.class.getName());
            long tankId = bundle.getLong(Tank.TANK_ID);
            tank = Tank.load(Tank.class, tankId);
            if (tank!=null) {
                editTextNom.setText(tank.getNom());
                editTextPv.setText(String.valueOf(tank.getPv()));
                SpinnerTool.SelectSpinnerItemByValue(spinnerNation, tank.getNation().name());
                SpinnerTool.SelectSpinnerItemByValue(spinnerGenre, tank.getGenre().name());
            }
        }
    }

    private boolean onSave() {
        Editable edName = editTextNom.getText();
        Editable edPv = editTextPv.getText();
        if (edName==null || "".equals(edName.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.nom_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else if (edPv==null || "".equals(edPv.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.pv_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (tank == null) {
                tank = new Tank();
            }
            tank.setNation((Nation) spinnerNation.getSelectedItem());
            tank.setGenre((Genre) spinnerGenre.getSelectedItem());
            tank.setNom(edName.toString());
            tank.setPv(Integer.valueOf(edPv.toString()));

            tank.save();
            Toast.makeText(getBaseContext(), getString(R.string.tank_save), Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private boolean onDelete() {
        // TODO Popup e confirmation
        if (tank != null) {
            tank.delete();
            Toast.makeText(getBaseContext(), getString(R.string.tank_delete), Toast.LENGTH_LONG).show();
        }
        return true;
    }

}
