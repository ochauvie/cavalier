package com.cavalier.listner;


import com.cavalier.model.EvenementMonture;
import com.cavalier.model.Monture;

public interface MontureListener {

    void onClick(Monture item, int position);

    void onDeleteEvenement(EvenementMonture item, int position);

}
