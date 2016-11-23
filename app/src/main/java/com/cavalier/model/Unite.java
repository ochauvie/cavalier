package com.cavalier.model;

import com.cavalier.R;

public enum Unite implements IRefData {

    KG(R.string.unite_kg, 0),
    LITRE(R.string.unite_litre, 0);

    private final int label;
    private final int flag;

    private Unite(int label, int flag) {
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
