package com.tankbattle.listner;

import com.tankbattle.model.Tank;

/**
 * Created by Olivier on 09/09/15.
 */
public interface TankListener {

    public void onClickTank(Tank item, int position);
}
