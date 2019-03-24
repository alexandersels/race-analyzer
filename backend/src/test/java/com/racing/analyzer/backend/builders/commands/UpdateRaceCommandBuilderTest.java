package com.racing.analyzer.backend.builders.commands;

import com.racing.analyzer.backend.commands.UpdateRaceCommand;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.entities.Race;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateRaceCommandBuilderTest {

    @Test
    public void withNameIsCorrect() {
        UpdateRaceCommand updateRaceCommand = UpdateRaceCommand.getBuilder()
                                                               .withName("Nieuwe Zolder")
                                                               .build();
        assertThat(updateRaceCommand.getName()).isEqualTo("Nieuwe Zolder");
    }

    @Test
    public void withUrlIsCorrect() {
        UpdateRaceCommand updateRaceCommand = UpdateRaceCommand.getBuilder()
                                                               .withUrl("livetiming.com")
                                                               .build();
        assertThat(updateRaceCommand.getUrl()).isEqualTo("livetiming.com");
    }

    @Test
    public void isRecordingIsCorrect() {
        UpdateRaceCommand updateRaceCommand = UpdateRaceCommand.getBuilder()
                                                               .isRecording(true)
                                                               .build();
        assertThat(updateRaceCommand.isRecording());
    }

    @Test
    public void withVersionIsCorrect() {
        UpdateRaceCommandBuilder builder = new UpdateRaceCommandBuilder();
        UpdateRaceCommand updateRaceCommand = builder.withVersion(10).build();
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

        UpdateRaceCommand command = UpdateRaceCommand.getBuilder()
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

        UpdateRaceCommand command = UpdateRaceCommand.getBuilder()
                                                           .startFrom(dto)
                                                           .build();

        assertThat(command.getName()).isEqualTo("Zolder");
        assertThat(command.getUrl()).isEqualTo("website");
        assertThat(command.getVersion()).isEqualTo(0);
        assertThat(command.isRecording());
    }

}
