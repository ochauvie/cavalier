package com.tankbattle.tools;

import android.view.MenuItem;

/**
 * Created by olivier on 16/09/15.
 */
public class Utils {

    public static void enableItem (MenuItem item) {
        item.setEnabled(true);
        item.getIcon().setAlpha(255);
    }

    public static void disableItem (MenuItem item) {
        item.setEnabled(false);
        item.getIcon().setAlpha(130);
    }
}
