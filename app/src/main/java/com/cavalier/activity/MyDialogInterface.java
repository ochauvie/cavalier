package com.cavalier.activity;

/**
 * Created by olivier on 11/09/15.
 */
public class MyDialogInterface {
    DialogReturn dialogReturn;

    public interface DialogReturn {
        void onDialogCompleted(boolean answer, String type);
    }

    public void setListener(DialogReturn dialogReturn) {
        this.dialogReturn = dialogReturn;
    }

    public DialogReturn getListener() {
        return dialogReturn;
    }
}
