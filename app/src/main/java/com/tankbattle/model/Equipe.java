package com.tankbattle.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Olivier on 05/09/15.
 */
@Table(name = "Equipe")
public class Equipe extends Model implements Serializable{

    public static final String EQUIPE_ID = "EQUIPE_ID";

    @Column(name = "Nom")
    private String nom;

    @Column(name = "DateCreation")
    private Date dateCreation;

    // Liste des tanks de l'équipe
    public List<EquipeTank> tanks() {
        return getMany(EquipeTank.class, "Equipe");
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

    @Override
    public String toString() {
        return this.nom;
    }

    public boolean isTankOfEquipe(Tank tank) {
        if (tanks() !=null) {
            for (EquipeTank equipeTank:tanks()) {
                if (equipeTank.getTank().getId().equals(tank.getId())) {
                    return true;
                }
            }
        }
        return false;
    }


}
