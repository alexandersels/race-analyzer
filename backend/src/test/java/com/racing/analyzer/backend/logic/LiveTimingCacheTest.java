package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LiveTimingCacheTest {

    private final Race race = new Race();
    private LiveTiming firstEntry;

    @Before
    public void init() {
        firstEntry = new LiveTiming("Sels", 69, "CLS", 1, 1L, 2L,
                "BEL", false, "Car", LiveTimingState.RACING,
                3L, 4L, 5L, race);
    }

    @Test
    public void nothingChanged() {
        LiveTimingCache cache = new LiveTimingCache();
        cache.isNewEntry(firstEntry);

        LiveTiming secondEntry = new LiveTiming("Sels", 69, "CLS", 1, 1L, 2L,
                "BEL", false, "Car", LiveTimingState.RACING,
                3L, 4L, 5L, race);


        assertThat(cache.isNewEntry(secondEntry)).isFalse();
    }

    @Test
    public void lastTimeChangedResultsInNewEntry() {
        LiveTiming secondEntry = new LiveTiming("Sels", 69, "CLS", 1, 2L, 2L,
                "BEL", false, "Car", LiveTimingState.RACING,
                3L, 4L, 5L, race);

        LiveTimingCache cache = new LiveTimingCache();
        cache.isNewEntry(firstEntry);

        assertThat(cache.isNewEntry(secondEntry)).isTrue();
    }

    @Test
    public void sectorOneChangedResultsInNewEntry() {
        LiveTiming secondEntry = new LiveTiming("Sels", 69, "CLS", 1, 1L, 2L,
                "BEL", false, "Car", LiveTimingState.RACING,
                4L, 4L, 5L, race);

        LiveTimingCache cache = new LiveTimingCache();
        cache.isNewEntry(firstEntry);

        assertThat(cache.isNewEntry(secondEntry)).isTrue();
    }

    @Test
    public void sectorTwoChangedResultsInNewEntry() {
        LiveTiming secondEntry = new LiveTiming("Sels", 69, "CLS", 1, 1L, 2L,
                "BEL", false, "Car", LiveTimingState.RACING,
                3L, 5L, 5L, race);

        LiveTimingCache cache = new LiveTimingCache();
        cache.isNewEntry(firstEntry);

        assertThat(cache.isNewEntry(secondEntry)).isTrue();
    }

    @Test
    public void sectorThreeChangedResultsInNewEntry() {
        LiveTiming secondEntry = new LiveTiming("Sels", 69, "CLS", 1, 1L, 2L,
                "BEL", false, "Car", LiveTimingState.RACING,
                3L, 4L, 6L, race);

        LiveTimingCache cache = new LiveTimingCache();
        cache.isNewEntry(firstEntry);

        assertThat(cache.isNewEntry(secondEntry)).isTrue();
    }

    @Test
    public void pitChangedResultsInNewEntry() {
        LiveTiming secondEntry = new LiveTiming("Sels", 69, "CLS", 1, 1L, 2L,
                "BEL", true, "Car", LiveTimingState.RACING,
                3L, 4L, 5L, race);

        LiveTimingCache cache = new LiveTimingCache();
        cache.isNewEntry(firstEntry);

        assertThat(cache.isNewEntry(secondEntry)).isTrue();
    }

    @Test
    public void stateChangedResultsInNewEntry() {
        LiveTiming secondEntry = new LiveTiming("Sels", 69, "CLS", 1, 1L, 2L,
                "BEL", false, "Car", LiveTimingState.PIT,
                3L, 4L, 5L, race);

        LiveTimingCache cache = new LiveTimingCache();
        cache.isNewEntry(firstEntry);

        assertThat(cache.isNewEntry(secondEntry)).isTrue();
    }

    @Test
    public void positionChangedResultsInNewEntry() {
        LiveTiming secondEntry = new LiveTiming("Sels", 69, "CLS", 2, 1L, 2L,
                "BEL", false, "Car", LiveTimingState.RACING,
                3L, 4L, 5L, race);

        LiveTimingCache cache = new LiveTimingCache();
        cache.isNewEntry(firstEntry);

        assertThat(cache.isNewEntry(secondEntry)).isTrue();
    }
}
