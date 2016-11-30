package com.cavalier.listner;

import com.cavalier.model.Cours;


public interface CoursListener {

    void onShowObservation(Cours item, int position);

    void onDelete(Cours item, int position);

    void onSelectCavalier(Cours item, int position);

    void onSelectMonture(Cours item, int position);

    void onSelectMoniteur(Cours item, int position);

    void onSelectLieu(Cours item, int position);

    void onSelectDate(Cours item, int position);

}
