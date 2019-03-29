package com.racing.analyzer.backend.commands;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateRaceCommandTest {

    @Test
    public void getIdIsCorrect() {
        UpdateRaceCommand command = new UpdateRaceCommand(1,"Zolder", true, "url", 10);
        assertThat(command.getId()).isEqualTo(1);
    }

    @Test
    public void getNameIsCorrect() {
        UpdateRaceCommand command = new UpdateRaceCommand(1,"Zolder", true, "url", 10);
        assertThat(command.getName()).isEqualTo("Zolder");
    }

    @Test
    public void getUrlIsCorrect() {
        UpdateRaceCommand command = new UpdateRaceCommand(1,"Zolder", true, "url", 10);
        assertThat(command.getUrl()).isEqualTo("url");
    }

    @Test
    public void isRecordingCorrect() {
        UpdateRaceCommand command = new UpdateRaceCommand(1,"Zolder", true, "url", 10);
        assertThat(command.isRecording());
    }

    @Test
    public void isVersionCorrect() {
        UpdateRaceCommand command = new UpdateRaceCommand(1,"Zolder", true, "url", 10);
        assertThat(command.getVersion()).isEqualTo(10);
    }
}
