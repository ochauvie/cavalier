package com.tankbattle.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * Created by Olivier on 05/09/15.
 */
@Table(name = "Equipe")
public class Equipe extends Model {

    @Column(name = "Nom")
    private String nom;

    @Column(name = "DateCreation")
    private Date dateCreation;

    // Liste des tanks de l'équipe
    public List<Tank> tanks() {
        return getMany(Tank.class, "EquipeTank");
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
}
