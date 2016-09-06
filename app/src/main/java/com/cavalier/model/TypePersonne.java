package com.cavalier.model;

import com.cavalier.R;
import com.cavalier.model.IRefData;

public enum TypePersonne implements IRefData {

    MONITEUR(R.string.type_personne_moniteur, R.drawable.genre_heavy),
    CAVALIER(R.string.type_personne_cavalier, R.drawable.genre_medium);

    private final int label;
    private final int flag;

    private TypePersonne(int label, int flag) {
        this.label = label;
        this.flag = flag;
    }

    public int getLabel() {
        return label;
    }

    public int getFlag() {
        return flag;
    }
}
