package com.cavalier.service;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.cavalier.model.Cours;
import com.cavalier.model.CoursFilter;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.cavalier.model.TypeLieu;

import java.util.Date;
import java.util.List;

/**
 * Service pour les Cours
 */
public class CoursService {

    public static List<Cours> getAll() {
        return new Select()
                .from(Cours.class)
                .orderBy("date DESC")
                .execute();
    }

    public static void deleteAll() {
        new Delete().from(Cours.class)
                .execute();
    }

    public static List<Cours> getByCavalier(Personne personne) {
        return new Select()
                .from(Cours.class)
                .where("cavalier = ?", personne.getId())
                .orderBy("date DESC")
                .execute();
    }

    public static List<Cours> getByMonture(Monture monture) {
        return new Select()
                .from(Cours.class)
                .where("monture = ?", monture.getId())
                .orderBy("date DESC")
                .execute();
    }

    public static List<Cours> getByMoniteur(Personne personne) {
        return new Select()
                .from(Cours.class)
                .where("moniteur = ?", personne.getId())
                .orderBy("date DESC")
                .execute();
    }

    public static List<Cours> getByTypeLieu(TypeLieu typeLieu) {
        return new Select()
                .from(Cours.class)
                .where("typeLieu = ?", typeLieu.name())
                .orderBy("date DESC")
                .execute();
    }

    public static List<Cours> getByDate(Date date) {
        return new Select()
                .from(Cours.class)
                .where("date = ?", date.getTime())
                .orderBy("date DESC")
                .execute();
    }

    public static boolean isPersonneInCours(Personne personne) {
        String critere = personne.getType().name().toLowerCase();
        List<Model> list = new Select()
                .from(Cours.class)
                .where(critere + " = ?", personne.getId())
                .execute();
        return list != null && list.size() > 0;
    }

    public static boolean isMontureInCours(Monture monture) {
        List<Model> list = new Select()
                .from(Cours.class)
                .where("monture = ?", monture.getId())
                .execute();
        return list != null && list.size() > 0;
    }

    public static List<Cours> getByFilter(CoursFilter coursFilter) {
        List<Cours> coursList = getAll();
        for (int i = coursList.size() - 1; i >= 0 ; i--) {
            Cours cours = coursList.get(i);
            boolean toRemove = false;
            if (coursFilter.getCavalierId() != null && !cours.getCavalier().getId().equals(coursFilter.getCavalierId())) {
                toRemove = true;
            }
            if (coursFilter.getMoniteurId() != null && !cours.getMoniteur().getId().equals(coursFilter.getMoniteurId())) {
                toRemove = true;
            }
            if (coursFilter.getMontureId() != null && !cours.getMonture().getId().equals(coursFilter.getMontureId())) {
                toRemove = true;
            }
            if (coursFilter.getStartDate() != null && coursFilter.getStartDate().after(cours.getDate())) {
                toRemove = true;
            }
            if (coursFilter.getEndDate() != null && coursFilter.getEndDate().before(cours.getDate())) {
                toRemove = true;
            }
            if (toRemove) {
                coursList.remove(cours);
            }
        }
        return coursList;
    }

}
