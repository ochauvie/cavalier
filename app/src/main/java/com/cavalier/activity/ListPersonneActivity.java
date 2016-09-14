package com.cavalier.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.cavalier.adapter.PersonneListAdapter;
import com.cavalier.listner.PersonneListener;
import com.cavalier.model.Personne;
import com.cavalier.model.TypePersonne;
import com.cavalier.service.ImportService;
import com.cavalier.service.PersonneService;
import com.cavalier.R;
import com.cavalier.tools.SimpleFileDialog;

import java.io.File;
import java.util.List;

public class ListPersonneActivity extends ListActivity implements PersonneListener{

    private ListView listView;
    private PersonneListAdapter personneListAdapter;
    private TypePersonne typePersonne;
    private List<Personne> personneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_list_personne);

        initView();
        personneList = PersonneService.findByType(typePersonne);

        listView = getListView();

        // Creation et initialisation de l'Adapter
        personneListAdapter = new PersonneListAdapter(this, personneList);
        personneListAdapter.addListener(this);

        //Initialisation de la liste avec les donnees
        setListAdapter(personneListAdapter);

    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            this.typePersonne = TypePersonne.valueOf(bundle.getString(Personne.TYPE_PERSONNE));
            if (TypePersonne.CAVALIER.equals(this.typePersonne)) {
                this.setTitle(R.string.title_activity_list_cavalier);
            } else {
                this.setTitle(R.string.title_activity_list_moniteur);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cavalier_list_personne, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_personne:
                Intent myIntent = new Intent(getApplicationContext(), AddPersonneActivity.class);
                myIntent.putExtra(Personne.TYPE_PERSONNE, typePersonne.name());
                startActivityForResult(myIntent, 0);
                finish();
                return true;
            case R.id.action_close_list:
                finish();
                return true;
            case R.id.action_import:
                importPersonne();
                return true;
        }

        return false;
    }

    @Override
    public void onClick(Personne personne, int position) {
        Intent myIntent = new Intent(getApplicationContext(), AddPersonneActivity.class);
        myIntent.putExtra(Personne.ID_PERSONNE, personne.getId());
        startActivityForResult(myIntent, 0);
        finish();
    }


    @Override
    public void onBackPressed() {
        // Nothings
    }

    private void importPersonne() {
        SimpleFileDialog FileOpenDialog = new SimpleFileDialog(this, "FileOpen",
                new SimpleFileDialog.SimpleFileDialogListener() {
                    @Override public void onChosenDir(String chosenDir) {
                        // The code in this function will be executed when the dialog OK button is pushed
                        File file = new File(chosenDir);
                        // Initiate the upload
                        ImportService importService = new ImportService(ListPersonneActivity.this);
                        String result = importService.importPersonne(file);
                        if (result!=null) {
                            Toast.makeText(ListPersonneActivity.this, result, Toast.LENGTH_LONG).show();
                        } else {
                            // Update the list
                            refreshList();
                            Toast.makeText(ListPersonneActivity.this, getString(R.string.menu_import_ok), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        FileOpenDialog.Default_File_Name = "";
        FileOpenDialog.chooseFile_or_Dir();
    }

    private void refreshList() {
        if (personneList!=null) {
            for (int i=personneList.size()-1; i>=0; i--) {
                personneList.remove(i);
            }
        }
        personneListAdapter.notifyDataSetChanged();
        if (personneList!=null) {
            personneList.addAll(PersonneService.findByType(typePersonne));
            personneListAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(ListPersonneActivity.this, getString(R.string.import_reload_list), Toast.LENGTH_LONG).show();
        }
    }
}
