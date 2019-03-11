package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.Readers.LiveTimingCSVReader;
import com.racing.analyzer.backend.dto.RoundDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.junit.Before;
import org.junit.Test;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;

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

    private Collection<LiveTiming> expectedDataSet() {
        Race race = new Race("Zolder", false, "url");
        return ImmutableSet.of(
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 107219000, -1, "NLD", false, "CAR", LiveTimingState.RACING, 36438000, 39593000, 31188000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 99000000, 99000000, "NLD", false, "CAR", LiveTimingState.RACING, 32062000, 36438000, 30500000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 97343000, 97343000, "NLD", false, "CAR", LiveTimingState.RACING, 31109000, 36281000, 29953000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 97203000, 97203000, "NLD", false, "CAR", LiveTimingState.RACING, 31172000, 35672000, 30359000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 98031000, 97203000, "NLD", false, "CAR", LiveTimingState.RACING, 31250000, 36391000, 30390000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 97594000, 97203000, "NLD", false, "CAR", LiveTimingState.RACING, 31344000, 36125000, 30125000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96859000, 96859000, "NLD", false, "CAR", LiveTimingState.RACING, 31453000, 35531000, 29875000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96328000, 96328000, "NLD", false, "CAR", LiveTimingState.RACING, 31188000, 35078000, 30062000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 95875000, 95875000, "NLD", false, "CAR", LiveTimingState.RACING, 31031000, 35250000, 29594000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 95703000, 95703000, "NLD", false, "CAR", LiveTimingState.RACING, 31375000, 34656000, 29672000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 95703000, 95703000, "NLD", false, "CAR", LiveTimingState.RACING, 31094000, 34922000, 29687000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 97062000, 95703000, "NLD", false, "CAR", LiveTimingState.RACING, 31000000, 35625000, 30437000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 97375000, 95703000, "NLD", false, "CAR", LiveTimingState.RACING, 31016000, 36578000, 29781000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 98844000, 95703000, "NLD", false, "CAR", LiveTimingState.RACING, 31922000, 37016000, 29906000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 95844000, 95703000, "NLD", false, "CAR", LiveTimingState.RACING, 31500000, 34844000, 29500000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96406000, 95703000, "NLD", false, "CAR", LiveTimingState.RACING, 31343000, 35375000, 29688000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96953000, 95703000, "NLD", false, "CAR", LiveTimingState.RACING, 31734000, 35484000, 29735000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 105859000, 95703000, "NLD", true, "CAR", LiveTimingState.PIT, 31281000, 35094000, -1, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 179234000, 95703000, "NLD", true, "CAR", LiveTimingState.OUTLAP, -1, 35531000, 29719000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96719000, 95703000, "NLD", true, "CAR", LiveTimingState.RACING, 31625000, 35437000, 29657000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 95609000, 95609000, "NLD", true, "CAR", LiveTimingState.RACING, 30968000, 34875000, 29766000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96734000, 95609000, "NLD", true, "CAR", LiveTimingState.RACING, 31234000, 35891000, 29609000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96922000, 95609000, "NLD", true, "CAR", LiveTimingState.RACING, 31422000, 35109000, 30391000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 95953000, 95609000, "NLD", true, "CAR", LiveTimingState.RACING, 31140000, 34875000, 29938000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96875000, 95609000, "NLD", true, "CAR", LiveTimingState.RACING, 31453000, 35578000, 29844000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96218000, 95609000, "NLD", true, "CAR", LiveTimingState.RACING, 31140000, 34985000, 30093000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96156000, 96156000, "NLD", true, "CAR", LiveTimingState.RACING, 31438000, 34922000, 29796000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96672000, 96156000, "NLD", true, "CAR", LiveTimingState.RACING, 30922000, 35703000, 30047000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 95969000, 96156000, "NLD", true, "CAR", LiveTimingState.RACING, 30844000, 35375000, 29750000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 97562000, 96156000, "NLD", true, "CAR", LiveTimingState.RACING, 31375000, 35906000, 30281000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 98797000, 98797000, "NLD", true, "CAR", LiveTimingState.RACING, 31141000, 37859000, 29797000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96922000, 98797000, "NLD", true, "CAR", LiveTimingState.RACING, 31328000, 35844000, 29750000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96968000, 95609000, "NLD", true, "CAR", LiveTimingState.RACING, 31390000, 35813000, 29765000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96406000, 95609000, "NLD", true, "CAR", LiveTimingState.RACING, 30875000, 35563000, 29968000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 97750000, 95609000, "NLD", true, "CAR", LiveTimingState.RACING, 31844000, 36047000, 29859000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96390000, 95609000, "NLD", true, "CAR", LiveTimingState.RACING, 30953000, 35906000, 29531000, race),
                new LiveTiming("DANNY WERKMAN", 127, "SGT", 14, 96672000, 95609000, "NLD", true, "CAR", LiveTimingState.RACING, 31344000, 35484000, 29844000, race)
        );
    }
}
