package com.cavalier.model;

import com.cavalier.R;

public enum Galop implements IRefData {

    ZERO(R.string.galop0, 0),
    UN(R.string.galop1, 0),
    DEUX(R.string.galop2, 0),
    TROIS(R.string.galop3, 0),
    QUATRE(R.string.galop4, 0),
    CINQ(R.string.galop5, 0),
    SIX(R.string.galop6, 0),
    SEPT(R.string.galop7, 0);

    private final int label;
    private final int flag;

    Galop(int label, int flag) {
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
