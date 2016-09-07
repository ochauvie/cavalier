package com.tankbattle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.cavalier.R;
import com.tankbattle.adapter.EquipeSpinnerAdapter;
import com.tankbattle.model.Bataille;
import com.tankbattle.model.BatailleTank;
import com.tankbattle.model.Equipe;
import com.tankbattle.model.EquipeTank;
import com.tankbattle.model.Tank;
import com.tankbattle.service.EquipeService;
import com.tankbattle.service.TankService;

import java.util.Date;
import java.util.List;


/**
 * Created by Olivier on 13/09/15.
 */
public class CreateBatailleActivity extends Activity {

    private EditText editTextNom;
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

        editTextNom = (EditText)  findViewById(R.id.editTextNom);
    }

    private void loadSpinnerEquipe(Spinner spinner) {
        spinner.setAdapter(new EquipeSpinnerAdapter(this, EquipeService.getAllEquipes()));
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
        Equipe equipe1 = (Equipe) spEquipe1.getSelectedItem();
        Equipe equipe2 = (Equipe) spEquipe2.getSelectedItem();
        if (edName==null || "".equals(edName.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.nom_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else if (equipe1==null || equipe2==null || equipe1.getId().equals(equipe2.getId())) {
            Toast.makeText(getBaseContext(), getString(R.string.equipes_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else if (isConstitutionEquipeKo(equipe1, equipe2)) {
            Toast.makeText(getBaseContext(), getString(R.string.equipes_ko), Toast.LENGTH_LONG).show();
            return false;
        } else {
            try {
                ActiveAndroid.beginTransaction();
                if (currentyBataille == null) {
                    currentyBataille = new Bataille();
                }
                currentyBataille.setDateCreation(new Date());
                currentyBataille.setFinished(0);
                currentyBataille.setNom(edName.toString());

                currentyBataille.setEquipe1(equipe1);
                currentyBataille.setEquipe2(equipe2);

                currentyBataille.save();

                List<EquipeTank> equipeTanks1 = equipe1.tanks();
                for (EquipeTank equipeTank1:equipeTanks1) {
                    Tank hangarTank = TankService.getTankById(equipeTank1.getTank().getId());
                    BatailleTank bT = new BatailleTank(currentyBataille, equipeTank1.getTank(), hangarTank.getPv());
                    bT.save();
                }
                List<EquipeTank> equipeTanks2 = equipe2.tanks();
                for (EquipeTank equipeTank2:equipeTanks2) {
                    Tank hangarTank2 = TankService.getTankById(equipeTank2.getTank().getId());
                    BatailleTank bT2 = new BatailleTank(currentyBataille, equipeTank2.getTank(), hangarTank2.getPv());
                    bT2.save();
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

    @Override
    public void onBackPressed() {
        // Nothings
    }

    private boolean isConstitutionEquipeKo(Equipe equipe1, Equipe equipe2) {
        for (EquipeTank equipeTank1:equipe1.tanks()) {
            if (equipe2.isTankOfEquipe(equipeTank1.getTank())) {
                return true;
            }
        }
        return false;
    }

}

