package com.cavalier.listner;

import com.cavalier.model.Carte;
import com.cavalier.model.CarteItem;
import com.cavalier.model.Personne;


public interface PersonneListener {

    void onClick(Personne item, int position);

}
