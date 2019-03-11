package com.racing.analyzer.backend.builders;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LiveTimingBuilderTest {

    @Test
    public void withNameIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withName("Alexander").build();
        assertThat(liveTiming.getName()).isEqualTo("Alexander");
    }

    @Test
    public void withNumberIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withNumber(10).build();
        assertThat(liveTiming.getNumber()).isEqualTo(10);
    }

    @Test
    public void withClsIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withCls("My Class").build();
        assertThat(liveTiming.getCls()).isEqualTo("My Class");
    }

    @Test
    public void withPositionIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withPosition(5).build();
        assertThat(liveTiming.getPosition()).isEqualTo(5);
    }

    @Test
    public void withLastLapTimeIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withLastLapTime(5000L).build();
        assertThat(liveTiming.getLastTime()).isEqualTo(5000L);
    }

    @Test
    public void withBestLapTimeIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withBestLapTime(6000L).build();
        assertThat(liveTiming.getBestTime()).isEqualTo(6000L);
    }

    @Test
    public void withNationalityIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withNationality("BEL").build();
        assertThat(liveTiming.getNationality()).isEqualTo("BEL");
    }

    @Test
    public void witInPitIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.inPit(true).build();
        assertThat(liveTiming.isInPit());
    }

    @Test
    public void withCarIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withCar("Peugot").build();
        assertThat(liveTiming.getCar()).isEqualTo("Peugot");
    }

    @Test
    public void withSateIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withState(LiveTimingState.OUTLAP).build();
        assertThat(liveTiming.getState()).isEqualTo(LiveTimingState.OUTLAP);
    }

    @Test
    public void withSectorOneIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withSectorOne(50L).build();
        assertThat(liveTiming.getSectorOne()).isEqualTo(50L);
    }

    @Test
    public void withSectorTwoIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withSectorTwo(60L).build();
        assertThat(liveTiming.getSectorTwo()).isEqualTo(60L);
    }

    @Test
    public void withSectorThreeIsCorrect() {
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withSectorThree(70L).build();
        assertThat(liveTiming.getSectorThree()).isEqualTo(70L);
    }

    @Test
    public void withRaceIsCorrect() {
        Race race = new Race();
        LiveTimingBuilder builder = new LiveTimingBuilder();
        LiveTiming liveTiming = builder.withRace(race).build();
        assertThat(liveTiming.getRace()).isEqualTo(race);
    }


}
