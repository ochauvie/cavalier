package com.tankbattle.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.tankbattle.R;
import com.tankbattle.adapter.TankInEquipeListAdapter;
import com.tankbattle.listner.TankInBatailleListener;
import com.tankbattle.model.Bataille;
import com.tankbattle.model.BatailleTank;
import com.tankbattle.model.Tank;
import com.tankbattle.service.BatailleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by olivier on 14/09/15.
 */
public class BatailleActivity extends Activity implements TankInBatailleListener{

    private ListView listViewEquipe1, listViewEquipe2;
    private TextView textViewNom, textViewDate;
    private Bataille bataille;
    private List<Tank> tanksEquipe1 = new ArrayList<Tank>();
    private List<Tank> tanksEquipe2 = new ArrayList<Tank>();
    private TankInEquipeListAdapter adapterEquipe1, adapterEquipe2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bataille);

        textViewNom = (TextView)  findViewById(R.id.textViewNom);
        textViewDate = (TextView)  findViewById(R.id.textViewDate);
        listViewEquipe1 = (ListView) findViewById(R.id.listViewEquipe1);
        listViewEquipe2 = (ListView) findViewById(R.id.listViewEquipe2);

        initView();

        adapterEquipe1 = new TankInEquipeListAdapter(this, tanksEquipe1, true, null);
        adapterEquipe1.addListener(this);
        adapterEquipe2 = new TankInEquipeListAdapter(this, tanksEquipe2, true, null);
        adapterEquipe2.addListener(this);
        listViewEquipe1.setAdapter(adapterEquipe1);
        listViewEquipe2.setAdapter(adapterEquipe2);
    }

    private void initView() {
        bataille = BatailleService.getCurrentBataille();
        if (bataille!=null) {
            textViewNom.setText(bataille.getNom());
            textViewDate.setText(bataille.getDateCreation().toString());

            List<BatailleTank> batailleTanks = bataille.tanks();
            for (BatailleTank batailleTank:batailleTanks) {
                Tank tank = batailleTank.getTank();
                tank.setPv(batailleTank.getPvRestant());
                if (bataille.getEquipe1().isTankOfEquipe(tank)) {
                    tanksEquipe1.add(tank);
                } else if (bataille.getEquipe2().isTankOfEquipe(tank)) {
                    tanksEquipe2.add(tank);
                }
            }
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.no_current_bataille), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bataille, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_bataille:
                // Sauvegarder sans quitter
                onSaveBataille();
                return false;
            case R.id.action_exit_bataille:
                // Quitter en sauvegardant
                onExitBataille();
                return true;
            case R.id.action_close_bataille:
                // Clôturer la bataille:
                onEndBataille();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void onSaveBataille() {
        try {
            ActiveAndroid.beginTransaction();
            for (Tank tank1:tanksEquipe1) {
                for (BatailleTank bt:bataille.tanks()) {
                    if (bt.getTank().getId()==tank1.getId()) {
                        bt.setPvRestant(tank1.getPv());
                        bt.save();
                        break;
                    }
                }
            }
            for (Tank tank2:tanksEquipe2) {
                for (BatailleTank bt:bataille.tanks()) {
                    if (bt.getTank().getId()==tank2.getId()) {
                        bt.setPvRestant(tank2.getPv());
                        bt.save();
                        break;
                    }
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            Toast.makeText(getBaseContext(), getString(R.string.current_bataille_save), Toast.LENGTH_LONG).show();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }

    private void onExitBataille() {
        // TODO Popup Oui/Non
        onSaveBataille();
        finish();
    }

    private void onEndBataille() {
        // TODO Popup Oui/Non
        onSaveBataille();
        bataille.setDateFin(new Date());
        bataille.save();
        Toast.makeText(getBaseContext(), getString(R.string.current_bataille_end), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onClickAddPv(Tank item, int position) {
        item.setPv(item.getPv()+1);
        if (bataille.getEquipe1().isTankOfEquipe(item)) {
            adapterEquipe1.notifyDataSetChanged();
        } else {
            adapterEquipe2.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickDeletePv(Tank item, int position) {
        item.setPv(item.getPv()-1);
        if (bataille.getEquipe1().isTankOfEquipe(item)) {
            adapterEquipe1.notifyDataSetChanged();
        } else {
            adapterEquipe2.notifyDataSetChanged();
        }
    }
}

