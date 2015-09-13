package com.tankbattle.adapter;

/**
 * Created by Olivier on 13/09/15.
 */
public class TagTank {

    private int position;
    private String origine;

    public TagTank(int position, String origine) {
        this.position = position;
        this.origine = origine;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }
}
