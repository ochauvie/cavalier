package com.cavalier.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.util.Date;

@Table(name = "personne")
public class Personne extends Model  {

    public static final String ID_PERSONNE = "ID_PERSONNE";
    public static final String TYPE_PERSONNE = "TYPE_PERSONNE";

    @Expose
    @Column(name = "nom")
    private String nom;

    @Expose
    @Column(name = "prenom")
    private String prenom;

    @Expose
    @Column(name = "dateNaissance")
    private Date dateNaissance;

    @Expose
    @Column(name = "sexe")
    private Sexe sexe;

    @Expose
    @Column(name = "type")
    private TypePersonne type;

    @Column(name = "img")
    private byte[] img;

    @Expose
    @Column(name = "galop")
    private Galop galop;

    @Expose
    @Column(name = "licence")
    private String licence;


    public Personne() {
    }

    public Personne(String nom, String prenom, Date dateNaissance, Sexe sexe, TypePersonne type, byte[] img, Galop galop) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.type = type;
        this.img = img;
        this.galop = galop;
    }

    public Personne(String nom, String prenom, Date dateNaissance, Sexe sexe, TypePersonne type) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public TypePersonne getType() {
        return type;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public void setType(TypePersonne type) {
        this.type = type;
    }

    public Galop getGalop() {
        return galop;
    }

    public void setGalop(Galop galop) {
        this.galop = galop;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }
}
