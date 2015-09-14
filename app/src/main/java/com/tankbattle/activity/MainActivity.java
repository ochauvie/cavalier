package com.tankbattle.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.tankbattle.R;
import com.tankbattle.data.InitDataBase;
import com.tankbattle.model.Bataille;
import com.tankbattle.service.BatailleService;

// DOC:
// http://www.vogella.com/tutorials
// PHOTO: http://blog.ace-dev.fr/2011/07/14/tutoriel-android-partie-13-lappareil-photo/
// SCREEN: http://developer.android.com/guide/practices/screens_support.html
//          http://developer.android.com/guide/topics/media/camera.html
public class MainActivity extends Activity implements MyDialogInterface.DialogReturn {

    private Button but1, but2, but3, but4, but5;
    private MyDialogInterface myInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ouvre la BD
        ActiveAndroid.initialize(this);

        setContentView(R.layout.activity_main);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        but1 = (Button) findViewById(R.id.button1);
        but1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onInitDb();

            }
        });

        but2 = (Button) findViewById(R.id.button2);
        but2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), ListTankActivity.class), 0);
            }
        });

        but3 = (Button) findViewById(R.id.button3);
        but3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), ListEquipeActivity.class), 0);
            }
        });

        but4 = (Button) findViewById(R.id.button4);
        but4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), CreateBatailleActivity.class), 0);
            }
        });

        but5 = (Button) findViewById(R.id.button5);
        but5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), BatailleActivity.class), 0);
            }
        });

        setButton();
    }

    private void setButton() {
        // Si pas de bataille en cours, affichage de "Nouvelle bataille", sinon "Continuer la bataille"
        Bataille currentBataille = BatailleService.getCurrentBataille();
        if (currentBataille!=null) {
            but4.setVisibility(View.GONE);
            but5.setVisibility(View.VISIBLE);
        } else {
            but4.setVisibility(View.VISIBLE);
            but5.setVisibility(View.GONE);
        }
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
                InitDataBase.initTankList(getApplicationContext());
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
        builder.setIcon(R.drawable.delete);
        builder.setTitle("Voulez vous vraiement initialiser la base de données (les équipes et les parties seront perdues)");
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
        return true;
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if (answer) {
            InitDataBase.initBataille();
            InitDataBase.initEquipe();
            InitDataBase.initTankList(getApplicationContext());
            setButton();
            Toast.makeText(getBaseContext(), getString(R.string.db_initialized), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        setButton();
        super.onResume();
    }
}


