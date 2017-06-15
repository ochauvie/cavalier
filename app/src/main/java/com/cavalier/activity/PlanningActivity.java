package com.cavalier.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.cavalier.R;
import com.cavalier.model.Cours;
import com.cavalier.model.PlanningEvent;
import com.cavalier.model.PlanningNote;
import com.cavalier.model.TypePlanningEvent;
import com.cavalier.service.CoursService;
import com.cavalier.service.PlanningEventService;
import com.cavalier.service.PlanningNoteService;
import com.cavalier.tools.PictureUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// https://github.com/alamkanak/Android-Week-View
public class PlanningActivity extends Activity implements MonthLoader.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {

    private LinearLayout linearlayout;
    private WeekView mWeekView;
    private List<Cours> coursList;
    private List<PlanningEvent> planningEventList;
    private List<PlanningNote> planningNoteList;
    private ProgressBar spinner;


    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_DAY_VIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_planning);

        linearlayout = (LinearLayout) findViewById(R.id.planning);
        linearlayout.setBackgroundColor(Color.WHITE);

        coursList = CoursService.getAll();
        planningEventList = PlanningEventService.getAll();
        planningNoteList = PlanningNoteService.getAll();

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        mWeekView.goToHour(8);

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<>();

        for (Cours cours:coursList) {
            Calendar deb = Calendar.getInstance();
            Calendar fin = Calendar.getInstance();
            deb.setTime(cours.getDate());
            fin.setTime(cours.getDate());
            fin.add(Calendar.HOUR, cours.getDuree());
            if (deb.get(Calendar.YEAR) == newYear && (deb.get(Calendar.MONTH)+1) == newMonth) {
                WeekViewEvent weekViewEvent = new WeekViewEvent();
                weekViewEvent.setTypeEvent(TypePlanningEvent.COURS.name());
                weekViewEvent.setId(cours.getId());
                weekViewEvent.setName(getString(TypePlanningEvent.COURS.getLabel()) + "\n" + cours.getCavalier().getPrenom() + "\n" + cours.getMonture().getNom());
                weekViewEvent.setStartTime(deb);
                weekViewEvent.setEndTime(fin);
                weekViewEvent.setColor(cours.getMonture().getPlanningColor());
                weekViewEvent.setTextColor(Color.WHITE);
                //weekViewEvent.setLocation(cours.getTypeLieu().name());
                events.add(weekViewEvent);
            }
        }

        for (PlanningEvent planningEvent:planningEventList) {
            Calendar deb = Calendar.getInstance();
            Calendar fin = Calendar.getInstance();
            deb.setTime(planningEvent.getDateDebut());
            fin.setTime(planningEvent.getDateFin());
            if (deb.get(Calendar.YEAR) == newYear && (deb.get(Calendar.MONTH)+1) == newMonth) {
                WeekViewEvent weekViewEvent = new WeekViewEvent();
                weekViewEvent.setTypeEvent(TypePlanningEvent.COURS_PLANIFIE.name());
                weekViewEvent.setId(planningEvent.getId());
                weekViewEvent.setName(getString(TypePlanningEvent.COURS_PLANIFIE.getLabel()) + "\n" + planningEvent.getCavalier().getPrenom() + "\n" + planningEvent.getMonture().getNom());
                weekViewEvent.setStartTime(deb);
                weekViewEvent.setEndTime(fin);
                weekViewEvent.setTextColor(Color.WHITE);
                int alpha = 150;
                weekViewEvent.setColor(ColorUtils.setAlphaComponent(planningEvent.getMonture().getPlanningColor(), alpha));
                events.add(weekViewEvent);
            }
        }

        for (PlanningNote planningNote:planningNoteList) {
            Calendar deb = Calendar.getInstance();
            Calendar fin = Calendar.getInstance();
            deb.setTime(planningNote.getDateDebut());
            fin.setTime(planningNote.getDateFin());
            if (deb.get(Calendar.YEAR) == newYear && (deb.get(Calendar.MONTH)+1) == newMonth) {
                WeekViewEvent weekViewEvent = new WeekViewEvent();
                weekViewEvent.setTypeEvent(TypePlanningEvent.NOTE.name());
                weekViewEvent.setId(planningNote.getId());
                weekViewEvent.setName(getString(TypePlanningEvent.NOTE.getLabel()) + "\n" + planningNote.getTitre());
                weekViewEvent.setStartTime(deb);
                weekViewEvent.setEndTime(fin);
                weekViewEvent.setColor(Color.YELLOW);
                weekViewEvent.setTextColor(Color.BLACK);
                events.add(weekViewEvent);
            }
        }

        return events;
    }


    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

        // TODO : choix action sur l'élément (menu: delete, update, transfomrer)

        if ( TypePlanningEvent.COURS.name().equals(event.getTypeEvent())) {
            Cours cours = CoursService.getById(event.getId());
            if (cours != null) {
                showCoursEvent(cours);
            }
        } else if (TypePlanningEvent.COURS_PLANIFIE.name().equals(event.getTypeEvent())) {
            PlanningEvent plannigEvent = PlanningEventService.getById(event.getId());
            if (plannigEvent != null) {
                spinner.setVisibility(View.VISIBLE);
                showPlannigEvent(plannigEvent);
            }
        } else if (TypePlanningEvent.NOTE.name().equals(event.getTypeEvent())) {
            PlanningNote planningNote = PlanningNoteService.getById(event.getId());
            if (planningNote != null) {
                showPlannigNote(planningNote);
            }
        }
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        //Toast.makeText(this, "onEventLongPress event: " + event.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        final Calendar  calendar = time;

        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_planning_new, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
        btnDismiss.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        Button btnAddCours = (Button)popupView.findViewById(R.id.addCoursPlanifie);
        btnAddCours.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                Intent myIntent = new Intent(getApplicationContext(), AddPlanningEventActivity.class);
                myIntent.putExtra("Calendar", calendar);
                startActivityForResult(myIntent, 200);
                popupWindow.dismiss();
            }
        });

        Button btnNote = (Button)popupView.findViewById(R.id.addNote);
        btnNote.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent noteIntent = new Intent(getApplicationContext(), AddPlanningNoteActivity.class);
                noteIntent.putExtra("Calendar", calendar);
                startActivityForResult(noteIntent, 300);
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(linearlayout, Gravity.CENTER, 0, 0);

    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    private void showCoursEvent(Cours cours) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.activity_cours_viewer, null);

        TextView txMonture = (TextView)  view.findViewById(R.id.txMonture);
        TextView txCavalier = (TextView) view.findViewById(R.id.txCavalier);
        TextView txMoniteur = (TextView) view.findViewById(R.id.txMoniteur);
        TextView txLieu = (TextView) view.findViewById(R.id.txLieu);
        TextView txObs = (TextView) view.findViewById(R.id.txObs);

        ImageView imgMonture = (ImageView) view.findViewById(R.id.imgMonture);
        ImageView imgCavalier = (ImageView) view.findViewById(R.id.imgCavalier);
        ImageView imgMoniteur = (ImageView) view.findViewById(R.id.imgMoniteur);

        txMonture.setText(cours.getMonture().getNom());
        txCavalier.setText(cours.getCavalier().getPrenom() + " " + cours.getCavalier().getNom());
        txMoniteur.setText(cours.getMoniteur().getPrenom() + " " + cours.getMoniteur().getNom());
        txLieu.setText(getString(cours.getTypeLieu().getLabel()));
        txObs.setText(cours.getObservation());

        if (cours.getMonture().getImg() != null) {
            imgMonture.setImageBitmap(PictureUtils.getImage(cours.getMonture().getImg()));
        }
        if (cours.getMoniteur().getImg() != null) {
            imgMoniteur.setImageBitmap(PictureUtils.getImage(cours.getMoniteur().getImg()));
        }
        if (cours.getCavalier().getImg() != null) {
            imgCavalier.setImageBitmap(PictureUtils.getImage(cours.getCavalier().getImg()));
        }

        builder.setView(view);
        builder.setCancelable(true);

        builder.setIcon(R.drawable.details);
        builder.setTitle("Reprise");

        builder.setInverseBackgroundForced(true);
        builder.setNegativeButton(R.string.action_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showPlannigEvent(PlanningEvent planningEvent) {
        Intent myIntent = new Intent(getApplicationContext(), AddPlanningEventActivity.class);
        myIntent.putExtra("PlanningEvent", planningEvent.getId());
        startActivityForResult(myIntent, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 || requestCode == 200 ) {
            coursList = CoursService.getAll();
            planningEventList = PlanningEventService.getAll();
            mWeekView.notifyDatasetChanged();
        }
        if (requestCode == 300) {
            planningNoteList = PlanningNoteService.getAll();
            mWeekView.notifyDatasetChanged();
        }
        spinner.setVisibility(View.GONE);
    }

    private void showPlannigNote(PlanningNote planningNote) {
        Intent myIntent = new Intent(getApplicationContext(), AddPlanningNoteActivity.class);
        myIntent.putExtra("PlanningNote", planningNote.getId());
        startActivityForResult(myIntent, 300);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cavalier_planning, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.goToHour(8);
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.goToHour(8);
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.goToHour(8);
                }
                return true;
            case R.id.action_close_cours:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Nothings
    }

}
