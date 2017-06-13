package com.cavalier.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.adapter.IDataSpinnerAdapter;
import com.cavalier.adapter.MontureSpinnerAdapter;
import com.cavalier.adapter.PersonneSpinnerAdapter;
import com.cavalier.listner.CoursListener;
import com.cavalier.model.Cours;
import com.cavalier.model.IRefData;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.cavalier.model.PlanningEvent;
import com.cavalier.model.TypeLieu;
import com.cavalier.model.TypePersonne;
import com.cavalier.service.MontureService;
import com.cavalier.service.PersonneService;
import com.cavalier.tools.SpinnerTool;
import com.cavalier.tools.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AddPlanningEventActivity extends Activity implements MyDialogInterface.DialogReturn {

    private Spinner spinnerMoniteur, spinnerCavalier, spinnerMonture, spinnerLieu;
    private DatePicker dateDebutPicker, dateFinPicker;
    private TimePicker timeDebutPicker, timeFinPicker;
    private EditText editTextObservation;
    private PlanningEvent planningEvent = null;
    private MyDialogInterface myInterface;
    private Menu menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_add_planning_event);

        spinnerCavalier = (Spinner) findViewById(R.id.spCavalier);
        loadSpinnerCavalier(spinnerCavalier);
        spinnerMoniteur = (Spinner) findViewById(R.id.spMoniteur);
        loadSpinnerMoniteur(spinnerMoniteur);
        spinnerMonture = (Spinner) findViewById(R.id.spMonture);
        loadSpinnerMonture(spinnerMonture);
        spinnerLieu = (Spinner) findViewById(R.id.spLieu);
        loadSpinnerLieu(spinnerLieu);

        timeDebutPicker = (TimePicker) findViewById(R.id.timeDebutPicker);
        timeDebutPicker.setIs24HourView(true);
        timeFinPicker = (TimePicker) findViewById(R.id.timeFinPicker);
        timeFinPicker.setIs24HourView(true);

        dateDebutPicker = (DatePicker) findViewById(R.id.dateDebutPicker);
        dateFinPicker = (DatePicker) findViewById(R.id.dateFinPicker);

        editTextObservation = (EditText)  findViewById(R.id.editTextObservation);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        initView();

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Scrool to top
        final ScrollView scrollview = (ScrollView)findViewById(R.id.cours_scrollView);
        scrollview.post(new Runnable() {
            public void run() {
                scrollview.scrollTo(0, 0);
            }
        });

    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            Calendar calendarDeb = (Calendar) bundle.get("Calendar");
            if (calendarDeb != null) {
                dateDebutPicker.updateDate(calendarDeb.get(Calendar.YEAR), calendarDeb.get(Calendar.MONTH), calendarDeb.get(Calendar.DAY_OF_MONTH));
                timeDebutPicker.setCurrentHour(calendarDeb.get(Calendar.HOUR_OF_DAY));
                timeDebutPicker.setCurrentMinute(calendarDeb.get(Calendar.MINUTE));
            }
            long  planningEventId = bundle.getLong("PlanningEvent");
            planningEvent = PlanningEvent.load(PlanningEvent.class, planningEventId);
            if (planningEvent != null) {
                editTextObservation.setText(planningEvent.getObservation());
                SpinnerTool.SelectSpinnerItemByValue(spinnerCavalier, planningEvent.getCavalier());
                SpinnerTool.SelectSpinnerItemByValue(spinnerMoniteur, planningEvent.getMoniteur());
                SpinnerTool.SelectSpinnerItemByValue(spinnerMonture, planningEvent.getMonture());
                SpinnerTool.SelectSpinnerItemByValue(spinnerLieu, planningEvent.getTypeLieu().name());

                Calendar deb = Calendar.getInstance();
                Calendar fin = Calendar.getInstance();
                deb.setTime(planningEvent.getDateDebut());
                fin.setTime(planningEvent.getDateFin());

                dateDebutPicker.updateDate(deb.get(Calendar.YEAR), deb.get(Calendar.MONTH), deb.get(Calendar.DAY_OF_MONTH));
                timeDebutPicker.setCurrentHour(deb.get(Calendar.HOUR_OF_DAY));
                timeDebutPicker.setCurrentMinute(deb.get(Calendar.MINUTE));
                dateFinPicker.updateDate(fin.get(Calendar.YEAR), fin.get(Calendar.MONTH), fin.get(Calendar.DAY_OF_MONTH));
                timeFinPicker.setCurrentHour(fin.get(Calendar.HOUR_OF_DAY));
                timeFinPicker.setCurrentMinute(fin.get(Calendar.MINUTE));
            }
            updateMenu(menu);
            this.setTitle(R.string.title_activity_update_planning);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_add_planning, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        updateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_planning:
                if (onSave()) {
                    finish();
                    return true;
                }
                return false;
            case R.id.action_close_planning:
                finish();
                return true;
            case R.id.action_delete_planning:
                onDelete();
                return true;
            case R.id.action_transform_reprise:
                onTransformCours();
                return true;
        }
        return false;
    }

    private void updateMenu(Menu menu) {
        if (menu !=null) {
            MenuItem itemR = menu.findItem(R.id.action_transform_reprise);
            MenuItem itemD = menu.findItem(R.id.action_delete_planning);
            if (planningEvent == null) {
                Utils.disableItem(itemR);
                Utils.disableItem(itemD);
            } else {
                Utils.enableItem(itemR);
                Utils.enableItem(itemD);
            }
        }
    }

    private void loadSpinnerCavalier(Spinner spinner) {
        spinner.setAdapter(new PersonneSpinnerAdapter(this, PersonneService.findByType(TypePersonne.CAVALIER)));
    }

    private void loadSpinnerMoniteur(Spinner spinner) {
        spinner.setAdapter(new PersonneSpinnerAdapter(this, PersonneService.findByType(TypePersonne.MONITEUR)));
    }

    private void loadSpinnerMonture(Spinner spinner) {
        spinner.setAdapter(new MontureSpinnerAdapter(this, MontureService.getAll()));
    }

    private void loadSpinnerLieu(Spinner spinner) {
        ArrayList<IRefData> list = new ArrayList<>();
        Collections.addAll(list, TypeLieu.values());
        spinner.setAdapter(new IDataSpinnerAdapter(this, list, R.layout.light_custom_spinner));
    }


    private boolean onSave() {
        if (planningEvent == null) {
            planningEvent = new PlanningEvent();
        }
        int hour = timeDebutPicker.getCurrentHour();
        int min = timeDebutPicker.getCurrentMinute();
        int day = dateDebutPicker.getDayOfMonth();
        int month = (dateDebutPicker.getMonth());
        int year = dateDebutPicker.getYear();
        planningEvent.setDateDebut(new GregorianCalendar(year, month, day, hour, min).getTime());
        hour = timeFinPicker.getCurrentHour();
        min = timeFinPicker.getCurrentMinute();
        day = dateFinPicker.getDayOfMonth();
        month = (dateFinPicker.getMonth());
        year = dateFinPicker.getYear();
        planningEvent.setDateFin(new GregorianCalendar(year, month, day, hour, min).getTime());

        planningEvent.setCavalier((Personne) spinnerCavalier.getSelectedItem());
        planningEvent.setMoniteur((Personne) spinnerMoniteur.getSelectedItem());
        planningEvent.setMonture((Monture) spinnerMonture.getSelectedItem());
        planningEvent.setTypeLieu((TypeLieu) spinnerLieu.getSelectedItem());
        planningEvent.setObservation(editTextObservation.getText().toString());

        planningEvent.save();
        Toast.makeText(getBaseContext(), getString(R.string.planning_save), Toast.LENGTH_LONG).show();

        return true;
    }


    private void onDelete() {
        if (planningEvent != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(getString(R.string.title_activity_delete_planning) + " " + planningEvent.getMonture().getNom());
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(true, "planningEvent");
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(false, "planningEvent");
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void onTransformCours() {
        if (planningEvent != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.selle);
            builder.setTitle(R.string.action_transform_reprise);
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(true, "createCours");
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(false, "createCours");
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onBackPressed() {
        // Nothings
    }


    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if ("planningEvent".equals(type) && answer && planningEvent != null) {
            planningEvent.delete();
            Toast.makeText(getBaseContext(), getString(R.string.planning_delete), Toast.LENGTH_LONG).show();
            finish();
        }
        if ("createCours".equals(type) && answer && planningEvent != null) {
            long intervalle = planningEvent.getDateFin().getTime() - planningEvent.getDateDebut().getTime();
            int duree = (int) TimeUnit.HOURS.convert(intervalle, TimeUnit.MILLISECONDS);

            Cours cours = new Cours(planningEvent.getMoniteur(),
                    planningEvent.getCavalier(),
                    planningEvent.getMonture(),
                    planningEvent.getTypeLieu(),
                    planningEvent.getDateDebut(),
                    duree,
                    planningEvent.getObservation());
            cours.save();
            planningEvent.delete();
            Toast.makeText(getBaseContext(), getString(R.string.action_transform_reprise_ok), Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
