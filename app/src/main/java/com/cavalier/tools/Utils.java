package com.cavalier.tools;

import android.view.MenuItem;

public class Utils {

    public static void enableItem (MenuItem item) {
        item.setEnabled(true);
        item.getIcon().setAlpha(255);
    }

    public static void disableItem (MenuItem item) {
        item.setEnabled(false);
        item.getIcon().setAlpha(130);
    }

    public static void hideItem (MenuItem item) {
        item.setVisible(false);
    }

    public static void showItem (MenuItem item) {
        item.setVisible(true);
    }
}
