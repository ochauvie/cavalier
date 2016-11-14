package com.cavalier.model;

import com.cavalier.R;
import com.cavalier.model.IRefData;

public enum TypeLieu implements IRefData {

    CARRIERE(R.string.type_lieu_carriere, 0),
    MANEGE(R.string.type_lieu_manege, 0),
    EXTERIEUR(R.string.type_lieu_exterieur, 0),
    ECURIE(R.string.type_lieu_ecurie, 0),
    CLUB_HOUSE(R.string.type_lieu_club_house, 0),
    AUTRE(R.string.type_lieu_autre, 0),;

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
