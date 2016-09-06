package com.cavalier.model;

import com.cavalier.R;
import com.cavalier.model.IRefData;

public enum Genre implements IRefData {

    ETALON(R.string.genre_etalon, R.drawable.genre_heavy),
    HONGRE(R.string.genre_hongre, R.drawable.genre_medium),
    JUMENT(R.string.genre_jument, R.drawable.genre_destroyer),
    POULIN(R.string.genre_poulin, R.drawable.genre_light);

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
