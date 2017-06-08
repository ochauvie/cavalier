package com.cavalier.model;

import com.cavalier.R;

public enum Heure implements IRefData {

    H0(R.string.h0, 0, 0),
    H1(R.string.h1, 0, 1),
    H2(R.string.h2, 0, 2),
    H3(R.string.h3, 0, 3),
    H4(R.string.h4, 0, 4),
    H5(R.string.h5, 0, 5),
    H6(R.string.h6, 0, 6),
    H7(R.string.h7, 0, 7),
    H8(R.string.h8, 0, 8),
    H9(R.string.h9, 0, 9),
    H10(R.string.h10, 0, 10),
    H11(R.string.h11, 0, 11),
    H12(R.string.h12, 0, 12),
    H13(R.string.h13, 0, 13),
    H14(R.string.h14, 0, 14),
    H15(R.string.h15, 0, 15),
    H16(R.string.h16, 0, 16),
    H17(R.string.h17, 0, 17),
    H18(R.string.h18, 0, 18),
    H19(R.string.h19, 0, 19),
    H20(R.string.h20, 0, 20),
    H21(R.string.h21, 0, 21),
    H22(R.string.h22, 0, 22),
    H23(R.string.h23, 0, 23);


    private final int label;
    private final int flag;
    private final int value;

    Heure(int label, int flag, int value) {
        this.label = label;
        this.flag = flag;
        this.value = value;
    }

    public int getLabel() {
        return label;
    }

    public int getFlag() {
        return flag;
    }

    public int getValue() {
        return value;
    }
}
