package com.tankbattle.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.tankbattle.R;
import com.tankbattle.adapter.EquipeListAdapter;
import com.tankbattle.service.EquipeService;


/**
 * Created by Olivier on 13/09/15.
 */
public class CreateBatailleActivity extends Activity {

    private TextView textDateCreation, textDateFin;
    private Button btStart;
    private Spinner spEquipe1, spEquipe2;

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
        spinner.setAdapter(new EquipeListAdapter(this, EquipeService.getAllEquipes()));
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            // TODO
        }
    }

}
