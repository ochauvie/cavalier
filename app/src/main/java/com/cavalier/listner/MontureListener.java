package com.cavalier.listner;


import com.cavalier.model.EvenementMonture;
import com.cavalier.model.Monture;

public interface MontureListener {

    public void onClick(Monture item, int position);

    public void onDeleteEvenement(EvenementMonture item, int position);

}
