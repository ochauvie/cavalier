package com.cavalier.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;


@Table(name = "ration")
public class Ration extends Model {

    @Expose
    @Column(name = "monture")
    private Monture monture;

    @Expose
    @Column(name = "type")
    private String type;

    @Expose
    @Column(name = "quantite")
    private float quantite;

    @Expose
    @Column(name = "unite")
    private Unite unite;

    @Expose
    @Column(name = "frequence")
    private String frequence;

    @Expose
    @Column(name = "observation")
    private String observation;


}


