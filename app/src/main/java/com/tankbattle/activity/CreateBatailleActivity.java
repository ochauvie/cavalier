package com.tankbattle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.tankbattle.R;
import com.tankbattle.adapter.EquipeSpinnerAdapter;
import com.tankbattle.model.Bataille;
import com.tankbattle.model.BatailleTank;
import com.tankbattle.model.Equipe;
import com.tankbattle.model.EquipeTank;
import com.tankbattle.model.Genre;
import com.tankbattle.model.Nation;
import com.tankbattle.model.Tank;
import com.tankbattle.service.EquipeService;

import java.util.Date;
import java.util.List;


/**
 * Created by Olivier on 13/09/15.
 */
public class CreateBatailleActivity extends Activity {

    private TextView textDateCreation, textDateFin;
    private EditText editTextNom;
    private Button btStart;
    private Spinner spEquipe1, spEquipe2;
    private Bataille currentyBataille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bataille);

        spEquipe1 = (Spinner) findViewById(R.id.spEquipe1);
        loadSpinnerEquipe(spEquipe1);

        spEquipe2 = (Spinner) findViewById(R.id.spEquipe2);
        loadSpinnerEquipe(spEquipe2);

        textDateCreation = (TextView)  findViewById(R.id.textDateCreation);
        textDateFin = (TextView)  findViewById(R.id.textDateFin);

        initView();
    }

    private void loadSpinnerEquipe(Spinner spinner) {
        spinner.setAdapter(new EquipeSpinnerAdapter(this, EquipeService.getAllEquipes()));
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            // TODO
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_bataille, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_bataille:
                if (onCreateBataille()) {
                    Intent batailleActivity = new Intent(getApplicationContext(), BatailleActivity.class);
                    startActivity(batailleActivity);
                    finish();
                    return true;
                }
                return false;
            case R.id.action_close_bataille:
                finish();
                return true;
        }
        return false;
    }

    private boolean onCreateBataille() {
        Editable edName = editTextNom.getText();
        if (edName==null || "".equals(edName.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.nom_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else {
            try {
                ActiveAndroid.beginTransaction();
                if (currentyBataille == null) {
                    currentyBataille = new Bataille();
                }
                currentyBataille.setDateCreation(new Date());
                currentyBataille.setNom(edName.toString());
                Equipe equipe1 = (Equipe) spEquipe1.getSelectedItem();
                Equipe equipe2 = (Equipe) spEquipe2.getSelectedItem();
                currentyBataille.setEquipe1(equipe1);
                currentyBataille.setEquipe2(equipe2);
                currentyBataille.save();

                List<EquipeTank> equipeTanks1 = equipe1.tanks();
                for (EquipeTank equipeTank1:equipeTanks1) {
                    BatailleTank bT = new BatailleTank(currentyBataille, equipeTank1.getTank(), equipeTank1.getTank().getPv());
                    bT.save();
                }
                List<EquipeTank> equipeTanks2 = equipe2.tanks();
                for (EquipeTank equipeTank2:equipeTanks2) {
                    BatailleTank bT = new BatailleTank(currentyBataille, equipeTank2.getTank(), equipeTank2.getTank().getPv());
                    bT.save();
                }

                ActiveAndroid.setTransactionSuccessful();
                Toast.makeText(getBaseContext(), getString(R.string.bataille_save), Toast.LENGTH_LONG).show();
            }
            finally {
                ActiveAndroid.endTransaction();
            }
        }
        return true;
    }



}

