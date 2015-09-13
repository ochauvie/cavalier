package com.tankbattle.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.tankbattle.R;
import com.tankbattle.adapter.TankInEquipeListAdapter;
import com.tankbattle.listner.MyDragListener;
import com.tankbattle.model.Equipe;
import com.tankbattle.model.EquipeTank;
import com.tankbattle.model.Nation;
import com.tankbattle.model.Tank;
import com.tankbattle.service.EquipeService;
import com.tankbattle.service.TankService;

import java.util.ArrayList;
import java.util.List;


public class AddEquipeActivity extends Activity implements MyDialogInterface.DialogReturn {

    private ListView listViewEquipe, listViewHangar;
    private EditText editTextNom;
    private Equipe equipe = null;
    private MyDialogInterface myInterface;

    List<Tank> tanksInEquipeList = new ArrayList<Tank>();
    List<Tank> tanskInHangarList = TankService.getAllTanks();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipe);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        editTextNom = (EditText)  findViewById(R.id.editTextNom);
        listViewEquipe = (ListView) findViewById(R.id.listViewEquipe);
        listViewHangar = (ListView) findViewById(R.id.listViewHangar);

        initView();

        TankInEquipeListAdapter adapterEquipe= new TankInEquipeListAdapter(this, tanksInEquipeList, false, TankInEquipeListAdapter.ORIGINE_EQUIPE);
        TankInEquipeListAdapter adapterHangar= new TankInEquipeListAdapter(this, tanskInHangarList, false, TankInEquipeListAdapter.ORIGINE_HANGAR);
        listViewEquipe.setAdapter(adapterEquipe);
        listViewHangar.setAdapter(adapterHangar);

        listViewEquipe.setOnDragListener(new MyDragListener(adapterEquipe, adapterHangar));
        listViewHangar.setOnDragListener(new MyDragListener(adapterHangar, adapterEquipe));

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_equipe, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_delete_equipe);
        if (equipe!=null) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_equipe:
                if (onSave()) {
                    Intent listActivity = new Intent(getApplicationContext(), ListEquipeActivity.class);
                    startActivity(listActivity);
                    finish();
                    return true;
                }
                return false;
            case R.id.action_close_equipe:
                Intent listActivity = new Intent(getApplicationContext(), ListEquipeActivity.class);
                startActivity(listActivity);
                finish();
                return true;
            case R.id.action_delete_equipe:
                onDelete();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            long equipeId = bundle.getLong(Equipe.EQUIPE_ID);
            equipe = Equipe.load(Equipe.class, equipeId);
            if (equipe!=null) {
                editTextNom.setText(equipe.getNom());
                List<EquipeTank> tanks = equipe.tanks();
                if (tanks!=null) {
                    for (EquipeTank equipeTank:tanks) {
                        Tank tank = equipeTank.getTank();
                        tanksInEquipeList.add(tank);

                        // Supprime du hangar
                        for (Tank hTank:tanskInHangarList) {
                            if (hTank.getId() == tank.getId()) {
                                tanskInHangarList.remove(hTank);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean onSave() {
        Editable edName = editTextNom.getText();
        if (edName==null || "".equals(edName.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.nom_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else {
            try {
                ActiveAndroid.beginTransaction();
                if (equipe == null) {
                    equipe = new Equipe();
                } else {
                    for (EquipeTank equipeTank : equipe.tanks()) {
                        equipeTank.delete();
                    }
                }
                equipe.setNom(edName.toString());
                equipe.save();
                for (Tank tank:tanksInEquipeList) {
                    EquipeTank equipeTank = new EquipeTank(equipe, tank);
                    equipeTank.save();
                }
                ActiveAndroid.setTransactionSuccessful();
                Toast.makeText(getBaseContext(), getString(R.string.equipe_save), Toast.LENGTH_LONG).show();
            }
            finally {
                ActiveAndroid.endTransaction();
            }
        }
        return true;
    }

    private boolean onDelete() {
        if (equipe != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(equipe.getNom());
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(true, null);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(false, null);
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return true;
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if (answer && equipe!=null) {
            equipe.delete();
            Toast.makeText(getBaseContext(), getString(R.string.equipe_delete), Toast.LENGTH_LONG).show();
            Intent listActivity = new Intent(getApplicationContext(), ListEquipeActivity.class);
            startActivity(listActivity);
            finish();
        }
    }
}
