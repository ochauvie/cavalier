package com.cavalier.listner;

import com.cavalier.model.Cours;


public interface CoursListener {

    public void onShowObservation(Cours item, int position);

    public void onDelete(Cours item, int position);

    public void onSelectCavalier(Cours item, int position);

    public void onSelectMonture(Cours item, int position);

    public void onSelectDate(Cours item, int position);

}
