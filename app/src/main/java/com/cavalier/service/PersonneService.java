package com.cavalier.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.cavalier.model.Personne;
import com.cavalier.model.TypePersonne;
import com.tankbattle.model.Nation;
import com.tankbattle.model.Tank;
import com.tankbattle.model.TankVictoires;

import java.util.List;

/**
 * Service pour les Personne
 */
public class PersonneService {

    public static List<Personne> getAll() {
        return new Select()
                .from(Personne.class)
                .orderBy("nom ASC")
                .execute();
    }

    public static List<Personne> findByType(TypePersonne type) {
        return new Select()
                .from(Personne.class)
                .where("type = ?", type.name())
                .orderBy("nom ASC")
                .execute();
    }

    public static Personne getById(long id) {
        return new Select()
                .from(Personne.class)
                .where("Id = ?", id)
                .executeSingle();
    }


    public static void deleteAllTanks() {
        new Delete().from(Personne.class)
                    .execute();
    }

}
