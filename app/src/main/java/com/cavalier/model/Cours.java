package com.cavalier.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;

@Table(name = "cours")
public class Cours  extends Model implements Serializable {

    @Column(name = "moniteur")
    private Personne moniteur;

    @Column(name = "cavalier")
    private Personne cavalier;

    @Column(name = "monture")
    private Monture monture;

    @Column(name = "lieu")
    private Lieu lieu;

    @Column(name = "date")
    private Date date;

    @Column(name = "duree")
    private Integer duree;

    public Cours(Personne moniteur, Personne cavalier, Monture monture, Lieu lieu, Date date, Integer duree) {
        this.moniteur = moniteur;
        this.cavalier = cavalier;
        this.monture = monture;
        this.lieu = lieu;
        this.date = date;
        this.duree = duree;
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

    public Monture getCheval() {
        return monture;
    }

    public void setCheval(Monture monture) {
        this.monture = monture;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
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
}
