package com.racing.analyzer.backend.builders;

import com.racing.analyzer.backend.entities.Race;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RaceBuilderTest {

    @Test
    public void withNameIsCorrect() {
        Race race = Race.builder().name("Zolder").build();
        assertThat(race.getName()).isEqualTo("Zolder");
    }

    @Test
    public void withUrlIsCorrect() {
        Race race = Race.builder().url("http://localhost.be").build();
        assertThat(race.getUrl()).isEqualTo("http://localhost.be");
    }

    @Test
    public void isRecordingIsCorrect() {
        Race race = Race.builder().recording(true).build();
        assertThat(race.isRecording());
    }
}
