package com.cavalier.listner;

import com.cavalier.model.Carte;
import com.cavalier.model.CarteItem;


public interface CarteListener {

    void onUpdateCarte(Carte item, int position);

    void onDeleteCarte(Carte item, int position);

    void onUpdateCarteItem(CarteItem item, int position);


}
