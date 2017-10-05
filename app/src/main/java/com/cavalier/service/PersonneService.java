package com.cavalier.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.cavalier.model.Carte;
import com.cavalier.model.Personne;
import com.cavalier.model.TypePersonne;

import java.util.List;

/**
 * Service pour les Personnes
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

    public static Personne findByNomPrenom(String nom, String prenom) {
        return new Select()
                .from(Personne.class)
                .where("nom = ?", nom)
                .where("prenom = ?", prenom)
                .executeSingle();
    }


    public static void deleteAll() {
        new Delete().from(Personne.class)
                    .execute();
    }

    public static List<Carte> findCarteByPersonneId(Long idPersonne) {
        return new Select()
                .from(Carte.class)
                .where("cavalier = ?", idPersonne)
                .orderBy("dateOuverture DESC")
                .execute();
    }


}
