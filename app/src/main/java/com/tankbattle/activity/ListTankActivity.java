package com.tankbattle.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tankbattle.R;
import com.tankbattle.adapter.TankListAdapter;
import com.tankbattle.listner.TankListener;
import com.tankbattle.model.Tank;
import com.tankbattle.service.TankService;

import java.util.List;

public class ListTankActivity extends ListActivity implements TankListener{

    private ListView listView;
    private TankListAdapter tankListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tank);

        List<Tank> tankList = TankService.getAllTanks();

        listView = getListView();

        // Creation et initialisation de l'Adapter
        tankListAdapter = new TankListAdapter(this, tankList);
        tankListAdapter.addListener(this);

        //Initialisation de la liste avec les donnees
        setListAdapter(tankListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_tank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_tank:
                Intent myIntent = new Intent(getApplicationContext(), AddTankActivity.class);
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
    public void onClickTank(Tank tank, int position) {
        Intent myIntent = new Intent(getApplicationContext(), AddTankActivity.class);
        myIntent.putExtra(Tank.TANK_ID, tank.getId());
        startActivityForResult(myIntent, 0);
        finish();

    }
}
