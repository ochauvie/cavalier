package com.cavalier.model;

import com.cavalier.R;

public enum TypePlanningEvent implements IRefData {

    COURS(R.string.planning_cours, 0),
    COURS_PLANIFIE(R.string.planning_cours_planifie, 0),
    NOTE(R.string.planning_note, 0);

    private final int label;
    private final int flag;

    TypePlanningEvent(int label, int flag) {
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
