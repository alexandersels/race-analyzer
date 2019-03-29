package com.racing.analyzer.backend.commands;

import com.racing.analyzer.backend.commands.race.CreateRaceCommand;
import com.racing.analyzer.backend.entities.Race;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateRaceCommandTest {

    @Test
    public void getNameIsCorrect() {
        CreateRaceCommand command = new CreateRaceCommand("Zolder",  "url");
        assertThat(command.getName()).isEqualTo("Zolder");
    }

    @Test
    public void getUrlIsCorrect() {
        CreateRaceCommand command = new CreateRaceCommand("Zolder",  "url");
        assertThat(command.getUrl()).isEqualTo("url");
    }

    @Test
    public void getEntityToCreateIsCorrect() {
        CreateRaceCommand command = new CreateRaceCommand("Zolder",  "url");
        Race race = command.getEntityToCreate();

        assertThat(race.getName()).isEqualTo("Zolder");
        assertThat(race.getUrl()).isEqualTo("url");
        assertThat(race.isRecording()).isFalse();
    }
}
