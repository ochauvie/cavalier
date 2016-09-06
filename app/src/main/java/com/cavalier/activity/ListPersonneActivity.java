package com.cavalier.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cavalier.adapter.PersonneListAdapter;
import com.cavalier.listner.PersonneListener;
import com.cavalier.model.Personne;
import com.cavalier.model.TypePersonne;
import com.cavalier.service.PersonneService;
import com.tankbattle.R;
import com.tankbattle.activity.AddTankActivity;
import com.tankbattle.adapter.TankListAdapter;
import com.tankbattle.listner.TankListener;
import com.tankbattle.model.Tank;
import com.tankbattle.model.TankVictoires;
import com.tankbattle.service.TankService;
import com.tankbattle.tools.SpinnerTool;

import java.util.List;

public class ListPersonneActivity extends ListActivity implements PersonneListener{

    private ListView listView;
    private PersonneListAdapter personneListAdapter;
    private TypePersonne typePersonne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tank);

        initView();

        List<Personne> personneList = PersonneService.findByType(typePersonne);

        listView = getListView();

        // Creation et initialisation de l'Adapter
        personneListAdapter = new PersonneListAdapter(this, personneList);
        personneListAdapter.addListener(this);

        //Initialisation de la liste avec les donnees
        setListAdapter(personneListAdapter);

    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            this.typePersonne = TypePersonne.valueOf(bundle.getString(Personne.TYPE_PERSONNE));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cavalier_list_personne, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_personne:
                Intent myIntent = new Intent(getApplicationContext(), AddPersonneActivity.class);
                myIntent.putExtra(Personne.TYPE_PERSONNE, typePersonne.name());
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
    public void onClick(Personne personne, int position) {
        Intent myIntent = new Intent(getApplicationContext(), AddPersonneActivity.class);
        myIntent.putExtra(Personne.ID_PERSONNE, personne.getId());
        startActivityForResult(myIntent, 0);
        finish();
    }


    @Override
    public void onBackPressed() {
        // Nothings
    }
}
