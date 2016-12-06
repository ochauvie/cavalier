package com.cavalier.model;

import com.cavalier.R;

public enum TypeEvenement implements IRefData {

    FERRAGE(R.string.type_evenement_ferrage, R.drawable.event),
    PARAGE(R.string.type_evenement_parage, R.drawable.event),
    VACCIN(R.string.type_evenement_vaccin, R.drawable.vaccin),
    VERMIFUGE(R.string.type_evenement_vermifuge, R.drawable.vermifuge),
    DENT(R.string.type_evenement_dent, R.drawable.dent),
    OSTHEOPATHIE(R.string.type_evenement_ostheopathie, R.drawable.ostheopathe),
    GOUDRON(R.string.type_evenement_goudron, R.drawable.goudron),
    AUTE(R.string.type_evenement_autre, R.drawable.autre);

    private final int label;
    private final int flag;

    TypeEvenement(int label, int flag) {
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
