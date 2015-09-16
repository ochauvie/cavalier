package com.tankbattle.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.tankbattle.R;
import com.tankbattle.adapter.TankInEquipeListAdapter;
import com.tankbattle.adapter.TankListAdapter;
import com.tankbattle.listner.TankInBatailleListener;
import com.tankbattle.model.Bataille;
import com.tankbattle.model.BatailleTank;
import com.tankbattle.model.Tank;
import com.tankbattle.model.TankVictoires;
import com.tankbattle.service.BatailleService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by olivier on 14/09/15.
 */
public class BatailleActivity extends Activity implements TankInBatailleListener, MyDialogInterface.DialogReturn{

    private ListView listViewEquipe1, listViewEquipe2;
    private TextView textViewNom, textViewDate, viewTextEquipe1, viewTextEquipe2;
    private Bataille bataille;
    private List<Tank> tanksEquipe1 = new ArrayList<Tank>();
    private List<Tank> tanksEquipe2 = new ArrayList<Tank>();
    private TankInEquipeListAdapter adapterEquipe1, adapterEquipe2;
    private MyDialogInterface myInterface;
    private Tank currentVictoire = null;
    private Tank currentDestroy = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bataille);

        textViewNom = (TextView)  findViewById(R.id.textViewNom);
        textViewDate = (TextView)  findViewById(R.id.textViewDate);
        viewTextEquipe1 = (TextView)  findViewById(R.id.viewTextEquipe1);
        viewTextEquipe2 = (TextView)  findViewById(R.id.viewTextEquipe2);
        listViewEquipe1 = (ListView) findViewById(R.id.listViewEquipe1);
        listViewEquipe2 = (ListView) findViewById(R.id.listViewEquipe2);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        initView();

        adapterEquipe1 = new TankInEquipeListAdapter(this, tanksEquipe1, true, null, false, bataille.getNom());
        adapterEquipe1.addListener(this);
        adapterEquipe2 = new TankInEquipeListAdapter(this, tanksEquipe2, true, null, false, bataille.getNom());
        adapterEquipe2.addListener(this);
        listViewEquipe1.setAdapter(adapterEquipe1);
        listViewEquipe2.setAdapter(adapterEquipe2);
        listViewEquipe1.setScrollbarFadingEnabled(false);
        listViewEquipe2.setScrollbarFadingEnabled(false);
    }

    private void initView() {
        bataille = BatailleService.getCurrentBataille();
        if (bataille!=null) {
            textViewNom.setText(bataille.getNom());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            String sDateCrea = sdf.format(bataille.getDateCreation());
            textViewDate.setText(sDateCrea);

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
            updateEquipeTitle();
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.no_current_bataille), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemS = menu.findItem(R.id.action_save_bataille);
        MenuItem itemE = menu.findItem(R.id.action_exit_bataille);
        MenuItem itemC = menu.findItem(R.id.action_close_bataille);
        itemS.setEnabled(true);
        itemS.getIcon().setAlpha(255);
        itemE.setEnabled(true);
        itemE.getIcon().setAlpha(255);
        itemC.setEnabled(true);
        itemC.getIcon().setAlpha(255);
        return true;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.delete);
        builder.setTitle("Faire une pause");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myInterface.getListener().onDialogCompleted(true, "ACTION_PAUSE");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myInterface.getListener().onDialogCompleted(false, "ACTION_PAUSE");
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void onEndBataille() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.delete);
        builder.setTitle("Terminer la bataille " + bataille.getNom());
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myInterface.getListener().onDialogCompleted(true, "ACTION_END");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myInterface.getListener().onDialogCompleted(false, "ACTION_END");
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if (answer) {
            if ("ACTION_PAUSE".equals(type)) {
                onSaveBataille();
                finish();
            }
            else if ("ACTION_END".equals(type)) {
                onSaveBataille();
                //bataille.setDateFin(new Date());
                //bataille.setFinished(1);
                //bataille.save();
                bataille.delete();
                finish();
            }
            else if ("VICTOIRE_OK".equals(type)) {
                if (currentVictoire!=null && currentDestroy!=null) {
                    TankVictoires tankVictoire = new TankVictoires(bataille.getNom(), currentDestroy, currentVictoire);
                    tankVictoire.save();
                    adapterEquipe1.notifyDataSetChanged();
                    adapterEquipe2.notifyDataSetChanged();
                    Toast.makeText(getBaseContext(), getString(R.string.victoire_save), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onClickAddPv(Tank item, int position) {
        item.setPv(item.getPv() + 1);
        if (bataille.getEquipe1().isTankOfEquipe(item)) {
            adapterEquipe1.notifyDataSetChanged();
        } else {
            adapterEquipe2.notifyDataSetChanged();
        }
        updateEquipeTitle();
    }

    @Override
    public void onClickDeletePv(Tank item, int position) {
        if (item.getPv()>0) {
            item.setPv(item.getPv() - 1);
            if (item.isDestroyed()) {
                selectVictoire(item);
            }
            if (bataille.getEquipe1().isTankOfEquipe(item)) {
                adapterEquipe1.notifyDataSetChanged();
            } else {
                adapterEquipe2.notifyDataSetChanged();
            }
        }
        updateEquipeTitle();
    }

    @Override
    public void onClickVictoire(Tank item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.victoire);
        builder.setTitle("Victoires du tank " + item.getNom());
        builder.setInverseBackgroundForced(true);
        String result = "";
        for (TankVictoires victoire:item.victoires()) {
            // On n'affiche que les victoires de la bataille en cours
            if (victoire.getNomBataille().equals(bataille.getNom())) {
//            result = result + victoire.getTankDetruit().getNation().getLabel() + "\t"
//                    + victoire.getTankDetruit().getGenre().getLabel() + "\t"
//                    + victoire.getTankDetruit().getNom()  +  "\n";

                result = result + victoire.getTankDetruit().getNom() + "\n";
            }
        }
        builder.setMessage(result);
        builder.setNegativeButton(R.string.fermer, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void selectVictoire(Tank tank) {
        currentDestroy = tank;
        currentVictoire = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.victoire);
        builder.setTitle("Qui à détruit le tank " + tank.getNom() + " ?");

        final List<Tank> tankList;
        if (bataille.getEquipe1().isTankOfEquipe(tank)) {
            tankList = tanksEquipe2;
        } else {
            tankList = tanksEquipe1;
        }

        final String[] tks = new String[tankList.size()];
        for (int i = 0; i < tankList.size(); i++) {
            tks[i] = tankList.get(i).getNom();
        }
        builder.setSingleChoiceItems(tks, -1,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        String tankNom = tks[which];
                        for (Tank tk : tankList) {
                            if (tk.getNom().equals(tankNom)) {
                                currentVictoire = tk;
                                break;
                            }
                        }
                    }
                });


        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myInterface.getListener().onDialogCompleted(true, "VICTOIRE_OK");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.action_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myInterface.getListener().onDialogCompleted(false, "VICTOIRE_PERSONNE");
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void updateEquipeTitle() {
        if (bataille!=null) {
            viewTextEquipe1.setText(bataille.getEquipe1().getNom() + " (" + String.valueOf(getNbTanksAlive(tanksEquipe1)) + ")");
            viewTextEquipe2.setText(bataille.getEquipe2().getNom() + " (" + String.valueOf(getNbTanksAlive(tanksEquipe2)) + ")");
        }
    }

    private int getNbTanksAlive(List<Tank> list) {
        int result= 0;
        if (list!=null) {
            for (Tank t:list) {
                if (!t.isDestroyed()) {
                    result++;
                }
            }
        }
        return result;
    }
}

