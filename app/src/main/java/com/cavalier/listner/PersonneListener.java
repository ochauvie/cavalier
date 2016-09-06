package com.cavalier.listner;

import com.cavalier.model.Personne;


public interface PersonneListener {

    public void onClick(Personne item, int position);

    public void onDelete(Personne item);
}
