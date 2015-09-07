package com.tankbattle.model;

import com.tankbattle.R;

public enum Genre {

    LOURD(R.string.genre_lourd, R.drawable.genre_lourd_rouge),
    MOYEN(R.string.genre_moyen, R.drawable.genre_lourd_rouge),
    LEGER(R.string.genre_leger, R.drawable.genre_lourd_rouge),
    ANTI_CHAR(R.string.genre_anti_char, R.drawable.genre_lourd_rouge),
    CAM(R.string.genre_cam, R.drawable.genre_lourd_rouge);

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
