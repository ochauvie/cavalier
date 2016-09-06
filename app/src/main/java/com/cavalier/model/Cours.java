package com.cavalier.model;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name = "cours")
public class Cours {

    @Column(name = "moniteur")
    private Personne moniteur;

    @Column(name = "cavalier")
    private Personne cavalier;

    @Column(name = "cheval")
    private Cheval cheval;

    @Column(name = "lieu")
    private Lieu lieu;

    @Column(name = "date")
    private Date date;

    @Column(name = "duree")
    private Integer duree;

    public Cours(Personne moniteur, Personne cavalier, Cheval cheval, Lieu lieu, Date date, Integer duree) {
        this.moniteur = moniteur;
        this.cavalier = cavalier;
        this.cheval = cheval;
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

    public Cheval getCheval() {
        return cheval;
    }

    public void setCheval(Cheval cheval) {
        this.cheval = cheval;
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
