package com.cavalier.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.adapter.IDataSpinnerAdapter;
import com.cavalier.listner.MontureListener;
import com.cavalier.model.Genre;
import com.cavalier.model.IRefData;
import com.cavalier.model.Monture;
import com.cavalier.service.CoursService;
import com.cavalier.tools.SpinnerTool;
import com.cavalier.tools.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class AddMontureActivity extends Activity implements MyDialogInterface.DialogReturn, MontureListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Spinner spinnerGenre;
    private EditText editTextNom, editTextRobe;
    private ImageView imageView;
    private MyDialogInterface myInterface;
    private Monture monture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_add_monture);

        spinnerGenre = (Spinner) findViewById(R.id.spGenre);
        loadSpinnerGenre();

        editTextNom = (EditText)  findViewById(R.id.editTextNom);
        editTextRobe = (EditText)  findViewById(R.id.editTextRobe);
        imageView = (ImageView) findViewById(R.id.cheval_pic);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        initView();

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
                Utils.disableItem(itemS);
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
            case R.id.action_picture:
                dispatchTakePictureIntent();
                return true;
        }
        return false;
    }

    private void loadSpinnerGenre() {
        ArrayList<IRefData> list = new ArrayList<IRefData>();
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
                SpinnerTool.SelectSpinnerItemByValue(spinnerGenre, monture.getGenre().name());
            }
        }
    }

    private boolean onSave() {
        Editable edName = editTextNom.getText();
        Editable edRobe = editTextRobe.getText();
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

            monture.save();
            Toast.makeText(getBaseContext(), getString(R.string.monture_save), Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private boolean onDelete() {
        if (monture != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(monture.getNom() + " - " + getString(monture.getGenre().getLabel()) + " - " + monture.getRobe());
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
        if (answer && monture!=null) {
            monture.delete();
            Toast.makeText(getBaseContext(), getString(R.string.monture_delete), Toast.LENGTH_LONG).show();
            Intent listActivity = new Intent(getApplicationContext(), ListMontureActivity.class);
            startActivity(listActivity);
            finish();
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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

//    public void insertImg(int id , Bitmap img ) {
//
//
//        byte[] data = getBitmapAsByteArray(img); // this is a function
//
//        insertStatement_logo.bindLong(1, id);
//        insertStatement_logo.bindBlob(2, data);
//
//        insertStatement_logo.executeInsert();
//        insertStatement_logo.clearBindings() ;
//
//    }
//
//    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(CompressFormat.PNG, 0, outputStream);
//        return outputStream.toByteArray();
//    }
//
//    public Bitmap getImage(int i){
//
//        String qu = "select img  from table where feedid=" + i ;
//        Cursor cur = db.rawQuery(qu, null);
//
//        if (cur.moveToFirst()){
//            byte[] imgByte = cur.getBlob(0);
//            cur.close();
//            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
//        }
//        if (cur != null && !cur.isClosed()) {
//            cur.close();
//        }
//
//        return null ;
//    }

}
