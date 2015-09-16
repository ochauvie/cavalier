package com.tankbattle.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.tankbattle.model.Nation;
import com.tankbattle.model.Tank;
import com.tankbattle.model.TankVictoires;

import java.util.List;

/**
 * Service pour les Tank
 */
public class TankService {

    public static List<Tank> getAllTanks() {
        return new Select()
                .from(Tank.class)
                .orderBy("Nation, Genre, Nom ASC")
                .execute();
    }

    public static List<Tank> findTanksByNation(Nation nation) {
        return new Select()
                .from(Tank.class)
                .where("Nation = ?", nation.name())
                .orderBy("Nation, Genre, Nom ASC")
                .execute();
    }

    public static Tank getTankById(long id) {
        return new Select()
                .from(Tank.class)
                .where("Id = ?", id)
                .executeSingle();
    }


    public static void deleteAllTanks() {
        new Delete().from(Tank.class)
                    .execute();
    }

    public static void deleteAllTankVictoires() {
        new Delete().from(TankVictoires.class)
                .execute();
    }

    public static List<TankVictoires> findDefaites(Tank tank) {
        return new Select()
                .from(TankVictoires.class)
                .where("TankDetruit = ?", tank.getId())
                .orderBy("NomBataille ASC")
                .execute();
    }

}
