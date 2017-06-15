package com.cavalier.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cavalier.R;
import com.cavalier.model.PlanningNote;
import com.cavalier.tools.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddPlanningNoteActivity extends Activity implements MyDialogInterface.DialogReturn {

    private TableRow dateDebutRowPicker, dateFinRowPicker, dateDebutRowTxt, dateFinRowTxt, titreRow, noteRow;
    private DatePicker dateDebutPicker, dateFinPicker;
    private TimePicker timeDebutPicker, timeFinPicker;
    private EditText editTextTitre, editTextNote;
    private TextView textDateDebut, textDateFin;
    private Button buttonUpdateDates, buttonSaveDates;

    private PlanningNote planningNote = null;
    private MyDialogInterface myInterface;
    private Menu menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_add_planning_note);

        titreRow = (TableRow) findViewById(R.id.titreRow);
        noteRow = (TableRow) findViewById(R.id.noteRow);

        dateDebutRowPicker = (TableRow) findViewById(R.id.dateDebutRowPicker);
        dateFinRowPicker = (TableRow) findViewById(R.id.dateFinRowPicker);

        dateDebutRowTxt = (TableRow) findViewById(R.id.dateDebutRowTxt);
        dateFinRowTxt = (TableRow) findViewById(R.id.dateFinRowTxt);

        textDateDebut = (TextView) findViewById(R.id.textDateDebut);
        textDateFin = (TextView) findViewById(R.id.textDateFin);
        buttonUpdateDates = (Button) findViewById(R.id.updateDates);
        buttonSaveDates = (Button) findViewById(R.id.saveDates);

        timeDebutPicker = (TimePicker) findViewById(R.id.timeDebutPicker);
        timeDebutPicker.setIs24HourView(true);
        timeFinPicker = (TimePicker) findViewById(R.id.timeFinPicker);
        timeFinPicker.setIs24HourView(true);

        dateDebutPicker = (DatePicker) findViewById(R.id.dateDebutPicker);
        dateFinPicker = (DatePicker) findViewById(R.id.dateFinPicker);

        editTextTitre = (EditText)  findViewById(R.id.editTextTitre);
        editTextNote = (EditText)  findViewById(R.id.editTextNote);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        buttonUpdateDates.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDates();
            }
        });

        buttonSaveDates.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveDates();
            }
        });

        initView();

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Scrool to top
        final ScrollView scrollview = (ScrollView)findViewById(R.id.noteView);
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

                Calendar calendarFin = calendarDeb;
                calendarFin.add(Calendar.HOUR, 1);
                dateFinPicker.updateDate(calendarFin.get(Calendar.YEAR), calendarFin.get(Calendar.MONTH), calendarFin.get(Calendar.DAY_OF_MONTH));
                timeFinPicker.setCurrentHour(calendarFin.get(Calendar.HOUR_OF_DAY));
                timeFinPicker.setCurrentMinute(calendarFin.get(Calendar.MINUTE));

            }
            long  planningNoteId = bundle.getLong("PlanningNote");
            planningNote = PlanningNote.load(PlanningNote.class, planningNoteId);
            if (planningNote != null) {
                editTextTitre.setText(planningNote.getTitre());
                editTextNote.setText(planningNote.getNote());

                Calendar deb = Calendar.getInstance();
                Calendar fin = Calendar.getInstance();
                deb.setTime(planningNote.getDateDebut());
                fin.setTime(planningNote.getDateFin());

                dateDebutPicker.updateDate(deb.get(Calendar.YEAR), deb.get(Calendar.MONTH), deb.get(Calendar.DAY_OF_MONTH));
                timeDebutPicker.setCurrentHour(deb.get(Calendar.HOUR_OF_DAY));
                timeDebutPicker.setCurrentMinute(deb.get(Calendar.MINUTE));
                dateFinPicker.updateDate(fin.get(Calendar.YEAR), fin.get(Calendar.MONTH), fin.get(Calendar.DAY_OF_MONTH));
                timeFinPicker.setCurrentHour(fin.get(Calendar.HOUR_OF_DAY));
                timeFinPicker.setCurrentMinute(fin.get(Calendar.MINUTE));
            }
            updateMenu(menu);
            this.setTitle(R.string.title_activity_update_note);
        }

        saveDates();
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
        }
        return false;
    }

    private void updateMenu(Menu menu) {
        if (menu !=null) {
            MenuItem itemR = menu.findItem(R.id.action_transform_reprise);
            Utils.hideItem(itemR);
            MenuItem itemD = menu.findItem(R.id.action_delete_planning);
            if (planningNote == null) {
                Utils.disableItem(itemD);
            } else {
                Utils.enableItem(itemD);
            }
        }
    }

    private boolean onSave() {
        if (planningNote == null) {
            planningNote = new PlanningNote();
        }
        int hour = timeDebutPicker.getCurrentHour();
        int min = timeDebutPicker.getCurrentMinute();
        int day = dateDebutPicker.getDayOfMonth();
        int month = (dateDebutPicker.getMonth());
        int year = dateDebutPicker.getYear();
        planningNote.setDateDebut(new GregorianCalendar(year, month, day, hour, min).getTime());
        hour = timeFinPicker.getCurrentHour();
        min = timeFinPicker.getCurrentMinute();
        day = dateFinPicker.getDayOfMonth();
        month = (dateFinPicker.getMonth());
        year = dateFinPicker.getYear();
        planningNote.setDateFin(new GregorianCalendar(year, month, day, hour, min).getTime());

        planningNote.setTitre(editTextTitre.getText().toString());
        planningNote.setNote(editTextNote.getText().toString());

        planningNote.save();
        Toast.makeText(getBaseContext(), getString(R.string.note_save), Toast.LENGTH_LONG).show();

        return true;
    }


    private void onDelete() {
        if (planningNote != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(getString(R.string.title_activity_delete_note) + " " + planningNote.getTitre());
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(true, "planningNote");
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(false, "planningNote");
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
        if ("planningNote".equals(type) && answer && planningNote != null) {
            planningNote.delete();
            Toast.makeText(getBaseContext(), getString(R.string.note_delete), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void updateDates() {
        dateDebutRowTxt.setVisibility(View.GONE);
        dateFinRowTxt.setVisibility(View.GONE);
        buttonUpdateDates.setVisibility(View.GONE);
        titreRow.setVisibility(View.GONE);
        noteRow.setVisibility(View.GONE);

        dateDebutRowPicker.setVisibility(View.VISIBLE);
        dateFinRowPicker.setVisibility(View.VISIBLE);
        buttonSaveDates.setVisibility(View.VISIBLE);
    }


    private void saveDates() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        String dateDebutFormat = dateFormat.format(new Date(dateDebutPicker.getYear(), dateDebutPicker.getMonth(), dateDebutPicker.getDayOfMonth()));
        String dateFinFormat = dateFormat.format(new Date(dateFinPicker.getYear(), dateFinPicker.getMonth(), dateFinPicker.getDayOfMonth()));

        String heureDebut = timeDebutPicker.getCurrentHour() + " : " +  timeDebutPicker.getCurrentMinute();
        String heureFin = timeFinPicker.getCurrentHour() + " : " +  timeFinPicker.getCurrentMinute();

        textDateDebut.setText(dateDebutFormat + " " + heureDebut);
        textDateFin.setText(dateFinFormat + " " + heureFin);

        dateDebutRowPicker.setVisibility(View.GONE);
        dateFinRowPicker.setVisibility(View.GONE);
        buttonSaveDates.setVisibility(View.GONE);

        dateDebutRowTxt.setVisibility(View.VISIBLE);
        dateFinRowTxt.setVisibility(View.VISIBLE);
        buttonUpdateDates.setVisibility(View.VISIBLE);
        titreRow.setVisibility(View.VISIBLE);
        noteRow.setVisibility(View.VISIBLE);

    }
}
