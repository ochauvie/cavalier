package com.tankbattle.model;

/**
 * Created by Olivier on 05/09/15.
 */
public class Equipe {

    public void FabriqueEquipe() {
        Tank monTank = new Tank();
        monTank.setNom("KV1");
        monTank.setPv(10);

        monTank.addPv(-2);

        int pvDuTank = monTank.getPv();



        String nomDuTank = monTank.getNom();
    }
}
