package com.cavalier.model;

import com.tankbattle.R;
import com.tankbattle.model.IRefData;

public enum Sexe implements IRefData {

    MASCULIN(R.string.genre_masculin, R.drawable.genre_heavy),
    FEMININ(R.string.genre_feminin, R.drawable.genre_medium);

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
