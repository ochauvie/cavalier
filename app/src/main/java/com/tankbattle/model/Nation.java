package com.tankbattle.model;


import com.tankbattle.R;

public enum Nation {

    FR(R.string.nation_fr, R.drawable.fr),
    DE(R.string.nation_de, R.drawable.de),
    RU(R.string.nation_ru, R.drawable.ru),
    US(R.string.nation_us, R.drawable.us),
    EN(R.string.nation_en, R.drawable.england),
    IT(R.string.nation_it, R.drawable.it),
    JP(R.string.nation_jp, R.drawable.jp);

    private final int label;
    private final int flag;

    private Nation(int label, int flag) {
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
