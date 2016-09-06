package com.cavalier.model;

import com.tankbattle.R;
import com.tankbattle.model.IRefData;

public enum TypeLieu implements IRefData {

    ECURIE(R.string.type_lieu_ecurie, R.drawable.genre_heavy),
    CARRIERE(R.string.type_lieu_carriere, R.drawable.genre_heavy),
    MANEGE(R.string.type_lieu_manege, R.drawable.genre_medium),
    EXTERIEUR(R.string.type_lieu_exterieur, R.drawable.genre_destroyer),
    AUTRE(R.string.type_lieu_autre, R.drawable.genre_destroyer),;

    private final int label;
    private final int flag;

    private TypeLieu(int label, int flag) {
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
