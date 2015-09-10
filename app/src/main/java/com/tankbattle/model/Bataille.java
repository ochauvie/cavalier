package com.tankbattle.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * Created by olivier on 09/09/15.
 */
@Table(name = "Bataille")
public class Bataille extends Model {

    @Column(name = "Nom")
    private String nom;

    @Column(name = "DateCreation")
    private Date dateCreation;

    @Column(name = "DateFin")
    private Date dateFin;

    private Equipe equipeVert;

    private Equipe equipeRouge;


    // Liste des tanks de la bataille
    public List<BatailleTank> tanks() {
        return getMany(BatailleTank.class, "Bataille");
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isTerminate() {
        if (dateFin != null) {
            return true;
        }
        return false;
    }

}
