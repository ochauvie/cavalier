package com.cavalier.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.adapter.CarteItemListAdapter;
import com.cavalier.adapter.PersonneListAdapter;
import com.cavalier.listner.CarteListener;
import com.cavalier.model.Carte;
import com.cavalier.model.CarteItem;
import com.cavalier.model.Personne;
import com.cavalier.service.PersonneService;
import com.cavalier.tools.SpinnerTool;
import com.cavalier.tools.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UpdateCarteActivity extends Activity implements CarteListener {

    private Spinner spinnerPersonne;
    private TextView txNom, txDateOuverture, txDateCloture;
    private Carte carte;
    private ListView listView;
    private List<CarteItem> carteItemList = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private CarteItemListAdapter carteItemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_update_carte);

        spinnerPersonne = (Spinner) findViewById(R.id.spPersonne);
        loadSpinnerPersonne(spinnerPersonne);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            long carteId = bundle.getLong(Carte.ID_CARTE);
            carte = Carte.load(Carte.class, carteId);
            if (carte != null) {
                SpinnerTool.SelectSpinnerItemByValue(spinnerPersonne, carte.getCavalier());
                spinnerPersonne.setEnabled(false);
            }
        }

        txNom = (TextView)  findViewById(R.id.txNom);
        txNom.setText(carte.getNom());
        txDateOuverture = (TextView)  findViewById(R.id.txDateOuverture);
        txDateOuverture.setText("Date d'ouverture: " + sdf.format(carte.getDateOuverture()));
        txDateCloture = (TextView)  findViewById(R.id.txDateCloture);
        if (carte.getDateCloture() != null) {
            txDateCloture.setText("Date clôture: " + sdf.format(carte.getDateCloture()));
        } else {
            txDateCloture.setText("");
        }


        GridView gridView = (GridView)findViewById(R.id.gridview);



        //listView = getListView();
        for (int i=0; i<carte.getCapacite(); i++) {
            carteItemList.add(new CarteItem(i, i<carte.getPointRestant() ? false : true));
        }
        carteItemListAdapter = new CarteItemListAdapter(this, carteItemList);
        gridView.setAdapter(carteItemListAdapter);
        carteItemListAdapter.addListener(this);

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_add_carte, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemS = menu.findItem(R.id.action_add);
        Utils.enableItem(itemS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                if (onSave()) {
                    Intent myIntent = new Intent(getApplicationContext(), AddPersonneActivity.class);
                    myIntent.putExtra(Personne.ID_PERSONNE, carte.getCavalier().getId());
                    startActivity(myIntent);
                    finish();
                }
                return true;
            case R.id.action_close:
                Intent intent = new Intent(getApplicationContext(), AddPersonneActivity.class);
                intent.putExtra(Personne.ID_PERSONNE, carte.getCavalier().getId());
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    }

    private void loadSpinnerPersonne(Spinner spinner) {
        spinner.setAdapter(new PersonneListAdapter(this, PersonneService.getAll()));
    }

    private boolean onSave() {
        int ptRestant = carte.getCapacite();
        for (CarteItem carteItem: carteItemList) {
            if (carteItem.isCheck()) {
                ptRestant--;
            }
        }
        if (ptRestant==0) {
            carte.setDateCloture(new Date());
        } else {
            carte.setDateCloture(null);
        }
        carte.setPointRestant(ptRestant);
        carte.save();
        Toast.makeText(getBaseContext(), getString(R.string.carte_save), Toast.LENGTH_LONG).show();
       return true;
    }

    @Override
    public void onBackPressed() {
        // Nothings
    }

    @Override
    public void onUpdateCarte(Carte item, int position) {
        // Nothings
    }

    @Override
    public void onDeleteCarte(Carte carte, int position) {
        // Nothings
    }

    @Override
    public void onUpdateCarteItem(CarteItem item, int position) {
        carteItemList.get(position).setCheck(!item.isCheck());
    }
}

