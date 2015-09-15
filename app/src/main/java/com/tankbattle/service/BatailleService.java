package com.tankbattle.service;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.tankbattle.model.Bataille;
import com.tankbattle.model.Equipe;

import java.util.ArrayList;
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

    public static boolean isEquipeInBataille(Equipe equipe) {
        List<Model> bts = new ArrayList<Model>();
        bts.addAll(new Select()
                .from(Bataille.class)
                .where("Equipe1 = ?", equipe.getId())
                .execute());
        bts.addAll(new Select()
                .from(Bataille.class)
                .where("Equipe2 = ?", equipe.getId())
                .execute());
        if (bts.size()>0 ){
            return true;
        }
        return false;
    }

}
