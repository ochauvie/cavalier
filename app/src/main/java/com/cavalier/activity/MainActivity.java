package com.cavalier.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.cavalier.data.InitDataBase;
import com.cavalier.model.Personne;
import com.cavalier.model.TypePersonne;
import com.cavalier.R;
import com.tankbattle.activity.AddTankActivity;
import com.tankbattle.activity.BatailleActivity;
import com.tankbattle.activity.CreateBatailleActivity;
import com.tankbattle.activity.ListEquipeActivity;
import com.tankbattle.activity.ListTankActivity;


public class MainActivity extends Activity implements MyDialogInterface.DialogReturn {

    private Button but0, but1, but2, but3, but4, but5;
    private MyDialogInterface myInterface;
    private Animation blinkAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ouvre la BD
        ActiveAndroid.initialize(this);

        setContentView(R.layout.activity_cavalier_main);

        // load the animation
        blinkAnim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        // Initialisation BD
        but0 = (Button) findViewById(R.id.button0);
        but0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onInitDb();

            }
        });

        // Moniteurs
        but1 = (Button) findViewById(R.id.button1);
        but1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ListPersonneActivity.class);
                myIntent.putExtra(Personne.TYPE_PERSONNE, TypePersonne.MONITEUR.name());
                startActivityForResult(myIntent, 0);
            }
        });

        // Cavaliers
        but2 = (Button) findViewById(R.id.button2);
        but2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ListPersonneActivity.class);
                myIntent.putExtra(Personne.TYPE_PERSONNE, TypePersonne.CAVALIER.name());
                startActivityForResult(myIntent, 0);
            }
        });

        // Montures
        but3 = (Button) findViewById(R.id.button3);
        but3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), ListMontureActivity.class), 0);
            }
        });

        // Nouveau cours
        but4 = (Button) findViewById(R.id.button4);
        but4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), ListMontureActivity.class), 0);
            }
        });
        but4.startAnimation(blinkAnim);

        // Mes cours
        but5 = (Button) findViewById(R.id.button5);
        but5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), ListMontureActivity.class), 0);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_init_data:
                //InitDataBase.initTankList(getApplicationContext());
                Toast.makeText(getBaseContext(), getString(R.string.db_initialized), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_show_list_tank:
                startActivityForResult(new Intent(getApplicationContext(), ListTankActivity.class), 0);

                return true;
            case R.id.action_show_add_tank:
                startActivityForResult(new Intent(getApplicationContext(), AddTankActivity.class), 0);
                return true;
        }
        return false;
    }


    private boolean onInitDb() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.smallsave);
        builder.setTitle("Initialisation de l'application");
        builder.setMessage("Voulez vous vraiement initialiser la base de données ?");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myInterface.getListener().onDialogCompleted(true, "INIT_BD");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myInterface.getListener().onDialogCompleted(false, "INIT_BD");
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        return true;
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if (answer) {
            if ("INIT_BD".equals(type)) {
                InitDataBase.initPersonnes();
                InitDataBase.initMontures();
                Toast.makeText(getBaseContext(), getString(R.string.db_initialized), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}


