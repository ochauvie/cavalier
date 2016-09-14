package com.cavalier.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
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
    @Column(name = "robe")
    private String robe;

    @Expose
    @Column(name = "dateNaissance")
    private Date dateNaissance;

    public Monture() {
    }

    public Monture(String nom, Genre genre, String robe, Date dateNaissance) {
        this.nom = nom;
        this.genre = genre;
        this.robe = robe;
        this.dateNaissance = dateNaissance;
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
}
