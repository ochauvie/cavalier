package com.cavalier.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.util.Date;

@Table(name = "carte")
public class Carte extends Model  {

    public static final String ID_CARTE = "ID_CARTE";

    @Expose
    @Column(name = "nom")
    private String nom;

    @Expose
    @Column(name = "cavalier")
    private Personne cavalier;

    @Expose
    @Column(name = "capacite")
    private Integer capacite;

    @Expose
    @Column(name = "pointRestant")
    private Integer pointRestant;

    @Expose
    @Column(name = "dateOuverture")
    private Date dateOuverture;

    @Expose
    @Column(name = "dateCloture")
    private Date dateCloture;



    public Carte() {
    }

    public Carte(String nom, Personne cavalier, Integer capacite, Integer pointRestant, Date dateOuverture, Date dateCloture) {
        this.nom = nom;
        this.cavalier = cavalier;
        this.capacite = capacite;
        this.pointRestant = pointRestant;
        this.dateOuverture = dateOuverture;
        this.dateCloture = dateCloture;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Personne getCavalier() {
        return cavalier;
    }

    public void setCavalier(Personne cavalier) {
        this.cavalier = cavalier;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public Integer getPointRestant() {
        return pointRestant;
    }

    public void setPointRestant(Integer pointRestant) {
        this.pointRestant = pointRestant;
    }

    public Date getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(Date dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public Date getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(Date dateCloture) {
        this.dateCloture = dateCloture;
    }
}

