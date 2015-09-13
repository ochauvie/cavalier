package com.tankbattle.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * Created by olivier on 09/09/15.
 */

@Table(name = "EquipeTank")
public class EquipeTank extends Model{

    public EquipeTank() {
    }

    public EquipeTank(Equipe equipe, Tank tank) {
        this.equipe = equipe;
        this.tank = tank;
    }

    @Column(name = "Equipe", onDelete= Column.ForeignKeyAction.CASCADE)
    public Equipe equipe;

    @Column(name = "Tank", onDelete=Column.ForeignKeyAction.CASCADE)
    public Tank tank;


    public Equipe getEquipe() {
        return equipe;
    }

    public Tank getTank() {
        return tank;
    }
}
