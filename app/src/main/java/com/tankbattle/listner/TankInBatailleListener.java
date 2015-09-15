package com.tankbattle.listner;

import com.tankbattle.model.Tank;

/**
 * Created by Olivier on 09/09/15.
 */
public interface TankInBatailleListener {

    public void onClickAddPv(Tank item, int position);

    public void onClickDeletePv(Tank item, int position);

    public void onClickVictoire(Tank item, int position);
}
