package com.racing.analyzer.backend.builders.commands;

import com.racing.analyzer.backend.commands.CreateRaceCommand;
import com.racing.analyzer.backend.commands.UpdateRaceCommand;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.entities.Race;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateRaceCommandBuilderTest {

    @Test
    public void withNameIsCorrect() {
        CreateRaceCommand createRaceCommand = CreateRaceCommand.getBuilder()
                .withName("Nieuwe Zolder")
                .build();
        assertThat(createRaceCommand.getName()).isEqualTo("Nieuwe Zolder");
    }

    @Test
    public void withUrlIsCorrect() {
        CreateRaceCommand createRaceCommand = CreateRaceCommand.getBuilder()
                .withUrl("website")
                .build();
        assertThat(createRaceCommand.getUrl()).isEqualTo("website");
    }

    @Test
    public void isRecordingIsCorrect() {
        CreateRaceCommand createRaceCommand = CreateRaceCommand.getBuilder()
                .isRecording(true)
                .build();
        assertThat(createRaceCommand.isRecording());
    }

    @Test
    public void withVersionIsCorrect() {
        UpdateRaceCommandBuilder builder = new UpdateRaceCommandBuilder();
        final UpdateRaceCommand updateRaceCommand = builder.withVersion(10).build();
        assertThat(updateRaceCommand.getVersion()).isEqualTo(10);
    }

    @Test
    public void startFromDTOIsCorrect() {
        RaceDTO dto = RaceDTO.builder()
                .name("Zolder")
                .recording(true)
                .url("website")
                .version(2)
                .build();

        final UpdateRaceCommand command = UpdateRaceCommand.getBuilder()
                .startFrom(dto)
                .build();

        assertThat(command.getName()).isEqualTo("Zolder");
        assertThat(command.getUrl()).isEqualTo("website");
        assertThat(command.getVersion()).isEqualTo(2);
        assertThat(command.isRecording());
    }

    @Test
    public void startFromEntityIsCorrect() {
        Race dto = Race.builder()
                .name("Zolder")
                .recording(true)
                .url("website")
                .build();

        final UpdateRaceCommand command = UpdateRaceCommand.getBuilder()
                .startFrom(dto)
                .build();

        assertThat(command.getName()).isEqualTo("Zolder");
        assertThat(command.getUrl()).isEqualTo("website");
        assertThat(command.getVersion()).isEqualTo(0);
        assertThat(command.isRecording());
    }

}
