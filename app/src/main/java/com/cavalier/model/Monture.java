package com.cavalier.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.util.Date;

@Table(name = "cheval")
public class Monture extends Model  {

    public static final String ID_MONTURE = "ID_MONTURE";

    @Expose
    @Column(name = "nom")
    private String nom;

    @Expose
    @Column(name = "genre")
    private Genre genre;

    @Expose
    @Column(name = "race")
    private String race;

    @Expose
    @Column(name = "robe")
    private String robe;

    @Expose
    @Column(name = "dateNaissance")
    private Date dateNaissance;

    @Expose
    @Column(name = "caracteristique")
    private String caracteristique;

    @Column(name = "img")
    private byte[] img;

    public Monture() {
    }

    public Monture(String nom, byte[] img, String caracteristique, Date dateNaissance, String robe, String race, Genre genre) {
        this.nom = nom;
        this.img = img;
        this.caracteristique = caracteristique;
        this.dateNaissance = dateNaissance;
        this.robe = robe;
        this.race = race;
        this.genre = genre;
    }

    public Monture(String nom) {
        this.nom = nom;
    }

    public Monture(String nom, Genre genre, String robe, Date dateNaissance, byte[] img, String race) {
        this.nom = nom;
        this.genre = genre;
        this.robe = robe;
        this.dateNaissance = dateNaissance;
        this.img = img;
        this.race = race;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getRobe() {
        return robe;
    }

    public void setRobe(String robe) {
        this.robe = robe;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(String caracteristique) {
        this.caracteristique = caracteristique;
    }
}
