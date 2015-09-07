package com.tankbattle.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.ActiveAndroid;
import com.tankbattle.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_show_list_tank:
                startActivityForResult(new Intent(getApplicationContext(), ListTankActivity.class), 0);
                finish();
                return true;
            case R.id.action_show_add_tank:
                startActivityForResult(new Intent(getApplicationContext(), AddTankActivity.class), 0);
                finish();
                return true;
        }

        return true;
    }
}
