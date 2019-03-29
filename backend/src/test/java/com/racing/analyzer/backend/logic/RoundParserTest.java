package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.LiveTimingHelper;
import com.racing.analyzer.backend.dto.RoundDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import com.racing.analyzer.backend.readers.LiveTimingCSVReader;
import org.junit.Before;
import org.junit.Test;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class RoundParserTest {

    private Collection<LiveTiming> driverData = new ArrayList<>();

    @Before
    public void init() {
        Collection<LiveTiming> liveTimings = LiveTimingCSVReader.parseTimings("LiveTrackingDemoData.csv");
        driverData = liveTimings.stream()
                .filter(timings -> timings.getNumber() == 127)
                .collect(Collectors.toList());
    }

    @Test
    public void amountOfRoundsIsCorrect() {
        Collection<RoundDTO> actualSet = RoundParser.createRoundDataForSpecificDriver(driverData);
        assertThat(actualSet.size()).isEqualTo(expectedDataSet().size());
    }

    @Test
    public void testFirstLapRegistered() {
        final Collection<RoundDTO> rounds = RoundParser.createRoundDataForSpecificDriver(createFirstLapDataSet());

        RoundDTO expected = new RoundDTO();
        expected.inPit = false;
        expected.lapTime = 79000L;
        expected.sectorOneTime = 30000L;
        expected.sectorTwoTime = 25000L;
        expected.sectorThreeTime = 24000L;

        assertThat(rounds).containsExactly(expected);
    }

    @Test
    public void testConsecutiveLapsRegistered() {
        final Collection<RoundDTO> rounds = RoundParser.createRoundDataForSpecificDriver(createConsecutiveLapDataSet());

        RoundDTO expectedRoundOne = new RoundDTO();
        expectedRoundOne.inPit = false;
        expectedRoundOne.lapTime = 5L;
        expectedRoundOne.sectorOneTime = 2L;
        expectedRoundOne.sectorTwoTime = 1L;
        expectedRoundOne.sectorThreeTime = 2L;

        RoundDTO expectedRoundTwo = new RoundDTO();
        expectedRoundTwo.inPit = false;
        expectedRoundTwo.lapTime = 10L;
        expectedRoundTwo.sectorOneTime = 3L;
        expectedRoundTwo.sectorTwoTime = 2L;
        expectedRoundTwo.sectorThreeTime = 5L;

        RoundDTO expectedRoundThree = new RoundDTO();
        expectedRoundThree.inPit = false;
        expectedRoundThree.lapTime = 14L;
        expectedRoundThree.sectorOneTime = 4L;
        expectedRoundThree.sectorTwoTime = 4L;
        expectedRoundThree.sectorThreeTime = 6L;

        assertThat(rounds).containsExactly(expectedRoundOne, expectedRoundTwo, expectedRoundThree);
    }

    @Test
    public void testConsecutiveLapsWithGapsRegistered() {
        final Collection<RoundDTO> rounds = RoundParser.createRoundDataForSpecificDriver(createConsecutiveLapDataSetWithGap());

        RoundDTO expectedRoundOne = new RoundDTO();
        expectedRoundOne.inPit = false;
        expectedRoundOne.lapTime = 5L;
        expectedRoundOne.sectorOneTime = 2L;
        expectedRoundOne.sectorTwoTime = 1L;
        expectedRoundOne.sectorThreeTime = 2L;

        RoundDTO expectedRoundTwo = new RoundDTO();
        expectedRoundTwo.inPit = true;
        expectedRoundTwo.lapTime = 8L;
        expectedRoundTwo.sectorOneTime = 3L;
        expectedRoundTwo.sectorTwoTime = 2L;
        expectedRoundTwo.sectorThreeTime = -1L;

        RoundDTO expectedRoundThree = new RoundDTO();
        expectedRoundThree.inPit = false;
        expectedRoundThree.lapTime = 20L;
        expectedRoundThree.sectorOneTime = -1L;
        expectedRoundThree.sectorTwoTime = 5L;
        expectedRoundThree.sectorThreeTime = 6L;

        assertThat(rounds).containsExactly(expectedRoundOne, expectedRoundTwo, expectedRoundThree);
    }

    private Collection<LiveTiming> expectedDataSet() {
        Race race = Race.builder().id(1)
                .name("Zolder")
                .recording(false)
                .url("url")
                .build();

        return ImmutableSet.of(
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 107219000L, -1L, "NLD", false, "CAR", LiveTimingState.RACING, 36438000L,
                        39593000L, 31188000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 99000000L, 99000000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        32062000L, 36438000L, 30500000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 97343000L, 97343000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31109000L, 36281000L, 29953000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 97203000L, 97203000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31172000L, 35672000L, 30359000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 98031000L, 97203000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31250000L, 36391000L, 30390000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 97594000L, 97203000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31344000L, 36125000L, 30125000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96859000L, 96859000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31453000L, 35531000L, 29875000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96328000L, 96328000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31188000L, 35078000L, 30062000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 95875000L, 95875000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31031000L, 35250000L, 29594000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 95703000L, 95703000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31375000L, 34656000L, 29672000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 95703000L, 95703000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31094000L, 34922000L, 29687000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 97062000L, 95703000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31000000L, 35625000L, 30437000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 97375000L, 95703000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31016000L, 36578000L, 29781000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 98844000L, 95703000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31922000L, 37016000L, 29906000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 95844000L, 95703000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31500000L, 34844000L, 29500000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96406000L, 95703000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31343000L, 35375000L, 29688000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96953000L, 95703000L, "NLD", false, "CAR", LiveTimingState.RACING,
                        31734000L, 35484000L, 29735000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 105859000L, 95703000L, "NLD", true, "CAR", LiveTimingState.PIT,
                        31281000L, 35094000L, -1L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 179234000L, 95703000L, "NLD", true, "CAR", LiveTimingState.OUTLAP, -1L,
                        35531000L, 29719000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96719000L, 95703000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31625000L, 35437000L, 29657000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 95609000L, 95609000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        30968000L, 34875000L, 29766000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96734000L, 95609000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31234000L, 35891000L, 29609000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96922000L, 95609000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31422000L, 35109000L, 30391000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 95953000L, 95609000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31140000L, 34875000L, 29938000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96875000L, 95609000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31453000L, 35578000L, 29844000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96218000L, 95609000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31140000L, 34985000L, 30093000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96156000L, 96156000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31438000L, 34922000L, 29796000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96672000L, 96156000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        30922000L, 35703000L, 30047000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 95969000L, 96156000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        30844000L, 35375000L, 29750000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 97562000L, 96156000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31375000L, 35906000L, 30281000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 98797000L, 98797000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31141000L, 37859000L, 29797000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96922000L, 98797000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31328000L, 35844000L, 29750000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96968000L, 95609000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31390000L, 35813000L, 29765000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96406000L, 95609000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        30875000L, 35563000L, 29968000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 97750000L, 95609000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31844000L, 36047000L, 29859000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96390000L, 95609000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        30953000L, 35906000L, 29531000L, race),
                LiveTimingHelper.create(1, "DANNY WERKMAN", 127, "SGT", 14, 96672000L, 95609000L, "NLD", true, "CAR", LiveTimingState.RACING,
                        31344000L, 35484000L, 29844000L, race)
        );
    }

    private Collection<LiveTiming> createFirstLapDataSet() {
        Race race = Race.builder().id(1)
                .name("Zolder")
                .recording(false)
                .url("url")
                .build();

        return Arrays.asList(
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, -1L, -1L, "NLD", false,
                        "CAR", LiveTimingState.RACING, -1L, -1L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, -1L, -1L, "NLD", false,
                        "CAR", LiveTimingState.RACING, 30000L, -1L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, -1L, -1L, "NLD", false,
                        "CAR", LiveTimingState.RACING, 30000L, 25000L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 79000L, 80000L, "NLD", false,
                        "CAR", LiveTimingState.RACING, 30000L, 25000L, 24000L, race)
        );
    }

    private Collection<LiveTiming> createConsecutiveLapDataSet() {
        Race race = Race.builder().id(1)
                .name("Zolder")
                .recording(false)
                .url("url")
                .build();

        return Arrays.asList(
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 5L, 5L, "NLD", false,
                        "CAR", LiveTimingState.RACING, 2L, 1L, 2L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 5L, 5L, "NLD", false,
                        "CAR", LiveTimingState.RACING, 3L, -1L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 5L, 5L, "NLD", false,
                        "CAR", LiveTimingState.RACING, 3L, 2L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 10L, 5L, "NLD", false,
                        "CAR", LiveTimingState.RACING, 3L, 2L, 5L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 10L, 5L, "NLD", false,
                        "CAR", LiveTimingState.RACING, 4L, -1L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 10L, 5L, "NLD", false,
                        "CAR", LiveTimingState.RACING, 4L, 4L, -1L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 14L, 5L, "NLD", false,
                        "CAR", LiveTimingState.RACING, 4L, 4L, 6L, race)
        );
    }

    private Collection<LiveTiming> createConsecutiveLapDataSetWithGap() {
        Race race = Race.builder().id(1)
                .name("Zolder")
                .recording(false)
                .url("url")
                .build();

        return Arrays.asList(
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 5L, 5L, "NLD", false,
                        "CAR", LiveTimingState.RACING, 2L, 1L, 2L, race),
                LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 5L, 5L, "NLD", false,
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
