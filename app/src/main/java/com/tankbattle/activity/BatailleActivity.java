package com.tankbattle.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tankbattle.R;
import com.tankbattle.adapter.TankInEquipeListAdapter;
import com.tankbattle.listner.MyDragListener;
import com.tankbattle.model.Bataille;
import com.tankbattle.model.Equipe;
import com.tankbattle.model.Tank;
import com.tankbattle.service.BatailleService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olivier on 14/09/15.
 */
public class BatailleActivity extends Activity {

    private ListView listViewEquipe1, listViewEquipe2;
    private TextView textViewNom, textViewDate;
    private Bataille bataille;
    private List<Tank> tanksEquipe1 = new ArrayList<Tank>();
    private List<Tank> tanksEquipe2 = new ArrayList<Tank>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bataille);

        textViewNom = (TextView)  findViewById(R.id.textViewNom);
        textViewDate = (TextView)  findViewById(R.id.textViewDate);
        listViewEquipe1 = (ListView) findViewById(R.id.listViewEquipe1);
        listViewEquipe2 = (ListView) findViewById(R.id.listViewEquipe2);

        initView();

        TankInEquipeListAdapter adapterEquipe1 = new TankInEquipeListAdapter(this, tanksEquipe1, true, null);
        TankInEquipeListAdapter adapterEquipe2 = new TankInEquipeListAdapter(this, tanksEquipe2, true, null);
        listViewEquipe1.setAdapter(adapterEquipe1);
        listViewEquipe2.setAdapter(adapterEquipe2);


    }

    private void initView() {
        bataille = BatailleService.getCurrentBataille();
        if (bataille!=null) {



        } else {
            Toast.makeText(getBaseContext(), getString(R.string.no_current_bataille), Toast.LENGTH_LONG).show();
            finish();
        }


    }




}

