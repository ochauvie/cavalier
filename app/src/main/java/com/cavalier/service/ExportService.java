package com.cavalier.service;


import android.content.Context;

import com.cavalier.model.Cours;
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

    private boolean addMonture, addPersonne, addCours;
    private Gson gson;


    public ExportService(Context context, boolean addMonture, boolean addPersonne, boolean addCours) {
        this.addMonture = addMonture;
        this.addPersonne = addPersonne;
        this.addCours = addCours;

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
    }

    private void doBackupMonture(String filePath) throws Exception  {
        File myFile = new File(filePath + "AppCavalier_Montures.json");
        myFile.createNewFile();
        FileOutputStream fOut = new FileOutputStream(myFile);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

        List<Monture> montures = MontureService.getAll();

        Type type = new TypeToken<List<Monture>>() {}.getType();
        String json = gson.toJson(montures, type);

        myOutWriter.append(json);
        myOutWriter.close();
        fOut.close();
    }

    private void doBackupPersonne(String filePath) throws Exception  {
        File myFile = new File(filePath + "AppCavalier_Personnes.json");
        myFile.createNewFile();
        FileOutputStream fOut = new FileOutputStream(myFile);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

        List<Personne> personnes = PersonneService.getAll();
        Type type = new TypeToken<List<Monture>>() {}.getType();
        String json = gson.toJson(personnes, type);
        myOutWriter.append(json);

        myOutWriter.close();
        fOut.close();
    }

    private void doBackupCours(String filePath) throws Exception  {
        File myFile = new File(filePath + "AppCavalier_Cours.json");
        myFile.createNewFile();
        FileOutputStream fOut = new FileOutputStream(myFile);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

        List<Cours> coursList = CoursService.getAll();
        Type type = new TypeToken<List<Monture>>() {}.getType();
        String json = gson.toJson(coursList, type);
        myOutWriter.append(json);

        myOutWriter.close();
        fOut.close();
    }
}
