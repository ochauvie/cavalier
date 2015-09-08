package com.tankbattle.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.tankbattle.model.Nation;
import com.tankbattle.model.Tank;

import java.util.List;

/**
 * Service pour les Tank
 */
public class TankService {

    public static List<Tank> getAllTanks() {
        return new Select()
                .from(Tank.class)
                .orderBy("Nom ASC")
                .execute();
    }

    public static List<Tank> findTanksByNation(Nation nation) {
        return new Select()
                .from(Tank.class)
                .where("Nation = ?", nation.name())
                .orderBy("Nom ASC")
                .execute();
    }


    public static void deleteAllTanks() {
        new Delete().from(Tank.class)
                    .execute();
    }

}
