package com.tankbattle.listner;

import com.tankbattle.model.Tank;
import com.tankbattle.model.TankVictoires;

/**
 * Created by Olivier on 09/09/15.
 */
public interface TankListener {

    public void onClickTank(Tank item, int position);

    public void onDeleteVictoire(TankVictoires item);
}
