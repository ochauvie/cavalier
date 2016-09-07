package com.cavalier.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cavalier.R;
import com.cavalier.adapter.MontureListAdapter;
import com.cavalier.adapter.PersonneListAdapter;
import com.cavalier.listner.MontureListener;
import com.cavalier.listner.PersonneListener;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.cavalier.model.TypePersonne;
import com.cavalier.service.MontureService;
import com.cavalier.service.PersonneService;

import java.util.List;

public class ListMontureActivity extends ListActivity implements MontureListener{

    private ListView listView;
    private MontureListAdapter montureListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_list_monture);

        List<Monture> montureList = MontureService.getAll();
        listView = getListView();

        // Creation et initialisation de l'Adapter
        montureListAdapter = new MontureListAdapter(this, montureList);
        montureListAdapter.addListener(this);

        //Initialisation de la liste avec les donnees
        setListAdapter(montureListAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cavalier_list_monture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_monture:
                Intent myIntent = new Intent(getApplicationContext(), AddMontureActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
                return true;
            case R.id.action_close_list:
                finish();
                return true;
        }

        return false;
    }

    @Override
    public void onClick(Monture monture, int position) {
        Intent myIntent = new Intent(getApplicationContext(), AddMontureActivity.class);
        myIntent.putExtra(Monture.ID_MONTURE, monture.getId());
        startActivityForResult(myIntent, 0);
        finish();
    }


    @Override
    public void onBackPressed() {
        // Nothings
    }
}
