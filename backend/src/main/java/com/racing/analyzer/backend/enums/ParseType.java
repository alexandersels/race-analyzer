package com.racing.analyzer.backend.enums;

public enum ParseType {
    POSITION(0),
    ICON(1),
    STATE(2),
    NUMBER(3),
    NAME(4),
    CLS(5),
    PIC(6),
    GAP(7),
    LAST(8),
    BEST(9),
    PIT(10),
    NAT(11),
    CAR(12),
    S(13),
    S1(14),
    S2(15),
    S3(16);

    private final int value;

    ParseType(int value) {
        this.value = value;
    }

    public int getIndex() {
        return value;
    }
}

