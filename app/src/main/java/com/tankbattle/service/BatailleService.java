package com.tankbattle.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.tankbattle.model.Bataille;
import com.tankbattle.model.Equipe;

import java.util.List;

/**
 * Service pour les Batailles
 */
public class BatailleService {

    public static List<Bataille> getAllBatailles() {
        return new Select()
                .from(Bataille.class)
                .orderBy("Nom ASC")
                .execute();
    }


    public static Bataille getCurrentBataille() {
        List<Bataille> bats = getAllBatailles();
        if (bats!=null) {
            for (Bataille bat:bats) {
                if (bat.isFinished()==0) {
                    return bat;
                }
            }
        }
        return null;
    }

    public static void deleteAllBatailles() {
        new Delete().from(Bataille.class)
                .execute();
    }

}
