package com.cavalier.service;

import android.content.Context;

import com.cavalier.model.Cours;
import com.cavalier.model.EvenementMonture;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.cavalier.model.TypePersonne;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImportService {
    private final Gson gson;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public ImportService(Context context) {
        gson = new GsonBuilder().serializeNulls()
                .setExclusionStrategies(new MyExclusionStrategy(null))
                .setDateFormat("dd/MM/yyyy")
                .registerTypeAdapter(Date.class, new JsonDeserializer()
                {
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                            throws JsonParseException
                    {
                        try
                        {
                            System.out.println(json.getAsString());
                            return sdf.parse((json).getAsString());
                        }
                        catch (ParseException e)
                        {
                            throw new JsonParseException(e);
                        }
                    }
                }).create();

    }

    public String importMonture(File file) {
        try {
            String json = getJson(file);
            Monture[] montures = gson.fromJson(json, Monture[].class);
            if (montures!=null) {
                for (Monture monture:montures) {
                    monture.save();
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    public String importEvenement(File file) {
        try {
            String json = getJson(file);
            EvenementMonture[] events = gson.fromJson(json, EvenementMonture[].class);
            if (events!=null) {
                for (EvenementMonture event:events) {

                    Monture monture = MontureService.findByNom(event.getMonture().getNom());
                    if (monture != null) {
                        event.setMonture(monture);
                        event.save();
                    }
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    public String importPersonne(File file) {
        try {
            String json = getJson(file);
            Personne[] personnes = gson.fromJson(json, Personne[].class);
            if (personnes!=null) {
                for (Personne personne:personnes) {
                    personne.save();
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    public String importCours(File file) {
        try {
            String json = getJson(file);
            Cours[] coursList = gson.fromJson(json, Cours[].class);
            if (coursList!=null) {
                for (Cours cours:coursList) {

                    int ctrl = 0;
                    Monture monture = MontureService.findByNom(cours.getMonture().getNom());
                    if (monture != null) {
                        cours.setMonture(monture);
                        ctrl++;
                    }
                    Personne cavalier = PersonneService.findByNomPrenom(cours.getCavalier().getNom(), cours.getCavalier().getPrenom());
                    if (cavalier != null && TypePersonne.CAVALIER.equals(cavalier.getType())) {
                        cours.setCavalier(cavalier);
                        ctrl++;
                    }
                    Personne moniteur = PersonneService.findByNomPrenom(cours.getMoniteur().getNom(), cours.getMoniteur().getPrenom());
                    if (moniteur != null && TypePersonne.MONITEUR.equals(moniteur.getType())) {
                        cours.setMoniteur(moniteur);
                        ctrl++;
                    }
                    if (ctrl == 3) {
                        cours.save();
                    }
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }


    private String getJson(File file) throws Exception {
        String json = "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            json = json + line;
        }
        br.close();
        return json;
    }


}