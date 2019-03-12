package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.dto.DriverDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverParserTest {

    @Test
    public void testFirstLapRegistered() {
        final Collection<DriverDTO> driverData = DriverParser.createDriverData(createTestData());
        final DriverDTO driver = driverData.stream().findFirst().get();

        assertThat(driver.car).isEqualTo("CAR");
        assertThat(driver.name).isEqualTo("Sels");
        assertThat(driver.number).isEqualTo(69);
        assertThat(driver.pitStops).isEqualTo(1);
        assertThat(driver.rounds.size()).isEqualTo(3);

    }

    private Collection<LiveTiming> createTestData() {
        Race race = new Race("Zolder", false, "url");
        return Arrays.asList(
                new LiveTiming("Sels", 69, "SGT", 1, 5L, 5L, "NLD", false,
                               "CAR", LiveTimingState.RACING, 2L, 1L, 2L, race),
                new LiveTiming("Sels", 69, "SGT", 1, 5L, 5L, "NLD", false,
                               "CAR", LiveTimingState.RACING, 3L, -1L, -1L, race),
                new LiveTiming("Sels", 69, "SGT", 1, 5L, 5L, "NLD", false,
                               "CAR", LiveTimingState.RACING, 3L, 2L, -1L, race),
                new LiveTiming("Sels", 69, "SGT", 1, 8L, 5L, "NLD", true,
                               "CAR", LiveTimingState.RACING, 3L, 2L, -1L, race),
                new LiveTiming("Sels", 69, "SGT", 1, 8L, 5L, "NLD", true,
                               "CAR", LiveTimingState.OUTLAP, -1L, -1L, -1L, race),
                new LiveTiming("Sels", 69, "SGT", 1, 8L, 5L, "NLD", true,
                               "CAR", LiveTimingState.OUTLAP, -1L, 5L, -1L, race),
                new LiveTiming("Sels", 69, "SGT", 1, 20L, 5L, "NLD", false,
                               "CAR", LiveTimingState.RACING, -1L, 5L, 6L, race)
                            );
    }
}
