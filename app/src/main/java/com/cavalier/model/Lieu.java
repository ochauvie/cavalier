package com.cavalier.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "lieu")
public class Lieu extends Model implements Serializable {

    @Column(name = "nom")
    private String nom;

    @Column(name = "type")
    private TypeLieu type;

    public Lieu(String nom, TypeLieu type) {
        this.nom = nom;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeLieu getType() {
        return type;
    }

    public void setType(TypeLieu type) {
        this.type = type;
    }
}
