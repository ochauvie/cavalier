package com.cavalier.model;

import com.cavalier.R;
import com.cavalier.model.IRefData;

public enum Sexe implements IRefData {

    MASCULIN(R.string.genre_masculin, 0),
    FEMININ(R.string.genre_feminin, 0);

    private final int label;
    private final int flag;

    private Sexe(int label, int flag) {
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
