package com.cavalier.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.util.Date;

@Table(name = "evenement")
public class EvenementMonture extends Model  {

    @Expose
    @Column(name = "monture")
    private Monture monture;

    @Expose
    @Column(name = "type")
    private TypeEvenement type;

    @Expose
    @Column(name = "date")
    private Date date;

    @Expose
    @Column(name = "observation")
    private String observation;

    public EvenementMonture() {
    }

    public EvenementMonture(Monture monture, TypeEvenement type, Date date, String observation) {
        this.monture = monture;
        this.type = type;
        this.date = date;
        this.observation = observation;
    }

    public Monture getMonture() {
        return monture;
    }

    public void setMonture(Monture monture) {
        this.monture = monture;
    }

    public TypeEvenement getType() {
        return type;
    }

    public void setType(TypeEvenement type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
