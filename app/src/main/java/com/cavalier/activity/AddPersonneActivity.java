package com.cavalier.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cavalier.listner.PersonneListener;
import com.cavalier.model.Personne;
import com.cavalier.model.Sexe;
import com.cavalier.model.TypePersonne;
import com.cavalier.service.CoursService;
import com.cavalier.R;
import com.cavalier.adapter.IDataSpinnerAdapter;
import com.cavalier.model.IRefData;
import com.cavalier.tools.SpinnerTool;
import com.cavalier.tools.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class AddPersonneActivity extends Activity implements MyDialogInterface.DialogReturn, PersonneListener {

    private Spinner spinnerSexe;
    private EditText editTextNom, editTextPrenom;
    private TextView textViewType;
    private MyDialogInterface myInterface;
    private Personne personne = null;
    private TypePersonne typePersonne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_add_personne);

        spinnerSexe = (Spinner) findViewById(R.id.spSexe);
        loadSpinnerSexe();

        editTextNom = (EditText)  findViewById(R.id.editTextNom);
        editTextPrenom = (EditText)  findViewById(R.id.editTextPrenom);
        textViewType = (TextView) findViewById(R.id.textViewType);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        initView();
        if (TypePersonne.CAVALIER == typePersonne) {
            getActionBar().setIcon(R.drawable.bombe);
            setTitle("Cavalier");
        } else {
            getActionBar().setIcon(R.drawable.coach);
            setTitle("Moniteur");
        }

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_add_personne, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemD = menu.findItem(R.id.action_delete_personne);
        MenuItem itemS = menu.findItem(R.id.action_add_personne);
        if (personne != null) {
            if (CoursService.isPersonneInCours(personne)) {
                Utils.disableItem(itemD);
                Utils.enableItem(itemS);
            } else {
                Utils.enableItem(itemD);
                Utils.enableItem(itemS);
            }
        } else {
            Utils.disableItem(itemD);
            Utils.enableItem(itemS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_personne:
                if (onSave()) {
                    Intent listPersonneActivity = new Intent(getApplicationContext(), ListPersonneActivity.class);
                    listPersonneActivity.putExtra(Personne.TYPE_PERSONNE, typePersonne.name());
                    startActivity(listPersonneActivity);
                    finish();
                    return true;
                }
                return false;
            case R.id.action_close_personne:
                Intent listPersonneActivity = new Intent(getApplicationContext(), ListPersonneActivity.class);
                listPersonneActivity.putExtra(Personne.TYPE_PERSONNE, typePersonne.name());
                startActivity(listPersonneActivity);
                finish();
                return true;
            case R.id.action_delete_personne:
                onDelete();
                return false;
        }
        return false;
    }

    private void loadSpinnerSexe() {
        ArrayList<IRefData> list = new ArrayList<IRefData>();
        Collections.addAll(list, Sexe.values());
        spinnerSexe.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.light_custom_spinner));
    }



    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            //tank = (Tank)bundle.getSerializable(Tank.class.getName());
            long personneId = bundle.getLong(Personne.ID_PERSONNE);
            personne = Personne.load(Personne.class, personneId);
            if (personne!=null) {
                editTextNom.setText(personne.getNom());
                editTextPrenom.setText(personne.getPrenom());
                textViewType.setText(personne.getType().getLabel());
                SpinnerTool.SelectSpinnerItemByValue(spinnerSexe, personne.getSexe().name());
                typePersonne = personne.getType();

            } else {
                typePersonne = TypePersonne.valueOf(bundle.getString(Personne.TYPE_PERSONNE));
                textViewType.setText(typePersonne.getLabel());
            }
        }
    }

    private boolean onSave() {
        Editable edName = editTextNom.getText();
        Editable edPrenom = editTextPrenom.getText();
        if (edName==null || "".equals(edName.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.nom_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else if (edPrenom==null || "".equals(edPrenom.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.prenom_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (personne == null) {
                personne = new Personne();
                personne.setType(typePersonne);
            }
            personne.setSexe((Sexe) spinnerSexe.getSelectedItem());
            personne.setNom(edName.toString());
            personne.setPrenom(edPrenom.toString());

            personne.save();
            Toast.makeText(getBaseContext(), getString(R.string.personne_save), Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private boolean onDelete() {
        if (personne != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(personne.getNom() + " - " + personne.getPrenom());
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
        if (answer && personne!=null) {
            personne.delete();
            Toast.makeText(getBaseContext(), getString(R.string.personne_delete), Toast.LENGTH_LONG).show();
            Intent listPersonneActivity = new Intent(getApplicationContext(), ListPersonneActivity.class);
            listPersonneActivity.putExtra(Personne.TYPE_PERSONNE, typePersonne.name());
            startActivity(listPersonneActivity);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // Nothings
    }

    @Override
    public void onClick(Personne  item, int position) {
        // Nothings
    }



}
