package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.entities.LiveTiming;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class LiveTimingCache {

    private Map<Integer, LiveTiming> dataCache = new HashMap<>();

    public boolean isNewEntry(LiveTiming liveTiming) {
        if (!dataCache.containsKey(liveTiming.getNumber())) {
            addLiveTiming(liveTiming);
            return true;
        } else {
            LiveTiming cachedValue = dataCache.get(liveTiming.getNumber());
            if (!isEqualTo(cachedValue, liveTiming)) {
                addLiveTiming(liveTiming);
                return true;
            } else {
                return false;
            }
        }
    }

    private void addLiveTiming(LiveTiming liveTiming) {
        dataCache.put(liveTiming.getNumber(), liveTiming);
    }

    private boolean isEqualTo(LiveTiming oldValue, LiveTiming newValue) {

        if (oldValue.getLastTime() != newValue.getLastTime()) {
            return false;
        }

        if (sectorsChanged(oldValue, newValue)) {
            return false;
        }

        if (oldValue.isInPit() != newValue.isInPit()) {
            return false;
        }

        if (oldValue.getState() != newValue.getState()) {
            return false;
        }

        if (oldValue.getPosition() != newValue.getPosition()) {
            return false;
        }

        return true;
    }

    private boolean sectorsChanged(LiveTiming oldValue, LiveTiming newValue) {
        if (oldValue.getSectorOne() != newValue.getSectorOne() ||
                oldValue.getSectorTwo() != newValue.getSectorTwo() ||
                oldValue.getSectorThree() != newValue.getSectorThree()) {
            return true;
        } else {
            return false;
        }
    }

}
