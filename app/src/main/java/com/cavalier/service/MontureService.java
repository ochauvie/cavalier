package com.cavalier.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.cavalier.model.Genre;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.cavalier.model.TypePersonne;

import java.util.List;

/**
 * Service pour les Montures
 */
public class MontureService {

    public static List<Monture> getAll() {
        return new Select()
                .from(Monture.class)
                .orderBy("nom ASC")
                .execute();
    }

    public static List<Monture> findByGenre(Genre genre) {
        return new Select()
                .from(Monture.class)
                .where("genre = ?", genre.name())
                .orderBy("nom ASC")
                .execute();
    }

    public static Monture findByNom(String nom) {
        return new Select()
                .from(Monture.class)
                .where("nom = ?", nom)
                .executeSingle();
    }

    public static Monture getById(long id) {
        return new Select()
                .from(Monture.class)
                .where("Id = ?", id)
                .executeSingle();
    }


    public static void deleteAll() {
        new Delete().from(Monture.class)
                    .execute();
    }

}
