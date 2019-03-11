package com.racing.analyzer.backend.commands;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateRaceCommandTest {

    @Test
    public void getNameIsCorrect() {
        UpdateRaceCommand command = new UpdateRaceCommand("Zolder", true, "url", 10);
        assertThat(command.getName()).isEqualTo("Zolder");
    }

    @Test
    public void getUrlIsCorrect() {
        UpdateRaceCommand command = new UpdateRaceCommand("Zolder", true, "url", 10);
        assertThat(command.getUrl()).isEqualTo("url");
    }

    @Test
    public void isRecordingCorrect() {
        UpdateRaceCommand command = new UpdateRaceCommand("Zolder", true, "url", 10);
        assertThat(command.isRecording());
    }

    @Test
    public void isVersionCorrect() {
        UpdateRaceCommand command = new UpdateRaceCommand("Zolder", true, "url", 10);
        assertThat(command.getVersion()).isEqualTo(10);
    }
}
