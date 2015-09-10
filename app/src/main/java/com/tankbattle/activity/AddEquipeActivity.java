package com.tankbattle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.tankbattle.R;
import com.tankbattle.model.Equipe;


public class AddEquipeActivity extends Activity {

    private EditText editTextNom;
    private Equipe equipe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipe);

        editTextNom = (EditText)  findViewById(R.id.editTextNom);

        initView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_equipe, menu);
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
            case R.id.action_close_equipe:
                finish();
                Intent listActivity = new Intent(getApplicationContext(), ListEquipeActivity.class);
                startActivity(listActivity);
                finish();
                return true;
            case R.id.action_delete_equipe:
                if (onDelete()) {
                    listActivity = new Intent(getApplicationContext(), ListEquipeActivity.class);
                    startActivity(listActivity);
                    finish();
                    return true;
                }
        }
        return false;
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
        // TODO Popup e confirmation
        if (equipe != null) {
            equipe.delete();
            Toast.makeText(getBaseContext(), getString(R.string.equipe_delete), Toast.LENGTH_LONG).show();
        }
        return true;
    }

}
