package com.cavalier.service;

import android.content.Context;

import com.cavalier.model.Monture;
import com.cavalier.model.Personne;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImportService {
    private Gson gson;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

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
                            return sdf.parse(((JsonPrimitive) json).getAsString());
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