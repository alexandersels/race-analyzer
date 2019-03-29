package com.racing.analyzer.backend.mappers;

import com.racing.analyzer.backend.LiveTimingHelper;
import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class LiveTimingMapperTest {

    private LiveTiming liveTiming;

    private LiveTimingMapper mapper = Mappers.getMapper(LiveTimingMapper.class);

    @Before
    public void init() {
        Race race = Race.builder()
                        .id(1)
                        .url("url")
                        .recording(false)
                        .build();

        liveTiming = LiveTimingHelper.create(1, "Sels", 69, "SGT", 1, 5L, 6L, "NLD",
                                      true, "CAR", LiveTimingState.RACING, 2L, 1L, 3L, race);
    }

    @Test
    public void testNameMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getName()).isEqualTo("Sels");
    }

    @Test
    public void testNumberMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getNumber()).isEqualTo(69);
    }

    @Test
    public void testClsMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getCls()).isEqualTo("SGT");
    }

    @Test
    public void testPositionMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getPosition()).isEqualTo(1);
    }

    @Test
    public void testLastTimeMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getLastTime()).isEqualTo(5L);
    }

    @Test
    public void testBestTimeMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getBestTime()).isEqualTo(6L);
    }

    @Test
    public void testNationalityMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getNationality()).isEqualTo("NLD");
    }

    @Test
    public void testInPitMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.isInPit()).isTrue();
    }

    @Test
    public void testCarMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getCar()).isEqualTo("CAR");
    }

    @Test
    public void testStateMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getState()).isEqualTo(LiveTimingState.RACING);
    }

    @Test
    public void testSectorOneMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getSectorOne()).isEqualTo(2L);
    }

    @Test
    public void testSectorTwoMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getSectorTwo()).isEqualTo(1L);
    }

    @Test
    public void testSectorThreeMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getSectorThree()).isEqualTo(3L);
    }

    @Test
    public void testRaceMapping() {
        LiveTimingDTO mappedDto = mapper.toDto(liveTiming);
        assertThat(mappedDto.getRace()).isEqualTo(1);
    }
}
