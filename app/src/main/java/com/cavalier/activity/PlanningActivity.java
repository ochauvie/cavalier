package com.cavalier.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.cavalier.R;
import com.cavalier.model.Cours;
import com.cavalier.model.Personne;
import com.cavalier.service.CoursService;
import com.cavalier.service.PersonneService;

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

        Personne cavalier = PersonneService.findByNomPrenom("Chauvie", "Olivier");
        coursList = CoursService.getByCavalier(cavalier);

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
                weekViewEvent.setName(cours.getCavalier().getPrenom() + " - " + cours.getMonture().getNom());
                weekViewEvent.setStartTime(deb);
                weekViewEvent.setEndTime(fin);
                weekViewEvent.setColor(Color.RED);
                weekViewEvent.setLocation(cours.getTypeLieu().name());
                events.add(weekViewEvent);
            }
        }

        return events;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "onEventClick event: " + event.getName(), Toast.LENGTH_SHORT).show();

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
}
