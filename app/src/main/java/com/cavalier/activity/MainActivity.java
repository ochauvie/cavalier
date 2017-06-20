package com.cavalier.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.cavalier.adapter.IDataSpinnerAdapter;
import com.cavalier.data.InitDataBase;
import com.cavalier.model.Cours;
import com.cavalier.model.EvenementMonture;
import com.cavalier.model.IRefData;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.cavalier.model.PlanningEvent;
import com.cavalier.model.PlanningNote;
import com.cavalier.model.Ration;
import com.cavalier.model.RefData;
import com.cavalier.model.TypePersonne;
import com.cavalier.R;

import java.util.ArrayList;


public class MainActivity extends Activity implements MyDialogInterface.DialogReturn, ListView.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ListView myDrawer;
    private MyDialogInterface myInterface;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ouvre la BD
        Configuration.Builder config = new Configuration.Builder(this);
        config.addModelClasses(Personne.class, Monture.class, Cours.class, EvenementMonture.class, Ration.class, PlanningEvent.class, PlanningNote.class);
        ActiveAndroid.initialize(config.create());
        updateDatabase();

        setContentView(R.layout.activity_cavalier_main);

        // load the animation
        Animation  blinkAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        myDrawer = (ListView) findViewById(R.id.my_drawer);
        myDrawer.setAdapter(new IDataSpinnerAdapter(this, getListDrawer(), R.layout.custom_spinner));
        myDrawer.setOnItemClickListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ActionBar actionBar = getActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(getString(R.string.drawer_open));
                }
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ActionBar actionBar = getActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(getString(R.string.drawer_close));
                }
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }


        // Initialisation BD
        Button but0 = (Button) findViewById(R.id.button0);
        but0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onInitDb();

            }
        });
        but0.setVisibility(View.INVISIBLE);

        // Moniteurs
        Button but1 = (Button) findViewById(R.id.button1);
        but1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ListPersonneActivity.class);
                myIntent.putExtra(Personne.TYPE_PERSONNE, TypePersonne.MONITEUR.name());
                startActivityForResult(myIntent, 0);
            }
        });

        // Cavaliers
        Button but2 = (Button) findViewById(R.id.button2);
        but2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ListPersonneActivity.class);
                myIntent.putExtra(Personne.TYPE_PERSONNE, TypePersonne.CAVALIER.name());
                startActivityForResult(myIntent, 0);
            }
        });

        // Montures
        Button but3 = (Button) findViewById(R.id.button3);
        but3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), ListMontureActivity.class), 0);
            }
        });

        // Nouveau cours
        Button but4 = (Button) findViewById(R.id.button4);
        but4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), AddCoursActivity.class), 0);
            }
        });
        //but4.startAnimation(blinkAnim);

        // Mes cours
        Button but5 = (Button) findViewById(R.id.button5);
        but5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), ListCoursActivity.class), 0);
            }
        });

        // Planning
        Button but6 = (Button) findViewById(R.id.button6);
        but6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), PlanningActivity.class), 0);
            }
        });
    }

    private void onInitDb() {
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
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if (answer) {
            if ("INIT_BD".equals(type)) {
                InitDataBase.initCours();
                InitDataBase.initPersonnes();
                InitDataBase.initMontures();
                Toast.makeText(getBaseContext(), getString(R.string.db_initialized), Toast.LENGTH_LONG).show();
            }
        }
    }


    private void openHippologie() {
        Intent myIntent = new Intent(getApplicationContext(), HttpActivity.class);
        myIntent.putExtra("URL", "http://www.hippologie.fr/");
        startActivity(myIntent);
    }

//    private void openKathEquitation() {
//        Intent myIntent = new Intent(getApplicationContext(), HttpActivity.class);
//        myIntent.putExtra("URL", "https://www.facebook.com/people/Kath%C3%A9quitation-Mary/100010372802263");
//        startActivity(myIntent);
//    }

    private void updateDatabase() {}


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (id == 0) {
            openHippologie();
        } else if (id == 1) {
            Intent exportActivity = new Intent(MainActivity.this, ExportActivity.class);
            startActivityForResult(exportActivity, 0);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private ArrayList<IRefData> getListDrawer() {
        ArrayList<IRefData> listDrawer = new ArrayList<>();
        RefData item = new RefData(R.string.menu_hippologie, R.drawable.internet);
        listDrawer.add(item);

        item = new RefData(R.string.menu_export_db, R.drawable.backup);
        listDrawer.add(item);

        return listDrawer;
    }


}


