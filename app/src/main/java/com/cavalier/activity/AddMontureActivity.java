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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.adapter.EvenementListAdapter;
import com.cavalier.adapter.IDataSpinnerAdapter;
import com.cavalier.listner.MontureListener;
import com.cavalier.model.EvenementMonture;
import com.cavalier.model.Genre;
import com.cavalier.model.IRefData;
import com.cavalier.model.Monture;
import com.cavalier.service.CoursService;
import com.cavalier.service.MontureService;
import com.cavalier.tools.PictureUtils;
import com.cavalier.tools.SpinnerTool;
import com.cavalier.tools.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class AddMontureActivity extends ListActivity implements MyDialogInterface.DialogReturn, MontureListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_SELECT = 2;

    private Spinner spinnerGenre;
    private EditText editTextNom, editTextRobe, editTextRace, editTextCaracteristique;
    private ImageView imageView, eventAdd;
    private MyDialogInterface myInterface;
    private Monture monture = null;

    private ListView listView;
    private List<EvenementMonture> evenementList = new ArrayList<>();
    private EvenementListAdapter evenementListAdapter;
    private EvenementMonture selectedEvenementMonture;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_add_monture);

        spinnerGenre = (Spinner) findViewById(R.id.spGenre);
        loadSpinnerGenre();

        editTextNom = (EditText)  findViewById(R.id.editTextNom);
        editTextRobe = (EditText)  findViewById(R.id.editTextRobe);
        editTextRace = (EditText)  findViewById(R.id.editTextRace);
        editTextCaracteristique = (EditText)  findViewById(R.id.editTextCaracteristique);
        imageView = (ImageView) findViewById(R.id.cheval_pic);
        eventAdd = (ImageView) findViewById(R.id.event_add);

        eventAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addEvenement();
            }
        });

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        initView();

        // Liste évènements
        listView = getListView();
        if (monture != null) {
            evenementList = MontureService.findEvenementByMonture(monture);
        }
        evenementListAdapter = new EvenementListAdapter(this, evenementList);
        evenementListAdapter.addListener(this);
        setListAdapter(evenementListAdapter);

        // Scrool to top
        final ScrollView scrollview = (ScrollView)findViewById(R.id.main_scrollView);
        scrollview.post(new Runnable() {
            public void run() {
                scrollview.scrollTo(0, 0);
            }
        });

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_add_monture, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemD = menu.findItem(R.id.action_delete_monture);
        MenuItem itemS = menu.findItem(R.id.action_add_monture);
        if (monture != null) {
            if (CoursService.isMontureInCours(monture)) {
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
            case R.id.action_add_monture:
                if (onSave()) {
                    Intent listActivity = new Intent(getApplicationContext(), ListMontureActivity.class);
                    startActivity(listActivity);
                    finish();
                    return true;
                }
                return false;
            case R.id.action_close_monture:
                Intent listActivity = new Intent(getApplicationContext(), ListMontureActivity.class);
                startActivity(listActivity);
                finish();
                return true;
            case R.id.action_delete_monture:
                onDelete();
                return false;
            case R.id.action_take_picture:
                takeChevalPictureIntent();
                return true;
            case R.id.action_select_picture:
                selectChevalPictureIntent();
                return true;
        }
        return false;
    }

    private void loadSpinnerGenre() {
        ArrayList<IRefData> list = new ArrayList<>();
        Collections.addAll(list, Genre.values());
        spinnerGenre.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.light_custom_spinner));
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            long montureId = bundle.getLong(Monture.ID_MONTURE);
            monture = Monture.load(Monture.class, montureId);
            if (monture != null) {
                editTextNom.setText(monture.getNom());
                editTextRobe.setText(monture.getRobe());
                editTextCaracteristique.setText(monture.getCaracteristique());
                editTextRace.setText(monture.getRace());
                SpinnerTool.SelectSpinnerItemByValue(spinnerGenre, monture.getGenre().name());
                if (monture.getImg() != null) {
                    imageView.setImageBitmap(PictureUtils.getImage(monture.getImg()));
                }
            }
        }
    }

    private boolean onSave() {
        Editable edName = editTextNom.getText();
        Editable edRobe = editTextRobe.getText();
        Editable edCaracteristique =  editTextCaracteristique.getText();
        Editable edRace = editTextRace.getText();
        if (edName==null || "".equals(edName.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.nom_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (monture == null) {
                monture = new Monture();
            }
            monture.setGenre((Genre) spinnerGenre.getSelectedItem());
            monture.setNom(edName.toString());
            monture.setRobe(edRobe.toString());
            monture.setCaracteristique(edCaracteristique.toString());
            monture.setRace(edRace.toString());

            imageView.buildDrawingCache();
            Bitmap imageBitmap = imageView.getDrawingCache();
            if (imageBitmap != null) {
                monture.setImg(PictureUtils.getBitmapAsByteArray(imageBitmap));
            }

            monture.save();
            Toast.makeText(getBaseContext(), getString(R.string.monture_save), Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void onDelete() {
        if (monture != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(monture.getNom() + " - " + getString(monture.getGenre().getLabel()) + " - " + monture.getRobe());
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(true, "monture");
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(false, "monture");
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if ("monture".equals(type) && answer && monture!=null) {
            monture.delete();
            Toast.makeText(getBaseContext(), getString(R.string.monture_delete), Toast.LENGTH_LONG).show();
            Intent listActivity = new Intent(getApplicationContext(), ListMontureActivity.class);
            startActivity(listActivity);
            finish();
        }
        if ("evenement".equals(type) && answer && selectedEvenementMonture!=null) {
            selectedEvenementMonture.delete();
            Toast.makeText(getBaseContext(), getString(R.string.evenement_delete), Toast.LENGTH_LONG).show();
            if (evenementList!=null) {
                for (int i=evenementList.size()-1; i>=0; i--) {
                    evenementList.remove(i);
                }
            }
            evenementList.addAll(MontureService.findEvenementByMonture(monture));
            evenementListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        // Nothings
    }

    @Override
    public void onClick(Monture item, int position) {
        // Nothings
    }

    @Override
    public void onDeleteEvenement(EvenementMonture item, int position) {
        if (item != null) {
            selectedEvenementMonture = item;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(getString(item.getType().getLabel()) + " - " + sdf.format(item.getDate()) );
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(true, "evenement");
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(false, "evenement");
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void takeChevalPictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void selectChevalPictureIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 280);
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
            imageView.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_IMAGE_SELECT && resultCode == RESULT_OK) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = extras.getParcelable("data");
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }

    private void addEvenement() {
        Intent myIntent = new Intent(getApplicationContext(), AddEvenementActivity.class);
        myIntent.putExtra(Monture.ID_MONTURE, monture.getId());
        startActivity(myIntent);
        finish();
    }


}
