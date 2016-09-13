package com.cavalier.model;

import java.util.Date;

public class CoursFilter {

    private Personne moniteur;

    private Personne cavalier;

    private Monture monture;

    private TypeLieu typeLieu;

    private Date date;


    public Personne getMoniteur() {
        return moniteur;
    }

    public void setMoniteur(Personne moniteur) {
        this.moniteur = moniteur;
    }

    public Personne getCavalier() {
        return cavalier;
    }

    public void setCavalier(Personne cavalier) {
        this.cavalier = cavalier;
    }

    public Monture getMonture() {
        return monture;
    }

    public void setMonture(Monture monture) {
        this.monture = monture;
    }

    public TypeLieu getTypeLieu() {
        return typeLieu;
    }

    public void setTypeLieu(TypeLieu typeLieu) {
        this.typeLieu = typeLieu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
