package com.racing.analyzer.backend.mappers;

import com.racing.analyzer.backend.dto.LiveTimingDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LiveTimingMapperTest {

    private LiveTiming liveTiming;

    @Before
    public void init() {
        Race race = new Race();
        liveTiming = new LiveTiming("Sels", 69, "SGT", 1, 5L, 6L, "NLD", true,
                                    "CAR", LiveTimingState.RACING, 2L, 1L, 3L, race);
    }

    @Test
    public void testNameMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.driverName).isEqualTo("Sels");
    }

    @Test
    public void testNumberMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.carNumber).isEqualTo(69);
    }

    @Test
    public void testClsMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.cls).isEqualTo("SGT");
    }

    @Test
    public void testPositionMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.position).isEqualTo(1);
    }

    @Test
    public void testLastTimeMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.lastTime).isEqualTo(5L);
    }

    @Test
    public void testBestTimeMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.bestTime).isEqualTo(6L);
    }

    @Test
    public void testNationalityMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.nationality).isEqualTo("NLD");
    }

    @Test
    public void testInPitMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.inPit).isTrue();
    }

    @Test
    public void testCarMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.car).isEqualTo("CAR");
    }

    @Test
    public void testStateMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.state).isEqualTo(LiveTimingState.RACING);
    }

    @Test
    public void testSectorOneMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.sectorOneTime).isEqualTo(2L);
    }

    @Test
    public void testSectorTwoMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.sectorTwoTime).isEqualTo(1L);
    }

    @Test
    public void testSectorThreeMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.sectorThreeTime).isEqualTo(3L);
    }

    @Test
    public void testRaceMapping() {
        LiveTimingDTO mappedDto = LiveTimingMapper.toDto(liveTiming);
        assertThat(mappedDto.race).isEqualTo(0);
    }
}
