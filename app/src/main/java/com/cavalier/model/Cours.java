package com.cavalier.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.util.Date;

@Table(name = "cours")
public class Cours  extends Model  {

    @Expose
    @Column(name = "moniteur")
    private Personne moniteur;

    @Expose
    @Column(name = "cavalier")
    private Personne cavalier;

    @Expose
    @Column(name = "monture")
    private Monture monture;

    @Expose
    @Column(name = "typeLieu")
    private TypeLieu typeLieu;

    @Expose
    @Column(name = "date")
    private Date date;

    @Expose
    @Column(name = "duree")
    private Integer duree;

    @Expose
    @Column(name = "observation")
    private String observation;

    public Cours() {
    }

    public Cours(Personne moniteur, Personne cavalier, Monture monture, TypeLieu typeLieu, Date date, Integer duree, String observation) {
        this.moniteur = moniteur;
        this.cavalier = cavalier;
        this.monture = monture;
        this.typeLieu = typeLieu;
        this.date = date;
        this.duree = duree;
        this.observation = observation;
    }

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

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Monture getMonture() {
        return monture;
    }

    public void setMonture(Monture monture) {
        this.monture = monture;
    }
}
