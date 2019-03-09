package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.entities.LiveTiming;

import java.util.HashMap;
import java.util.Map;

public class LiveTimingCache {

    private Map<Integer, LiveTiming> dataCache = new HashMap<>();

    public boolean isNewEntry(LiveTiming liveTiming) {
//        if (!dataCache.containsKey(liveTiming.getNumber())) {
//            addLiveTiming(liveTiming);
//            return true;
//        } else {
//            LiveTiming cachedValue = dataCache.get(liveTiming.getNumber());
//            if (!isEqualTo(cachedValue, liveTiming)) {
//                addLiveTiming(liveTiming);
//                return true;
//            } else {
//                return false;
//            }
//        }
        return true;
    }

    public void clear() {
        dataCache.clear();
    }

    private void addLiveTiming(LiveTiming liveTiming) {
        dataCache.put(liveTiming.getNumber(), liveTiming);
    }

    private boolean isEqualTo(LiveTiming oldValue, LiveTiming newValue) {

        if(oldValue.getLastTime() != newValue.getLastTime()) {
            return false;
        }

        if(oldValue.isInPit() != newValue.isInPit()) {
            return false;
        }

        if(oldValue.getState() != newValue.getState()) {
            return false;
        }

        if(oldValue.getPosition() != newValue.getPosition()) {
            return false;
        }

        return true;
    }
}
