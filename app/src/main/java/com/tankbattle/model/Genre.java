package com.tankbattle.model;

import com.tankbattle.R;

public enum Genre implements IRefData {

    HEAVY(R.string.genre_lourd, R.drawable.genre_heavy),
    MEDIUM(R.string.genre_moyen, R.drawable.genre_medium),
    LIGHT(R.string.genre_leger, R.drawable.genre_light),
    DESTROYER(R.string.genre_anti_char, R.drawable.genre_destroyer),
    ARTILLERY(R.string.genre_cam, R.drawable.genre_artillery);

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
