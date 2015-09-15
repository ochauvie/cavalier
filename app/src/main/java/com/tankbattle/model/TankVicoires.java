package com.tankbattle.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * Created by olivier on 09/09/15.
 */

@Table(name = "TankVicoires")
public class TankVicoires extends Model{

    public TankVicoires() {
    }

    public TankVicoires(Tank tank, Tank victoire) {
        this.tank = tank;
        this.victoire = victoire;
    }

    @Column(name = "Tank", onDelete= Column.ForeignKeyAction.CASCADE)
    public Tank tank;

    @Column(name = "Victoire", onDelete=Column.ForeignKeyAction.CASCADE)
    public Tank victoire;

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public Tank getVictoire() {
        return victoire;
    }

    public void setVictoire(Tank victoire) {
        this.victoire = victoire;
    }
}
