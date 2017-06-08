package com.cavalier.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.cavalier.R;
import com.cavalier.model.Cours;
import com.cavalier.model.Personne;
import com.cavalier.service.CoursService;
import com.cavalier.service.PersonneService;
import com.cavalier.tools.PictureUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// https://github.com/alamkanak/Android-Week-View
public class PlanningActivity extends Activity implements MonthLoader.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener{

    private LinearLayout linearlayout;
    private WeekView mWeekView;
    private List<Cours> coursList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavalier_planning);

        linearlayout = (LinearLayout) findViewById(R.id.planning);
        linearlayout.setBackgroundColor(Color.WHITE);

//        Personne cavalier = PersonneService.findByNomPrenom("Chauvie", "Olivier");
//        coursList = CoursService.getByCavalier(cavalier);
        coursList = CoursService.getAll();

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
                weekViewEvent.setId(cours.getId());
                weekViewEvent.setName("Reprise\n" + cours.getCavalier().getPrenom() + "\n" + cours.getMonture().getNom());
                weekViewEvent.setStartTime(deb);
                weekViewEvent.setEndTime(fin);
                weekViewEvent.setColor(Color.RED);
                //weekViewEvent.setLocation(cours.getTypeLieu().name());
                events.add(weekViewEvent);
            }
        }

        return events;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Cours cours = CoursService.getById(event.getId());
        if (cours != null) {
            showEventCours(cours);
        }

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "onEventLongPress event: " + event.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    private void showEventCours(Cours cours) {
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



}
