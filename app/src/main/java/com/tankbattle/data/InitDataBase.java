package com.tankbattle.data;

import com.tankbattle.model.Genre;
import com.tankbattle.model.Nation;
import com.tankbattle.model.Tank;
import com.tankbattle.service.TankService;

/**
 * Created by olivier on 08/09/15.
 */
public class InitDataBase {

    public static void initTankList() {

        TankService.deleteAllTanks();

        // Russe
        Tank tank = new Tank("KV-1", 10, Nation.RU, Genre.HEAVY);
        tank.save();

        tank = new Tank("T34", 10, Nation.RU, Genre.MEDIUM);
        tank.save();

        tank = new Tank("ISU 152", 10, Nation.RU, Genre.DESTROYER);
        tank.save();

        // Allemand
        tank = new Tank("Tigre 1", 10, Nation.DE, Genre.HEAVY);
        tank.save();

        // Anglais
        tank = new Tank("Churchill", 10, Nation.EN, Genre.HEAVY);
        tank.save();

        // Americain
        tank = new Tank("Sherman", 10, Nation.US, Genre.HEAVY);
        tank.save();



    }
}
