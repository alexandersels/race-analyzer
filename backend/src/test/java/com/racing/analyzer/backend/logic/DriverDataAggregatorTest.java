package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.LiveTimingHelper;
import com.racing.analyzer.backend.dto.statistics.AggregatedDriverDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import com.racing.analyzer.backend.logic.aggregators.DriverDataAggregator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverDataAggregatorTest {

    @Test
    public void testFirstLapRegistered() {
        final Collection<AggregatedDriverDTO> driverData = DriverDataAggregator.aggregate(createTestData());
        final AggregatedDriverDTO driver = driverData.stream().findFirst().get();

        assertThat(driver.getCar()).isEqualTo("CAR");
        assertThat(driver.getName()).isEqualTo("Sels");
        assertThat(driver.getNumber()).isEqualTo(69);
        assertThat(driver.getPitStops()).isEqualTo(1);
        assertThat(driver.getRounds().size()).isEqualTo(3);
        assertThat(driver.getBestLap()).isEqualTo(5L);

    }

    private Collection<LiveTiming> createTestData() {
        Race race = Race.builder()
            .name("Zolder")
            .recording(false)
            .url("url")
            .build();

        return Arrays.asList(
                LiveTimingHelper.create(1,"Sels", 69, "SGT", 1, 5L, 5L, "NLD", false,
                               "CAR", LiveTimingState.RACING, 2L, 1L, 2L, race),
                LiveTimingHelper.create(1,"Sels", 69, "SGT", 1, 5L, 5L, "NLD", false,
                               "CAR", LiveTimingState.RACING, 3L, -1L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 5L, 5L, "NLD", false,
                               "CAR", LiveTimingState.RACING, 3L, 2L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 8L, 5L, "NLD", true,
                               "CAR", LiveTimingState.RACING, 3L, 2L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 8L, 5L, "NLD", true,
                               "CAR", LiveTimingState.OUTLAP, -1L, -1L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 8L, 5L, "NLD", true,
                               "CAR", LiveTimingState.OUTLAP, -1L, 5L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 20L, 5L, "NLD", false,
                               "CAR", LiveTimingState.RACING, -1L, 5L, 6L, race)
                            );
    }
}
