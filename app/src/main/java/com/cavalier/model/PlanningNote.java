package com.cavalier.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.util.Date;

@Table(name = "planningNote")
public class PlanningNote extends Model  {

    @Expose
    @Column(name = "dateDebut")
    private Date dateDebut;

    @Expose
    @Column(name = "dateFin")
    private Date dateFin;

    @Expose
    @Column(name = "titre")
    private String titre;

    @Expose
    @Column(name = "note")
    private String note;


    public PlanningNote() {
    }

    public PlanningNote(Date dateDebut, Date dateFin, String titre, String note) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.titre = titre;
        this.note = note;
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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
