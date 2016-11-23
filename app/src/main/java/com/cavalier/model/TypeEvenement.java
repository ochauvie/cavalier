package com.cavalier.model;

import com.cavalier.R;

public enum TypeEvenement implements IRefData {

    FERRAGE(R.string.type_evenement_ferrage, 0),
    PARAGE(R.string.type_evenement_parage, 0),
    VACCIN(R.string.type_evenement_vaccin, 0),
    AUTE(R.string.type_evenement_autre, 0);

    private final int label;
    private final int flag;

    private TypeEvenement(int label, int flag) {
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
