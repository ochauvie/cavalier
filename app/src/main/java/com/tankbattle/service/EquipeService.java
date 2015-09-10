package com.tankbattle.service;

import com.activeandroid.query.Select;
import com.tankbattle.model.Equipe;

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


}
