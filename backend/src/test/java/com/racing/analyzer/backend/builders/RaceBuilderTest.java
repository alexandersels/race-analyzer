package com.racing.analyzer.backend.builders;

import com.racing.analyzer.backend.entities.Race;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RaceBuilderTest {

    @Test
    public void withNameIsCorrect() {
        RaceBuilder builder = new RaceBuilder();
        Race race = builder.withName("Zolder").build();
        assertThat(race.getName()).isEqualTo("Zolder");
    }

    @Test
    public void withUrlIsCorrect() {
        RaceBuilder builder = new RaceBuilder();
        Race race = builder.withUrl("http://localhost.be").build();
        assertThat(race.getUrl()).isEqualTo("http://localhost.be");
    }

    @Test
    public void isRecordingIsCorrect() {
        RaceBuilder builder = new RaceBuilder();
        Race race = builder.isRecording(true).build();
        assertThat(race.isRecording());
    }
}
