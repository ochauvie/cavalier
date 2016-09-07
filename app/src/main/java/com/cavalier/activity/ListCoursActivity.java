package com.cavalier.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cavalier.R;
import com.cavalier.adapter.CoursListAdapter;
import com.cavalier.adapter.MontureListAdapter;
import com.cavalier.listner.CoursListener;
import com.cavalier.listner.MontureListener;
import com.cavalier.model.Cours;
import com.cavalier.model.Monture;
import com.cavalier.service.CoursService;
import com.cavalier.service.MontureService;

import java.util.List;

public class ListCoursActivity extends ListActivity implements CoursListener{

    private ListView listView;
    private CoursListAdapter coursListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_list_cours);

        List<Cours> coursList = CoursService.getAll();
        listView = getListView();

        // Creation et initialisation de l'Adapter
        coursListAdapter = new CoursListAdapter(this, coursList);
        coursListAdapter.addListener(this);

        //Initialisation de la liste avec les donnees
        setListAdapter(coursListAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_list_cours, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close_list:
                finish();
                return true;
        }

        return false;
    }

    @Override
    public void onClick(Cours monture, int position) {
        // Nothings
    }


    @Override
    public void onBackPressed() {
        // Nothings
    }
}
