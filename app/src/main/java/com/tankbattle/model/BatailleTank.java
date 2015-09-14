package com.tankbattle.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by olivier on 09/09/15.
 */

@Table(name = "BatailleTank")
public class BatailleTank extends Model{

    @Column(name = "Bataille", onDelete= Column.ForeignKeyAction.CASCADE)
    public Bataille bataille;

    @Column(name = "Tank", onDelete=Column.ForeignKeyAction.CASCADE)
    public Tank tank;

    @Column(name = "PvRestant")
    private int pvRestant;

    @Column(name = "PvRestant")
    private int numEquipe;

    public BatailleTank() {
    }

    public BatailleTank(Bataille bataille, Tank tank, int pvRestant) {
        this.bataille = bataille;
        this.tank = tank;
        this.pvRestant = pvRestant;
    }

    public void addPv(int pvToAdd) {
        this.pvRestant = this.pvRestant + pvToAdd;
    }

    public boolean isDestroyed() {
        if (this.pvRestant<=0) {
            return true;
        }
        return false;
    }

    public Bataille getBataille() {
        return bataille;
    }

    public void setBataille(Bataille bataille) {
        this.bataille = bataille;
    }

    public int getPvRestant() {
        return pvRestant;
    }

    public void setPvRestant(int pvRestant) {
        this.pvRestant = pvRestant;
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }
}
