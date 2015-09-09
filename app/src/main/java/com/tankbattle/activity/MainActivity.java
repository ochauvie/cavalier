package com.tankbattle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.tankbattle.R;
import com.tankbattle.data.InitDataBase;

// DOC:
// http://www.vogella.com/tutorials
// PHOTO: http://blog.ace-dev.fr/2011/07/14/tutoriel-android-partie-13-lappareil-photo/
// SCREEN: http://developer.android.com/guide/practices/screens_support.html
//          http://developer.android.com/guide/topics/media/camera.html
public class MainActivity extends Activity {

    private Button but1, but2, but3, but4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ouvre la BD
        ActiveAndroid.initialize(this);

        setContentView(R.layout.activity_main);

        but1 = (Button) findViewById(R.id.button1);
        but1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InitDataBase.initTankList(getApplicationContext());
                Toast.makeText(getBaseContext(), getString(R.string.db_tank_initialized), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getBaseContext(), "En travaux", Toast.LENGTH_LONG).show();
            }
        });

        but4 = (Button) findViewById(R.id.button4);
        but4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "En travaux", Toast.LENGTH_LONG).show();
            }
        });


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
                Toast.makeText(getBaseContext(), getString(R.string.db_tank_initialized), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_show_list_tank:
                startActivityForResult(new Intent(getApplicationContext(), ListTankActivity.class), 0);

                return true;
            case R.id.action_show_add_tank:
                startActivityForResult(new Intent(getApplicationContext(), AddTankActivity.class), 0);
                return true;
        }

        return true;
    }
}
