package com.tankbattle.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * Created by olivier on 09/09/15.
 */

@Table(name = "TankVictoires")
public class TankVictoires extends Model{

    public TankVictoires() {
    }

    public TankVictoires(String nomBataille, Tank tankDetruit, Tank tankVictorieux) {
        this.tankDetruit = tankDetruit;
        this.tankVictorieux = tankVictorieux;
        this.nomBataille = nomBataille;
    }

    @Column(name = "TankDetruit", onDelete= Column.ForeignKeyAction.CASCADE)
    public Tank tankDetruit;

    @Column(name = "Tank", onDelete=Column.ForeignKeyAction.CASCADE)
    public Tank tankVictorieux;

    @Column(name = "NomBataille")
    public String nomBataille;

    public Tank getTankDetruit() {
        return tankDetruit;
    }

    public void setTankDetruit(Tank tankDetruit) {
        this.tankDetruit = tankDetruit;
    }

    public Tank getTankVictorieux() {
        return tankVictorieux;
    }

    public void setTankVictorieux(Tank tankVictorieux) {
        this.tankVictorieux = tankVictorieux;
    }

    public String getNomBataille() {
        return nomBataille;
    }

    public void setNomBataille(String nomBataille) {
        this.nomBataille = nomBataille;
    }
}
