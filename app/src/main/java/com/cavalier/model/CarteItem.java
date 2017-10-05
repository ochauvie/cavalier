package com.cavalier.model;



public class CarteItem {


    private int order;
    private boolean check;

    public CarteItem(int order, boolean check) {
        this.order = order;
        this.check = check;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

