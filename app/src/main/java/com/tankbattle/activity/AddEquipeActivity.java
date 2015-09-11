package com.tankbattle.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.tankbattle.R;
import com.tankbattle.model.Equipe;


public class AddEquipeActivity extends Activity implements MyDialogInterface.DialogReturn {

    private EditText editTextNom;
    private Equipe equipe = null;
    private MyDialogInterface myInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipe);

        editTextNom = (EditText)  findViewById(R.id.editTextNom);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        initView();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_equipe, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_delete_equipe);
        if (equipe!=null) {
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_equipe:
                if (onSave()) {
                    Intent listActivity = new Intent(getApplicationContext(), ListEquipeActivity.class);
                    startActivity(listActivity);
                    finish();
                    return true;
                }
                return false;
            case R.id.action_close_equipe:
                Intent listActivity = new Intent(getApplicationContext(), ListEquipeActivity.class);
                startActivity(listActivity);
                finish();
                return true;
            case R.id.action_delete_equipe:
                onDelete();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            long equipeId = bundle.getLong(Equipe.EQUIPE_ID);
            equipe = Equipe.load(Equipe.class, equipeId);
            if (equipe!=null) {
                editTextNom.setText(equipe.getNom());
            }
        }
    }

    private boolean onSave() {
        Editable edName = editTextNom.getText();
        if (edName==null || "".equals(edName.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.nom_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (equipe == null) {
                equipe = new Equipe();
            }
            equipe.setNom(edName.toString());

            equipe.save();
            Toast.makeText(getBaseContext(), getString(R.string.equipe_save), Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private boolean onDelete() {
        if (equipe != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(equipe.getNom());
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
        return true;
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if (answer && equipe!=null) {
            equipe.delete();
            Toast.makeText(getBaseContext(), getString(R.string.equipe_delete), Toast.LENGTH_LONG).show();
            Intent listActivity = new Intent(getApplicationContext(), ListEquipeActivity.class);
            startActivity(listActivity);
            finish();
        }
    }


}
