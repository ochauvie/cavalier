package com.cavalier.model;

import com.cavalier.R;
import com.cavalier.model.IRefData;

public enum Genre implements IRefData {

    ETALON(R.string.genre_etalon, 0),
    HONGRE(R.string.genre_hongre, 0),
    JUMENT(R.string.genre_jument, 0),
    POULIN(R.string.genre_poulin, 0),
    POULICHE(R.string.genre_pouliche, 0);

    private final int label;
    private final int flag;

    private Genre(int label, int flag) {
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
