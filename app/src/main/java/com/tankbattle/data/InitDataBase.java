package com.tankbattle.data;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;

import com.tankbattle.model.Genre;
import com.tankbattle.model.Nation;
import com.tankbattle.model.Tank;
import com.tankbattle.service.EquipeService;
import com.tankbattle.service.TankService;

/**
 * Created by olivier on 08/09/15.
 */
public class InitDataBase {

    public static void initTankList(Context context) {

        TankService.deleteAllTanks();

        ContextWrapper cw = new ContextWrapper(context);
        String DEFAULT_IMAGE = "default_tank";

        // Russe
        Tank tank = new Tank("KV-1", 10, Nation.RU, Genre.HEAVY, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("KV-2", 10, Nation.RU, Genre.HEAVY, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("T-54", 10, Nation.RU, Genre.MEDIUM, "tank_t54");
        tank.save();
        tank = new Tank("T-34", 10, Nation.RU, Genre.MEDIUM, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("Su-152", 10, Nation.RU, Genre.DESTROYER, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("Su-85", 10, Nation.RU, Genre.DESTROYER, DEFAULT_IMAGE);
        tank.save();

        // Allemand
        tank = new Tank("Tigre 1 Jaune", 10, Nation.DE, Genre.HEAVY, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("Tigre 1 Gris", 10, Nation.DE, Genre.HEAVY, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("Ferdinand", 10, Nation.DE, Genre.DESTROYER, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("Jagdpanther", 10, Nation.DE, Genre.DESTROYER, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("Jagdtiger", 10, Nation.DE, Genre.DESTROYER, "tank_jagtiger");
        tank.save();
        tank = new Tank("Stug III g", 10, Nation.DE, Genre.DESTROYER, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("Panzer slf 4c", 10, Nation.DE, Genre.DESTROYER, "tank_panzer_slf4c");
        tank.save();
        tank = new Tank("Marder III", 10, Nation.DE, Genre.DESTROYER, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("Marder 38t", 10, Nation.DE, Genre.DESTROYER, DEFAULT_IMAGE);
        tank.save();

        // Anglais
        tank = new Tank("Churchill I", 10, Nation.EN, Genre.HEAVY, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("AS-90 SPG", 10, Nation.EN, Genre.ARTILLERY, "tank_as90spg");
        tank.save();
        tank = new Tank("M2 Light", 10, Nation.EN, Genre.LIGHT, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("Cromwell", 10, Nation.EN, Genre.MEDIUM, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("Warrior 33", 10, Nation.EN, Genre.LIGHT, DEFAULT_IMAGE);
        tank.save();

        // Americain
        tank = new Tank("M1A1", 10, Nation.US, Genre.HEAVY, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("M4 Sherman", 10, Nation.US, Genre.MEDIUM, DEFAULT_IMAGE);
        tank.save();
        tank = new Tank("M4A3E2 Sherman", 10, Nation.US, Genre.MEDIUM, DEFAULT_IMAGE);
        tank.save();


        // Francais
        tank = new Tank("B1 bis", 10, Nation.FR, Genre.HEAVY, DEFAULT_IMAGE);
        tank.save();
    }

    public static void initEquipeList() {
        EquipeService.deleteAllEquipes();
    }
}