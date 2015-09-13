package com.tankbattle.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.tankbattle.model.Equipe;
import com.tankbattle.model.Tank;

import java.util.List;

/**
 * Service pour les Tank
 */
public class EquipeService {

    public static List<Equipe> getAllEquipes() {
        return new Select()
                .from(Equipe.class)
                .orderBy("Nom ASC")
                .execute();
    }

    public static void deleteAllEquipes() {
        new Delete().from(Equipe.class)
                .execute();
    }


}
