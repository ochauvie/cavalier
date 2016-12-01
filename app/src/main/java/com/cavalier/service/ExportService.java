package com.cavalier.service;


import android.content.Context;

import com.cavalier.model.Cours;
import com.cavalier.model.EvenementMonture;
import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.List;

public class ExportService {

    private boolean addMonture, addPersonne, addCours, addEvenements;
    private Gson gson;


    public ExportService(Context context, boolean addMonture, boolean addPersonne, boolean addCours, boolean addEvenements) {
        this.addMonture = addMonture;
        this.addPersonne = addPersonne;
        this.addCours = addCours;
        this.addEvenements = addEvenements;

        gson = new GsonBuilder().serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .setExclusionStrategies(new MyExclusionStrategy(null))
                .setDateFormat("dd/MM/yyyy")
                .create();
    }

    public void doBackup(String filePath) throws Exception {
        if (addMonture) {
            doBackupMonture(filePath);
        }
        if (addPersonne) {
            doBackupPersonne(filePath);
        }
        if (addCours) {
            doBackupCours(filePath);
        }
        if (addEvenements) {
            doBackupEvenements(filePath);
        }
    }

    private void doBackupMonture(String filePath) throws Exception  {
        File myFile = new File(filePath + "AppCavalier_Montures.json");
        if (myFile.createNewFile()) {
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            try {
                List<Monture> montures = MontureService.getAll();

                Type type = new TypeToken<List<Monture>>() {
                }.getType();
                String json = gson.toJson(montures, type);
                myOutWriter.append(json);
            } finally {
                myOutWriter.close();
                fOut.close();
            }
        }
    }

    private void doBackupPersonne(String filePath) throws Exception  {
        File myFile = new File(filePath + "AppCavalier_Personnes.json");
        if (myFile.createNewFile()) {
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

            try {
                List<Personne> personnes = PersonneService.getAll();
                Type type = new TypeToken<List<Monture>>() {
                }.getType();
                String json = gson.toJson(personnes, type);
                myOutWriter.append(json);
            } finally {
                myOutWriter.close();
                fOut.close();
            }
        }
    }

    private void doBackupCours(String filePath) throws Exception  {
        File myFile = new File(filePath + "AppCavalier_Cours.json");
        if (myFile.createNewFile()) {
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            try {
                List<Cours> coursList = CoursService.getAll();

                // Remove img to light export
                for (Cours cours : coursList) {
                    cours.getMonture().setImg(null);
                    cours.getCavalier().setImg(null);
                    cours.getMoniteur().setImg(null);
                }

                Type type = new TypeToken<List<Monture>>() {
                }.getType();
                String json = gson.toJson(coursList, type);
                myOutWriter.append(json);

            } finally {
                myOutWriter.close();
                fOut.close();
            }
        }
    }

    private void doBackupEvenements(String filePath) throws Exception  {
        File myFile = new File(filePath + "AppCavalier_Soins.json");
        if (myFile.createNewFile()) {
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            try {
                Type type = new TypeToken<List<EvenementMonture>>() {
                }.getType();

                List<Monture> montures = MontureService.getAll();
                if (montures != null) {
                    for (Monture monture:montures) {
                        List<EvenementMonture> events = MontureService.findEvenementByMonture(monture);

                        if (events != null && events.size()>0) {
                            String json = gson.toJson(events, type);
                            myOutWriter.append(json);
                        }
                    }
                }
            } finally {
                myOutWriter.close();
                fOut.close();
            }
        }
    }
}
