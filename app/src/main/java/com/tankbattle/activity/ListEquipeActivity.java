package com.tankbattle.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tankbattle.R;
import com.tankbattle.adapter.EquipeListAdapter;
import com.tankbattle.listner.EquipeListener;
import com.tankbattle.model.Equipe;
import com.tankbattle.service.EquipeService;

import java.util.List;

public class ListEquipeActivity extends ListActivity implements EquipeListener {

    private ListView listView;
    private EquipeListAdapter equipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_equipe);

        List<Equipe> equipeList = EquipeService.getAllEquipes();

        listView = getListView();

        // Creation et initialisation de l'Adapter
        equipeListAdapter = new EquipeListAdapter(this, equipeList);
        equipeListAdapter.addListener(this);

        //Initialisation de la liste avec les donnees
        setListAdapter(equipeListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_equipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_equipe:
                Intent myIntent = new Intent(getApplicationContext(), AddEquipeActivity.class);
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
    public void onClickEquipe(Equipe equipe, int position) {
        Intent myIntent = new Intent(getApplicationContext(), AddEquipeActivity.class);
        myIntent.putExtra(Equipe.EQUIPE_ID, equipe.getId());
        startActivityForResult(myIntent, 0);
        finish();

    }
}
