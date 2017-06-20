package com.cavalier.model;


public class RefData implements IRefData {

    private int label;
    private int img;

    public RefData(int label, int img) {
        this.label = label;
        this.img = img;
    }

    @Override
    public int getLabel() {
        return label;
    }

    @Override
    public int getFlag() {
        return img;
    }
}