package com.racing.analyzer.backend.builders;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LiveTimingBuilderTest {

    @Test
    public void withNameIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().name("Alexander").build();
        assertThat(liveTiming.getName()).isEqualTo("Alexander");
    }

    @Test
    public void withNumberIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().number(10).build();
        assertThat(liveTiming.getNumber()).isEqualTo(10);
    }

    @Test
    public void withClsIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().cls("My Class").build();
        assertThat(liveTiming.getCls()).isEqualTo("My Class");
    }

    @Test
    public void withPositionIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().position(5).build();
        assertThat(liveTiming.getPosition()).isEqualTo(5);
    }

    @Test
    public void withLastLapTimeIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().lastTime(5000L).build();
        assertThat(liveTiming.getLastTime()).isEqualTo(5000L);
    }

    @Test
    public void withBestLapTimeIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().bestTime(6000L).build();
        assertThat(liveTiming.getBestTime()).isEqualTo(6000L);
    }

    @Test
    public void withNationalityIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().nationality("BEL").build();
        assertThat(liveTiming.getNationality()).isEqualTo("BEL");
    }

    @Test
    public void witInPitIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().inPit(true).build();
        assertThat(liveTiming.isInPit());
    }

    @Test
    public void withCarIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().car("Peugot").build();
        assertThat(liveTiming.getCar()).isEqualTo("Peugot");
    }

    @Test
    public void withSateIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().state(LiveTimingState.OUTLAP).build();
        assertThat(liveTiming.getState()).isEqualTo(LiveTimingState.OUTLAP);
    }

    @Test
    public void withSectorOneIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().sectorOne(50L).build();
        assertThat(liveTiming.getSectorOne()).isEqualTo(50L);
    }

    @Test
    public void withSectorTwoIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().sectorTwo(60L).build();
        assertThat(liveTiming.getSectorTwo()).isEqualTo(60L);
    }

    @Test
    public void withSectorThreeIsCorrect() {
        LiveTiming liveTiming = LiveTiming.builder().sectorThree(70L).build();
        assertThat(liveTiming.getSectorThree()).isEqualTo(70L);
    }

    @Test
    public void withRaceIsCorrect() {
        Race race = Race.builder().id(1).build();
        LiveTiming liveTiming = LiveTiming.builder().race(race).build();
        assertThat(liveTiming.getRace()).isEqualTo(race);
    }


}
