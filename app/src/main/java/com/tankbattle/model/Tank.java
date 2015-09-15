package com.tankbattle.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Definition d'un tank
 */
@Table(name = "Tank")
public class Tank extends Model implements Serializable {

    public static final String TANK_ID = "TANK_ID";

    @Column(name = "Nom")
    private String nom;

    @Column(name = "Pv")
    private int pv;

    @Column(name = "Nation")
    private Nation nation;

    @Column(name = "Genre")
    private Genre genre;

    @Column(name = "Image")
    private String image;

    public Tank() {
    }

    public Tank(String nom, int pv, Nation nation, Genre genre, String image) {
        this.nom = nom;
        this.pv = pv;
        this.nation = nation;
        this.genre = genre;
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isDestroyed() {
        if (this.pv<=0) {
            return true;
        }
        return false;
    }


    // Liste des équipes dont le tank appartient
    public List<Equipe> equipes() {
        return getMany(Equipe.class, "EquipeTank");
    }

    // Liste des tanks détruits
    public List<TankVictoires> victoires() {
        return getMany(TankVictoires.class, "Tank");
    }


    @Override
    public String toString() {
        return this.nom;
    }
}
