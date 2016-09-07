package com.cavalier.service;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.cavalier.model.Cours;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.cavalier.model.TypePersonne;
import com.tankbattle.model.Equipe;
import com.tankbattle.model.EquipeTank;
import com.tankbattle.model.Tank;

import java.util.List;

/**
 * Service pour les Cours
 */
public class CoursService {

    public static List<Cours> getAll() {
        return new Select()
                .from(Cours.class)
                .orderBy("cavalier ASC")
                .execute();
    }

    public static void deleteAll() {
        new Delete().from(Cours.class)
                .execute();
    }

    public static boolean isPersonneInCours(Personne personne) {
        String critere = personne.getType().name().toLowerCase();
        List<Model> list = new Select()
                .from(Cours.class)
                .where(critere + " = ?", personne.getId())
                .execute();
        if (list!=null && list.size() > 0 ){
            return true;
        }
        return false;
    }

    public static boolean isMontureInCours(Monture monture) {
        List<Model> list = new Select()
                .from(Cours.class)
                .where("monture = ?", monture.getId())
                .execute();
        if (list!=null && list.size() > 0 ){
            return true;
        }
        return false;
    }


}
