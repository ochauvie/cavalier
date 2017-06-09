package com.cavalier.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.cavalier.model.PlanningEvent;
import com.cavalier.model.TypeLieu;

import java.util.List;

public class PlanningEventService {

    public static List<PlanningEvent> getAll() {
        return new Select()
                .from(PlanningEvent.class)
                .orderBy("dateDebut DESC")
                .execute();
    }

    public static void deleteAll() {
        new Delete().from(PlanningEvent.class)
                .execute();
    }

    public static PlanningEvent getById(Long id) {
        return new Select()
                .from(PlanningEvent.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static List<PlanningEvent> getByCavalier(Personne personne) {
        return new Select()
                .from(PlanningEvent.class)
                .where("cavalier = ?", personne.getId())
                .orderBy("dateDebut DESC")
                .execute();
    }

    public static List<PlanningEvent> getByMonture(Monture monture) {
        return new Select()
                .from(PlanningEvent.class)
                .where("monture = ?", monture.getId())
                .orderBy("dateDebut DESC")
                .execute();
    }

    public static List<PlanningEvent> getByMoniteur(Personne personne) {
        return new Select()
                .from(PlanningEvent.class)
                .where("moniteur = ?", personne.getId())
                .orderBy("dateDebut DESC")
                .execute();
    }

    public static List<PlanningEvent> getByTypeLieu(TypeLieu typeLieu) {
        return new Select()
                .from(PlanningEvent.class)
                .where("type = ?", typeLieu.name())
                .orderBy("dateDebut DESC")
                .execute();
    }




}
