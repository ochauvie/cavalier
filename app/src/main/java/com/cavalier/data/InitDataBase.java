package com.cavalier.data;

import android.content.Context;
import android.content.ContextWrapper;

import com.cavalier.model.Genre;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.cavalier.model.Sexe;
import com.cavalier.model.TypePersonne;
import com.cavalier.service.MontureService;
import com.cavalier.service.PersonneService;

import java.util.Date;

public class InitDataBase {

    public static final String DEFAULT_IMAGE = "default_tank";

    public static void initPersonnes() {

        PersonneService.deleteAll();

        // Moniteurs
        Personne moniteur = new Personne("Mary", "Katheleen", null, Sexe.FEMININ, TypePersonne.MONITEUR);
        moniteur.save();

        // Cavaliers
        moniteur = new Personne("Chauvie", "Olivier", null, Sexe.MASCULIN, TypePersonne.CAVALIER);
        moniteur.save();

        moniteur = new Personne("Chauvie", "Guillaume", null, Sexe.MASCULIN, TypePersonne.CAVALIER);
        moniteur.save();

        moniteur = new Personne("Chauvie", "Patricia", null, Sexe.FEMININ, TypePersonne.CAVALIER);
        moniteur.save();

    }

    public static void initMontures() {
        MontureService.deleteAll();

        Monture monture = new Monture("Soumoulou", Genre.JUMENT, "Baie", null);
        monture.save();

        monture = new Monture("Cyrano", Genre.ETALON, "Noir", null);
        monture.save();

    }



}

