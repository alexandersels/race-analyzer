package com.racing.analyzer.backend.enums;

public enum LiveTimingState {
    RACING,
    PIT,
    OUTLAP,
    UNKNOWN;

    public static LiveTimingState from(int value) {
        switch (value) {
            case 0:
                return LiveTimingState.RACING;
            case 1:
                return LiveTimingState.PIT;
            case 2:
                return LiveTimingState.OUTLAP;
            default:
                return LiveTimingState.UNKNOWN;
        }
    }
}
