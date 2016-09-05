package com.cavalier.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;

@Table(name = "Cheval")
public class Cheval extends Model implements Serializable {


    @Column(name = "nom")
    private String nom;

    @Column(name = "genre")
    private Genre genre;

    @Column(name = "robe")
    private String robe;

    @Column(name = "dateNaissance")
    private Date dateNaissance;

    public Cheval(String nom, Genre genre, String robe, Date dateNaissance) {
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
