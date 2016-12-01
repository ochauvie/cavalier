package com.cavalier.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.adapter.IDataSpinnerAdapter;
import com.cavalier.adapter.MontureSpinnerAdapter;
import com.cavalier.model.EvenementMonture;
import com.cavalier.model.IRefData;
import com.cavalier.model.Monture;
import com.cavalier.model.TypeEvenement;
import com.cavalier.service.MontureService;
import com.cavalier.tools.SpinnerTool;
import com.cavalier.tools.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class AddEvenementActivity extends Activity implements DatePickerDialog.OnDateSetListener {

    private Spinner spinnerMonture, spinnerType;
    private EditText editTextObservation;
    private TextView textDate;
    private ImageButton selectDate;
    private DatePickerDialog datePickerDialog = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    private Monture monture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_add_evenement);

        spinnerMonture = (Spinner) findViewById(R.id.spMonture);
        loadSpinnerMonture(spinnerMonture);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            long montureId = bundle.getLong(Monture.ID_MONTURE);
            monture = Monture.load(Monture.class, montureId);
            if (monture != null) {
                SpinnerTool.SelectSpinnerItemByValue(spinnerMonture, monture);
                spinnerMonture.setEnabled(false);
            }
        }

        spinnerType = (Spinner) findViewById(R.id.spType);
        loadSpinnerType(spinnerType);

        editTextObservation = (EditText)  findViewById(R.id.editTextObservation);
        textDate = (TextView)  findViewById(R.id.textViewDate);
        //textDate.setEnabled(false);
        textDate.setText(sdf.format(new Date()));

        selectDate = (ImageButton) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sDate = textDate.getText().toString();
                String[] ssDate = sDate.split("/");
                datePickerDialog = new DatePickerDialog(v.getContext(),
                        AddEvenementActivity.this,
                        Integer.parseInt(ssDate[2]),
                        Integer.parseInt(ssDate[1])-1,
                        Integer.parseInt(ssDate[0]));
                datePickerDialog.show();
            }
        });
        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_add_evenement, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemS = menu.findItem(R.id.action_add);
        Utils.enableItem(itemS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                if (onSave()) {
                    Intent myIntent = new Intent(getApplicationContext(), AddMontureActivity.class);
                    myIntent.putExtra(Monture.ID_MONTURE, monture.getId());
                    startActivity(myIntent);
                    finish();
                    return true;
                }
                return false;
            case R.id.action_close:
                Intent myIntent = new Intent(getApplicationContext(), AddMontureActivity.class);
                myIntent.putExtra(Monture.ID_MONTURE, monture.getId());
                startActivity(myIntent);
                finish();
                return true;
        }
        return false;
    }

    private void loadSpinnerMonture(Spinner spinner) {
        spinner.setAdapter(new MontureSpinnerAdapter(this, MontureService.getAll()));
    }

    private void loadSpinnerType(Spinner spinner) {
        ArrayList<IRefData> list = new ArrayList<>();
        Collections.addAll(list, TypeEvenement.values());
        spinner.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.custom_spinner));
    }

    private boolean onSave() {
        EvenementMonture evenementMonture = new EvenementMonture();
        try {
            evenementMonture.setDate(sdf.parse(textDate.getText().toString()));
        } catch (ParseException pe) {
            evenementMonture.setDate(new Date());
        }
        monture = (Monture) spinnerMonture.getSelectedItem();
        evenementMonture.setMonture(monture);
        evenementMonture.setType((TypeEvenement) spinnerType.getSelectedItem());
        evenementMonture.setObservation(editTextObservation.getText().toString());

        evenementMonture.save();
        Toast.makeText(getBaseContext(), getString(R.string.evenement_save), Toast.LENGTH_LONG).show();

        return true;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String day = String.valueOf(dayOfMonth);
        if (day.length()<2) {day = "0" + day;}
        String month = String.valueOf(monthOfYear+1);
        if (month.length()<2) {month = "0" + month;}
        String y = String.valueOf(year);
        if (y.length()<2) {y = "0" + y;}
        textDate.setText(day + "/" + month + "/" + y);
        datePickerDialog.hide();
    }

    @Override
    public void onBackPressed() {
        // Nothings
    }

}
