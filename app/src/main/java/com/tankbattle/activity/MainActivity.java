package com.tankbattle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.tankbattle.R;
import com.tankbattle.data.InitDataBase;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActiveAndroid.initialize(this);

        setContentView(R.layout.activity_main);


        // PHOTO: http://blog.ace-dev.fr/2011/07/14/tutoriel-android-partie-13-lappareil-photo/
        // SCREEN: http://developer.android.com/guide/practices/screens_support.html
        //          http://developer.android.com/guide/topics/media/camera.html
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_init_data:
                InitDataBase.initTankList();
                Toast.makeText(getBaseContext(), getString(R.string.db_tank_initialized), Toast.LENGTH_LONG).show();
                return true;
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
