package com.cavalier.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.service.ExportService;

public class ExportActivity extends Activity implements MyDialogInterface.DialogReturn {

    private MyDialogInterface myInterface;
    private ImageButton btExport;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_export);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        btExport = (ImageButton) findViewById(R.id.btExport);
        btExport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backupDb();
            }
        });
    }

    private void backupDb() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.backup);
        builder.setTitle(R.string.menu_export_db);
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myInterface.getListener().onDialogCompleted(true, null);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myInterface.getListener().onDialogCompleted(false, null);
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if (answer) {
            CheckBox expMonture = (CheckBox) findViewById(R.id.exp_monture);
            CheckBox expPersonne = (CheckBox) findViewById(R.id.exp_personne);
            CheckBox expCours = (CheckBox) findViewById(R.id.exp_cours);
            CheckBox expEvenements = (CheckBox) findViewById(R.id.exp_evenement);

            if (expMonture.isChecked() || expPersonne.isChecked() || expCours.isChecked() || expEvenements.isChecked()) {
                ExportService exportService = new ExportService(this,
                        expMonture.isChecked(),
                        expPersonne.isChecked(),
                        expCours.isChecked(),
                        expEvenements.isChecked());

                try {
                    String filePath = Environment.getExternalStorageDirectory().getPath() + "/";
                    exportService.doBackup(filePath);
                    Toast.makeText(getBaseContext(),
                            "Done writing SD " + filePath,
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cavalier_export, menu);
        return true;
    }

    /**
     * Call when menu item is selected
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        // Nothings
    }
}