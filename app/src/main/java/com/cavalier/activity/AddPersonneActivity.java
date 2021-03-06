package com.cavalier.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cavalier.adapter.CarteListAdapter;
import com.cavalier.listner.CarteListener;
import com.cavalier.listner.PersonneListener;
import com.cavalier.model.Carte;
import com.cavalier.model.CarteItem;
import com.cavalier.model.Galop;
import com.cavalier.model.Personne;
import com.cavalier.model.Sexe;
import com.cavalier.model.TypePersonne;
import com.cavalier.service.CoursService;
import com.cavalier.R;
import com.cavalier.adapter.IDataSpinnerAdapter;
import com.cavalier.model.IRefData;
import com.cavalier.service.PersonneService;
import com.cavalier.tools.PictureUtils;
import com.cavalier.tools.SpinnerTool;
import com.cavalier.tools.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class AddPersonneActivity extends ListActivity implements MyDialogInterface.DialogReturn, PersonneListener, CarteListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_SELECT = 2;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    private Spinner spinnerSexe, spinnerGalop;
    private EditText editTextNom, editTextPrenom, editTextLicence;
    private TextView textViewType;
    private ImageView imageView, eventAdd;
    private MyDialogInterface myInterface;
    private Personne personne = null;
    private TypePersonne typePersonne;

    private List<Carte> cartes = new ArrayList<>();
    private Carte currentCarte = null;
    private ListView listView;
    private CarteListAdapter carteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_add_personne);

        spinnerSexe = (Spinner) findViewById(R.id.spSexe);
        loadSpinnerSexe();

        spinnerGalop = (Spinner) findViewById(R.id.spGalop);
        loadSpinnerGalop();

        editTextNom = (EditText)  findViewById(R.id.editTextNom);
        editTextPrenom = (EditText)  findViewById(R.id.editTextPrenom);
        editTextLicence = (EditText) findViewById(R.id.editTextLicence);
        textViewType = (TextView) findViewById(R.id.textViewType);
        imageView = (ImageView) findViewById(R.id.personne_pic);
        eventAdd = (ImageView) findViewById(R.id.event_add);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        initView();

        if (TypePersonne.CAVALIER == typePersonne) {
            if (getActionBar() != null) {
                getActionBar().setIcon(R.drawable.bombe);
            }
            setTitle("Cavalier");
        } else {
            if (getActionBar() != null) {
                getActionBar().setIcon(R.drawable.coach);
            }
            setTitle("Moniteur");
        }

        // Liste évènements
        listView = getListView();
        carteListAdapter = new CarteListAdapter(this, cartes);
        carteListAdapter.addListener(this);
        setListAdapter(carteListAdapter);

        eventAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addCarte();
            }
        });

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
            case R.id.action_take_picture:
                takePersonnePictureIntent();
                return true;
            case R.id.action_select_picture:
                selectPersonnePictureIntent();
                return true;
        }
        return false;
    }

    private void loadSpinnerSexe() {
        ArrayList<IRefData> list = new ArrayList<>();
        Collections.addAll(list, Sexe.values());
        spinnerSexe.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.light_custom_spinner));
    }

    private void loadSpinnerGalop() {
        ArrayList<IRefData> list = new ArrayList<>();
        Collections.addAll(list, Galop.values());
        spinnerGalop.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.light_custom_spinner));
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
                editTextLicence.setText(personne.getLicence());
                textViewType.setText(personne.getType().getLabel());
                SpinnerTool.SelectSpinnerItemByValue(spinnerSexe, personne.getSexe().name());
                if (personne.getGalop() != null) {
                    SpinnerTool.SelectSpinnerItemByValue(spinnerGalop, personne.getGalop().name());
                }
                typePersonne = personne.getType();
                if (personne.getImg() != null) {
                    imageView.setImageBitmap(PictureUtils.getImage(personne.getImg()));
                }
                cartes = PersonneService.findCarteByPersonneId(personneId);

            } else {
                typePersonne = TypePersonne.valueOf(bundle.getString(Personne.TYPE_PERSONNE));
                textViewType.setText(typePersonne.getLabel());
            }
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickImageListener();
            }
        });
    }

    private boolean onSave() {
        Editable edName = editTextNom.getText();
        Editable edPrenom = editTextPrenom.getText();
        Editable edLicense = editTextLicence.getText();
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
            personne.setGalop((Galop) spinnerGalop.getSelectedItem());
            personne.setNom(edName.toString());
            personne.setPrenom(edPrenom.toString());
            personne.setLicence(edLicense.toString());

            imageView.buildDrawingCache();
            Bitmap imageBitmap = imageView.getDrawingCache();
            if (imageBitmap != null) {
                personne.setImg(PictureUtils.getBitmapAsByteArray(imageBitmap));
            }

            personne.save();
            Toast.makeText(getBaseContext(), getString(R.string.personne_save), Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void onDelete() {
        if (personne != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(personne.getNom() + " - " + personne.getPrenom());
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(true, "DELETE");
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(false, "DELETE");
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void onClickImageListener() {
        if (personne != null && personne.getImg() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(R.string.image_delete);
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(true, "IMAGE");
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(false, "IMAGE");
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if (answer && personne!=null && "DELETE".equals(type)) {
            personne.delete();
            Toast.makeText(getBaseContext(), getString(R.string.personne_delete), Toast.LENGTH_LONG).show();
            Intent listPersonneActivity = new Intent(getApplicationContext(), ListPersonneActivity.class);
            listPersonneActivity.putExtra(Personne.TYPE_PERSONNE, typePersonne.name());
            startActivity(listPersonneActivity);
            finish();
        }
        if (answer && personne!=null && "IMAGE".equals(type)) {
            personne.setImg(null);
            imageView.setImageBitmap(null);
        }
        if (answer && currentCarte!=null && "DELETE_CARTE".equals(type)) {
            currentCarte.delete();
            Toast.makeText(getBaseContext(), getString(R.string.carte_delete), Toast.LENGTH_LONG).show();
            if (cartes!=null) {
                for (int i=cartes.size()-1; i>=0; i--) {
                    cartes.remove(i);
                }
            }
            cartes.addAll(PersonneService.findCarteByPersonneId(personne.getId()));
            carteListAdapter.notifyDataSetChanged();
        }
    }

    private void takePersonnePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void selectPersonnePictureIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 156);
        intent.putExtra("outputY", 180);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_IMAGE_SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, 156, 180, false));
        }
        if (requestCode == REQUEST_IMAGE_SELECT && resultCode == RESULT_OK) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = extras.getParcelable("data");
                imageView.setImageBitmap(imageBitmap);
            }
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

    private void addCarte() {
        if (personne != null) {
            Intent myIntent = new Intent(getApplicationContext(), AddCarteActivity.class);
            myIntent.putExtra(Personne.ID_PERSONNE, personne.getId());
            startActivity(myIntent);
            finish();
        }
    }

    @Override
    public void onUpdateCarte(Carte carte, int position) {
        if (carte != null) {
            Intent myIntent = new Intent(getApplicationContext(), UpdateCarteActivity.class);
            myIntent.putExtra(Carte.ID_CARTE, carte.getId());
            startActivity(myIntent);
            finish();
        }
    }

    @Override
    public void onDeleteCarte(Carte carte, int position) {
        if (carte != null) {
            currentCarte = carte;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(carte.getNom() + " - " + sdf.format(carte.getDateOuverture()));
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(true, "DELETE_CARTE");
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(false, "DELETE_CARTE");
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onUpdateCarteItem(CarteItem item, int position) {
        // Nothings
    }



}
