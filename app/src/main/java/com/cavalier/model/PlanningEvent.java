package com.cavalier.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.util.Date;

@Table(name = "planningEvent")
public class PlanningEvent extends Model  {

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
    @Column(name = "type")
    private TypeLieu typeLieu;

    @Expose
    @Column(name = "dateDebut")
    private Date dateDebut;

    @Expose
    @Column(name = "dateFin")
    private Date dateFin;

    @Expose
    @Column(name = "observation")
    private String observation;

    public PlanningEvent() {
    }

    public PlanningEvent(Personne moniteur, Personne cavalier, Monture monture, TypeLieu typeLieu, Date dateDebut, Date dateFin, String observation) {
        this.moniteur = moniteur;
        this.cavalier = cavalier;
        this.monture = monture;
        this.typeLieu = typeLieu;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
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
